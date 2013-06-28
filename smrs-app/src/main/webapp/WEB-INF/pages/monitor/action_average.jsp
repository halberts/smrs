<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>全业务调用汇总</title>
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pieRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>
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

#linechart table.jqplot-table-legend {
	margin-left: 50px;
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

var appIds;	// 全局变量，为click事件使用

/**
 * 生成直线和柱状组合图
 */
function plotLineChart(data, title) {
	$("#linechart").empty();
	//alert(data+'====='+title);
	/**
	 * plot8, 动态直线图
	 */
	 
	var bardatas = eval(data.bardatas); 
	var linedatas = eval(data.linedatas);
	var ticks = data.ticks;
	if(ticks == null || ticks.length == 0) {
		$("#linechart").html("未找到同期数据。");
		return ;
	}
	
	 $.jqplot("linechart", [bardatas, linedatas], {
		    	title: title, 
		        // Turns on animatino for all series in this plot.
		        animate: true,
		        // Will animate plot on calls to plot1.replot({resetAxes:true})
		        animateReplot: true,

		        series:[
		            // bar   
		            {
		            	renderer: $.jqplot.BarRenderer,
		                pointLabels: {
		                    show: true
		                },
		                showHighlight: true,	
		                rendererOptions: {
		                    animation: {
		                        speed: 2500
		                    },
		                    barWidth: 15,
		                    barPadding: -15,
		                    barMargin: 0,
		                    highlightMouseOver: false
		                }
		            }, 
		            
		            // line
		            {
		            	yaxis: 'y2axis',
		                rendererOptions: {
		                    animation: {
		                        speed: 2000
		                    }
		                }
		            }
		        ],
		        
		        axesDefaults: {
		            pad: 0
		        },
		        
		        
		        axes: {
		            xaxis: {
		           		renderer: $.jqplot.CategoryAxisRenderer,
		           		ticks: ticks
		            },
		            
		            
		            yaxis: {
		                tickOptions: {
		                    formatString: "%d"
		                },
		                rendererOptions: {
		                    forceTickAt0: true
		                }
		            },
		            y2axis: {
		                tickOptions: {
		                    formatString: "%.2f"
		                },
		                rendererOptions: {
		                    alignTicks: true,	// 让y2的ticks和y1的ticks在一条直线上。
		                    forceTickAt0: true
		                }
		            }
		        },
		        
		        highlighter: {
		            show: true, 
		            showMarker: true,
		            sizeAdjust: 7.5 , 
		            tooltipAxes: 'y',
		            tooltipLocation : 'ne'
		            
		        },
		        legend: {
		        	show: true,
		        	location: 'ne',
		        	placement: 'outsideGrid',
		        	labels: ['调用频次（次）', '平均消耗时间（毫秒）']
		        	
		        }
		        
		    });
	
}


/**
 * 生成饼图
 */
function plotChart(data) {
	
	 var piedata = eval(data.averageTimeWithName); 
	if(piedata.length == 0) {
		addMessageInfo("red","未查到相关数据。");
		return ;
	}
	
	//$("#details").show();
	//$("#result").show('slow');	//先显示div，否则jqplot将无法显示。
	appIds = data.applists;
	$("#piechart").empty();
	
	$.jqplot('piechart', [piedata], 
			{
				/* title: '全业务调用比较', */
				seriesDefaults: {
		  			shadow: false, //阴影效果
		  			renderer: jQuery.jqplot.PieRenderer, 
		  			rendererOptions: { 
		  				showDataLabels: true
		  			}
		  			
		  		},
		  		legend: {
		  			show: true,
		  			location: 'nw'
		  		},
		  		grid: {
		  			drawBorder: false,
		  			background: '#fff',
		  			shadow: false
		  		}
		  		
			}
	
	);
/* 	
	$("#piechart .jqplot-table-legend").css({
		top: '40px',
		left: '80px'
		
	}); */
	
}


