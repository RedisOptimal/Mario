<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title></title>
    <link href="${ctx}/static/jquery-ui/jquery-ui-1.7.custom.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctx}/static/jquery/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery-highcharts/modules/exporting.js"></script>
    <script>

        var sendObj = ""

        //发送量曲线图
        function nodeCountChart(){
            var chart;
                $(document).ready(function() {
                    //定义一个HighCharts
                    chart = new Highcharts.Chart({
                        //配置chart选项
                        chart: {
                            renderTo: 'node_count_chart',  //容器名，和body部分的div id要一致
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
                            text: '',
                            y:10  //默认对齐是顶部，所以这里代表距离顶部10px
                        },
                        //配置副标题
                        subtitle: {
                            text: '',
                            y:30
                        },
                        //配置x轴
                        xAxis: {
                            title: {
                                text: 'Time Stamp'
                            },
                            categories: sendObj["timeStamp"]
                        },
                        // 配置y轴
                        yAxis: {
                            title: {
                                text: 'Node Count'
                            },
                            labels: {  
                                formatter: function() {
                                    return this.value
                                }
                            }
                        },
                        //配置数据点提示框
                        tooltip: {
                            crosshairs: true,
                        },
                        //配置数据列
                        series: [{
                            name: 'Node Count',
                            marker: {
                                symbol: 'diamond'
                            },
                            data: sendObj["nodeCount"]
                        },{
                            name: 'Total Watches',
                            marker: {
                                symbol: 'diamond'
                            },
                            data: sendObj["totalWatches"]
                        },{
                            name: 'Client Number',
                            marker: {
                                symbol: 'diamond'
                            },
                            data: sendObj["clientNumber"]
                        }]
                        });
                    });
        }
        //获取昨天的日期
        function getYestorday() {
            var date = new Date();
            date.setDate(date.getDate()- 1);//获取前天天的日期

            var year = date.getFullYear();

            var month = date.getMonth()+1;//获取当前月份的日期
            if(month < 10) month = "0" + month;

            var day = date.getDate();
            if(day < 10) day = "0" + day;

            return year+"-"+month+"-"+day;
        }

        $(function() {

            //设置默认日期
            $("#date").attr("value",getYestorday());

            search();
        });

        //通过ajax获取数据
        function getDataByAjax(){
            var args = getArgs();
            //获取日期
            var date = $("#date").attr("value");

            var url = "${ctx}/graph/server_state_data";

            $.ajax({
                type:"get",
                dataType:"json",
                url:url,
                data:{
                    server_id:args.server_id,
                    date:date
                },
                success:function(data){
                    sendObj = $.parseJSON(data.send);
                    nodeCountChart();
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
    <div id="base-1" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
        <div id="node_count_chart"></div>
    </div>
</body>

</html>