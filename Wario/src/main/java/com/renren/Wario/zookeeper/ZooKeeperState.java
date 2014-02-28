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

	private String mode = null;

	public ZooKeeperState(String connectionString) {
		String host = connectionString.substring(0,
				connectionString.indexOf(':'));
		int port = Integer.parseInt(connectionString.substring(connectionString
				.indexOf(':') + 1));
		this.host = host;
		this.port = port;
		mode = updateMode();
	}

	public boolean isModeChanged() {
		boolean res = false;
		String newMode = updateMode();
		if(newMode != null && !newMode.equals(mode)) {
			res = true;
			System.err.println(host + ":" + port + " : " + mode + " -> " + newMode);
		}
		mode = newMode;
		return res;
	}

	public String getMode() {
		return mode;
	}

	private String updateMode() {
		String mode = null;
		try {
			String statText = cmd("stat");

			Scanner scanner = new Scanner(statText);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.startsWith("Mode:")) {
					mode = line.substring(line.indexOf(":") + 1, line.length())
							.replaceAll(" ", "");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mode;
	}

	private String cmd(String cmd) throws IOException {
		return FourLetterWordMain.send4LetterWord(host, port, cmd);
	}
}
