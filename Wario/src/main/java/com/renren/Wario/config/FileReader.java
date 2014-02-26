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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileReader {

	private static Logger logger = LogManager.getLogger(FileReader.class
			.getName());

	public static String read(String path) throws IOException {
		String text = "";
		File file = new File(path);
		InputStreamReader in = null;
		BufferedReader reader = null;
		try {
			in = new InputStreamReader(new FileInputStream(file));
			reader = new BufferedReader(in);
			String line;
			while ((line = reader.readLine()) != null) {
				text = text + line;
			}

		} catch (FileNotFoundException e) {
			logger.error("File not found. path = " + path);
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return text;
	}

}
