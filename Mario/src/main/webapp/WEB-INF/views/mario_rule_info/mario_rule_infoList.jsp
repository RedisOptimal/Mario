<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>管理</title>
    <script type="text/javascript">
    $(document).ready(function(){
    
    });
    </script>
</head>

<body>
    <c:if test="${not empty message}">
        <div id="message" class="row alert alert-success alert-dismissable"><button data-dismiss="alert" class="close">×</button>${message}</div>
    </c:if>
    <div class="row">
        <form class="form-inline" action="" id="form">
            <div class="form-group">
                <label for="search_zk_id">zk_id：</label> 
                <input type="text" id="search_zk_id" name="search_zk_id" class="form-control" style="width: 150px;" value="${param.search_zk_id}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_path">path：</label> 
                <input type="text" id="search_path" name="search_path" class="form-control" style="width: 150px;" value="${param.search_path}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_type">type：</label> 
                <input type="text" id="search_type" name="search_type" class="form-control" style="width: 150px;" value="${param.search_type}" placeholder="" />
            </div>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th>ID</th>
                <th>Zookeeper cluster</th>
                <th>Path</th>
                <th>Type</th>
                <th>Min children number</th>
                <th>Max children number</th>
                <th>enable</th>
                <th>管理</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mario_rule_infos.content}" var="mario_rule_info">
                <tr>
                    <tr>
                    <td><a href="${ctx}/mario_rule_info/update/${mario_rule_info.id}">${mario_rule_info.id}</a></td>
                    <td>${mario_rule_info.zk_id}</td>
                    <td>${mario_rule_info.path}</td>
                    <td>${mario_rule_info.type}</td>
                    <td>${mario_rule_info.min_children_number}</td>
                    <td>${mario_rule_info.max_children_number}</td>
                    <td>
                        <c:if test="${mario_rule_info.enable}"><span class="label label-success">ENABLE</span></c:if>
                        <c:if test="${!mario_rule_info.enable}"><span class="label label-danger">DISABLE</span></c:if>
                    </td>
                    <td><a href="${ctx}/mario_rule_info/delete/${mario_rule_info.id}">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination page="${mario_rule_infos}" />
    <div class="row"><a class="btn btn-default" href="${ctx}/mario_rule_info/create">创建</a></div>
</body>
</html>
