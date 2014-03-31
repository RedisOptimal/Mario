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
	private final int zkId;
	private String zkName;
	private int sessionTimeout = 10000;
	private String observerAuth = null;

	private Set<String> connectionStrings = null;
	private Map<String, ZooKeeperClient> clients = null;
	private String observer = null;
	private ZooKeeperClient observerClient = null;

	public ZooKeeperCluster(int zkId, JSONObject object) {
		this.zkId = zkId;
		this.object = object;
		connectionStrings = new HashSet<String>();
		connectionStrings.clear();
		clients = new HashMap<String, ZooKeeperClient>();
		clients.clear();
		observer = "";
		observerAuth = "";
	}

	public void init() throws JSONException {
		zkName = object.getString("zkName");
		sessionTimeout = object.getInt("sessionTimeout");
		connectionStrings = readJSONObject();
		observer = object.getString("observer");
		observerAuth = object.getString("observerAuth");
		addClients(connectionStrings);
		addObserverClient(observer, observerAuth);

		logger.warn("Cluster " + zkName + " inited!");
	}

	public void close() {
		deleteClients(connectionStrings);
		observerClient.releaseConnection();
		logger.warn("Client " + observer + " removed from " + zkName + ".");
		logger.warn("Cluster " + zkName + " closed!");
	}

	public void updateClients(JSONObject object) throws JSONException {
		this.object = object;
		sessionTimeout = object.getInt("sessionTimeout");
		String newObserver = object.getString("observer");
		String newObserverAuth = object.getString("observerAuth");
		if (!observer.equals(newObserver)
				|| !observerAuth.equals(newObserverAuth)) {
			observer = newObserver;
			observerAuth = newObserverAuth;
			updateObserverClient(observer, observerAuth);
		}
		Set<String> newConnectionStrings = readJSONObject();
		Set<String> tmp = WarioUtilTools.getIntersection(connectionStrings,
				newConnectionStrings);
		deleteClients(WarioUtilTools.getDifference(connectionStrings, tmp));
		addClients(WarioUtilTools.getDifference(newConnectionStrings, tmp));
		connectionStrings = newConnectionStrings;
	}

	/**
	 * @return the zkName
	 */
	public String getZkName() {
		return zkName;
	}

	/**
	 * @return the clients
	 */
	public Map<String, ZooKeeperClient> getClients() {
		return clients;
	}

	/**
	 * @return the observer client
	 */
	public ZooKeeperClient getObserverClient() {
		return observerClient;
	}

	/**
	 * Read JSONObject and return the connection strings of clients.
	 * 
	 * @return connectionStrings
	 * @throws JSONException
	 */
	private Set<String> readJSONObject() throws JSONException {
		Set<String> res = new HashSet<String>();
		res.clear();
		JSONArray connectionStringArray = object.getJSONArray("serverIPList");
		for (int i = 0; i < connectionStringArray.length(); ++i) {
			res.add(connectionStringArray.getString(i));
		}
		return res;
	}

	private void addClients(Set<String> connectionStrings) {
		Iterator<String> it = connectionStrings.iterator();
		while (it.hasNext()) {
			String connectionString = it.next();
			if (!clients.containsKey(connectionString)) {
				ZooKeeperClient client = new ZooKeeperClient(connectionString,
						sessionTimeout);
				clients.put(connectionString, client);
				AddClient add = new AddClient(client);
				new Thread(add).start();
			}
		}
	}

	private void updateObserverClient(String connectionString,
			String observerAuth) {
		if (observerClient != null) {
			observerClient.releaseConnection();
		}
		logger.warn("Client " + observer + " removed from " + zkName + ".");
		addObserverClient(connectionString, observerAuth);
	}

	private void addObserverClient(String connectionString, String observerAuth) {
		observerClient = new ZooKeeperClient(connectionString, sessionTimeout,
				observer, observerAuth, zkId);
		if (observerClient != null) {
			AddClient add = new AddClient(observerClient);
			new Thread(add).start();
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
					+ zkName + ".");
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
						+ zkName + ".");
			}
		}
	}
}
