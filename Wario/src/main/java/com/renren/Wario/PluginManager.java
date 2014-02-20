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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.renren.Wario.zookeeper.ZookeeperConfigFactory;

public class PluginManager {
	
	private static Logger logger = LogManager
			.getLogger(PluginManager.class.getName());

	// 2min
	private final Long MaxThreadSleepTime = 120L;
	private final Long MinThreadSleepTime = 20L;

	private final Long UpdateConfigInterval = 300L;
	// Configuration variable
	private final String configPathPrefix;
	private final String serverConfigName = "server.json";
	private final String pluginConfigName = "plugin.json";
	
	private Set<IAlertPlugin> alertPlugins = null;
	private Thread backgroundThread = null;
	
	private ISmsSender defaultSmsSender = new DefaultSmsSender();
	private IMailSender defaultMailSender = new DefaultMailSender();
	
	private String serverConfigText;
	private String pluginConfigText;
	
	// used for singleton
	private static PluginManager pluginManager = null;

	private ZookeeperConfigFactory zookeeperConfigFactory = ZookeeperConfigFactory.getFactory();
	
	private class DoProcess implements Runnable {
		private Set<IAlertPlugin> alertPlugins = null;

		public DoProcess(Set<IAlertPlugin> alertPlugins) {
			this.alertPlugins = alertPlugins;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			Long prevTime = new Date().getTime();
			while (true) {
				Long startProcessTime = new Date().getTime();
				synchronized (PluginManager.this.alertPlugins) {
					Iterator<IAlertPlugin> iterator = alertPlugins.iterator();
					while (iterator.hasNext()) {
						IAlertPlugin onPorcess = iterator.next();
						try {
							onPorcess.run();
						} catch (Throwable e) {
							logger.warn("Exception when background runing : " + e.toString());
						}
					}
				}
				Long endProcessTime = new Date().getTime();
				try {
					Thread.sleep((Math.max(MinThreadSleepTime,
							Math.min(MaxThreadSleepTime, endProcessTime - startProcessTime))) << 10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Long currTime = new Date().getTime();
				if (currTime - prevTime > UpdateConfigInterval) {
					prevTime = currTime;
					try {
						loadConfig();
					} catch (IOException e) {
						logger.error("Error in load config." + e.toString());
						e.printStackTrace();
					} catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
				}
			}
		}
	}

	private PluginManager() {
		if (System.getenv("default.config.path") != null) {
			configPathPrefix = System.getenv("default.config.path");
		} else {
			configPathPrefix = "./conf/";
		}
		try {
			loadConfig();
		} catch (Throwable e) {
			logger.fatal("Error when init : " + e);
			System.exit(1);
		}
		alertPlugins = new ConcurrentSkipListSet<IAlertPlugin>();
		backgroundThread = new Thread(new DoProcess(alertPlugins));
		backgroundThread.setDaemon(true);
		backgroundThread.start();
		
	}

	public synchronized static PluginManager getInstance() {
		if (pluginManager == null) {
			pluginManager = new PluginManager();
		}
		return pluginManager;
	}

	public void addAlertPlugin(IAlertPlugin alertPlugin) {
		synchronized (alertPlugins) {
			alertPlugins.add(alertPlugin);
		}
	}

	public void clear() {
		synchronized (alertPlugins) {
			alertPlugins.clear();
		}
	}

	public void deleteAlertPlugin(IAlertPlugin alertPlugin) {
		synchronized (alertPlugins) {
			alertPlugins.remove(alertPlugin);
		}
	}
	/**
	 * Config file format as fellow :
	 * {
	 * 	"ZookeeperTest":{
	 * 		serverIPList:["localhost:2181","localhost:2182"],
	 * 		sessionTimeout:"5000"
	 * 	}
	 * }
	 * @param path
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public void loadConfig() throws IOException, JSONException {
		File serverFile = new File(configPathPrefix + serverConfigName);
		File pluginFile = new File(configPathPrefix + pluginConfigName);
		InputStreamReader in = null;
		BufferedReader reader = null;		
		try {
			in = new InputStreamReader(new FileInputStream(serverFile));
			reader = new BufferedReader(in);
			String text = "", line;
			while ((line = reader.readLine()) != null) {
				text = text + line;
			}
			JSONObject jsonObject = new JSONObject(text);
			Iterator<?> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String zookeeperName = (String) iterator.next();
				String metaString = jsonObject.getJSONObject(zookeeperName).toString();
				System.err.println(zookeeperName + " : " + metaString);
				zookeeperConfigFactory.updateInstance(zookeeperName, metaString);
			}
			logger.info("Old server config : " + serverConfigText + " New server config : " + text);
			serverConfigText = text;
		} catch (FileNotFoundException e) {
			logger.error("Server config not found.");
			e.printStackTrace();
			throw e;
		} catch (JSONException e) {
			logger.error("Can't parsing server.json, check the file format." + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (in != null) {
				in.close();
			}
		}
		
		try {
			in = new InputStreamReader(new FileInputStream(pluginFile));
			reader = new BufferedReader(in);
			String text = "", line;
			while ((line = reader.readLine()) != null) {
				text = text + line;
			}
			JSONObject jsonObject = new JSONObject(text);
			Iterator<?> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String serviceName = (String) iterator.next();
				JSONObject body = jsonObject.getJSONObject(serviceName);
				String zookeeperName = body.getString("zookeeperName");
				System.err.println(serviceName + " " + zookeeperName);
			}
			logger.info("Old plugin config : " + pluginConfigText + " New plugin config : " + text);
			pluginConfigText = text;
		} catch (FileNotFoundException e) {		
			logger.error("Plugin config not found.");
			e.printStackTrace();
			throw e;
		} catch (JSONException e) {
			logger.error("Can't parsing server.json, check the file format." + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}

	@Override
	public String toString() {
		// TODO override toString() method for log
		return null;
	}

	public static void main(String[] args) throws IOException, JSONException {
		PluginManager pluginManager = new PluginManager();
		pluginManager.loadConfig();

	}

}
