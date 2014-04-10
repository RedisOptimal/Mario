package com.renren.infra.xweb.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import com.renren.infra.xweb.entity.Mario_plugin_info;
import com.renren.infra.xweb.entity.Mario_server_info;
import com.renren.infra.xweb.entity.Mario_zk_info;
import com.renren.infra.xweb.service.Mario_plugin_infoService;
import com.renren.infra.xweb.service.Mario_zk_infoService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_plugin_info")
public class Mario_plugin_infoController {

	@Autowired
	private Mario_plugin_infoService service;
	@Autowired
	private Mario_zk_infoService zkInfoService;

	@RequestMapping(value = {"", "/list"})
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Page<Mario_plugin_info> mario_plugin_infos = service
				.getMario_plugin_info(searchParams, pageNumber, Const.PAGE_SIZE);

		model.addAttribute("mario_plugin_infos", mario_plugin_infos);
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "mario_plugin_info/mario_plugin_infoList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		List<Mario_zk_info> mario_zk_infoList = zkInfoService
				.getAllMario_zk_info();
		Page<Mario_zk_info> mario_zk_infos = new PageImpl<Mario_zk_info>(
				mario_zk_infoList,
				new PageRequest(0, mario_zk_infoList.size()),
				mario_zk_infoList.size());
		model.addAttribute("mario_zk_infos", mario_zk_infos);

		model.addAttribute("mario_plugin_info", new Mario_plugin_info());
		model.addAttribute("action", "create");
		return "mario_plugin_info/mario_plugin_infoForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Mario_plugin_info newMario_plugin_info,
			BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("action", "create");
			List<Mario_zk_info> mario_zk_infoList = zkInfoService
					.getAllMario_zk_info();
			Page<Mario_zk_info> mario_zk_infos = new PageImpl<Mario_zk_info>(
					mario_zk_infoList,
					new PageRequest(0, mario_zk_infoList.size()),
					mario_zk_infoList.size());
			model.addAttribute("mario_zk_infos", mario_zk_infos);
			return "mario_plugin_info/mario_plugin_infoForm";
		}
		if (zkInfoService.getMario_zk_info(newMario_plugin_info.getzk_id()) == null) {
			redirectAttributes.addFlashAttribute("alertType", "alert-danger");
			redirectAttributes.addFlashAttribute("message", "集群已经不存在");			
		} else {
			service.saveMario_plugin_info(newMario_plugin_info);
			redirectAttributes.addFlashAttribute("alertType", "alert-success");
			redirectAttributes.addFlashAttribute("message", "创建成功");
		}
		return "redirect:/mario_plugin_info/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Mario_plugin_info mario_plugin_info = service.getMario_plugin_info(id);
		model.addAttribute("mario_plugin_info",
				mario_plugin_info);
		
		Mario_zk_info mario_zk_info = zkInfoService.getMario_zk_info(mario_plugin_info.getzk_id());
		List<Mario_zk_info> mario_zk_infoList = new ArrayList<Mario_zk_info>();
		mario_zk_infoList.add(mario_zk_info);
		Page<Mario_zk_info> mario_zk_infos = new PageImpl<Mario_zk_info>(
				mario_zk_infoList,
				new PageRequest(0, mario_zk_infoList.size()),
				mario_zk_infoList.size());
		model.addAttribute("mario_zk_infos", mario_zk_infos);
		
		model.addAttribute("action", "update");

		return "mario_plugin_info/mario_plugin_infoForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(
			@Valid Mario_plugin_info mario_plugin_info,
			BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			
			Mario_zk_info mario_zk_info = zkInfoService.getMario_zk_info(mario_plugin_info.getzk_id());
			List<Mario_zk_info> mario_zk_infoList = new ArrayList<Mario_zk_info>();
			mario_zk_infoList.add(mario_zk_info);
			Page<Mario_zk_info> mario_zk_infos = new PageImpl<Mario_zk_info>(
					mario_zk_infoList,
					new PageRequest(0, mario_zk_infoList.size()),
					mario_zk_infoList.size());
			model.addAttribute("mario_zk_infos", mario_zk_infos);
			
			model.addAttribute("action", "update");
			return "mario_plugin_info/mario_plugin_infoForm";
		}
		if (zkInfoService.getMario_zk_info(mario_plugin_info.getzk_id()) == null) {
			redirectAttributes.addFlashAttribute("alertType", "alert-danger");
			redirectAttributes.addFlashAttribute("message", "集群已经不存在");			
		} else {
			service.saveMario_plugin_info(mario_plugin_info);
			redirectAttributes.addFlashAttribute("alertType", "alert-success");
			redirectAttributes.addFlashAttribute("message", "修改成功");
		}
		return "redirect:/mario_plugin_info/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) {
		service.deleteMario_plugin_info(id);
		redirectAttributes.addFlashAttribute("alertType", "alert-success");
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/mario_plugin_info";
	}

	@ModelAttribute("preloadMario_plugin_info")
	public Mario_plugin_info getMario_plugin_info(
			@RequestParam(value = "id", required = false) Integer id) {
		if (id != null) {
			return service.getMario_plugin_info(id);
		}
		return null;
	}

}
