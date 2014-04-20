
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>插件管理</title>
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
                <label for="search_plugin_name">Plugin name：</label> 
                <input type="text" id="search_plugin_name" name="search_plugin_name" class="form-control" style="width: 150px;" value="${param.search_plugin_name}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_zk_id">Zookeeper cluster：</label> 
                <input type="text" id="search_zk_id" name="search_zk_id" class="form-control" style="width: 150px;" value="${param.search_zk_id}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_msg_sender">Msg sender：</label> 
                <input type="text" id="search_msg_sender" name="search_msg_sender" class="form-control" style="width: 150px;" value="${param.search_msg_sender}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_mail_sender">Mail sender：</label> 
                <input type="text" id="search_mail_sender" name="search_mail_sender" class="form-control" style="width: 150px;" value="${param.search_mail_sender}" placeholder="" />
            </div>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th>Plugin name</th>
                <th>Zookeeper cluster</th>
                <th>Msg sender</th>
                <th>Mail sender</th>
                <th>Phone number</th>
                <th>Email address</th>
                <th>Args</th>
                <th>Comment</th>
                <th>管理</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mario_plugin_infos.content}" var="mario_plugin_info">
                <tr>
                    <tr>
                    <td><a href="${ctx}/mario_plugin_info/update/${mario_plugin_info.id}">${mario_plugin_info.plugin_name}</a></td>
                    <td>${mario_plugin_info.cluster_name}</td>
                    <td>${mario_plugin_info.msg_sender}</td>
                    <td>${mario_plugin_info.mail_sender}</td>
                    <td>${mario_plugin_info.phone_number}</td>
                    <td>${mario_plugin_info.email_address}</td>
                    <td>${mario_plugin_info.args}</td>
                    <td>${mario_plugin_info.commit}</td>
                    <td><a href="${ctx}/mario_plugin_info/delete/${mario_plugin_info.id}">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination page="${mario_plugin_infos}" />
    <div class="row"><a class="btn btn-default" href="${ctx}/mario_plugin_info/create">创建</a></div>
</body>
</html>
