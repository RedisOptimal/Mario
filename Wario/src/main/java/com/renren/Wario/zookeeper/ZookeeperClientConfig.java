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

import java.util.Comparator;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * @author zhe
 *
 */
public class ZookeeperClientConfig implements Comparable<ZookeeperClientConfig>, Comparator<ZookeeperClientConfig> {
	
	private static Logger logger = LogManager.getLogger(ZookeeperClientConfig.class
			.getName());
	
	private final String zookeeperName;
	private final String connectString;
	private final int sessionTimeout;
	private final String metaString;
	/**
	 *
	 * @param metaString
	 */
	
	public ZookeeperClientConfig(String zookeeperString, String metaString) {
		this.metaString = metaString;
		this.zookeeperName = zookeeperString;
		JSONObject jsonObject = new JSONObject(metaString);
		connectString = jsonObject.getString("connectString");
		sessionTimeout = jsonObject.getInt("sessionTimeout");
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
	 * @return the connectString
	 */
	public String getConnectString() {
		return connectString;
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

	@Override
	public boolean equals(Object o) {
		return (this.compareTo((ZookeeperClientConfig) o) == 0);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(ZookeeperClientConfig o1, ZookeeperClientConfig o2) {
		return o1.compareTo(o2);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ZookeeperClientConfig o) {
		if (this.getZookeeperName().equals(o.getZookeeperName())) {
			if (this.getConnectString().equals(o.getConnectString())) {
				return this.getSessionTimeout() - o.getSessionTimeout();
			} else {
				return this.getConnectString().compareTo(o.getConnectString());
			}
		} else {
			return this.getZookeeperName().compareTo(o.getZookeeperName());
		}
	}
	
}
