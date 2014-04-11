package com.renren.infra.xweb.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.renren.infra.xweb.entity.Menu;
import com.renren.infra.xweb.entity.Role;
import com.renren.infra.xweb.entity.ShiroUser;
import com.renren.infra.xweb.entity.User;
import com.renren.infra.xweb.service.AccountService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping("/account/role")
public class RoleController {

    @Autowired
    private AccountService accountService;

    @RequiresRoles(value = "admin")
    @RequestMapping(value = { "", "list" })
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

        Page<Role> roles = accountService.searchRoles(searchParams, pageNumber, Const.PAGE_SIZE);

        model.addAttribute("roles", roles);
        model.addAttribute("searchParams",
                Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        return "account/roleList";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        List<Menu> menus = accountService.getAllMenu();
        model.addAttribute("role", new Role());
        model.addAttribute("action", "create");
        model.addAttribute("allMenus", menus);
        return "account/roleForm";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Role role, BindingResult result,
            @RequestParam(value = "menuIds", defaultValue = "") String menuIds, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Menu> menus = accountService.getAllMenu();

            model.addAttribute("allMenus", menus);
            model.addAttribute("action", "create");
            return "account/roleForm";
        }

        //add menu tree list
        role.getMenuList().clear();
        String[] ids = StringUtils.splitByWholeSeparator(menuIds, ",");
        for (String id : ids) {
            Menu menu = new Menu(Long.valueOf(id));
            role.getMenuList().add(menu);
        }

        accountService.saveRole(role);

        resetUserMenu();
        redirectAttributes.addFlashAttribute("message", "创建角色成功");
        return "redirect:/account/role";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Role role = accountService.getRole(id);
        role.setMenuList(accountService.getMenuByRoleID(role.getId()));

        List<Menu> menus = accountService.getAllMenu();

        model.addAttribute("role", role);
        model.addAttribute("action", "update");
        model.addAttribute("allMenus", menus);
        return "account/roleForm";

    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid Role role, BindingResult result,
            @RequestParam(value = "menuIds", defaultValue = "") String menuIds, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            role.setMenuList(accountService.getMenuByRoleID(role.getId()));

            List<Menu> menus = accountService.getAllMenu();

            model.addAttribute("role", role);
            model.addAttribute("action", "update");
            model.addAttribute("allMenus", menus);
            return "account/roleForm";
        }

        //add menu tree list
        role.getMenuList().clear();
        String[] ids = StringUtils.splitByWholeSeparator(menuIds, ",");
        for (String id : ids) {
            Menu menu = new Menu(Long.valueOf(id));
            role.getMenuList().add(menu);
        }

        accountService.saveRole(role);

        resetUserMenu();
        redirectAttributes.addFlashAttribute("message", "保存角色成功");
        return "redirect:/account/role";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (id == null || id == 1L) {
            redirectAttributes.addFlashAttribute("message", "删除角色失败，角色Admin不能删除");
        } else {
            List<User> users = accountService.findUserByRoleId(id);
            if (users == null || users.size() == 0) {
                accountService.deleteRole(id);
                resetUserMenu();
                redirectAttributes.addFlashAttribute("message", "删除角色成功");
            } else {
                redirectAttributes.addFlashAttribute("message", "删除角色失败，请先删除该角色下的用户，再删除该角色");
            }
        }
        return "redirect:/account/role";
    }

    @RequiresRoles("admin")
    @RequestMapping("checkRoleName")
    @ResponseBody
    public String checkRoleName(@RequestParam("oldRoleName") String oldRoleName,
            @RequestParam("roleName") String roleName) {
        if (roleName.equals(oldRoleName)) {
            return "true";
        } else if (accountService.findRoleByName(roleName) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 预加载Role信息
     * 
     * @param id
     * @param model
     */
    @ModelAttribute
    public void getRole(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("role", accountService.getRole(id));
        }
        model.addAttribute("role", new Role());
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
