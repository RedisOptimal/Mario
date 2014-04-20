
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>集群管理</title>
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
                <label for="search_zk_name">Zookeeper cluster：</label> 
                <input type="text" id="search_zk_name" name="search_zk_name" class="form-control" style="width: 150px;" value="${param.search_zk_name}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_session_timeout">Session timeout：</label> 
                <input type="text" id="search_session_timeout" name="search_session_timeout" class="form-control" style="width: 150px;" value="${param.search_session_timeout}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_observer">Observer：</label> 
                <input type="text" id="search_observer" name="search_observer" class="form-control" style="width: 150px;" value="${param.search_observer}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_observer_auth">Observer auth：</label> 
                <input type="text" id="search_observer_auth" name="search_observer_auth" class="form-control" style="width: 150px;" value="${param.search_observer_auth}" placeholder="" />
            </div>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th>ID</th>
                <th>Zookeeper Cluster</th>
                <th>Session timeout</th>
                <th>Observer</th>
                <th>Observer auth</th>
                <th>管理</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mario_zk_infos.content}" var="mario_zk_info">
                <tr>
                    <tr>
                    <td><a href="${ctx}/mario_zk_info/update/${mario_zk_info.id}">${mario_zk_info.id}</a></td>
                    <td>${mario_zk_info.zk_name}</td>
                    <td>${mario_zk_info.session_timeout}</td>
                    <td>${mario_zk_info.observer}</td>
                    <td>${mario_zk_info.observer_auth}</td>
                    <td><a href="${ctx}/mario_zk_info/delete/${mario_zk_info.id}">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination page="${mario_zk_infos}" />
    <div class="row"><a class="btn btn-default" href="${ctx}/mario_zk_info/create">创建</a></div>
</body>
</html>
