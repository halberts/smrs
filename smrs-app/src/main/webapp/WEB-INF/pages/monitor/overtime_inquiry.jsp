<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>超时查询</title>
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
	var grid2 = $("#list2");
	
	// 设置为tab宽度
	var tabWidth = $("#result").width();
	grid.setGridWidth(tabWidth*0.94);
	grid2.setGridWidth(tabWidth*0.94);
	
	/**
	 * tab1-查询按钮
	 */
	$("#searchBtn1").click(function(){
		$("#list1ctn").show();
		$("#list2ctn").hide();
		$("#details").hide();
		var appId = $("select[name='appSel']").find('option:selected').val();
		var time = $("select[name='weekAndMonth']").find('option:selected').val();
		var serviceName = $("#svcApi").val();
		var url = 'overtimeTopTen.action?appId=' + appId + '&time=' + time + '&serviceName=' + serviceName;
		grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
	});

	/**
	 * tab2-查询按钮
	 */
	$("#searchBtn2").click(function(){
		$("#list1ctn").show();
		$("#list2ctn").hide();
		$("#details").hide();
		$("#tabs-2-msg").hide();
		var appId = $("select[name='appSel2']").find('option:selected').val();
		var serviceName = $("#svcApi2").val();
		var sdate = $("#sdate").datepicker("getDate");
		var edate = $("#edate").datepicker("getDate");
		var diff = edate - sdate;	// 相差的毫秒数
		var days = diff / (1000*60*60*24);	// 相差的天数
		if(days <= 0) {
			showErrorMsg('tabs-2-msg',"red","结束日期必须大于起始日期。");
			return ;
		} else if(days > 30) {
			showErrorMsg('tabs-2-msg',"red","日期跨度不得超过30天。");
			return ;
		} else {
			var from = $("#sdate").val();
			var to = $("#edate").val();
			var url = 'overtimeTopTen.action?appId=' + appId + '&from=' + from + '&to=' + to + '&serviceName=' + serviceName;;
			grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		}
		
	});
	
	/**
	 * tab3-查询按钮
	 */
	$("#searchBtn3").click(function(){
		$("#list1ctn").hide();
		$("#list2ctn").show();
		$("#details").hide();
		$("#tabs-3-msg").hide();
		var appId = $("select[name='appSel3']").find('option:selected').val();
		var serviceName = $("#svcApi3").val();
		if(serviceName == '') {
			showErrorMsg('tabs-3-msg',"red","请输入service名。");
			return ;
		}
		var url = 'overtimeTopTen.action?appId=' + appId + '&from=3m&serviceName=' + serviceName;;
		grid2.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
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
			<li class="last"><a href="#">超时查询</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="box">
			<div class="title">
				超时查询
				<span class="hide"></span>
			</div>
			<div class="content nopadding">
				<div class="tabs">
					<div class="tabmenu">
						<ul> 
							<li><a href="#tabs-1">快速查询</a></li> 
							<li><a href="#tabs-2">按时间段查询</a></li>
							<li><a href="#tabs-3">按service查询</a></li>  
						</ul>
					</div>
					<div id="tabs-1" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面可快速查询某系统超时前10位。</span>
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name="weekAndMonth" list="#{'2h':'近2小时','6h':'近6小时','12h':'近12小时','1d':'当天','3d':'近3天','week':'本周','month':'本月'}" />
										<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
										service：
										<input type='text' id='svcApi' />
										<button type="button" id="searchBtn1" class="orange"><span>查询</span></button>
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-2" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面可查找一个月内任意时间段某系统超时前10位。</span>
						</div>
						<div id="tabs-2-msg"  style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-2-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name='appSel2' list='appLists' listKey='id' listValue='appName'></s:select>
										时间：
										<sj:datepicker id="sdate" displayFormat="yy-mm-dd" showOn="focus"/> 
										到
										<sj:datepicker id="edate" displayFormat="yy-mm-dd" showOn="focus"/> 
										service：
										<input type='text' id='svcApi2' />
										<button type="button" id="searchBtn2" class="orange"><span>查询</span></button>
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-3" class="tab">
						<div class="message blue">
							<span><b>说明：</b>本页面可按service查询近三个月的超时情况。</span>
						</div>
						<div id="tabs-3-msg"  style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-3-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										请选择业务系统：
										<s:select name='appSel3' list='appLists' listKey='id' listValue='appName'></s:select>
										请输入service：
										<input type='text' id='svcApi3' />
										<button type="button" id="searchBtn3" class="orange"><span>查询</span></button>
									</th>
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
			<div id="list1ctn" class="content">
				<table id='list1'></table>
			</div>
			<div id='list2ctn' class="content">
				<table id='list2'></table>
				<div id='pager2'></div>
			</div>
		</div>
	</div>
	<div id='details' class="section">
		<div class="box">
			<div class="title">
				详细信息
				<span class="hide"></span>
			</div>
			<div class="content">
				<table border="1">
					<tr>
						<td>监控ID</td>
						<td><span id='issueID'></span></td>
						
						<td>Action名称</td>
						<td colspan='3'><span id='actionName'></span></td>
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
						<td colspan='5'><span id='inValue'></span></td>
					</tr>
					
					<tr>
						<td>异常详细信息</td>
						<td colspan='5'><span id='serviceEx'></span></td>
					</tr>
					
					<tr>
						<td>SQL栈信息</td>
						<td colspan='5'><span id='insight'></span> </td>
					</tr>
				</table>
			</div>
		</div>
	</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1");
	grid.jqGrid({
		colNames: ['监控ID', 'Service名', '发生节点', '执行时间（毫秒）', '发生时间'],
		colModel: [
		           {name: 'issueId', index: 'issueId', align:'center', width: '30'},
		           {name: 'serviceName', index: 'serviceName'},
		           {name: 'nodeName', index: 'nodeName', align:'center', width: '40'},
		           {name: 'executeTime', index: 'executeTime', align:'center', width: '40'},
		           {name: 'createTime', index: 'createTime', align:'center', width: '60'}
		           ],
		height: '221',
		rownumbers: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false
		},
		
		ondblClickRow: function(rowid, iRow, iCol, e) {
			var issueId = grid.jqGrid('getRowData', rowid).issueId;
			var url = 'issueDetailInfo.action?issueId=' + issueId;
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
					
					$("#details").slideDown();
				},
				error: function(data) {
					alert('ajax获取异常详细信息出错。')
					return ;
				}
			});
			
		},
		
		loadComplete: function(data) {
			var caption = data.from + ' - ' + data.to + ' （双击查看详细信息）';
			//$("#resultTitle").html(caption + "<span class='hide'></span>");
			//grid.jqGrid('setCaption', caption);
		}
		
	});
 	
	var grid2 = $("#list2");
	grid2.jqGrid({
		colNames: ['监控ID', 'Service名', '响应级别', '执行时间（毫秒）', '发生时间'],
		colModel: [
		           {name: 'issueId', index: 'issueId', align:'center', width: '40'},
		           {name: 'serviceName', index: 'serviceName'},
		           {name: 'nodeName', index: 'nodeName', align:'center', width: '40'},
		           {name: 'executeTime', index: 'executeTime', align:'center', width: '40'},
		           {name: 'createTime', index: 'createTime', align:'center', width: '40'}
		           ],
		height: '221',
		pager: '#pager2',
		rownumbers: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false,
			total: "total",
			page: "page",
			records: "records"
		},
		
		ondblClickRow: function(rowid, iRow, iCol, e) {
			var issueId = grid2.jqGrid('getRowData', rowid).issueId;
			var url = 'issueDetailInfo.action?issueId=' + issueId;
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
					
					$("#details").slideDown();
				},
				error: function(data) {
					alert('ajax获取异常详细信息出错。')
					return ;
				}
			});
			
		},
		
		loadComplete: function(data) {
			var caption = data.from + ' - ' + data.to + ' （双击查看详细信息）';
			//grid2.jqGrid('setCaption', caption);
		}
		
	});
	
	$("#list1ctn").show();
	$("#list2ctn").hide();
	$("#details").hide();
	
	var showErrorMsg = function(id,type,message){
		addMessageInfo(type,message);
		var messageType = ".message."+type+"";
		$(messageType).hide();
		$("#"+id).show();
		$("#"+id).children(messageType).show();
	}
	
</script>

</body>
</html>
