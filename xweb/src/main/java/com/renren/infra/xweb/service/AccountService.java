package com.renren.infra.xweb.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.renren.infra.xweb.entity.Menu;
import com.renren.infra.xweb.entity.Role;
import com.renren.infra.xweb.entity.ShiroUser;
import com.renren.infra.xweb.entity.User;
import com.renren.infra.xweb.repository.MenuMybatisDao;
import com.renren.infra.xweb.repository.RoleMybatisDao;
import com.renren.infra.xweb.repository.UserMybatisDao;

/**
 * 用户权限菜单业务逻辑类
 * 
 * @author Administrator
 * @create-time 2013-9-27
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@Component
@Transactional(readOnly = true)
public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class);

    public static final String HASH_ALGORITHM = "SHA-1";

    public static final int HASH_INTERATIONS = 1024;

    private static final int SALT_SIZE = 8;

    @Autowired
    private UserMybatisDao userDao;

    @Autowired
    private RoleMybatisDao roleDao;

    @Autowired
    private MenuMybatisDao menuDao;

    public User getUser(Long id) {
        return userDao.findById(id);
    }

    public Page<User> searchUser(Map<String, Object> parameters, int pageNumber, int pageSize) {
        List<User> users = userDao.find(parameters, new RowBounds((pageNumber - 1) * pageSize,
                pageSize));
        int totalnum = userDao.findTotalNum(parameters);
        return new PageImpl<User>(users, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user) {
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            entryptPassword(user);
        }
        if (user.getId() == 0) {
            userDao.insert(user);
            userDao.insertUserRole(user);
        } else {
            userDao.deleteUserRole(user.getId());
            userDao.insertUserRole(user);
            userDao.update(user);
        }
    }

    @Transactional(readOnly = false)
    public void deleteUser(Long id) {
        if (isSupervisor(id)) {
            logger.warn("操作员" + getCurrentUserName() + "尝试删除超级管理员用户");
            throw new ServiceException("不能删除超级管理员用户");
        }
        userDao.deleteUserRole(id);
        userDao.delete(id);
    }

    public User findUserByLoginName(String username) {
        return userDao.findByLoginName(username);
    }

    /**
     * 判断是否超级管理员.
     */
    private boolean isSupervisor(Long id) {
        return id == 1;
    }

    /**
     * 取出Shiro中的当前用户LoginName.
     */
    private String getCurrentUserName() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user.loginName;
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt,
                HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

    public List<Role> getAllRole() {
        return roleDao.findAll();
    }

    public List<Role> getRoleByUserID(Long id) {
        return roleDao.findRoleByUserID(id);
    }

    public Role findRoleByName(String roleName) {
        return roleDao.findRoleByName(roleName);
    }

    public Page<Role> searchRoles(Map<String, Object> parameters, int pageNumber, int pageSize) {
        List<Role> roles = roleDao.find(parameters, new RowBounds((pageNumber - 1) * pageSize,
                pageSize));
        int totalnum = roleDao.findTotalNum(parameters);
        return new PageImpl<Role>(roles, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    @Transactional(readOnly = false)
    public void saveRole(Role role) {
        if (role.getId() == 0) {
            roleDao.insert(role);
            insertRoleMenu(role);
        } else {
            roleDao.deleteRoleMenu(role.getId());
            insertRoleMenu(role);
            roleDao.update(role);
        }
    }

    private void insertRoleMenu(Role role) {
        if (StringUtils.isNotBlank(role.getMenuIds())) {
            roleDao.insertRoleMenu(role);
        }
    }

    @Transactional(readOnly = false)
    public void deleteRole(Long id) {
        roleDao.deleteRoleMenu(id);
        roleDao.delete(id);
    }

    public Role getRole(Long id) {
        return roleDao.findRoleById(id);
    }

    public List<Menu> getAllMenu() {
        return menuDao.findAll();
    }

    public List<Menu> getMenuByRoleID(long id) {
        return menuDao.findMenuByRoleID(id);
    }

    public List<User> findUserByRoleId(Long id) {
        return userDao.findUserByRoleId(id);
    }

    public List<Menu> searchMenu(Map<String, Object> parameters) {
        return menuDao.find(parameters);
    }

    public Menu findByMenuName(String menuName) {
        return menuDao.findMenuByName(menuName);
    }

    public List<Role> getRoleByMenuID(Long id) {
        return roleDao.findRoleByMenuID(id);
    }

    @Transactional(readOnly = false)
    public void saveMenu(Menu menu) {
        if (menu.getId() == 0) {
            menuDao.insert(menu);
        } else {
            menuDao.update(menu);
        }
    }

    @Transactional(readOnly = false)
    public void deleteMenu(Long id) {
        menuDao.delete(id);
        menuDao.deleteChildMenu("%" + id + "%");
    }

    public Menu getTopMenu() {
        return menuDao.findById(1);//id为1的为顶级菜单
    }

    public Menu getMenu(Long id) {
        return menuDao.findById(id);
    }

    public List<Menu> findMenuByUserID(long id) {
        return menuDao.findMenuByUserID(id);
    }

    @Transactional(readOnly = false)
    public void saveProfile(User user) {
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            entryptPassword(user);
        }
        userDao.updateProfile(user);
    }

    /**
     * 根据工号查询用户
     * @param staffNo
     * @return
     */
    public User findUserByStaffNo(String staffNo) {
        return userDao.findByStaffNo(staffNo);
    }

}
