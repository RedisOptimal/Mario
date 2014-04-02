<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Test管理</title>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#test_msg").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form:form modelAttribute="test" method="post" id="inputForm" action="${ctx}/test/${action}" cssClass="form-horizontal">
		<input type="hidden" name="id" value="${test.id}"/>
		<fieldset>
			<legend><small>管理Test</small></legend>
			<div class="form-group">
				<label for="test_msg" class="col-sm-2 control-label">test<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<form:input id="msg" path="msg" cssClass="form-control required" minlength="3"/>
					<form:errors path="msg" cssClass="error" />
				</div>
			</div>	
			<div class="form-group">
				<label for="detail" class="col-sm-2 control-label">任务描述<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<form:textarea id="detail" path="detail" cssClass="form-control required"></form:textarea>
					<form:errors path="detail" cssClass="error" />
				</div>
			</div>	
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button id="submit_btn" class="btn btn-primary" type="submit">提交</button>&nbsp;	
					<button id="cancel_btn" class="btn btn-default" type="button" onclick="history.back()">返回</button>
				</div>
			</div>
		</fieldset>
	</form:form>
	</form>
</body>
</html>
