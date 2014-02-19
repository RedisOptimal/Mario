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
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
/**
 * This class is model of zookeeper, and we provided some safe
 * operations.
 * 
 * @author zhe.yuan
 *
 */
public class ZookeeperClient implements Comparable<ZookeeperClient>, Comparator<ZookeeperClient> {
	
	private static Logger logger = LogManager.getLogger(ZookeeperClient.class
			.getName());
	
	private ZooKeeper zk = null;
	private final String connectString;
	private final int sessionTimeout;
	private boolean isAvailable;
	
	private class SessionWatcher implements Watcher {
		private CountDownLatch countDownLatch;
		public SessionWatcher(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}
		
		@Override
		public void process(WatchedEvent event) {
			if (event.getType() == EventType.None) {
				if (event.getState().equals(KeeperState.SyncConnected)) {
					countDownLatch.countDown();
				} else if (event.getState().equals(KeeperState.Expired)
						|| event.getState().equals(KeeperState.Disconnected)) {
					try {
						countDownLatch = new CountDownLatch(1);
						zk = new ZooKeeper(ZookeeperClient.this.getConnectString(),
								ZookeeperClient.this.getSessionTimeout(), new SessionWatcher(countDownLatch));
						countDownLatch.await(50, TimeUnit.SECONDS);
					} catch (IOException e) {
						logger.error("Can't connect zookeeper with : " + e.toString());
						e.printStackTrace();
					} catch (InterruptedException e) {
						logger.error("Connect zookeeper timeout : " + e.toString());
						e.printStackTrace();
					}
				}
			}
		}		
	}
	
	public ZookeeperClient(String connectString, int sessionTimeout) {
		this.connectString = connectString;
		this.sessionTimeout = sessionTimeout;
		this.isAvailable = true;
	}

	public void Init() throws IOException {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		zk = new ZooKeeper(connectString, sessionTimeout, new SessionWatcher(countDownLatch));
		try {
			countDownLatch.await(50, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("Connect zookeeper timeout : " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the connectString
	 */
	public String getConnectString() {
		return connectString;
	}

	/**
	 * @return the sessionTimeout
	 */
	public int getSessionTimeout() {
		return sessionTimeout;
	}
	
	@Override
	public boolean equals(Object o) {
		return (this.compareTo((ZookeeperClient) o) == 0);
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(ZookeeperClient o1, ZookeeperClient o2) {
		return o1.compareTo(o2);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ZookeeperClient o) {
		if (this.getConnectString().equals(o.getConnectString())) {
			return this.getSessionTimeout() - o.getSessionTimeout();
		} else {
			return this.getConnectString().compareTo(o.getConnectString());
		}
	}

	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void close() {
		this.isAvailable = false;
		try {
			this.zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
