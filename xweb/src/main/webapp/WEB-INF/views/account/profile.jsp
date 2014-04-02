<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>资料修改</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="inputForm" action="${ctx}/account/user/profile" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}"/>
		<fieldset>
			<legend><small>资料修改</small></legend>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">用户名:</label>
				<div class="col-sm-10">
					<input type="text" id="name" name="name" value="${user.name}" class="form-control required" minlength="3"/>
				</div>
			</div>
			<div class="form-group">
				<label for="plainPassword" class="col-sm-2 control-label">密码:</label>
				<div class="col-sm-10">
					<input type="password" id="plainPassword" name="plainPassword" class="form-control" placeholder=""/>
				</div>
			</div>
			<div class="form-group">
				<label for="confirmPassword" class="col-sm-2 control-label">确认密码:</label>
				<div class="col-sm-10">
					<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" equalTo="#plainPassword" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-2 control-label">邮箱:</label>
				<div class="col-sm-10">
					<input type="text" id="email" name="email" value="${user.email }" class="form-control"/>
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
				<div class="col-sm-offset-2 col-sm-10">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
				</div>
			</div>
		</fieldset>
	</form>
</body>
</html>
