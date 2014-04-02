
package com.renren.infra.xweb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.renren.infra.xweb.entity.Mario_zk_info;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author yong.cao
 * @create-time 2014-3-25
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@MyBatisRepository
public interface Mario_zk_infoMybatisDao {

    Mario_zk_info findById(Integer id);

    List<Mario_zk_info> find(Map<String, Object> parameters, RowBounds rowBounds);

    List<Mario_zk_info> findAll();

    int findTotalNum(Map<String, Object> filterParams);
    
    void insert(Mario_zk_info mario_zk_info);

    void update(Mario_zk_info mario_zk_info);
    
    void delete(Integer id);
}
