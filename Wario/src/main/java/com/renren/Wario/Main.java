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

import org.json.JSONObject;

import com.renren.Wario.config.ConfigLoader;
import com.renren.Wario.zookeeper.ZooKeeperCluster;

public class Main {

	private static Map<String, ZooKeeperCluster> clusters = new HashMap<String, ZooKeeperCluster>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConfigLoader configLoader = new ConfigLoader();
		configLoader.loadConfig();
		
		Map<String, JSONObject> serverObjects = configLoader.getServerObjects();
		
		Iterator<Entry<String, JSONObject>> it = serverObjects.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<String, JSONObject> entry = (Map.Entry<String, JSONObject>)it.next();
			String zookeeperName = (String)entry.getKey();
			JSONObject object = serverObjects.get(zookeeperName);
			ZooKeeperCluster zookeeperCluster = new ZooKeeperCluster(zookeeperName, object);
			zookeeperCluster.init();
			clusters.put(zookeeperName, zookeeperCluster);
		}
		
		while(true);
	}

}
