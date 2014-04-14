
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>服务器管理</title>
    <script type="text/javascript">
    $(document).ready(function(){
    
    });
    </script>
</head>

<body>
    <c:if test="${not empty message}">
        <div id="message" class="row alert ${alertType} alert-dismissable"><button data-dismiss="alert" class="close">×</button>${message}</div>
    </c:if>
    <div class="row">
        <form class="form-inline" action="" id="form">
            <div class="form-group">
                <label for="search_zk_id">zk_id：</label> 
                <input type="text" id="search_zk_id" name="search_zk_id" class="form-control" style="width: 150px;" value="${param.search_zk_id}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_host">host：</label> 
                <input type="text" id="search_host" name="search_host" class="form-control" style="width: 150px;" value="${param.search_host}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_port">port：</label> 
                <input type="text" id="search_port" name="search_port" class="form-control" style="width: 150px;" value="${param.search_port}" placeholder="" />
            </div>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th>id</th>
                <th>zk_id</th>
                <th>host</th>
                <th>port</th>
                <th>管理</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mario_server_infos.content}" var="mario_server_info">
                <tr>
                    <tr>
                    <td><a href="${ctx}/mario_server_info/update/${mario_server_info.id}">${mario_server_info.id}</a></td>
                    <td>${mario_server_info.zk_id}</td>
                    <td>${mario_server_info.host}</td>
                    <td>${mario_server_info.port}</td>
                    <td><a href="${ctx}/mario_server_info/delete/${mario_server_info.id}">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination page="${mario_server_infos}" />
    <div class="row"><a class="btn btn-default" href="${ctx}/mario_server_info/create">创建</a></div>
</body>
</html>
