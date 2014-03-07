package com.renren.Wario.plugin;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;

public class ReadWriteTestPlugin extends IPlugin {

	private static Logger logger = Logger.getLogger(ReadWriteTestPlugin.class);

	private final String number = "";
	private final String address = "";

	private static String path = "/test";
	private static String INITIAL = "I'm the initial data.";
	private static String UPDATED = "I'm the updated data.";

	@Override
	public void run() {
		logger.info("ReadWriteTestPlugin runs at client. "
				+ client.getConnectionString());
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
			mailSender.sendMail(address,
					"Client can not be used. " + client.getConnectionString());
			msgSender.sendMessage(number,
					"Client can not be used. " + client.getConnectionString());
		}
	}
}
