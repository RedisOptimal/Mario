<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>节点状态</title>
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
                <label for="search_data">data：</label> 
                <input type="text" id="search_data" name="search_data" class="form-control" style="width: 150px;" value="${param.search_data}" placeholder="" />
            </div>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th>zk_id</th>
                <th>path</th>
                <th>data</th>
                <th>data_length</th>
                <th>num_children</th>
                <th>state_time</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mario_node_states.content}" var="mario_node_state">
                <tr>
                    <tr>
                    <td>${mario_node_state.zk_id}</td>
                    <td><a href="${ctx}/mario_node_state/update/${mario_node_state.id}">${mario_node_state.path}</a></td>
                    <td>${mario_node_state.data}</td>
                    <td>${mario_node_state.data_length}</td>
                    <td>${mario_node_state.num_children}</td>
                    <td>${mario_node_state.state_time}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination page="${mario_node_states}" />
</body>
</html>
