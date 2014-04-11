/**
 * 绘图js
 */
var draw = {};
draw.namespace = function(str){
	var arr = str.split("."), o = draw;
	for ( i = (arr[0] == "draw") ? 1 : 0; i < arr.length; i++) {
		o[arr[i]] = o[arr[i]] || {};
		o = o[arr[i]];
	}
}

//绘制函数关系图
draw.namespace("func");
draw.func.clearSVG = function(){
	d3.select("svg").remove();
}

//绘图
//初始化节点
function initNode(serviceName, width, height) {	
 	var nodeArr = new Array();

 	var node = new Object();
 	node.id = 0;
 	node.serviceId = serviceName;
 	node.x = width/2;
 	node.y = height/2;
 	nodeArr.push(node);
 	return nodeArr;
}
//初始化父节点
function initParentNodeArr(serviceIdArr, width, height) {
 	var nodeArr = new Array();
 	for(var i = 0; i < serviceIdArr.length; i++) {
 		var node = new Object();
 		node.id = i;
 		node.eid = serviceIdArr[i].eid;
 		node.serviceId = serviceIdArr[i].servicename + " " + serviceIdArr[i].functionname;
 		node.x = width*(i+1)/(serviceIdArr.length+1);
 		node.y = height/4;
 		nodeArr.push(node);
 	}
 	return nodeArr;
}
//初始化子节点
function initChildNodeArr(serviceIdArr, width, height) {
 	var nodeArr = new Array();
 	for(var i = 0; i < serviceIdArr.length; i++) {
 		var node = new Object();
 		node.id = i;
 		node.eid = serviceIdArr[i].eid;
 		node.serviceId = serviceIdArr[i].servicename + " " + serviceIdArr[i].functionname;
 		node.x = width*(i+1)/(serviceIdArr.length+1);
 		node.y = height*3/4;
 		nodeArr.push(node);
 	}
	return nodeArr;
}
//初始化节点，父节点，子节点
function initLinks(service, parentService, childService) {
 	var linkArr = new Array();
 	for(var i = 0; i < parentService.length; i++) {
 		var link = new Object();
 		link.source = service[0];
 		link.target = parentService[i];
 		link.left = false;
 		link.right = true;
 		linkArr.push(link);
 	}

 	for(var i = 0; i < childService.length; i++) {
 		var link = new Object();
 		link.source = childService[i];
 		link.target = service[0];
 		link.left = false;
 		link.right = true;
 		linkArr.push(link);
 	}

 	return linkArr;
 }
//
function showServiceDetail(d){
	$.ajax({
		url : "../../../chains/nodedetail",
		data : {
			eid : d.eid
		},
		dataType : "json",
		success : function(data) {
			$("#nodedetail_model_body_table").empty();
			
			var valueArr = new Array();
			valueArr.push("<thead><th width='30%'>服务属性</th><th width='70%'>值</th></thead>");
			valueArr.push("<tr><td>eid</td><td>" + data.detail.eid + "</td></tr>");
			valueArr.push("<tr><td>服务编号</td><td>" + data.detail.sid + "</td></tr>");
			valueArr.push("<tr><td>版本</td><td>" + data.detail.sv + "</td></tr>");
			valueArr.push("<tr><td>函数编号</td><td>" + data.detail.fid + "</td></tr>");
			valueArr.push("<tr><td>服务名称</td><td>" + data.detail.servicename + "</td></tr>");
			valueArr.push("<tr><td>服务类名</td><td>" + data.detail.serviceclass + "</td></tr>");
			valueArr.push("<tr><td>函数名</td><td>" + data.detail.functionname + "</td></tr>");
			
			$("#nodedetail_model_body_table").append(valueArr.join());
			
			$('#nodedetail_modal').modal({
			    backdrop:true,
			    keyboard:true,
			    show:true
			});
		},
		error :function(){
			
		}
	});
}

