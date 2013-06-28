<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.haier.openplatform.console.util.CronExpressionEx"%>
<%@page import="com.haier.openplatform.console.util.DateFormatUtil"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>quartz任务监控</title>
	
<style>
/* 设置jqgrid表格字体 */
.ui-jqgrid {
    font-size: 12px;
    position: relative;
}
</style>

<script>
$(document).ready(function(){
	var grid1 = $("#list1");
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		var appName = $("#appSel option:selected").text();
		var url = 'searchJobPlan.action?appName=' + appName;
		grid1.jqGrid('clearGridData');
		grid1.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
	});

});
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div><div><ul>
			<li class="first"></li>
			<li><a href="#">监控中心</a></li>
			<li><a href="#">Quartz监控</a></li>
			<li class="last"><a href="#">任务信息</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>点击Quartz执行计划在下方表格中查看执行明细。</span>
		</div>
	</div>
	<div class="section" id='cretial'>
		<jsp:include page="/common/messages.jsp" />
		<div class="box">
			<div class="title">
				查询：
				<span class="hide"></span>
			</div>
			<div class="content">
		        <table>
		        	<tr>
						<th>
							<s:select name='appSel' list='appList' listKey='id' listValue='appName'></s:select>
							<button type="button" id="searchBtn" class="orange"><span>查询</span></button>
						</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id='result' class='section'>
		<div class="box">
			<div class="title">
				查询结果
				<span class="hide"></span>
			</div>
			<div class="content">
				<table id='list1'></table>
				<div id='pager1'></div>
				<br/>
				<table id='list2'></table>
				<div id='pager2'></div>
			</div>
		</div>
	</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid1 = $("#list1");
	grid1.jqGrid({
		colNames: ['id', '任务名','任务描述','CRON表达式','创建时间','最后修改时间','计划最近更新','所在服务器','调度器实例','任务类型','优先级','任务分组','调度器','日历','版本'],
		colModel: [
				   {name:'id',index:'id', width:1,hidden:true,key:true,frozen : true},
		           {name: 'jobName', index: 'jobName', align:'center',width:'220',align:'left',frozen : true}, 
		           {name: 'jobDescription', index: 'jobDescription', align:'left',width:'350'}, 
		           {name: 'cronExpression', index: 'cronExpression',width:'220', align:'left',frozen : true}, 
		           {name: 'gmtCreate', index: 'gmtCreate',width:220,  formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'},hidden:true},
		           {name: 'gmtModified', index: 'gmtModified',width:220,  formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'},hidden:true},
		           {name: 'scheduledDate', index: 'scheduledDate',width:'180',  formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}},
		           {name: 'host', index: 'host',width:200, align:'left'}, 
		           {name: 'schedulerInstanceId', index: 'schedulerInstanceId',width:500, align:'center',hidden:true}, 
		           {name: 'triggerType', index: 'triggerType', align:'center',hidden:true}, 
		           {name: 'triggerPriority', index: 'triggerPriority', align:'center',hidden:true}, 
		           {name: 'jobGroup', index: 'jobGroup', align:'center',width:500,width:'170',hidden:true},
		           {name: 'schedulerName', index: 'schedulerName', align:'left',width:500,key:true,hidden:true},
		           {name: 'calendarName', index: 'calendarName', align:'center',hidden:true}, 
		           {name: 'schedulerVersion', index: 'schedulerVersion',width:80,  align:'center'}
		           ],
		pager: '#pager1',
		prmNames:{page:"pager.currentPage",rows:"pager.pageSize"},
		height: 'auto',
		rownumbers: false,
		viewrecords: true,
		jsonReader: {
			root:"pager.records",
			total: "pager.totalPages",
			page: "pager.currentPage",
			records: "pager.totalRecords",
			repeatitems: false
		},
		onSelectRow: function(rowid, iRow, iCol, e) {
			var jobPlanId = grid1.jqGrid('getRowData', rowid).id;
			var gridurl = "searchJobTrace.action?jobPlanId=" + jobPlanId;	
	    	$.ajax({
	    		url: gridurl,
	    		type: 'GET',
	    		success: function(data) {
   	    			$("#list2").jqGrid('setGridParam', {url: gridurl}).trigger('reloadGrid');
	    		},
	    		
	    		error: function() {
	    			addMessageInfo("red","ajax获取数据出错。");
	    			return ;
	    		}
	    	});
		},
	});
	var w = $("#right").width();
	grid1.setGridWidth(w*0.94);
	
	var grid2 = $("#list2");
	grid2.jqGrid({
		colNames: ['执行时间','下次执行时间','上次执行时间','运行节点','耗时(ms)','日历','错误消息'],
		colModel: [
				   {name: 'scheduledFireTime', index: 'scheduledFireTime',width:250,  formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}},
				   {name: 'nextFireTime', index: 'nextFireTime',width:250,  formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}},
				   {name: 'previousFireTime', index: 'previousFireTime',width:250,  formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}},
		           {name: 'host', index: 'host', align:'center',width:'200',align:'left'}, 
		           {name: 'jobRunTime', index: 'jobRunTime',width:'90',align:'right'}, 
		           {name: 'calendarName', index: 'calendarName', align:'center'}, 
		           {name: 'errorMessage', index: 'errorMessage', align:'center',width:400}
		           ],
		pager: '#pager2',
		prmNames:{page:"pager.currentPage",rows:"pager.pageSize"},
		height: 'auto',
		rownumbers: false,
		viewrecords: true,
		jsonReader: {
			root:"pager.records",
			total: "pager.totalPages",
			page: "pager.currentPage",
			records: "pager.totalRecords",
			repeatitems: false
		}
	});
	var w = $("#right").width();
	grid2.setGridWidth(w*0.94);	
</script>


</body>
</html>
