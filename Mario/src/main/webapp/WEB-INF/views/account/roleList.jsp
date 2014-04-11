<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>角色列表</title>
	<script>
		$(document).ready(function() {
			$("#account-tab").addClass("active");
		});
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="row alert alert-success alert-dismissable"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<form class="form-inline" action="#">
			<div class="form-group">
		    	<label for="search_name">角色名：</label> <input type="text" id="search_name" name="search_name" class="form-control" style="width: 150px;" value="${param.search_name}" placeholder="角色名" />
		    </div>
		    <button type="submit" class="btn btn-default pull-right" id="search_btn">查询</button>
	    </form>
	</div>	
	<br/>
	<div class="row">
		<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
			<thead>
			<tr>
				<th>角色名</th>
				<th>详细</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<c:if test="${empty roles.content}">
				<tr>
					<td colspan="3">暂无数据</td>
				</tr>
			</c:if>
			<c:forEach items="${roles.content}" var="role">
				<tr>
					<td><shiro:hasRole name="admin"><a href="${ctx}/account/role/update/${role.id}">${role.name}</a>&nbsp;</shiro:hasRole></td>
					<td>${role.detail}&nbsp;</td>
					<td>
						<shiro:hasRole name="admin">
							<a href="${ctx}/account/role/delete/${role.id}" id="editLink-${role.name}">删除</a>
						</shiro:hasRole>
					</td>
				</tr>
			</c:forEach>
			</tbody>		
		</table>
	</div>
	<tags:pagination page="${roles}" />
	<shiro:hasRole name="admin">
		<div class="row"><a class="btn btn-default" href="${ctx}/account/role/create">创建角色</a></div>
	</shiro:hasRole>
</body>
</html>
