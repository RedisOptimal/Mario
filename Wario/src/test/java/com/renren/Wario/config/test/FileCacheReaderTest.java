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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Assert;
import org.junit.Test;

import com.renren.Wario.config.FileCacheReader;

public class FileCacheReaderTest {
	private static String fileContent1 = new String(
										"{" +
										"\"This is first file.\"" +
										"}");
	
	private static String fileContent2 = new String(
										"{" +
										"\"This is second file.\"" +
										"}");
	
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
	
	@Test
	public void generalTest() {
		try {
			@SuppressWarnings("unused")
			String fileContent = FileCacheReader.read("tmp.txt");
		} catch (IOException e) {
			Assert.assertTrue(e instanceof FileNotFoundException);
		}	
		
		try {
			output("tmp.txt", fileContent1);
			String fileContent = FileCacheReader.read("tmp.txt");
			Assert.assertTrue(fileContent.equals(fileContent1));
			fileContent = FileCacheReader.read("tmp.txt");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			output("tmp.txt", fileContent2);
			fileContent = FileCacheReader.read("tmp.txt");
			Assert.assertTrue(fileContent.equals(fileContent2));
		} catch (IOException e) {
			Assert.fail();
			e.printStackTrace();
		} finally {
			new File("tmp.txt").delete();
		}
	}
	
}
