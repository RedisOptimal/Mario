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

import com.renren.Wario.config.ApplicationProperties;
import com.renren.Wario.config.ConfigLoader;
import com.renren.Wario.mailsender.IMailSender;
import com.renren.Wario.msgsender.IMsgSender;
import com.renren.Wario.plugin.IPlugin;
import com.renren.Wario.zookeeper.ZooKeeperClient;
import com.renren.Wario.zookeeper.ZooKeeperCluster;

public class WarioMain extends Thread {
	// TODO BUG:当集群和插件删除时应该删除context信息
	private static Logger logger = LogManager.getLogger(WarioMain.class
			.getName());

	private final String pluginPackage = "com.renren.Wario.plugin.";
	private final String msgSenderPackage = "com.renren.Wario.msgsender.";
	private final String mailSenderPackage = "com.renren.Wario.mailsender.";
	private final String pluginPathPrefix;

	private ConfigLoader configLoader = null;
	// <zkId, cluster>
	private Map<Integer, ZooKeeperCluster> clusters = null;
	// <pluginName, <zkId, context> >
	private final Map<String, Map<Integer, byte[]>> contexts;

	private static int SLEEPSECOND;
	
	public WarioMain() {
		if (System.getProperty("default.plugin.path") == null) {
			pluginPathPrefix = "./plugins/";
		} else {
			pluginPathPrefix = System.getProperty("default.plugin.path");
		}
		configLoader = ConfigLoader.getInstance();
		clusters = new HashMap<Integer, ZooKeeperCluster>();
		contexts = new TreeMap<String, Map<Integer, byte[]>>();
		SLEEPSECOND = ApplicationProperties.getDefaultSleep();
	}

