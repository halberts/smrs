<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>单业务分组汇总</title>
	
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script>
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
	
</style>
<script>
var plot1;	// 全局变量
function plotChart(data) {
	$("#barchart").empty();
	
	var ticks = data.ticks;
	var bardata = eval(data.bardata); 
	
	if(data.ticks.length == 0) {
		$("#errorMsg").show();
		addMessageInfo("red",'未找到相关数据。');
		return ;
	}
	
	plot1 = $.jqplot('barchart', bardata, {
		animate: true,
	      seriesDefaults: {
	          renderer:$.jqplot.BarRenderer,
	          pointLabels: { 
	          	show: true
	          },
	          rendererOptions: {
	        	  barWidth: 23
	          }
	      },
	      axes: {
	          xaxis: {
	              renderer: $.jqplot.CategoryAxisRenderer,
	              ticks: ticks
	          }
			
	      }
	});
}

$(document).ready(function(){
	/**
	 * 查询按钮
	 */
	$("#searchBtn2").click(function(){
		$("#errorMsg").hide();
		var appId = $("select[name='appSel']").find('option:selected').val();
		var appnm = $("select[name='appSel']").find('option:selected').text();
		var sdate = $("#sdate").datepicker("getDate");
		var edate = $("#edate").datepicker("getDate");
		var serviceName = $("#svcApi2").val();
		var diff = edate - sdate;	// 相差的毫秒数
		var days = diff / (1000*60*60*24);	// 相差的天数
		if(days <= 0) {
			$("#errorMsg").show();
			addMessageInfo("red",'结束日期必须大于起始日期。');
			return ;
		} else if(days > 30) {
			$("#errorMsg").show();
			addMessageInfo("red",'日期跨度不得超过30天。');
			return ;
		} else {
			var from = $("#sdate").val();
			var to = $("#edate").val();
			var url = 'serviceGroupIssue.action?appId=' + appId + '&from=' + from + '&to=' + to;
			$.ajax({
				url: url,
				type: "GET",
				success: function(data){
					plotChart(data);
				},
				error: function(){
					addMessageInfo("red",'ajax获取图表数据出错。');
					return ;
				}
			});
			
		}
		
	});
	
	/**
	 * 最大化时重新设定表格宽度
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
			<li class="last"><a href="#">单业务分组汇总</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面查看一个月内单个业务系统各分组异常数量。</span>
		</div>
	</div>
	<div class="section" id='cretial'>
		<div id="errorMsg">
			<jsp:include page="/common/messages.jsp" />
		</div>
		<div class="box">
			<div class="title">
				单业务分组汇总
				<span class="hide"></span>
			</div>
			<div class="content">
				<table>
		        	<tr>
						<th>
							<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
							时间：
							<sj:datepicker id="sdate" displayFormat="yy-mm-dd" showOn="focus"/> 
							到
							<sj:datepicker id="edate" displayFormat="yy-mm-dd" showOn="focus"/> 
							<button type="button" id="searchBtn2" class="blue"><span>查看</span></button>
						</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="section" id='details'>
		<div class="box">
			<div class="title">
				各分组异常情况
				<span class="hide"></span>
			</div>
			<div class="content">
				<div id="barchart" style="width:98%; height:340px;z-index:0"></div>
			</div>
		</div>
	</div>
</body>
</html>
