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
    <form:form id="inputForm" modelAttribute="mario_node_state" action="${ctx}/mario_node_state/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${mario_node_state.id}"/>
        <fieldset>
            <legend><small>管理</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">zk_id:</label>
                <div class="col-sm-10">
                    <input type="text" id="zk_id" name="zk_id"  value="${mario_node_state.zk_id}" class="form-control required" readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">path:</label>
                <div class="col-sm-10">
                    <input type="text" id="path" name="path"  value="${mario_node_state.path}" class="form-control required" readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">data:</label>
                <div class="col-sm-10">
                    <input type="text" id="data" name="data"  value="${mario_node_state.data}" class="form-control required" readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">data_length:</label>
                <div class="col-sm-10">
                    <input type="text" id="data_length" name="data_length"  value="${mario_node_state.data_length}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">num_children:</label>
                <div class="col-sm-10">
                    <input type="text" id="num_children" name="num_children"  value="${mario_node_state.num_children}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">version:</label>
                <div class="col-sm-10">
                    <input type="text" id="version" name="version"  value="${mario_node_state.version}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">aversion:</label>
                <div class="col-sm-10">
                    <input type="text" id="aversion" name="aversion"  value="${mario_node_state.aversion}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">cversion:</label>
                <div class="col-sm-10">
                    <input type="text" id="cversion" name="cversion"  value="${mario_node_state.cversion}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">ctime:</label>
                <div class="col-sm-10">
                    <input type="text" id="ctime" name="ctime"  value="${mario_node_state.ctimeString}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">mtime:</label>
                <div class="col-sm-10">
                    <input type="text" id="mtime" name="mtime"  value="${mario_node_state.mtimeString}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">czxid:</label>
                <div class="col-sm-10">
                    <input type="text" id="czxid" name="czxid"  value="${mario_node_state.czxid}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">mzxid:</label>
                <div class="col-sm-10">
                    <input type="text" id="mzxid" name="mzxid"  value="${mario_node_state.mzxid}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">pzxid:</label>
                <div class="col-sm-10">
                    <input type="text" id="pzxid" name="pzxid"  value="${mario_node_state.pzxid}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">ephemeral_owner:</label>
                <div class="col-sm-10">
                    <input type="text" id="ephemeral_owner" name="ephemeral_owner"  value="${mario_node_state.ephemeral_owner}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">state_version:</label>
                <div class="col-sm-10">
                    <input type="text" id="state_version" name="state_version"  value="${mario_node_state.state_version}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">state_time:</label>
                <div class="col-sm-10">
                    <input type="text" id="state_time" name="state_time"  value="${mario_node_state.state_time}" class="form-control " readOnly="true"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
                </div>
            </div>
        </fieldset>
    </form:form>
</body>
</html>

