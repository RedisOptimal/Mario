
package com.renren.infra.xweb.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.renren.infra.xweb.entity.Mario_node_state;
import com.renren.infra.xweb.entity.Mario_zk_info;
import com.renren.infra.xweb.service.Mario_node_stateService;
import com.renren.infra.xweb.service.Mario_zk_infoService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_node_state")
public class Mario_node_stateController {

	@Autowired
	private Mario_zk_infoService zkService;
    @Autowired
    private Mario_node_stateService service;
    @Autowired
    private Mario_zk_infoService zkInfoService;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

    	List<Mario_zk_info> mario_zk_infos = zkService.getAllMario_zk_info();
    	model.addAttribute("mario_zk_infos", mario_zk_infos);
    	
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Mario_node_state> mario_node_states = service.getMario_node_state(searchParams, pageNumber, Const.PAGE_SIZE);
        for (Mario_node_state mario_node_state : mario_node_states) {
            mario_node_state.setCluster_name(zkInfoService.getMario_zk_info(mario_node_state.getzk_id()).getzk_name());
        }

        model.addAttribute("mario_node_states", mario_node_states);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "mario_node_state/mario_node_stateList";
    }
}
