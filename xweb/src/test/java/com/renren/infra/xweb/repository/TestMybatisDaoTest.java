package com.renren.infra.xweb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.google.common.collect.Lists;

@DirtiesContext
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestMybatisDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private TestMybatisDao dao;

    private static List<com.renren.infra.xweb.entity.Test> testList = Lists.newArrayList();

    @BeforeClass
    public static void setUpClass() {
        for (int i = 0; i < 10; i++) {
            com.renren.infra.xweb.entity.Test test = new com.renren.infra.xweb.entity.Test();
            test.setMsg("" + i);
            testList.add(test);
        }
    }

    @Test
    public void testTest() {
        com.renren.infra.xweb.entity.Test test = dao.getTestById(1);

        Assert.assertEquals("test", test.getMsg());
    }

    @Test
    public void testInsert() {
        com.renren.infra.xweb.entity.Test test = new com.renren.infra.xweb.entity.Test();
        test.setMsg("insert new test");

        dao.insert(test);

        Assert.assertEquals(2, dao.findAll().size());

    }

    @Test
    public void testInsertBatch() {

        int beforeInsertCount = countRowsInTable("xweb_test");

        dao.insertBatch(testList);

        int afterInsertCount = countRowsInTable("xweb_test");

        Assert.assertEquals(beforeInsertCount + testList.size(), afterInsertCount);
    }

    @Test
    public void testDelete() {
        int deleteNum = dao.delete(1);

        Assert.assertEquals(1, deleteNum);
    }
    
    @Test
    public void testDeleteAll(){
        int deleteNum = dao.deleteAll();
        
        Assert.assertTrue(deleteNum > 0);
    }

    @Test
    public void testDeleteByID() {
        int deleteNum = dao.deleteById(1);

        Assert.assertEquals(1, deleteNum);
    }

    @Test
    public void testFindTotalNum() {
        Map<String, Object> searchMap = new HashMap<String, Object>();

        int total = dao.findTotalNum(searchMap);
        Assert.assertTrue(total > 0);
        Assert.assertEquals(1, total);

        searchMap.put("msg", "test");
        int searchTotal = dao.findTotalNum(searchMap);
        Assert.assertTrue(searchTotal > 0);
        Assert.assertEquals(1, searchTotal);

        searchMap.put("msg", "tester");
        int searchTestTotal = dao.findTotalNum(searchMap);
        Assert.assertEquals(0, searchTestTotal);
    }

    @Test
    public void testBuildPage() {
        Map<String, Object> filterParams = new HashMap<String, Object>();
        int pageNumber = 1;
        int pageSize = 2;

        List<com.renren.infra.xweb.entity.Test> tests = dao.find(filterParams, new RowBounds(
                (pageNumber - 1) * pageSize, pageSize));
        int totalnum = dao.findTotalNum(filterParams);

        Page<com.renren.infra.xweb.entity.Test> pageTest = new PageImpl<com.renren.infra.xweb.entity.Test>(
                tests, new PageRequest(pageNumber - 1, pageSize), totalnum);

        Assert.assertNotNull(pageTest);
        Assert.assertEquals(1, pageTest.getContent().size());
        Assert.assertEquals(1, totalnum);
    }

}
