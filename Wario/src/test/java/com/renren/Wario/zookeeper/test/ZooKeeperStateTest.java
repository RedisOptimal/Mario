package com.renren.Wario.zookeeper.test;

import static org.junit.Assert.*;

import org.apache.zookeeper.server.quorum.QuorumPeerMain;
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
	
	@Test
	public void test() {
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
		
		ZooKeeperState state = new ZooKeeperState("localhost:2181");
		
		assertEquals(1, state.getReceived());
		assertEquals(0, state.getSent());
		assertEquals(0, state.getOutStanding());
		assertEquals("standalone", state.getMode());
		assertEquals(4, state.getNodeCount());
		assertEquals(0, state.getTotalWatches());
		
	}

}
