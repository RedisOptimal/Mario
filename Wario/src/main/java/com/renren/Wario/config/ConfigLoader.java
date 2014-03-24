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

	private MySQLHelper helper = new MySQLHelper();
	
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
			String sql = "select id, zk_name, session_timeout from mario_zk_info";
			helper.open();
			ResultSet rs = helper.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String zk_name = rs.getString("zk_name");
				int sessionTimeout = rs.getInt("session_timeout");
				
				tmpServerObjects.put(zk_name, getServerObject(id, sessionTimeout));
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
	
	private JSONObject getServerObject(int zkId, int sessionTimeout) throws JSONException, ClassNotFoundException, SQLException {
		JSONObject serverObject = new JSONObject();
		JSONArray serverIPList = new JSONArray();
		String sql = "select id, host, port, mode from mario_server_info where zk_id = " + zkId;
		helper.open();
		ResultSet rs = helper.executeQuery(sql);
		while(rs.next()) {
			String host = rs.getString("host");
			int port = rs.getInt("port");
			serverIPList.put(host + ":" + port);
		}
		serverObject.put("serverIPList", serverIPList);
		serverObject.put("sessionTimeout", sessionTimeout);
		serverObject.put("authInfo", "");
		return serverObject;
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
