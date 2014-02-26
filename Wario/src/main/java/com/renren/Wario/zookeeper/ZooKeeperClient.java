/**
 * Copyright 2014 Renren.com Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.renren.Wario.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.FourLetterWordMain;

public class ZooKeeperClient {

	private static Logger logger = LogManager.getLogger(ZooKeeperClient.class
			.getName());

	private final int maxRetryTimes = 3;
	private final long waitTime = 20;

	private ZooKeeper zk = null;
	private String connectString = null;
	private int sessionTimeout;

	private boolean isAvailable;
	private int retryTimes;
	private CountDownLatch countDownLatch = null;

	public ZooKeeperClient(String connectString, int sessionTimeout) {
		this.connectString = connectString;
		this.sessionTimeout = sessionTimeout;
		this.isAvailable = false;

		connect();
	}

	public void close() {
		isAvailable = false;
		try {
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	private String doCommand(String command) {
		String res = null;
		String host = connectString.substring(0, connectString.indexOf(':'));
		int port = Integer.parseInt(connectString.substring(connectString
				.indexOf(':') + 1));
		try {
			res = FourLetterWordMain.send4LetterWord(host, port, command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	private class SessionWatcher implements Watcher {

		public void process(WatchedEvent event) {
			if (event.getType() == EventType.None) {
				if (event.getState().equals(KeeperState.SyncConnected)) {
					countDownLatch.countDown();
				} else if (event.getState().equals(KeeperState.Expired)
						|| event.getState().equals(KeeperState.Disconnected)) {
					isAvailable = false;
					connect();
				}
			}
		}
	}

	private void connect() {
		retryTimes = 0;
		while (!isAvailable && retryTimes < maxRetryTimes) {
			countDownLatch = new CountDownLatch(1);
			try {
				if (zk != null) {
					zk.close();
				}
				zk = new ZooKeeper(connectString, sessionTimeout,
						new SessionWatcher());
				if (countDownLatch.await(waitTime, TimeUnit.SECONDS)) {
					isAvailable = true;

					System.err.println(connectString + " " + sessionTimeout
							+ " connect successfully!");
				} else {
					checkRetryTimes();
				}
			} catch (IOException e) {
				e.printStackTrace();
				checkRetryTimes();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (retryTimes >= maxRetryTimes) {
			logger.error("Can't connect zookeeper, maybe wrong address or zookeeper have down.");
		}
	}

	private void checkRetryTimes() {
		retryTimes++;
		logger.error("Can't connect zookeeper " + connectString);
		if (retryTimes >= maxRetryTimes) {
			logger.error("Can't connect zookeeper " + connectString
					+ ", maybe wrong address or zookeeper have down.");
		}
	}

}