//参数divid DIV的名称; serviceFuncName函数名称，sourceServiceFuncName为调用函数，targetServiceFuncName被调用函数
draw.func.drawFuncRelation = function(divid, serviceName, sourceServiceFuncName, targetServiceFuncName){
	var width = 840, height = 400,
    colors = d3.scale.category10();

	var svg = d3.select("#" + divid).append('svg').attr("class", "chart").attr('width', width).attr('height', height);//初始化svg

	var serviceId;
	var parentServiceId;
	var childServiceId;
	
	var service = initNode(serviceName, width, height);
	var parentService = initParentNodeArr(sourceServiceFuncName, width, height);
	var childService = initChildNodeArr(targetServiceFuncName, width, height);

    //画圆形 serviceCircle
	svg.append('svg:g').selectAll('s')
	.data(service)
	.enter().append('svg:g')
     .append('svg:circle')
     .attr('class', 'node')
     .attr('cx', function(d){return d.x;})
     .attr('cy', function(d){return d.y;})
     .attr('r', 10)
     .style('fill', function(d) {return "steelblue";})
     .style('stroke', function(d) { return "black"; })
     .classed('reflexive', function(d) { return false; });

	//parentCircle
     svg.append('svg:g').selectAll('parent').data(parentService)
 	 .enter().append('svg:g')
     .append('svg:circle')
     .attr('class', 'node')
     .attr('cx', function(d){return d.x;})
     .attr('cy', function(d){return d.y;})
     .attr('r', 10)
     .style('fill', d3.rgb(0,255,0))
     .style('stroke', function(d) { return "black"; })
     .on('mousedown', function(d){showServiceDetail(d);})
     .classed('reflexive', function(d) { return true; });


     var childCircle = svg.append('svg:g').selectAll('child').data(childService);
 	 var child = childCircle.enter().append('svg:g');
     child.append('svg:circle')
     .attr('class', 'node')
     .attr('cx', function(d){return d.x;})
     .attr('cy', function(d){return d.y;})
     .attr('r', 10)
     .attr('stroke', 'black')
     .style('fill', d3.rgb(255,0,0))
     .style('stroke', function(d) { return "black"; })
     .on('mousedown', function(d){showServiceDetail(d);})
     .classed('reflexive', function(d) { return true; });


    svg.selectAll("parenttext")
	.data(parentService)
	.enter()
    .append('svg:text')
	.attr('x', function(d) {return d.x})
	.attr('y', function(d) {return d.y-40})
	.attr('class', 'id')
	.text(function(d) { return d.serviceId; });

	svg.selectAll("childtext")
	.data(childService)
	.enter()
    .append('svg:text')
	.attr('x', function(d) {return d.x})
	.attr('y', function(d) {return d.y+40})
	.attr('class', 'id')
	.text(function(d) { return d.serviceId; });

	svg.selectAll("servicetext")
	.data(service)
	.enter()
    .append('svg:text')
	.attr('x', function(d) {return d.x})
	.attr('y', function(d) {return d.y})
	.attr('class', 'id')
	.text(function(d) { return d.serviceId; });

     var links = new Array();
     links = initLinks(service, parentService, childService);

     svg.append('svg:defs').append('svg:marker')
    .attr('id', 'end-arrow')
    .attr('viewBox', '0 -5 10 10')
    .attr('refX', 6)
    .attr('markerWidth', 3)
    .attr('markerHeight', 3)
    .attr('orient', 'auto')
  	.append('svg:path')
    .attr('d', 'M0,-5L10,0L0,5')
    .attr('fill', '#000');

	svg.append('svg:defs').append('svg:marker')
    .attr('id', 'start-arrow')
    .attr('viewBox', '0 -5 10 10')
    .attr('refX', 4)
    .attr('markerWidth', 3)
    .attr('markerHeight', 3)
    .attr('orient', 'auto')
  	.append('svg:path')
    .attr('d', 'M10,-5L0,0L10,5')
    .attr('fill', '#000');

    var path = svg.append('svg:g').selectAll('path');
    path = path.data(links);
    path.enter().append('svg:path')
    .attr('class', 'link')
    .attr('d', function(d) {
	    var deltaX = d.target.x - d.source.x,
	        deltaY = d.target.y - d.source.y,
	        dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY),
	        normX = deltaX / dist,
	        normY = deltaY / dist,
	        sourcePadding = d.left ? 30 : 25,
	        targetPadding = d.right ? 30 : 25,
	        sourceX = d.source.x + (sourcePadding * normX),
	        sourceY = d.source.y + (sourcePadding * normY),
	        targetX = d.target.x - (targetPadding * normX),
	        targetY = d.target.y - (targetPadding * normY);
	    return 'M' + sourceX + ',' + sourceY + 'L' + targetX + ',' + targetY;
	  })
    .classed('selected', function(d) { return false; })
    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });
	
}

