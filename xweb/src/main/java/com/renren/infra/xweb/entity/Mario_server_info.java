
package com.renren.infra.xweb.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 
 * @author yong.cao
 * @create-time 2014-3-25
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class Mario_server_info {

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
    private String host;
    /**
    * 
    */
    private Integer port;
    
    /**
    *
    * Mario_server_info 的缺省构造方法
    *
    */
    public Mario_server_info () {
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
    public Integer getzk_id(){
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
    public String gethost(){
        return this.host;
    }
    /**
    *
    * set方法
    *
    */
    public void sethost(String host){
        this.host = host;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getport(){
        return this.port;
    }
    /**
    *
    * set方法
    *
    */
    public void setport(Integer port){
        this.port = port;
    }
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}