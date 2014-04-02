
package com.renren.infra.xweb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.renren.infra.xweb.entity.Mario_node_state;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author yong.cao
 * @create-time 2014-3-26
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@MyBatisRepository
public interface Mario_node_stateMybatisDao {

    Mario_node_state findById(Integer id);

    List<Mario_node_state> find(Map<String, Object> parameters, RowBounds rowBounds);

    List<Mario_node_state> findAll();

    int findTotalNum(Map<String, Object> filterParams);
    
    void insert(Mario_node_state mario_node_state);

    void update(Mario_node_state mario_node_state);
    
    void delete(Integer id);
}
