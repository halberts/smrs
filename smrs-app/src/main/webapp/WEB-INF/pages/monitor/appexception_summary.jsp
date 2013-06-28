<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>全业务异常汇总</title>
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasAxisTickRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasTextRenderer.min.js"></script>
<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
<style>
/* 设置jqgrid表格字体 */
.ui-jqgrid {
    font-size: 12px;
    position: relative;
}
.jqplot-axis {
	font-size: 1em;
}

table.jqplot-table-legend,table.jqplot-cursor-legend {
	background-color: rgba(255, 255, 255, 0.6);
	border: 1px solid #CCCCCC;
	font-size: 1em;
	position: absolute;
}


table.jqplot-table-legend td {
	padding: 2px;
	white-space: nowrap;
}

.jqplot-point-label {
	font-size: 1em;
	z-index: 2;
}	

.jqplot-highlighter-tooltip {
	font-size: 1.5em;
	z-index: 2;
}

table.jqplot-table-legend {
  width: auto !important; 
}
</style>

<script>

var appIds;	//全局变量，为click使用。
var barTicks;
var appName;


/**
 * 生成直线图
 */
function plotLineChart(data, title) {
	$("#linechart").empty();
	var linedata = eval(data.linedata);
	var labels = data.labels;
	var ticks = data.ticks;
	
	$.jqplot('linechart', linedata, {
		animate: true,
	      title: title,
	      highlighter: {	//鼠标经过高亮显示每个点
	        show: true,
			tooltipAxes: 'y',
	        sizeAdjust: 7.5
	      },
	      seriesDefaults: {
	          pointLabels: { 
	          	show: true
	          }
	      },
		  axes: {
			xaxis: { 
				renderer: $.jqplot.CategoryAxisRenderer,
				ticks: ticks
			},
			yaxis: {
				min: 0,
				tickOptions: { formatString: '%d'}
			}
		  },
		  
		  legend: {
			show: true,
			placement: 'outsideGrid',
			labels: labels
		  }
	  });
	
	$("#linechart").show();
}



/**
 * 生成柱状图
 */
function plotChart(data) { 
	var labelsContent = data.barLabels; 
	appIds = new Array();
	barTicks = new Array();
	var ytick = new Array();
	for(i=-1;i<=data.appLists.length;i++){
		var applist = new Array();
		if(i==-1){
			applist[0] = i;
			applist[1] = "";
		}else if(i==data.appLists.length){
			applist[0] = data.appLists.length;
			applist[1] = "";
		}else{
			applist[0] = i;
			applist[1] = data.appLists[i].appName;
			appIds[i] = data.appLists[i].id;
			barTicks[i] = data.appLists[i].appName;
		}
		ytick[i+1] = applist;	
	}
	var plot2 = $.jqplot('barchart', data.exAppLists, {        
		
		stackSeries: true,            
		captureRightClick: true,            
		seriesDefaults:{               
			renderer:$.jqplot.BarRenderer,               
			shadowAngle: 135,               
			rendererOptions: {                   
				barDirection: 'horizontal',                   
				highlightMouseDown: true                  
				},               
				pointLabels: {show: true, 
					formatString: '%d'}           
				},
				legend: {               
					show: true,               
					location: 'e',               
					//placement: 'outside',
					yoffset:12,
					labels: labelsContent
					},  
				axes: {               
					yaxis: {                   
						//renderer: $.jqplot.CategoryAxisRenderer,
						  ticks:ytick
					}
					}
			});   
	$(".jqplot-table-legend").css("top",20);	
}


