
package com.renren.infra.xweb.repository;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.renren.infra.xweb.entity.Menu;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class MenuMybatisDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private MenuMybatisDao dao;

    @Test
    public void testFindAll() {
        List<Menu> menus = dao.findAll();

        Assert.assertNotNull(menus);
        Assert.assertEquals(3, menus.size());
    }

    @Test
    public void testFindByRoleId() {
        List<Menu> menus = dao.findMenuByRoleID(1L);

        Assert.assertNotNull(menus);
        Assert.assertEquals(3, menus.size());
    }

    @Test
    public void testFindById() {
        Menu menu = dao.findById(1L);

        Assert.assertNotNull(menu);
        Assert.assertEquals(0L, menu.getParentId());

    }
    
    @Test
    public void testDelete(){
        int beforeDeleteCount = countRowsInTable("xweb_menu");
        dao.delete(2L);
        
        int afterDeleteCount = countRowsInTable("xweb_menu");

        System.out.println("before : " + beforeDeleteCount);
        System.out.println("after : " + afterDeleteCount);
        
        Assert.assertTrue(beforeDeleteCount > afterDeleteCount);
    }

    @Test
    public void testeDeleteChildMenu(){
        int beforeDeleteCount = countRowsInTable("xweb_menu");
        dao.deleteChildMenu("%" + 1 + "%");
        
        int afterDeleteCount = countRowsInTable("xweb_menu");

        System.out.println("before : " + beforeDeleteCount);
        System.out.println("after : " + afterDeleteCount);
        
        Assert.assertTrue(beforeDeleteCount > afterDeleteCount);
        
    }
    
}
