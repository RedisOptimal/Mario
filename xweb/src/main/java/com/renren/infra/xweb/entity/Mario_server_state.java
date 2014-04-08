
package com.renren.infra.xweb.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 
 * @author yong.cao
 * @create-time 2014-3-21
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class Mario_server_state {

    /**
    * 
    */
    private Integer id;
    /**
    * 
    */
    private Integer server_id;
    /**
    * 
    */
    private Integer min_latency;
    /**
    * 
    */
    private Integer ave_latency;
    /**
    * 
    */
    private Integer max_latency;
    /**
    * 
    */
    private Long received;
    /**
    * 
    */
    private Long sent;
    /**
    * 
    */
    private Integer outstanding;
    /**
    * 
    */
    private String zxid;
    /**
    * 
    */
    private String mode;
    /**
    * 
    */
    private Integer node_count;
    /**
    * 
    */
    private Integer total_watches;
    /**
    * 
    */
    private Integer client_number;
    /**
    * 
    */
    private Long time_stamp;
    
    /**
    *
    * Mario_server_state 的缺省构造方法
    *
    */
    public Mario_server_state () {
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
    public Integer getServer_id(){
        return this.server_id;
    }
    /**
    *
    * set方法
    *
    */
    public void setserver_id(Integer server_id){
        this.server_id = server_id;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getMin_latency(){
        return this.min_latency;
    }
    /**
    *
    * set方法
    *
    */
    public void setmin_latency(Integer min_latency){
        this.min_latency = min_latency;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getAve_latency(){
        return this.ave_latency;
    }
    /**
    *
    * set方法
    *
    */
    public void setave_latency(Integer ave_latency){
        this.ave_latency = ave_latency;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getMax_latency(){
        return this.max_latency;
    }
    /**
    *
    * set方法
    *
    */
    public void setmax_latency(Integer max_latency){
        this.max_latency = max_latency;
    }
    /**
    *
    * get方法
    *
    */
    public Long getReceived(){
        return this.received;
    }
    /**
    *
    * set方法
    *
    */
    public void setreceived(Long received){
        this.received = received;
    }
    /**
    *
    * get方法
    *
    */
    public Long getSent(){
        return this.sent;
    }
    /**
    *
    * set方法
    *
    */
    public void setsent(Long sent){
        this.sent = sent;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getOutstanding(){
        return this.outstanding;
    }
    /**
    *
    * set方法
    *
    */
    public void setoutstanding(Integer outstanding){
        this.outstanding = outstanding;
    }
    /**
    *
    * get方法
    *
    */
    public String getZxid(){
        return this.zxid;
    }
    /**
    *
    * set方法
    *
    */
    public void setzxid(String zxid){
        this.zxid = zxid;
    }
    /**
    *
    * get方法
    *
    */
    public String getMode(){
        return this.mode;
    }
    /**
    *
    * set方法
    *
    */
    public void setmode(String mode){
        this.mode = mode;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getNode_count(){
        return this.node_count;
    }
    /**
    *
    * set方法
    *
    */
    public void setnode_count(Integer node_count){
        this.node_count = node_count;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getTotal_watches(){
        return this.total_watches;
    }
    /**
    *
    * set方法
    *
    */
    public void settotal_watches(Integer total_watches){
        this.total_watches = total_watches;
    }
    /**
    *
    * get方法
    *
    */
    public Integer getClient_number(){
        return this.client_number;
    }
    /**
    *
    * set方法
    *
    */
    public void setclient_number(Integer client_number){
        this.client_number = client_number;
    }
    /**
    *
    * get方法
    *
    */
    public Long getTime_stamp(){
        return this.time_stamp;
    }
    /**
    *
    * set方法
    *
    */
    public void settime_stamp(Long time_stamp){
        this.time_stamp = time_stamp;
    }
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}