<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>登录页面</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/select2/select2_metro.css" />
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME STYLES --> 
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/pages/login.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon"/>
</head>
<!-- BEGIN BODY -->
<body class="login">
	<!-- BEGIN LOGO -->
	<div class="logo">
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal login-form">
			<h2 class="form-title">ZooKeeper监控平台</h2>
			<%
			    String error = (String) request
			            .getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			    if (error != null) {
			%>
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert">×</button>
				登录失败，请重试.
			</div>
			<%
			    }
			%>
			<div class="form-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<input class="form-control placeholder-no-fix required" type="text" autocomplete="off" placeholder="用户名" id="username" name="username"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<input class="form-control placeholder-no-fix required" type="password" autocomplete="off" placeholder="密码" id="password" name="password"/>
				</div>
			</div>
			<div class="form-actions">
				<label class="checkbox" for="rememberMe">
				    <input type="checkbox" id="rememberMe" name="rememberMe" /> 记住我
				</label>
				<button type="submit" class="btn green pull-right">
				登录 <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
			
		</form>
		<!-- END LOGIN FORM -->        
	</div>
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">
		人人网&copy; Company 2013 <a target="_blank"	href="http://www.miibeian.gov.cn/">京ICP证090254号</a>
	</div>
	<!-- END COPYRIGHT -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->   
	<!--[if lt IE 9]>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/respond.min.js"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/excanvas.min.js"></script> 
	<![endif]-->   
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript" ></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>  
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript" ></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>	
	<script type="text/javascript" src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/select2/select2.min.js"></script>     
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/scripts/app.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/scripts/login.js" type="text/javascript"></script> 
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		jQuery(document).ready(function() {   
			App.init();
			Login.init();
			//$("#loginForm").validate();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
