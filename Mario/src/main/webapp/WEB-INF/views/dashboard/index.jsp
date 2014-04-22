<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<html>
<head>
	<meta charset="utf-8" />
	<title>首页</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
</head>
<body>
	<c:forEach items="${zk_states}" var="zk_state">
    	<div class="portlet box blue">
    		<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-thumb-tack"></i><a href="${ctx}/graph/zk_state?zk_id=${zk_state.zkInfo.id}">${zk_state.zkInfo.zk_name}</a>
				</div>
				<div class="tools">
					<a href class="collapse">
					</a>
					<!-- <a href class="reload">
					</a> -->
				</div>
			</div>
			<div class="portlet-body">
				<div class="table-responsive">
					<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th colspan="3">Info</th>
								<th colspan="7">State</th>
							</tr>
							<tr>
								<th>Server Id</th>
								<th>Host</th>
								<th>Port</th>
								<th>Mode</th>
								<th>OutStanding</th>
								<th>Zxid</th>
								<th>Node Count</th>
								<th>Total Wactches</th>
								<th>Client Number</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${zk_state.serverStates}" var="serverState">
							<tr>
								<td><a href="${ctx}/graph/server_state?server_id=${serverState.info.id}">${serverState.info.id}</a></td>
								<td>${serverState.info.host}</td>
								<td>${serverState.info.port}</td>
								<td>${serverState.mode}</td>
								<td>${serverState.outStanding}</td>
								<td>
									<script type="text/javascript">
			                            var zkid = ${serverState.zxid};
			                            document.write("0x" + zkid.toString(16))
			                        </script>
								</td>
								<td>${serverState.nodeCount}</td>
								<td>${serverState.totalWatches}</td>
								<td>${serverState.state.client_number}</td>
								<td>
									<c:if test="${serverState.ruok=='imok'}"><span class="label label-success">IMOK</span></c:if>
									<c:if test="${serverState.ruok!='imok'}"><span class="label label-danger">DOWN</span></c:if>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
	        </div>
	    </div>
	</c:forEach>
</body>
</html>
