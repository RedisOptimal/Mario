
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

    /**
     * 保存Mario_server_info.
     * 
     * @param newMario_server_info
     */
    public void saveMario_server_info(Mario_server_info newMario_server_info) {
        if (newMario_server_info.getid() != null     ) {
            mario_server_infoDao.update(newMario_server_info);
        } else {
            mario_server_infoDao.insert(newMario_server_info);
        }
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

}
