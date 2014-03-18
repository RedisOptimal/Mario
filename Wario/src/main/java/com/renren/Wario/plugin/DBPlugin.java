package com.renren.Wario.plugin;

import java.sql.SQLException;

import com.renren.Wario.db.MySQLHelper;

public class DBPlugin extends IPlugin {
	private MySQLHelper helper = new MySQLHelper();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		client.state.update();
		
		String sql = "insert into ZKState (MinLatency, AveLatency, MaxLatency, Received, Sent, OutStanding, Zxid, Mode, NodeCount, TotalWatches, ClientNumber, TimeStamp) values ("
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
		
		try {
			helper.open();
			helper.execute(sql);
			helper.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
