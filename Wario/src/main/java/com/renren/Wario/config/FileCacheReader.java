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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileCacheReader {

	private static Logger logger = LogManager.getLogger(FileCacheReader.class
			.getName());

	private static final class FileCache implements Comparable<FileCache>,
			Comparator<FileCache> {
		private final String pathString;
		private final long lastModified;
		private final String content;

		public FileCache(String pathString) throws IOException {
			String tmpString = "";
			File file = new File(pathString);

			InputStreamReader in = null;
			BufferedReader reader = null;
			try {
				in = new InputStreamReader(new FileInputStream(file));
				reader = new BufferedReader(in);
				String line;
				while ((line = reader.readLine()) != null) {
					tmpString = tmpString + line;
				}
			} catch (FileNotFoundException e) {
				logger.error("File not found. path = " + pathString);
				throw e;
			} finally {
				content = tmpString;
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			}
			this.pathString = file.getAbsolutePath();
			lastModified = file.lastModified();
		}

		/**
		 * @return the content
		 */
		public String getContent() {
			return content;
		}

		/**
		 * @return the lastModified
		 */
		public long getLastModified() {
			return lastModified;
		}

		/**
		 * @return the pathString
		 */
		public String getPathString() {
			return pathString;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(FileCache o1, FileCache o2) {
			return o1.compareTo(o2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(FileCache arg0) {
			return this.getPathString().compareTo(arg0.getPathString());
		}
	}

	private static Map<String, FileCache> fileCache = new HashMap<String, FileCache>();

	public static String read(String path) throws IOException {
		File file = new File(path);
		String absolutePath = file.getAbsolutePath();
		synchronized (fileCache) {
			FileCache cache = fileCache.get(absolutePath);
			if (cache == null || cache.getLastModified() != file.lastModified()) {
				FileCache newCache = new FileCache(absolutePath);
				fileCache.put(absolutePath, newCache);
			}
			return fileCache.get(absolutePath).getContent();
		}
	}

}
