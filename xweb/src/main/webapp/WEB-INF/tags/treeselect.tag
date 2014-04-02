<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框"%>
<%@ attribute name="extId" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="notAllowSelectRoot" type="java.lang.Boolean" required="false" description="不允许选择根节点"%>
<%@ attribute name="notAllowSelectParent" type="java.lang.Boolean" required="false" description="不允许选择父节点"%>
<%@ attribute name="module" type="java.lang.String" required="false" description="过滤栏目模型（只显示指定模型，仅针对CMS的Category树）"%>
<%@ attribute name="selectScopeModule" type="java.lang.Boolean" required="false" description="选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）"%>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="input-group">
	<input id="${id}Id" name="${name}" class="${cssClass}" type="hidden" value="${value}"/>
	<input id="${id}Name" name="${labelName}" readonly="readonly" type="text" value="${labelValue}" maxlength="50" class="${cssClass}" style="${cssStyle}" />
 	<span class="input-group-btn">
		<a id="${id}Button" href="#" onclick="return false;" class="btn btn-default ${disabled}">&nbsp;<i class="glyphicon glyphicon-search"></i>&nbsp;</a>&nbsp;&nbsp;
    </span>
</div>
<!-- modal  -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">选择菜单</h4>
      </div>
      <div class="modal-body" id="myModelContent">
		<div class="scroller" style="height:300px" data-always-visible="1" data-rail-visible1="1">
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<label for="key" class="control-label" style="">关键字：</label>
						<input type="text" class="form-control" id="key" name="key" maxlength="50" style="width: 150px;" />
						<a href="#" onclick="return false;search();" class="btn">&nbsp;<i class="glyphicon glyphicon-search"></i>搜索</a>&nbsp;
					</div>
					<div id="tree" class="ztree" ></div>
				</div>
			</div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="myModelOK();">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
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
				myModelOK();
			}
		}
	};
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
	
	$("#${id}Button").click(function(){
		//使用model窗口打开ztree
		$('#myModal').modal();
	});
	
	function myModelOK(){
		var ids = [], names = [], nodes = [];
		nodes = tree.getSelectedNodes();

		for(var i=0; i<nodes.length; i++) {
			ids.push(nodes[i].id);
			names.push(nodes[i].name);
			break; // 如果为非复选框选择，则返回第一个选择 
		}
		$("#${id}Id").val(ids);
		$("#${id}Name").val(names);
		
		$('#myModal').modal('hide');
	}
</script>