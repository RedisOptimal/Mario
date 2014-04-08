package com.renren.infra.xweb.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;

/**
 * 菜单
 * 
 * @author yong.cao
 * @create-time 2013-9-23
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class Menu {

    private long id;

    private Menu parent;//父菜单

    private long parentId;//父菜单id

    private String parentIds;//id名称

    @NotBlank(message = "菜单名不能为空")
    private String name;//名称

    private String href;//连接

    private int sort;//展示顺序

    private String isShow;//是否展示

    private String permission;//权限

    private List<Menu> childList = Lists.newArrayList();//子菜单

    public Menu() {
    }

    public Menu(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<Menu> getChildList() {
        return childList;
    }

    public void setChildList(List<Menu> childList) {
        this.childList = childList;
    }

    public static void sortList(List<Menu> list, List<Menu> sourcelist, Long parentId) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Menu e = sourcelist.get(i);
            if (e.getParentId() == parentId) {
                list.add(e);
                // 判断是否还有子节点, 有则继续获取子节点
                for (int j = 0; j < sourcelist.size(); j++) {
                    Menu child = sourcelist.get(j);
                    if (child.getParentId() == e.getId()) {
                        sortList(list, sourcelist, e.getId());
                        break;
                    }
                }
            }
        }
    }

    public boolean isRoot() {
        return isRoot(this.id);
    }

    public static boolean isRoot(Long id) {
        return id != 0 && id == 1L;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
