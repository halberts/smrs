<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.haier.openplatform.console.util.CronExpressionEx"%>
<%@page import="com.haier.openplatform.console.util.DateFormatUtil"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>任务运行监控</title>
<link type="text/css" rel="stylesheet"
	href="${staticURL}/style/jquery.jqplot.min.css" />
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.multiselect.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasAxisTickRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasTextRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.cursor.min.js"></script>
<script src="${staticURL}/scripts/artdialog/jquery.artDialog.js?skin=opera"></script>
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

.searchDate {
	width: 100px;
}

.ui-corner-all,.ui-corner-bottom,.ui-corner-right,.ui-corner-br {
	border-radius: 3px;
}

.error {
	color: red;
}

.executed {
	color: green;
}

.normal {
	color: black;
}


button.ui-button:hover {
	background: #FDF5CE;
	border:1px solid #FBCB09; 
	
}

.frzCol {
	background-color: #FFFBCC; 
}

.jqplot-highlighter-tooltip {
	font-size: 12px;
}


.updated {
	/* background-color: rgb(255, 205, 174);  */
	background-color: #9AFF9A;
}

.not_updated {
	background-color: #FFFBCC; 
}


#lockBtn {
	position:relative; 
	top: -3px; 
	margin-right:2px;
	color: black;
}

#lockBtn:hover {
	color: rgb(225, 112, 9);
	text-decoration: underline;
}


</style>

