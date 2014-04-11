
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>集群管理</title>
    <script>
        $(document).ready(function() {
            //聚焦第一个输入框
            $("#loginName").focus();
        });
        
    </script>
</head>

<body>
    <form:form id="inputForm" modelAttribute="mario_zk_info" action="${ctx}/mario_zk_info/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${mario_zk_info.id}"/>
        <fieldset>
            <legend><small>管理</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">zk_name:</label>
                <div class="col-sm-10">
                    <input type="text" id="zk_name" name="zk_name"  value="${mario_zk_info.zk_name}" class="form-control required"/>
                    <form:errors path="zk_name" cssClass="error" />
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">session_timeout:</label>
                <div class="col-sm-10">
                    <input type="text" id="session_timeout" name="session_timeout"  value="${mario_zk_info.session_timeout}" class="form-control required"/>
                    <form:errors path="session_timeout" cssClass="error" />
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">observer:</label>
                <div class="col-sm-10">
                    <input type="text" id="observer" name="observer"  value="${mario_zk_info.observer}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">observer_auth:</label>
                <div class="col-sm-10">
                    <input type="text" id="observer_auth" name="observer_auth"  value="${mario_zk_info.observer_auth}" class="form-control "/>
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

