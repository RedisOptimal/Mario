package com.renren.infra.xweb.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springside.modules.utils.Collections3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * 用户.
 * 
 * @author calvin
 */
public class User {

    private long id;

    private String loginName;

    private String plainPassword;

    private String password;

    private String salt;

    private String name;

    private String email;

    private String status;

    private String staffNo;

    private String phone;

    private String mobile;

    private List<Role> roleList = Lists.newArrayList(); // 有序的关联对象集合

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotBlank(message = "登录名不能为空")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @JsonIgnore
    public String getRoleNames() {
        return Collections3.extractToString(roleList, "name", ", ");
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(long id) {
        return id != 0 && id == 1L;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
