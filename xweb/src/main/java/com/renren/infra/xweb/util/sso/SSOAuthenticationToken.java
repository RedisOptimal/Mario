package com.renren.infra.xweb.util.sso;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 单点登录Token
 * 
 * @author yong.cao
 * @create-time 2013-10-14
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class SSOAuthenticationToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 1879791843859762091L;

    private String staffNo;//工号

    public SSOAuthenticationToken() {
    }

    public SSOAuthenticationToken(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public void clear() {
        this.staffNo = null;
    }

    public String toString() {
        return "staffNo=" + this.staffNo;
    }
}
