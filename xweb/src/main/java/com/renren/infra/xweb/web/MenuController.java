package com.renren.infra.xweb.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Lists;
import com.renren.infra.xweb.entity.Menu;
import com.renren.infra.xweb.entity.Role;
import com.renren.infra.xweb.entity.ShiroUser;
import com.renren.infra.xweb.service.AccountService;

@Controller
@RequestMapping("/account/menu")
public class MenuController {

    @Autowired
    private AccountService accountService;

    private static Map<String, String> allShows = new HashMap<String, String>();
    static {
        allShows.put("1", "显示");
        allShows.put("0", "未显示");
    }

    @RequiresRoles(value = "admin")
    @RequestMapping(value = { "", "list" })
    public String list(Model model, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

        List<Menu> sortedMenus = Lists.newArrayList();
        List<Menu> allMenus = accountService.searchMenu(searchParams);
        Menu.sortList(sortedMenus, allMenus, 1L);

        model.addAttribute("menus", sortedMenus);
        model.addAttribute("allShows", allShows);
        return "account/menuList";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        Menu topMenu = accountService.getTopMenu();
        Menu initMenu = new Menu();
        initMenu.setParent(topMenu);

        model.addAttribute("menu", initMenu);
        model.addAttribute("allShows", allShows);
        model.addAttribute("action", "create");

        return "account/menuForm";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Menu menu, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Menu topMenu = accountService.getTopMenu();
            menu.setParent(topMenu);

            model.addAttribute("menu", menu);
            model.addAttribute("allShows", allShows);
            model.addAttribute("action", "create");

            return "account/menuForm";
        }
        generateMenuParentIds(menu);
        accountService.saveMenu(menu);
        redirectAttributes.addFlashAttribute("message", "创建菜单成功");

        return "redirect:/account/menu";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Menu menu = accountService.getMenu(id);
        menu.setParent(accountService.getMenu(menu.getParentId()));

        model.addAttribute("menu", menu);
        model.addAttribute("allShows", allShows);
        model.addAttribute("action", "update");

        return "account/menuForm";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid Menu menu, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            menu.setParent(accountService.getMenu(menu.getParentId()));

            model.addAttribute("menu", menu);
            model.addAttribute("allShows", allShows);
            model.addAttribute("action", "update");

            return "account/menuForm";
        }

        generateMenuParentIds(menu);
        accountService.saveMenu(menu);

        resetUserMenu();
        redirectAttributes.addFlashAttribute("message", "保存菜单成功");

        return "redirect:/account/menu";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (id == 0) {//顶级菜单
            redirectAttributes.addFlashAttribute("message", "删除菜单失败，顶级菜单不能删除");
            redirectAttributes.addFlashAttribute("success", false);
        }

        List<Role> roles = accountService.getRoleByMenuID(id);
        if (roles == null || roles.size() == 0) {
            accountService.deleteMenu(id);
            redirectAttributes.addFlashAttribute("message", "删除菜单成功");
            redirectAttributes.addFlashAttribute("success", true);
        } else {
            redirectAttributes.addFlashAttribute("message", "删除菜单失败，请先删除该菜单使用的角色，再删除菜单");
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/account/menu";
    }

    @RequestMapping("checkMenuName")
    @ResponseBody
    public String checkMenuName(@RequestParam("oldMenuName") String oldMenuName,
            @RequestParam("menuName") String menuName) {
        if (menuName.equals(oldMenuName)) {
            return "true";
        } else if (accountService.findByMenuName(menuName) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 生成对应的ParentIds，取得parentId对应Menu，封装ParentIds
     * 
     * @param menu
     */
    private void generateMenuParentIds(Menu menu) {
        Menu parentMenu = accountService.getMenu(menu.getParentId());
        String parentIds = parentMenu.getParentIds() + parentMenu.getId() + ",";
        menu.setParentIds(parentIds);//设置ParentIds
    }

    /**
     * 重置User的Menu信息
     */
    private void resetUserMenu() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

        Session session = currentUser.getSession();
        List<Menu> menus = accountService.findMenuByUserID(user.getId());
        session.setAttribute("menuList", menus);
    }
}
