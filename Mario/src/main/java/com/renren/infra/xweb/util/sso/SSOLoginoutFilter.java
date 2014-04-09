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
import org.springframework.beans.factory.annotation.Autowired;

import com.sso.api.inter.SSOInterface;

/**
 * 单点登录退出拦截器
 * 
 * @author Administrator
 * @create-time 2013-10-15
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class SSOLoginoutFilter implements Filter {

    @Autowired
    private SSOInterface sso;

    /**
     * Default constructor.
     */
    public SSOLoginoutFilter() {
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

        //clean renren sso login info
        sso.ppClearLocalLogin(request, response);
        sso.ppLogout(request, response, "/");//跳转到相应的页面

        //shiro logout 
        SecurityUtils.getSubject().logout();

        return;

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

    public SSOInterface getSso() {
        return sso;
    }

    public void setSso(SSOInterface sso) {
        this.sso = sso;
    }

}