//绘制调用链模式拓扑图
//展示节点详细信息
draw.func.drawCallChain = function(divid, patterns, details){
	var width = 840, height = 400, colors = d3.scale.category10();
	var svg = d3.select("#" + divid).append('svg').attr('width', width).attr('height', height);//初始化svg
	
	// set up initial nodes and links
	//  - nodes are known by 'id', not by index in array.
	//  - reflexive edges are indicated on the node (as a bold black circle).
	//  - links are always source < target; edge directions are set by 'left' and 'right'.
	// define arrow markers for graph links
	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'end-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 6)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M0,-5L10,0L0,5')
	    .attr('fill', '#000');

	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'start-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 4)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M10,-5L0,0L10,5')
	    .attr('fill', '#000');

	// line displayed when dragging new nodes
	var drag_line = svg.append('svg:path')
	  .attr('class', 'link dragline hidden')
	  .attr('d', 'M0,0L0,0');

	// handles to link and node element groups
	var path = svg.append('svg:g').selectAll('path'),
	    circle = svg.append('svg:g').selectAll('g');

	// mouse event vars
	var selected_node = null,
	    selected_link = null,
	    mousedown_link = null,
	    mousedown_node = null,
	    mouseup_node = null;

	function resetMouseVars() {
	  mousedown_node = null;
	  mouseup_node = null;
	  mousedown_link = null;
	}

	// update force layout (called automatically each iteration)
	function tick() {
	  // draw directed edges with proper padding from node centers
	  path.attr('d', function(d) {
	    var deltaX = d.target.x - d.source.x,
	        deltaY = d.target.y - d.source.y,
	        dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY),
	        normX = deltaX / dist,
	        normY = deltaY / dist,
	        sourcePadding = d.left ? 17 : 12,
	        targetPadding = d.right ? 17 : 12,
	        sourceX = d.source.x + (sourcePadding * normX),
	        sourceY = d.source.y + (sourcePadding * normY),
	        targetX = d.target.x - (targetPadding * normX),
	        targetY = d.target.y - (targetPadding * normY);
	    return 'M' + sourceX + ',' + sourceY + 'L' + targetX + ',' + targetY;
	  });

	  circle.attr('transform', function(d) {
	    return 'translate(' + d.x + ',' + d.y + ')';
	  });
	}

	// update graph (called when needed)
	function restart() {
	  // path (link) group
	  path = path.data(links);

	  // update existing links
	  path.classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });


	  // add new links
	  path.enter().append('svg:path')
	    .attr('class', 'link')
	    .classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });

	  // remove old links
	  //path.exit().remove();


	  // circle (node) group
	  // NB: the function arg is crucial here! nodes are known by id, not by index!
	  circle = circle.data(nodes, function(d) { return d.id; });

	  // update existing nodes (reflexive & selected visual states)
	  circle.selectAll('circle')
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); });

	  // add new nodes
	  var g = circle.enter().append('svg:g');

	  g.append('svg:circle')
	    .attr('class', 'node')
	    .attr('r', 12)
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); })
	    .style('stroke', function(d) { return d3.rgb(colors(d.id)).darker().toString(); })
	    .on('mousedown', function(d){showNodeDetail(d);})
	    .call(force.drag);
	    

	  // show node IDs
	  g.append('svg:text')
	      .attr('x', 0)
	      .attr('y', 4)
	      .attr('class', 'id')
	      .text(function(d) { return d.serviceid; });

	  // remove old nodes
	  circle.exit().remove();

	  // set the graph in motion
	  force.start();
	}

	function initNodes(details) {
		for(var i = 0; i < details.length; i++) {
			var node = new Object();
			node.id = i;
			node.eid = details[i].eid;
			node.serviceid = details[i].serviceclass + " " + details[i].functionname;
			nodes.push(node);
		}
	}

	function initLinks(patterns) {
		for(var i = 0; i < patterns.length; i++) {
			var link = new Object();
			for(var j = 0; j < nodes.length; j++ ){
				var node = nodes[j];
				if(node.eid == patterns[i].source.eid){
					link.source = nodes[j];
				}
				if(node.eid == patterns[i].target.eid){
					link.target = nodes[j];
				}
			}
			link.left = false;
			link.right = true;
			links.push(link);
		}
	}

	var nodes = new Array(),
	  lastNodeId = 0,
	  links = new Array();

		// init D3 force layout
		var force = d3.layout.force()
	    .nodes(nodes)
	    .links(links)
	    .size([width, height])
	    .linkDistance(100)
	    .charge(-500)
	    .on('tick', tick);

	initNodes(details);
	initLinks(patterns);
	restart();
}

