
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

import com.renren.infra.xweb.entity.Mario_zk_info;
import com.renren.infra.xweb.service.Mario_zk_infoService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_zk_info")
public class Mario_zk_infoController {

    @Autowired
    private Mario_zk_infoService service;

    @RequestMapping(value = { "", "/list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Mario_zk_info> mario_zk_infos = service.getMario_zk_info(searchParams, pageNumber, Const.PAGE_SIZE);

        model.addAttribute("mario_zk_infos", mario_zk_infos);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "mario_zk_info/mario_zk_infoList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("mario_zk_info", new Mario_zk_info());
        model.addAttribute("action", "create");
        return "mario_zk_info/mario_zk_infoForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Mario_zk_info newMario_zk_info, RedirectAttributes redirectAttributes) {
        if (service.saveMario_zk_info(newMario_zk_info)) {
        	redirectAttributes.addFlashAttribute("alertType", "alert-success");
        	redirectAttributes.addFlashAttribute("message", "创建成功");        	
        } else {
        	redirectAttributes.addFlashAttribute("alertType", "alert-danger");
    		redirectAttributes.addFlashAttribute("message", "更新失败:名字冲突");        	
        }
        return "redirect:/mario_zk_info/";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm( @PathVariable("id") Integer id, Model model) {
        model.addAttribute("mario_zk_info", service.getMario_zk_info(id));
        model.addAttribute("action", "update");
        return "mario_zk_info/mario_zk_infoForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadMario_zk_info") Mario_zk_info mario_zk_info,
            RedirectAttributes redirectAttributes) {
    	if (mario_zk_info.getzk_name() == null || mario_zk_info.getzk_name().trim().equals("")) {
            redirectAttributes.addFlashAttribute("alertType", "alert-danger");
    		redirectAttributes.addFlashAttribute("message", "更新失败:请填写ZooKeeper名字");
    		return "redirect:/mario_zk_info/";
    	}
    	if (mario_zk_info.getsession_timeout() == null) {
            redirectAttributes.addFlashAttribute("alertType", "alert-danger");
    		redirectAttributes.addFlashAttribute("message", "更新失败:请填写ZooKeeper Session Timeout");
    		return "redirect:/mario_zk_info/";
    	}
    	if (service.saveMario_zk_info(mario_zk_info)) {
    		redirectAttributes.addFlashAttribute("alertType", "alert-success");
    		redirectAttributes.addFlashAttribute("message", "更新成功");
    	} else {
        	redirectAttributes.addFlashAttribute("alertType", "alert-danger");
    		redirectAttributes.addFlashAttribute("message", "更新失败:名字冲突");      		
    	}
        return "redirect:/mario_zk_info/";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete( @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        // TODO 删除其他表中的信息
    	service.deleteMario_zk_info(id);
        redirectAttributes.addFlashAttribute("alertType", "alert-success");
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/mario_zk_info";
    }

    @ModelAttribute("preloadMario_zk_info")
    public Mario_zk_info getMario_zk_info( @RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return service.getMario_zk_info(id);
        }
        return null;
    }

}
