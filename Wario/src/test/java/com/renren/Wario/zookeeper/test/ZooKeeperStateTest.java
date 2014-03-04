package com.renren.Wario.zookeeper.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.server.quorum.QuorumPeerMain;
import org.junit.BeforeClass;
import org.junit.Test;

import com.renren.Wario.zookeeper.ZooKeeperState;

public class ZooKeeperStateTest {

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
	public void test() throws IOException, KeeperException {

		ZooKeeperState state = new ZooKeeperState("localhost:2181");
		assertEquals(-1, state.getReceived());
		assertEquals(-1, state.getSent());
		assertEquals(-1, state.getOutStanding());
		assertNull(state.getMode());
		assertEquals(-1, state.getNodeCount());
		assertEquals(-1, state.getTotalWatches());
		state.update();
		assertEquals(-1, state.getReceived());
		assertEquals(-1, state.getSent());
		assertEquals(-1, state.getOutStanding());
		assertNull(state.getMode());
		assertEquals(-1, state.getNodeCount());
		assertEquals(-1, state.getTotalWatches());

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

		assertEquals(-1, state.getReceived());
		assertEquals(-1, state.getSent());
		assertEquals(-1, state.getOutStanding());
		assertNull(state.getMode());
		assertEquals(-1, state.getNodeCount());
		assertEquals(-1, state.getTotalWatches());

		state.update();
		assertEquals(1, state.getReceived());
		assertEquals(0, state.getSent());
		assertEquals(0, state.getOutStanding());
		assertEquals("standalone", state.getMode());
		assertEquals(4, state.getNodeCount());
		assertEquals(0, state.getTotalWatches());
		ZooKeeper zk = new ZooKeeper("localhost:2181", 5000, null);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			zk.create("/test", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (InterruptedException e) {
		}
		state.update();
		assertEquals(6, state.getReceived());
		assertEquals(5, state.getSent());
		assertEquals(0, state.getOutStanding());
		assertEquals("standalone", state.getMode());
		assertEquals(5, state.getNodeCount());
		assertEquals(0, state.getTotalWatches());
		try {
			zk.delete("/test", -1);
		} catch (KeeperException e) {
		} catch (InterruptedException e) {
		}
		state.update();
		assertEquals(9, state.getReceived());
		assertEquals(8, state.getSent());
		assertEquals(0, state.getOutStanding());
		assertEquals("standalone", state.getMode());
		assertEquals(4, state.getNodeCount());
		assertEquals(0, state.getTotalWatches());
	}

}
