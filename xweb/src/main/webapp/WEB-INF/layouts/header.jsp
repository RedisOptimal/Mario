<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->  
			<a class="navbar-brand" href="${ctx}">
			<span style="font: bolder; padding-left: 30px;">展示系统</span>
			</a>
			<!-- END LOGO -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav navbar-nav pull-right">
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<shiro:user>
				<li class="dropdown user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
					<span class="username"><shiro:principal property="name"/></span>
					<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<shiro:hasRole name="admin">
							<li><a href="${ctx}/account/user">用户管理</a></li>
							<li><a href="${ctx}/account/role">角色管理</a></li>
							<li><a href="${ctx}/account/menu">菜单管理</a></li>
							<li class="divider"></li>
						</shiro:hasRole>
						<li><a href="${ctx}/account/user/profile">编辑个人信息</a></li>
						<li><a href="${ctx}/logout">退出</a>	</li>
					</ul>
				</li>
				</shiro:user>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			<!-- END TOP NAVIGATION MENU -->
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>