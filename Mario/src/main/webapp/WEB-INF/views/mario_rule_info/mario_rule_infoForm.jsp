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
            if($('#rule-type').val() == "children number"){
                $("#min-num").css("display","block");
                $("#max-num").css("display","block");
            }else{
                $("#min-num").css("display","none");
                $("#max-num").css("display","none");
            }
        });

        function selectEvent(ee){
            if($(ee).val() == "children number"){
                $("#min-num").css("display","block");
                $("#max-num").css("display","block");
            }else{
                $("#min-num").css("display","none");
                $("#max-num").css("display","none");
            }
        }
        
    </script>

</head>

<body>
    <form:form id="inputForm" modelAttribute="mario_rule_info" action="${ctx}/mario_rule_info/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${mario_rule_info.id}"/>
        <fieldset>
            <legend><small>管理</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Zookeeper cluster:</label>
                <div class="col-sm-10">
                    <select type="text" name="zk_id" class="form-control required">
                        <c:forEach items="${mario_zk_infos.content}" var="mario_zk_info">
                            <option value="${mario_zk_info.id}" title="${mario_zk_info.id}">${mario_zk_info.zk_name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Path:</label>
                <div class="col-sm-10">
                    <input type="text" id="path" name="path"  value="${mario_rule_info.path}" class="form-control required"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Type:</label>
                <div class="col-sm-10">
                    <select id="rule-type" type="text" name="type" class="form-control required" onchange = "selectEvent(this);">
                            <option value="children number" title="children number" <c:if test="${mario_rule_info.type=='children number'}"> selected="selected"</c:if>>children number</option>
                            <option value="node exists" title="node exists" <c:if test="${mario_rule_info.type=='node exists'}"> selected="selected"</c:if>>node exists</option>
                            <option value="data changed" title="data changed" <c:if test="${mario_rule_info.type=='data changed'}"> selected="selected"</c:if>>data changed</option>
                    </select>
                </div>
            </div>
            <div id = "min-num" class="form-group">
                <label for="name" class="col-sm-2 control-label">Min children number:</label>
                <div class="col-sm-10">
                    <input type="text" id="min_children_number" name="min_children_number"  value="${mario_rule_info.min_children_number}" class="form-control "/>
                </div>
            </div>
            <div id = "max-num" class="form-group">
                <label for="name" class="col-sm-2 control-label">Max children number:</label>
                <div class="col-sm-10">
                    <input type="text" id="max_children_number" name="max_children_number"  value="${mario_rule_info.max_children_number}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Phone number:</label>
                <div class="col-sm-10">
                    <input type="text" id="phone_number" name="phone_number"  value="${mario_rule_info.phone_number}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Email address:</label>
                <div class="col-sm-10">
                    <input type="text" id="email_address" name="email_address"  value="${mario_rule_info.email_address}" class="form-control "/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">enable:</label>
                <div class="col-sm-10"> 
                    <select type="text" name="enable" class="form-control required">
                        <option value="true" title="true" <c:if test="${mario_rule_info.enable}"> selected="selected"</c:if>>True</option>
                        <option value="false" title="false" <c:if test="${!mario_rule_info.enable}"> selected="selected"</c:if>>False</option>
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

