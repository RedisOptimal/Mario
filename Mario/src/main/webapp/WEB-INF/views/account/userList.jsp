<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户列表</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="row alert alert-success alert-dismissable"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<form class="form-inline" action="#">
			<div class="form-group">
		 		<label for="search_loginName">登录名：</label> <input type="text" id="search_loginName" name="search_loginName" class="form-control" style="width: 150px;" value="${param.search_loginName}" placeholder="登录名" />
		 	</div> 
		 	<div class="form-group">
		    	<label for="search_name">姓名：</label> <input type="text" id="search_name" name="search_name" class="form-control" style="width: 150px;" value="${param.search_name}" placeholder="姓名" />
		    </div>
		    <button type="submit" class="btn btn-default pull-right" id="search_btn">查询</button>
	    </form>
	</div>	
	<br/>
	<div class="row">
		<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
			<thead>
			<tr>
				<th>登录名</th>
				<th>姓名</th>
				<th>邮箱</th>
				<th>角色</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<c:if test="${empty users.content}">
				<tr>
					<td colspan="6">暂无数据</td>
				</tr>
			</c:if>
			<c:forEach items="${users.content}" var="user">
				<tr>
					<td><shiro:hasRole name="admin"><a href="${ctx}/account/user/update/${user.id}">${user.loginName}</a>&nbsp;</shiro:hasRole></td>
					<td>${user.name}&nbsp;</td>
					<td>${user.email}&nbsp;</td>
					<td>${user.roleNames}&nbsp;</td>
					<td>${allStatus[user.status]}&nbsp;</td>
					<td>
						<shiro:hasRole name="admin">
							<a href="${ctx}/account/user/delete/${user.id}" id="editLink-${user.loginName}">删除</a>
						</shiro:hasRole>
					</td>
				</tr>
			</c:forEach>
			</tbody>		
		</table>
	</div>
	<tags:pagination page="${users}" />
	<shiro:hasRole name="admin">
		<div class="row"><a class="btn btn-default" href="${ctx}/account/user/create">创建用户</a></div>
	</shiro:hasRole>
</body>
</html>