$(document).ready(function(){
	//loadLeftMenuAndNav();
	
    $("#searchBtn").click(function(){
    	$(".message.red").hide();
    	
    	
    	//$("#appdetail").hide();
    	//var sp = $("#timeDiv ul.inline-ul li").find('span.current');	// 获取选中的span.
		var time = $("#time").val();
    	var url = "getActionAverageTime.action?time=" + time;
    	
    	$.ajax({
    		url: url,
    		type: 'GET',
    		success: function(data){
    			
    			plotChart(data);
    			var title;
    			if(time == 'week') {
    				title = '本周';
    			} else {
    				title = '本月'
    			}
    			//$("#recent3Chart").html(title+"全业务调用频次信息比较<span class='hide'></span>");
    			$("#list1").jqGrid("setGridParam", {url: url}).trigger("reloadGrid").jqGrid(/* 'setCaption', title + ' 全业务调用频次详细信息' */);
    			
    			
    			
    		},
    		error: function(){
    			addMessageInfo("red","ajax获取数据出错。");
    			return ;
    		}
    	});
    	
    	
    }); 
     
    // 绑定pie的点击事件 
    $("#piechart").bind('jqplotDataClick',
    		function(ev, seriesIndex, pointIndex, data){
    			var appname = data[0];
    			var appId = appIds[pointIndex];
    			//var sp = $("#timeDiv ul.inline-ul li").find('span.current');	// 获取选中的span.
    			var time = $("#time").val();
    			var timeText = $("#time option:selected").text();
    	    	var url = "getThreeWeekOrYearAverage.action?time=" + time + "&appId=" + appId;		
    	     
    	    	//alert(url);
    	    	var title;
    	    	if(time == 'week') {
    	    		title = appname + ' 近三周调用情况'; 
    	    	} else {
    	    		title = appname + ' 近三月调用情况'; 
    	    	}
    	    	
    	    	$.ajax({
    	    		url: url,
    	    		type: 'GET',
    	    		success: function(data) {
    	    			//$("#appdetail").show();   
    	    			plotLineChart(data, title);
    	    			// var gridurl = "getAppCallTopTen.action?time=" + time + "&appId=" + appId;   
    	    			//$("#list2").jqGrid("setGridParam", {url: gridurl}).trigger("reloadGrid").jqGrid('setCaption', appname+ '' + timeText + ' 调用前10 的service');
    	    			var gridurl = 'searchTopTenProfile.action?appId=' + appId + '&time='+time;
    	    			$("#list2").jqGrid('setGridParam', {url: gridurl}).trigger('reloadGrid').jqGrid('setCaption', appname+ '' + timeText + '平均时间后十的Action');
    	    			
    	    			$("#rightContent").animate({
    	                	scrollTop: $("#linechart").offset().top-80
    	                }, 1000);
    	    		},
    	    		
    	    		error: function() {
    	    			addMessageInfo("red","ajax获取数据出错。");
    	    			return ;
    	    		}
    	    	});
    	    	
    	    	 	    	
			}
    ); 
	
    
    /**
	 * 最大化时重新设定表格宽度
	 */
 	$("#clickDiv").click(function(){
 		var w = $("#details").width();
 		$("#list2").setGridWidth(w * 0.7);
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
		<li class="last"><a href="#">全业务调用汇总</a></li>
	</ul></div></div>
</div>
<div class="section">
<div class="message blue">
		<span><b>说明：</b>本页面查看全业务Action平均响应时间汇总情况。（点击饼图查看具体系统近三周/三月 的调用频次和平耗时，以及平均时间后10）</span>
	</div>
	<div class="box" >
		<div class="title">
			选择查询条件
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
	<div class="half">
		<div class="box">
			<div class="title">
				全业务Action平均响应时间比较（请点击饼图查看最近三周/三月数据比较）
				<span class="hide"></span>
			</div>
			<div class="content"  style="overflow-y: scroll">
				<div id="piechart" class="flot-graph" style="width: 100%; height: 250px; padding: 0px; position: relative;">
				</div>
			</div>
		</div>
	</div>
	<div class="half">
		<div class="box">
			<div class="title" id="recent3Line">
				全业务Action调用频次详细信息
				<span class="hide"></span>
			</div>
			<div class="content" id="listContent">
				<table id='list1'></table>
			</div>
		</div>
	</div>
</div>
<div class="section">
		<div class="box">
			<div class="title" id="recent3Chart">
				近三周/月调用比较
				<span class="hide"></span>
			</div>
			<div class="content">
				<div id="linechart" class="flot-graph" style="width: 100%; height: 250px; padding: 0px; position: relative;">
				</div>
			</div>
		</div>
</div>
<div class="section">
	<div class="box">
		<div class="title" id="recent3Detial">
			平均响应时间后10的Action
			<span class="hide"></span>
		</div>
		<div class="content">
			<table id='list2'></table>
		</div>
	</div>
</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1");
	grid.jqGrid({
		colNames: ['应用ID','应用系统', '调用总次数','平均消耗时间（毫秒）'], 
		colModel: [
				   {name: 'appId', index: 'appId',hidden:true},
		           {name: 'appName', index: 'appName'},
		           {name: 'callNum', index: 'callNum'}, 
		           {name: 'averageTime', index: 'averageTime', align:'center', formatter: 'number', formatoptions: {decimalPlaces: 2}}
		           ],
		rownumbers: true,
		height: '250',
		jsonReader: {
			root: 'list',
			repeatitems: false
		},
		ondblClickRow: function(rowid, iRow, iCol, e) {
			var appName = grid.jqGrid('getRowData', rowid).appName;  
			var appId = grid.jqGrid('getRowData', rowid).appId;
			var time = $("#time").val();
			var timeText = $("#time option:selected").text();
	    	var url = "getThreeWeekOrYearAverage.action?time=" + time + "&appId=" + appId;		
	    	var title;
	    	if(time == 'week') {
	    		title = appName + ' 近三周调用情况'; 
	    	} else {
	    		title = appName + ' 近三月调用情况'; 
	    	}
	    	
	    	$.ajax({
	    		url: url,
	    		type: 'GET',
	    		success: function(data) {
	    			//$("#appdetail").show();   
	    			plotLineChart(data, title);
	    			//$("#recent3Detial").html(title+"-调用前10的API<span class='hide'></span>");
	    			//$("#recent3Line").html(title+"<span class='hide'></span>");
	    			var gridurl = 'searchTopTenProfile.action?appId=' + appId + '&time='+time;
   	    			$("#list2").jqGrid('setGridParam', {url: gridurl}).trigger('reloadGrid').jqGrid('setCaption', appname+ '' + timeText + '平均时间后十的Action');
	    			
	    			
	    			$("#rightContent").animate({
	                	scrollTop: $("#linechart").offset().top-80
	                }, 1000);
	    		},
	    		
	    		error: function() {
	    			addMessageInfo("red","ajax获取数据出错。");
	    			return ;
	    		}
	    	});
		},
		rowNum: -1
		
	});
	grid.setGridWidth(jQuery('#listContent').width() * 0.98);
	$("#list2").jqGrid({
		colNames: ['Action名称', 'Action方法', '调用次数', '平均消耗时间（毫秒）'],
		colModel: [
		           {name: 'actionName', index: 'actionName'},
		           {name: 'actionMethod', index: 'actionMethod', align:'center', width: '20'},
		           {name: 'num', index: 'num', align:'center', width: '20'},
		           {name: 'averageTime', index: 'averageTime', align:'center', width: '40', formatter: 'number', formatoptions: {decimalPlaces: 2}}
		           ],
		viewrecords: true,
		height: '250',
		autowidth: true,
		rownumbers: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false
		} 
	});
	
	
	//var w = $("#details").width();
	//$("#list2").setGridWidth(w * 0.9);
	
	//$("#result").hide();
	//$("#appdetail").hide();
	
</script>

</body>
</html>
