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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

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

	// <pluginName, <ZooKeeperName, context> >
	private final Map<String, Map<String, byte[]>> clusterContext;

	private final String pluginPackage = "com.renren.Wario.plugin.";
	private final String msgSenderPackage = "com.renren.Wario.msgsender.";
	private final String mailSenderPackage = "com.renren.Wario.mailsender.";
	private final String pluginPathPrefix;

	private ConfigLoader configLoader = null;
	private Map<String, ZooKeeperCluster> clusters = null;

	public WarioMain() {
		if (System.getProperty("default.plugin.path") == null) {
			pluginPathPrefix = "./plugins/";
		} else {
			pluginPathPrefix = System.getProperty("default.plugin.path");
		}
		clusterContext = new TreeMap<String, Map<String, byte[]>>();
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
		while (!isInterrupted()) {

			configLoader.loadConfig();
			updateServerConfig(configLoader.getServerObjects());

			try {
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Iterator<Entry<String, JSONArray>> it = configLoader
					.getPluginObjects().entrySet().iterator();
			// 删除配置中没有的数据结构
			Set<String> differenceSet = new HashSet<String>();
			differenceSet.addAll(clusterContext.keySet());
			differenceSet.removeAll(configLoader.getPluginObjects().keySet());
			clusterContext.entrySet().removeAll(differenceSet);

			while (it.hasNext()) {
				Map.Entry<String, JSONArray> entry = it.next();

				String pluginName = entry.getKey();
				JSONArray arrary = entry.getValue();
				if (!clusterContext.containsKey(pluginName)) {
					clusterContext.put(pluginName,
							new TreeMap<String, byte[]>());
				}

				for (int i = 0; i < arrary.length(); ++i) {
					JSONObject object;
					try {
						object = arrary.getJSONObject(i);
						processPlugin(pluginName, object);
					} catch (JSONException e) {
						logger.error("Failed to process json string : "
								+ pluginName + " " + i + "th lines.");
					}
				}
			}
		}
	}

	private void processPlugin(String pluginName, JSONObject object)
			throws JSONException {
		String zooKeeperName = object.getString("ZooKeeperName");
		ZooKeeperCluster cluster = null;
		if (clusters.containsKey(zooKeeperName)) {
			cluster = clusters.get(zooKeeperName);
		} else {
			logger.error("Wrong zooKeeperName! " + zooKeeperName);
			return;
		}
		Iterator<Entry<String, ZooKeeperClient>> it = cluster.getClients()
				.entrySet().iterator();

		if (!clusterContext.get(pluginName).containsKey(zooKeeperName)) {
			clusterContext.get(pluginName).put(zooKeeperName, new byte[1024]);
		}

		while (it.hasNext()) {
			Map.Entry<String, ZooKeeperClient> entry = it.next();
			ZooKeeperClient client = entry.getValue();
			IPlugin plugin = createPlugin(pluginName, object, client,
					clusterContext.get(pluginName).get(zooKeeperName));
			try {
				plugin.run();
				logger.info(pluginName + " runs at "
						+ client.getConnectionString() + " successfully!");
			} catch (Exception e) {
				logger.info(pluginName + " runs at "
						+ client.getConnectionString() + " failed! "
						+ e.toString());
			}
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
					logger.error(zookeeperName + " init failed! "
							+ e.toString());
				}
				clusters.put(zookeeperName, cluster);
			} else {
				cluster = clusters.get(zookeeperName);
				try {
					cluster.updateClients(object);
				} catch (JSONException e) {
					logger.error(zookeeperName + " update failed! "
							+ e.toString());
				}
			}
		}
	}

	private IPlugin createPlugin(String pluginName, JSONObject object,
			ZooKeeperClient client, byte[] context) {
		IPlugin plugin = null;
		boolean success = false;
		try {
			String msgSenderName = object.getString("MsgSender");
			String mailSenderName = object.getString("MailSender");
			JSONArray array = object.getJSONArray("args");
			File file = new File(pluginPathPrefix);
			URL url = file.toURI().toURL();
			URL[] urls = new URL[] { url };
			ClassLoader classLoader = new URLClassLoader(urls);
			plugin = (IPlugin) classLoader
					.loadClass(pluginPackage + pluginName).newInstance();
			plugin.msgSender = (IMsgSender) Class.forName(
					msgSenderPackage + msgSenderName).newInstance();
			plugin.mailSender = (IMailSender) Class.forName(
					mailSenderPackage + mailSenderName).newInstance();
			plugin.client = client;
			plugin.clusterContext = context;

			ArrayList<String> args = new ArrayList<String>();
			args.clear();
			for (int i = 0; i < array.length(); i++) {
				args.add(array.getString(i));
			}
			plugin.args = new String[array.length()];
			plugin.args = args.toArray(plugin.args);
			success = true;
		} catch (InstantiationException e) {
			logger.error(pluginName + " create failed! " + e.toString());
		} catch (IllegalAccessException e) {
			logger.error(pluginName + " create failed! " + e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(pluginName + " create failed! " + e.toString());
		} catch (JSONException e) {
			logger.error(pluginName + " create failed! " + e.toString());
		} catch (Exception e) {
			logger.error(pluginName + " create failed! " + e.toString());
		}
		if (success) {
			return plugin;
		} else {
			return null;
		}
	}
}
