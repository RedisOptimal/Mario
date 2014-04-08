<#--This is the list jsp template to generate list jsp file.-->

<#assign class="${sqlTable.entityname?cap_first}">
<#assign package="${prop['project.package']}">
<#assign remark="${sqlTable.remark?default('')}">
<#assign id>
<a href="${r'${ctx}'}/${class?lower_case}/update/<#if sqlTable.primaryKeys?exists><#list sqlTable.primaryKeys as key><#if key_index!=0>/</#if>${r'$'}{${class?lower_case}.${key.attrName?lower_case}}</#list></#if>"><#rt>
</#assign>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${r"${pageContext.request.contextPath}"}"/>

<html>
<head>
    <title>${remark}管理</title>
    <script type="text/javascript">
    $(document).ready(function(){
    
    });
    </script>
</head>

<body>
    <c:if test="${r"${not empty message}"}">
        <div id="message" class="row alert alert-success alert-dismissable"><button data-dismiss="alert" class="close">×</button>${r"${message}"}</div>
    </c:if>
    <div class="row">
        <form class="form-inline" action="" id="form">
        	<#if sqlTable.sqlColumns?exists>
            <#list sqlTable.sqlColumns as column >
            <#if !column.key>
            <div class="form-group">
                <label for="search_${column.attrName?lower_case}">${column.attrName?lower_case}：</label> 
                <input type="text" id="search_${column.attrName?lower_case}" name="search_${column.attrName?lower_case}" class="form-control" style="width: 150px;" value="${r"$"}{param.search_${column.attrName?lower_case}}" placeholder="${column.remark}" />
            </div>
            </#if>
            </#list>
            </#if>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <#list sqlTable.sqlColumns as column >
                <th>${column.attrName}</th>
                </#list>
                <th>管理</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${r'${'}${class?lower_case}s.content}" var="${class?lower_case}">
                <tr>
                    <tr>
                    <#if sqlTable.sqlColumns?exists>
                    <#list sqlTable.sqlColumns as column >
                    <#if !column.key && column_index == sqlTable.primaryKeys?size>
                    <td>${id}${r'$'}{${class?lower_case}.${column.attrName?lower_case}}</a></td>
                    <#elseif !column.key>
                    <td>${r'$'}{${class?lower_case}.${column.attrName?lower_case}}</td>
                    </#if>
                    </#list>
                    </#if>
                    <td><a href="${r'${ctx}'}/${class?lower_case}/delete/<#if sqlTable.primaryKeys?exists><#list sqlTable.primaryKeys as key><#if key_index!=0>/</#if>${r'$'}{${class?lower_case}.${key.attrName?lower_case}}</#list></#if>">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination page="${r'$'}{${class?lower_case}s}" />
    <div class="row"><a class="btn btn-default" href="${r'${ctx}'}/${class?lower_case}/create">创建${remark}</a></div>
</body>
</html>
