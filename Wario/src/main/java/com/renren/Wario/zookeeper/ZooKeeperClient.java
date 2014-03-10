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
package com.renren.Wario.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperClient implements Watcher {

	private static Logger logger = LogManager.getLogger(ZooKeeperClient.class
			.getName());

	private ZooKeeper zk = null;
	private final String connectionString;
	private final int sessionTimeout;

	private volatile boolean isAvailable;
	private final String ZK_PATH;
	public ZooKeeperState state = null;

	private CountDownLatch countDownLatch = null;

	public ZooKeeperClient(String connectionString, int sessionTimeout) {
		this.connectionString = connectionString;
		this.sessionTimeout = sessionTimeout;
		isAvailable = false;
		ZK_PATH = "/god_damn_zookeeper_" + connectionString;
		state = new ZooKeeperState(connectionString);
	}

	public void createConnection() {
		releaseConnection();
		countDownLatch = new CountDownLatch(1);

		try {
			zk = new ZooKeeper(connectionString, sessionTimeout, this);
			countDownLatch.await();
		} catch (IOException e) {
			logger.error("Create connection failed! IOException occured!\n"
					+ e.toString());
		} catch (InterruptedException e) {
			logger.error("InterruptedException occured!\n" + e.toString());
		}

		try {
			zk.create(ZK_PATH, "this is a test node.".getBytes(),
					Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (KeeperException e) {

		} catch (InterruptedException e) {

		}
	}

	public void releaseConnection() {
		isAvailable = false;
		if (zk != null) {
			try {
				zk.close();
			} catch (InterruptedException e) {
				logger.error("InterruptedException occured!\n" + e.toString());
			}
		}
	}

	@Override
	public void process(WatchedEvent event) {
		logger.info("SessionWatcher: " + connectionString + "|"
				+ event.getType() + "|" + event.getState());

		if (event.getType() == EventType.None) {
			if (event.getState().equals(KeeperState.SyncConnected)) {
				isAvailable = true;
				countDownLatch.countDown();
			} else if (event.getState().equals(KeeperState.Expired)
					|| event.getState().equals(KeeperState.Disconnected)) {
				isAvailable = false;
				createConnection();
			}
		}
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * @return the connectionString
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * @return the sessionTimeout
	 */
	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public Stat exists(String path) throws KeeperException,
			InterruptedException {
		return zk.exists(path, false);
	}

	public byte[] getData(String path) throws KeeperException,
			InterruptedException {
		return zk.getData(path, false, null);
	}

	public List<String> getChildren(String path) throws KeeperException,
			InterruptedException {
		return zk.getChildren(path, false);
	}

	public Stat testExists(String path) throws KeeperException,
			InterruptedException {
		return zk.exists(ZK_PATH + path, false);
	}

	public void testCreate(String path, byte[] data) throws KeeperException,
			InterruptedException {
		zk.create(ZK_PATH + path, data, Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
	}

	public byte[] testGetData(String path) throws KeeperException,
			InterruptedException {
		return zk.getData(ZK_PATH + path, false, null);
	}

	public void testSetData(String path, byte[] data) throws KeeperException,
			InterruptedException {
		zk.setData(ZK_PATH + path, data, -1);
	}

	public List<String> testGetChildren(String path) throws KeeperException,
			InterruptedException {
		return zk.getChildren(path, false);
	}

	public void testDdelete(String path) throws InterruptedException,
			KeeperException {
		zk.delete(ZK_PATH + path, -1);
	}

}
