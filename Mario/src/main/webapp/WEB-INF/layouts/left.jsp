<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<shiro:user>
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->        
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone"></div>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li class="start">
					<!-- START DASHBOARD -->
					<a href="${ctx}/index">
					<i class="fa fa-home"></i> 
					<span class="title">首页</span>
					<span class="selected"></span>
					</a>
					<!-- END DASHBOARD -->
				</li>
				<c:forEach items="${menuList}" var="menu">
				<c:if test="${menu.parentId ne 0}">
					<c:if test="${menu.parentId eq 1 }">
						<li class="">
						<a href="javascript:;">
						<i class="fa fa-home"></i> 
						<span class="title">${menu.name}</span>
						<span class="arrow "></span>
						</a>
						<ul class="sub-menu">
							<c:set var="prefix" value="http://" />
							<c:forEach items="${menuList}" var="menuChild">
								<c:set var="string" value="${menuChild.href}" />
								<c:if test="${menuChild.parentId eq menu.id}">
									<li>
										<c:if test="${fn:startsWith(string, prefix)}">
											<a href="${menuChild.href}" target="_blank">${menuChild.name}</a>
										</c:if>
										<c:if test="${!fn:startsWith(string, prefix)}">
											<a href="${ctx}${menuChild.href}">${menuChild.name}</a>
										</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
						</li>
					</c:if>
				</c:if>
				</c:forEach>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->
</shiro:user>