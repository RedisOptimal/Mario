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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class DefaultSmsSender implements ISmsSender {

	/* (non-Javadoc)
	 * @see com.renren.Wario.ISmsSender#sendSms(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendSms(String telephoneNumber, String smsMessage) {
		File sms = new File("smsOut.txt");
		OutputStreamWriter out = null;
		BufferedWriter writer = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(sms));
			writer = new BufferedWriter(out);
			writer.append(telephoneNumber + " : " + smsMessage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
