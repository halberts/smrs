<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>邮件监控</title>
<script src="${staticURL}/../scripts/artdialog/jquery.artDialog.js?skin=opera"></script>
<script>
$(document).ready(function(){
	var grid = $("#list1");
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		hideOrShowRedAndGreen('');//隐藏错误和成功提示信息
		var appName = $("select[name='appSel']").find('option:selected').val();
		if(appName == 'ALL') {
			appName = '';
		}
		var sd = $("#sdate").datepicker('getDate');
		var ed = $("#edate").datepicker('getDate');
		var diff = ed - sd;
		var days = diff / (1000 * 60 * 60 * 24);
		if(days <= 0) {
			hideOrShowRedAndGreen('red');
			addMessageInfo("red","结束日期必须大于起始日期。");
			return ;
		} else {
			var from = $("#sdate").val();
			var to = $("#edate").val();
			var url = 'searchEmails.action?appName=' + appName + '&from=' + from + '&to=' + to;
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
			<li class="last"><a href="#">邮件监控</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面查看指定时间段内的邮件情况。</span>
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
				查询结果（双击查看详细内容）
				<span class="hide"></span>
			</div>
			<div class="content">
				<table id='list1'></table>
				<div id='pager1'></div>
			</div>
		</div>
	</div>
	<div id='emailContent' style='display:none;'>
		<pre id='emailCtn'></pre>
	</div> 
<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1");
	grid.jqGrid({
		colNames: ['系统名称', '主题', '错误信息', '发送时间', '内容'],
		colModel: [
				   {name: 'system', index: 'system', align:'center'},
		           {name: 'subject', index: 'subject', align:'center'},
		           {name: 'error', index: 'error', align:'center'},
		           {name: 'sendTime', index: 'sendTime', align:'center', formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}},
		           {name: 'content', index: 'content', align: 'center', hidden: true},
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
		},
		
		ondblClickRow: function(rowid, iRow, iCol, e) {
			var ct = grid.jqGrid('getRowData', rowid).content;
			$("#emailCtn").text(ct);
			art.dialog({
				title: '邮件详细内容',
				content: document.getElementById('emailContent'),
				lock: true,
				width: '70%',
				close: function() {
					$("#emailCtn").empty();
				}
			}); 
		}
		
	});
	
	var w = $("#right").width();
	grid.setGridWidth(w*0.94);
</script>


</body>
</html>
