<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>登录页</title>
<script>
	$(document).ready(function() {
		$("#loginForm").validate();
	});
</script>
</head>

<body>
	<div class="row-fluid span3" style="float: none; margin: 0 auto;">
		<div class="dialog">
			<div class="block">
				<p class="block-heading">登录</p>
				<div class="block-body">
					<form id="loginForm" class="form-signin" action="${ctx}/login"
						method="post" class="form-horizontal">
						<%
						    String error = (String) request
						            .getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
						    if (error != null) {
						%>
						<div class="alert alert-error input-medium controls">
							<button class="close" data-dismiss="alert">×</button>
							登录失败，请重试.
						</div>
						<%
						    }
						%>
						<div class="control-group">
							<label for="username" class="control-label">名称:</label>
							<div class="controls">
								<input type="text" id="username" name="username"
									value="${username}" class="span12 input-medium required" />
							</div>
						</div>
						<div class="control-group">
							<label for="password" class="control-label">密码:</label>
							<div class="controls">
								<input type="password" id="password" name="password"
									class="span12 input-medium required" />
							</div>
						</div>
						<input id="submit_btn" class="btn btn-primary pull-right" type="submit" value="登录"/> 
						<label class="remember-me" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe" /> 记住我</label>
					</form>
				</div>
			</div>
			<p>
				<span class="help-block">(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)</span>
			</p>
		</div>
	</div>
</body>
</html>
