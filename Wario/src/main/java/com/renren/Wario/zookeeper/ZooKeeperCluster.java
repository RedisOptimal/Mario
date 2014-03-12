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

	private Set<String> connectStrings = null;
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
		connectStrings = new HashSet<String>();
		connectStrings.clear();
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
		connectStrings = readJSONObject();
		addClients(connectStrings);
	}

	public void updateClients(JSONObject object) throws JSONException {
		this.object = object;
		sessionTimeout = object.getInt("sessionTimeout");
		Set<String> newConnectStrings = readJSONObject();
		Set<String> tmp = WarioUtilTools.getIntersection(connectStrings,
				newConnectStrings);
		deleteClients(WarioUtilTools.getDifference(connectStrings, tmp));
		addClients(WarioUtilTools.getDifference(newConnectStrings, tmp));
		connectStrings = newConnectStrings;
	}

	private void addClients(Set<String> connectStrings) {
		Iterator<String> it = connectStrings.iterator();
		while (it.hasNext()) {
			String connectString = it.next();
			if (!clients.containsKey(connectString)) {
				AddClient add = new AddClient(connectString, sessionTimeout);
				new Thread(add).start();
			}
		}
	}

	private class AddClient implements Runnable {

		private String connectString = null;
		private int sessionTimeout;

		public AddClient(String connectString, int sessionTimeout) {
			this.connectString = connectString;
			this.sessionTimeout = sessionTimeout;
		}

		@Override
		public void run() {
			ZooKeeperClient client = new ZooKeeperClient(connectString,
					sessionTimeout, authInfo);
			synchronized (clients) {
				clients.put(connectString, client);
				logger.warn("Client "
						+ connectString
						+ " added to "
						+ zookeeperName
						+ ("".equals(authInfo) ? ""
								: (" with auth " + authInfo)) + ".");
			}
			client.createConnection();
		}

	}

	private void deleteClients(Set<String> connectStrings) {
		Iterator<String> it = connectStrings.iterator();
		while (it.hasNext()) {
			String connectString = it.next();
			if (clients.containsKey(connectString)) {
				ZooKeeperClient zookeeperClient = clients.get(connectString);
				zookeeperClient.releaseConnection();
				clients.remove(connectString);
				logger.warn("Client " + connectString + " removed from "
						+ zookeeperName + ".");
			}
		}
	}

	private Set<String> readJSONObject() throws JSONException {
		Set<String> res = new HashSet<String>();
		res.clear();
		JSONArray connectStringArray = object.getJSONArray("serverIPList");
		for (int i = 0; i < connectStringArray.length(); ++i) {
			res.add(connectStringArray.getString(i));
		}
		return res;
	}

}
