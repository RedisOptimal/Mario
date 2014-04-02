<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>ZooKeeper监控平台-<sitemesh:title/></title>
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
	<!-- BEGIN PAGE LEVEL PLUGIN STYLES --> 
	<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${ctx}/static/jquery-ztree/3.5.14/css/zTreeStyle/zTreeStyle.css" type="text/css" />
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/My97DatePicker/skin/WdatePicker.css" type="text/css" rel="stylesheet" />
	<!-- END PAGE LEVEL PLUGIN STYLES -->
	<!-- BEGIN THEME STYLES --> 
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${ctx}/static/bootstrap-metronic/template_content/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type=”image/x-icon”/>
	
	<!-- BEGIN CORE PLUGINS -->   
	<!--[if lt IE 9]>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/respond.min.js"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/excanvas.min.js"></script> 
	<![endif]-->   
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>   
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript" ></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>  
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript" ></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-ztree/3.5.14/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-ztree/3.5.14/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<sitemesh:head/>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<shiro:user>
	<%@ include file="/WEB-INF/layouts/header.jsp"%>
	</shiro:user>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="/WEB-INF/layouts/left.jsp" %>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<sitemesh:body/>
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/jquery.vmap.js" type="text/javascript"></script>   
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.russia.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.world.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.europe.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.germany.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.usa.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata.js" type="text/javascript"></script>  
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/flot/jquery.flot.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/flot/jquery.flot.resize.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery.pulsate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap-daterangepicker/moment.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>     
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/gritter/js/jquery.gritter.js" type="text/javascript"></script>
	<script src="${ctx}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<!-- IMPORTANT! fullcalendar depends on jquery-ui-1.10.3.custom.min.js for drag & drop support -->
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/fullcalendar/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/plugins/jquery.sparkline.min.js" type="text/javascript"></script>  
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/scripts/app.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-metronic/template_content/assets/scripts/index.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->  
	<script>
		jQuery(document).ready(function() {    
		   App.init(); // initlayout and core plugins
		   Index.init();
 		   Index.initCharts(); // init index page's custom scripts
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
