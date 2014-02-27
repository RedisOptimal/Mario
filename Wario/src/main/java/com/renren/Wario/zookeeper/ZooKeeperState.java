package com.renren.Wario.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.client.FourLetterWordMain;

public class ZooKeeperState {
	private String host = null;
	private int port;
	
	private String statText = null;
	private String mode = null;
	
	public String getMode() {
		update();
		return mode;
	}
	
	public ZooKeeperState(String connectString) {
		String host = connectString.substring(0, connectString.indexOf(':'));
		int port = Integer.parseInt(connectString.substring(connectString
				.indexOf(':') + 1));
		this.host = host;
		this.port = port;
	}
	
	private void update() {
		try {
			statText = cmd("stat");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String cmd(String cmd) throws IOException {
		return FourLetterWordMain.send4LetterWord(host, port, cmd);
	}
	
	public static void main(String[] args) throws IOException {
		ZooKeeperState state = new ZooKeeperState("localhost:21815");
		
	}
}
