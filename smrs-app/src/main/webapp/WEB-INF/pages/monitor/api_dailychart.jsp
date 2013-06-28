<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>API 24小时调用汇总</title>
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>
<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
<style>
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
var plot1;
var ticks;
function plotChart(data) {
	$("#chart_animate_line").empty();
	$(".message.red").hide();
	
	/**
	 * plot8, 动态直线图
	 */
	 
	var bardata = eval(data.bardata); 
	var linedata = eval(data.linedata);
	ticks = data.ticks;
	
	if(ticks.length == 0) {
		addMessageInfo("red","未查到相关数据。");
		return ;
	}
	
	plot1 = $.jqplot("chart_animate_line", [bardata, linedata], {
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
        	placement: 'outsideGrid',
        	labels: ['调用频次（次）', '平均消耗时间（毫秒）']
        }
        
    });
	
}


$(document).ready(function(){
	//loadLeftMenuAndNav();
	//$('#dt').datepicker({dateFormat: 'yy-mm-dd'});
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		var appId = $("select[name='appSel']").find("option:selected").val();
		var from = $("#dt").val();
		if(from == '') {
			addMessageInfo("red","请选择日期。");
			return ;
		}
		var selectedDate = $("#dt").val();
		$("#date").val(selectedDate);//将当前选择的日期设置到hidden域中
		var url = "getApiDailyCall.action?appId=" + appId + "&from=" + from;
		$.ajax({
			url: url,
			type: "GET",
			success: function(data){
				$("#details").show();
				plotChart(data);
				
			},
			error: function(){
				addMessageInfo("red","ajax获取图表数据出错。");
				return ;
			}
		});
	});
	
	$('#chart_animate_line').bind('jqplotDataClick',
        function (ev, seriesIndex, pointIndex, data) {
			var appId = $("select[name='appSel']").find("option:selected").val();
			//var hour = data[0]-1;//小时数
			var hour = ticks[data[0]-1].substring(0,2);
			var selectedDate = $("#date").val();
			var split = selectedDate.split('-');
			var date = new Date(split[0], split[1] - 1,split[2]);
			date.setHours(parseInt(hour,10),0,0,0);
			var fromDate = date.getTime();//alert(parseInt(hour)+1);
			date.setHours(parseInt(hour,10)+1,0,0,0);
			var toDate =  date.getTime();
			var url = "searchAppOvertimeTopAPI.action?fromeDate=" + fromDate + "&appId=" + appId+"&toDate="+toDate;
			//var title = appName + ' ' + timeText + ' 超时前10详细信息';
			//$("#details div.box div.title").html(title+"<span class='hide'></span>");
			$("#list1").jqGrid("setGridParam", {url: url}).trigger("reloadGrid");/*.jqGrid('setCaption' , title );*/
            $('#detail').show();
            
			//调用前10的API 
			var url2 = "searchAppCallSumTopAPI.action?fromeDate=" + fromDate + "&appId=" + appId+"&toDate="+toDate;
			$("#list2").jqGrid("setGridParam", {url: url2}).trigger("reloadGrid");/*.jqGrid('setCaption' , title );*/
            $('#detail2').show();
            $("#rightContent").animate({
            	scrollTop: $("#list1").offset().top
            }, 1000);
            $("#rightContent").animate({
            	scrollTop: $("#list2").offset().top
            }, 1000);
        }
    );
	/**
	 * 最大化时重新设定图表宽度
	 */
 	$("#clickDiv").click(function(){
 		plot1.replot();
	}); 
	
});
</script>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">监控中心</a></li>
		<li><a href="#">综合监控报告</a></li>
		<li class="last"><a href="#">API频次—24小时查询</a></li>
	</ul></div></div>
</div>
<div class="section">
	<div class="message blue">
		<span><b>说明：</b>本页面查看一天24小时内API调用频次和平均消耗时间对比图。</span>
	</div>
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<form onsubmit="return false;">
				<table>
					<tr>
						<th>
							<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
							请选择查看时间：
							<sj:datepicker id="dt" displayFormat="yy-mm-dd" showOn="focus"/> 
							<button class="orange"  id="searchBtn" >
								<span>查看</span>
							</button>
							<input type="hidden" id="date"/>
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
			API24小时调用频次
			<span class="hide"></span>
		</div>
		<div class="content" style="display: block;">
			<div id="chart_animate_line" class="flot-graph" style="width: 100%; height: 350px; padding: 0px; position: relative;">
			</div>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			调用频次前10的API
			<span class="hide"></span>
		</div>
		<div class="content" id="detail2">
			<table id='list2'></table>
		</div>
	</div>
</div>
<div class="section" id="details">
	<div class="box">
		<div class="title">
			调用超时时间前10的API
			<span class="hide"></span>
		</div>
		<div class="content" id="detail">
			<table id='list1'></table>
		</div>
	</div>
</div>
<!-- 生成分页表格 -->
<script type='text/javascript'>
	//调用超时时间前10的API
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
	
	//调用量
	var grid2 = $("#list2"); 
	$("#list2").jqGrid({
		colNames: ['Service名称', '阀值', '响应级别', '调用次数', '平均消耗时间（毫秒）'],
		colModel: [
		           {name: 'serviceName', index: 'serviceName'},
		           {name: 'alertMax', index: 'alertMax', align:'center', width: '20'},
		           {name: 'overtimeLv', index: 'overtimeLv', align:'center', width: '20'},
		           {name: 'callNum', index: 'callNum', align:'center', width: '30'},
		           {name: 'averageTime', index: 'averageTime', align:'center', width: '40', formatter: 'number', formatoptions: {decimalPlaces: 2}}
		           ],
		viewrecords: true,
		height: '230',
		autowidth: true,
		rownumbers: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false
		}
		
	});
	grid2.setGridWidth(w * 0.95);
	$('#detail2').hide();	
</script>
</body>
</html>
