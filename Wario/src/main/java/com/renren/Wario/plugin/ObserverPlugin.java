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
package com.renren.Wario.plugin;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

public class ObserverPlugin extends IPlugin {

	private int count = 0;
	
	@Override
	public void run() {
		System.out.println(new Date());
		try {
			List<String> children = client.getChildren("/");
			Iterator<String> it = children.iterator();
			while(it.hasNext()) {
				dfs("/" + it.next());
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(new Date());
		System.out.println(count);
	}
	
	void dfs(String path) {
		try {
			Stat stat = new Stat();
			client.getData(path, stat);
			count ++;
			List<String> children = client.getChildren(path);
			Iterator<String> it = children.iterator();
			while(it.hasNext()) {
				dfs(path + "/" + it.next());
			}
		} catch (KeeperException e) {
			System.err.println(path + " " + e.toString());
			return ;
		} catch (InterruptedException e) {
			System.err.println(path + " " + e.toString());
			return ;
		}
	}
}
