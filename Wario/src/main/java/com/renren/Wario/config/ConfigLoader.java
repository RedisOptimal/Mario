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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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

	private Map<Integer, JSONObject> serverObjects = new HashMap<Integer, JSONObject>();
	private Map<String, JSONArray> pluginObjects = new HashMap<String, JSONArray>();

	/**
	 * @return the serverObjects
	 */
	public Map<Integer, JSONObject> getServerObjects() {
		return serverObjects;
	}

	/**
	 * @return the pluginObjects
	 */
	public Map<String, JSONArray> getPluginObjects() {
		return pluginObjects;
	}

	private ConfigLoader() {

	}

	public static synchronized ConfigLoader getInstance() {
		if (configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}

	public void loadConfig() {

		try {
			
			loadServerConfig();

			loadPluginConfig();
			
		} catch (JSONException e) {
			logger.error("Load config failed! " + e.toString());
		} 
	}

	private void loadServerConfig() throws JSONException {

		Map<Integer, JSONObject> tmpServerObjects = new HashMap<Integer, JSONObject>();
		MySQLHelper helper = new MySQLHelper();
		String sql = "select id, zk_name, session_timeout, observer, observer_auth from mario_zk_info";
		try {
			helper.open();
			ResultSet rs = helper.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				JSONArray serverIPList = getserverIPList(id);
				int sessionTimeout = rs.getInt("session_timeout");
				String observer = rs.getString("observer");
				String observerAuth = rs.getString("observer_auth");

				JSONObject serverObject = new JSONObject();
				serverObject.put("serverIPList", serverIPList);
				serverObject.put("sessionTimeout", sessionTimeout);
				serverObject.put("observer", observer);
				serverObject.put("observerAuth", observerAuth);

				tmpServerObjects.put(id, serverObject);
			}
		} catch (SQLException e) {
			logger.error("MysqlHelper open failed or execute sql " + sql
					+ " failed! " + e.toString());
		} catch (ClassNotFoundException e) {
			logger.error("MysqlHelper open failed! " + e.toString());
		} finally {
			try {
				helper.close();
			} catch (SQLException e) {
				logger.error("MysqlHelper close failed! " + e.toString());
			}
		}
		serverObjects = tmpServerObjects;
	}

	private JSONArray getserverIPList(int zkId) {
		JSONArray serverIPList = new JSONArray();
		MySQLHelper helper = new MySQLHelper();
		String sql = "select id, host, port from mario_server_info "
				+ "where zk_id = " + zkId;
		try {
			helper.open();
			ResultSet rs = helper.executeQuery(sql);
			while (rs.next()) {
				String host = rs.getString("host");
				int port = rs.getInt("port");
				serverIPList.put(host + ":" + port);
			}
		} catch (SQLException e) {
			logger.error("MysqlHelper open failed or execute sql " + sql
					+ " failed! " + e.toString());
		} catch (ClassNotFoundException e) {
			logger.error("MysqlHelper open failed! " + e.toString());
		} finally {
			try {
				helper.close();
			} catch (SQLException e) {
				logger.error("MysqlHelper close failed! " + e.toString());
			}
		}
		return serverIPList;
	}

	private void loadPluginConfig() throws JSONException {
		Map<String, JSONArray> tmpPluginObjects = new HashMap<String, JSONArray>();
		MySQLHelper helper = new MySQLHelper();
		String sql = "select distinct plugin_name from mario_plugin_info";
		try {
			helper.open();
			ResultSet rs = helper.executeQuery(sql);
			while (rs.next()) {
				String pluginName = rs.getString("plugin_name");
				tmpPluginObjects.put(pluginName, getZKArrayRunsOnPlugin(pluginName));
			}
			// Run DBPlugin and RulePlugin on all zk.
			JSONArray allZKArray = getAllZKArray();
			tmpPluginObjects.put("DBPlugin", allZKArray);
			tmpPluginObjects.put("RulePlugin", allZKArray);
		} catch (SQLException e) {
			logger.error("MysqlHelper open failed or execute sql " + sql
					+ " failed! " + e.toString());
		} catch (ClassNotFoundException e) {
			logger.error("MysqlHelper open failed! " + e.toString());
		} finally {
			try {
				helper.close();
			} catch (SQLException e) {
				logger.error("MysqlHelper close failed! " + e.toString());
			}
		}
		pluginObjects = tmpPluginObjects;
	}

	private JSONArray getZKArrayRunsOnPlugin(String pluginName) throws JSONException {
		JSONArray arrary = new JSONArray();
		MySQLHelper helper = new MySQLHelper();
		String sql = "select zk_id, msg_sender, mail_sender, phone_number, email_address, args, commit from mario_plugin_info where plugin_name = '" + pluginName + "'";
		try {
			helper.open();
			ResultSet rs = helper.executeQuery(sql);
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("zkId", rs.getInt("zk_id"));
				object.put("msgSender", rs.getString("msg_sender"));
				object.put("mailSender", rs.getString("mail_sender"));
				object.put("phoneNumber", rs.getString("phone_number"));
				object.put("emailAddress", rs.getString("email_address"));
				object.put("args", rs.getString("args"));
				object.put("commit", rs.getString("commit"));
				arrary.put(object);
			}
		} catch (SQLException e) {
			logger.error("MysqlHelper open failed or execute sql " + sql
					+ " failed! " + e.toString());
		} catch (ClassNotFoundException e) {
			logger.error("MysqlHelper open failed! " + e.toString());
		} finally {
			try {
				helper.close();
			} catch (SQLException e) {
				logger.error("MysqlHelper close failed! " + e.toString());
			}
		}
		return arrary;
	}

	private JSONArray getAllZKArray() throws JSONException {
		JSONArray arrary = new JSONArray();
		Iterator<Entry<Integer, JSONObject>> it = serverObjects.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, JSONObject> entry = it.next();
			JSONObject object = new JSONObject();
			object.put("zkId", entry.getKey());
			object.put("msgSender", "DebugMsgSender");
			object.put("mailSender", "DebugMailSender");
			object.put("phoneNumber", "");
			object.put("emailAddress", "");
			object.put("args", "");
			object.put("commit", "");
			arrary.put(object);
		}
		return arrary;
	}
}
