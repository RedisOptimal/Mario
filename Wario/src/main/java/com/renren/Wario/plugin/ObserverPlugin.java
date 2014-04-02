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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import com.renren.Wario.db.MySQLHelper;

public class ObserverPlugin extends IPlugin {

	private static final Logger logger = LogManager
			.getLogger(ObserverPlugin.class);
	
	private static final int MAX_THREAD_NUM = 20;

	private static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1 << 16);
	private Thread[] threads = new Thread[MAX_THREAD_NUM];
	private int stateVersion;
	
	@Override
	public void run() {
		if(client == null) {
			return ;
		}
//		System.out.println("start at\t" + new Date());
		
		stateVersion = getMaxStateVersion() + 1;
		
		for (int i = 0; i < MAX_THREAD_NUM; i++) {
			threads[i] = new Thread(new DBWriter());
			threads[i].start();
		}
		
		try {
			List<String> children = client.getChildren("/");
			Iterator<String> it = children.iterator();
			while (it.hasNext()) {
				queue.add("/" + it.next());
			}
		} catch (KeeperException e) {
			logger.error("Get children failed at /. " + e.toString());
		} catch (InterruptedException e) {
			logger.error("Get children failed at /. " + e.toString());
		} catch (Exception e) {
			logger.error("Get children failed at /. " + e.toString());
		}
		
		do {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!queue.isEmpty());
		
		for (int i = 0; i < MAX_THREAD_NUM; i++) {
			threads[i].interrupt();
		}
		
//		System.out.println("end at\t" + new Date());
	}
	
	private int getMaxStateVersion() {
		int maxVersion = -1;
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
		return maxVersion;
	}

	private class DBWriter implements Runnable {
		
		private MySQLHelper helper = new MySQLHelper();
		private Stat stat = new Stat();
		private String path;
		private String sql;
		
		@Override
		public void run() {
			try {
				helper.open();
				while (true) {
					path = queue.take();
					List<String> children = client.getChildren(path);
					Iterator<String> it = children.iterator();
					while (it.hasNext()) {
						queue.add(path + "/" + it.next());
					}
					writeToDB();
				}
			} catch (ClassNotFoundException e) {
				logger.error("MysqlHelper open failed! " + e.toString());
			} catch (SQLException e) {
				logger.error("MysqlHelper open failed! " + e.toString());
			} catch (KeeperException e) {
				logger.error("Get children failed at " + path + ". "
						+ e.toString());
			} catch (InterruptedException e) {
			} finally {
				try {
					helper.close();
				} catch (SQLException e) {
					logger.error("MysqlHelper close failed! " + e.toString());
				}
			}
		}
		
		private void writeToDB() {
			
			try {
				byte[] data = client.getData(path, stat);
				long mzxid = getMaxMzxid(path);
				if(mzxid == stat.getMzxid()) {
					sql = "update mario_node_state set data = '" + getString(data) 
							+ "', data_length = " + stat.getDataLength()
							+ ", num_children = " + stat.getNumChildren()
							+ ", version = " + stat.getVersion()
							+ ", aversion = " + stat.getAversion()
							+ ", cversion = " + stat.getCversion()
							+ ", ctime = " + stat.getCtime()
							+ ", mtime = " + stat.getMtime()
							+ ", czxid = " + stat.getCzxid()
							+ ", mzxid = " + stat.getMzxid()
							+ ", pzxid = " + stat.getMzxid()
							+ ", ephemeral_owner = " + stat.getEphemeralOwner()
							+ ", state_version = " + stateVersion
							+ ", state_time = " + "now()"
							+ " where zk_id = " + client.getZkId()
							+ " and path = '" + path + "'"
							+ " and mzxid = " + mzxid;
				} else {
					sql = "insert into mario_node_state (zk_id, path, data, data_length, num_children, version, aversion, cversion, ctime, mtime, czxid, mzxid, pzxid, ephemeral_owner, state_version, state_time) values " + "("
							+ client.getZkId() + ", '"
							+ path + "', '"
							+ getString(data) + "', "
							+ stat.getDataLength() + ", "
							+ stat.getNumChildren() + ", " 
							+ stat.getVersion() + ", " 
							+ stat.getAversion() + ", " 
							+ stat.getCversion() + ", " 
							+ stat.getCtime() + ", " 
							+ stat.getMtime() + ", " 
							+ stat.getCzxid() + ", " 
							+ stat.getMzxid() + ", " 
							+ stat.getPzxid() + ", " 
							+ stat.getEphemeralOwner() + ", "
							+ stateVersion + ", "
							+ "now()"+ ")";
				}
				helper.execute(sql);
			} catch (KeeperException e) {
				logger.error("Get data failed at " + path + ". " + e.toString());
			} catch (InterruptedException e) {
				logger.error("Get data failed at " + path + ". " + e.toString());
			} catch (SQLException e) {
				logger.error("Execute sql '" + sql + "' failed! "
						+ e.toString());
			}
		}
		
		private String getString(byte[] data) {
			String res = "";
			if (data != null && data.length < 255) {
				for (int i = 0; i < data.length && (int) data[i] > 0; i++) {
					res += (char) data[i];
				}
			}
			return res;
		}
		
		private long getMaxMzxid(String path) throws SQLException {
			long maxZxid = 0L;
			sql = "select max(mzxid) from mario_node_state where zk_id = "
					+ client.getZkId() + " and path = '" + path + "'";
			ResultSet rs = helper.executeQuery(sql);
			while (rs.next()) {
				maxZxid = rs.getLong("max(mzxid)");
			}
			return maxZxid;
		}
	}
}
