package com.renren.Wario.zookeeper;

import java.awt.image.RescaleOp;
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
	
	private static Logger logger = LogManager.getLogger(ZooKeeperClient.class.getName());
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}
	
	public String doCommand(String command) {
		String res = null;
		String host = connectString.substring(0, connectString.indexOf(':'));
		int port = Integer.parseInt(connectString.substring(connectString.indexOf(':') + 1));
		try {
			res = FourLetterWordMain.send4LetterWord(host, port, command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	private class SessionWatcher implements Watcher {
		
		public void process(WatchedEvent event) {
			// TODO Auto-generated method stub
			if(event.getType() == EventType.None) {
				if(event.getState().equals(KeeperState.SyncConnected)) {
					countDownLatch.countDown();
				} else if (event.getState().equals(KeeperState.Expired) || event.getState().equals(KeeperState.Disconnected)) {
					isAvailable = false;
					connect();
				}
			}
		}
	}
	
	private void connect() {
		retryTimes = 0;
		while(!isAvailable && retryTimes < maxRetryTimes) {
			countDownLatch = new CountDownLatch(1);
			try {
				if(zk != null) {
					zk.close();
				}
				zk = new ZooKeeper(connectString, sessionTimeout, new SessionWatcher());
				if(countDownLatch.await(waitTime, TimeUnit.SECONDS)) {
					isAvailable = true;
				} else {
					checkRetryTimes();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				checkRetryTimes();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(retryTimes >= maxRetryTimes) {
			logger.error("Can't connect zookeeper, maybe wrong address or zookeeper have down.");
		}
	}

	private void checkRetryTimes() {
		retryTimes ++;
		logger.error("Can't connect zookeeper " + connectString);
		if(retryTimes >= maxRetryTimes) {
			logger.error("Can't connect zookeeper " + connectString + ", maybe wrong address or zookeeper have down.");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZooKeeperClient zkClient = new ZooKeeperClient("localhost:2181", 3000);
		
		if(zkClient.isAvailable) {
			System.out.println(zkClient.doCommand("ruok"));
			System.out.println(zkClient.doCommand("stat"));
		}

		//while(true);
	}

}
