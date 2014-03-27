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

import java.sql.SQLException;
import java.util.Date;
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

	@Override
	public void run() {
		System.out.println("start at\t" + new Date());

		for (int i = 0; i < MAX_THREAD_NUM; i++) {
			threads[i] = new Thread(new DBWriter());
			threads[i].start();
		}
		
		deleteAll();
		
		try {
			List<String> children = client.getChildren("/");
			Iterator<String> it = children.iterator();
			while (it.hasNext()) {
				queue.add("/" + it.next());
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		
		System.out.println("end at\t" + new Date());
	}

	private void deleteAll() {
		MySQLHelper helper = new MySQLHelper();
		try {
			helper.open();
			String sql = "delete from mario_node_state where zk_id = "
					+ client.getZkId();
			helper.execute(sql);
			helper.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class DBWriter implements Runnable {
		
		MySQLHelper helper = new MySQLHelper();
		String path, sql;

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
					writeToDB(path);
				}
			} catch (ClassNotFoundException e) {
				logger.error("MysqlHelper open failed! " + e.toString());
			} catch (SQLException e) {
				logger.error("MysqlHelper open failed! " + e.toString());
			} catch (KeeperException e) {
				logger.error("Get children failed at " + path + ". " + e.toString());
			} catch (InterruptedException e) {
			} finally {
				try {
					helper.close();
				} catch (SQLException e) {
					logger.error("MysqlHelper close failed! " + e.toString());
				}
			}
		}
		
		private void writeToDB(String path) {
			Stat stat = new Stat();
			String data = "";
			try {
				client.getData(path, stat);
				sql = "insert into mario_node_state (zk_id, path, data, data_length, num_children) values " + "("
						+ client.getZkId() + ", '"
						+ path + "', '"
						+ data + "', "
						+ stat.getDataLength() + ", "
						+ stat.getNumChildren() + ")";
				helper.execute(sql);
			} catch (KeeperException e) {
				logger.error("Get data failed at " + path + ". " + e.toString());
			} catch (InterruptedException e) {
				logger.error("Get data failed at " + path + ". " + e.toString());
			} catch (SQLException e) {
				logger.error("Execute sql '" + sql + "' failed! " + e.toString());
			}
		}
		
	}
}
