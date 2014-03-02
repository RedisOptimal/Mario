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

import org.apache.log4j.PropertyConfigurator;
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
	public void generalTest() {
		final ZooKeeperClient zooKeeperClient = new ZooKeeperClient("localhost:2181",
				5000);
		Assert.assertFalse(zooKeeperClient.isAvailable());
		Assert.assertNull(zooKeeperClient.state.getMode());
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
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Assert.assertTrue(zooKeeperClient.isAvailable());
		Assert.assertTrue(zooKeeperClient.state.isModeChanged());
		Assert.assertEquals(zooKeeperClient.state.getMode(), "standalone");
		Assert.assertEquals(zooKeeperClient.getConnectionString(),
				"localhost:2181");
		Assert.assertEquals(zooKeeperClient.getSessionTimeout(), 5000);
		Assert.assertTrue(zooKeeperClient.isAvailable());
		zooKeeperClient.releaseConnection();
		Assert.assertFalse(zooKeeperClient.isAvailable());
		zooKeeperClient.createConnection();
		Assert.assertTrue(zooKeeperClient.isAvailable());
	}

}
