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

import org.apache.zookeeper.client.FourLetterWordMain;

public class ZooKeeperState {
	private String host = null;
	private int port;

	private int minLatency, avgLatency, maxLatency;
	private int received;
	private int sent;
	private int outStanding;
	private String zxid = null;
	private String mode = null;
	private int nodeCount;
	private int totalWatches;

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
		try {
			String statText = cmd("stat");
			Scanner scanner = new Scanner(statText);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
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
					outStanding = Integer
							.parseInt(getStringValueFromLine(line));
				} else if (line.startsWith("Zxid:")) {
					zxid = getStringValueFromLine(line);
				} else if (line.startsWith("Mode:")) {
					mode = getStringValueFromLine(line);
				} else if (line.startsWith("Node count:")) {
					nodeCount = Integer.parseInt(getStringValueFromLine(line));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String wchsText = cmd("wchs");
			Scanner scanner = new Scanner(wchsText);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.startsWith("Total watches:")) {
					totalWatches = Integer
							.parseInt(getStringValueFromLine(line));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	private String cmd(String cmd) throws IOException {
		return FourLetterWordMain.send4LetterWord(host, port, cmd);
	}
}
