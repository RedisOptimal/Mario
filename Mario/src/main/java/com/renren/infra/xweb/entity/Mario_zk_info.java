
package com.renren.infra.xweb.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 
 * @author yong.cao
 * @create-time 2014-3-25
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class Mario_zk_info {

    /**
    * 
    */
    private Integer id;
    /**
    * 
    */
    private String zk_name;
    /**
    * 
    */
    private Integer session_timeout;
    /**
    * 
    */
    private String observer;
    /**
    * 
    */
    private String observer_auth;
    
    /**
    *
    * Mario_zk_info 的缺省构造方法
    *
    */
    public Mario_zk_info () {
    }
    
    /**
    *
    * get方法
    *
    */
    public Integer getid(){
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
    @NotNull(message = "必须填写ZooKeeper名字")
    @NotBlank(message = "必须填写ZooKeeper名字")
    public String getzk_name(){
        return this.zk_name;
    }
    /**
    *
    * set方法
    *
    */
    public void setzk_name(String zk_name){
        this.zk_name = zk_name;
    }
    /**
    *
    * get方法
    *
    */
    @NotNull(message = "必须填写ZooKeeper Session Timeout")
    public Integer getsession_timeout(){
        return this.session_timeout;
    }
    /**
    *
    * set方法
    *
    */
    public void setsession_timeout(Integer session_timeout){
        this.session_timeout = session_timeout;
    }
    /**
    *
    * get方法
    *
    */
    public String getobserver(){
        return this.observer;
    }
    /**
    *
    * set方法
    *
    */
    public void setobserver(String observer){
        this.observer = observer;
    }
    /**
    *
    * get方法
    *
    */
    public String getobserver_auth(){
        return this.observer_auth;
    }
    /**
    *
    * set方法
    *
    */
    public void setobserver_auth(String observer_auth){
        this.observer_auth = observer_auth;
    }
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}