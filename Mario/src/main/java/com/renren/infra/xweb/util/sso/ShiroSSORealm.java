package com.renren.infra.xweb.util.sso;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.Encodes;

import com.renren.infra.xweb.entity.Menu;
import com.renren.infra.xweb.entity.Role;
import com.renren.infra.xweb.entity.ShiroUser;
import com.renren.infra.xweb.entity.User;
import com.renren.infra.xweb.service.AccountService;

/**
 * 单点登录的验证Realm
 * 
 * @author yong.cao
 * @create-time 2013-10-14
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class ShiroSSORealm extends AuthorizingRealm {

    private AccountService accountService;

    /* 
     * 认证回调函数,登录时调用
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        SSOAuthenticationToken ssoToken = (SSOAuthenticationToken)token;
        User user = accountService.findUserByStaffNo(ssoToken.getStaffNo());
        
        if (user != null) {
            if (user.getStatus().equals("0")) {//disable
                throw new DisabledAccountException();
            }

            //用户对应的Menu信息
            List<Menu> menus = accountService.findMenuByUserID(user.getId());
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setAttribute("menuList", menus);

            byte[] salt = Encodes.decodeHex(user.getSalt());
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getName()),
                    user.getPassword(), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    /* 
     * 授权回调函数,进行鉴权但缓存中无用户的授权信息时调用.
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        User user = accountService.findUserByLoginName(shiroUser.loginName);
        List<Role> userRoles = accountService.getRoleByUserID(user.getId());//用户ID对应的角色列表信息

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Role role : userRoles) {
            // 基于Role的权限信息
            info.addRole(role.getName());
            // 基于Permission的权限信息
            //TODO:add permission value info 
            //info.addStringPermissions(role.getPermissionList());
        }
        return info;
    }
    
    /**
     * 设定校验规则.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        AllowAllCredentialsMatcher matcher = new AllowAllCredentialsMatcher();
        setCredentialsMatcher(matcher);
    }
    
    /**
     * 设定验证Token类
     */
    @PostConstruct
    public void initAuthenticationTokenClass(){
        setAuthenticationTokenClass(SSOAuthenticationToken.class);
    }
    
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}
