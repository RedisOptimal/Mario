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

import java.io.IOException;
import java.util.Comparator;

import com.renren.Wario.zookeeper.ZookeeperClientConfig;
import com.renren.Wario.zookeeper.ZookeeperConfigFactory;

public abstract class IAlertPlugin implements Runnable, Comparator<IAlertPlugin>, Comparable<IAlertPlugin> {
	protected final String serviceName;
	protected final String zookeeperName;
	protected ISmsSender smsSender = null;
	protected IMailSender mailSender = null;
	protected ZookeeperClientConfig zookeeperClient;
	
	public IAlertPlugin(String serviceName, String zookeeperName, ISmsSender smsSender, IMailSender mailSender) throws IOException {
		this.serviceName = serviceName;
		this.zookeeperName = zookeeperName;
		this.smsSender = smsSender;
		this.mailSender = mailSender;
		zookeeperClient = ZookeeperConfigFactory.getFactory().getInstance(zookeeperName);
	}

	public abstract String toHtml();

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @return the zookeeperName
	 */
	public String getZookeeperName() {
		return zookeeperName;
	}

	@Override
	public boolean equals(Object o) {
		return (this.compareTo((IAlertPlugin) o) == 0);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(IAlertPlugin o) {
		return this.getServiceName().compareTo(o.getServiceName());
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IAlertPlugin o1, IAlertPlugin o2) {
		return o1.compareTo(o2);
	}

	
}
