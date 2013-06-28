<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>API 调用查询</title>
<!--[if IE]>
<style type="text/css">
	#wrapper #container #right .section {
		display : block;
	}
</style>
<![endif]-->
<script>
$(document).ready(function(){
	
	//$('#tabs').tabs();
	
	var grid = $("#list1");
	$('#content').hide();      //隐藏列表
	
	/**
	 * tab1-查询按钮
	 */
	$("#searchBtn1").click(function(){
		hideOrShowRedAndGreen('');//隐藏错误和成功提示信息
		$('#content').show();      //显示列表
		var appId = $("select[name='appSel']").find('option:selected').val();
		var time = $("select[name='weekAndMonth']").find('option:selected').val();
		var serviceName = $("#svcApi").val();
		var url = 'getApiCall.action?appId=' + appId + '&time=' + time + '&serviceName=' + serviceName;
		grid.jqGrid('clearGridData');	//清空表格，使pager重置
		grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
	});
	
	/**
	 * tab2-查询按钮
	 */
	$("#searchBtn2").click(function(){
		hideOrShowRedAndGreen('');//隐藏错误和成功提示信息
		var appId = $("select[name='appSel2']").find('option:selected').val();
		var sdate = $("#sdate").datepicker("getDate");
		var edate = $("#edate").datepicker("getDate");
		var serviceName = $("#svcApi2").val();
		var diff = edate - sdate;
		var diff = edate - sdate;	// 相差的毫秒数
		var days = diff / (1000*60*60*24);	// 相差的天数
		if(days <= 0) {
			hideOrShowRedAndGreen('red');
			addMessageInfo("red","结束日期必须大于起始日期。");
			return ;
		} else if(days > 30) {
			hideOrShowRedAndGreen('red');
			addMessageInfo("red","日期跨度不得超过30天。");
			return ;
		} else {
			$('#content').show();      //显示列表
			var from = $("#sdate").val();
			var to = $("#edate").val();
			var url = 'getApiCall.action?appId=' + appId + '&from=' + from + '&to=' + to + '&serviceName=' + serviceName;
			grid.jqGrid('clearGridData');	//清空表格，使pager重置
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
			<li class="last"><a href="#">API频次查询</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="box">
			<div class="title">
				API频次查询
				<span class="hide"></span>
			</div>
			<div class="content nopadding">
				<div class="tabs">
					<div class="tabmenu">
						<ul> 
							<li><a href="#tabs-1" onclick="$('#content').hide();">快速查询</a></li> 
							<li><a href="#tabs-2" onclick="$('#content').hide();">按时间段查询</a></li> 
						</ul>
					</div>
					<div id="tabs-1" class="tab">
						<div class="message blue">
							<span><b>说明：</b>本页面可快速查看本周和本月，某应用系统API调用频次和平均消耗时间。</span>
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										请选择时间：
										<s:select name="weekAndMonth" list="#{'week':'本周','month':'本月'}" />
										请选择业务系统：
										<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
										请输入service：
										<input type='text' id='svcApi' />
										<button type="button" id="searchBtn1" class="orange"><span>查询</span></button>
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-2" class="tab">
						<div class="message blue">
							<span><b>说明：</b>本页面可查看一个月内任意时间段，某应用系统API调用频次和平均消耗时间。</span>
						</div>
						<br>
						<jsp:include page="/common/messages.jsp" />
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name='appSel2' list='appLists' listKey='id' listValue='appName'></s:select>
										时间：
										<sj:datepicker id="sdate" displayFormat="yy-mm-dd" showOn="focus"/> 
										到
										<sj:datepicker id="edate" displayFormat="yy-mm-dd" showOn="focus"/> 
										请输入service：
										<input type='text' id='svcApi2' />
										<button type="button" id="searchBtn2" class="orange"><span>查询</span></button>
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
				查询结果
				<span class="hide"></span>
			</div>
			<div class="content" id="content">
				<table id='list1'></table>
				<div id='pager1'></div>
			</div>
		</div>
	</div>


<!-- 生成分页表格 -->
<script type='text/javascript'>
		var grid = $("#list1");
		grid.jqGrid({
		colNames: ['Service名称', '阀值', '响应级别', '调用次数', '平均消耗时间（毫秒）'],
		colModel: [
		           {name: 'serviceName', index: 'serviceName'},
		           {name: 'alertMax', index: 'alertMax', align:'center', width: '10'},
		           {name: 'overtimeLv', index: 'overtimeLv', align:'center', width: '10'},
		           {name: 'callNum', index: 'callNum', align:'center', width: '15'},
		           {name: 'averageTime', index: 'averageTime', align:'center', width: '20', formatter: 'number', formatoptions: {decimalPlaces: 2}},
		           ],
		pager: '#pager1',
		height: '221',
		rownumbers: true,
		viewrecords: true,
		jsonReader: {
			root: 'pager.records',
			repeatitems: false,
			total: "total",
			page: "page",
			records: "records"
		}
		
	});
	
	// 设置为tab宽度
	var tabWidth = $("#result").width();
	grid.setGridWidth(tabWidth*0.94);
	
</script>
</body>
</html>