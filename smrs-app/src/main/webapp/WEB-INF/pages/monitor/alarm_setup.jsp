<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设置告警阀值</title>

<script>
$(document).ready(function(){
	var grid = $("#list1");
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		hideOrShowRedAndGreen('');
		var appId = $("select[name='appSel']").find('option:selected').val();
		var serviceName = $("#svcApi").val();
		var url = 'getServiceAlarm.action?appId=' + appId + '&serviceName=' + serviceName;
		grid.jqGrid('clearGridData');	// 先清空表格数据，为的是将pager重置。
		grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
	});
	
	/**
	 * 保存按钮
	 */
	$("#saveBtn").click(function(){
		hideOrShowRedAndGreen('');
		// 获取选中行的service id
		var selectedRows = grid.jqGrid('getGridParam', 'selarrrow');
		var len = selectedRows.length;
		if(len == 0) {
			hideOrShowRedAndGreen('red');
			addMessageInfo("red",'请勾选需要设置的service！');
			return ;
		}
		// 获取 新阀值
		var alertMax = $("#alertMax").val();
		if(alertMax == '') {
			hideOrShowRedAndGreen('red');
			addMessageInfo("red",'请设置新阀值！');
			return ;
		}
		
		alertMax = $.trim(alertMax);	//去空格
		
		if(isNaN(alertMax) == true) {
			hideOrShowRedAndGreen('red');
			addMessageInfo("red",'有无效字符（非数字），请重新输入！');
			return ;
		}
		var serviceIds = [];
		for (var i = 0; i < len; i++) {
			var row = grid.jqGrid('getRowData', selectedRows[i]);
			serviceIds.push(row.id);
		}
		// ajax提交数据到后台进行更新
		var url = 'saveServiceAlarm.action?alertMax=' + alertMax + '&serviceIds=' + serviceIds;
		$.ajax({
			url: url,
			type: 'GET',
			success: function(){
				// 更新表格
				grid.trigger('reloadGrid');
				hideOrShowRedAndGreen('green');
				addMessageInfo("green",'保存成功！');
				return;
			},
			error: function(){
				hideOrShowRedAndGreen('red');
				addMessageInfo("red",'保存过程出错！');
				return;
			}
		});
		
	});
	
});

var hideOrShowRedAndGreen = function(cls){
	$(".message.red").hide();
	$(".message.green").hide();
	if(cls != ''){
		$(".message."+cls).show();
	}
}
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div><div><ul>
			<li class="first"></li>
			<li><a href="#">监控中心</a></li>
			<li><a href="#">基础数据维护</a></li>
			<li class="last"><a href="#">设定告警阀值</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面设置告警阀值。</span>
		</div>
	</div>
	<!-- 查询条件 -->
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
							<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
							请输入service：
							<input type='text' id='svcApi' />
							<button type="button" id="searchBtn" class="orange"><span>查询</span></button>
							请输入新阀值：
							<input type='text' id='alertMax' placeholder="xxxxx"/>
							<button type="button" id="saveBtn" class="blue"><span>保存</span></button>
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
		colNames: ['id', 'service名称', '阀值'],
		colModel: [
				   {name: 'id', index: 'id', align:'center', hidden: true},
		           {name: 'serviceApiName', index: 'serviceApiName'},
		           {name: 'alertMax', index: 'alertMax', align:'center', width: '25'},
		           ],
		pager: '#pager1',
		height: '221',
		rownumbers: true,
		multiselect: true,
		jsonReader: {
			root:"pager.records",
			total: "total",
			page: "page",
			records: "records",
			repeatitems: false
		}
		
	});
	
	var w = $("#cretial").width();
	grid.setGridWidth(w*0.93);
	
</script>


</body>
</html>
