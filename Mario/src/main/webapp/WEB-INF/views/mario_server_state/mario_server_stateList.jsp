<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>服务器状态信息</title>
    <script type="text/javascript" src="${ctx}/static/jquery-jsjava/text/Format.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-jsjava/text/DateFormat.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-jsjava/text/SimpleDateFormat.js"></script>
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
                <label for="search_server_id">server_id：</label> 
                <input type="text" id="search_server_id" name="search_server_id" class="form-control" style="width: 150px;" value="${param.search_server_id}" placeholder="" />
            </div>
            <div class="form-group">
                <label for="search_mode">mode：</label> 
                <input type="text" id="search_mode" name="search_mode" class="form-control" style="width: 150px;" value="${param.search_mode}" placeholder="" />
            </div>
            <button type="submit" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div class="row">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th>server_id</th>
                <th>min_latency</th>
                <th>ave_latency</th>
                <th>max_latency</th>
                <th>received</th>
                <th>sent</th>
                <th>outstanding</th>
                <th>zxid</th>
                <th>mode</th>
                <th>node_count</th>
                <th>total_watches</th>
                <th>client_number</th>
                <th>time_stamp</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mario_server_states.content}" var="mario_server_state">
                <tr>
                    <tr>
                    
                    <td><a href="${ctx}/graph/server_state?server_id=${mario_server_state.server_id}">${mario_server_state.server_id}</a></td>
                    
                    
                    <td>${mario_server_state.min_latency}</td>
                    
                    
                    <td>${mario_server_state.ave_latency}</td>
                    
                    
                    <td>${mario_server_state.max_latency}</td>
                    
                    
                    <td>${mario_server_state.received}</td>
                    
                    
                    <td>${mario_server_state.sent}</td>
                    
                    
                    <td>${mario_server_state.outstanding}</td>
                    
                    
                    <td>
                        <script type="text/javascript">
                            var zkid = ${mario_server_state.zxid};
                            document.write("0x" + zkid.toString(16))
                        </script>
                    </td>
                    
                    
                    <td>${mario_server_state.mode}</td>
                    
                    
                    <td>${mario_server_state.node_count}</td>
                    
                    
                    <td>${mario_server_state.total_watches}</td>
                    
                    
                    <td>${mario_server_state.client_number}</td>
                    
                    
                    <td>
                        <script type="text/javascript">
                            var format = new SimpleDateFormat();
                            var pattern = "yyyy-MM-dd HH:mm:ss";
                            format.applyPattern(pattern); 
                            document.write(format.format(new Date(${mario_server_state.time_stamp})))
                        </script>
                    </td>                   
                    
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <tags:pagination page="${mario_server_states}" />
</body>
</html>
