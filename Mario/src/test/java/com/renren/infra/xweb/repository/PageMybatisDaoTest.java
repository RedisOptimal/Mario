package com.renren.infra.xweb.repository;

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

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml"})
public class PageMybatisDaoTest extends SpringTransactionalTestCase {
    
    @Autowired
    private TestMybatisDao testDao;
    
    @Test
    public void testPageFindAll(){
        int offset = 0;
        int limit = 2;
        RowBounds rowBounds = new RowBounds(offset, limit);
        
        List<com.renren.infra.xweb.entity.Test> tests = testDao.find(new HashMap<String, Object>(), rowBounds);
        Assert.assertNotNull(tests);
        Assert.assertEquals(1, tests.size());
    }
    
    @Test
    public void testFindAllCount(){
        Map<String, Object> params = new HashMap<String, Object>();
        int count = testDao.findTotalNum(params);
    
        Assert.assertTrue(count > 0);
    }
    
}