$(document).ready(function(){
	/**
	 * 搜索按钮 
	 */
	$("#searchBtn").click(function(){
		$(".message.red").hide();
		$("#barchart").empty();
		//$("#detailGrid").hide();
		//$("#line").hide();
		var time = $("#time").val();
		//$("#details").hide();
		//$("#listDetails").hide();
		//$("#details").hide();
    	$("#charArea").show();
    	var url = "allAppEx.action?time=" + time;
    	
    	$.ajax({
    		url: url,
    		type: 'GET',
    		success: function(data){
    			plotChart(data);
    		},
    		error: function(){
    			addMessageInfo("red","ajax获取图表数据出错。");
    			return ;
    		}
    	});
    	
		
    }); 
	
	
	// 绑定bar的点击事件 
    $("#barchart").bind('jqplotDataClick',
    		function(ev, seriesIndex, pointIndex, data){
    			$(".message.red").hide();
				var time = $("#time").val();
		    	var timeText = $("#time").text();
    			var appId = appIds[pointIndex];	// 获取点击的系统appid
    			appName = barTicks[pointIndex];
    			var url = 'singleAppRecentEx.action?appId=' + appId + '&time=' + time;
    			
    			var title;
    			if(time == 'week') {
    				title = appName + ' 近三周异常情况';
    			} else {
    				title = appName + ' 近三月异常情况';
    			}

    			//$("#details div.box div.title").html(title+"<span class='hide'></span>");
    			//$("#listDetails div.box div.title").html(title+"排名前10"+"<span class='hide'></span>");
    			$("#list1").jqGrid("setGridParam", {url: url}).trigger("reloadGrid").jqGrid(/* 'setCaption', appName + ' ' + timeText + ' 异常前10 的service' */);
    			var grid = $("#list1");
    			var w = $(".section").width();
    			grid.setGridWidth(w * 0.92);
    			$("#details").show();
    			$("#listDetails").show();
    			/* $.ajax({
    				url: url,
    				type: 'GET',
    				cache: false,
    				success: function(data) {
    					$("#line").show();
    					
    					plotLineChart(data, title);
    					
    	    			$("#list1").jqGrid("setGridParam", {url: url}).trigger("reloadGrid").jqGrid('setCaption', appName + ' ' + timeText + ' 异常前10 的service');
    					
    	    			$("#detailGrid").show();
    	    			
    	    			$("#rightContent").animate({
    	                	scrollTop: $("#line").offset().top-80
    	                }, 1000);
    				},
    				
    				error: function() {
    					$("#info").text('ajax获取数据出错。');
    					return;
    				}
    			}); */
    			
			}
    ); 
	
	

});
</script>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">监控中心</a></li>
		<li><a href="#">综合监控报告</a></li>
		<li class="last"><a href="#">所有系统异常汇总</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="message blue">
		<span><b>说明：</b>本页面查看全业务异常汇总情况。（点击柱子查看具体系统近三周/三月 异常情况，以及异常前10）</span>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			选择查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<select id="time">
				<option value="week">本周</option>
				<option value="month">本月</option>
			</select>
			<button  id="searchBtn" class="orange">
				<span>查看</span>
			</button>
		</div>
	</div>
</div>
<div class="section" id="charArea">
	<div class="box">
		<div class="title" id='bartitle'>
			查询结果（请点击柱子查看明细）
			<span class="hide"></span>
		</div>
		<div class="content" id='barcontent'>
			<div id="barchart" class="flot-graph" style="width: 100%;height:600px;padding: 0px; position: relative;">
			</div>
		</div>
	</div>
</div>
<div class="section" id='details'>
	<div class="box">
		<div class="title">
			近3月/周异常报表
			<span class="hide"></span>
		</div>
		<div class="content">
			<div id="linechart" class="flot-graph" style="width: 100%; padding: 0px; position: relative;">
			</div>
		</div>
	</div>
</div>
<div class="section" id="listDetails">
	<div class="box">
		<div class="title">
			近3月/周异常情况排名前10
			<span class="hide"></span>
		</div>
		<div class="content">
			<table id='list1'></table>
		</div>
	</div>
</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
 	var grid = $("#list1");
 	grid.jqGrid({
		colNames: ['Service名称', '发生异常次数'],
		colModel: [
		           {name: 'serviceName', index: 'serviceName'},
		           {name: 'exceptionNum', index: 'exceptionNum', align:'center', width: '30'}
		           ],
		rownumbers: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false
		},
		loadComplete: function(data) {
			//$("#line").show();
			if(data.time == 'week') {
				title = appName + ' 近三周异常情况';
			} else {
				title = appName + ' 近三月异常情况';
			}
			
			plotLineChart(data, title);
			
			$("#detailGrid").show();
			
			$("#rightContent").animate({
            	//scrollTop: $("#line").offset().top-80
            }, 1000);
		},
		
		height: '230'
		
	}); 
	//$("#line").hide();
	//$("#detailGrid").hide();
	
	
	/* var w = $(".section").width();
	grid.setGridWidth(w * 0.93); */
	
</script>

</body>
</html>
