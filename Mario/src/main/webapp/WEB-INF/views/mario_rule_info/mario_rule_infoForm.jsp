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
    <form:form id="inputForm" modelAttribute="mario_rule_info" action="${ctx}/mario_rule_info/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${mario_rule_info.id}"/>
        <fieldset>
            <legend><small>管理</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">zk_id:</label>
                <div class="col-sm-10">
                    <select type="text" name="zk_id" class="form-control required">
                        <c:forEach items="${mario_zk_infos.content}" var="mario_zk_info">
                            <option value="${mario_zk_info.id}" title="${mario_zk_info.id}">${mario_zk_info.zk_name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">path:</label>
                <div class="col-sm-10">
                    <input type="text" id="path" name="path"  value="${mario_rule_info.path}" class="form-control required"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">type:</label>
                <div class="col-sm-10">
                    <select type="text" name="type" class="form-control required">
                            <option value="node exists" title="node exists">node exists</option>
                            <option value="children number" title="children number">children number</option>
                            <option value="data changed" title="data changed">data changed</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">min_children_number:</label>
                <div class="col-sm-10">
                    <input type="text" id="min_children_number" name="min_children_number"  value="${mario_rule_info.min_children_number}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">max_children_number:</label>
                <div class="col-sm-10">
                    <input type="text" id="max_children_number" name="max_children_number"  value="${mario_rule_info.max_children_number}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">phone_number:</label>
                <div class="col-sm-10">
                    <input type="text" id="phone_number" name="phone_number"  value="${mario_rule_info.phone_number}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">email_address:</label>
                <div class="col-sm-10">
                    <input type="text" id="email_address" name="email_address"  value="${mario_rule_info.email_address}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">enable:</label>
                <div class="col-sm-10">
                    <select type="text" name="enable" value="${mario_rule_info.enable}" class="form-control required">
                        <option value="true" title="true">True</option>
                        <option value="false" title="false">False</option>
                    </select>
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

