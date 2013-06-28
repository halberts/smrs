<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设置响应级别</title>
<script>
$(document).ready(function(){
	var grid = $("#list1");
	$("#searchBtn").click(function(){
		var appId = $("select[name='appSel']").find('option:selected').val();
		var serviceName = $("#svcApi").val();
		var url = 'getServiceLevel.action?appId=' + appId + '&serviceName=' + serviceName;
		grid.jqGrid('clearGridData');	// 先清空表格数据，为的是将pager重置。
		grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
	});
	
	
	/**
	 * 保存按钮
	 */
	$("#saveBtn").click(function(){
		$('.message.red').hide();
		$('.message.green').hide();
		// 获取选中行的service id
		var selectedRows = grid.jqGrid('getGridParam', 'selarrrow');
		var serviceIds = [];
		
		var len = selectedRows.length;
		if(len == 0) {
			addMessageInfo("red","请勾选需要设置的API！");
			return ;
		}
		
		for (var i = 0; i < len; i++) {
			var row = grid.jqGrid('getRowData', selectedRows[i]);
			serviceIds.push(row.id);
		}
		
		// 获取level值
		var level = $("select[name='lvSel']").find('option:selected').val();
		// ajax提交数据到后台进行更新
		var url = 'saveServiceLevel.action?level=' + level + '&serviceIds=' + serviceIds;
		$.ajax({
			url: url,
			type: 'GET',
			cache: false,
			success: function(){
				// 更新表格
				grid.trigger('reloadGrid');
				addMessageInfo("green","保存成功！");
				return;
			},
			
			error: function(){
				addMessageInfo("red","保存过程出现错误！");
				return;
			}
			
		});
		
	});
	
	/**
	 * 最大化时重新设定表格宽度
	 */
 	$("#clickDiv").click(function(){
 		var w = $("#cretial").width();
 		$("#list1").setGridWidth(w*0.8);
	}); 
});


</script>
</head>

<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">监控中心</a></li>
		<li><a href="#">基础数据维护 </a></li>
		<li class="last"><a href="#">设定响应级别</a></li>
	</ul></div></div>
</div>
<div class="section">
	<div class="message blue">
		<span><b>说明：</b>本页面设置各业务系统API的响应级别。勾选一个活多个API，点击‘批量调整’按钮保存修改。</span>
	</div>
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			选择查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:select name="appSel" list="appLists" listKey="id" listValue="appName"></s:select>
		<span>&nbsp;&nbsp;API名：</span>
		<input type='text' id='svcApi' placeholder="根据API名称模糊查询"/>
		<button class="orange"  id="searchBtn">
			<span>查询</span>
		</button>
		
		请选择响应级别：
	    <s:select name='lvSel' list='timeoutLevelList' listKey='lvName' listValue='lvName' ></s:select>
		<button class="blue"  id="saveBtn">
			<span>批量调整</span>
		</button>
		</div>
	</div>
</div>
<div class="section" id='cretial'>
	<div class="three-quarter">
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
	<div class="quarter">
		<div class="box">
			<div class="title">
				API响应级别设置参考标准
				<span class="hide"></span>
			</div>
			<div class="content">
				<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div>
					<table class="sorting">
						<thead>
						<tr>
							<th>响应级别</th>
							<th>响应时间</th>
						</tr>
						</thead>
						<tbody>
						<s:iterator value="timeoutLevelList" id="lv">
							<tr>
								<td><s:property value="#lv.lvName"/></td>
								<td><s:property value="#lv.otTime"/></td>
							</tr>
						</s:iterator>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1"); 
	grid.jqGrid({
		colNames: ['id', 'API名称', '响应级别'],
		colModel: [
				   {name: 'id', index: 'id', align:'center', hidden: true},
		           {name: 'serviceApiName', index: 'serviceApiName'},
		           {name: 'overtimeLv', index: 'overtimeLv', align:'center', width: '25'},
		           ],
		/* caption: '查询结果', */
		multiselect: true,
		height: '221',
		pager: '#pager1',
		rownumbers: true,
		jsonReader: {
			root:"pager.records",
			total: "total",
			page: "page",
			records: "records",
			repeatitems: false
		}
		
	});
	
	var w = $("#cretial").width();
	grid.setGridWidth(w*0.7);
	
</script>

</body>
</html>
