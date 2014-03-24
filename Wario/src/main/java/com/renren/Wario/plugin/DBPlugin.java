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

import java.sql.ResultSet;
import java.sql.SQLException;

import com.renren.Wario.db.MySQLHelper;

public class DBPlugin extends IPlugin {
	private MySQLHelper helper = new MySQLHelper();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		client.state.update();
		
		try {
			String sql = "insert into mario_server_state (server_id, min_latency, ave_latency, max_latency, received, sent, outStanding, zxid, mode, node_count, total_watches, client_number, time_stamp) values ("
					+ getServerId(client.state.getHost(), client.state.getPort())
					+ ", "
					+ client.state.getMinLatency()
					+ ", "
					+ client.state.getAvgLatency()
					+ ", "
					+ client.state.getMaxLatency()
					+ ", "
					+ client.state.getReceived()
					+ ", "
					+ client.state.getSent()
					+ ", "
					+ client.state.getOutStanding()
					+ ", '"
					+ client.state.getZxid()
					+ "', '"
					+ client.state.getMode()
					+ "', "
					+ client.state.getNodeCount()
					+ ", "
					+ client.state.getTotalWatches()
					+ ", "
					+ client.state.getClientNumber()
					+ ", "
					+ System.currentTimeMillis() + ")";
			helper.open();
			helper.execute(sql);
			helper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private int getServerId(String host, int port) throws ClassNotFoundException, SQLException {
		int serverId = -1;
		String sql = "select zk_id from mario_server_info where host = '" + host + "' and port = " + port;
		helper.open();
		ResultSet result = helper.executeQuery(sql);
		while(result.next()) {
			serverId = result.getInt(1);
		}
		helper.close();
		return serverId;
	}

}
