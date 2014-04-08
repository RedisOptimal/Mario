package com.renren.infra.xweb.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.google.common.collect.Maps;
import com.renren.infra.xweb.data.UserData;
import com.renren.infra.xweb.entity.Role;
import com.renren.infra.xweb.entity.User;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserMybatisDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private UserMybatisDao userDao;

    @Test
    public void getUser() throws Exception {
        User user = userDao.findById(1L);
        assertNotNull("User not found", user);
        assertEquals("admin", user.getLoginName());
    }

    @Test
    public void searchUser() throws Exception {
        Map<String, Object> parameter = Maps.newHashMap();
        parameter.put("name", "admin");
        List<User> result = userDao.find(parameter, new RowBounds());
        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).getLoginName());
    }

    @Test
    public void createAndDeleteUser() throws Exception {
        // create
        int count = countRowsInTable("xweb_user");
        User user = UserData.randomUser();
        userDao.insert(user);
        Long id = user.getId();

        assertEquals(count + 1, countRowsInTable("xweb_user"));
        User result = userDao.findById(id);
        assertEquals(user.getLoginName(), result.getLoginName());

        // delete
        userDao.delete(id);
        assertEquals(count, countRowsInTable("xweb_user"));
        assertNull(userDao.findById(id));
    }

    @Test
    public void testFindByLoginName() {
        User user = userDao.findByLoginName("admin");

        assertNotNull(user);
        assertEquals("Admin", user.getName());
    }

    @Test
    public void testInsertUserRole() {
        User user = new User();
        user.setId(0);

        List<Role> roleList = new ArrayList<Role>();
        Role role = new Role();
        role.setId(1L);
        roleList.add(role);

        Role role2 = new Role();
        role2.setId(2L);
        roleList.add(role2);

        user.setRoleList(roleList);

        int count = countRowsInTable("xweb_user_role");
        userDao.insertUserRole(user);
        int nowcount = countRowsInTable("xweb_user_role");
        Assert.assertEquals(count + 2, nowcount);
    }

    @Test
    public void testDeleteUserRole() {
        User user = new User();
        user.setId(1L);

        int count = countRowsInTable("xweb_user_role");
        userDao.deleteUserRole(user.getId());
        int nowcount = countRowsInTable("xweb_user_role");

        Assert.assertEquals(count - 1, nowcount);

    }

    @Test
    public void testFindUserByRole() {
        List<User> users = userDao.findUserByRoleId(1L);

        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void testFindTotalNum() {
        Map<String, Object> searchMap = new HashMap<String, Object>();

        int total = userDao.findTotalNum(searchMap);
        Assert.assertTrue(total > 0);
        Assert.assertEquals(2, total);

        searchMap.put("name", "admin");
        int searchByNameTotal = userDao.findTotalNum(searchMap);
        Assert.assertTrue(searchByNameTotal > 0);
        Assert.assertEquals(1, searchByNameTotal);

        searchMap.put("loginName", "admin");
        int searchByLoginNameTotal = userDao.findTotalNum(searchMap);
        Assert.assertTrue(searchByLoginNameTotal > 0);
        Assert.assertEquals(1, searchByLoginNameTotal);
    }

}
