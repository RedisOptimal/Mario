<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Ella: HBase Monitor</title>

<link href="../static/cached_report.css" media="screen" rel="stylesheet"
	type="text/css">
<!-- 1. Add these JavaScript inclusions in the head of your 
page -->
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/highcharts.js"></script>

<!-- 1a) Optional: add a theme file -->
<!--
             <script type="text/javascript" 
src="../js/themes/gray.js"></script>
         -->

<!-- 1b) Optional: the exporting module -->
<script type="text/javascript" src="../js/modules/exporting.js"></script>


<!-- 2. Add the JavaScript to initialize the chart on document 
ready -->
<script type="text/javascript">

$(function(){
	$(".mod-header.radius").click(function(){
		var showDivId = $(this).attr("data");
		if($("#"+showDivId).css("display") === 'none'){
			$("#"+showDivId).css("display", "block");
		}else{
			$("#"+showDivId).css("display", "none");
		}
	});
});

             function createRWLoad(div_id, data){
                 var chart = new Highcharts.Chart({
                     chart: {
                         renderTo: div_id,
                         defaultSeriesType: 'area'
                     },
                     title: {
                         text: 'R/W requests last 1h'
                     },
                     subtitle: {
                         text: ''
                     },
                     xAxis: {
                         title: {
                             text: 'last 1 hour'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value; // clean, unformatted number for year
                             }
                         }
                     },
                     yAxis: {
                         title: {
                             text: 'r/w requests'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value / 1000 +'k';
                             }
                         }
                     },
                     tooltip: {
                         formatter: function() {
                             return  'Processed <b>' + Highcharts.numberFormat(this.y, 0) + '</b>' + this.series.name + 'requests'+
                                 '<br/> in '+ this.x + "'";
                         }
                     },
                     plotOptions: {
                         area: {
                             pointStart: 0,
                             marker: {
                                 enabled: false,
                                 symbol: 'circle',
                                 radius: 1,
                                 states: {
                                     hover: {
                                         enabled: true
                                     }
                                 }
                             }
                         }
                     },
                     series: data
                 });
             }

             function createStoreCFCountLoad(div_id, data){
                 var chart = new Highcharts.Chart({
                     chart: {
                         renderTo: div_id,
                         defaultSeriesType: 'line'
                     },
                     title: {
                         text: 'Stores Flush/Compaction last 1h'
                     },
                     subtitle: {
                         text: ''
                     },
                     xAxis: {
                         title: {
                             text: 'last 1 hour'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value; // clean, unformatted number for year
                             }
                         }
                     },
                     yAxis: {
                         title: {
                             text: 'Flush/Compaction count'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value;
                             }
                         }
                     },
                     tooltip: {
                         formatter: function() {
                             return  'Processed <b>' + Highcharts.numberFormat(this.y, 0) + '</b>' + this.series.name + 'requests'+
                                 '<br/> in '+ this.x + "'";
                         }
                     },
                     plotOptions: {
                         area: {
                             pointStart: 0,
                             marker: {
                                 enabled: false,
                                 symbol: 'circle',
                                 radius: 1,
                                 states: {
                                     hover: {
                                         enabled: true
                                     }
                                 }
                             }
                         }
                     },
                     series: data
                 });
             }

             function createStoreCFSizeLoad(div_id, data){
                 var chart = new Highcharts.Chart({
                     chart: {
                         renderTo: div_id,
                         defaultSeriesType: 'line'
                     },
                     title: {
                         text: 'Stores Flush/Compaction last 1h'
                     },
                     subtitle: {
                         text: ''
                     },
                     xAxis: {
                         title: {
                             text: 'last 1 hour'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value; // clean, unformatted number for year
                             }
                         }
                     },
                     yAxis: {
                         title: {
                             text: 'Flush/Compaction Size (MB)'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value;
                             }
                         }
                     },
                     tooltip: {
                         formatter: function() {
                             return  this.series.name + ' <b>' + Highcharts.numberFormat(this.y, 0) + '</b> MB store files' + '<br/> in '+ this.x + "'";
                         }
                     },
                     plotOptions: {
                         area: {
                             pointStart: 0,
                             marker: {
                                 enabled: false,
                                 symbol: 'circle',
                                 radius: 1,
                                 states: {
                                     hover: {
                                         enabled: true
                                     }
                                 }
                             }
                         }
                     },
                     series: data
                 });
             }

             function createFsWriteCountLoad(div_id, data){
                 var chart = new Highcharts.Chart({
                     chart: {
                         renderTo: div_id,
                         defaultSeriesType: 'line'
                     },
                     title: {
                         text: 'HFile/HLog Write Count last 1h'
                     },
                     subtitle: {
                         text: ''
                     },
                     xAxis: {
                         title: {
                             text: 'last 1 hour'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value; // clean, unformatted number for year
                             }
                         }
                     },
                     yAxis: {
                         title: {
                             text: 'HFile/HLog write count'
                         },
                         labels: {
                             formatter: function() {
                                 return this.value;
                             }
                         }
                     },
                     tooltip: {
                         formatter: function() {
                             return  'Processed <b>' + Highcharts.numberFormat(this.y, 0) + '</b> requests for '+ this.series.name + '<br/> in '+ this.x + "'";
                         }
                     },
                     plotOptions: {
                         area: {
                             pointStart: 0,
                             marker: {
                                 enabled: false,
                                 symbol: 'circle',
                                 radius: 1,
                                 states: {
                                     hover: {
                                         enabled: true
                                     }
                                 }
                             }
                         }
                     },
                     series: data
                 });
             }

             function createFsWriteSizeLoad(div_id, data){
                 var chart = new Highcharts.Chart({
                     chart: {
                         renderTo: div_id,
                         zoomType: 'x',
                         spacingRight: 20
                     },
                     title: {
                         text: 'HFile/HLog Write Size last 1h'
                     },
                     subtitle: {
                         text: ''
                     },
                     xAxis: {
                         type: 'datetime',
                         maxZoom: 1, // fourteen days
                         title: {
                             text: null
                         }
                     },
                     yAxis: {
                         title: {
                             text: 'Write Size(MB)'
                         },
                         min: 0.6,
                         startOnTick: false,
                         showFirstLabel: false
                     },
                     tooltip: {
                         shared: true
                     },
                     legend: {
                         enabled: false
                     },
                     plotOptions: {
                         area: {
                             fillColor: {
                                 linearGradient: [0, 0, 0, 300],
                                 stops: [
                                     [0, Highcharts.getOptions().colors[0]],
                                     [1, 'rgba(2,0,0,0)']
                                 ]
                             },
                             lineWidth: 1,
                             marker: {
                                 enabled: false,
                                 states: {
                                     hover: {
                                         enabled: true,
                                         radius: 5
                                     }
                                 }
                             },
                             shadow: false,
                             states: {
                                 hover: {
                                     lineWidth: 1
                                 }
                             }
                         }
                     },
                     series: [{
                         type: 'area',
                         name: 'Write Size',
                         pointInterval: 1,
                         pointStart: 0,
                         data: data
                         }]
                 });
             }

             $(document).ready(function() {

                 $.ajax({
                     type: "GET",
                     dataType: "json",
  url:"../rswrload.do?regionserver_host=10.4.19.70",
                     success: function(msg){
                         createRWLoad('container_kv_rw', msg.series);
                     },
                     error: function(XMLHttpRequest, textStatus, 
errorThrown) {
                         //alert(XMLHttpRequest.status);
                         //alert(XMLHttpRequest.readyState);
                         //console.log(textStatus);
                     }
                 });

                 $.ajax({
                     type: "GET",
                     dataType: "json",
  url:"../rsstorefcload.do?regionserver_host=10.4.19.70&type=count",
                     success: function(msg){
  createStoreCFCountLoad('container_store_cf_count', msg.series);
                     },
                     error: function(XMLHttpRequest, textStatus, 
errorThrown) {
                         /* alert(XMLHttpRequest.status);
                         alert(XMLHttpRequest.readyState);
                         console.log(textStatus); */
                     }
                 });

                 $.ajax({
                     type: "GET",
                     dataType: "json",
  url:"../rsstorefcload.do?regionserver_host=10.4.19.70&type=size",
                     success: function(msg){
  createStoreCFSizeLoad('container_store_cf_size', msg.series);
                     },
                     error: function(XMLHttpRequest, textStatus, 
errorThrown) {
                         /* alert(XMLHttpRequest.status);
                         alert(XMLHttpRequest.readyState);
                         console.log(textStatus); */
                     }
                 });

                 $.ajax({
                     type: "GET",
                     dataType: "json",
  url:"../rsfswriteload.do?regionserver_host=10.4.19.70&type=count",
                     success: function(msg){
  createFsWriteCountLoad('container_store_fs_write_count', msg.series);
                     },
                     error: function(XMLHttpRequest, textStatus, 
errorThrown) {
                         /* alert(XMLHttpRequest.status);
                         alert(XMLHttpRequest.readyState);
                         console.log(textStatus); */
                     }
                 });

                 $.ajax({
                     type: "GET",
                     dataType: "json",
  url:"../rsfswriteload.do?regionserver_host=10.4.19.70&type=size",
                     success: function(msg){
  createFsWriteSizeLoad('container_store_fs_write_size', msg.series);
                     },
                     error: function(XMLHttpRequest, textStatus, 
errorThrown) {
                         /* alert(XMLHttpRequest.status);
                         alert(XMLHttpRequest.readyState);
                         console.log(textStatus); */
                     }
                 });

             });

         </script>

