
package com.renren.infra.xweb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.renren.infra.xweb.entity.Mario_server_info;
import com.renren.infra.xweb.repository.Mario_server_infoMybatisDao;

@Component
@Transactional
public class Mario_server_infoService {

    @Autowired
    private Mario_server_infoMybatisDao mario_server_infoDao;

    /**
     * 创建分页查询.
     * 
     * @param filterParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return Page<Test>
     */
    public Page<Mario_server_info> getMario_server_info(Map<String, Object> filterParams, int pageNumber, int pageSize) {

        List<Mario_server_info> mario_server_infos = mario_server_infoDao.find(filterParams, buildRowBounds(pageNumber, pageSize));
        int totalnum = mario_server_infoDao.findTotalNum(filterParams);

        return new PageImpl<Mario_server_info>(mario_server_infos, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    /**
     * 查找所有的Mario_server_info.
     * 
     * @return List<Mario_server_info>
     */
    public List<Mario_server_info> getAllMario_server_info() {
        return mario_server_infoDao.findAll();
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

    public boolean updateRecord(Mario_server_info mario_server_info) {
    	if (mario_server_info.getid() == null) {
    		return false;
    	}
    	try {
    		mario_server_infoDao.update(mario_server_info);
    	} catch (Throwable e) {
    		return false;
    	}
    	return true;
    }
    
    public boolean saveNewRecord(Mario_server_info newMario_server_info) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("host", newMario_server_info.gethost());
    	paramMap.put("port", newMario_server_info.getport());
    	if (mario_server_infoDao.find(paramMap, new RowBounds()).isEmpty()) {
	        mario_server_infoDao.insert(newMario_server_info);
	        return true;
	    }
    	return false;
    }

    /**
     * 根据主键获取Mario_server_info
     * 
     * @param id
     * @return Mario_server_info
     */
    public Mario_server_info getMario_server_info(Integer id    ) {
        return mario_server_infoDao.findById	(id);
    }

    /**
     * 根据主键删除Mario_server_info
     * 
     * @param id
     */
    public void deleteMario_server_info(Integer id) {
        mario_server_infoDao.delete(id);
    }

    public void deleteMario_server_infoByZkid(Integer zk_id) {
    	mario_server_infoDao.deleteByZkid(zk_id);
    }
    
}
