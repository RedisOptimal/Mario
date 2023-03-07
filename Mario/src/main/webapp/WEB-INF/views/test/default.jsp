<%@ page contentType="text/html;charset=UTF-8"%>
<%--<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>展示系统-<sitemesh:write property='title'/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/common/css/default.css" type="text/css" rel="stylesheet" />

<!-- jquery -->
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js" type="text/javascript"></script>
<!-- 自定义函数 -->
<!-- 日期插件 
<script src="${ctx }/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx }/static/My97DatePicker/skin/WdatePicker.css" type="text/javascript"></script>-->
<!-- 图表js -->
<script type="text/javascript" src="${ctx}/static/jquery-highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-highcharts/modules/exporting.js"></script>
<!-- menu -->
<script type="text/javascript"> 
	var leftWidth = "160"; // 左侧窗口大小
	function wSize(){
		var minHeight = 500, minWidth = 980;
		var strs=getWindowSize().toString().split(",");
		$("#menuFrame, #mainFrame, #openClose").height((strs[0]<minHeight?minHeight:strs[0])-$("#header").height()-$("#footer").height()-32);
		$("#openClose").height($("#openClose").height()-5);
		if(strs[1]<minWidth){
			$("#main").css("width",minWidth-10);
			$("html,body").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"});
		}else{
			$("#main").css("width","auto");
			$("html,body").css({"overflow":"hidden","overflow-x":"hidden","overflow-y":"hidden"});
		}
		$("#right").width($("#content").width()-$("#left").width()-$("#openClose").width()-5);
	}
</script>
	<style type="text/css">
		#main {padding:0;margin:0;} #main .container-fluid{padding:0 7px 0 10px;}
		#header {margin:0 0 10px;position:static;} #header li {font-size:14px;_font-size:12px;}
		#header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px;}
		#footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
		#footer, #footer a {color:#999;} 
	</style>
	<script type="text/javascript"> 
		$(document).ready(function() {
			$("#menu a.menu").click(function(){
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				if(!$("#openClose").hasClass("close")){
					$("#openClose").click();
				}
			});
		});
	</script>
<sitemesh:write property='head'/>
</head>

<body>
	<div class="container-fluid">
		<shiro:user>
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		</shiro:user>
		<div id="content" class="row-fluid">
			<shiro:user>
			<div id="left" class="span2" >
			<%@ include file="/WEB-INF/layouts/left.jsp" %>
			</div>
			</shiro:user>
			<div id="right" class="">
				<sitemesh:write property='body'/>
			</div>	
		</div>
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
	<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
