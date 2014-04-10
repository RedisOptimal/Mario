package com.renren.infra.xweb.web;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.renren.infra.xweb.entity.Mario_zk_info;
import com.renren.infra.xweb.service.Mario_plugin_infoService;
import com.renren.infra.xweb.service.Mario_server_infoService;
import com.renren.infra.xweb.service.Mario_zk_infoService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_zk_info")
public class Mario_zk_infoController {

	@Autowired
	private Mario_zk_infoService service;

	@Autowired
	private Mario_server_infoService serverInfoService;

	@Autowired
	private Mario_plugin_infoService pluginInfoService;
	
	@RequestMapping(value = {"", "/list"})
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<Mario_zk_info> mario_zk_infos = service.getMario_zk_info(
				searchParams, pageNumber, Const.PAGE_SIZE);

		model.addAttribute("mario_zk_infos", mario_zk_infos);
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "mario_zk_info/mario_zk_infoList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("mario_zk_info", new Mario_zk_info());
		model.addAttribute("action", "create");
		return "mario_zk_info/mario_zk_infoForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Mario_zk_info newMario_zk_info,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "mario_zk_info/mario_zk_infoForm";
        }
		if (service.saveNewRecord(newMario_zk_info)) {
			redirectAttributes.addFlashAttribute("alertType", "alert-success");
			redirectAttributes.addFlashAttribute("message", "创建成功");
		} else {
			redirectAttributes.addFlashAttribute("alertType", "alert-danger");
			redirectAttributes.addFlashAttribute("message", "更新失败:名字冲突");
		}
		return "redirect:/mario_zk_info/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("mario_zk_info", service.getMario_zk_info(id));
		model.addAttribute("action", "update");
		return "mario_zk_info/mario_zk_infoForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid Mario_zk_info mario_zk_info,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", "update");
            return "mario_zk_info/mario_zk_infoForm";
        }
		if (service.updateRecord(mario_zk_info)) {
			redirectAttributes.addFlashAttribute("alertType", "alert-success");
			redirectAttributes.addFlashAttribute("message", "更新成功");
		} else {
			redirectAttributes.addFlashAttribute("alertType", "alert-danger");
			redirectAttributes.addFlashAttribute("message", "更新失败:名字冲突");
		}
		return "redirect:/mario_zk_info/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) {
		service.deleteMario_zk_info(id);
		serverInfoService.deleteMario_server_infoByZkid(id);
		pluginInfoService.deleteMario_plugin_infoByZkid(id);
		redirectAttributes.addFlashAttribute("alertType", "alert-success");
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/mario_zk_info";
	}

	@ModelAttribute("preloadMario_zk_info")
	public Mario_zk_info getMario_zk_info(
			@RequestParam(value = "id", required = false) Integer id) {
		if (id != null) {
			return service.getMario_zk_info(id);
		}
		return null;
	}

}
