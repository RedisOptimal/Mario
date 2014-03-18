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
package com.renren.Wario.plugin;

import java.util.Scanner;

public class DefaultPlugin extends IPlugin {

	private String[] numbers;
	private String[] addresses;
	
	private final int maxOutStanding = 30;
	
	private String mode;
	private int outStanding;

	@Override
	public void run() {
		readContext();

		String message = "";

		if (!client.state.ruok()) {
			message += "ZooKeeper " + client.getConnectionString()
					+ " is down!\n";
		}

		client.state.update();
		String newMode = client.state.getMode();
		if (mode != null && !mode.equals(newMode)) {
			message += "ZooKeeper " + client.getConnectionString()
					+ " has changed mode from " + mode + " to " + newMode
					+ ".\n";
			mode = newMode;
		}

		int newOutStanding = client.state.getOutStanding();
		if (outStanding > maxOutStanding && newOutStanding > maxOutStanding) {
			message += "Client " + client.getConnectionString()
					+ " exceed max outstanding. Max outstanding is "
					+ maxOutStanding + ", but now is " + newOutStanding
					+ " and last time is " + outStanding + ".\n";
		}
		outStanding = newOutStanding;

		if (!"".equals(message)) {
			numbers = args[0].split(",");
			addresses = args[1].split(",");
			for (String number : numbers) {
				msgSender.sendMessage(number, message);
			}
			for (String address : addresses) {
				mailSender.sendMail(address, message);
			}
		}

		writeContext();
	}

	/**
	 * clusterContext: 
	 * connectionString1#Mode#OutStanding 
	 * connectionString2#Mode#OutStanding
	 * connectionString3#Mode#OutStanding
	 */
	private void readContext() {
		String text = new String(clusterContext);
		boolean exists = false;
		Scanner scanner = new Scanner(text);
		while (scanner.hasNext()) {
			String line = scanner.next();
			if (line.startsWith(client.getConnectionString())) {
				exists = true;
				String[] contexts = line.split("#");
				mode = contexts[1];
				outStanding = Integer.parseInt(contexts[2]);
			}
		}
		scanner.close();

		if (!exists) {
			mode = client.state.getMode();
			outStanding = client.state.getOutStanding();
			String newLine = client.getConnectionString() + "#" + mode + "#"
					+ outStanding + "\n";
			text += newLine;
			clusterContext = text.getBytes();
		}
	}

	private void writeContext() {
		String res = "";
		String text = new String(clusterContext);
		Scanner scanner = new Scanner(text);
		while (scanner.hasNext()) {
			String line = scanner.next();
			if (line.startsWith(client.getConnectionString())) {
				String newLine = client.getConnectionString() + "#" + mode
						+ "#" + outStanding + "\n";
				res += newLine;
			} else {
				res += line;
			}
		}
		scanner.close();
		clusterContext = res.getBytes();
	}
}
