<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springside.org.cn/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>角色管理</title>
	<script>
		$(document).ready(function() {
			$("#account-tab").addClass("active");
			
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						remote: "${ctx}/account/role/checkRoleName?oldRoleName=" + encodeURIComponent('${role.name}')
					},
				},
				messages: {
					loginName: {
						remote: "角色名已存在"
					},
				},
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(':radio') || element.is(":checkbox") )
						error.appendTo(element.parent().next());
					else
						error.insertAfter(element);
				}
			});
			
			//初始化setting
			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			//初始化数据
			var zNodes=[
					<c:forEach items="${allMenus}" var="menu">{id:${menu.id}, pId:${not empty menu.parentId?menu.parentId:0}, name:"${not empty menu.parentId?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
			
			//聚焦第一个输入框
			$("#name").focus();
		});
		
	</script>
</head>

<body>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/account/role/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${role.id}"/>
		<fieldset>
			<legend><small>管理角色</small></legend>
			<div id="messageBox" class="alert alert-danger" style="display:none">输入有误，请先更正。</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">角色名<span class="required">*</span>:</label>
				<div class="col-sm-10">
					<form:input path="name" id="name" cssClass="form-control required"/>
					<form:errors path="name" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label for="detail" class="col-sm-2 control-label">详细:</label>
				<div class="col-sm-10">
					<input type="text" id="detail" name="detail" value="${role.detail}" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="menu" class="col-sm-2 control-label">菜单:</label>
				<div class="col-sm-10">
					<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
					<input id="menuIds" name="menuIds" type="hidden"/>
				</div>
			</div>
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
