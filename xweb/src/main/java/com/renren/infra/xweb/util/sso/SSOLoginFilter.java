package com.renren.infra.xweb.util.sso;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.sso.api.bean.LogonBean;
import com.sso.api.inter.SSOInterface;
import com.sso.api.service.SSOService;

/**
 * 与OA结合的SSO拦截器
 * 
 * @author yong.cao
 * @create-time 2013-10-14
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class SSOLoginFilter implements Filter {

    @Autowired
    private SSOService service;

    @Autowired
    private SSOInterface sso;

    /**
     * Default constructor.
     */
    public SSOLoginFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        //renren sso logical code
        LogonBean bean = service.getLogonInfo(request, response);
        if ((bean == null) || (bean.getLogonstatus() == null)
                || (!bean.getLogonstatus().equals("1"))) {
            bean = sso.ppLogin(request, response);
            if (bean == null) {
                return;
            }
        }

        try {
            //shiro login
            SSOAuthenticationToken token = new SSOAuthenticationToken(bean.getLogonname());
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            //username or staffNo is not in system, so redirect to register page and need to write log.
            String errorPage = "login";
            request.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "暂未开通权限，请联系管理员");
            response.sendRedirect(errorPage);//跳转到信息提示页面！！
            
            return ;
        } catch (LockedAccountException lae) {
            //account for that username or staffNo is locked - can't login. so redirect to some info page. 

        }
        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {

    }

    public SSOService getService() {
        return service;
    }

    public void setService(SSOService service) {
        this.service = service;
    }

    public SSOInterface getSso() {
        return sso;
    }

    public void setSso(SSOInterface sso) {
        this.sso = sso;
    }

}
