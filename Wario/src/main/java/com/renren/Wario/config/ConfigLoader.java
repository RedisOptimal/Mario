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
package com.renren.Wario.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigLoader {

	private static Logger logger = LogManager.getLogger(ConfigLoader.class
			.getName());

	// used for singleton
	private static ConfigLoader configLoader = null;

	// Configuration variable
	private final String configPathPrefix;
	private final String serverConfigFile = "server.json";
	private final String pluginConfigFile = "plugin.json";

	private String serverConfigText = null;
	private String pluginConfigText = null;

	private Map<String, JSONObject> serverObjects = null;
	private Map<String, JSONArray> pluginObjects = null;

	private ConfigLoader() {
		if (System.getProperty("default.config.path") == null) {
			configPathPrefix = "./conf/";
		} else {
			configPathPrefix = System.getProperty("default.config.path");
		}
		serverObjects = new HashMap<String, JSONObject>();
		pluginObjects = new HashMap<String, JSONArray>();
	}

	public static synchronized ConfigLoader getInstance() {
		if (configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}

	public void loadConfig() {
		Map<String, JSONObject> tmpServerObjects = new HashMap<String, JSONObject>();
		String zookeeperName = "";
		try {
			serverConfigText = FileCacheReader.read(configPathPrefix
					+ serverConfigFile);
			JSONObject jsonObject = new JSONObject(serverConfigText);
			Iterator<?> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				zookeeperName = (String) iterator.next();
				tmpServerObjects.put(zookeeperName,
						jsonObject.getJSONObject(zookeeperName));
			}
			serverObjects = tmpServerObjects;
		} catch (IOException e) {
			logger.error("Load server config : " + e.toString());
		} catch (JSONException e) {
			logger.error("Can't parsing cluster " + zookeeperName
					+ ", check the file format. " + e.toString());
		}

		Map<String, JSONArray> tmpPluginObjects = new HashMap<String, JSONArray>();
		String pluginName = "";
		try {
			pluginConfigText = FileCacheReader.read(configPathPrefix
					+ pluginConfigFile);
			JSONObject jsonObject = new JSONObject(pluginConfigText);
			Iterator<?> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				pluginName = (String) iterator.next();
				tmpPluginObjects.put(pluginName,
						jsonObject.getJSONArray(pluginName));
			}
			pluginObjects = tmpPluginObjects;
		} catch (IOException e) {
			logger.error("Load plugin config : " + e.toString());
		} catch (JSONException e) {
			logger.error("Can't parsing plugin " + pluginName
					+ ", check the file format. " + e.toString());
		}
	}

	/**
	 * @return the serverObjects
	 */
	public Map<String, JSONObject> getServerObjects() {
		return serverObjects;
	}

	/**
	 * @return the pluginObjects
	 */
	public Map<String, JSONArray> getPluginObjects() {
		return pluginObjects;
	}

}
