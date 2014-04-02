package com.renren.infra.xweb.util.sso;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.renren.infra.xweb.entity.Menu;
import com.renren.infra.xweb.entity.ShiroUser;
import com.renren.infra.xweb.service.AccountService;

/**
 * remember me filter, add session info if user checked remember me
 * 
 * @author yong.cao
 * @create-time 2013-12-4
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class RembermeFilter implements Filter {

    private AccountService accountService;

    /**
     * Default constructor.
     */
    public RembermeFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered()) {
            ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            //用户对应的Menu信息
            List<Menu> menus = accountService.findMenuByUserID(user.getId());
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setAttribute("menuList", menus);
        }

        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}
