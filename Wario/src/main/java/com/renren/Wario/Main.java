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

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {
	private static Logger logger = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) {
		if (System.getProperty("default.config.path") == null) {
			PropertyConfigurator.configure(System.getProperty("user.dir")
					+ File.separator + "conf" + File.separator
					+ "log4j.properties");
		} else {
			PropertyConfigurator.configure(System
					.getProperty("default.config.path")
					+ File.separator
					+ "log4j.properties");
		}
		WarioMain warioMain = new WarioMain();
		warioMain.init();
		warioMain.start();
		try {
			warioMain.join();
			logger.error("WarioMain exited and Main thread will exit.");
		} catch (InterruptedException e) {
			logger.error("WarioMain interrupted with " + e.toString());
		}
	}

}
