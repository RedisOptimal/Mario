package com.renren.infra.xweb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.renren.infra.xweb.entity.Role;

@MyBatisRepository
public interface RoleMybatisDao {

    Role findRoleById(Long id);

    Role findRoleByName(String roleName);

    List<Role> findAll();

    List<Role> findRoleByUserID(Long userId);

    List<Role> findRoleByMenuID(Long menuId);

    List<Role> find(Map<String, Object> parameters, RowBounds rowBounds);

    int findTotalNum(Map<String, Object> parameters);

    void insert(Role role);

    void update(Role role);

    void delete(Long id);

    void insertRoleMenu(Role role);

    void deleteRoleMenu(Long id);

}
