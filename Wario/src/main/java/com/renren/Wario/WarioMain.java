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
	private Map<String, IPlugin> plugins = null;

	public WarioMain() {
		configLoader = new ConfigLoader();
		clusters = new HashMap<String, ZooKeeperCluster>();
		plugins = new HashMap<String, IPlugin>();
	}

	public void init() {
		configLoader.loadConfig();
		clusters.clear();
		updateServerConfig(configLoader.serverObjects);
		plugins.clear();
		updatePluginConfig(configLoader.pluginObjects);
	}

	@Override
	public void run() {
		while (true) {

			configLoader.loadConfig();
			updateServerConfig(configLoader.serverObjects);
			updatePluginConfig(configLoader.pluginObjects);

			work();

			try {
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void work() {
		Iterator<Entry<String, IPlugin>> it = plugins.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, IPlugin> entry = it.next();

			process(entry.getValue());
		}
	}
	
	private void process(IPlugin plugin) {
		Iterator<Entry<String, ZooKeeperClient>> it = plugin.cluster.clients.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<String, ZooKeeperClient> entry = it.next();
			
			plugin.setClient(entry.getValue());
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
				cluster.init();
				clusters.put(zookeeperName, cluster);
			} else {
				cluster = clusters.get(zookeeperName);
				cluster.updateClients(object);
			}
		}
	}

	private void updatePluginConfig(Map<String, JSONArray> pluginObjects) {
		IPlugin plugin = null;
		Iterator<Entry<String, JSONArray>> it = pluginObjects.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<String, JSONArray> entry = it.next();

			String pluginName = entry.getKey();
			JSONArray arrary = entry.getValue();
			
			for(int i = 0; i < arrary.length(); ++ i) {
				JSONObject object;
				try {
					object = arrary.getJSONObject(i);
					String messionName = object.getString("messionName");
					if (!plugins.containsKey(messionName)) {
						plugin = createPlugin(pluginName, object);
						plugins.put(messionName, plugin);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
				
		}
	}

	private IPlugin createPlugin(String pluginName, JSONObject object) {
		IPlugin plugin = null;
		try {
			String zookeeperName = object.getString("zookeeperName");
			String msgSenderName = object.getString("msgSender");
			String mailSenderName = object.getString("mailSender");

			plugin = (IPlugin) Class.forName(pluginPackage + pluginName)
					.newInstance();
			plugin.zookeeperName = zookeeperName;
			plugin.cluster = clusters.get(zookeeperName);
			plugin.msgSender = (IMsgSender) Class.forName(
					msgSenderPackage + msgSenderName).newInstance();
			plugin.mailSender = (IMailSender) Class.forName(
					mailSenderPackage + mailSenderName).newInstance();

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
