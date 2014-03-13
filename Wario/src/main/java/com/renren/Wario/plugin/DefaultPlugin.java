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
	private final int testTime = 5;
	
	private String mode;

	@Override
	public void run() {
		readContext();

		String message = "";

		if (!client.isAvailable() || !client.state.ruok()) {
				message += "Client " + client.getConnectionString() + " is down!\n";
		}

		client.state.update();
		String newMode = client.state.getMode();
		if (mode != null && !mode.equals(newMode)) {
			mode = newMode;
			message += "Client " + client.getConnectionString() + " has changed mode to " + newMode + ".\n";
		}

		int outStanding = 0;
		for(int i = 0; i < testTime; ++ i) {
			client.state.update();
			outStanding += client.state.getOutStanding();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (outStanding > maxOutStanding * testTime) {
			message += "Client " + client.getConnectionString() + " exceed max outstanding.\n";
		}

		numbers = args[0].split(",");
		addresses = args[1].split(",");
		if(!"".equals(message)) {
			for(String number : numbers) {
				msgSender.sendMessage(number, message);
			}
			for(String address : addresses) {
				mailSender.sendMail(address, message);
			}
		}
		
		writeContext();
	}

	/**
	 * clusterContext: 
	 * connectionString1#Mode 
	 * connectionString2#Mode
	 * connectionString3#Mode
	 */
	private void readContext() {
		String text = new String(clusterContext);
		boolean exists = false;
		Scanner scanner = new Scanner(text);
		while (scanner.hasNext()) {
			String line = scanner.next();
			if (line.startsWith(client.getConnectionString())) {
				exists = true;
				String[] args = line.split("#");
				mode = args[1];
			}
		}
		scanner.close();

		if (!exists) {
			mode = client.state.getMode();
			String newLine = client.getConnectionString() + "#" + mode + "\n";
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
				String newLine = client.getConnectionString() + "#" + mode + "\n";
				res += newLine;
			} else {
				res += line;
			}
		}
		scanner.close();
		clusterContext = res.getBytes();
	}
}