<script>
	var loop;
	var plotChart;
	var jobId;	// 全局变量，历史数据中使用。
	
	
	/**
	 * 根据jobState来设置单元格样式
	 */
	function setCellStyle(grid, rowid, aData) {
		if (aData.jobState == '错误') {
			grid.setCell(rowid, 'jobState', '', 'error', {
				title : 'error occurs'
			});
		} else if (aData.jobState == '已执行') {
			grid.setCell(rowid, 'jobState', '', 'executed', {
				title : 'job executed'
			});
		
		} else {
			grid.setCell(rowid, 'jobState', '', 'normal', {
				title : ''
			});
		
		}
		
		/**
		 * 高亮updated行
		 */
		if(aData.isUpdated == 'true') {
			grid.setCell(rowid, 'schedName', '', 'updated');
			grid.setCell(rowid, 'jobGroup', '', 'updated');
			grid.setCell(rowid, 'jobName', '', 'updated');
		} else {
			grid.setCell(rowid, 'schedName', '', 'not_updated');
			grid.setCell(rowid, 'jobGroup', '', 'not_updated');
			grid.setCell(rowid, 'jobName', '', 'not_updated');
		}
	}
	
	function plotLineChart(data) {
		$("#historyChart").empty();
		var dt = data.jobHistories;
		var from = data.from, to = data.to;
		var searchRange = from + ' - ' + to;
		if(dt.length == 0) {
			$("#hisErr").text(searchRange + '    没有历史数据。');
			return ;
		}
		
		var dtt = eval(dt);
		var xmin, xmax;
		
		var yRange = data.yRange;
		var ymin = yRange[0], ymax = yRange[1];
		
		if(ymin == ymax && ymax == 0) {
			ymax = 1;
		}
		
		var title = data.jobName;
		var days = data.daysRange;
		
		var tickInterval;
		var fmt;
		if(days == 1) {	
			xmin = from + ' ' + '00:00';
			xmax = from + ' ' + '23:00';
			tickInterval = '1 hour';
			fmt = '%H';
			searchRange = from + '一天24小时情况';
			
		} else {
			xmin = from;
			xmax = to;
			
			if(days <= 7) {
				tickInterval = '1 day';
			} else if(days <= 30) {
				tickInterval = '4 days';
			} else if(days <= 60) {
				tickInterval = '1 week';
			} else{
				tickInterval = '2 weeks';
			}
			
			fmt = '%Y-%#m-%#d';
		}
		$.jqplot('historyChart', [dtt], {
			  title: title,
	          animate: true,
	          animateReplot: true,
			      axes:{
			        xaxis:{  		        	
						min: xmin,      
	  					max: xmax, 				
					  	renderer:$.jqplot.DateAxisRenderer, //x轴展示形式为日期
					  	tickInterval: tickInterval,
					  	tickOptions:{
						  formatString: fmt
					 	},
					 	label: searchRange
			        },
			        yaxis:{
					  	  min: ymin,      
						  max: ymax, 		        	
						  tickOptions:{
							showLabel: true,
							formatString:'%d 秒' 
						  }
			        }
			      },
		
	        highlighter: {
	        	show:true,
	            showMarker:true,
	            tooltipAxes: 'xy',
	            yvalues: 4,
	            formatString:'<table class="jqplot-highlighter"> \
	                <tr><td>日期:</td><td>%s</td></tr> \
	                <tr><td>运行时间:</td><td>%s</td></tr> \
	                <tr><td style="color:green">成功:</td><td style="color:green">%s</td></tr> \
	                <tr><td style="color:red">失败:</td><td style="color:red">%s</td></tr> \
	            </table>'
	        },
	        
	        cursor:{
	            show: true,
	            zoom: true
	        } 
		
		});
		
		
	}
	
	
	
	function getChartData(jobId) {
		var historyRange = $("#hidSel").find('option:selected').val();
		$.ajax(
				{
					url : 'jobHisView.action?jobId='+jobId + '&historyRange=' + historyRange,
					success : function(data)
					{
						$("#hisErr").text('');
						var dt = data.jobHistories;
						if(dt.length == 0) {
							$("#hisErr").text('没有历史数据');
						}
						
						/* var dialog = art.dialog(
						{
							title : '历史数据（可拖动放大，请双击还原）',
							lock : true,
							content : document.getElementById('historyDiv'),
							width : '650px',
							close : function(){
								
							}

						}); */
						
						plotLineChart(data);
						
					},
					
					error: function() {
						$("#hisErr").text('ajax获取历史数据出错。');
						return;
					}


				});
	}
	 
	function refreshGridDatas() {
		
		clearInterval(loop);	// 先停止定时器，待更新完表格后再开启（防止网络延时等问题）
		$("#quartzConsoleTbl").trigger('reloadGrid');
		loop = setInterval(refreshGridDatas, 2000);	//重新开启定时器
	}
	
	
	$(document).ready(function() {
		//loadLeftMenuAndNav();	// 左侧及导航条。
		
		$('#historyChart').bind('jqplotDataClick', 
	          function (ev, seriesIndex, pointIndex, data) {
	              
	              $("#errDtl").html(data[4]);
	              $("#historyCrtDetail").slideDown();
	          }
	    );
				
		
		/**
		 * 鼠标移进事件
		 */
		$("#triggerTimeExp").mousedown(function(){
			$("#errInfo").text('');
		});
		
		
		/**
		 * 触发任务 
		 */
		$("#triggerBtn").click(function(){
			$("#errInfo").text('');
			
			var selectedRows = $("#quartzConsoleTbl").jqGrid('getGridParam', 'selarrrow');
			
			var len = selectedRows.length;
			if(len == 0) {
				$("#errInfo").text('请选择要触发的任务');
				return ;
			}
			var jobIds = [];
			for (var i = 0; i < len; i++) {
				var row = $("#quartzConsoleTbl").jqGrid('getRowData', selectedRows[i]);
				jobIds.push(row.jobId);
			}
			
			// jobIds 传给后台 action，不返回数据。 action定义jobIds来获取即可。
			$.ajax({
				url: 'jobTrigger.action?jobIds=' + jobIds,
				type: 'POST',
				cache: false,
				error: function() {
					$("#errInfo").text('触发任务后台出错。');
				}
			});
			
		});
		
		
		/**
		 * 设置定时任务
		 */
		$("#calenderBtn").click(function(){
			
			$("#errInfo").text('');
			
			
			var triggerTimeExp = $("#triggerTimeExp").val();
			var rule = "((\\d|/|\\*|\\?)+\\s+){5}(\\d|/|\\*|\\?)?";
			var exp = new RegExp(rule);
			var result = exp.test(triggerTimeExp);	// 用正则表达式校验输入是否规范
			
			if(result == false) {
				$("#errInfo").text('表达式格式有误。');
				return ;
			}

			// job selection validation
			var selectedRows = $("#quartzConsoleTbl").jqGrid('getGridParam', 'selarrrow');			
			var len = selectedRows.length;
			if(len == 0) {
				$("#errInfo").text('请选择要触发的任务');
				return ;
			}
			
			// find out all selected jobs
			var jobIds = [];
			for (var i = 0; i < len; i++) {
				var row = $("#quartzConsoleTbl").jqGrid('getRowData', selectedRows[i]);
				jobIds.push(row.jobId);
			}
			
			$.ajax({
				url: 'jobSchedule.action?expression=' + triggerTimeExp + '&jobIds=' + jobIds,
				type: 'POST',
				cache: false,
				error: function() {
					$("#errInfo").text('设置定时任务过程出错。');
				}
			});
			
		});
		
		/**
		 * 隐藏左侧时，重新设定表格宽度
		 */
		$(".clickBar").click(function(){
			var width = $("#gridContaier").width();
			$("#quartzConsoleTbl").setGridWidth(width);
			
		});
		
		
		/**
		 * 历史数据中查看按钮
		 */
		$("#hisBtn").click(function(){
			$("#hisErr").text('');
			
			var historyRange = $("#hidSel").find('option:selected').val();
			var url = 'jobHisView.action?jobId=' + jobId + '&historyRange=' + historyRange;
			
			$.ajax({
				url: url,
				cache: false,
				type: 'GET',
				success: function(data){
					$("#hisErr").text('');
					plotLineChart(data);
				},
				
				error: function(){
					$("#hisErr").text('ajax获取该时间段历史数据出错。');
					return ;
				}
			});
			
		});
		
		
		/**
		 * 暂停/开启自动刷新按钮（暂停定时器）
		 */
		$("#lockBtn").toggle(function(){
			$("#lockImg").attr('src', '${staticURL}/gfx/lock.jpg');
			$(this).text('开启自动刷新');
			clearInterval(loop);	// 先停止定时器，待触发或设定任务后再开启（防止网络延时等问题）
			
		}, function(){
			$("#lockImg").attr('src', '${staticURL}/gfx/unlock.jpg');
			$(this).text('暂停自动刷新');
			loop = setInterval(refreshGridDatas, 2000);
		});
		
		
		
		/**
		 * 最大化时重新设定表格宽度
		 */
	 	$("#clickDiv").click(function(){
	 		var width = $("#gridContaier").width();
			$("#quartzConsoleTbl").setGridWidth(width);
		}); 
		
		
		
		  $("#historyChart").bind('jqplotDataMouseOver',  function( ev, seriesIndex, pointIndex, data ){
			
			var top = $(".jqplot-highlighter-tooltip").css("top");
			var left = $(".jqplot-highlighter-tooltip").css("left");
			var topValue = top.replace("px","");
			var leftValue = left.replace("px","");
			topValue = parseFloat(topValue);
			leftValue = parseFloat(leftValue);
			if(topValue<-33){
				topValue = -33;
				var rightTop = topValue+"px";
				$(".jqplot-highlighter-tooltip").css("top",rightTop);
			}
			if(leftValue < -19){
				leftValue = -19;
				var rightLeft = leftValue +"px";
				$(".jqplot-highlighter-tooltip").css("left",rightLeft);
			}
		
		});  
		
	});
	
	
	
	 </script>


