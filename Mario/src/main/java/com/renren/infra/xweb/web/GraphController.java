package com.renren.infra.xweb.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;

import com.renren.infra.xweb.entity.Mario_server_info;
import com.renren.infra.xweb.entity.Mario_server_state;
import com.renren.infra.xweb.service.Mario_server_infoService;
import com.renren.infra.xweb.service.Mario_server_stateService;

@Controller
@RequestMapping("/graph")
public class GraphController {

	@Autowired
	Mario_server_stateService service;
	@Autowired
	Mario_server_infoService serverInfoService;
	
	@RequestMapping(value = "/server_state", method = RequestMethod.GET)
	public String graphServer(Model model) {
        return "graph/server_state";
	}

	@RequestMapping(value = "/zk_state", method = RequestMethod.GET)
	public String graphZk(Model model) {
        return "graph/zk_state";
	}
	
    @RequestMapping(value = "/server_state_data", method = RequestMethod.GET)
    public @ResponseBody String serverData(
    		@RequestParam(value="server_id") int serverId, 
    		@RequestParam(value="date") String date) {

    	JsonMapper mapper = new JsonMapper();
    	Map<String, String> data = new HashMap<String, String>();
      
    	Long start_time_stamp = 0L;
    	Long end_time_stamp = 0L;
    	
    	try {
			start_time_stamp = new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
			end_time_stamp = start_time_stamp + 86400000L;
		} catch (ParseException e) {
		}
    	
		List<Mario_server_state> mario_server_stateList = service
				.getMario_server_state(serverId, start_time_stamp, end_time_stamp);

    	List<Object> timeStampList = new ArrayList<Object>();
    	List<Object> nodeCountList = new ArrayList<Object>();
    	List<Object> totalWatchesList = new ArrayList<Object>();
    	List<Object> clientNumberList = new ArrayList<Object>();
    	
    	//采样密度，每天12*24个点
		int mod = mario_server_stateList.size() / (12 * 24);
		mod = mod == 0 ? 1 : mod;
		
		for(int i = 0; i < mario_server_stateList.size(); i ++ ) {
			if(i % mod == 0) {
				timeStampList.add(mario_server_stateList.get(i).getTime_stamp("HH:mm"));
				nodeCountList.add(mario_server_stateList.get(i).getNode_count());
				totalWatchesList.add(mario_server_stateList.get(i).getTotal_watches());
				clientNumberList.add(mario_server_stateList.get(i).getClient_number());
			}
		}
		
		Map<String, Object> send = new HashMap<String, Object>();
		
    	send.put("timeStamp", timeStampList);
    	send.put("nodeCount", nodeCountList);
    	send.put("totalWatches", totalWatchesList);
    	send.put("clientNumber", clientNumberList);
    	
    	data.put("send", mapper.toJson(send));
    	
    	return mapper.toJson(data);
    }
    
    @RequestMapping(value = "/zk_state_data", method = RequestMethod.GET)
    public @ResponseBody String zkData(
    		@RequestParam(value="zk_id") int zkId, 
    		@RequestParam(value="date") String date) {

    	JsonMapper mapper = new JsonMapper();
    	Map<String, String> data = new HashMap<String, String>();
      
    	Long start_time_stamp = 0L;
    	Long end_time_stamp = 0L;
    	
    	try {
			start_time_stamp = new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
			end_time_stamp = start_time_stamp + 86400000L;
		} catch (ParseException e) {
		}
    	
    	List<Mario_server_state> zk_state_List = new ArrayList<Mario_server_state>();
    	
    	List<Mario_server_info> mario_server_infos = serverInfoService.getMario_server_infoByZkid(zkId);
    	for(Mario_server_info mario_server_info : mario_server_infos) {
    		int serverId = mario_server_info.getid();
			List<Mario_server_state> mario_server_stateList = service
					.getMario_server_state(serverId, start_time_stamp, end_time_stamp);
			for(int i = 0; i < mario_server_stateList.size(); i ++) {
				if(i >= zk_state_List.size()) {
					zk_state_List.add(mario_server_stateList.get(i));
				} else {
					Mario_server_state server_state = zk_state_List.get(i);
					server_state.setclient_number(server_state.getClient_number() + mario_server_stateList.get(i).getClient_number());
					server_state.settotal_watches(server_state.getTotal_watches() + mario_server_stateList.get(i).getTotal_watches());
				}
			}
    	}

    	List<Object> timeStampList = new ArrayList<Object>();
    	List<Object> nodeCountList = new ArrayList<Object>();
    	List<Object> totalWatchesList = new ArrayList<Object>();
    	List<Object> clientNumberList = new ArrayList<Object>();
    	
    	//采样密度，每天12*24个点
		int mod = zk_state_List.size() / (12 * 24);
		mod = mod == 0 ? 1 : mod;
		
		for(int i = 0; i < zk_state_List.size(); i ++ ) {
			if(i % mod == 0) {
				timeStampList.add(zk_state_List.get(i).getTime_stamp("HH:mm"));
				nodeCountList.add(zk_state_List.get(i).getNode_count());
				totalWatchesList.add(zk_state_List.get(i).getTotal_watches());
				clientNumberList.add(zk_state_List.get(i).getClient_number());
			}
		}
		
		Map<String, Object> send = new HashMap<String, Object>();
		
    	send.put("timeStamp", timeStampList);
    	send.put("nodeCount", nodeCountList);
    	send.put("totalWatches", totalWatchesList);
    	send.put("clientNumber", clientNumberList);
    	
    	data.put("send", mapper.toJson(send));
    	
    	return mapper.toJson(data);
    }
}