</head>
<body>
	<div class="hd">
		<div class="userHeader clearfix">
			<span>Ella: A Watchdog on HBase</span>
			<div class="logo">
				<a id="logo" href="http://wiki.apache.org/lucene-hadoop/Hbase"><img
					src="../static/hbase_logo.png" alt="HBase Logo" title="HBase Logo" />
				</a>
			</div>
		</div>
	</div>

	<br />
	<br />
	<!-- BODY LEFT -->
	<div class="bd clearfix">
		<div id="leftColContainer">
			<div class="leftCol">
				<div id="siderNav">
					<ul class="nav-items">


						<li class="nav-item item-top current-item on"><span> <a
								href="">RegionServer ∏∫‘ÿ</a> </span>
						</li>

						<li class="nav-item "><span> <a href="i">RegionServer
									–‘ƒ‹</a> </span>
						</li>

						<li class="nav-item "><span> <a href="">RegionServer
									RPC</a> </span></li>

						<li class="nav-item "><span> <a href="#">JVM
									Heap/Gc</a> </span>
						</li>

					</ul>
				</div>
			</div>
		</div>

		<div id="mainContainer3">
			<div class="contentCol">

				<div class="mod mod1" id="today_table">
					<div class="mod-header radius" data="container_kv_rw">
						<h2>KV read/write</h2>
					</div>

					<div id="container_kv_rw"
						style="width: 500px; height: 400px; margin: left"></div>
				</div>
			</div>
		</div>

		<div id="mainContainer3">
			<div class="contentCol">

				<div class="mod mod1" id="today_table">
					<div class="mod-header radius">
						<h2>Store flush/compaction</h2>
					</div>

					<table>
						<tr>
							<td><div id="container_store_cf_count"
									style="width: 500px; height: 400px; margin: left"></div>
							</td>
							<td><div id="container_store_cf_size"
									style="width: 500px; height: 400px; margin: left"></div>
							</td>
						</tr>
					</table>


				</div>
			</div>
		</div>

		<div id="mainContainer3">
			<div class="contentCol">

				<div class="mod mod1" id="today_table">
					<div class="mod-header radius">
						<h2>HFile/HLog write</h2>
					</div>

					<table>
						<tr>
							<td><div id="container_store_fs_write_count"
									style="width: 500px; height: 400px; margin: left"></div>
							</td>
							<td><div id="container_store_fs_write_size"
									style="width: 500px; height: 400px; margin: left"></div></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
</body>
</html>