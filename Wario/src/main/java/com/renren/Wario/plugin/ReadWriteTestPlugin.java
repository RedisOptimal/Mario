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
		boolean canBeUsed = true;
		String message = "";
		if(!client.isAvailable()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!client.isAvailable()) {
				canBeUsed = false;
				message += "Client can not establish connection with " +
							client.getConnectionString() + ". \n";
			}
		}
		
		try {
			if (client.testExists(path) == null) {
				client.testCreate(path, INITIAL.getBytes());
			}
			canBeUsed = INITIAL.equals(new String(client.testGetData(path)));
			client.testSetData(path, UPDATED.getBytes());
			canBeUsed = UPDATED.equals(new String(client.testGetData(path)));
			client.testDdelete(path);
		} catch (KeeperException e) {
			canBeUsed = false;
			message += "ZooKeeper " + client.getConnectionString() 
					+ " can not be used.";
		} catch (InterruptedException e) {
		}
		
		if (!canBeUsed) {
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
