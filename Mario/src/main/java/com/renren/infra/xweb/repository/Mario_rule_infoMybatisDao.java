
package com.renren.infra.xweb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.renren.infra.xweb.entity.Mario_rule_info;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author yong.cao
 * @create-time 2014-4-14
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@MyBatisRepository
public interface Mario_rule_infoMybatisDao {

    Mario_rule_info findById(Integer id);

    List<Mario_rule_info> findAll();

    List<Mario_rule_info> find(Map<String, Object> parameters);

    void insert(Mario_rule_info mario_rule_info);

    void update(Mario_rule_info mario_rule_info);
    
    void delete(Integer id);
	List<Mario_rule_info> find(Map<String, Object> filterParams,
			RowBounds buildRowBounds);

	int findTotalNum(Map<String, Object> filterParams);
	
	Mario_rule_info getMario_rule_infoById(Integer id);}