	public void init() {
		configLoader.loadConfig();
		clusters.clear();
		updateServerConfig(configLoader.getServerObjects());
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			long start = System.currentTimeMillis();
			
			configLoader.loadConfig();
			Map<Integer, JSONObject> serverObjects = configLoader.getServerObjects();
			Map<String, JSONArray> pluginObjects = configLoader.getPluginObjects();
			
			removeUselessClusters(serverObjects);
			
			removeUselessContexts(pluginObjects);
			
			updateServerConfig(serverObjects);

			runPlugins(pluginObjects);
			
			do {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					
				}
			} while(System.currentTimeMillis() - start < SLEEPSECOND * 1000);
		}
	}
	
	private void removeUselessClusters(Map<Integer, JSONObject> serverObjects) {
		Set<Integer> uselessClusters = WarioUtilTools
				.getDifferenceByInteger(clusters.keySet(), serverObjects.keySet());
		Iterator<Integer> it = uselessClusters.iterator();
		while (it.hasNext()) {
			int zkId = it.next();
			ZooKeeperCluster cluster = clusters.get(zkId);
			cluster.close();
			clusters.remove(zkId);
		}
	}
	
	private void removeUselessContexts(Map<String, JSONArray> pluginObjects) {
		Set<String> uselessContexts = WarioUtilTools
				.getDifference(contexts.keySet(), pluginObjects.keySet());
		contexts.entrySet().removeAll(uselessContexts);

	}

	private void updateServerConfig(Map<Integer, JSONObject> serverObjects) {
		ZooKeeperCluster cluster = null;

		Iterator<Entry<Integer, JSONObject>> it = serverObjects.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, JSONObject> entry = (Map.Entry<Integer, JSONObject>) it
					.next();

			Integer zkId = entry.getKey();
			JSONObject object = entry.getValue();

			if (!clusters.containsKey(zkId)) {
				cluster = new ZooKeeperCluster(zkId, object);
				try {
					cluster.init();
				} catch (JSONException e) {
					logger.error(zkId + " init failed! " + e.toString());
				}
				clusters.put(zkId, cluster);
			} else {
				cluster = clusters.get(zkId);
				try {
					cluster.updateClients(object);
				} catch (JSONException e) {
					logger.error(cluster.getZkId() + " update failed! "
							+ e.toString());
				}
			}
		}
	}

	/**
	 * Run every plugin.
	 */
	private void runPlugins(Map<String, JSONArray> pluginObjects) {
		JSONArray observerPluginArray = null;
		JSONArray rulePluginArray = null;
		Iterator<Entry<String, JSONArray>> it = pluginObjects.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, JSONArray> entry = it.next();

			String pluginName = entry.getKey();
			JSONArray array = entry.getValue();
			
			if (!contexts.containsKey(pluginName)) {
				contexts.put(pluginName, new TreeMap<Integer, byte[]>());
			}
			
			if ("ObserverPlugin".equals(pluginName)) {
				observerPluginArray = array;
			} else if ("RulePlugin".equals(pluginName)) {
				rulePluginArray = array;
			} else {
				runPluginOnClusters(pluginName, array);
			}
		}

		// Make sure RulePlugin runs before ObserverPlugin
		if(rulePluginArray != null) {
			runPluginOnClusters("RulePlugin", rulePluginArray);
		}
		if(observerPluginArray != null) {
			runPluginOnClusters("ObserverPlugin", observerPluginArray);
		} 
	}
	
	/**
	 * Run plugin on every cluster.
	 * @param pluginName
	 * @param clusters
	 */
	private void runPluginOnClusters(String pluginName, JSONArray clusters) {
		for (int i = 0; i < clusters.length(); ++i) {
			try {
				JSONObject object = clusters.getJSONObject(i);
				if ("ObserverPlugin".equals(pluginName)) {
					processPluginOnObserver(pluginName, object);
				} else if ("RulePlugin".equals(pluginName)) {
					processPluginOnObserver(pluginName, object);
				} else {
					processPlugin(pluginName, object);
				}
			} catch (JSONException e) {
				logger.error("Failed to process json string : "
						+ pluginName + " " + i + "th line. " + e.toString());
			}
		}
	}

	/**
	 * Process the plugin on every client of the cluster except the observer.
	 * @param pluginName
	 * @param object
	 * @throws JSONException
	 */
	private void processPlugin(String pluginName, JSONObject object)
			throws JSONException {
		int zkId = object.getInt("zkId");
		ZooKeeperCluster cluster = null;
		if (clusters.containsKey(zkId)) {
			cluster = clusters.get(zkId);
		} else {
			logger.error("Wrong zkId! " + zkId);
			return;
		}
		if (!contexts.get(pluginName).containsKey(zkId)) {
			contexts.get(pluginName).put(zkId, new byte[1 << 20]); // 1M
		}

		Iterator<Entry<String, ZooKeeperClient>> it = cluster.getClients()
				.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, ZooKeeperClient> entry = it.next();
			ZooKeeperClient client = entry.getValue();
			byte[] context = contexts.get(pluginName).get(zkId);
			try {
				IPlugin plugin = createPlugin(pluginName, object, client,
						context);
				plugin.run();
				logger.info(pluginName + " runs at "
						+ client.getConnectionString() + " successfully!");
			} catch (Exception e) {
				logger.error(pluginName + " runs at "
						+ client.getConnectionString() + " failed! "
						+ e.toString());
			}
		}
	}

	/**
	 * Process plugin on the observer client.
	 * @param pluginName
	 * @param object
	 * @throws JSONException
	 */
	private void processPluginOnObserver(String pluginName, JSONObject object)
			throws JSONException {
		int zkId = object.getInt("zkId");
		ZooKeeperCluster cluster = null;
		if (clusters.containsKey(zkId)) {
			cluster = clusters.get(zkId);
		} else {
			logger.error("Wrong zkId! " + zkId);
			return;
		}
		if (!contexts.get(pluginName).containsKey(zkId)) {
			contexts.get(pluginName).put(zkId, new byte[1 << 20]); // 1M
		}

		ZooKeeperClient client = cluster.getObserverClient();
		if (client != null) {
			byte[] context = contexts.get(pluginName).get(zkId);
			try {
				IPlugin plugin = createPlugin(pluginName, object, client, context);
				Thread pluginThread = new Thread(plugin);
				pluginThread.start();
				try {
                    pluginThread.join(150 * 1000);
                    if (pluginThread.isAlive()) {
                        pluginThread.interrupt();
                        logger.error(pluginName + " have run out of times. " + 
                                   "It may be hang and must be killed.");
                    }
                } catch (InterruptedException e) {
                }
				logger.info(pluginName + " runs at " + client.getConnectionString()
						+ " successfully!");
			} catch (Exception e) {
				logger.error(pluginName + " runs at "
						+ client.getConnectionString() + " failed! " + e.toString());
			}
		} else {
			logger.warn(pluginName + " must run on observer, please config observer address.");
		}
	}

	private IPlugin createPlugin(String pluginName, JSONObject object,
			ZooKeeperClient client, byte[] context)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, MalformedURLException, JSONException {
		IPlugin plugin = null;
		String msgSenderName = object.getString("msgSender");
		String mailSenderName = object.getString("mailSender");
		String phoneNumber = object.getString("phoneNumber");
		String emailAddress = object.getString("emailAddress");
		String args = object.getString("args");

		URL pluginUrl = new File(pluginPathPrefix + File.separator + pluginName
				+ ".jar").toURI().toURL();
		URL msgSenderUrl = new File(pluginPathPrefix + File.separator
				+ msgSenderName + ".jar").toURI().toURL();
		URL mailSenderUrl = new File(pluginPathPrefix + File.separator
				+ mailSenderName + ".jar").toURI().toURL();
		URL[] urls = new URL[] { pluginUrl, msgSenderUrl, mailSenderUrl };

		ClassLoader classLoader = new URLClassLoader(urls);
		plugin = (IPlugin) classLoader.loadClass(pluginPackage + pluginName)
				.newInstance();
		plugin.msgSender = (IMsgSender) classLoader.loadClass(
				msgSenderPackage + msgSenderName).newInstance();
		plugin.mailSender = (IMailSender) classLoader.loadClass(
				mailSenderPackage + mailSenderName).newInstance();
		plugin.numbers = phoneNumber.split(",");
		plugin.addresses = emailAddress.split(",");
		plugin.args = args.split(",");
		plugin.client = client;
		plugin.clusterContext = context;
		return plugin;
	}
}
