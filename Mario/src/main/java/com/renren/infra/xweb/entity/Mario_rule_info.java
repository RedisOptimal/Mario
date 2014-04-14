
package com.renren.infra.xweb.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 
 * @author yong.cao
 * @create-time 2014-4-14
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class Mario_rule_info {

    /**
    * 
    */
    private Integer id;
    /**
    * 
    */
    private Integer zk_id;
    /**
    * 
    */
    private String path;
    /**
    * 
    */
    private String type;
    /**
    * 
    */
    private Integer min_children_number;
    /**
    * 
    */
    private Integer max_children_number;
    /**
    * 
    */
    private String phone_number;
    /**
    * 
    */
    private String email_address;
    /**
    * 
    */
    private Long user_id;
    /**
    * 
    */
    private Boolean enable;
    
    /**
    *
    * Mario_rule_info 的缺省构造方法
    *
    */
    public Mario_rule_info () {
    }
    
    /**
    *
    * get方法
    *
    */
    public Integer getId(){
        return this.id;
    }
    /**
    *
    * set方法
    *
    */
    public void setid(Integer id){
        this.id = id;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getZk_id(){
        return this.zk_id;
    }
    /**
    *
    * set方法
    *
    */
    public void setzk_id(Integer zk_id){
        this.zk_id = zk_id;
    }
    /**
    *
    * get方法
    *
    */
    public String getPath(){
        return this.path;
    }
    /**
    *
    * set方法
    *
    */
    public void setpath(String path){
        this.path = path;
    }
    /**
    *
    * get方法
    *
    */
    public String getType(){
        return this.type;
    }
    /**
    *
    * set方法
    *
    */
    public void settype(String type){
        this.type = type;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getMin_children_number(){
        return this.min_children_number;
    }
    /**
    *
    * set方法
    *
    */
    public void setmin_children_number(Integer min_children_number){
        this.min_children_number = min_children_number;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getMax_children_number(){
        return this.max_children_number;
    }
    /**
    *
    * set方法
    *
    */
    public void setmax_children_number(Integer max_children_number){
        this.max_children_number = max_children_number;
    }
    /**
    *
    * get方法
    *
    */
    public String getPhone_number(){
        return this.phone_number;
    }
    /**
    *
    * set方法
    *
    */
    public void setphone_number(String phone_number){
        this.phone_number = phone_number;
    }
    /**
    *
    * get方法
    *
    */
    public String getEmail_address(){
        return this.email_address;
    }
    /**
    *
    * set方法
    *
    */
    public void setemail_address(String email_address){
        this.email_address = email_address;
    }
    /**
    *
    * get方法
    *
    */
    public Long getUser_id(){
        return this.user_id;
    }
    /**
    *
    * set方法
    *
    */
    public void setuser_id(Long user_id){
        this.user_id = user_id;
    }
    /**
    *
    * get方法
    *
    */
    public Boolean getEnable(){
        return this.enable;
    }
    /**
    *
    * set方法
    *
    */
    public void setenable(Boolean enable){
        this.enable = enable;
    }
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}