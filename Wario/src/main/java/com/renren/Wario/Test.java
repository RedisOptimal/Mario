package com.renren.Wario;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int c = 0;
		while(true) {
			ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, null);
			System.out.println(++ c);
		}
	}

}
