
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

import com.renren.infra.xweb.entity.Mario_zk_info;
import com.renren.infra.xweb.repository.Mario_zk_infoMybatisDao;

@Component
@Transactional
public class Mario_zk_infoService {

    @Autowired
    private Mario_zk_infoMybatisDao mario_zk_infoDao;

    /**
     * 创建分页查询.
     * 
     * @param filterParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return Page<Test>
     */
    public Page<Mario_zk_info> getMario_zk_info(Map<String, Object> filterParams, int pageNumber, int pageSize) {

        List<Mario_zk_info> mario_zk_infos = mario_zk_infoDao.find(filterParams, buildRowBounds(pageNumber, pageSize));
        int totalnum = mario_zk_infoDao.findTotalNum(filterParams);

        return new PageImpl<Mario_zk_info>(mario_zk_infos, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    /**
     * 查找所有的Mario_zk_info.
     * 
     * @return List<Mario_zk_info>
     */
    public List<Mario_zk_info> getAllMario_zk_info() {
        return mario_zk_infoDao.findAll();
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

    public boolean updateRecord(Mario_zk_info mario_zk_info) {
    	if (mario_zk_info.getid() == null) {
    		return false;
    	}
    	try {
    		mario_zk_infoDao.update(mario_zk_info);
    	} catch (Throwable e) {
    		return false;
    	}
    	return true;
    }
    
    public boolean saveNewRecord(Mario_zk_info newMario_zk_info) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("zk_name", newMario_zk_info.getzk_name());
    	if (mario_zk_infoDao.find(paramMap, new RowBounds()).isEmpty()) {
	        mario_zk_infoDao.insert(newMario_zk_info);
	        return true;
    	}
    	return false;
    }

    /**
     * 根据主键获取Mario_zk_info
     * 
     * @param id
     * @return Mario_zk_info
     */
    public Mario_zk_info getMario_zk_info(Integer id    ) {
        return mario_zk_infoDao.findById	(id);
    }

    /**
     * 根据主键删除Mario_zk_info
     * 
     * @param id
     */
    public void deleteMario_zk_info(Integer id) {
        mario_zk_infoDao.delete(id);
    }

}
