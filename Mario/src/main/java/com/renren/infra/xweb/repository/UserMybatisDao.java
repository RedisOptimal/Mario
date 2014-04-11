package com.renren.infra.xweb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.renren.infra.xweb.entity.User;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface UserMybatisDao {

    User findById(long id);

    User findByLoginName(String loginName);

    User findByStaffNo(String staffNo);

    List<User> find(Map<String, Object> parameters, RowBounds bounds);

    int findTotalNum(Map<String, Object> parameters);

    void insert(User user);

    void update(User user);

    void updateProfile(User user);

    void delete(long id);

    List<User> findUserByRoleId(long id);
    
    void insertUserRole(User user);

    void deleteUserRole(long id);




}
