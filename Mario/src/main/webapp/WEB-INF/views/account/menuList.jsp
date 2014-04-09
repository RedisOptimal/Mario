<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>菜单列表</title>
	<!-- tree table -->
	<link href="${ctx}/static/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/static/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
	<script>
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 5});
		});
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="row alert <c:if test="${success}">alert-success</c:if> <c:if test="${not success}">alert-danger</c:if>"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<form class="form-inline" action="#">
		    <div class="form-group">
		    	<label for="search_name">菜单名：</label> <input type="text" id="search_name" name="search_name" class="form-control" style="width: 150px;" value="${param.search_name}" placeholder="菜单名" />
		    </div>
		    <button type="submit" class="btn btn-default pull-right" id="search_btn">查询</button>
	    </form>
	</div>	
	<br/>
	<div class="row">		
		<table id="treeTable" class="table table-striped table-bordered table-condensed table-hover">
			<thead>
			<tr>
				<th>菜单名</th>
				<th>链接</th>
				<th>排序</th>
				<th>是否展示</th>
				<th>权限</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<c:if test="${empty menus}">
				<tr>
					<td colspan="6">暂无数据</td>
				</tr>
			</c:if>
			<c:forEach items="${menus}" var="menu">
				<tr id="${menu.id}" pId="${menu.parentId ne 1?menu.parentId:'0'}">
					<td><shiro:hasRole name="admin"><a href="${ctx}/account/menu/update/${menu.id}">${menu.name}</a>&nbsp;</shiro:hasRole></td>
					<td>${menu.href}&nbsp;</td>
					<td>${menu.sort}&nbsp;</td>
					<td>${allShows[menu.isShow]}&nbsp;</td>
					<td>${menu.permission}&nbsp;</td>
					<td>
						<shiro:hasRole name="admin">
							<a href="${ctx}/account/menu/delete/${menu.id}" id="editLink-${menu.name}">删除</a>
						</shiro:hasRole>
					</td>
				</tr>
			</c:forEach>
			</tbody>		
		</table>
	</div>
	<shiro:hasRole name="admin">
		<div class="row"><a class="btn btn-default" href="${ctx}/account/menu/create">创建菜单</a></div>
	</shiro:hasRole>
</body>
</html>
