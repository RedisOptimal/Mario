
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>管理</title>
    <script>
        $(document).ready(function() {
            //聚焦第一个输入框
            $("#loginName").focus();
        });
        
    </script>
</head>

<body>
    <form:form id="inputForm" modelAttribute="mario_server_info" action="${ctx}/mario_server_info/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${mario_server_info.id}"/>
        <fieldset>
            <legend><small>管理</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">zk_id:</label>
                <div class="col-sm-10">
                    <input type="text" id="zk_id" name="zk_id"  value="${mario_server_info.zk_id}" class="form-control required"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">host:</label>
                <div class="col-sm-10">
                    <input type="text" id="host" name="host"  value="${mario_server_info.host}" class="form-control required"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">port:</label>
                <div class="col-sm-10">
                    <input type="text" id="port" name="port"  value="${mario_server_info.port}" class="form-control required"/>
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

