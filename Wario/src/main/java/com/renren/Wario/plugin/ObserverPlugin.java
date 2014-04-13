/**
 *    Copyright 2014 Renren.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.renren.Wario.plugin;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import com.renren.Wario.db.MySQLHelper;

public class ObserverPlugin extends IPlugin {

	private static final Logger logger = LogManager
			.getLogger(ObserverPlugin.class.getName());

	private static final int MAX_THREAD_NUM = 2;
	private Thread[] dumpThreads = new Thread[MAX_THREAD_NUM];

	private class PathAndStat {
		private final String path;
		private final Stat stat;
		private final byte[] data;
		public PathAndStat(String path, byte[] data, Stat stat) {
			this.path = path;
			this.data = data;
			this.stat = stat;
		}
		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}
		/**
		 * @return the data
		 */
		public byte[] getData() {
			return data;
		}
		/**
		 * @return the stat
		 */
		public Stat getStat() {
			return stat;
		}
	}

	private final BlockingQueue<PathAndStat> trackQueue = new LinkedBlockingQueue<PathAndStat>();
	private final BlockingQueue<PathAndStat> saveQueue = new LinkedBlockingQueue<PathAndStat>();

	private final CountDownLatch keyFieldLatch = new CountDownLatch(1);

	@Override
	public void run() {
		if (client == null) {
			return;
		}

		Long startTime = System.currentTimeMillis();
		int stateVersion = getNextStateVersion();

		Stat stat = new Stat();
		byte[] data = null;
		try {
			data = client.getData("/", stat);
		} catch (KeeperException e) {
			logger.error("Exception when get root " + e);
			return;
		} catch (InterruptedException e) {
			logger.error("Thread interrupted");
		}
		PathAndStat root = new PathAndStat("/", data, stat);
		trackQueue.offer(root);
		saveQueue.offer(root);

		for (int i = 0; i < dumpThreads.length; ++i) {
			dumpThreads[i] = new Thread(new DBWriter(stateVersion));
			dumpThreads[i].start();
		}
		logger.error("Init finished "
				+ (System.currentTimeMillis() - startTime));
		try {
			PathAndStat node;
			while (!trackQueue.isEmpty()) {
				node = trackQueue.take();
				List<String> children;
				try {
					children = client.getChildren(node.getPath());
				} catch (KeeperException e) {
					logger.error("Exception when track path " + node.getPath()
							+ e);
					continue;
				}
				Iterator<String> it = children.iterator();
				while (it.hasNext()) {
					String child = it.next();
					stat = new Stat();
					try {
						data = client.getData((node.getPath().endsWith("/")
								? node.getPath()
								: node.getPath() + "/") + child, stat);
						PathAndStat tmpNode = new PathAndStat((node.getPath()
								.endsWith("/")
								? node.getPath()
								: node.getPath() + "/")
								+ child, data, stat);
						trackQueue.offer(tmpNode);
						saveQueue.offer(tmpNode);
					} catch (KeeperException e) {
						logger.error("Exception when track path "
								+ node.getPath() + e);
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("Thread interrupted");
		} finally {
			keyFieldLatch.countDown();
		}
		logger.info("Track done " + (System.currentTimeMillis() - startTime));
		try {
			for (int i = 0; i < dumpThreads.length; ++i) {
				dumpThreads[i].join();
			}
		} catch (InterruptedException e) {
			logger.error("Thread interrupted");
		}

		logger.info("Run main thread "
				+ (System.currentTimeMillis() - startTime) + " ms");
	}

	private int getNextStateVersion() {
		int maxVersion = 0;
		if (clusterContext[0] == '#' && clusterContext[5] == '#') {
			for (int i = 1; i <= 4; ++i) {
				maxVersion = maxVersion << 8 + (short) clusterContext[i];
			}
		} else {
			MySQLHelper helper = new MySQLHelper();
			String sql = "select max(state_version) from mario_node_state";
			try {
				helper.open();
				ResultSet rs = helper.executeQuery(sql);
				while (rs.next()) {
					maxVersion = rs.getInt("max(state_version)");
				}
			} catch (ClassNotFoundException e) {
				logger.error("MysqlHelper open failed! " + e.toString());
			} catch (SQLException e) {
				logger.error("MysqlHelper open failed or execute sql " + sql
						+ " failed! " + e.toString());
			} finally {
				try {
					helper.close();
				} catch (SQLException e) {
					logger.error("MysqlHelper close failed! " + e.toString());
				}
			}
		}
		int tmp = ++maxVersion;

		clusterContext[0] = '#';
		for (int i = 4; i > 0; --i) {
			clusterContext[i] = (byte) (tmp & 0xff);
			tmp >>= 8;
		}
		clusterContext[5] = '#';
		return maxVersion;
	}

	private class DBWriter implements Runnable {

		private MySQLHelper helper = new MySQLHelper();
		private String sql;
		private final int nextStatVersion;
		private PreparedStatement updatePs;
		private PreparedStatement insertPs;
		
		public DBWriter(int nextStatVersion) {
			this.nextStatVersion = nextStatVersion;
			try {
				helper.open();
				updatePs = helper
						.getPreparedStatement("update mario_node_state set data = ? "
								+ ", data_length = ? "
								+ ", num_children = ? "
								+ ", version = ? "
								+ ", aversion = ? "
								+ ", cversion = ? "
								+ ", ctime = ? "
								+ ", mtime = ? "
								+ ", czxid = ? "
								+ ", mzxid = ? "
								+ ", pzxid = ? "
								+ ", ephemeral_owner = ? "
								+ ", state_version = ? "
								+ ", state_time = now() "
								+ "where zk_id = ?  and path = ? and mzxid = ? ");
				insertPs = helper
						.getPreparedStatement("insert into mario_node_state (zk_id, " +
								"path, data, data_length, num_children, version, aversion, " + 
								"cversion, ctime, mtime, czxid, mzxid, pzxid, ephemeral_owner, " +
								"state_version, state_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
			} catch (SQLException e) {
				logger.error("MysqlHelper open failed! " + e.toString());
			} catch (ClassNotFoundException e) {
				logger.error("MysqlHelper open failed! " + e.toString());
			}
		}
		
		@Override
		public void run() {
			try {
				while (true) {
					PathAndStat node = null;
					while (!saveQueue.isEmpty()) {
						try {
							synchronized (saveQueue) {
								if (!saveQueue.isEmpty()) {
									node = saveQueue.take();
								} else {
									continue;
								}
							}
							try {
								writeToDB(node);
							} catch (SQLException e) {
								logger.error("Execute sql failed! "
										+ e.toString());
							}
						} catch (InterruptedException e) {
							logger.error("DB thread interrupted.");
						}
					}
					try {
						if (keyFieldLatch.await(100, TimeUnit.MILLISECONDS)) {
							break;
						}
					} catch (InterruptedException e) {
					}
				}
			} finally {
				try {
					updatePs.close();
					insertPs.close();
					helper.close();
				} catch (SQLException e) {
					logger.error("MysqlHelper close failed! " + e.toString());
				}
			}
		}

		private void writeToDB(PathAndStat node) throws SQLException {
				updatePs.setBytes(1, node.getData());
				updatePs.setInt(2, node.getStat().getDataLength());
				updatePs.setInt(3, node.getStat().getNumChildren());
				updatePs.setInt(4, node.getStat().getVersion());
				updatePs.setInt(5, node.getStat().getAversion());
				updatePs.setInt(6, node.getStat().getCversion());
				updatePs.setLong(7, node.getStat().getCtime());
				updatePs.setLong(8, node.getStat().getMtime());
				updatePs.setLong(9, node.getStat().getCzxid());
				updatePs.setLong(10, node.getStat().getMzxid());
				updatePs.setLong(11, node.getStat().getPzxid());
				updatePs.setLong(12, node.getStat().getEphemeralOwner());
				updatePs.setInt(13, nextStatVersion);
				updatePs.setInt(14, client.getZkId());
				updatePs.setString(15, node.getPath());
				updatePs.setLong(16, node.getStat().getMzxid());
			if (updatePs.executeUpdate() == 0) {
				insertPs.setInt(1, client.getZkId());
				insertPs.setString(2, node.getPath());
				insertPs.setBytes(3, node.getData());
				insertPs.setInt(4, node.getStat().getDataLength());
				insertPs.setInt(5, node.getStat().getNumChildren());
				insertPs.setInt(6, node.getStat().getVersion());
				insertPs.setInt(7, node.getStat().getAversion());
				insertPs.setInt(8, node.getStat().getCversion());
				insertPs.setLong(9, node.getStat().getCtime());
				insertPs.setLong(10, node.getStat().getMtime());
				insertPs.setLong(11, node.getStat().getCzxid());
				insertPs.setLong(12, node.getStat().getMzxid());
				insertPs.setLong(13, node.getStat().getPzxid());
				insertPs.setLong(14, node.getStat().getEphemeralOwner());
				insertPs.setInt(15, nextStatVersion);
				insertPs.execute();
			}
		}
	}
}