</head>

<body >
<div id="breadcrumbs">
	<div ><div><ul>
		<li class="first"></li>
		<li><a href="#">任务监控</a></li>
		<li class="last"><a href="#">任务执行监控</a></li>
	</ul></div></div>
</div>
<div class="section">
	<div class="message orange">
		<span><b>警告提示：</b>对任务的触发或修改都会对现有运行任务产生影响，所有对任务的更新操作将记录操作日志 ，请谨慎操作。</span>
	</div>
	<div class="message blue">
		<span><b>操作提示：</b>表格默认2秒自动刷新，若要定时任务和触发任务，请先暂停自动刷新，操作结束后再开启。</span>
	</div>
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			后台任务运行状态
			<span class="hide"></span>
		</div>
		<div class="content">
			<input type='text' id='triggerTimeExp' placeholder="在此处输入CRON表达式" size="20" onclick="openCronPlus()"/>
			<button id="calenderBtn" class="orange">
				<span >调整</span>
			</button>
			<button class="blue" id="triggerBtn" >
					<span>触发任务</span>
				</button>
				<a id='lockBtn' href='javascript:'>暂停自动刷新</a>
						<img  id='lockImg' src='${staticURL}/gfx/unlock.jpg' width='22' height='20' alt='解锁'/>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			双击查看历史数据（注：黄色列双击无效，绿色表示该行已更新）
			<span class="hide"></span>
		</div>
		<div class="content">
			<table id="quartzConsoleTbl" ></table>
		</div>
	</div>
