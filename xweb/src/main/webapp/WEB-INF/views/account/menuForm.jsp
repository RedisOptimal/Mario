<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>菜单管理</title>
	<script>
		$(document).ready(function() {
			$("#account-tab").addClass("active");
			
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						remote: "${ctx}/account/menu/checkMenuName?oldMenuName=" + encodeURIComponent('${menu.name}')
					},
				},
				messages: {
					name: {
						remote: "菜单名称已存在"
					},
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(':radio') || element.is(":checkbox") )
						error.appendTo(element.parent().parent().parent().parent());
					else
						error.insertAfter(element);
				}
			});
			
			//聚焦第一个输入框
			$("#name").focus();
			
		});
		
	</script>
</head>

<body>
	<form:form id="inputForm" modelAttribute="menu" action="${ctx}/account/menu/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${menu.id}"/>
		<fieldset>
			<legend><small>管理菜单</small></legend>
			<div id="messageBox" class="alert alert-danger" style="display:none">输入有误，请先更正。</div>
			<div class="form-group">
				<label for="groupList" class="col-sm-2 control-label">菜单:</label>
				<div class="col-sm-10">	
					<tags:treeselect id="menu" name="parentId" value="${menu.parent.id}" labelName="parentName" labelValue="${menu.parent.name}" title="菜单" url="static/menu/treeData" extId="${menu.id}" cssClass="form-control required" />			
				</div>
			</div>	
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">菜单名称<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<form:input path="name" id="name" cssClass="form-control required"/>
					<form:errors path="name" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label for="href" class="col-sm-2 control-label">链接:</label>
				<div class="col-sm-10">
					<input type="text" id="href" name="href" value="${menu.href}" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="sort" class="col-sm-2 control-label">排序:</label>
				<div class="col-sm-10">
					<input type="text" id="sort" name="sort" value="${menu.sort}" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="isShow" class="col-sm-2 control-label">是否显示<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<c:forEach items="${allShows}" var="shows">
						<label for="show${shows.key}" class="radio">
							<input id="show${shows.key}" name="isShow" type="radio" value="${shows.key}" <c:if test="${menu.isShow==shows.key}">checked="true"</c:if>  class="required" /> ${shows.value}&nbsp;
						</label>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label for="permission" class="col-sm-2 control-label">权限:</label>
				<div class="col-sm-10">
					<input type="text" id="permission" name="permission" value="${menu.permission}" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
					<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
				</div>
			</div>
		</fieldset>
	</form:form>
</body>
</html>