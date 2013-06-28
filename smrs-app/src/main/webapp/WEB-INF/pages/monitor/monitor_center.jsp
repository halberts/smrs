<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>监控中心</title>
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.multiselect.css" />
<script src="${staticURL}/../scripts/jquery.multiselect.min.js"></script>

<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
<script src="${staticURL}/../scripts/jquery.ui.datepicker.js"></script>

<script>

var loop;

function refreshGridDatas() {
	clearInterval(loop);	// 先停止定时器，待更新完表格后再开启（防止网络延时等问题）
	$("#apiList").trigger('reloadGrid');
	$("#exList").trigger('reloadGrid');
	loop = setInterval(refreshGridDatas, 20000);	//重新开启定时器
}

$(document).ready(function(){
	/**
	 * tab-1 查询按钮
	 */
	$("#searchBtn1").click(function(){
		$("#details").hide();
		$("#tabs-1-msg").hide();
		hideOrShowRedAndGreen('');//隐藏错误和成功提示信息
		var issueId = $("#issueId").val();
		issueId = $.trim(issueId);
		if(issueId == '') {
			showErrorMsg("tabs-1-msg","red","请输入监控Id。");
			return ;
		}
		
		if(isNaN(issueId) == true) {
			showErrorMsg("tabs-1-msg","red","请输入正确的监控Id，只允许数字。");
			return ;
		}
		
		var url = 'issueDetailIndex.action?issueId=' + issueId;
		
		$.ajax({
			url: url,
			type: 'GET',
			cache: false,
			success: function(data) {
				var detail = data.issueDetail;
				if(detail == null || detail == ''){
					showErrorMsg("tabs-1-msg","red","未找到相关数据。");
					return;
				}
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
				$("#details").slideDown();
			},
			error: function(data) {
				showErrorMsg("tabs-1-msg","red","ajax获取异常详细信息出错。");
				return ;
			}
		});
	});
	/**
	 * tab2-查询按钮
	 */
	$("#searchBtn2").click(function(){
		var appId = $("select[name='appSel']").find('option:selected').val();
		var serviceName = $("#svcApi2").val();
		$("#list1").jqGrid('clearGridData');	//清空表格，为了pager重置
		var url = 'serviceInquiryIndex.action?appId=' + appId + '&from=1m&serviceName=' + serviceName;;
		$("#list1").jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
	});
	
	/**
	 * tab3-查询按钮
	 */
	$("#searchBtn3").click(function(){
		$("#tabs-3-msg").hide();
		var extype = $("#extype").val();
		if(extype == null) {
			showErrorMsg("tabs-3-msg","red","请至少选择一种要查看的异常类型。");
			return ;
		}
		clearInterval(loop);
		var appId = $("select[name='appSel3']").find('option:selected').val();
		var url = 'realTimeApi.action?appId=' + appId;
		var url2 = 'realTimeEx.action?appId=' + appId + '&extype=' + extype;
		$("#apiList").jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		$("#exList").jqGrid('setGridParam', {url: url2}).trigger('reloadGrid');
		loop = setInterval(refreshGridDatas, 20000);	//开启定时器（20秒一次）
	});
 	
 	$("#tabs-3-result").hide();
	$("#result").hide();
});
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div><div><ul>
			<li class="first"></li>
			<li><a href="#">监控中心</a></li>
			<li><a href="#">应用系统监控</a></li>
			<li class="last"><a href="#">监控中心</a></li>
		</ul></div></div>
	</div>
	<div class="section" id="cretial">
		<div class="box">
			<div class="title">
				监控中心
				<span class="hide"></span>
			</div>
			<div class="content nopadding">
				<div class="tabs">
					<div class="tabmenu">
						<ul> 
							<li><a href="#tabs-1" onclick="tabOnclick('tabs-1');">精确查询</a></li>
							<li><a href="#tabs-2" onclick="tabOnclick('tabs-2');">按service查询</a></li>
							<li><a href="#tabs-3" onclick="tabOnclick('tabs-3');">实时监控</a></li>
						</ul>
					</div>
					<div id="tabs-1" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面通过监控ID来查找异常信息。</span>
						</div>
						<div id="tabs-1-msg"  style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-1-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										监控ID：
										<input type='text' id='issueId' />
										<button type="button" id="searchBtn1" class="orange"><span>查询</span></button>
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-2" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面查找本月内异常信息。</span>
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
										service名：
										<input type='text' id='svcApi2' />
										<button type="button" id="searchBtn2" class="orange"><span>查询</span></button>
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-3" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面实时监控异常和serviceApi情况。（查看后每20秒将自动刷新）</span>
						</div>
						<div id="tabs-3-msg" style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-3-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name='appSel3' list='appLists' listKey='id' listValue='appName'></s:select>
										<select id='extype'>
											<option value='1,2,3'>查看所有</option>
											<option value='1'>已知异常</option>
											<option value='2'>未知异常</option>
											<option value='3'>超时异常</option>
										</select>
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
			<div class="title">
				本月查询结果（双击查看详细信息）
				<span class="hide"></span>
			</div>
			<div class="content">
				<table id='list1'></table>
				<div id='pager1'></div>
			</div>
		</div>
	</div>
	<br>
	<div class="section" id="tabs-3-result">
		<div class="box">
			<div class="title">
				异常实时监控
				<span class="hide"></span>
			</div>
			<div class="content">
				<table id='exList' style='float:left'></table>	
				<div id='exPager'></div>
			</div>
		</div>
		<div class="box">
			<div class="title">
				ServiceApi实时监控
				<span class="hide"></span>
			</div>
			<div class="content">
				<table id='apiList'></table>
				<div id='apiPager'></div>
			</div>
		</div>
	</div>
	<div class="section" id="details">
		<div class="box">
			<div class="title">
				详情
				<span class="hide"></span>
			</div>
			<div class="content">
				<table border='1'>
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
		           {name: 'issueId', index: 'issueId', align:'center', width: '25'},
		           {name: 'issueType', index: 'issueType', align:'center', width: '35'},
		           {name: 'exceptionName', index: 'exceptionName', align:'center', width: '60'},
		           {name: 'serviceName', index: 'serviceName', align:'center'},
		           {name: 'nodeAlias', index: 'nodeAlias', align:'center', width: '55'},
		           {name: 'createTime', index: 'createTime', align:'center', width: '60'}
		           ],
		pager: '#pager1',
		height: '221',
		autowidth: true,
		rownumbers: true,
		viewrecords: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false,
			total: 'total',
			page: 'page',
			records: 'records'
		},
		
		ondblClickRow: function(rowid, iRow, iCol, e) {
			var issueId = grid.jqGrid('getRowData', rowid).issueId;
			var url = 'issueDetailIndex.action?issueId=' + issueId;
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
					$("#details").slideDown();
				},
				error: function(data) {
					alert('ajax获取异常详细信息出错。');
					return ;
				}
			});
		}
	});
		
	// 设置为tab宽度
	var tabWidth = $("#cretial").width();
	grid.setGridWidth(tabWidth*0.96);
	
	
	
	$("#exList").jqGrid({
		colNames: ['监控ID', '异常名称', 'Service名', '异常发生的节点', '发生时间', '异常类型'],
		colModel: [
		           {name: 'issueId', index: 'issueId', align:'center', hidden:true},
		           {name: 'exceptionName', index: 'exceptionName', align:'center', hidden:true},
		           {name: 'serviceName', index: 'serviceName', align:'left', width: '115'},
		           {name: 'nodeAlias', index: 'nodeAlias', align:'center', hidden:true},
		           {name: 'createTime', index: 'createTime', align:'center', width: '35'},
		           {name: 'issueType', index: 'issueType', align:'center', width: '20'}
		          ],
        rownumbers: true,
        height: '560',
  		jsonReader: {
  			root: 'pager.records',
  			repeatitems: false
  		},
  		rowNum: [25]
  		
	}).setGridWidth(tabWidth*0.96);
	
	
	$("#apiList").jqGrid({
		colNames: ['Service名', '调用次数', '平均消耗时间', '最后执行时间'],
		colModel: [
		           {name: 'serviceName', index: 'serviceName', align:'left'},
		           {name: 'callNum', index: 'callNum', align:'center', width: '15'},
		           {name: 'averageTime', index: 'averageTime', align:'center', hidden:true},
		           {name: 'lastExecTime', index: 'lastExecTime', align:'center', width: '40'},
		          ],
        rownumbers: true,
        height: '560',
  		jsonReader: {
  			root: 'apiPager.records',
  			repeatitems: false
  		},
  		rowNum: [25]
	}).setGridWidth(tabWidth*0.96);
	
	var showErrorMsg = function(id,type,message){
		addMessageInfo(type,message);
		var messageType = ".message."+type+"";
		$(messageType).hide();
		$("#"+id).show();
		$("#"+id).children(messageType).show();
	}
	
	var tabOnclick = function(tabId){
		if(tabId == 'tabs-1'){
			$("#tabs-3-result").hide();
			$("#result").hide();
			$("#details").show();
		}else if(tabId == 'tabs-2'){
			$("#result").show();
			$("#tabs-3-result").hide();
			$("#details").hide();
		}else if(tabId == 'tabs-3'){
			$("#result").hide();
			$("#details").hide();
			$("#tabs-3-result").show();
		}
	}
	
</script>
</body>
</html>
