package com.renren.infra.xweb.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.renren.infra.xweb.entity.Mario_rule_info;
import com.renren.infra.xweb.entity.Mario_zk_info;
import com.renren.infra.xweb.entity.ShiroUser;
import com.renren.infra.xweb.service.AccountService;
import com.renren.infra.xweb.service.Mario_rule_infoService;
import com.renren.infra.xweb.service.Mario_zk_infoService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/mario_rule_info")
public class Mario_rule_infoController {

	@Autowired
	private Mario_rule_infoService service;
	@Autowired
	private Mario_zk_infoService zkInfoService;
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = { "", "/list" })
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");

		// 在查询条件中增加用户名
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (!user.loginName.equals("admin")) {
			searchParams.put("user_id",
					accountService.findUserByLoginName(user.loginName).getId());
		}

		Page<Mario_rule_info> mario_rule_infos = service.getMario_rule_info(
				searchParams, pageNumber, Const.PAGE_SIZE);
		for (Mario_rule_info mario_rule_info : mario_rule_infos) {
		    mario_rule_info.setCluster_name(zkInfoService.getMario_zk_info(mario_rule_info.getZk_id()).getzk_name());
		}
		model.addAttribute("mario_rule_infos", mario_rule_infos);
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "mario_rule_info/mario_rule_infoList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {

		// 为zk_id下拉菜单绑定数据
		List<Mario_zk_info> mario_zk_infoList = zkInfoService
				.getAllMario_zk_info();
		Page<Mario_zk_info> mario_zk_infos = new PageImpl<Mario_zk_info>(
				mario_zk_infoList,
				new PageRequest(0, mario_zk_infoList.size()),
				mario_zk_infoList.size());
		model.addAttribute("mario_zk_infos", mario_zk_infos);

		model.addAttribute("mario_rule_info", new Mario_rule_info());
		model.addAttribute("action", "create");
		return "mario_rule_info/mario_rule_infoForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Mario_rule_info newMario_rule_info,
			RedirectAttributes redirectAttributes) {

		// 增加user_id字段
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		newMario_rule_info.setuser_id(accountService.findUserByLoginName(
				user.loginName).getId());

		service.saveMario_rule_info(newMario_rule_info);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/mario_rule_info/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Mario_rule_info mario_rule_info = service.getMario_rule_info(id);

		// 为zk_id下拉菜单绑定数据
		Mario_zk_info mario_zk_info = zkInfoService
				.getMario_zk_info(mario_rule_info.getZk_id());
		List<Mario_zk_info> mario_zk_infoList = new ArrayList<Mario_zk_info>();
		mario_zk_infoList.add(mario_zk_info);
		Page<Mario_zk_info> mario_zk_infos = new PageImpl<Mario_zk_info>(
				mario_zk_infoList,
				new PageRequest(0, mario_zk_infoList.size()),
				mario_zk_infoList.size());
		model.addAttribute("mario_zk_infos", mario_zk_infos);

		model.addAttribute("mario_rule_info", mario_rule_info);
		model.addAttribute("action", "update");
		return "mario_rule_info/mario_rule_infoForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute("preloadMario_rule_info") Mario_rule_info mario_rule_info,
			RedirectAttributes redirectAttributes) {

		// 增加user_id字段
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		mario_rule_info.setuser_id(accountService.findUserByLoginName(
				user.loginName).getId());

		service.saveMario_rule_info(mario_rule_info);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/mario_rule_info/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) {
		service.deleteMario_rule_info(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/mario_rule_info";
	}

	@ModelAttribute("preloadMario_rule_info")
	public Mario_rule_info getMario_rule_info(
			@RequestParam(value = "id", required = false) Integer id) {
		if (id != null) {
			return service.getMario_rule_info(id);
		}
		return null;
	}

}
