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
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperClient implements Watcher {

	private static Logger logger = LogManager.getLogger(ZooKeeperClient.class
			.getName());

	private ZooKeeper zk = null;
	private String connectionString = null;
	private int sessionTimeout;
	public ZooKeeperState state = null;
	
	private boolean isAvailable;
	private CountDownLatch countDownLatch = null;

	public ZooKeeperClient(String connectionString, int sessionTimeout) {
		this.connectionString = connectionString;
		this.sessionTimeout = sessionTimeout;
		this.state = new ZooKeeperState(connectionString);
	}

	public void createConnection() {
		releaseConnection();
		countDownLatch = new CountDownLatch(1);

		try {
			zk = new ZooKeeper(connectionString, sessionTimeout, this);
			countDownLatch.await();
		} catch (IOException e) {
			logger.error("Create connection failed! IOException occured!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error("InterruptedException occured!");
			e.printStackTrace();
		}
	}

	public void releaseConnection() {
		if (zk != null) {
			try {
				zk.close();
				isAvailable = false;
			} catch (InterruptedException e) {
				logger.error("InterruptedException occured!");
				e.printStackTrace();
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

	public String getConnectionString() {
		return connectionString;
	}

}
