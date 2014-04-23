package com.renren.infra.xweb.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.zookeeper.client.FourLetterWordMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.renren.infra.xweb.entity.Mario_server_info;
import com.renren.infra.xweb.entity.Mario_server_state;
import com.renren.infra.xweb.entity.Mario_zk_info;
import com.renren.infra.xweb.service.Mario_server_infoService;
import com.renren.infra.xweb.service.Mario_server_stateService;
import com.renren.infra.xweb.service.Mario_zk_infoService;

@Controller
@RequestMapping(value = "/index")
public class IndexController {

	private static Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	Mario_zk_infoService zkInfoService;
	@Autowired
	Mario_server_infoService serverInfoService;
	@Autowired
	Mario_server_stateService serverStateService;
	
    @RequestMapping(value = "")
    public String index(Model model, RedirectAttributes redirectAttributes) {
    	List<Mario_zk_info> mario_zk_infos = zkInfoService.getAllMario_zk_info();
    	List<Mario_server_info> mario_server_infos = serverInfoService.getAllMario_server_info();
    	List<ZKState> zkStates = new ArrayList<ZKState>();
    	for(Mario_zk_info mario_zk_info : mario_zk_infos) {
    		List<ServerState> serverStates = new ArrayList<ServerState>();
    		for(Mario_server_info mario_server_info : mario_server_infos) {
    			if(mario_server_info.getzk_id().equals(mario_zk_info.getid())) {
    	    		ServerState serverState = new ServerState();
    	    		serverState.setInfo(mario_server_info);
    	    		serverState.setState(serverStateService.getLastMario_server_state(mario_server_info.getid()));
    	    		serverState.update();
    	    		serverStates.add(serverState);
    			}
    		}
    		
    		ZKState zkState = new ZKState();
    		zkState.setZkInfo(mario_zk_info);
    		zkState.setServerStates(serverStates);
    		
    		zkStates.add(zkState);
    	}
    	model.addAttribute("zk_states", zkStates);
        return "dashboard/index";
    }
    
	public class ZKState {
    	private Mario_zk_info zkInfo;
    	private List<ServerState> serverStates;
		public Mario_zk_info getZkInfo() {
			return zkInfo;
		}
		public void setZkInfo(Mario_zk_info zkInfo) {
			this.zkInfo = zkInfo;
		}
		public List<ServerState> getServerStates() {
			return serverStates;
		}
		public void setServerStates(List<ServerState> serverStates) {
			this.serverStates = serverStates;
		}
    }

    public class ServerState {
    	
    	private String ruok = "";
		private int outStanding = -1;
		private long zxid = -1;
		private String mode = "";
		private int nodeCount = -1;
		private int totalWatches = -1;
		
    	private Mario_server_info info;
    	private Mario_server_state state;
    	
		public String getRuok() {
			return ruok;
		}
		public int getOutStanding() {
			return outStanding;
		}
		public long getZxid() {
			return zxid;
		}
		public String getMode() {
			return mode;
		}
		public int getNodeCount() {
			return nodeCount;
		}
		public int getTotalWatches() {
			return totalWatches;
		}
		public Mario_server_info getInfo() {
			return info;
		}
		public void setInfo(Mario_server_info info) {
			this.info = info;
		}
		public Mario_server_state getState() {
			return state;
		}
		public void setState(Mario_server_state state) {
			this.state = state;
		}

		public void update() {
			ruok = cmd("ruok").replace("\n", "");			
			String statText = cmd("srvr");
			if (!"".equals(statText)) {
				Scanner scannerForStat = new Scanner(statText);
				while (scannerForStat.hasNext()) {
					String line = scannerForStat.nextLine();
					if (line.startsWith("Outstanding:")) {
						outStanding = Integer
								.parseInt(getStringValueFromLine(line));
					} else if (line.startsWith("Zxid:")) {
						zxid = Long.parseLong(getStringValueFromLine(line)
								.substring(2), 16);
					} else if (line.startsWith("Mode:")) {
						mode = getStringValueFromLine(line);
					} else if (line.startsWith("Node count:")) {
						nodeCount = Integer.parseInt(getStringValueFromLine(line));
					}
				}
				scannerForStat.close();
			}

			String wchsText = cmd("wchs");
			if (!"".equals(wchsText)) {
				Scanner scannerForWchs = new Scanner(wchsText);
				while (scannerForWchs.hasNext()) {
					String line = scannerForWchs.nextLine();
					if (line.startsWith("Total watches:")) {
						totalWatches = Integer
								.parseInt(getStringValueFromLine(line));
					}
				}
				scannerForWchs.close();
			}
		}

		private String getStringValueFromLine(String line) {
			return line.substring(line.indexOf(":") + 1, line.length())
					.replaceAll(" ", "").trim();
		}

		private class SendThread extends Thread {
			private String cmd;

			public String ret = "";

			public SendThread(String cmd) {
				this.cmd = cmd;
			}

			@Override
			public void run() {
				try {
					ret = FourLetterWordMain.send4LetterWord(info.gethost().trim(), info.getport(), cmd);
				} catch (IOException e) {
					return;
				}
			}

		}

		private String cmd(String cmd) {
			final int waitTimeout = 1;
			SendThread sendThread = new SendThread(cmd);
			sendThread.start();
			try {
				sendThread.join(waitTimeout * 1000);
				return sendThread.ret;
			} catch (InterruptedException e) {
				logger.error("Send " + cmd + " to client " + info.gethost()
						+ ":" + info.getport() + " failed!");
			}
			return "";
		}
    }

}
