package com.renren.Wario.plugin;

import org.apache.zookeeper.KeeperException;

public class ReadWriteTestPlugin extends IPlugin {

	private String[] numbers;
	private String[] addresses;

	private static String path = "/test";
	private static String INITIAL = "I'm the initial data.";
	private static String UPDATED = "I'm the updated data.";

	@Override
	public void run() {
		String message = "";
		if(!client.isAvailable()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (!client.isAvailable()) {
			message += "Client can not establish connection with "
					+ client.getConnectionString() + ". \n";
		} else {
			try {
				if (client.testExists(path) == null) {
					client.testCreate(path, INITIAL.getBytes());
				}
				if (!INITIAL.equals(new String(client.testGetData(path)))) {
					message += "ZooKeeper: " + client.getConnectionString()
							+ "\nRead error.\n";
				}
				client.testSetData(path, UPDATED.getBytes());
				if (!UPDATED.equals(new String(client.testGetData(path)))) {
					message += "ZooKeeper: " + client.getConnectionString()
							+ "\nRead error.\n";
				}
				client.testDdelete(path);
			} catch (KeeperException e) {
				message += "ZooKeeper: " + client.getConnectionString() + "\n"
						+ e.toString();
			} catch (InterruptedException e) {
				message += "ZooKeeper: " + client.getConnectionString() + "\n"
						+ e.toString();
			}
		}		
		
		if (!"".equals(message)) {
			numbers = args[0].split(",");
			addresses = args[1].split(",");

			for (String address : addresses) {
				mailSender.sendMail(address, message);
			}
			for (String number : numbers) {
				msgSender.sendMessage(number, message);
			}
		}
	}
}
