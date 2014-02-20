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

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This is a singleton factory class.
 * 
 * @author zhe.yuan
 *
 */
public final class ZookeeperClientFactory {

	private static Logger logger = LogManager
			.getLogger(ZookeeperClientFactory.class.getName());

	private Map<String, ZookeeperClient> zookeeperMap;
	private ZookeeperConfigFactory zookeeperClientConfigFactory = 
								ZookeeperConfigFactory.getFactory();
	
	// used for singleton
	private static ZookeeperClientFactory zookeeperClientFactory = null;

	private ZookeeperClientFactory() {
		zookeeperMap = new TreeMap<String, ZookeeperClient>();
	}

	public ZookeeperClient getInstance(String zookeeperName) throws IOException {
		ZookeeperClient oldClient;
		ZookeeperClientConfig config = zookeeperClientConfigFactory.getInstance(zookeeperName);
		ZookeeperClient newClient = new ZookeeperClient(config.getConnectString(), config.getSessionTimeout());
	
		synchronized (zookeeperMap) {
			if ((oldClient = zookeeperMap.get(zookeeperName)) != null) {
				// update
				if (!oldClient.equals(newClient)) {
					oldClient.close();
					newClient.Init();
					zookeeperMap.put(zookeeperName, newClient);
				}
			} else {
				newClient.Init();
				zookeeperMap.put(zookeeperName, newClient);
			}
		}
		return zookeeperMap.get(zookeeperName);
	}

	public synchronized static ZookeeperClientFactory getFactory() {
		if (zookeeperClientFactory == null) {
			zookeeperClientFactory = new ZookeeperClientFactory();
		}
		return zookeeperClientFactory;
	}
}
