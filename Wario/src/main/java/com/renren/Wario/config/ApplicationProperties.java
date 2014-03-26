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
package com.renren.Wario.config;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 系统配置信息
 * 
 * @author yong.cao
 * 
 */
public class ApplicationProperties implements Serializable {

	private static final long serialVersionUID = -58262947532410730L;

	private static final Logger logger = LogManager
			.getLogger(ApplicationProperties.class);

	private static final String FILE_NAME = "application";

	private static Properties properties;

	/**
	 * 单例构建配置文件
	 */
	private static ApplicationProperties instance = new ApplicationProperties();

	private ApplicationProperties() {;
		properties = initializeProperties(FILE_NAME);
	}

	public static ApplicationProperties getInstance() {
		return instance;
	}

	/**
	 * 数据源类名
	 * 
	 * @return
	 */
	public static String getJdbcDriver() {
		return getProperty("jdbc.driver");
	}

	/**
	 * 数据源连接地址
	 * 
	 * @return
	 */
	public static String getJdbcUrl() {
		return getProperty("jdbc.url");
	}

	/**
	 * 数据源用户名
	 * 
	 * @return
	 */
	public static String getJdbcUsername() {
		return getProperty("jdbc.username");
	}

	/**
	 * 数据源密码
	 * 
	 * @return
	 */
	public static String getJdbcPassword() {
		return getProperty("jdbc.password");
	}

	public static Properties getProperties() {
		if (properties == null) {
			properties = initializeProperties(FILE_NAME);
		}
		return properties;
	}

	/**
	 * 初始化properties，加入初始化参数
	 * @param filename
	 * @return
	 */
	private static Properties initializeProperties(String filename) {
		PropertyResourceBundle configBundle = (PropertyResourceBundle) PropertyResourceBundle
				.getBundle(filename);

		Properties newProp = new Properties();
		Enumeration<String> keys = configBundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) configBundle.getObject(key);
			newProp.put(key, value);
		}
		return newProp;
	}

	/**
	 * 根据key获取对应的value
	 * 
	 * @param key
	 * @return
	 */
	private static String getProperty(String key) {
		String value = "";
		try {
			value = getProperties().getProperty(key);
			if (value == null) {
				throw new Exception("Property value null for " + key);
			}
		} catch (Exception e) {
			logger.info("Property " + key + " not found in properties file");
		}
		return value;
	}

	/**
	 * 获取相同key的一组properties
	 * @param props
	 * @param mask
	 * @return
	 */
	public static Properties extractProperties(Properties props, String mask) {
		Properties results = new Properties();
		Enumeration<?> enum1 = props.keys();
		while (enum1.hasMoreElements()) {
			String aKey = (String) enum1.nextElement();
			if (aKey != null && aKey.indexOf(mask) >= 0) {
				Object aValue = props.getProperty(aKey);
				results.put(aKey, aValue);
			}
		}
		return results;
	}

}
