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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.InitialContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.renren.Wario.config.ConfigLoader;
import com.renren.Wario.zookeeper.ZooKeeperCluster;

public class WarioMain extends Thread {
	
	private static Logger logger = LogManager.getLogger(WarioMain.class.getName());

	private ConfigLoader configLoader = null;
	private Map<String, ZooKeeperCluster> clusters = null;
	
	public WarioMain() {
		configLoader = new ConfigLoader();
		clusters = new HashMap<String, ZooKeeperCluster>();
	}
	
	public void init() {
		configLoader.loadConfig();
		clusters.clear();
		updateServerConfig(configLoader.serverObjects);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			System.err.println("start...");
			configLoader.loadConfig();
			updateServerConfig(configLoader.serverObjects);
			System.err.println("end...");
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void updateServerConfig(Map<String, JSONObject> serverObjects){
		ZooKeeperCluster zookeeperCluster = null;
		Iterator<Entry<String, JSONObject>> it = serverObjects.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<String, JSONObject> entry = (Map.Entry<String, JSONObject>)it.next();
			
			String zookeeperName = (String)entry.getKey();
			JSONObject object = serverObjects.get(zookeeperName);
			
			if(!clusters.containsKey(zookeeperName)) {
				zookeeperCluster = new ZooKeeperCluster(zookeeperName, object);
				zookeeperCluster.init();
				clusters.put(zookeeperName, zookeeperCluster);
			} else {
				zookeeperCluster = clusters.get(zookeeperName);
				zookeeperCluster.updateClients(object);
			}
		}
	}
	
}
