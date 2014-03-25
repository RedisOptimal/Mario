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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.renren.Wario.db.MySQLHelper;

public class ConfigLoader {

	private static Logger logger = LogManager.getLogger(ConfigLoader.class
			.getName());

	// used for singleton
	private static ConfigLoader configLoader = null;

	// Configuration variable
	private final String configPathPrefix;
	private final String pluginConfigFile = "plugin.json";

	private String pluginConfigText = null;

	private Map<String, JSONObject> serverObjects = new HashMap<String, JSONObject>();
	private Map<String, JSONArray> pluginObjects = new HashMap<String, JSONArray>();

	private ConfigLoader() {
		if (System.getProperty("default.config.path") == null) {
			configPathPrefix = "./conf/";
		} else {
			configPathPrefix = System.getProperty("default.config.path");
		}
	}

	public static synchronized ConfigLoader getInstance() {
		if (configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}

	public void loadConfig() {

		loadServerConfig();

		loadPluginConfig();
	}

	private void loadServerConfig() {

		Map<String, JSONObject> tmpServerObjects = new HashMap<String, JSONObject>();
		try {
			String sql = "select id, zk_name, session_timeout, observer, observer_auth from mario_zk_info";
			MySQLHelper helper = new MySQLHelper();
			helper.open();
			ResultSet rs = helper.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				JSONArray serverIPList = getserverIPList(id);
				String zk_name = rs.getString("zk_name");
				int sessionTimeout = rs.getInt("session_timeout");
				String observer = rs.getString("observer");
				String observerAuth = rs.getString("observer_auth");
				
				JSONObject serverObject = new JSONObject();
				serverObject.put("serverIPList", serverIPList);
				serverObject.put("sessionTimeout", sessionTimeout);
				serverObject.put("observer", observer);
				serverObject.put("observerAuth", observerAuth);
				
				tmpServerObjects.put(zk_name, serverObject);
			}
			helper.close();
			serverObjects = tmpServerObjects;
		} catch (ClassNotFoundException e) {
			logger.error("Load server config failed! " + e.toString());
		} catch (SQLException e) {
			logger.error("Load server config failed! " + e.toString());
		} catch (JSONException e) {
			logger.error("Load server config failed! " + e.toString());
		}
	}

	private JSONArray getserverIPList(int zkId)
			throws JSONException, ClassNotFoundException, SQLException {
		JSONArray serverIPList = new JSONArray();
		String sql = "select id, host, port from mario_server_info "
				+ "where zk_id = " + zkId;
		MySQLHelper helper = new MySQLHelper();
		helper.open();
		ResultSet rs = helper.executeQuery(sql);
		while (rs.next()) {
			String host = rs.getString("host");
			int port = rs.getInt("port");
			serverIPList.put(host + ":" + port);
		}
		helper.close();
		return serverIPList;
	}

	private void loadPluginConfig() {

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
