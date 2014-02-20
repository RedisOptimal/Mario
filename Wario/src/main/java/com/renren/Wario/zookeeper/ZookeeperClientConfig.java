/**
 * Copyright 2014 Renren.com Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.renren.Wario.zookeeper;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZookeeperClientConfig {

    private static Logger logger =
            LogManager.getLogger(ZookeeperClientConfig.class.getName());

    private final String zookeeperName;
    private final Map<String, ZookeeperClient> clientMap;
    private final int sessionTimeout;
    private final String metaString;

    /**
     * @param metaString
     * @throws JSONException
     */

    public ZookeeperClientConfig(String zookeeperName, String metaString)
        throws JSONException{
        logger.info(zookeeperName + " init with " + metaString);
        this.metaString = metaString;
        this.zookeeperName = zookeeperName;
        JSONObject jsonObject = new JSONObject(metaString);
        sessionTimeout = jsonObject.getInt("sessionTimeout");
        JSONArray serverIPArray = jsonObject.getJSONArray("serverIPList");
        clientMap = new TreeMap<String, ZookeeperClient>();

        for (int i = 0; i < serverIPArray.length(); ++i) {
            ZookeeperClient tmpClient =
                    new ZookeeperClient(serverIPArray.getString(i),
                        sessionTimeout);
            try {
                tmpClient.Init();
            } catch (IOException e) {
                logger.error("Zookeeper client " + serverIPArray.getString(i)
                             + ". First initial failed with " + e.toString());
                e.printStackTrace();
            }
            clientMap.put(serverIPArray.getString(i), tmpClient);
        }
    }

    @Override
    public String toString() {
        return zookeeperName + ":" + metaString;
    }

    /**
     * @return the zookeeperName
     */
    public String getZookeeperName() {
        return zookeeperName;
    }

    /**
     * @return the sessionTimeout
     */
    public int getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * @return the metaString
     */
    public String getMetaString() {
        return metaString;
    }

    public Set<String> getConnectString() {
        return clientMap.keySet();
    }

    @SuppressWarnings("unchecked")
	public Map.Entry<String, ZookeeperClient> getClientMap() {
        return (Entry<String, ZookeeperClient>) clientMap.entrySet();
    }
    
    @Override
    public boolean equals(Object o) {
        // TODO 实现clientMap集合判断
        ZookeeperClientConfig o2 = (ZookeeperClientConfig) o;
        return (this.getSessionTimeout() == o2.getSessionTimeout())
               && this.getMetaString().equals(o2.getMetaString());
    }

}
