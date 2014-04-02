<#--This is the form jsp template to generate form jsp file.-->
<#assign class="${sqlTable.entityname?cap_first}">
<#assign package="${prop['project.package']}">
<#assign remark="${sqlTable.remark?default('')}">

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${r"${pageContext.request.contextPath}"}"/>
<html>
<head>
    <title>${remark}管理</title>
    <script>
        $(document).ready(function() {
            //聚焦第一个输入框
            $("#loginName").focus();
        });
        
    </script>
</head>

<body>
    <form:form id="inputForm" modelAttribute="${class?lower_case}" action="${r'${ctx}'}/${class?lower_case}/${r'${action}'}" method="post" class="form-horizontal">
        <#-- primary key info -->
        <#if sqlTable.primaryKeys?exists>
        <#list sqlTable.primaryKeys as key>
        <input type="hidden" name="${key.attrName?lower_case}" value="${r'$'}{${class?lower_case}.${key.attrName?lower_case}}"/>
        </#list>
        </#if>
        <fieldset>
            <legend><small>管理${remark}</small></legend>
            <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
            <#if sqlTable.sqlColumns?exists>
            <#list sqlTable.sqlColumns as column >
            <#if !column.key>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">${column.attrName}:</label>
                <div class="col-sm-10">
                    <input type="text" id="${column.attrName}" name="${column.attrName}"  value="${r'${'}${class?lower_case}.${column.attrName}}" class="form-control <#if !column.nullable>required</#if>"/>
                </div>
            </div>
            </#if>
            </#list>
            </#if>
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