function showNodeDetail(d){
	$.ajax({
		url : "../../chains/nodedetail",
		data : {
			eid : d.eid
		},
		dataType : "json",
		success : function(data) {
			$("#nodedetail_model_body_table").empty();
			
			var valueArr = new Array();
			valueArr.push("<thead><th width='30%'>服务属性</th><th width='70%'>值</th></thead>");
			valueArr.push("<tr><td>eid</td><td>" + data.detail.eid + "</td></tr>");
			valueArr.push("<tr><td>服务编号</td><td>" + data.detail.sid + "</td></tr>");
			valueArr.push("<tr><td>版本</td><td>" + data.detail.sv + "</td></tr>");
			valueArr.push("<tr><td>函数编号</td><td>" + data.detail.fid + "</td></tr>");
			valueArr.push("<tr><td>服务名称</td><td>" + data.detail.servicename + "</td></tr>");
			valueArr.push("<tr><td>服务类名</td><td>" + data.detail.serviceclass + "</td></tr>");
			valueArr.push("<tr><td>函数名</td><td>" + data.detail.functionname + "</td></tr>");
			
			$("#nodedetail_model_body_table").append(valueArr.join());
			
			$('#nodedetail_modal').modal({
			    backdrop:true,
			    keyboard:true,
			    show:true
			});
		},
		error :function(){
			
		}
	});
}

function iteratorPatternsTree(patterns){
	
}

