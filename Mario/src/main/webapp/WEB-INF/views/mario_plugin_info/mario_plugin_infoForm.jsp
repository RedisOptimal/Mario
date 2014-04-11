
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
    <form:form id="inputForm" modelAttribute="mario_plugin_info" action="${ctx}/mario_plugin_info/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${mario_plugin_info.id}"/>
        <fieldset>
            <legend><small>管理</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">plugin_name:</label>
                <div class="col-sm-10">
                    <input type="text" id="plugin_name" name="plugin_name"  value="${mario_plugin_info.plugin_name}" class="form-control required"/>
                    <form:errors path="plugin_name" cssClass="error" />
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">zk_id:</label>
                <div class="col-sm-10">
                    <select type="text" id="zk_id" name="zk_id" class="form-control required">
                        <c:forEach items="${mario_zk_infos.content}" var="mario_zk_info">
                            <option value="${mario_zk_info.id}" title="${mario_zk_info.id}">${mario_zk_info.zk_name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">msg_sender:</label>
                <div class="col-sm-10">
                    <input type="text" id="msg_sender" name="msg_sender"  value="${mario_plugin_info.msg_sender}" class="form-control required"/>
                    <form:errors path="msg_sender" cssClass="error" />
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">mail_sender:</label>
                <div class="col-sm-10">
                    <input type="text" id="mail_sender" name="mail_sender"  value="${mario_plugin_info.mail_sender}" class="form-control required"/>
                    <form:errors path="mail_sender" cssClass="error" />
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">phone_number:</label>
                <div class="col-sm-10">
                    <input type="text" id="phone_number" name="phone_number"  value="${mario_plugin_info.phone_number}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">email_address:</label>
                <div class="col-sm-10">
                    <input type="text" id="email_address" name="email_address"  value="${mario_plugin_info.email_address}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">args:</label>
                <div class="col-sm-10">
                    <input type="text" id="args" name="args"  value="${mario_plugin_info.args}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">commit:</label>
                <div class="col-sm-10">
                    <input type="text" id="commit" name="commit"  value="${mario_plugin_info.commit}" class="form-control "/>
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

