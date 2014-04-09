<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>数据选择</title>
	<!-- bootstrap3 -->
	<link href="${ctx}/static/bootstrap/3.0.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<!-- jquery -->
	<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- ztree树控件 -->
	<link rel="stylesheet" href="${ctx}/static/jquery-ztree/3.5.14/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script src="${ctx}/static/jquery-ztree/3.5.14/js/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-ztree/3.5.14/js/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var key, lastValue = "", nodeList = [];
		var tree, setting = {view:{selectedMulti:false},check:{enable:"${checked}",nocheckInherit:true},
				data:{simpleData:{enable:true}},
				view:{
					fontCss:function(treeId, treeNode) {
						return (!!treeNode.highlight) ? {"font-weight":"bold"} : {"font-weight":"normal"};
					}
				},
				callback:{beforeClick:function(id, node){
					if("${checked}" == "true"){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}
				}, 
				onDblClick:function(){
					top.$.jBox.getBox().find("button[value='ok']").trigger("click");
				}}};
		$(document).ready(function(){
			$.get("${ctx}/static/menu/treeData?extId=${extId}&module=${module}&t="+new Date().getTime(), function(zNodes){
				// 初始化树结构
				tree = $.fn.zTree.init($("#tree"), setting, zNodes);
				
				// 默认展开一级节点
				var nodes = tree.getNodesByParam("level", 0);
				for(var i=0; i<nodes.length; i++) {
					tree.expandNode(nodes[i], true, false, false);
				}
				// 默认选择节点
				var ids = "${selectIds}".split(",");
				for(var i=0; i<ids.length; i++) {
					var node = tree.getNodeByParam("id", ids[i]);
					if("${checked}" == "true"){
						try{tree.checkNode(node, true, true);}catch(e){}
						tree.selectNode(node, false);
					}else{
						tree.selectNode(node, true);
					}
				}
			});
			key = $("#key");
			key.bind("focus", focusKey).bind("blur", blurKey).bind("change keydown cut input propertychange", searchNode);
		});
	  	function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
			searchNode(e);
		}
		function searchNode(e) {
			// 取得输入的关键字的值
			var value = $.trim(key.get(0).value);
			
			// 按名字查询
			var keyType = "name";
			if (key.hasClass("empty")) {
				value = "";
			}
			
			// 如果和上次一次，就退出不查了。
			if (lastValue === value) {
				return;
			}
			
			// 保存最后一次
			lastValue = value;
			
			// 如果要查空字串，就退出不查了。
			if (value === "") {
				return;
			}
			updateNodes(false);
			nodeList = tree.getNodesByParamFuzzy(keyType, value);
			updateNodes(true);
		}
		function updateNodes(highlight) {
			for(var i=0, l=nodeList.length; i<l; i++) {
				nodeList[i].highlight = highlight;				
				tree.updateNode(nodeList[i]);
				tree.expandNode(nodeList[i].getParentNode(), true, false, false);
			}
		}
		function search() {
			$("#search").slideToggle(200);
			$("#txt").toggle();
			$("#key").focus();
		}
	</script>
</head>
<body>
	<form class="form-horizontal">
		<fieldset>
		<div class="form-group" onclick="search();">
			<i class="icon-search"></i><label id="txt">搜索</label>
		</div>
		<div id="search" class="form-group hide" style="">
			<label for="key" class="control-label" style="">关键字：</label>
			<input type="text" class="empty" id="key" name="key" maxlength="50" style="">
		</div>
		<div id="tree" class="ztree form-group" ></div>
		</fieldset>
	</form>
</body>