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
package com.renren.Wario.config.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.renren.Wario.config.ConfigLoader;

public class ConfigLoaderTest {
	private static String pluginConfig1 = "";
	private static String pluginConfig2 = "{}";
	private static String pluginConfig3 = "{\n"
			+ "\"DefaultPlugin\":[\n"
			+ "{messionName:\"Agent1\",zookeeperName:\"Zookeeper1\",msgSender:\"DefaultMsgSender\",mailSender:\"DefaultMailSender\"}\n"
			+ "]\n" + "}";

	private void output(String path, String content) throws IOException {
		File file = new File(path);
		OutputStreamWriter out = null;
		BufferedWriter writer = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file));
			writer = new BufferedWriter(out);
			writer.write(content);
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	private boolean SearchFile(String file, String keyword) throws IOException {
		String str;
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			while ((str = bufferedReader.readLine()) != null) {
				if (str.indexOf(keyword) != -1) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return false;
	}

	@BeforeClass
	public static void init() {
		System.setProperty("default.config.path", "./");
		PropertyConfigurator
				.configure(System.getProperty("user.dir") + File.separator
						+ "conf" + File.separator + "log4j.properties");
	}

	@Test
	public void FileNotFoundTest() throws IOException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ConfigLoader config = ConfigLoader.getInstance();
		config.loadConfig();
		File file = new File("logs/Wario.log");
		Assert.assertTrue(file.exists());
		Assert.assertTrue(SearchFile("logs/Wario.log", "Load plugin config"));
	}

	@Test
	public void BlankConfigTest() throws IOException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		output("plugin.json", pluginConfig1);
		ConfigLoader config = ConfigLoader.getInstance();
		config.loadConfig();
		new File("plugin.json").delete();
		Assert.assertTrue(SearchFile(
				"logs/Wario.log",
				"parsing plugin , check the file format. org.json.JSONException: A JSONObject text must begin with '{'"));
	}

	@Test
	public void AcceptFormatTest() throws IOException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		output("plugin.json", pluginConfig3);
		ConfigLoader config = ConfigLoader.getInstance();
		config.loadConfig();
		Map<String, JSONArray> pluginMap = config.getPluginObjects();
		Assert.assertTrue(pluginMap.size() == 1);
		config.loadConfig();
		pluginMap = config.getPluginObjects();
		Assert.assertTrue(pluginMap.size() == 1);
		new File("plugin.json").delete();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		output("plugin.json", pluginConfig2);
		config.loadConfig();
		Assert.assertTrue(pluginMap.size() == 1);
		pluginMap = config.getPluginObjects();
		Assert.assertTrue(pluginMap.size() == 0);
		new File("plugin.json").delete();
	}
	
	@AfterClass
	public static void close() {

		new File("logs/Wario.log").delete();
	}
}
