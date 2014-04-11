
package com.renren.infra.xweb.web;

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

import com.renren.infra.xweb.entity.Mario_server_state;
import com.renren.infra.xweb.service.Mario_server_stateService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_server_state")
public class Mario_server_stateController {

    @Autowired
    private Mario_server_stateService service;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Mario_server_state> mario_server_states = service.getMario_server_state(searchParams, pageNumber, Const.PAGE_SIZE);

        model.addAttribute("mario_server_states", mario_server_states);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "mario_server_state/mario_server_stateList";
    }

}
