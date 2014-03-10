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

		ZooKeeperCluster cluster = clusters.get(zooKeeperName);
		Iterator<Entry<String, ZooKeeperClient>> it = cluster.getClients()
				.entrySet().iterator();

		if (!clusterContext.get(pluginName).containsKey(zooKeeperName)) {
			clusterContext.get(pluginName).put(zooKeeperName, new byte[1024]);
		}

		while (it.hasNext()) {
			Map.Entry<String, ZooKeeperClient> entry = it.next();

			IPlugin plugin = createPlugin(pluginName, object, entry.getValue(),
					clusterContext.get(pluginName).get(zooKeeperName));
			if (plugin != null) {
				try {
					plugin.run();
					logger.info("Call " + plugin.getClass().getName()
							+ " plugin run on "
							+ entry.getValue().getConnectionString()
							+ " was successful.");
				} catch (Throwable e) {
					logger.error("Call " + plugin.getClass().getName()
							+ " plugin run method : " + e.toString());
				}
			} else {
				logger.error("Construction plugin " + pluginName + " failed.");
				break;
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

	// TODO 异常的log处理和classloader
	private IPlugin createPlugin(String pluginName, JSONObject object,
			ZooKeeperClient client, byte[] context) {
		IPlugin plugin = null;
		try {
			String msgSenderName = object.getString("MsgSender");
			String mailSenderName = object.getString("MailSender");

			URL pluginUrl = null, msgSenderUrl = null, mailSenderUrl = null;
			ClassLoader classLoader = null;
			try {
				pluginUrl = new File(pluginPathPrefix + File.separator
						+ pluginName + ".jar").toURI().toURL();
				msgSenderUrl = new File(pluginPathPrefix + File.separator
						+ msgSenderName + ".jar").toURI().toURL();
				mailSenderUrl = new File(pluginPathPrefix + File.separator
						+ mailSenderName + ".jar").toURI().toURL();
				URL[] urls = new URL[]{pluginUrl, msgSenderUrl, mailSenderUrl};
				classLoader = new URLClassLoader(urls);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			plugin = (IPlugin) classLoader
					.loadClass(pluginPackage + pluginName).newInstance();
			plugin.msgSender = (IMsgSender) classLoader.loadClass(
					msgSenderPackage + msgSenderName).newInstance();
			plugin.mailSender = (IMailSender) classLoader.loadClass(
					mailSenderPackage + mailSenderName).newInstance();
			plugin.client = client;
			plugin.clusterContext = context;
		} catch (InstantiationException e) {
			// TODO log
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
