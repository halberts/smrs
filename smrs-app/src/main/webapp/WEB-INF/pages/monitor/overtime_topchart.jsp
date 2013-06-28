<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>全业务超时比较</title>
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasAxisTickRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasTextRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>

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

</style>

<script>

var appIds;	// 全局变量，为click事件使用。
var ticks; // 全局变量，为click事件使用。
function plotChart(data) {
	appIds = data.appIds;
	ticks = data.ticks;
	
	if(ticks.length == 0) {
		$("#info").text('未查到相关数据。');
		return ;
	}
	
	var bardatas = eval(data.bardatas); 
	//alert(bardatas);
	var chartData = new Array();
	for(i=0;i<data.ticks.length;i++){
		var yData = data.ticks[i];
		var xData = bardatas[i];
		chartData[i] = [xData,yData]; 
	}
	// 先清空图表
	$("#barChart").empty();
	
	$.jqplot("barChart", [chartData], {
		/* title: '所有业务系统超时数量比较', */
    	animate: true,
        seriesDefaults: {
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {
	        	barWidth: 23,
	        	barDirection: 'horizontal'
	        },
            pointLabels: { 
            	show: true
            }
        },
        axes: {
            yaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                //ticks: ticks,
                //tickRenderer: $.jqplot.CanvasAxisTickRenderer,
               /*  tickOptions: {
            	  angle: -30
                } */
            }
        }
        
     /*    highlighter: {
            show: true, 
            showMarker: true,
            sizeAdjust: 7.5 , 
            tooltipAxes: 'y',
            tooltipLocation : 'ne'
        } */
		
	});
	
}

$(document).ready(function(){
    $('#barChart').bind('jqplotDataClick',
            function (ev, seriesIndex, pointIndex, data) {
    			var appId = appIds[pointIndex];	// 获取点击柱子对应appid
    			var appName = ticks[pointIndex]; // 获取点击柱子对应appname
    			var sp = $("#timeDiv ul.inline-ul li").find('span.current');	// 获取选中的span.
    			var time = sp.attr('id');
    			var timeText = sp.text();
    			
    			var url = "getAppOvertimeTopTen.action?time=" + time + "&appId=" + appId;
    			var title = appName + ' ' + timeText + ' 超时前10详细信息';
    			$("#details div.box div.title").html(title+"<span class='hide'></span>");
    			$("#list1").jqGrid("setGridParam", {url: url}).trigger("reloadGrid");/*.jqGrid('setCaption' , title );*/
    			
                $('#detail').show();
                
                $("#rightContent").animate({
                	scrollTop: $("#list1").offset().top
                }, 1000);
                
            }
        );
    
    
    /**
     * 查询按钮
     */
    $("#searchBtn").click(function(){
    	$("#info").text('');
    	
    	$('#detail').hide();
   		var sp = $("#timeDiv ul.inline-ul li").find('span.current');	// 获取选中的span.
		var time = $("#time").val();
   	 
    	var url = "getAllOvertimeTopTen.action?time=" + time;
    	$.ajax({
    		url: url,
    		type: "GET",
    		success: function(data){
    			//$("#details").show();
    			plotChart(data);	
    		
    		},
    		error: function(){
    			$("#info").text("ajax获取图表数据出错。");
    			return ;
    		}
    		
    	});
    	
    });
    
    /**
	 * 最大化时重新设定表格宽度
	 */
 	$("#clickDiv").click(function(){
 		var w = $("#details").width();
 		grid.setGridWidth(w * 0.95);
	}); 
    
});
</script>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">监控中心</a></li>
		<li><a href="#">综合监控报告</a></li>
		<li class="last"><a href="#">全业务超时比较</a></li>
	</ul></div></div>
</div>
<div class="section">
<div class="message blue">
		<span><b>说明：</b>本页面查看全业务超时比较。（请点击柱子查看单个业务系统超时前10详细信息。）</span>
	</div>
	<div class="box">
		<div class="title">
			查看系统超时比较
			<span class="hide"></span>
		</div>
		<div class="content">
			<form onsubmit="return false;">
				<table>
					<tr>
						<th>
							<select id="time">
								<option value="week">本周</option>
								<option value="month">本月</option>
							</select>
							<button class="orange"  id="searchBtn" >
								<span>查看</span>
							</button>
						</th>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			超时前10位业务系统超时数量比较
			<span class="hide" style="background-position: right top;"></span>
		</div>
		<div class="content" style="display: block;">
			<div id="barChart" class="flot-graph" style="width: 100%; height: 450px; padding: 0px; position: relative;">
			</div>
		</div>
	</div>
</div>
<div class="section" id="details">
	<div class="box">
		<div class="title">
			超时API 排名前10
			<span class="hide"></span>
		</div>
		<div class="content" id="detail">
			<table id='list1'></table>
		</div>
	</div>
</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1"); 
	grid.jqGrid({
		colNames: ['Service名', '响应级别', '超时数量', '平均消耗时间（毫秒）'],
		colModel: [
		           {name: 'serviceName', index: 'serviceName'},
		           {name: 'level', index: 'level', align:'center', width: '40'},
		           {name: 'num', index: 'num', align:'center', width: '40'},
		           {name: 'averageTime', index: 'averageTime', align:'center', width: '40', formatter: 'number', formatoptions: {decimalPlaces: 2}}
		           ],
		rownumbers: true,
		height: '230',
		jsonReader: {
			root: 'pager.records',
			repeatitems: false
		}
		
	});
	var w = $("#details").width();
	grid.setGridWidth(w * 0.95);
	
	$('#detail').hide();	
</script>

</body>
</html>
