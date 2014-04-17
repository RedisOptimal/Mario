package com.renren.infra.xweb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.renren.infra.xweb.entity.Mario_server_state;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author yong.cao
 * @create-time 2014-3-21
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@MyBatisRepository
public interface Mario_server_stateMybatisDao {

	List<Mario_server_state> findAll();

	List<Mario_server_state> find(Map<String, Object> parameters);

	List<Mario_server_state> find(Map<String, Object> filterParams,
			RowBounds buildRowBounds);

	int findTotalNum(Map<String, Object> filterParams);

	Mario_server_state getMario_server_stateById(Integer id);

	List<Mario_server_state> findBetweenTimeStamp(
			@Param(value = "server_id") Integer server_id,
			@Param(value = "start_time_stamp") Long start_time_stamp,
			@Param(value = "end_time_stamp") Long end_time_stamp);

	Mario_server_state findLastStateByServerId(
			@Param(value = "server_id") Integer server_id);

}
