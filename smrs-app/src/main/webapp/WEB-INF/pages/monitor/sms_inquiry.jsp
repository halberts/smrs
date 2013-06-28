<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>短信监控</title>
	
<style>
/* 设置jqgrid表格字体 */
.ui-jqgrid {
    font-size: 12px;
    position: relative;
}
</style>

<script>
$(document).ready(function(){
	var grid = $("#list1");
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		var appName = $("select[name='appSel']").find('option:selected').val();
		if(appName == 'ALL') {
			appName = '';
		}

		var sd = $("#sdate").datepicker('getDate');
		var ed = $("#edate").datepicker('getDate');
		var diff = ed - sd;
		var days = diff / (1000 * 60 * 60 * 24);
		
		if(days <= 0) {
			addMessageInfo("red","结束日期必须大于起始日期。");
			return ;
		} else {
			var from = $("#sdate").val();
			var to = $("#edate").val();
			var url = 'searchSms.action?appName=' + appName + '&from=' + from + '&to=' + to;
			
			grid.jqGrid('clearGridData');
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
			<li><a href="#">消息监控</a></li>
			<li class="last"><a href="#">短信监控</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面查看指定时间段内的短信情况。</span>
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
							<s:select name='appSel' list='appLists' listKey='appName' listValue='appName'></s:select>
							时间：
							<sj:datepicker id="sdate" displayFormat="yy-mm-dd" showOn="focus"/> 
							到
							<sj:datepicker id="edate" displayFormat="yy-mm-dd" showOn="focus"/> 
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
			</div>
		</div>
	</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1");
	grid.jqGrid({
		colNames: ['系统名称', '收信人号码', '短信内容', '发送时间', '消息唯一标识码', '短信服务器返回码'],
		colModel: [
				   {name: 'system', index: 'system', align:'center', width: '90'},
		           {name: 'phoneNum', index: 'phoneNum', align:'center', width: '90'},
		           {name: 'content', index: 'content', align:'center'},
		           {name: 'sendTime', index: 'sendTime', align:'center', formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}},
		           {name: 'msgCode', index: 'msgCode', align:'center', width: '90'},
		           {name: 'returnCode', index: 'returnCode', align:'center', width: '100'}
		           ],
		pager: '#pager1',
		height: '221',
		rownumbers: true,
		viewrecords: true,
		jsonReader: {
			root:"pager.records",
			total: "total",
			page: "page",
			records: "records",
			repeatitems: false
		}
		
	});
	
	var w = $("#right").width();
	grid.setGridWidth(w*0.94);
</script>


</body>
</html>
