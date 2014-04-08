package com.renren.infra.xweb.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.renren.infra.xweb.entity.Test;
import com.renren.infra.xweb.repository.TestMybatisDao;

@Component
@Transactional
public class TestService {

    @Autowired
    private TestMybatisDao testDao;

    /**
     * 创建分页查询.
     * 
     * @param filterParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return Page<Test>
     */
    public Page<Test> getTest(Map<String, Object> filterParams, int pageNumber, int pageSize) {

        List<Test> tests = testDao.find(filterParams, buildRowBounds(pageNumber, pageSize));
        int totalnum = testDao.findTotalNum(filterParams);

        return new PageImpl<Test>(tests, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    /**
     * 查找所有的Test.
     * 
     * @return List<Test>
     */
    public List<Test> getAllTest() {
        return testDao.findAll();
    }

    /**
     * 创建分页请求.
     * 
     * @param pageNumber
     * @param pageSize
     * @return RowBounds
     */
    private RowBounds buildRowBounds(int pageNumber, int pageSize) {
        return new RowBounds((pageNumber - 1) * pageSize, pageSize);
    }

    /**
     * 保存Test.
     * 
     * @param newTest
     */
    public void saveTest(Test newTest) {
        if (newTest.getId() != null) {
            testDao.update(newTest);
        } else {
            testDao.insert(newTest);
        }
    }

    /**
     * 增加一组Test
     * 
     * @param testList
     */
    public void saveBatchTest(List<Test> testList) {
        testDao.insertBatch(testList);
    }

    /**
     * 根据id获取Test
     * 
     * @param id
     * @return Test
     */
    public Test getTest(Long id) {
        return testDao.getTestById(id.intValue());
    }

    /**
     * 根据id删除Test
     * 
     * @param id
     */
    public void deleteTest(Long id) {
        testDao.deleteById(id.intValue());
    }

}
