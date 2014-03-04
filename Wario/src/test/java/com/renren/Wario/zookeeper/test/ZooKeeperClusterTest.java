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
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.renren.Wario.zookeeper.ZooKeeperClient;
import com.renren.Wario.zookeeper.ZooKeeperCluster;

public class ZooKeeperClusterTest {
	private final String correctConfigString = "{serverIPList:[\"localhost:2181\", \"localhost:2182\"],sessionTimeout:6000}";
	private final String wrongConfigString = "{serverIPList:[\"localhost:2181\"]}";
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
	
	@BeforeClass
	public static void init() {
		new File("Wario.log").delete();
		System.setProperty("default.config.path", "./");
		PropertyConfigurator
				.configure(System.getProperty("user.dir") + File.separator
						+ "conf" + File.separator + "log4j.properties");
	}
	
	
	@Test
	public void generalTest() throws JSONException {
		JSONObject object = new JSONObject(wrongConfigString);
		ZooKeeperCluster cluster = new ZooKeeperCluster("test", object);
		Assert.assertEquals("test", cluster.getZookeeperName());
		try {
			cluster.init();
			Assert.fail();
		} catch (JSONException e) {
		}
		Assert.assertTrue(cluster.getClients().size() == 0);
		object = new JSONObject(correctConfigString);
		try {
			cluster.updateClients(object);
		} catch (JSONException e) {
			Assert.fail();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println(cluster.getClients().toString());
		Assert.assertTrue(cluster.getClients().size() == 2);
		for (ZooKeeperClient client : cluster.getClients().values()) {
			Assert.assertFalse(client.isAvailable());
		}
		
		ZooKeeperBackgroundServer server1 = new ZooKeeperBackgroundServer("2181");
		server1.setDaemon(true);
		server1.start();
		ZooKeeperBackgroundServer server2 = new ZooKeeperBackgroundServer("2182");
		server2.setDaemon(true);
		server2.start();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (ZooKeeperClient client : cluster.getClients().values()) {
			Assert.assertTrue(client.isAvailable());
		}
	}
}
