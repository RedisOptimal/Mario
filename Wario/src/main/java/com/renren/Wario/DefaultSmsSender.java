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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class DefaultSmsSender implements ISmsSender {
	private static Logger logger = LogManager
			.getLogger(DefaultSmsSender.class.getName());

	private String SERVER_URL = "http://sms.notify.d.xiaonei.com:2000/";
	
	/* (non-Javadoc)
	 * @see com.renren.Wario.ISmsSender#sendSms(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendSms(String telephoneNumber, String smsMessage) {
		String url = generateUrl(telephoneNumber, smsMessage);
		
		String ret = "";
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String tmp = null;
			while ((tmp = reader.readLine()) != null)
				ret += tmp;
			conn.disconnect();
		} catch (MalformedURLException e) {
			logger.warn("Number:" + telephoneNumber + " Message:" + smsMessage + " send failed!");
			e.printStackTrace();
		} catch (IOException e) {
			logger.warn("Number:" + telephoneNumber + " Message:" + smsMessage + " send failed!");
			e.printStackTrace();
		}
	}
	
	private String generateUrl(String mobilePhone, String msg) {
		StringBuilder url = new StringBuilder();
		url.append(SERVER_URL);

		url.append("receiver?number=");
		url.append(mobilePhone);

		url.append("&message=");
		url.append(urlEncode(msg));

		return url.toString();
	}
	
	private String urlEncode(String s) throws RuntimeException {
		try {
			return URLEncoder.encode(s, "UTF-8").replace(".", "%2E");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("This should never happen.");
		}
	}

}
