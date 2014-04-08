
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

import com.renren.infra.xweb.entity.Mario_plugin_info;
import com.renren.infra.xweb.service.Mario_plugin_infoService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_plugin_info")
public class Mario_plugin_infoController {

    @Autowired
    private Mario_plugin_infoService service;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Mario_plugin_info> mario_plugin_infos = service.getMario_plugin_info(searchParams, pageNumber, Const.PAGE_SIZE);

        model.addAttribute("mario_plugin_infos", mario_plugin_infos);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "mario_plugin_info/mario_plugin_infoList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("mario_plugin_info", new Mario_plugin_info());
        model.addAttribute("action", "create");
        return "mario_plugin_info/mario_plugin_infoForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Mario_plugin_info newMario_plugin_info, RedirectAttributes redirectAttributes) {
        service.saveMario_plugin_info(newMario_plugin_info);
        redirectAttributes.addFlashAttribute("message", "创建成功");
        return "redirect:/mario_plugin_info/";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm( @PathVariable("id") Integer id, Model model) {
        model.addAttribute("mario_plugin_info", service.getMario_plugin_info(id));
        model.addAttribute("action", "update");
        return "mario_plugin_info/mario_plugin_infoForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadMario_plugin_info") Mario_plugin_info mario_plugin_info,
            RedirectAttributes redirectAttributes) {
        service.saveMario_plugin_info(mario_plugin_info);
        redirectAttributes.addFlashAttribute("message", "更新成功");
        return "redirect:/mario_plugin_info/";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete( @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        service.deleteMario_plugin_info(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/mario_plugin_info";
    }

    @ModelAttribute("preloadMario_plugin_info")
    public Mario_plugin_info getMario_plugin_info( @RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return service.getMario_plugin_info(id);
        }
        return null;
    }

}
