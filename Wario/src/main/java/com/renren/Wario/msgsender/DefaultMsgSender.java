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
package com.renren.Wario.msgsender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DefaultMsgSender implements IMsgSender {

	private static Logger logger = LogManager.getLogger(DefaultMsgSender.class
			.getName());

	private final String SERVER_URL = "";

	@Override
	public void sendMessage(String number, String message) {
		System.err.println(number + ":" + message);
		String ret = "";
		try {
			String url = generateUrl(number, message);
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String tmp = null;
			while ((tmp = reader.readLine()) != null)
				ret += tmp;
			conn.disconnect();
			logger.info("Number:" + number + " Message:" + message + " return "
					+ ret + ".");
		} catch (MalformedURLException e) {
			logger.warn("Number:" + number + " Message:" + message
					+ " send failed!\n" + e.toString());
		} catch (IOException e) {
			logger.warn("Number:" + number + " Message:" + message
					+ " send failed!\n" + e.toString());
		}
	}

	private String generateUrl(String mobilePhone, String msg) throws UnsupportedEncodingException {
		StringBuilder url = new StringBuilder();
		url.append(SERVER_URL);

		url.append("receiver?number=");
		url.append(mobilePhone);

		url.append("&message=");
		url.append(urlEncode(msg));

		return url.toString();
	}

	private String urlEncode(String s) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, "UTF-8").replace(".", "%2E");
	}
}
