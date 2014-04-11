
package com.renren.infra.xweb.web;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.renren.infra.xweb.entity.Mario_node_state;
import com.renren.infra.xweb.service.Mario_node_stateService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_node_state")
public class Mario_node_stateController {

    @Autowired
    private Mario_node_stateService service;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Mario_node_state> mario_node_states = service.getMario_node_state(searchParams, pageNumber, Const.PAGE_SIZE);
			
        model.addAttribute("mario_node_states", mario_node_states);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "mario_node_state/mario_node_stateList";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm( @PathVariable("id") Integer id, Model model) {
    	// TODO 跳转节点展示
//    	Mario_node_state mario_node_state = service.getMario_node_state(id);
//		
//        model.addAttribute("mario_node_state", mario_node_state);
        model.addAttribute("action", "update");
        return "mario_node_state/mario_node_stateForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid Mario_node_state mario_node_state,
            RedirectAttributes redirectAttributes) {
//        service.saveMario_node_state(mario_node_state);
        redirectAttributes.addFlashAttribute("message", "更新成功");
        return "redirect:/mario_node_state/";
    }

}
