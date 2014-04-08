
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
			
        Iterator<Mario_node_state> it = mario_node_states.iterator();
			while(it.hasNext()) {
				Mario_node_state mario_node_state = it.next();
				mario_node_state.setMtimeString(new Date(mario_node_state.getmtime()).toString());
				mario_node_state.setCtimeString(new Date(mario_node_state.getctime()).toString());
				
			}
			
        model.addAttribute("mario_node_states", mario_node_states);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "mario_node_state/mario_node_stateList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("mario_node_state", new Mario_node_state());
        model.addAttribute("action", "create");
        return "mario_node_state/mario_node_stateForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Mario_node_state newMario_node_state, RedirectAttributes redirectAttributes) {
        service.saveMario_node_state(newMario_node_state);
        redirectAttributes.addFlashAttribute("message", "创建成功");
        return "redirect:/mario_node_state/";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm( @PathVariable("id") Integer id, Model model) {
    	
    	Mario_node_state mario_node_state = service.getMario_node_state(id);
		mario_node_state.setMtimeString(new Date(mario_node_state.getmtime()).toString());
		mario_node_state.setCtimeString(new Date(mario_node_state.getctime()).toString());
		
        model.addAttribute("mario_node_state", mario_node_state);
        model.addAttribute("action", "update");
        return "mario_node_state/mario_node_stateForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadMario_node_state") Mario_node_state mario_node_state,
            RedirectAttributes redirectAttributes) {
        service.saveMario_node_state(mario_node_state);
        redirectAttributes.addFlashAttribute("message", "更新成功");
        return "redirect:/mario_node_state/";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete( @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        service.deleteMario_node_state(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/mario_node_state";
    }

    @ModelAttribute("preloadMario_node_state")
    public Mario_node_state getMario_node_state( @RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return service.getMario_node_state(id);
        }
        return null;
    }

}
