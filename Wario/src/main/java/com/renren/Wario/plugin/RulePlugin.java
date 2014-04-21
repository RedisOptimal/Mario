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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import com.renren.Wario.db.MySQLHelper;

public class RulePlugin extends IPlugin {

	private static Logger logger = Logger.getLogger(RulePlugin.class);

	private MySQLHelper helper = new MySQLHelper();
	private PreparedStatement selectPs;

	@Override
	public void run() {
		String sql = "select path, type, min_children_number, max_children_number, phone_number, email_address, user_id from mario_rule_info where zk_id = " + client.getZkId() + " and enable = true";
		try {
			helper.open();
			selectPs = helper
					.getPreparedStatement("select mzxid from mario_node_state where zk_id = ? and path = ? and mzxid = ? ");
			ResultSet rs = helper.executeQuery(sql);
			while (rs.next()) {
			    if (rs.getString("phone_number") != null) {
			        numbers = rs.getString("phone_number").split(",");
			    } else {
			        numbers = null;
			    }
			    if (rs.getString("email_address") != null) {
			        addresses = rs.getString("email_address").split(",");
			    } else {
			        addresses = null;
			    }

				String type = rs.getString("type");
				if ("node exists".equals(type)) {
					processNodeExists(client.getZkId(), rs.getString("path"));
				} else if ("children number".equals(type)) {
					processChildrenNumber(client.getZkId(),
							rs.getString("path"),
							rs.getInt("min_children_number"),
							rs.getInt("max_children_number"));
				} else if ("data changed".equals(type)) {
					processDataChanged(client.getZkId(), rs.getString("path"));
				}
			}
		} catch (SQLException e) {
			logger.error("MysqlHelper open failed or get prepared statement failed or execute sql "
					+ sql + " failed! ", e);
		} catch (ClassNotFoundException e) {
			logger.error("MysqlHelper open failed! ", e);
		} finally {
			try {
				selectPs.close();
				helper.close();
			} catch (SQLException e) {
				logger.error("MysqlHelper close failed! ", e);
			}
		}
	}

	private void processNodeExists(int zk_id, String path) {
		try {
			if (client.exists(path) == null) {
				alert("Node " + path + " at zk " + zk_id + " is not exist!");
			}
		} catch (KeeperException e) {

		} catch (InterruptedException e) {

		}
	}

	private void processDataChanged(int zk_id, String path) {
		Stat stat = new Stat();
		try {
			client.getChildren(path, stat);
			selectPs.setInt(1, zk_id);
			selectPs.setString(2, path);
			selectPs.setLong(3, stat.getMzxid());
			ResultSet rs = selectPs.executeQuery();
//			while(rs.next()) {
//				System.err.println(rs.getLong("mzxid"));
//			}
			if (!rs.next()) {
				alert("Data changed on node " + path + " at zk " + zk_id + ".");
			}
		} catch (KeeperException e) {
			logger.error("Get children failed on " + path + " at zk " + zk_id
					+ "! ", e);
		} catch (InterruptedException e) {
			logger.error("Get children failed on " + path + " at zk " + zk_id
					+ "! ", e);
		} catch (SQLException e) {
			logger.error("Prepared statement set value failed! ", e);
		}
	}

	private void processChildrenNumber(int zk_id, String path,
			int minChildrenNumber, int maxChildrenNumber) {
		try {
			int childrenNumber = client.getChildren(path).size();
			if (childrenNumber < minChildrenNumber
					|| maxChildrenNumber < childrenNumber) {
				alert("Children number on node " + path + " at zk " + zk_id
						+ " is out of range [" + minChildrenNumber + ", "
						+ maxChildrenNumber + "].");
			}
		} catch (KeeperException e) {
			logger.error("Get children failed on " + path + " at zk " + zk_id
					+ "! ", e);
		} catch (InterruptedException e) {
			logger.error("Get children failed on " + path + " at zk " + zk_id
					+ "! ", e);
		}
	}

	private void alert(String message) {
	    if (numbers != null) {
    		for (String number : numbers) {
    			msgSender.sendMessage(number.trim(), message);
    		}
	    }
	    if (addresses != null) {
    		for (String address : addresses) {
    			mailSender.sendMail(address.trim(), message);
    		}
	    }
	}

}
