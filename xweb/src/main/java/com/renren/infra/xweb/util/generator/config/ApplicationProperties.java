package com.renren.infra.xweb.util.generator.config;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统配置信息
 * 
 * @author yong.cao
 * 
 */
public class ApplicationProperties implements Serializable {

	private static final long serialVersionUID = -58262947532410730L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationProperties.class);

	private static final String FILE_NAME = "generator/generator";

	private static Properties properties;

	/**
	 * 单例构建配置文件
	 */
	private static ApplicationProperties instance = new ApplicationProperties();

	private ApplicationProperties() {
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

	public static String getJdbcSchema() {
		return getProperty("jdbc.schema");
	}

	/**
	 * 项目名称
	 * 
	 * @return
	 */
	public static String getProjectName() {
		return getProperty("project.name");
	}

	/**
	 * 项目包名
	 * 
	 * @return
	 */
	public static String getProjectPackage() {
		return getProperty("project.package");
	}

	/**
	 * 文件输出路径
	 * 
	 * @return
	 */
	public static String getFileOutputPath() {
		return getProperty("generator.file.output.path");
	}

	/**
	 * 文件模板地址
	 * 
	 * @return
	 */
	public static String getFileTemplate() {
		return getProperty("generator.file.template");
	}

	/**
	 * 生成表信息
	 * 
	 * @return
	 */
	public static String getTablename() {
		return getProperty("generator.tablename");
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
			LOGGER.info("Property " + key + " not found in properties file");
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
