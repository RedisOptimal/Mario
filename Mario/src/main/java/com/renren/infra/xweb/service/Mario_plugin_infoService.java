
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

import com.renren.infra.xweb.entity.Mario_plugin_info;
import com.renren.infra.xweb.repository.Mario_plugin_infoMybatisDao;

@Component
@Transactional
public class Mario_plugin_infoService {

    @Autowired
    private Mario_plugin_infoMybatisDao mario_plugin_infoDao;

    /**
     * 创建分页查询.
     * 
     * @param filterParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return Page<Test>
     */
    public Page<Mario_plugin_info> getMario_plugin_info(Map<String, Object> filterParams, int pageNumber, int pageSize) {

        List<Mario_plugin_info> mario_plugin_infos = mario_plugin_infoDao.find(filterParams, buildRowBounds(pageNumber, pageSize));
        int totalnum = mario_plugin_infoDao.findTotalNum(filterParams);

        return new PageImpl<Mario_plugin_info>(mario_plugin_infos, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    /**
     * 查找所有的Mario_plugin_info.
     * 
     * @return List<Mario_plugin_info>
     */
    public List<Mario_plugin_info> getAllMario_plugin_info() {
        return mario_plugin_infoDao.findAll();
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
     * 保存Mario_plugin_info.
     * 
     * @param newMario_plugin_info
     */
    public void saveMario_plugin_info(Mario_plugin_info newMario_plugin_info) {
        if (newMario_plugin_info.getid() != null     ) {
            mario_plugin_infoDao.update(newMario_plugin_info);
        } else {
            mario_plugin_infoDao.insert(newMario_plugin_info);
        }
    }

    /**
     * 根据主键获取Mario_plugin_info
     * 
     * @param id
     * @return Mario_plugin_info
     */
    public Mario_plugin_info getMario_plugin_info(Integer id    ) {
        return mario_plugin_infoDao.findById	(id);
    }

    /**
     * 根据主键删除Mario_plugin_info
     * 
     * @param id
     */
    public void deleteMario_plugin_info(Integer id) {
        mario_plugin_infoDao.delete(id);
    }
    
    public void deleteMario_plugin_infoByZkid(Integer zk_id) {
    	mario_plugin_infoDao.deleteByZkid(zk_id);
    }

}
