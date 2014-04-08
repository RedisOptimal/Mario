package com.renren.infra.xweb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.renren.infra.xweb.entity.Test;

@MyBatisRepository
public interface TestMybatisDao {

    Test getTestById(int id);

    int delete(int id);
    
    int deleteAll();

    @Delete("delete from xweb_test where id =#{id}")
    int deleteById(@Param("id") int id);

    void insert(Test test);
    
    void insertBatch(List<Test> testList);

    void update(Test test);

    List<Test> findAll();

    List<Test> find(Map<String, Object> filterParams, RowBounds bounds);

    int findTotalNum(Map<String, Object> filterParams);

}
