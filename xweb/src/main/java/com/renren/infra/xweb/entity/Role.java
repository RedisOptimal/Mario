package com.renren.infra.xweb.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springside.modules.utils.Collections3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * 角色
 * 
 * @author yong.cao
 * @create-time 2013-9-26
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class Role {

    private long id;

    private String name;

    private String detail;

    private List<Menu> menuList = Lists.newArrayList();

    public Role() {
    }

    public Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotBlank(message = "角色名不能为空")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
    
    @JsonIgnore
    public String getMenuIds(){
        return Collections3.extractToString(menuList, "id", ", ");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
