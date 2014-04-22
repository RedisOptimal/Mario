<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>集群状态统计图</title>
    <link href="${ctx}/static/jquery-ui/jquery-ui-1.7.custom.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctx}/static/jquery/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-highcharts/modules/exporting.js"></script>
    <script>

        var sendObj = ""

        //Node Count
        function Chart(id, title, field){
            var chart;
                $(document).ready(function() {
                    //定义一个HighCharts
                    chart = new Highcharts.Chart({
                        //配置chart选项
                        chart: {
                            renderTo: id,  //容器名，和body部分的div id要一致
                            type: 'spline'            //图表类型，这里选择折线图
                        },
                        //配置链接及名称选项
                        credits: {
                            enabled : true,
                            href : "",
                            text : ""
                        },
                        //配置标题
                        title: {
                            text: title + "统计图",
                            y:10  //默认对齐是顶部，所以这里代表距离顶部10px
                        },
                        //配置副标题
                        subtitle: {
                            text: new Date().toString(),
                            y:30
                        },
                        //配置x轴
                        xAxis: {
                            type: 'datetime',
                            gridLineWidth: 1,
                            labels: {
                                step:36
                            }, 
                            categories: sendObj["timeStamp"]
                        },
                        // 配置y轴
                        yAxis: {
                            title: {
                                text: title
                            },
                            labels: {  
                                formatter: function() {
                                    return this.value
                                }
                            }
                        },
                        //图例 
                        legend: {  
                            layout: 'horizontal',  //图例显示的样式：水平（horizontal）/垂直（vertical） 
                            backgroundColor: '#ffc', //图例背景色 
                            align: 'right',  //图例水平对齐方式 
                            verticalAlign: 'top',  //图例垂直对齐方式 
                            x: -10,  //相对X位移 
                            y: 60,   //相对Y位移 
                            floating: true, //设置可浮动 
                            shadow: true  //设置阴影 
                        }, 
                        //配置数据点提示框
                        tooltip: {
                            crosshairs: true,
                        },
                        //配置数据列
                        series: [{
                            name: title,
                            marker: {
                                symbol: 'diamond'
                            },
                            data: sendObj[field]
                        }]
                        });
                    });
        }

        function getDate() {
            var date = new Date();

            var year = date.getFullYear();

            var month = date.getMonth()+1;//获取当前月份的日期
            if(month < 10) month = "0" + month;

            var day = date.getDate();
            if(day < 10) day = "0" + day;

            return year+"-"+month+"-"+day;
        }

        $(function() {

            //设置默认日期
            $("#date").attr("value",getDate());

            search();
        });

        //通过ajax获取数据
        function getDataByAjax(){
            var args = getArgs();
            //获取日期
            var date = $("#date").attr("value");

            var url = "${ctx}/graph/zk_state_data";

            $.ajax({
                type:"get",
                dataType:"json",
                url:url,
                data:{
                    zk_id:args.zk_id,
                    date:date
                },
                success:function(data){
                    sendObj = $.parseJSON(data.send);
                    Chart('node_count_chart', '节点数', 'nodeCount');
                    Chart('total_watches_chart', 'Watcher数', 'totalWatches');
                    Chart('client_number_chart', '客户端数', 'clientNumber');
                },
            });
        }

        function search(){
            getDataByAjax();
        }

        function getArgs(){
            var args = {};
            var match = null;
            var search = decodeURIComponent(location.search.substring(1));
            var reg = /(?:([^&]+)=([^&]+))/g;
            while((match = reg.exec(search))!==null){
                args[match[1]] = match[2];
            }
            return args;
        }

    </script>
</head>

<body>
    <div>
        <form class="form-inline form-horizontal" action="" id="form">
            <label for="date">选择时间：&nbsp;</label>
            <input id="date" name="search_startTime" 
                    type="text" readonly="readonly" maxlength="20" 
                    class="form-control input Wdate" 
                    style="width: 160px; cursor: auto;" 
                    value="${param.search_startTime}" 
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" 
                    placeholder="开始时间"/>
            <button type="button"  onclick="search()" class="btn btn-default pull-right">查询</button>
        </form>
    </div>
    <div id="chart-1" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
        <div id="node_count_chart"></div>
    </div>
    <div id="chart-2" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
        <div id="total_watches_chart"></div>
    </div>
    <div id="chart-3" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
        <div id="client_number_chart"></div>
    </div>
</body>

</html>
