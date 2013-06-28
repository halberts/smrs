<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>异常查询</title>
<!--[if IE]>
<style type="text/css">
	#wrapper #container #right .section {
		display : block;
	}
</style>
<![endif]-->
<script>
$(document).ready(function(){
	var grid = $("#list1");
	$("#contentDetail").hide();
	$("#content").hide();
	/**
	 * tab1-查询按钮
	 */
	$("#searchBtn1").click(function(){
		$("#content").show();
		var appId = $("select[name='appSel']").find('option:selected').val();
		var time = $("select[name='weekAndMonth']").find('option:selected').val();
		var serviceName = $("#svcApi").val();
		var issueTypeId = $("#exSel").find("option:selected").val();
		
		var url = 'getIssues.action?appId=' + appId + '&time=' + time + '&serviceName=' + serviceName + '&issueTypeId=' + issueTypeId;
		grid.jqGrid('clearGridData');	// 先清空表格数据，为的是将pager重置。
		grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
	});
	
	
	/**
	 * tab2-查询按钮
	 */
	$("#searchBtn2").click(function(){
		hideOrShowRedAndGreen('');//隐藏错误和成功提示信息
		var appId = $("select[name='appSel']").find('option:selected').val();
		var sdate = $("#sdate").datepicker("getDate");
		var edate = $("#edate").datepicker("getDate");
		var serviceName = $("#svcApi2").val();
		var issueTypeId = $("#exSel2").find("option:selected").val();
		
		var diff = edate - sdate;	// 相差的毫秒数
		var days = diff / (1000*60*60*24);	// 相差的天数
		
		if(days <= 0) {
			addMessageInfo("red","结束日期必须大于起始日期。");
			return ;
		} else if(days > 30) {
			addMessageInfo("red","日期跨度不得超过30天。");
			return ;
		} else {
			$("#content").show();
			var from = $("#sdate").val();
			var to = $("#edate").val();
			var url = 'getIssues.action?appId=' + appId + '&from=' + from + '&to=' + to + '&serviceName=' + serviceName + '&issueTypeId=' + issueTypeId;
			grid.jqGrid('clearGridData');	// 先清空表格数据，为的是将pager重置。
			grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		}
		
	});
});
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div><div><ul>
			<li class="first"></li>
			<li><a href="#">监控中心</a></li>
			<li><a href="#">应用系统监控</a></li>
			<li class="last"><a href="#">异常查询</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="box">
			<div class="title">
				异常查询
				<span class="hide"></span>
			</div>
			<div class="content nopadding">
				<div class="tabs">
					<div class="tabmenu">
						<ul> 
							<li><a href="#tabs-1" onclick="$('#content').hide();$('#contentDetail').hide();">快速查询</a></li> 
							<li><a href="#tabs-2" onclick="$('#content').hide();$('#contentDetail').hide();">按时间段查询</a></li> 
						</ul>
					</div>
					<div id="tabs-1" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面可快速查询某系统异常情况。</span>
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
										时间：
										<s:select name="weekAndMonth" list="#{'2h':'近2小时','6h':'近6小时','12h':'近12小时','1d':'当天','3d':'近3天','week':'本周','month':'本月'}" />
										service名：
										<input type='text' id='svcApi' />
										异常类型：
										<select id="exSel">
											<option value="0">ALL</option>
											<option value="1">EXCPETION</option>
											<option value="2">EXCPETION WITHOUT ERROR CODE</option>
											<option value="3">OVERTIME</option>
											<option value="4">BETA RELEASE TRACE</option>
											<option value="5">DEBUG,ONLY AVAILABLE IN DEV/TEST</option>
										</select>
										
									</th>
									<th>
										<button type="button" id="searchBtn1" class="orange"><span>查询</span></button>
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-2" class="tab">
						<div class="message blue" style="margin:-5px 0 10px 0;">
							<span><b>说明：</b>本页面可查找一个月内任意时间段某系统异常情况。</span>
						</div>
						<div style="margin:0 0 10px 0;">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content" style="padding:0;">
							<table>
					        	<tr>
									<td>系统：</td>
									<td>
										<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
									</td>
									<td>时间：</td>
									<td colspan="2">
										<sj:datepicker id="sdate" displayFormat="yy-mm-dd" showOn="focus"/> 
										到
										<sj:datepicker id="edate" displayFormat="yy-mm-dd" showOn="focus"/> 
									</td>
								</tr>
								<tr>
									<td>service名：</td>
									<td><input type='text' id='svcApi2' /></td>
									<td>异常类型：</td>
									<td>
										<select id="exSel2">
											<option value="0">ALL</option>
											<option value="1">EXCPETION</option>
											<option value="2">EXCPETION WITHOUT ERROR CODE</option>
											<option value="3">OVERTIME</option>
											<option value="4">BETA RELEASE TRACE</option>
											<option value="5">DEBUG,ONLY AVAILABLE IN DEV/TEST</option>
										</select>
									</td>
									<td>
										<button type="button" id="searchBtn2" class="orange"><span>查询</span></button>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id='result' class='section'>
		<div class="box">
			<div class="title" id="resultTitle">
				查询结果（双击查看详细信息）
				<span class="hide"></span>
			</div>
			<div class="content" id="content">
				<table id='list1'></table>
				<div id='pager1'></div>
			</div>
		</div>
	</div>
	<div id='details' class="section">
		<div class="box">
			<div class="title">
				详细信息
				<span class="hide"></span>
			</div>
			<div class="content" id="contentDetail">
				<table border='1' style='width:100%'>
					<tr>
						<td>监控ID</td>
						<td><span id='issueID'></span></td>
						
						<td>Action名称</td>
						<td colspan="3"><span id='actionName'></span></td>
					</tr>
					<tr>
						<td>应用系统</td>
						<td><span id='appName'></span> </td>
						
						<td>节点名称</td>
						<td><span id='nodeAlias'></span> </td>
						
						<td>节点IP</td>
						<td><span id='nodeIp'></span></td>
					</tr>
					<tr>
						<td>发生时间</td>
						<td><span id='createTime'></span> </td>
						
						<td>执行时间</td>
						<td><span id='excuteTime'></span> </td>
						
						<td>当前线程数</td>
						<td><span id='currentThrNum'></span></td>
					</tr>
					<tr>
						<td>负责人</td>
						<td><span id='ownerName'></span></td>
						
						<td>电话</td>
						<td><span id='ownerPhone'></span></td>
						
						<td>邮件</td>
						<td><span id='ownerEmail'></span></td>
					</tr>
					<tr>
						<td>输入参数</td>
						<td colspan='5'><textarea id='inValue'  readonly="readonly" style='width:100%;height:100px;'></textarea></td>
					</tr>
					<tr>
						<td>异常详细信息</td>
						<td colspan='5'><textarea id='serviceEx'  readonly="readonly" style='width:100%;height:300px;'></textarea></td>
					</tr>
					<tr>
						<td>SQL栈信息</td>
						<td colspan='5'><textarea id='insight' readonly="readonly" style='width:100%;height:300px;'></textarea> </td>
					</tr>
				</table>
			</div>
		</div>
	</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1");
		grid.jqGrid({
		colNames: ['监控ID', '异常类型', '异常名称', 'Service名', '异常发生的节点', '发生时间'],
		colModel: [
		           {name: 'issueId', index: 'issueId', align:'center', width: '40'},
		           {name: 'issueType', index: 'issueType', width: '60'},
		           {name: 'exceptionName', index: 'exceptionName', width: '60'},
		           {name: 'serviceName', index: 'serviceName'},
		           {name: 'nodeName', index: 'nodeName', align:'center', width: '40'},
		           {name: 'createTime', index: 'createTime', align:'center', width: '60'}
		           ],
		height: '221',
		rownumbers: true,
		pager: '#pager1',
		viewrecords: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false,
			total: "total",
			page: "page",
			records: "records"
		},
		
		ondblClickRow: function(rowid, iRow, iCol, e) {
			var issueId = grid.jqGrid('getRowData', rowid).issueId;
			var url = 'issueDetailEx.action?issueId=' + issueId;
			$.ajax({
				url: url,
				type: 'GET',
				cache: false,
				success: function(data) {
					var detail = data.issueDetail;
					$("#issueID").text(issueId);
					$("#actionName").text(detail.actionName);
					$("#inValue").text(detail.inValue);
					$("#serviceEx").text(detail.serviceEx);
					$("#excuteTime").text(detail.excuteTime);
					$("#createTime").text(detail.createTime);
					$("#currentThrNum").text(detail.currentThrNum);
					$("#ownerName").text(detail.ownerName);
					$("#ownerEmail").text(detail.ownerEmail);
					$("#ownerPhone").text(detail.ownerPhone);
					$("#insight").text(detail.insight);
					$("#appName").text(detail.appName);
					$("#nodeAlias").text(detail.nodeAlias);
					$("#nodeIp").text(detail.nodeIp);
					//$("#details").slideDown();
					$("#contentDetail").show();
				},
				error: function(data) {
					alert('ajax获取异常详细信息出错。')
					return ;
				}
			});
			
		},
		
		loadComplete: function(data) {
			var caption = data.from + ' - ' + data.to + ' （双击查看详细信息）';
			//grid.jqGrid('setCaption', caption);
		}
	
	});
	
	// 设置为tab宽度
	var tabWidth = $("#result").width();
	grid.setGridWidth(tabWidth*0.94);
	
	var showErrorMsg = function(id,type,message){
		addMessageInfo(type,message);
		var messageType = ".message."+type+"";
		$(messageType).hide();
		$("#"+id).children(messageType).show();
	}
</script>

</body>
</html>
