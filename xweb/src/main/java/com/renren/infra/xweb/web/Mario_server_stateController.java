
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

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("mario_server_state", new Mario_server_state());
        model.addAttribute("action", "create");
        return "mario_server_state/mario_server_stateForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Mario_server_state newMario_server_state, RedirectAttributes redirectAttributes) {
        service.saveMario_server_state(newMario_server_state);
        redirectAttributes.addFlashAttribute("message", "创建成功");
        return "redirect:/mario_server_state/";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm( @PathVariable("id") Integer id, Model model) {
        model.addAttribute("mario_server_state", service.getMario_server_state(id));
        model.addAttribute("action", "update");
        return "mario_server_state/mario_server_stateForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadMario_server_state") Mario_server_state mario_server_state,
            RedirectAttributes redirectAttributes) {
        service.saveMario_server_state(mario_server_state);
        redirectAttributes.addFlashAttribute("message", "更新成功");
        return "redirect:/mario_server_state/";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete( @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        service.deleteMario_server_state(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/mario_server_state";
    }

    @ModelAttribute("preloadMario_server_state")
    public Mario_server_state getMario_server_state( @RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return service.getMario_server_state(id);
        }
        return null;
    }

}
