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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.client.FourLetterWordMain;

public class ZooKeeperState {

	Logger logger = LogManager.getLogger(ZooKeeperState.class.getName());

	private final String host;
	private final int port;

	private int minLatency = -1, avgLatency = -1, maxLatency = -1;
	private long received = -1;
	private long sent = -1;
	private int outStanding = -1;
	private long zxid = -1;
	private String mode = null;
	private int nodeCount = -1;
	private int totalWatches = -1;
	private int clientNumber = -1;

	public ZooKeeperState(String connectionString) {
		String host = connectionString.substring(0,
				connectionString.indexOf(':'));
		int port = Integer.parseInt(connectionString.substring(connectionString
				.indexOf(':') + 1));
		this.host = host;
		this.port = port;
	}

	public void update() {
		String statText = cmd("srvr");
		if (!"".equals(statText)) {
			Scanner scannerForStat = new Scanner(statText);
			while (scannerForStat.hasNext()) {
				String line = scannerForStat.nextLine();
				if (line.startsWith("Latency min/avg/max:")) {
					String[] latencys = getStringValueFromLine(line).split("/");
					minLatency = Integer.parseInt(latencys[0]);
					avgLatency = Integer.parseInt(latencys[1]);
					maxLatency = Integer.parseInt(latencys[2]);
				} else if (line.startsWith("Received:")) {
					received = Long.parseLong(getStringValueFromLine(line));
				} else if (line.startsWith("Sent:")) {
					sent = Long.parseLong(getStringValueFromLine(line));
				} else if (line.startsWith("Outstanding:")) {
					outStanding = Integer
							.parseInt(getStringValueFromLine(line));
				} else if (line.startsWith("Zxid:")) {
					zxid = Long.parseLong(getStringValueFromLine(line)
							.substring(2), 16);
				} else if (line.startsWith("Mode:")) {
					mode = getStringValueFromLine(line);
				} else if (line.startsWith("Node count:")) {
					nodeCount = Integer.parseInt(getStringValueFromLine(line));
				}
			}
			scannerForStat.close();
		}

		String wchsText = cmd("wchs");
		if (!"".equals(wchsText)) {
			Scanner scannerForWchs = new Scanner(wchsText);
			while (scannerForWchs.hasNext()) {
				String line = scannerForWchs.nextLine();
				if (line.startsWith("Total watches:")) {
					totalWatches = Integer
							.parseInt(getStringValueFromLine(line));
				}
			}
			scannerForWchs.close();
		}

		String consText = cmd("cons");
		if (!"".equals(consText)) {
			Scanner scannerForCons = new Scanner(consText);
			if (!"".equals(consText)) {
				clientNumber = 0;
			}
			while (scannerForCons.hasNext()) {
				@SuppressWarnings("unused")
				String line = scannerForCons.nextLine();
				++clientNumber;
			}
			scannerForCons.close();
		}
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public boolean ruok() {
		return "imok\n".equals(cmd("ruok"));
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

	public long getReceived() {
		return received;
	}

	public long getSent() {
		return sent;
	}

	public int getOutStanding() {
		return outStanding;
	}

	public long getZxid() {
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

	public int getClientNumber() {
		return clientNumber;
	}

	private String getStringValueFromLine(String line) {
		return line.substring(line.indexOf(":") + 1, line.length())
				.replaceAll(" ", "").trim();
	}

	private class SendThread extends Thread {
		private String cmd;

		public String ret = "";

		public SendThread(String cmd) {
			this.cmd = cmd;
		}

		@Override
		public void run() {
			try {
				ret = FourLetterWordMain.send4LetterWord(host, port, cmd);
			} catch (IOException e) {
				return;
			}
		}

	}

	private String cmd(String cmd) {
		final int waitTimeout = 5;
		SendThread sendThread = new SendThread(cmd);
		sendThread.start();
		try {
			sendThread.join(waitTimeout * 1000);
			return sendThread.ret;
		} catch (InterruptedException e) {
			logger.error("Send " + cmd + " to client " + host + ":" + port
					+ " failed!");
		}
		return "";
	}
}
