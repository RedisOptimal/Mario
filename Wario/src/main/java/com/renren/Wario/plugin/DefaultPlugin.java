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

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DefaultPlugin extends IPlugin {
	private static Logger logger = LogManager.getLogger(DefaultPlugin.class
			.getName());
	private final int maxOutStanding = 30;

	private String mode;
	private int outStanding;

	@Override
	public void run() {
		logger.info("Get context : " + new String(clusterContext).trim());
		try {
			readContext();
		} catch (UnsupportedEncodingException e) {
			logger.error("Error when decoder ", e);
		}

		String message = "";

		if (!client.state.ruok()) {
			message += "ZooKeeper " + client.getConnectionString()
					+ " is down!\n";
			logger.error(message);
		}

		client.state.update();
		String newMode = client.state.getMode();
		if (newMode == null || "".equals(newMode.trim())) {
			message += "Can't get zookeeper " + client.getConnectionString()
					+ "'s status. Maybe down!!\n";
			logger.error(message);
		} else if (mode != null && !"".equals(mode) && !mode.equals(newMode)) {
			message += "ZooKeeper " + client.getConnectionString()
					+ " has changed mode from " + mode + " to " + newMode
					+ ".\n";
			mode = newMode;
			logger.error(message);
		} else if ("".equals(mode)) {
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
			for (String number : numbers) {
				msgSender.sendMessage(number.trim(), message);
			}
			for (String address : addresses) {
				mailSender.sendMail(address.trim(), message);
			}
		}

		try {
			writeContext();
		} catch (UnsupportedEncodingException e) {
			logger.error("Error when encoder ", e);
		}
		logger.info("Set context : " + new String(clusterContext).trim());
	}

	/**
	 * clusterContext: [ connectionString1#Mode#OutStanding\n
	 * connectionString2#Mode#OutStanding\n ]
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void readContext() throws UnsupportedEncodingException {
		String context = new String(clusterContext, "UTF-8").trim();
		if (!context.startsWith("[") || !context.endsWith("]")) {
			logger.info("Init " + client.getConnectionString()
					+ "'s context once here.");
			System.arraycopy("[]".getBytes(), 0, clusterContext, 0,
					"[]".length());
			context = new String(clusterContext, "UTF-8").trim();
		}
		boolean exist = false;
		Scanner scanner = new Scanner(
				context.substring(1, context.length() - 1));
		scanner.useDelimiter("\n");
		while (scanner.hasNext()) {
			String line = scanner.next();
			if (line.startsWith(client.getConnectionString())) {
				exist = true;
				String[] contexts = line.split("#");
				mode = contexts[1];
				outStanding = Integer.parseInt(contexts[2]);
			}
		}
		scanner.close();

		if (!exist) {
			mode = "";
			outStanding = 0;
			String newItem = client.getConnectionString() + "#" + mode + "#"
					+ outStanding + "\n";
			context = context.substring(0, context.length() - 1) + newItem
					+ "]";
			System.arraycopy(context.getBytes(), 0, clusterContext, 0,
					context.getBytes().length);
		}
	}

	private void writeContext() throws UnsupportedEncodingException {
		String newContext = "[";
		String context = new String(clusterContext, "UTF-8").trim();
		if (!context.startsWith("[") || !context.endsWith("]")) {
			logger.error("Decode on " + client.getConnectionString());
			return;
		}
		Scanner scanner = new Scanner(
				context.substring(1, context.length() - 1));
		while (scanner.hasNext()) {
			String line = scanner.next();
			if (line.startsWith(client.getConnectionString())) {
				String newLine = client.getConnectionString() + "#" + mode
						+ "#" + outStanding;
				newContext += newLine + "\n";
			} else {
				newContext += line + "\n";
			}
		}
		scanner.close();
		newContext += "]";
		System.arraycopy(newContext.getBytes(), 0, clusterContext, 0,
				newContext.getBytes().length);
	}
}