//绘制调用链实时图
draw.func.drawRealtimeCallChain = function(divid, receives, sends){
	var width = 840, height = 600, colors = d3.scale.category10();
	var svg = d3.select("#" + divid).append('svg').attr('width', width).attr('height', height);//初始化svg
	
	// set up initial nodes and links
	//  - nodes are known by 'id', not by index in array.
	//  - reflexive edges are indicated on the node (as a bold black circle).
	//  - links are always source < target; edge directions are set by 'left' and 'right'.
	// define arrow markers for graph links
	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'end-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 6)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M0,-5L10,0L0,5')
	    .attr('fill', '#000');

	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'start-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 4)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M10,-5L0,0L10,5')
	    .attr('fill', '#000');

	// line displayed when dragging new nodes
	var drag_line = svg.append('svg:path')
	  .attr('class', 'link dragline hidden')
	  .attr('d', 'M0,0L0,0');

	// handles to link and node element groups
	var path = svg.append('svg:g').selectAll('path'),
	    circle = svg.append('svg:g').selectAll('g');

	// mouse event vars
	var selected_node = null,
	    selected_link = null,
	    mousedown_link = null,
	    mousedown_node = null,
	    mouseup_node = null;

	function resetMouseVars() {
	  mousedown_node = null;
	  mouseup_node = null;
	  mousedown_link = null;
	}

	// update force layout (called automatically each iteration)
	function tick() {
	  // draw directed edges with proper padding from node centers
	  path.attr('d', function(d) {
	    var deltaX = d.target.x - d.source.x,
	        deltaY = d.target.y - d.source.y,
	        dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY),
	        normX = deltaX / dist,
	        normY = deltaY / dist,
	        sourcePadding = d.left ? 17 : 12,
	        targetPadding = d.right ? 17 : 12,
	        sourceX = d.source.x + (sourcePadding * normX),
	        sourceY = d.source.y + (sourcePadding * normY),
	        targetX = d.target.x - (targetPadding * normX),
	        targetY = d.target.y - (targetPadding * normY);
	    return 'M' + sourceX + ',' + sourceY + 'L' + targetX + ',' + targetY;
	  });

	  circle.attr('transform', function(d) {
	    return 'translate(' + d.x + ',' + d.y + ')';
	  });
	}

	// update graph (called when needed)
	function restart() {
	  // path (link) group
	  path = path.data(links);

	  // update existing links
	  path.classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });


	  // add new links
	  path.enter().append('svg:path')
	    .attr('class', 'link')
	    .classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });

	  // remove old links
	  //path.exit().remove();


	  // circle (node) group
	  // NB: the function arg is crucial here! nodes are known by id, not by index!
	  circle = circle.data(nodes, function(d) { return d.id; });

	  // update existing nodes (reflexive & selected visual states)
	  circle.selectAll('circle')
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); });

	  // add new nodes
	  var g = circle.enter().append('svg:g');

	  g.append('svg:circle')
	    .attr('class', 'node')
	    .attr('r', 12)
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); })
	    .style('stroke', function(d) { return d3.rgb(colors(d.id)).darker().toString(); })
	    .call(force.drag)
	    .on('mousedown', function(d){showRealTimeNodeDetail(d);});	    

	  // show node IDs
	  g.append('svg:text')
	      .attr('x', 0)
	      .attr('y', 4)
	      .attr('class', 'id')
	      .text(function(d) { return d.serviceid; });

	  // remove old nodes
	  circle.exit().remove();

	  // set the graph in motion
	  force.start();
	}

	function initNodes(receives, sends) {
		for(var i = 0; i < receives.length; i++){
			var node = new Object();
			node.id = i;
			node.type = "receive";
			node.node = receives[i].node;
			if(i === 0){
				node.serviceid = "root";
			}else{
				node.serviceid = "receive:" + node.node.serviceName + " " + node.node.functionName;
			}
			nodes.push(node);
		}
		
		for(var j = 0; j < sends.length; j++){
			var node = new Object();
			node.id = j + receives.length;
			node.type = "send";
			node.node = sends[j].node;
			node.serviceid = "send:" + node.node.funcId;
			nodes.push(node);
		}
		
	}

	function initLinks(receives, sends) {
		for ( var i = 0; i < receives.length; i++) {
			var receive = receives[i];
			var receiveNode = receive.node;
			for ( var j = 0; (receive.intChildren !== undefined) &&  (j < receive.intChildren.length); j++) {//receiveNode has intChildren should draw link
				var link = new Object();
				link.source = nodes[i];
				link.target = nodes[receives.length + receive.intChildren[j]];
				link.left = false;
				link.right = true;
				links.push(link);
			}
		}
		
		for ( var i = 0; i < sends.length; i++) {
			var send = sends[i];
			var sendNode = send.node;
			if(isInReceiveIndex(i, receives)){
				var link = new Object();
				link.source = nodes[receives.length + i];
				link.target = nodes[send.intChildIndex];
				link.left = false;
				link.right = true;
				links.push(link);
			}
		}

	}
	
	//receive not in send indexs
	function isInSendIndex(index, sends){
		for ( var i = 0; i < sends.length; i++) {
			var send = sends[i];
			var sendNode = send.node;
			if (index == send.intChildIndex) {
				return true;
			}
		}
		return false;
	}
	
	//send not in receive indexs
	function isInReceiveIndex(index, receives){
		for ( var i = 0; i < receives.length; i++) {
			var receive = receives[i];
			var receiveNode = receive.node;
			for ( var j = 0; (receive.intChildren !== undefined) && (j < receive.intChildren.length); j++) {
				if (index == receive.intChildren[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	function linkReceives(receives, sends, rootindex){
		var receive = receives[rootindex];
		var receiveNode = receive.node;
		//links
		for ( var i = 0; i < receive.intChildren.length; i++) {
			console.log("linkReceives for: " + i);
			linkSends(receives, sends, i);
		}
	}
	
	function linkSends(receives, sends, rootindex){
		var send = sends[rootindex];
		var sendNode = send.node;
		//links
		linkReceives(receives, sends, rootindex);
	}

	var nodes = new Array(),
	  lastNodeId = 0,
	  links = new Array();

		// init D3 force layout
		var force = d3.layout.force()
	    .nodes(nodes)
	    .links(links)
	    .size([width, height])
	    .linkDistance(100)
	    .charge(-500)
	    .on('tick', tick);

	initNodes(receives, sends);
	initLinks(receives, sends);
	restart();
}

function showRealTimeNodeDetail(d){
	//receive or send
	$("#nodedetail_model_body_table").empty();
	var valueArr = new Array();
	if (d.type === 'receive') {
		valueArr.push("<thead><th width='30%'>服务属性</th><th width='70%'>值</th></thead>");
		valueArr.push("<tr><td>服务名称</td><td>" + d.node.serviceName + "</td></tr>");
		valueArr.push("<tr><td>函数名</td><td>" + d.node.functionName + "</td></tr>");
		valueArr.push("<tr><td>版本</td><td>" + d.node.serviceVersion + "</td></tr>");
		valueArr.push("<tr><td>地址</td><td>" + ORIGINAL.intToString(d.node.selfIpAddress) + "</td></tr>");
		valueArr.push("<tr><td>端口</td><td>" + d.node.selfPort + "</td></tr>");
		valueArr.push("<tr><td>开始时间</td><td>" + new Date(d.node.startTime).format("yyyy-MM-dd HH:mm:ss.SSS") + "</td></tr>");
		valueArr.push("<tr><td>结束时间</td><td>" + new Date(d.node.endTime).format("yyyy-MM-dd HH:mm:ss.SSS") + "</td></tr>");
		valueArr.push("<tr><td>运行时间(ms)</td><td>" + (d.node.endTime - d.node.startTime) + "</td></tr>");
		if(d.node.extra){//show exception log
			valueArr.push("<tr><td>异常日志内容</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.logContent) + "</td></tr>");
			valueArr.push("<tr><td>异常键</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.logKey) + "</td></tr>");
			valueArr.push("<tr><td>异常类型</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.logType) + "</td></tr>");
			valueArr.push("<tr><td>异常</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.e) + "</td></tr>");
		}
	}else{
		valueArr.push("<thead><th width='30%'>服务属性</th><th width='70%'>值</th></thead>");
		valueArr.push("<tr><td>函数编号</td><td>" + d.node.funcId + "</td></tr>");
		valueArr.push("<tr><td>地址</td><td>" + ORIGINAL.intToString(d.node.selfIpAddress) + "</td></tr>");
		valueArr.push("<tr><td>端口</td><td>" + d.node.selfPort + "</td></tr>");
		valueArr.push("<tr><td>开始时间</td><td>" + new Date(d.node.startTime).format("yyyy-MM-dd HH:mm:ss.SSS") + "</td></tr>");
		valueArr.push("<tr><td>结束时间</td><td>" + new Date(d.node.endTime).format("yyyy-MM-dd HH:mm:ss.SSS") + "</td></tr>");
		valueArr.push("<tr><td>运行时间(ms)</td><td>" + (d.node.endTime - d.node.startTime) + "</td></tr>");
		if(d.node.extra){//show exception log
			valueArr.push("<tr><td>异常日志内容</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.logContent) + "</td></tr>");
			valueArr.push("<tr><td>异常键</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.logKey)+ "</td></tr>");
			valueArr.push("<tr><td>异常类型</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.logType) + "</td></tr>");
			valueArr.push("<tr><td>异常</td><td>" + convertUndefinedToEmptyString(d.node.extraLog.e) + "</td></tr>");
		}
	}
	
	$("#nodedetail_model_body_table").append(valueArr.join());
	
	$('#nodedetail_modal').modal({
	    backdrop:true,
	    keyboard:true,
	    show:true
	});	
}

function convertUndefinedToEmptyString(value){
	if(value == undefined){
		return "" ;
	}
	return value;
}

//绘制调用链实时图
draw.func.drawRealtimeCallChainTest = function(divid, receives, sends){
	var width = 840, height = 600, colors = d3.scale.category10();
	var svg = d3.select("#" + divid).append('svg').attr('width', width).attr('height', height);//初始化svg
	
	// set up initial nodes and links
	//  - nodes are known by 'id', not by index in array.
	//  - reflexive edges are indicated on the node (as a bold black circle).
	//  - links are always source < target; edge directions are set by 'left' and 'right'.
	// define arrow markers for graph links
	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'end-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 6)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M0,-5L10,0L0,5')
	    .attr('fill', '#000');

	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'start-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 4)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M10,-5L0,0L10,5')
	    .attr('fill', '#000');

	// line displayed when dragging new nodes
	var drag_line = svg.append('svg:path')
	  .attr('class', 'link dragline hidden')
	  .attr('d', 'M0,0L0,0');

	// handles to link and node element groups
	var path = svg.append('svg:g').selectAll('path'),
	    circle = svg.append('svg:g').selectAll('g');

	// mouse event vars
	var selected_node = null,
	    selected_link = null,
	    mousedown_link = null,
	    mousedown_node = null,
	    mouseup_node = null;

	function resetMouseVars() {
	  mousedown_node = null;
	  mouseup_node = null;
	  mousedown_link = null;
	}

	// update force layout (called automatically each iteration)
	function tick() {
	  // draw directed edges with proper padding from node centers
	  path.attr('d', function(d) {
	    var deltaX = d.target.x - d.source.x,
	        deltaY = d.target.y - d.source.y,
	        dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY),
	        normX = deltaX / dist,
	        normY = deltaY / dist,
	        sourcePadding = d.left ? 17 : 12,
	        targetPadding = d.right ? 17 : 12,
	        sourceX = d.source.x + (sourcePadding * normX),
	        sourceY = d.source.y + (sourcePadding * normY),
	        targetX = d.target.x - (targetPadding * normX),
	        targetY = d.target.y - (targetPadding * normY);
	    return 'M' + sourceX + ',' + sourceY + 'L' + targetX + ',' + targetY;
	  });

	  circle.attr('transform', function(d) {
	    return 'translate(' + d.x + ',' + d.y + ')';
	  });
	}

	// update graph (called when needed)
	function restart() {
	  // path (link) group
	  path = path.data(links);

	  // update existing links
	  path.classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });


	  // add new links
	  path.enter().append('svg:path')
	    .attr('class', 'link')
	    .classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });

	  // remove old links
	  //path.exit().remove();


	  // circle (node) group
	  // NB: the function arg is crucial here! nodes are known by id, not by index!
	  circle = circle.data(nodes, function(d) { return d.id; });

	  // update existing nodes (reflexive & selected visual states)
	  circle.selectAll('circle')
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); });

	  // add new nodes
	  var g = circle.enter().append('svg:g');

	  g.append('svg:circle')
	    .attr('class', 'node')
	    .attr('r', 12)
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); })
	    .style('stroke', function(d) { return d3.rgb(colors(d.id)).darker().toString(); })
	    .call(force.drag)
	    .on('mousedown', function(d){showRealTimeNodeDetail(d);});	    

	  // show node IDs
	  g.append('svg:text')
	      .attr('x', 0)
	      .attr('y', 4)
	      .attr('class', 'id')
	      .text(function(d) { return d.serviceid; });

	  // remove old nodes
	  circle.exit().remove();

	  // set the graph in motion
	  force.start();
	}

	function initNodes(receives, sends) {
		for(var i = 0; i < receives.length; i++){
			var node = new Object();
			node.id = i;
			node.type = "receive";
			node.node = receives[i].node;
			if(i === 0){
				node.serviceid = "root";
			}else{
				node.serviceid = "receive:" + i;
			}
			node.x = width*(i + 1)/(receives.length + 1);
			node.y = height/i ;
			nodes.push(node);
		}
		
		for(var j = 0; j < sends.length; j++){
			var node = new Object();
			node.id = j + receives.length;
			node.type = "send";
			node.node = sends[j].node;
			node.serviceid = "send:" + i;
			nodes.push(node);
		}
	}
	
	function initParentNodeArr(serviceIdArr, width, height) {
	 	var nodeArr = new Array();
	 	for(var i = 0; i < serviceIdArr.length; i++) {
	 		var node = new Object();
	 		node.id = i;
	 		node.eid = serviceIdArr[i].eid;
	 		node.serviceId = serviceIdArr[i].servicename + " " + serviceIdArr[i].functionname;
	 		node.x = width*(i+1)/(serviceIdArr.length+1);
	 		node.y = height/4;
	 		nodeArr.push(node);
	 	}
	 	return nodeArr;
	}

	function initLinks(receives, sends) {
		for ( var i = 0; i < receives.length; i++) {
			var receive = receives[i];
			var receiveNode = receive.node;
			for ( var j = 0; (receive.intChildren !== undefined) &&  (j < receive.intChildren.length); j++) {//receiveNode has intChildren should draw link
				var link = new Object();
				link.source = nodes[i];
				link.target = nodes[receives.length + receive.intChildren[j]];
				link.left = false;
				link.right = true;
				links.push(link);
			}
		}
		
		for ( var i = 0; i < sends.length; i++) {
			var send = sends[i];
			var sendNode = send.node;
			if(isInReceiveIndex(i, receives)){
				var link = new Object();
				link.source = nodes[receives.length + i];
				link.target = nodes[send.intChildIndex];
				link.left = false;
				link.right = true;
				links.push(link);
			}
		}
	}
	
	//send not in receive indexs
	function isInReceiveIndex(index, receives){
		for ( var i = 0; i < receives.length; i++) {
			var receive = receives[i];
			var receiveNode = receive.node;
			for ( var j = 0; (receive.intChildren !== undefined) && (j < receive.intChildren.length); j++) {
				if (index == receive.intChildren[j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	var nodes = new Array(),
	  lastNodeId = 0,
	  links = new Array();

		// init D3 force layout
		var force = d3.layout.force()
	    .nodes(nodes)
	    .links(links)
	    .size([width, height])
	    .linkDistance(100)
	    .charge(-500)
	    .on('tick', tick);

	initNodes(receives, sends);
	initLinks(receives, sends);
	restart();
}

//traversal nodes, return a new array contains tree deep and tree node id infos
function traversalNodes(receives, sends){
	var deep = 0;
	var deepArray = [];
	var deepObject = new Object();
	
	printReceNode(receives, sends, 0);
	
/*		for ( var i = 0; i < receives.length; i++) {
		var receive = receives[i];
		var receiveNode = receive.node;
		
		if (receive.intChildren !== undefined) {
			var childArray = receive.intChildren;
			
			
		}
		
		
	}*/
}

function linkReceives(receives, sends, rootindex){
	var receive = receives[rootindex];
	var receiveNode = receive.node;
	//links
	console.log("receive:" + receiveNode.index);
	for ( var i = 0; (receive.intChildren !== undefined) && (i < receive.intChildren.length); i++) {
		console.log("linkReceives for: " + i);
		linkSends(receives, sends, i);
	}
}

function linkSends(receives, sends, rootindex){
	var send = sends[rootindex];
	var sendNode = send.node;
	//links
	console.log("send:" + sendNode.index);
	linkReceives(receives, sends, rootindex);
}

//draw time statist chart, using jquery chart
draw.func.drawTimeStatistChart = function(divid, maxData, minData, avgData, xData){
	var chart = new Highcharts.Chart({
		chart : {
			renderTo : divid,
			type : 'line',
			marginRight : 130,
			marginBottom : 25
		},
		title : {
			text : 'API调用统计图',
			x : -20
		//center
		},
		xAxis : {
			categories : xData
		},
		yAxis : {
			title : {
				text : ''
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : -10,
			y : 100,
			borderWidth : 0
		},
		series : [ {
			name : 'max',
			data : maxData
		}, {
			name : 'min',
			data : minData
		}, {
			name : 'avg',
			data : avgData
		} ]
	});
	return chart;
}

//draw statist line chart
draw.func.drawStatistLineChart = function(divid, xData, seriesData, threshold){
	var chart = new Highcharts.Chart({
		chart : {
			renderTo : divid,
			type : 'line',
			marginRight : 130,
			marginBottom : 25
		},
		title : {
			text : 'API调用统计图',
			x : -20
		//center
		},
		xAxis : {
			categories : xData
		},
		yAxis : {
			title : {
				text : ''
			},
			plotLines : [{
				value : threshold,
				color : 'red',
				dashStyle : 'shortdash',
				width : 2,
				label : {
					text : '阈值'
				}
			}]
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : -10,
			y : 100,
			borderWidth : 0
		},
		series : seriesData
	});
	return chart;
}