</div>   

  <%@include file="cron_plugin.jsp" %>
     
     
     
     
    <sj:dialog 
    	id="myclickdialog" 
    	autoOpen="false" 
    	modal="true" 
    	title="历史数据（可拖动放大，请双击还原）"
    	width="600"  
    	onCloseTopics="dialogclosetopic"  
    >
    
    <div id='historyDiv' style=''>
	<span id='hisErr' style='color:red'></span>
	<div style='margin-right: 150px;'> 
		<select id='hidSel'>
			<option value='0'>当天</option>
			<option value='1' selected>过去一周</option>
			<option value='2'>过去一个月</option>
			<option value='3'>过去三个月</option>
		</select>
		
		<button class="" id="hisBtn" style='margin-bottom: 5px;margin-left:5px' >
			<span class="ui-button-text">查看</span>
		</button> 
	</div>
	<!-- <div style="height: 70px;"></div> -->
    <div id="historyChart" style="width: 550px; height: 300px;"></div>  
	<br />
	
 	<div id='historyCrtDetail' style='display: none;width: 550px;'>
		 <div class="content-box" style='margin-top: 20px; margin-bottom: 20px;'> 
			<div class="content-box-header">
				<h3>错误信息</h3>
			</div>
			<div class="content-box-content">
				<span id='errDtl' style='color:red'></span>
			</div>
		</div>
	
	</div> 
</div>
    </sj:dialog> 
    
    
    
		<script>
		
			
			$(document).ready(function() {
				$.subscribe('dialogclosetopic', function(event,ui) { 
			        $("#historyChart").empty();
					$("#historyCrtDetail").hide();
					$("#hisErr").text('');
					$("#hidSel").val(1);
				}); 
				// 分页表格
				var grid = $("#quartzConsoleTbl");
				grid.jqGrid('clearGridData');
				
				grid.jqGrid(
				{
					url : 'jobFullUpdate.action',
					colNames :
					[ 'jobId', 'isUpdated', '计划', '组', 'Job', '运行机器', '状态', '运行时间(毫秒)', '上次运行时间', '下次运行时间', '描述' ],
					colModel :
					[
					{
						name : 'jobId',
						index : 'jobId',
						align : "center",
						width : 20,
						hidden:true,	//隐藏列， 为trigger使用
						frozen: true,
						sortable: false
					},
					{
						name : 'isUpdated',
						index : 'isUpdated',
						align : "center",
						width : 20,
						hidden:true,	//隐藏列， 为高亮行使用
						frozen: true,
						sortable: false
					},
					{
						name : 'schedName',
						index : 'schedName',
						align : "center",
						width : 130,
						frozen: true,
						sortable: false,
						classes: 'frzCol'
					},
					{
						name : 'jobGroup',
						index : 'jobGroup',
						align : "center",
						width : 120,
						frozen: true,
						sortable: false,
						classes: 'frzCol'
					},
					{
						name : 'jobName',
						index : 'jobName',
						align : "center",
						width : 150,
						frozen: true,
						sortable: false,
						classes: 'frzCol'
					},
					{
						name : 'nodeName',
						index : 'nodeName',
						align : "center",
						sortable: false,
						width : 80
					},
					{
						name : 'jobState',
						index : 'jobState',
						align : "center",
						sortable: false,
						width : 80
					},
					{
						name : 'runtime',
						index : 'runtime',
						align : "center",
						sortable: false,
						width : 100
					},
					{
						name : 'prevFireTime',
						index : 'prevFireTime',
						align : "center",
						sortable: false,
						width : 160
					},
					{
						name : 'nextFireTime',
						index : 'nextFireTime',
						align : "center",
						sortable: false,
						width : 160
					},
					{
						name : 'description',
						index : 'description',
						align : "center",
						sortable: false,
						width : 160
					}],
					
					multiselect: true,
					autowidth: true,
					rownumbers : true,
					rowNum : 50,
					shrinkToFit : false,
					height : 348,
					/* caption: '双击查看历史数据（注：黄色列双击无效，绿色表示该行已更新）', */
					jsonReader :
					{
						root : 'jobViewMapsResponse',
						repeatitems : false
					},

					afterInsertRow : function(rowid, aData)
					{
						setCellStyle(grid, rowid, aData);
						
					},

					ondblClickRow : function(rowid, iRow, iCol, e)
					{
						$("#errInfo").text('');
						jobId = $("#quartzConsoleTbl").jqGrid('getRowData', rowid).jobId;
						getChartData(jobId); 
						$("#myclickdialog").dialog("open")
						
					}
					

				});

				grid.jqGrid('setFrozenColumns');
				
				var width = $(".section").width()-40;
				grid.setGridWidth(width);

				loop = setInterval(refreshGridDatas, 2000);

			});
		</script>

</body>
</html>
