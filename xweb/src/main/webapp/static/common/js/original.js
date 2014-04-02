/**
 * original通用javascript
 */
(function ($){
    //全局系统对象
    window['ORIGINAL'] = {};

    //convert ip int to ip string, for call chain ip address show 
    ORIGINAL.intToString = function(ip){
    	var ipAdd = (ip & 0xFF000000) >>> 24;
    	ipAdd += ".";
    	ipAdd += (ip & 0x00FF0000) >>> 16;
    	ipAdd += ".";
    	ipAdd += (ip & 0x0000FF00) >>> 8;
    	ipAdd += ".";
    	ipAdd += ip & 0x000000FF;
    	return ipAdd;
    }
    
    //time start plug-in
    ORIGINAL.startTime = function(){
    	WdatePicker({errDealMode:1, isShowClear:false,maxDate:'#F{$dp.$D(\'endtime\')||\'%y-%M-{%d}\'}', dateFmt:'yyyy-MM-dd HH'});
    }
    
    //time end plug=in
    ORIGINAL.endTime = function(){
    	WdatePicker({errDealMode:1, isShowClear:false, minDate:'#F{$dp.$D(\'starttime\')}', maxDate:'%y-%M-{%d} {%H+1}', dateFmt:'yyyy-MM-dd HH'});
    }
    
    ORIGINAL.showHourTime = function(){
    	var be = new Date(new Date().getTime()-(3600*1000)).format("yyyy-MM-dd HH");
    	var en = (new Date()).format("yyyy-MM-dd HH");
    	
    	$("#starttime").val(be);
    	$("#endtime").val(en);
    }
    
    ORIGINAL.showDayTime = function(){
    	var be = new Date(new Date().getTime()-(24*3600*1000)).format("yyyy-MM-dd HH");
    	var en = (new Date()).format("yyyy-MM-dd HH");
    	
    	$("#starttime").val(be);
    	$("#endtime").val(en);
    }

    ORIGINAL.showWeekTime = function(){
    	var be = new Date(new Date().getTime()-(7*24*3600*1000)).format("yyyy-MM-dd HH");
    	var en = (new Date()).format("yyyy-MM-dd HH");
    	
    	$("#starttime").val(be);
    	$("#endtime").val(en);
    }

    ORIGINAL.showMonthTime = function(){
    	var be = new Date(new Date().getTime()-(30*24*3600*1000)).format("yyyy-MM-dd HH");
    	var en = (new Date()).format("yyyy-MM-dd HH");
    	
    	$("#starttime").val(be);
    	$("#endtime").val(en);
    }
    
    
})(jQuery);