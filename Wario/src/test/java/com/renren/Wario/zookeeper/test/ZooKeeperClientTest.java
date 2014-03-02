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

		@Override
		public void run() {
			String[] args = new String[2];
			args[0] = "2181";
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
		if (zkBackgroundServer == null) {
			zkBackgroundServer = new ZooKeeperBackgroundServer();
			zkBackgroundServer.setDaemon(true);
			zkBackgroundServer.start();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void generalTest() {
		ZooKeeperClient zooKeeperClient = new ZooKeeperClient("localhost:2181",
				5000);
		Assert.assertFalse(zooKeeperClient.isAvailable());
		Assert.assertEquals(zooKeeperClient.state.getMode(), "standalone");
		zooKeeperClient.createConnection();
		Assert.assertEquals(zooKeeperClient.getConnectionString(),
				"localhost:2181");
		Assert.assertEquals(zooKeeperClient.getSessionTimeout(), 5000);
		Assert.assertTrue(zooKeeperClient.isAvailable());
		zooKeeperClient.releaseConnection();
		Assert.assertFalse(zooKeeperClient.isAvailable());
		zooKeeperClient.createConnection();
		Assert.assertTrue(zooKeeperClient.isAvailable());
		Assert.assertEquals(zooKeeperClient.state.getMode(), "standalone");
	}

}
