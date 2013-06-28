<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务分组</title>
<script src="${staticURL}/../scripts/artdialog/jquery.artDialog.js?skin=opera"></script>
<style>
	/* 设置jqgrid表格字体 */
	.ui-jqgrid {
	    font-size: 12px;
	    position: relative;
	}
</style>

<script>
var groupId;	// 全局变量
var artdlg;
var serviceList;

function setupGroup(rowid) {
	var rowdata = grid.jqGrid('getRowData', rowid);
	groupId = rowdata.id;
	
	var appId = rowdata.appId, groupNm = rowdata.serviceGrpNm;
	
	var url = 'businessServiceList.action?appId=' + appId;
	var appNm = $("select[name='appSel']").find('option:selected').text();
	
	var grid2 = $("#list2");
	grid2.jqGrid('setGridParam', {url: url}).trigger('reloadGrid').jqGrid('setCaption', appNm + ', ' + groupNm);
	grid2.jqGrid('setGridWidth', '700');
	//$("#checkList").width(700);
	
	// 初始化结果列表
	art.dialog({
		title: '添加service到分组',
		lock: true,
		height: '40%',
		content: document.getElementById('svcSetup'),
		close: function() {	// 关闭前的执行函数
			//$("#checkList ul.selectlist-list").empty();
		}
	}); 
}

$(document).ready(function(){
	var grid = $("#list1");
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		$("#dbdiv").hide();
		
		var appId = $("select[name='appSel']").find('option:selected').val();
		var appnm = $("select[name='appSel']").find('option:selected').text();
		var url = 'serviceGrpList.action?appId=' + appId;
		grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
	});
	
	/**
	 * 保存按钮
	 */
	$("#submitBtn").click(function(){
		hideOrShowRedAndGreen('');
		var grid2 = $("#list2");
		var ids = grid2.jqGrid('getGridParam', 'selarrrow');
		var len = ids.length;
		if(len == 0){
			hideOrShowRedAndGreen('red');
			addMessageInfo("red",'请选择要保存的service。');
			return;
		}
		var serviceIds = [];
		for(var i = 0; i < len; i++) {
			var rowid = ids[i];
			var rowdata = grid2.jqGrid('getRowData', rowid); 
			serviceIds.push(rowdata.id);
		}
		var url = 'updateGroupService.action?serviceIds=' + serviceIds + '&groupId=' + groupId;
		$.ajax({
			url: url,
			type: 'GET',
			cache: false,
			success: function() {
				hideOrShowRedAndGreen('green');
				addMessageInfo("green",'保存成功。');
				location.reload();
			},
			error: function() {
				hideOrShowRedAndGreen('red');
				addMessageInfo("red",'保存过程出错。');
				return ;
			}
		});
		
	});
	
	
	/**
	 * 最大化时重新设定表格宽度
	 */
 	$("#clickDiv").click(function(){
 		var w = $("#right").width();
 		grid.setGridWidth(w * 0.28);
 		
 		$("#nameTb").width(w*0.68);
	}); 
	
});
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div><div><ul>
			<li class="first"></li>
			<li><a href="#">监控中心</a></li>
			<li><a href="#">基础数据维护</a></li>
			<li class="last"><a href="#">业务分组</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面增删查改业务分组的service信息。</span>
		</div>
	</div>
	<div class="section" id='cretial'>
		<div class="box">
			<div class="title">
				业务分组service查询：
				<span class="hide"></span>
			</div>
			<div class="content">
		        <table>
		        	<tr>
						<th>
							<s:select name="appSel" list="appLists" listKey="id" listValue="appName">
							</s:select>
							<button type="button" id="searchBtn" class="blue"><span>查询</span></button>
						</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id='result' class='section'>
		<div class="quarter">
			<div class="box">
				<div class="title">
					双击某行进行编辑
					<span class="hide"></span>
				</div>
				<div class="content">
					<table id='list1'></table>
					<div id='pager1'></div>
				</div>
			</div>
		</div>
		<div id='dbdiv' class="three-quarter">
			<div class="box">
				<div class="title">
					查询结果
					<span class="hide"></span>
				</div>
				<div class="content">
					<table border='1' class="ui-widget" id='nameTb'>
						<thead class="ui-widget-header">
							<tr>
							<th><span id='tb_title'></span></th>
							</tr>
						</thead>
						<tbody class="ui-widget-content" id='tbd'>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div id='svcSetup' class="section" style='display:none'>
		<div class="box">
			<div style="margin:0 0 10px 0;">
				<jsp:include page="/common/messages.jsp" />
			</div>
			<div class="content">
				<table>
					<tr>
						<th>
							<button type="button" id="submitBtn" class="blue">
								<span>保存</span>
							</button>
						</th>
					</tr>
				</table>
			</div>
			<div class="content">
				<table id='list2'></table>
				<div id='pager2'></div>
			</div>
		</div>
	</div>
	
	<div class='section' style='display:none;'>
		<div class="box">
			<div class="content">
				<table id='list3'></table>
			</div>
		</div>
	</div>

