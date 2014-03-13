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
		
		if(!client.isAvailable()) {
			return ;
		}
		
		boolean canBeUsed = true;
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
		} catch (InterruptedException e) {
			canBeUsed = false;
		}
		
		if (!canBeUsed) {
			numbers = args[0].split(",");
			addresses = args[1].split(",");
			
			for (String address : addresses) {
				mailSender.sendMail(address, "Client can not be used. "
						+ client.getConnectionString());
			}
			for (String number : numbers) {
				msgSender.sendMessage(number, "Client can not be used. "
						+ client.getConnectionString());
			}
		}
	}
}
