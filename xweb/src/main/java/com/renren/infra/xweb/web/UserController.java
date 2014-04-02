package com.renren.infra.xweb.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.renren.infra.xweb.entity.Role;
import com.renren.infra.xweb.entity.ShiroUser;
import com.renren.infra.xweb.entity.User;
import com.renren.infra.xweb.service.AccountService;
import com.renren.infra.xweb.util.Const;

@Controller
@RequestMapping(value = "/account/user")
public class UserController {

    private static Map<String, String> allStatus = Maps.newHashMap();

    static {
        allStatus.put("1", "有效");
        allStatus.put("0", "无效");
    }

    @Autowired
    private AccountService accountService;

    @RequiresRoles("admin")
    @RequestMapping(value = "")
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
            Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<User> users = accountService.searchUser(searchParams, pageNumber, Const.PAGE_SIZE);
        for (User user : users) {
            user.setRoleList(accountService.getRoleByUserID(user.getId()));
        }

        model.addAttribute("users", users);
        model.addAttribute("allStatus", allStatus);
        return "account/userList";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        List<Role> roles = accountService.getAllRole();
        model.addAttribute("user", new User());
        model.addAttribute("action", "create");
        model.addAttribute("allRoles", roles);
        model.addAttribute("allStatus", allStatus);
        return "account/userForm";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult result,
            @RequestParam(value = "roleList") List<Long> checkedRoleList, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Role> roles = accountService.getAllRole();
            model.addAttribute("action", "create");
            model.addAttribute("allRoles", roles);
            model.addAttribute("allStatus", allStatus);
            return "account/userForm";
        }

        // bind roleList
        user.getRoleList().clear();
        for (Long roleId : checkedRoleList) {
            Role role = new Role(roleId);
            user.getRoleList().add(role);
        }

        accountService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "创建用户成功");
        return "redirect:/account/user";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        User user = accountService.getUser(id);
        List<Role> roles = accountService.getRoleByUserID(id);
        user.setRoleList(roles);

        model.addAttribute("action", "update");
        model.addAttribute("user", user);
        model.addAttribute("allStatus", allStatus);

        model.addAttribute("roleList", roles);
        model.addAttribute("allRoles", accountService.getAllRole());
        return "account/userForm";
    }

    /**
     * 演示自行绑定表单中的checkBox roleList到对象中.
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("user") User user, BindingResult result,
            @RequestParam(value = "roleList") List<Long> checkedRoleList, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Role> roles = accountService.getRoleByUserID(user.getId());
            user.setRoleList(roles);

            model.addAttribute("action", "update");
            model.addAttribute("allStatus", allStatus);

            model.addAttribute("roleList", roles);
            model.addAttribute("allRoles", accountService.getAllRole());

            return "account/userForm";
        }

        // bind roleList
        user.getRoleList().clear();
        for (Long roleId : checkedRoleList) {
            Role role = new Role(roleId);
            user.getRoleList().add(role);
        }

        accountService.saveUser(user);

        redirectAttributes.addFlashAttribute("message", "保存用户成功");
        return "redirect:/account/user";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (id == null || id == 1L) {
            redirectAttributes.addFlashAttribute("message", "删除用户失败，管理员不能删除");
        } else {
            accountService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "删除用户成功");
        }
        return "redirect:/account/user";
    }

    @RequiresUser
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(Model model) {
        ShiroUser currentUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User user = accountService.getUser(currentUser.getId());

        model.addAttribute("user", user);

        return "account/profile";
    }

    @RequiresUser
    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String saveProfile(User user, RedirectAttributes redirectAttributes) {

        accountService.saveProfile(user);

        updateUserName(user.getName());
        redirectAttributes.addFlashAttribute("message", "修改用户信息成功");

        return "redirect:/account/user/profile";
    }

    /**
     * 更新Shiro中当前用户的用户名.
     */
    private void updateUserName(String userName) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        user.name = userName;
    }

    /**
     * 判断用户是否已经存在
     * 
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @RequestMapping(value = "checkLoginName")
    @ResponseBody
    public String checkLoginName(@RequestParam("oldLoginName") String oldLoginName,
            @RequestParam("loginName") String loginName) {
        if (loginName.equals(oldLoginName)) {
            return "true";
        } else if (accountService.findUserByLoginName(loginName) == null) {
            return "true";
        }

        return "false";
    }

    /**
     * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
     * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("user", accountService.getUser(id));
        }
        model.addAttribute("user", new User());
    }

    /**
     * 不自动绑定对象中的roleList属性，另行处理。
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("roleList");
    }
}
