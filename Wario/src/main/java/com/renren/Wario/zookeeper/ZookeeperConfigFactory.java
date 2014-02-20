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

import java.util.Map;
import java.util.TreeMap;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * This is a singleton factory class.
 * 
 * @author zhe.yuan
 *
 */
public class ZookeeperConfigFactory {
	
	private static Logger logger = LogManager
			.getLogger(ZookeeperConfigFactory.class.getName());
	
	// Map<zookeeperName, config>
	private Map<String, ZookeeperClientConfig> zookeeperConfigMap;
	
	// used for singleton
	private static ZookeeperConfigFactory zookeeperConfigFactory = null;
	
	private ZookeeperConfigFactory() {
		zookeeperConfigMap = new TreeMap<String, ZookeeperClientConfig>();
	}
	
	public ZookeeperClientConfig getInstance(String zookeeperName) {
		synchronized (zookeeperConfigMap) {
			return zookeeperConfigMap.get(zookeeperName);
		}
	}
	
	public void clear() {
		synchronized (zookeeperConfigMap) {
			zookeeperConfigMap.clear();
		}
	}
	
	/**
	 * metaString's format as fellow : 
	 *	{
	 * 		serverIPList:["localhost:2181","localhost:2182"],
	 * 		sessionTimeout:"5000"
	 * 	}
	 * @param zookeeperName
	 * @param metaString
	 * @throws JSONException 
	 */
	public void addInstance(String zookeeperName, String metaString) throws JSONException {
		ZookeeperClientConfig newConfig = new ZookeeperClientConfig(zookeeperName, metaString);
		synchronized (zookeeperConfigMap) {
			ZookeeperClientConfig oldConfig = zookeeperConfigMap.get(zookeeperName);
			if (oldConfig == null) {
				// Update config map
				zookeeperConfigMap.put(zookeeperName, newConfig);
			}
		}
	}
	
	public synchronized static ZookeeperConfigFactory getFactory() {
		if (zookeeperConfigFactory == null) {
			zookeeperConfigFactory = new ZookeeperConfigFactory();
		}
		return zookeeperConfigFactory;
	}

}
