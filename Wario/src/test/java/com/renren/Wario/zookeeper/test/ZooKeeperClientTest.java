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
package com.renren.Wario.zookeeper.test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.apache.zookeeper.server.quorum.QuorumPeerMain;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.renren.Wario.zookeeper.ZooKeeperClient;

public class ZooKeeperClientTest {
	private static class ZooKeeperBackgroundServer extends Thread {
		private final String port;

		public ZooKeeperBackgroundServer(String port) {
			this.port = port;
		}

		@Override
		public void run() {
			String[] args = new String[2];
			args[0] = port;
			args[1] = "./zk_test_data/zkdata" + this.getId();
			QuorumPeerMain.main(args);
		}
	}

	private static ZooKeeperBackgroundServer zkBackgroundServer = null;

	@BeforeClass
	public static void init() {
		new File("Wario.log").delete();
		System.setProperty("default.config.path", "./");
		PropertyConfigurator
				.configure(System.getProperty("user.dir") + File.separator
						+ "conf" + File.separator + "log4j.properties");
	}

	@Test
	public void generalTest() throws InterruptedException, IOException,
			KeeperException {
		final ZooKeeperClient zooKeeperClient = new ZooKeeperClient(
				"localhost:2181", 5000);
		Assert.assertFalse(zooKeeperClient.isAvailable());
		new Thread() {
			@Override
			public void run() {
				zooKeeperClient.createConnection();
			}
		}.start();

		Assert.assertFalse(zooKeeperClient.isAvailable());

		if (zkBackgroundServer == null) {
			zkBackgroundServer = new ZooKeeperBackgroundServer("2181");
			zkBackgroundServer.setDaemon(true);
			zkBackgroundServer.start();
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Assert.assertTrue(zooKeeperClient.isAvailable());
		Assert.assertEquals("localhost:2181",
				zooKeeperClient.getConnectionString());
		Assert.assertEquals(5000, zooKeeperClient.getSessionTimeout());
		Assert.assertTrue(zooKeeperClient.isAvailable());
		zooKeeperClient.releaseConnection();
		Assert.assertFalse(zooKeeperClient.isAvailable());
		zooKeeperClient.createConnection();
		Assert.assertTrue(zooKeeperClient.isAvailable());

		CountDownLatch countDownLatch = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, new MyWatcher(
				countDownLatch));
		countDownLatch.await();

		try {
			ArrayList<ACL> acls = new ArrayList<ACL>();
			Id id = new Id("digest",
					DigestAuthenticationProvider.generateDigest("admin:admin"));
			ACL acl = new ACL(ZooDefs.Perms.ALL, id);
			acls.add(acl);
			zk.create("/testACL", "testACL".getBytes(), acls,
					CreateMode.PERSISTENT);

			ZooKeeperClient zooKeeperClient2 = new ZooKeeperClient(
					"localhost:2181", 2000, "admin:admin");
			zooKeeperClient2.createConnection();
			Assert.assertEquals("testACL",
					new String(zooKeeperClient2.getData("/testACL")));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} finally {
			zk.delete("/testACL", -1);
		}
	}

	private class MyWatcher implements Watcher {

		private CountDownLatch coutCountDownLatch;

		public MyWatcher(CountDownLatch countDownLatch) {
			this.coutCountDownLatch = countDownLatch;
		}

		@Override
		public void process(WatchedEvent event) {
			System.err.println("MyWatcher " + event.getType() + " | "
					+ event.getState());
			if (event.getType() == EventType.None) {
				if (event.getState().equals(KeeperState.SyncConnected)) {
					coutCountDownLatch.countDown();
				}
			}
		}
	}
}