<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1");
	grid.jqGrid({
		colNames: ['id', 'appId', '分组名称',  '备注'],
		colModel: [
				   {name: 'id', index: 'id', align:'center', hidden: true},
				   {name: 'appId', index: 'appId', align:'center', hidden: true},
				   {name: 'serviceGrpName', index: 'serviceGrpName', align:'center', editable:true, width: '200'},
				   {name: 'serviceGrpDetail', index: 'serviceGrpDetail', editable:true, edittype: 'textarea'}
		           ],
		pager: '#pager1',
		rowList: '',
		pgbuttons: false,
		pgtext: null,
		viewrecords: false,
		height: '265',
		rownumbers: true,
		jsonReader: {
			root:"svcGroupList",
			repeatitems: false
		},
		rowNum: -1,
		editurl: 'groupCrud.action',
		
		ondblClickRow: function(rowid, iRow, iCol, e) {
			grid3.jqGrid('clearGridData');
			setupGroup(rowid); 
		}, 
		
		onSelectRow: function(rowid) {
			$("#dbdiv").show();
			
			var tbd = $("#tbd").empty();
			
			var rowdata = grid.jqGrid('getRowData', rowid);
			$("#tb_title").text('属于 ' + rowdata.serviceGrpName + ' 的service');
			
			var grid3 = $("#list3");
			grid3.jqGrid('clearGridData');
			var appId = rowdata.appId;
			var groupId = rowdata.id;
			var url3 = 'serviceListOfGrp.action?appId=' + appId + '&groupId=' + groupId;
			grid3.jqGrid('setGridParam', {url: url3}).trigger('reloadGrid');
			
		},
	});
	
	grid.jqGrid('navGrid', '#pager1', {edit:true, add:true, del:true, search:false},
	
		{	// edit option
			reloadAfterSubmit: true,
 		 	onclickSubmit: function(params, posdata) {
 				var rowid = posdata.list1_id;
				var rowdata = grid.jqGrid('getRowData', rowid); 				
				var groupId = rowdata.id;
				return {groupId: groupId};
			}
			
		},
		
		{	// add option
			reloadAfterSubmit: true,
 			onclickSubmit: function(params, posdata) {
				var appId = $("select[name='appSel']").find('option:selected').val();
				return {appId: appId};
			} 
		},
		
		{	// del option
			reloadAfterSubmit: true,
			onclickSubmit: function(params, rowid) {
				var rowdata = grid.jqGrid('getRowData', rowid); 				
				var groupId = rowdata.id;
				return {groupId: groupId};
			}
		}
		
	);
	
	var w = $("#cretial").width();
	grid.setGridWidth(w * 0.21);
	
	$("#nameTb").width(w*0.68);
	$("#dbdiv").hide();
	
	var grid2 = $("#list2");
	grid2.jqGrid({
		colNames: ['id', 'service名称'],
		colModel: [
				   {name: 'id', index: 'id', align:'center', hidden: true},
		           {name: 'serviceApiName', index: 'serviceApiName'}
		           ],
		multiselect: true,
		height: '221',
		pager: '#pager2',
		jsonReader: {
			root:"pager.records",
			total: "total",
			page: "page",
			records: "records",
			repeatitems: false
		}
	});
	
	
	var grid3 = $("#list3");
	grid3.jqGrid({
		colNames: ['id', 'service名称'],
		colModel: [
				   {name: 'id', index: 'id', align:'center'},
		           {name: 'serviceApiName', index: 'serviceApiName'}
		           ],
		multiselect: true,
		height: '221',
		jsonReader: {
			root:"serviceList",
			repeatitems: false
		},
		rowNum: -1,
		loadComplete: function() {
			var ids = grid3.jqGrid('getDataIDs');
			var len = ids.length;
			//var ctn = $("#checkList ul.selectlist-list").empty();
			var tbd = $("#tbd").empty();
			for(var i = 0; i < len; i++) {
				// 设置结果列表
				var rowdata = grid3.jqGrid('getRowData', i+1);
				//addToMart(ctn, rowdata.serviceApiNm, rowdata.id);
				
				// 填充表格
				var tr = $("<tr></tr>").appendTo(tbd);
				$("<td>"+rowdata.serviceApiName+"</td>").appendTo(tr);
				
			}
		}
	});
</script>
</body>
</html>
