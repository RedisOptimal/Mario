<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>test管理</title>
	<link rel="stylesheet" href="${ctx}/static/jquery-fileupload/css/jquery.fileupload.css" type="text/css" />
	<script type="text/javascript" src="${ctx}/static/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery-fileupload/js/jquery.iframe-transport.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery-fileupload/js/jquery.fileupload.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#fileupload").fileupload({
			url : '${ctx}/test/upload',
			type : 'post',
			dataType : 'json',
			acceptFileTypes : '/(\.|\/)(xls?x)$/i', 
			maxFileSize : '5000000',//5M
			maxNumberOfFiles : 10,
			done: function (e, data) {
				console.log(data);
				console.log(data.result);
				console.log(data.result.files);
			},
			add: function (e, data) {
				$("#progress").css("display", "block");
		        data.submit();
		    }, 
			progressall: function (e, data) {            
				$('#progress .progress-bar').css('width', '');
				var progress = parseInt(data.loaded / data.total * 100, 10);            
				$('#progress .progress-bar').css('width', progress + '%');        
			} 
		});
		
	});

	function submitSearch(){
		$("#form").attr("action", "${ctx}/test");
		$("#form").attr("method", "post");
		$("#form").submit();  
	}
	
	function exportExcel(){
		$("#form").attr("action", "${ctx}/test/export");
		$("#form").attr("method", "post");
		$("#form").submit();  
	}
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="row alert alert-success alert-dismissable"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<form class="form-inline" action="" id="form">
			<div class="form-group">
				<label for="search_msg">名称：</label> 
				<input type="text" id="search_msg" name="search_msg" class="form-control" style="width: 150px;" value="${param.search_msg}" placeholder="名称" />
			</div>
			<div class="form-group">
				<label for="beginDate">日期范围：&nbsp;</label>
				<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="form-control input Wdate" style="width: 200px; cursor: auto;" value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" placeholder="日期"/>
			</div>
			<button type="button" class="btn btn-default pull-right" onclick="submitSearch();">查询</button>
			<span class="btn btn-default fileinput-button pull-right">
		        <span>导入</span>
	        	<input id="fileupload" type="file" name="files" multiple="" accept="xls|xlsx" />
			</span>
			<button type="button" class="btn btn-default pull-right" onclick="exportExcel();">导出</button>
		</form>
	</div>
    <p/>
	<div id="progress" class="row progress" style="display: none;">
        <div class="progress-bar progress-bar-success"></div>
    </div>
	<div class="row">
		<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
			<thead><tr><th>名称</th><th>管理</th></tr></thead>
			<tbody>
			<c:if test="${empty tests.content}">
				<tr>
					<td colspan="2">暂无数据</td>
				</tr>
			</c:if>
			<c:forEach items="${tests.content}" var="test">
				<tr>
					<td width="70%"><a href="${ctx}/test/update/${test.id}">${test.msg}</a></td>
					<td width="30%"><a href="${ctx}/test/delete/${test.id}">删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<tags:pagination page="${tests}" />
	<div class="row"><a class="btn btn-default" href="${ctx}/test/create">创建test</a></div>
</body>
</html>
