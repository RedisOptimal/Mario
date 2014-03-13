package com.renren.Wario.plugin;

import com.renren.Wario.plugin.IPlugin;

public class SamplePlugin extends IPlugin {

	private final String number = "13888888888";
	private final String address = "test@renren-inc.com";
	
	public void run() {
		
		if(!client.isAvailable()) {
			msgSender.sendMessage(number, "Client " + client.getConnectionString() + "is not ok!");
			mailSender.sendMail(address, "Client " + client.getConnectionString() + "is not ok!");
		}
	}

}
