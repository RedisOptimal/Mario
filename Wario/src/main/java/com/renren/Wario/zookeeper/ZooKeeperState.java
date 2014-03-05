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
package com.renren.Wario.zookeeper;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.client.FourLetterWordMain;

public class ZooKeeperState {

	Logger logger = LogManager.getLogger(ZooKeeperState.class.getName());

	private final String host;
	private final int port;

	private int minLatency = -1, avgLatency = -1, maxLatency = -1;
	private int received = -1;
	private int sent = -1;
	private int outStanding = -1;
	private String zxid = null;
	private String mode = null;
	private int nodeCount = -1;
	private int totalWatches = -1;

	public ZooKeeperState(String connectionString) {
		String host = connectionString.substring(0,
				connectionString.indexOf(':'));
		int port = Integer.parseInt(connectionString.substring(connectionString
				.indexOf(':') + 1));
		this.host = host;
		this.port = port;
		update();
	}

	public void update() {
		String statText = cmd("srvr");
		Scanner scannerForStat = new Scanner(statText);
		while (scannerForStat.hasNext()) {
			String line = scannerForStat.nextLine();
			if (line.startsWith("Latency min/avg/max:")) {
				String[] latencys = getStringValueFromLine(line).split("/");
				minLatency = Integer.parseInt(latencys[0]);
				avgLatency = Integer.parseInt(latencys[1]);
				maxLatency = Integer.parseInt(latencys[2]);
			} else if (line.startsWith("Received:")) {
				received = Integer.parseInt(getStringValueFromLine(line));
			} else if (line.startsWith("Sent:")) {
				sent = Integer.parseInt(getStringValueFromLine(line));
			} else if (line.startsWith("Outstanding:")) {
				outStanding = Integer.parseInt(getStringValueFromLine(line));
			} else if (line.startsWith("Zxid:")) {
				zxid = getStringValueFromLine(line);
			} else if (line.startsWith("Mode:")) {
				mode = getStringValueFromLine(line);
			} else if (line.startsWith("Node count:")) {
				nodeCount = Integer.parseInt(getStringValueFromLine(line));
			}
		}

		String wchsText = cmd("wchs");
		Scanner scannerForWchs = new Scanner(wchsText);
		while (scannerForWchs.hasNext()) {
			String line = scannerForWchs.nextLine();
			if (line.startsWith("Total watches:")) {
				totalWatches = Integer.parseInt(getStringValueFromLine(line));
			}
		}
	}

	public boolean ruok() {
		String res = "";
		res = cmd("ruok");
		return res.equals("imok\n");
	}

	public int getMinLatency() {
		return minLatency;
	}

	public int getAvgLatency() {
		return avgLatency;
	}

	public int getMaxLatency() {
		return maxLatency;
	}

	public int getReceived() {
		return received;
	}

	public int getSent() {
		return sent;
	}

	public int getOutStanding() {
		return outStanding;
	}

	public String getZxid() {
		return zxid;
	}

	public String getMode() {
		return mode;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public int getTotalWatches() {
		return totalWatches;
	}

	private String getStringValueFromLine(String line) {
		return line.substring(line.indexOf(":") + 1, line.length()).replaceAll(
				" ", "");
	}

	private class SendThread extends Thread {
		private CountDownLatch countDownLatch;
		private String cmd;

		public String ret = "";

		public SendThread(CountDownLatch countDownLatch, String cmd) {
			this.countDownLatch = countDownLatch;
			this.cmd = cmd;
		}

		@Override
		public void run() {
			try {
				ret = FourLetterWordMain.send4LetterWord(host, port, cmd);
			} catch (IOException e) {
				logger.error("Sent " + cmd + " to client " + host + ":" + port
						+ " failed!\n" + e.toString());
			}
			countDownLatch.countDown();
		}

	}

	private String cmd(String cmd) {
		final int waitTimeout = 5;
		CountDownLatch countDownLatch = new CountDownLatch(1);
		SendThread sendThread = new SendThread(countDownLatch, cmd);
		sendThread.start();
		try {
			if (!countDownLatch.await(waitTimeout, TimeUnit.SECONDS)) {
				sendThread.interrupt();
			}
		} catch (InterruptedException e) {
			logger.error("This will not happen.");
		}
		return sendThread.ret;
	}
}
