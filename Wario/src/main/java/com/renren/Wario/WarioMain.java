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
package com.renren.Wario;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.renren.Wario.config.ConfigLoader;
import com.renren.Wario.mailsender.IMailSender;
import com.renren.Wario.msgsender.IMsgSender;
import com.renren.Wario.plugin.IPlugin;
import com.renren.Wario.zookeeper.ZooKeeperClient;
import com.renren.Wario.zookeeper.ZooKeeperCluster;

public class WarioMain extends Thread {

	private static Logger logger = LogManager.getLogger(WarioMain.class
			.getName());

	private final String pluginPackage = "com.renren.Wario.plugin.";
	private final String msgSenderPackage = "com.renren.Wario.msgsender.";
	private final String mailSenderPackage = "com.renren.Wario.mailsender.";

	private ConfigLoader configLoader = null;
	private Map<String, ZooKeeperCluster> clusters = null;

	public WarioMain() {
		configLoader = ConfigLoader.getInstance();
		clusters = new HashMap<String, ZooKeeperCluster>();
	}

	public void init() {
		configLoader.loadConfig();
		clusters.clear();
		updateServerConfig(configLoader.getServerObjects());
	}

	@Override
	public void run() {
		while (true) {

			configLoader.loadConfig();
			updateServerConfig(configLoader.getServerObjects());

			try {
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Iterator<Entry<String, JSONArray>> it = configLoader.getPluginObjects().entrySet()
					.iterator();

			while (it.hasNext()) {
				Map.Entry<String, JSONArray> entry = it.next();

				String pluginName = entry.getKey();
				JSONArray arrary = entry.getValue();

				for (int i = 0; i < arrary.length(); ++i) {
					JSONObject object;
					try {
						object = arrary.getJSONObject(i);		
						processPlugin(pluginName, object);						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void processPlugin(String pluginName, JSONObject object) throws JSONException {
		String zooKeeperName = object.getString("ZooKeeperName");

		Iterator<Entry<String, ZooKeeperClient>> it = clusters.get(zooKeeperName)
				.getClients().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, ZooKeeperClient> entry = it.next();

			IPlugin plugin = createPlugin(pluginName, object, entry.getValue());
			plugin.run();
		}
	}

	private void updateServerConfig(Map<String, JSONObject> serverObjects) {
		ZooKeeperCluster cluster = null;
		Iterator<Entry<String, JSONObject>> it = serverObjects.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<String, JSONObject> entry = (Map.Entry<String, JSONObject>) it
					.next();

			String zookeeperName = entry.getKey();
			JSONObject object = entry.getValue();

			if (!clusters.containsKey(zookeeperName)) {
				cluster = new ZooKeeperCluster(zookeeperName, object);
				try {
					cluster.init();
				} catch (JSONException e) {
					logger.error("Read JSON object failed.\n"
							+ object.toString() + "\n" + e.toString());
				}
				clusters.put(zookeeperName, cluster);
			} else {
				cluster = clusters.get(zookeeperName);
				try {
					cluster.updateClients(object);
				} catch (JSONException e) {
					logger.error("Update failed " + zookeeperName + ".");
				}
			}
		}
	}

	private IPlugin createPlugin(String pluginName, JSONObject object, ZooKeeperClient client) {
		IPlugin plugin = null;
		try {
			String msgSenderName = object.getString("MsgSender");
			String mailSenderName = object.getString("MailSender");

			plugin = (IPlugin) Class.forName(pluginPackage + pluginName)
					.newInstance();
			plugin.msgSender = (IMsgSender) Class.forName(
					msgSenderPackage + msgSenderName).newInstance();
			plugin.mailSender = (IMailSender) Class.forName(
					mailSenderPackage + mailSenderName).newInstance();
			plugin.client = client;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return plugin;
	}
}
