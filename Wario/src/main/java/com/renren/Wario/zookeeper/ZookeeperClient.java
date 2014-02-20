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
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * 提供Zookeeper的抽象数据模型，保证服务安全
 */
public class ZookeeperClient implements Comparable<ZookeeperClient>, Comparator<ZookeeperClient> {

    private static Logger logger =
            LogManager.getLogger(ZookeeperClient.class.getName());

    private final int maxRetryTimes = 5;
    private ZooKeeper zk = null;
    private final String connectString;
    private final int sessionTimeout;
    private boolean isAvailable;

    private class SessionWatcher implements Watcher {

        private CountDownLatch countDownLatch;

        public SessionWatcher(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == EventType.None) {
                if (event.getState().equals(KeeperState.SyncConnected)) {
                    countDownLatch.countDown();
                } else if (event.getState().equals(KeeperState.Expired)
                           || event.getState().equals(KeeperState.Disconnected)) {
                	ZookeeperClient.this.isAvailable = false;
                	int retryTimes = 0;
                	while (!ZookeeperClient.this.isAvailable) {
	                    try {
	                        countDownLatch = new CountDownLatch(1);
	                        zk.close();
	                        zk = new ZooKeeper(
	                                    ZookeeperClient.this.getConnectString(),
	                                    ZookeeperClient.this.getSessionTimeout(),
	                                    new SessionWatcher(countDownLatch));
	                        countDownLatch.await(20, TimeUnit.SECONDS);
	                        ZookeeperClient.this.isAvailable = true;
	                    } catch (IOException e) {
	                        logger.error("Can't connect zookeeper "
	                                     + ZookeeperClient.this.getConnectString()
	                                     + " with : " + e.toString());
	                        e.printStackTrace();
	        	            retryTimes++;
	        	        	if (retryTimes > maxRetryTimes) {
	        	        		logger.error("Can't connect zookeeper " + ZookeeperClient.this.connectString + ", maybe wrong address or zookeeper have down.");
	        	        		break;
	        	        	}
	                    } catch (InterruptedException e) {
	                        logger.error("Connect zookeeper "
	                                     + ZookeeperClient.this.getConnectString()
	                                     + " timeout : " + e.toString());
	                        e.printStackTrace();
	        	            retryTimes++;
	        	        	if (retryTimes > maxRetryTimes) {
	        	        		logger.error("Can't connect zookeeper " + ZookeeperClient.this.connectString + ", maybe wrong address or zookeeper have down.");
	        	        		break;
	        	        	}
	                    }
	                }
                }
            }
        }
    }

    public ZookeeperClient(String connectString, int sessionTimeout){
        this.connectString = connectString;
        this.sessionTimeout = sessionTimeout;
        this.isAvailable = false;
    }

    public void Init() throws IOException {
        if (zk != null) {
            try {
                zk.close();
                zk = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int retryTimes = 0;
        while (!this.isAvailable) {
	        final CountDownLatch countDownLatch = new CountDownLatch(1);
	        zk = new ZooKeeper(connectString, sessionTimeout,
	                    new SessionWatcher(countDownLatch));
	        try {
	        	// BUG await的用法不对
	            countDownLatch.await(20, TimeUnit.SECONDS);
	            this.isAvailable = true;
	        } catch (InterruptedException e) {
	            logger.error("Can't connect zookeeper "
	                         + ZookeeperClient.this.getConnectString() + " with : "
	                         + e.toString());
	            e.printStackTrace();
	            retryTimes++;
	        	if (retryTimes > maxRetryTimes) {
	        		logger.error("Can't connect zookeeper " + ZookeeperClient.this.connectString + ", maybe wrong address or zookeeper have down.");
	        		throw new IOException("Can't connect zookeeper, maybe wrong address or zookeeper have down.");
	        	}
	        }
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

    /*
     * (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(ZookeeperClient o1, ZookeeperClient o2) {
        return o1.compareTo(o2);
    }

    /*
     * (non-Javadoc)
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
        if (zk == null) {
            return;
        }
        try {
            this.zk.close();
            this.zk = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @return zk客户端目前的状态
     */
    public States getClientState() {
        if (zk != null) {
            return zk.getState();
        }
        return null;
    }
    
    @Override
    protected void finalize() throws Throwable {
        close();
    }
}
