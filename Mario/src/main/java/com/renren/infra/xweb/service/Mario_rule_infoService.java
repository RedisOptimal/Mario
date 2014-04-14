
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

import com.renren.infra.xweb.entity.Mario_rule_info;
import com.renren.infra.xweb.repository.Mario_rule_infoMybatisDao;

@Component
@Transactional
public class Mario_rule_infoService {

    @Autowired
    private Mario_rule_infoMybatisDao mario_rule_infoDao;

    /**
     * 创建分页查询.
     * 
     * @param filterParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return Page<Test>
     */
    public Page<Mario_rule_info> getMario_rule_info(Map<String, Object> filterParams, int pageNumber, int pageSize) {

        List<Mario_rule_info> mario_rule_infos = mario_rule_infoDao.find(filterParams, buildRowBounds(pageNumber, pageSize));
        int totalnum = mario_rule_infoDao.findTotalNum(filterParams);

        return new PageImpl<Mario_rule_info>(mario_rule_infos, new PageRequest(pageNumber - 1, pageSize), totalnum);
    }

    /**
     * 查找所有的Mario_rule_info.
     * 
     * @return List<Mario_rule_info>
     */
    public List<Mario_rule_info> getAllMario_rule_info() {
        return mario_rule_infoDao.findAll();
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
     * 保存Mario_rule_info.
     * 
     * @param newMario_rule_info
     */
    public void saveMario_rule_info(Mario_rule_info newMario_rule_info) {
        if (newMario_rule_info.getId() != null     ) {
            mario_rule_infoDao.update(newMario_rule_info);
        } else {
            mario_rule_infoDao.insert(newMario_rule_info);
        }
    }

    /**
     * 根据主键获取Mario_rule_info
     * 
     * @param id
     * @return Mario_rule_info
     */
    public Mario_rule_info getMario_rule_info(Integer id    ) {
        return mario_rule_infoDao.findById	(id);
    }

    /**
     * 根据主键删除Mario_rule_info
     * 
     * @param id
     */
    public void deleteMario_rule_info(Integer id) {
        mario_rule_infoDao.delete(id);
    }

}
