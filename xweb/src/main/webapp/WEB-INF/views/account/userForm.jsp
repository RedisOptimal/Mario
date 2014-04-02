<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户管理</title>
	<script>
		$(document).ready(function() {
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						remote: "${ctx}/account/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')
					},
				},
				messages: {
					loginName: {
						remote: "用户登录名已存在"
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
			$("#loginName").focus();
		});
		
	</script>
</head>

<body>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/account/user/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}"/>
		<fieldset>
			<legend><small>管理用户</small></legend>
			<div id="messageBox" class="alert alert-danger" style="display:none">输入有误，请先更正。</div>
			<div class="form-group">
				<label for="loginName" class="col-sm-2 control-label">登录名<span class="required">*</span>:</label>
				<div class=col-sm-10>
					<form:input id="loginName" path="loginName" cssClass="form-control required"/>
					<form:errors path="loginName" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">用户名<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<form:input path="name" id="name" cssClass="form-control required"/>
					<form:errors path="name" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label for="plainPassword" class="col-sm-2 control-label">密码<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<input type="password" id="plainPassword" name="plainPassword" class="form-control required" placeholder=""/>
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-2 control-label">邮箱:</label>
				<div class="col-sm-10">
					<form:input path="email" id="email" cssClass="form-control"/>
					<form:errors path="email" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label for="staffNo" class="col-sm-2 control-label">工号:</label>
				<div class="col-sm-10">
					<input type="text" id="staffNo" name="staffNo" value="${user.staffNo }" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="phone" class="col-sm-2 control-label">电话:</label>
				<div class="col-sm-10">
					<input type="text" id="phone" name="phone" value="${user.phone}" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="mobile" class="col-sm-2 control-label">手机:</label>
				<div class="col-sm-10">
					<input type="text" id="mobile" name="mobile" value="${user.mobile}" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="groupList" class="col-sm-2 control-label">角色<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<c:forEach items="${allRoles}" var="role">
						<label for="roleList${role.id}" class="checkbox">
						<input id="roleList${role.id}" name="roleList" value="${role.id}" type="checkbox" class="required" <c:forEach items="${user.roleList}" var="selectRole"><c:if test="${selectRole.id==role.id}">checked</c:if></c:forEach> /> ${role.detail }&nbsp;
						</label>
					</c:forEach>	
				</div>
			</div>	
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label">状态<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<c:forEach items="${allStatus}" var="status">
						<label for="status${status.key}" class="radio">
							<input id="status${status.key}" name="status" type="radio" value="${status.key}" <c:if test="${user.status==status.key}">checked="true"</c:if>  class="required" /> ${status.value}&nbsp;
						</label>
					</c:forEach>
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
