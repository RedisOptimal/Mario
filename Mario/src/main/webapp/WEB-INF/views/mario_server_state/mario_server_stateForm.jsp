<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>服务器状态信息</title>
    <script>
        $(document).ready(function() {
            //聚焦第一个输入框
            $("#loginName").focus();
        });
        
    </script>
</head>

<body>
    <form:form id="inputForm" modelAttribute="mario_server_state" action="${ctx}/mario_server_state/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${mario_server_state.id}"/>
        <fieldset>
            <legend><small>管理</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Server Id:</label>
                <div class="col-sm-10">
                    <input type="text" id="server_id" name="server_id"  value="${mario_server_state.server_id}" class="form-control required"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Min latency:</label>
                <div class="col-sm-10">
                    <input type="text" id="min_latency" name="min_latency"  value="${mario_server_state.min_latency}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Ave latency:</label>
                <div class="col-sm-10">
                    <input type="text" id="ave_latency" name="ave_latency"  value="${mario_server_state.ave_latency}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Max latency:</label>
                <div class="col-sm-10">
                    <input type="text" id="max_latency" name="max_latency"  value="${mario_server_state.max_latency}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Received:</label>
                <div class="col-sm-10">
                    <input type="text" id="received" name="received"  value="${mario_server_state.received}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Sent:</label>
                <div class="col-sm-10">
                    <input type="text" id="sent" name="sent"  value="${mario_server_state.sent}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Outstanding:</label>
                <div class="col-sm-10">
                    <input type="text" id="outstanding" name="outstanding"  value="${mario_server_state.outstanding}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Zxid:</label>
                <div class="col-sm-10">
                    <input type="text" id="zxid" name="zxid"  value="${mario_server_state.zxid}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Mode:</label>
                <div class="col-sm-10">
                    <input type="text" id="mode" name="mode"  value="${mario_server_state.mode}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Node count:</label>
                <div class="col-sm-10">
                    <input type="text" id="node_count" name="node_count"  value="${mario_server_state.node_count}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Total watches:</label>
                <div class="col-sm-10">
                    <input type="text" id="total_watches" name="total_watches"  value="${mario_server_state.total_watches}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Client number:</label>
                <div class="col-sm-10">
                    <input type="text" id="client_number" name="client_number"  value="${mario_server_state.client_number}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Date:</label>
                <div class="col-sm-10">
                    <input type="text" id="time_stamp" name="time_stamp"  value="${mario_server_state.time_stamp}" class="form-control required"/>
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

