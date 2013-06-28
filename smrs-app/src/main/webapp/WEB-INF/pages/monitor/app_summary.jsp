<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>应用接入变化趋势汇总</title>
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
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">监控中心</a></li>
		<li><a href="#">综合监控报告</a></li>
		<li class="last"><a href="#">接入应用汇总</a></li>
	</ul></div></div>
</div>
<div class="section">
<div class="message blue">
		<span><b>说明：</b>汇总1年接入监控中心的应用每月的变化情况。</span>
	</div>
</div>
<div class="section">
		<div class="box">
			<div class="title" id="recent3Chart">
				接入应用总数<b><font id="totalApp" color="red" size="3"></font></b>个，本月较上月变化<b><font id="deltaApp" color="red" size="3"></font></b>个
				<span class="hide"></span>
			</div>
			<div class="content">
				<div id="linechart" class="flot-graph" style="width: 100%; height: 320px; padding: 0px; position: relative;">
				</div>
			</div>
		</div>
</div>
<div class="section" id="details">
	<div class="box">
		<div class="title">
			应用息息明细
			<span class="hide"></span>
		</div>
		<div class="content" id="detail">
			<table id='list1'></table>
		</div>
	</div>
</div>

<script>
$(document).ready(function(){
	$.ajax({
		url: "appSummary.action",
		type: 'GET',
		success: function(data) {
			$("#totalApp").html(data.appSummaryModel.totalAppSize);
			var linedatas = new Array();
			var ticks = new Array();
			for(var p in data.appSummaryModel.appMap){
				linedatas.push(data.appSummaryModel.appMap[p]);
				ticks.push(p);
			}
			var deltaApp = linedatas[linedatas.length-1]-linedatas[linedatas.length-2];
			if(deltaApp >= 0){
				deltaApp = "+"+deltaApp;
			}
			$("#deltaApp").html(deltaApp);
			plotLineChart(linedatas,ticks);
			
			gridReport(data.appSummaryModel.issueSupporterMap);
			var mydata = data.appSummaryModel.appList;
			for(var i=0;i<=mydata.length;i++){
				$("#list1").jqGrid('addRowData',i+1,mydata[i]);
			}
			
		},
		
		error: function() {
			addMessageInfo("red","ajax获取数据出错。");
			return ;
		}
	});
	
	
});
function gridReport(issueSupportMap){
	//grid
	//调用超时时间前10的API
	var grid = $("#list1"); 
	grid.jqGrid({
		colNames: ['应用标示', '接入时间','负责人ID', '第一负责人','第一负责人电话','第一负责人邮箱','第二负责人','第二负责人电话','第二负责人邮箱'],
		colModel: [
		           {name: 'appName', index: 'appName', width: '40'},
		           {
		        	   name:'gmtCreate',index:'gmtCreate',width:40,
					   formatter : function(value, options, rData){
						   var gmtCreate = rData.gmtCreate;
						   var tIndex = gmtCreate.indexOf("T");
						   return gmtCreate.substring(0,tIndex);
					   }
		           },
		           {name: 'sptId', index: 'sptId',align:'center',width:'70',hidden:true},
		           {name: 'ownerName', index: 'ownerName', align:'center', width: '40',key : true,
		        	   formatter:function(value,options,rData){
		        		   //return rData.sptId;
		        		   return issueSupportMap[rData.sptId].ownerName;
		           	   }
		           },
		           {name: 'ownerPhone', index: 'ownerPhone', align:'center', width: '40',key : true,
		        	   formatter:function(value,options,rData){
		        		   //return rData.sptId;
		        		   return issueSupportMap[rData.sptId].ownerPhone;
		           	   }
		           },
		           {name: 'ownerEmail', index: 'ownerEmail', align:'center', width: '40',key : true,
		        	   formatter:function(value,options,rData){
		        		   //return rData.sptId;
		        		   return issueSupportMap[rData.sptId].ownerEmail;
		           	   }
		           },
		           {name: 'sptOwnerName', index: 'sptOwnerName', align:'center', width: '40',key : true,
		        	   formatter:function(value,options,rData){
		        		   //return rData.sptId;
		        		   return issueSupportMap[rData.sptId].sptOwnerName;
		           	   }
		           },
		           {name: 'sptOwnerPhone', index: 'sptOwnerPhone', align:'center', width: '40',
		        	   formatter:function(value,options,rData){
		        		   //return rData.sptId;
		        		   return issueSupportMap[rData.sptId].sptOwnerPhone;
		           	   }
		           },
		           {name: 'sptOwnerEmail', index: 'sptOwnerEmail', align:'center', width: '40',key : true,
		        	   formatter:function(value,options,rData){
		        		   //return rData.sptId;
		        		   return issueSupportMap[rData.sptId].sptOwnerEmail;
		           	   }
		           }
		           ],
		rownumbers: true,
		height: 'auto',
		jsonReader: {
			root: 'data.appSummaryModel.appList',
			repeatitems: false
		}
		
	});
	var w = $("#details").width();
	grid.setGridWidth(w * 0.95);
}
/**
 * 生成直线和柱状组合图
 */
function plotLineChart(linedatas, ticks) {
	 $.jqplot("linechart", [linedatas], {
		        animate: true,
		        animateReplot: true,
		      seriesDefaults: { 
		          showMarker:true,
		          pointLabels: { show:true } 
		      },
		      axes: {
		        xaxis: {
		          pad: 0,
		          renderer: $.jqplot.CategoryAxisRenderer,
	           	  ticks: ticks
		        },
		      }
	});
}
</script>
</body>
</html>
