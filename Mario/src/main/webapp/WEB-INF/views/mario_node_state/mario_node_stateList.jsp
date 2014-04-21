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
                <label for="search_zk_id">Zookeeper cluster：</label> 
                <select type="text" id="search_zk_id" name="search_zk_id" class="form-control required" style="width: 150px;">
                    <option>no selected</option>
                    <c:forEach items="${mario_zk_infos}" var="mario_zk_info">
                        <option value="${mario_zk_info.id}" title="${mario_zk_info.id}"<c:if test="${param.search_zk_id==mario_zk_info.id}"> selected="selected"</c:if>>${mario_zk_info.zk_name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="search_path">Path：</label> 
                <input type="text" id="search_path" name="search_path" class="form-control" style="width: 150px;" value="${param.search_path}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_data">Data：</label> 
                <input type="text" id="search_data" name="search_data" class="form-control" style="width: 150px;" value="${param.search_data}" placeholder="" />
            </div>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th>Zookeeper cluster</th>
                <th>Path</th>
                <th>Data</th>
                <th>Data length</th>
                <th>Num children</th>
                <th>Date</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mario_node_states.content}" var="mario_node_state">
                <tr>
                    <tr>
                    <td>${mario_node_state.zk_id}</td>
                    <td>${mario_node_state.path}</td>
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
