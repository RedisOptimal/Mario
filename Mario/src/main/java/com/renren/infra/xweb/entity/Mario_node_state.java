
package com.renren.infra.xweb.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 
 * @author yong.cao
 * @create-time 2014-3-26
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class Mario_node_state {

    /**
    * 
    */
    private Integer zk_id;
    /**
    * 
    */
    private String cluster_name;
    /**
     * 
     */
    private String path;
    /**
    * 
    */
    private String data;
    /**
    * 
    */
    private Integer data_length;
    /**
    * 
    */
    private Integer num_children;
    /**
    * 
    */
    private Integer version;
    /**
    * 
    */
    private Integer aversion;
    /**
    * 
    */
    private Integer cversion;
    /**
    * 
    */
    private Long ctime;
    /**
    * 
    */
    private long mtime;
    /**
    * 
    */
    private Long czxid;
    /**
    * 
    */
    private Long mzxid;
    /**
    * 
    */
    private Long pzxid;
    /**
    * 
    */
    private Long ephemeral_owner;
    private int state_version;
    private Date state_time;
    
    /**
    *
    * Mario_node_state 的缺省构造方法
    *
    */
    public Mario_node_state () {
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
     * @return the cluster_name
     */
    public String getCluster_name() {
        return cluster_name;
    }

    
    /**
     * @param cluster_name the cluster_name to set
     */
    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }

    /**
    *
    * get方法
    *
    */
    public String getpath(){
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
    public String getdata(){
        return this.data;
    }
    /**
    *
    * set方法
    *
    */
    public void setdata(String data){
        this.data = data;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getdata_length(){
        return this.data_length;
    }
    /**
    *
    * set方法
    *
    */
    public void setdata_length(Integer data_length){
        this.data_length = data_length;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getnum_children(){
        return this.num_children;
    }
    /**
    *
    * set方法
    *
    */
    public void setnum_children(Integer num_children){
        this.num_children = num_children;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getversion(){
        return this.version;
    }
    /**
    *
    * set方法
    *
    */
    public void setversion(Integer version){
        this.version = version;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getaversion(){
        return this.aversion;
    }
    /**
    *
    * set方法
    *
    */
    public void setaversion(Integer aversion){
        this.aversion = aversion;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getcversion(){
        return this.cversion;
    }
    /**
    *
    * set方法
    *
    */
    public void setcversion(Integer cversion){
        this.cversion = cversion;
    }
    /**
    *
    * get方法
    *
    */
    public Long getctime(){
        return this.ctime;
    }
    /**
    *
    * set方法
    *
    */
    public void setctime(long ctime){
        this.ctime = ctime;
    }
    /**
    *
    * get方法
    *
    */
    public long getmtime(){
        return this.mtime;
    }
    /**
    *
    * set方法
    *
    */
    public void setmtime(Long mtime){
        this.mtime = mtime;
    }

	/**
    *
    * get方法
    *
    */
    public Long getczxid(){
        return this.czxid;
    }
    /**
    *
    * set方法
    *
    */
    public void setczxid(Long czxid){
        this.czxid = czxid;
    }
    /**
    *
    * get方法
    *
    */
    public Long getmzxid(){
        return this.mzxid;
    }
    /**
    *
    * set方法
    *
    */
    public void setmzxid(Long mzxid){
        this.mzxid = mzxid;
    }
    /**
    *
    * get方法
    *
    */
    public Long getpzxid(){
        return this.pzxid;
    }
    /**
    *
    * set方法
    *
    */
    public void setpzxid(Long pzxid){
        this.pzxid = pzxid;
    }
    /**
    *
    * get方法
    *
    */
    public Long getephemeral_owner(){
        return this.ephemeral_owner;
    }
    /**
    *
    * set方法
    *
    */
    public void setephemeral_owner(Long ephemeral_owner){
        this.ephemeral_owner = ephemeral_owner;
    }
	
    public int getState_version() {
		return state_version;
	}

	public void setState_version(int state_version) {
		this.state_version = state_version;
	}

	public String getState_time() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(state_time);
	}

	public void setState_time(Date state_time) {
		this.state_time = state_time;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}