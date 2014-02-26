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

	Logger logger = LogManager.getLogger(ConfigLoader.class.getName());

	private final String configPathPrefix = "./conf/";
	private final String serverConfigFile = "server.json";
	private final String pluginConfigFile = "plugin.json";

	private String serverConfigText = null;
	private String pluginConfigText = null;

	public Map<String, JSONObject> serverObjects = null;
	public Map<String, JSONArray> pluginObjects = null;

	public ConfigLoader() {
		serverObjects = new HashMap<String, JSONObject>();
		pluginObjects = new HashMap<String, JSONArray>();
	}

	public void loadConfig() {
		try {
			serverConfigText = FileReader.read(configPathPrefix
					+ serverConfigFile);
			JSONObject jsonObject = new JSONObject(serverConfigText);
			Iterator<?> iterator = jsonObject.keys();
			serverObjects.clear();
			while (iterator.hasNext()) {
				String zookeeperName = (String) iterator.next();
				serverObjects.put(zookeeperName,
						jsonObject.getJSONObject(zookeeperName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			logger.error("Can't parsing " + serverConfigFile
					+ ", check the file format.");
			e.printStackTrace();
		}

		try {
			pluginConfigText = FileReader.read(configPathPrefix
					+ pluginConfigFile);
			JSONObject jsonObject = new JSONObject(pluginConfigText);
			Iterator<?> iterator = jsonObject.keys();
			pluginObjects.clear();
			while (iterator.hasNext()) {
				String pluginName = (String) iterator.next();
				pluginObjects.put(pluginName,
						jsonObject.getJSONArray(pluginName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			logger.error("Can't parsing " + pluginConfigFile
					+ ", check the file format.");
			e.printStackTrace();
		}
	}

}
