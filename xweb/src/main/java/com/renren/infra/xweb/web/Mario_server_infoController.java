
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

import com.renren.infra.xweb.entity.Mario_server_info;
import com.renren.infra.xweb.service.Mario_server_infoService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_server_info")
public class Mario_server_infoController {

    @Autowired
    private Mario_server_infoService service;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Mario_server_info> mario_server_infos = service.getMario_server_info(searchParams, pageNumber, Const.PAGE_SIZE);

        model.addAttribute("mario_server_infos", mario_server_infos);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "mario_server_info/mario_server_infoList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("mario_server_info", new Mario_server_info());
        model.addAttribute("action", "create");
        return "mario_server_info/mario_server_infoForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Mario_server_info newMario_server_info, RedirectAttributes redirectAttributes) {
        service.saveMario_server_info(newMario_server_info);
        redirectAttributes.addFlashAttribute("message", "创建成功");
        return "redirect:/mario_server_info/";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm( @PathVariable("id") Integer id, Model model) {
        model.addAttribute("mario_server_info", service.getMario_server_info(id));
        model.addAttribute("action", "update");
        return "mario_server_info/mario_server_infoForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadMario_server_info") Mario_server_info mario_server_info,
            RedirectAttributes redirectAttributes) {
        service.saveMario_server_info(mario_server_info);
        redirectAttributes.addFlashAttribute("message", "更新成功");
        return "redirect:/mario_server_info/";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete( @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        service.deleteMario_server_info(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/mario_server_info";
    }

    @ModelAttribute("preloadMario_server_info")
    public Mario_server_info getMario_server_info( @RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return service.getMario_server_info(id);
        }
        return null;
    }

}
