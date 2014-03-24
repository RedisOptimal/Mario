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
package com.renren.Wario.zookeeper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.renren.Wario.WarioUtilTools;

public class ZooKeeperCluster {
	private static Logger logger = LogManager.getLogger(ZooKeeperCluster.class
			.getName());

	private JSONObject object = null;
	private int sessionTimeout = 10000;
	private final String zookeeperName;
	private String authInfo;

	private Set<String> connectionStrings = null;
	private Map<String, ZooKeeperClient> clients = null;

	/**
	 * @return the clients
	 */
	public Map<String, ZooKeeperClient> getClients() {
		return clients;
	}

	public ZooKeeperCluster(String zookeeperName, JSONObject object) {
		this.zookeeperName = zookeeperName;
		this.object = object;
		authInfo = "";
		connectionStrings = new HashSet<String>();
		connectionStrings.clear();
		clients = new HashMap<String, ZooKeeperClient>();
		clients.clear();
	}

	/**
	 * @return the zookeeperName
	 */
	public String getZookeeperName() {
		return zookeeperName;
	}

	public void init() throws JSONException {
		sessionTimeout = object.getInt("sessionTimeout");
		authInfo = object.getString("authInfo");
		connectionStrings = readJSONObject();
		addClients(connectionStrings);
		logger.warn("Cluster " + zookeeperName + " inited!");
	}

	public void close() {
		deleteClients(connectionStrings);
		logger.warn("Cluster " + zookeeperName + " closed!");
	}

	public void updateClients(JSONObject object) throws JSONException {
		this.object = object;
		sessionTimeout = object.getInt("sessionTimeout");
		Set<String> newConnectionStrings = readJSONObject();
		Set<String> tmp = WarioUtilTools.getIntersection(connectionStrings,
				newConnectionStrings);
		deleteClients(WarioUtilTools.getDifference(connectionStrings, tmp));
		addClients(WarioUtilTools.getDifference(newConnectionStrings, tmp));
		connectionStrings = newConnectionStrings;
	}

	private void addClients(Set<String> connectionStrings) {
		Iterator<String> it = connectionStrings.iterator();
		while (it.hasNext()) {
			String connectionString = it.next();
			if (!clients.containsKey(connectionString)) {
				ZooKeeperClient client = new ZooKeeperClient(connectionString,
						sessionTimeout, authInfo);
				clients.put(connectionString, client);
				AddClient add = new AddClient(client);
				new Thread(add).start();
			}
		}
	}

	private class AddClient implements Runnable {

		ZooKeeperClient client = null;
		
		public AddClient(ZooKeeperClient client) {
			this.client = client;
		}

		@Override
		public void run() {
			logger.warn("Client " + client.getConnectionString() + " added to "
					+ zookeeperName
					+ ("".equals(authInfo) ? "" : (" with auth " + authInfo))
					+ ".");
			client.createConnection();
		}

	}

	private void deleteClients(Set<String> connectionStrings) {
		Iterator<String> it = connectionStrings.iterator();
		while (it.hasNext()) {
			String connectionString = it.next();
			if (clients.containsKey(connectionString)) {
				ZooKeeperClient zookeeperClient = clients.get(connectionString);
				zookeeperClient.releaseConnection();
				clients.remove(connectionString);
				logger.warn("Client " + connectionString + " removed from "
						+ zookeeperName + ".");
			}
		}
	}

	private Set<String> readJSONObject() throws JSONException {
		Set<String> res = new HashSet<String>();
		res.clear();
		JSONArray connectionStringArray = object.getJSONArray("serverIPList");
		for (int i = 0; i < connectionStringArray.length(); ++i) {
			res.add(connectionStringArray.getString(i));
		}
		return res;
	}
}
