<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设定上线追踪</title>
	
<!--[if IE]>
<style type="text/css">
	#wrapper #container #right .section {
		display : block;
	}
</style>
<![endif]-->

<script src="${staticURL}/../scripts/artdialog/jquery.artDialog.js?skin=opera"></script>

<script>

var trackId; // 全局变量，更新时使用。
var actFlag; // 激活标志
var artdlg;	// 更新时的弹出框对象
var editRowid = -1;	// 编辑的行id
var ACTIVE_TYPE = {0: '否', 1: '是'};

/**
 * 编辑追踪记录
 */
function editTrackList(rowid) {
	editRowid = rowid;
	var grid = $("#list1");
	grid.jqGrid('clearGridData');
	$("#serviceName").val('');
	
	var grid2 = $("#list2");
	
	$("#tabs-1 .message .blue").hide();
	$(".red").hide();
	$(".green").hide();
	// 设置tab-1中选中下拉框
	var appId = $("select[name='appSel3']").find('option:selected').val();
	$("select[name='appSel']").val(appId).attr('disabled', true);	
	
	// 设置结果列表 
	var rowdata = grid2.jqGrid('getRowData', rowid);
	var serviceNames = rowdata.traceServices;
	var traceServices = serviceNames.split(',');
	var len = traceServices.length;
	var ctn = $("#groupServiceList");
	for(var i = 0; i < len; i++) {
		addToMart(ctn, traceServices[i]);
	}
	// 获取双击行的trackId和activeFlag
	trackId = rowdata.id;
	actFlag = rowdata.activeFlag;
	if(actFlag == '是') {
		$("#activeSel").val(1);	//设为是
	} else {
		$("#activeSel").val(0);	//设为否
	}
	// 设置时间段
	var tracePeriod = rowdata.tracePeriod;
	$("#tracePeriod").val(tracePeriod);
	// 改变按钮名字
	$("#submitBtn").children().text('更新');
	$("#artdlgCtn").append($("#tabs-1"));
	$("#artdlgCtn").append($("#result1"));
	$("#artdlgCtn").append($("#groupService"));
	$("#tab-4-result").show();
	artdlg = art.dialog({
		title: '编辑追踪信息',
		lock: true,
		content: document.getElementById('tab-4-result'),
		close: function() {	// 关闭前的执行函数
			grid.jqGrid('clearGridData');
			$("#tracePeriod").val('');
			$("select[name='appSel']").val(appId).attr('disabled', false);
			$("#submitBtn").children().text('提交');
			$("#activeSel").attr('disabled', true);
			$("#activeSel").val(0);	//设置选中
			$("#tabs-1").appendTo("#tabs");
			$("#result1").insertAfter("#title1");
			$("#groupService").insertAfter("#result1");
			$("#tab-4-result").hide();
			ctn.empty();
			grid2.trigger('reloadGrid');
			//resultShow('tab-3-result');
			hideOrShowRedAndGreen('');
		},
	}); 
}

$(document).ready(function(){
	var grid1 = $("#list1");
	var grid2 = $("#list2");
	var grid3 = $("#list2Vip");
	
	/**
	 * tab-1提交按钮
	 */
	$("#submitBtn").click(function(){
		$("#tabs-1-msg").hide();
		var url;
		var appId = $("select[name='appSel']").find('option:selected').val();
		var activeFlag = 0;//是否激活标识,默认是0
		
		var lis = $("#groupServiceList");	// 结果列表
		var trs = lis.find("tr");
		var len = trs.length;
		if(len == 0) {
			hideOrShowRedAndGreen('red');
			showErrorMsg('tabs-1-msg','red','请选择要添加的service。');
			return ;
		}
		
		var tracePeriod = $("#tracePeriod").val();
		tracePeriod = $.trim(tracePeriod);	// 去空格
		if(tracePeriod == '' || isNaN(tracePeriod) == true ) {
			hideOrShowRedAndGreen('red');
			showErrorMsg('tabs-1-msg','red','请正确设定追踪时间（只允许整数和小数）。');
			return ;
		}
		
		var traceServices = [];
		$.each(trs, function(){
			traceServices.push($(this).find("td").text());
		});
		
		if($(this).children().text() == '提交') {
			url = 'svReleaseTrack.action?appId=' + appId + '&tracePeriod=' + tracePeriod + '&activeFlag=' + activeFlag + '&traceServices=' + traceServices;
		} else {	// 更新
			url = 'updateReleaseTrack.action?appId=' + appId + '&tracePeriod=' + tracePeriod + '&activeFlag=' + activeFlag + '&trackId=' + trackId + '&traceServices=' + traceServices;
		}
		$.ajax({
			url: url,
			type: 'POST',
			success: function() {
				if($("#submitBtn").children().text() == '更新') {	// 更新成功
					artdlg.close();
					hideOrShowRedAndGreen('green');
					showErrorMsg('tabs-1-msg','green','更新成功。');
					$("#" + editRowid, "#list2").effect('highlight', {}, 3000);	// 高亮显示编辑行3秒
				} else {	// 提交成功
					hideOrShowRedAndGreen('green');
					showErrorMsg('tabs-1-msg','green','提交成功。');
					$("#list1").jqGrid('clearGridData');
				}
				return ;
			},
			error: function(){
				hideOrShowRedAndGreen('red');
				showErrorMsg('tabs-1-msg','red','ajax提交数据出错。');
				return ;
			}			
		});		
	});
	
	
	/**
	 * tab-1 查询按钮
	 */
	$("#searchBtn").click(function(){
		if($("#submitBtn").children().text() == '提交') {
			$("#checkList ul").empty();	// 提交时才清空，更新时不清空
		}	
		
		var appId = $("select[name='appSel']").find('option:selected').val();
		var serviceName = $("#serviceName").val();
		var url = 'newReleaseTrack.action?appId=' + appId + '&serviceName=' + serviceName;
		grid1 = $("#list1");
		grid1.jqGrid('clearGridData');
		grid1.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
	});
	
	/**
	 * tab-1添加按钮
	 */
	$("#addBtn").click(function(){
		var ids = grid1.jqGrid('getGridParam', 'selarrrow');
		var len = ids.length;
		var ctn = $("#groupServiceList");
		for(var i = 0; i < len; i++) {
			var rowid = ids[i];
			var rowdata = $("#list1").jqGrid('getRowData', rowid);
			var name = rowdata.serviceApiName;
			var id = rowdata.id;
			addToMart(ctn, name, id);
		}
		
	});
	
	/**
	 * tab-2查询按钮
	 */
	$("#searchBtn2").click(function(){
		var appId = $("select[name='appSel2']").find('option:selected').val();
		var serviceName = $("#svcApi").val();
		var url = 'newReleaseTrack.action?appId=' + appId + '&serviceName=' + serviceName;
		grid3.jqGrid('clearGridData');
		grid3.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		$("#result1").show();
	});
	
	/**
	 * tab-2设置VIP按钮
	 */
	$("#saveBtn").click(function(){
		
		// 获取选中行的service id
		var selectedRows = grid3.jqGrid('getGridParam', 'selarrrow');
		var serviceIds = [];
		var serviceNames = [];
		
		var len = selectedRows.length;
		if(len == 0) {
			hideOrShowRedAndGreen('red');
			showErrorMsg('tabs-2-msg','red','请勾选需要设置的service');
			return ;
		}
		
		for (var i = 0; i < len; i++) {
			var row = grid3.jqGrid('getRowData', selectedRows[i]);
			serviceIds.push(row.id);
			serviceNames.push(row.serviceApiNm);
		}
		var appName = $("select[name='appSel2']").find('option:selected').text();
		// ajax提交数据到后台进行更新
		var url = 'saveServiceVip.action?appName=' + appName + '&serviceNames=' + serviceNms + '&serviceIds=' + serviceIds;
		
		$.ajax({
			url: url,
			type: 'GET',
			cache: false,
			success: function(){
				// 更新表格
				grid3.trigger('reloadGrid');
				hideOrShowRedAndGreen('green');
				showErrorMsg('tabs-2-msg','green','保存成功。');
				return;
			},
			error: function(){
				hideOrShowRedAndGreen('red');
				showErrorMsg('tabs-2-msg','red','保存过程出现错误！');
				return;
			}
			
		});
	});

	/**
	 * tab-3查询按钮
	 */
	$("#searchBtn3").click(function(){
		$("#tabs-3-msg").hide();
		var appId = $("select[name='appSel3']").find('option:selected').val();
		var url = 'searchTrackList.action?appId=' + appId;
		grid2.jqGrid('clearGridData');
		grid2.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
	});
	
	/**
	 * tab-3激活按钮
	 */
	$("#activeBtn").click(function(){
		$("#tabs-3-msg").hide();
		var id = grid2.jqGrid('getGridParam', 'selrow');
		if(id == null) {
			hideOrShowRedAndGreen('red');
			showErrorMsg('tabs-3-msg','red','请选择要激活的Service。');
			return ;
		}
		var appName = $("select[name='appSel3']").find('option:selected').text();
		var trackId = grid2.jqGrid('getRowData', id).id;
		var url = 'activeReleaseTrack.action?trackId=' + trackId + '&appName=' + appName;
		grid2.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		$("#searchBtn3").trigger('click');
		hideOrShowRedAndGreen('green');
		showErrorMsg('tabs-3-msg','green','激活成功。');
	});
	
	
 	/**
	 * tab-4 激活vip按钮
	 */
	$("#activeBtnVip").click(function(){
		var appId = $("select[name='appSel4']").find('option:selected').val();
		var appName = $("select[name='appSel4']").find('option:selected').text();
		var url = 'activeVip.action?appId=' + appId + '&appName=' + appName;
		$.ajax({
			url: url,
			type: 'POST',
			cache: false,
			success: function(){
				hideOrShowRedAndGreen('green');
				showErrorMsg('tabs-4-msg','green','激活成功。');
			},
			error: function() {
				hideOrShowRedAndGreen('red');
				showErrorMsg('tabs-4-msg','red','激活过程出错，激活失败。');
				return ;
			}
			
		});
		
	});

	/**
	 * tab-4 激活lv按钮
	 */
	$("#activeBtnLv").click(function(){
		var appId = $("select[name='appSel4']").find('option:selected').val();
		var appName = $("select[name='appSel4']").find('option:selected').text();
		var url = 'activeLevel.action?appId=' + appId + '&appName=' + appName;
		$.ajax({
			url: url,
			type: 'POST',
			cache: false,
			success: function(){
				hideOrShowRedAndGreen('green');
				showErrorMsg('tabs-4-msg','green','激活成功。');
			},
			error: function() {
				hideOrShowRedAndGreen('red');
				showErrorMsg('tabs-4-msg','red','激活过程出错，激活失败。');
				return ;
			}
		});
		
	});
	
	//第一次只显示tab1的result
 	resultShow("tab-1-result");
	
});
</script>
</head>
<body id="body">
	<div id="breadcrumbs">
		<div><div><ul>
			<li class="first"></li>
			<li><a href="#">监控中心</a></li>
			<li><a href="#">基础数据维护</a></li>
			<li class="last"><a href="#">设定上线追踪</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<div class="box">
			<div class="title">
				设定上线追踪
				<span class="hide"></span>
			</div>
			<div class="content nopadding">
				<div class="tabs" id="tabs">
					<div class="tabmenu">
						<ul> 
							<li><a href="#tabs-1" onclick="resultShow('tab-1-result');">新增上线追踪</a></li> 
							<li><a href="#tabs-2" onclick="resultShow('tab-2-result');">VIP设置</a></li>
							<li><a href="#tabs-3" onclick="resultShow('tab-3-result');">激活上线追踪</a></li>  
							<li><a href="#tabs-4" onclick="resultShow('');">激活VIP和响应级别</a></li>  
						</ul>
					</div>
					<div id="tabs-1" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面新增上线追踪。（查询  -> 选择 -> 设置追踪时间 -> 提交）</span>
						</div>
						<div id="tabs-1-msg"  style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-1-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content" style="padding:20px 0 5px 0;">
							<table border="1px">
					        	<tr>
					        		<td>系统</td>
					        		<td>
					        			<s:select name="appSel" list="appLists" listKey="id" listValue="appName"></s:select>
									</td>
									<td>Service名：</td>
									<td colspan="2">
										<input type='text' id='serviceName' />
									</td>
									<td>
										<button type="button" id="searchBtn" class="orange"><span>查询</span></button>
									</td>
								</tr>
					        	<tr>
									<td>追踪时间：</td>
									<td>
										<input type='text' id='tracePeriod' />小时
					        		</td>
					        		<td colspan="4">
										<button type="button" id="submitBtn" class="orange"><span>提交</span></button>
										<button type="button" id="addBtn" class="orange"><span>添加</span></button>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-2" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面设置VIP。（支持批量，service名为可选）</span>
						</div>
						<div id="tabs-2-msg"  style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-2-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name="appSel2" list="appLists" listKey="id" listValue="appName"></s:select>
										Service名：
										<input type='text' id='svcApi' />
										<button type="button" id="searchBtn2" class="orange"><span>查询</span></button>
										<button type="button" id="saveBtn" class="orange"><span>设为VIP</span></button>
										
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-3" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面激活上线追踪。（可双击进行修改后再激活）</span>
						</div>
						<div id="tabs-3-msg"  style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-3-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name="appSel3" list="appLists" listKey="id" listValue="appName"></s:select>
										<button type="button" id="searchBtn3" class="orange"><span>查询</span></button>
										<button type="button" id="activeBtn" class="orange"><span>激活</span></button>
										
									</th>
								</tr>
							</table>
						</div>
					</div>
					<div id="tabs-4" class="tab">
						<div class="message blue" style="margin:-5px 0 0 0;">
							<span><b>说明：</b>本页面激活VIP和响应级别。（注：请警惕操作！）</span>
						</div>
						<div id="tabs-4-msg"  style="margin:10px 0 0 0;display:none;" onclick="$('#tabs-4-msg').hide();">
							<jsp:include page="/common/messages.jsp" />
						</div>
						<div class="content">
							<table>
					        	<tr>
									<th>
										<s:select name="appSel4" list="appLists" listKey="id" listValue="appName"></s:select>
										<button type="button" id="activeBtnVip" class="orange"><span>激活VIP</span></button>
										<button type="button" id="activeBtnLv" class="orange"><span>激活响应级别</span></button>
										
									</th>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="tab-1-result" class="section">
		<div class="box">
			<div id="title1" class="title">
				查询结果
				<span class="hide"></span>
			</div>
			<div id='result1' class="content">
				<table id='list1'></table>
				<div id='pager1'></div>
			</div>
			<div class="content" id="groupService">
				<table border="1">
					<tbody id='groupServiceList'>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="tab-2-result" class="section">
		<div class="box">
			<div class="title">
				查询结果
				<span class="hide"></span>
			</div>
			<div id='result2' class='content'>
				<table id='list2Vip'></table>
				<div id='pager2Vip'></div>
			</div>
		</div>
	</div>
	<br>
	<div id="tab-3-result" class="section">
		<div class="box">
			<div class="title">
				上线追踪列表（双击进行编辑）
				<span class="hide"></span>
			</div>
			<div id='result3' class='content'>
				<table id='list2'></table>
				<div id='pager2'></div>
			</div>
		</div>
	</div>
	<div id="tab-4-result" class="section">
		<div class="box" id="artdlgCtn">
			
		</div>
	</div>
<!-- 生成分页表格 -->
<script type='text/javascript'>
	var resultShow = function(id){
		$("#tab-1-result").hide();
		$("#tab-2-result").hide();
		$("#tab-3-result").hide();
		$("#tab-4-result").hide();
		if(id != ''){
			
			$("#"+id).attr("style","display:block;");
		}
	}
	
	function active_type(cellvalue, options, rowObject) {
		return ACTIVE_TYPE[cellvalue];
	}
	
	var grid = $("#list1");
	
	grid.jqGrid({
		colNames: ['id', 'service名称', '是否VIP'],
		colModel: [
		           {name: 'id', index: 'id', align:'center', hidden: true},
		           {name: 'serviceApiName', index: 'serviceApiName'},
		           {name: 'serviceVip', index: 'serviceVip', align:'center', width: '20', formatter: active_type}
		           ],
		pager: '#pager1',
		height: '221',
		multiselect: true,
		rownumbers: true,
		jsonReader: {
			root:"pager.records",
			total: "total",
			page: "page",
			records: "records",
			repeatitems: false
		}
		
	});
	
	var w = $("#tab-1-result").width();
	grid.setGridWidth(w * 0.96);
	
	var grid2 = $("#list2"); 
	grid2.jqGrid({
		colNames: ['id', '追踪Service', '追踪时间（小时）',  '激活标识'],
		colModel: [
				   {name: 'id', index: 'id', align:'center', hidden:true},
		           {name: 'traceServices', index: 'traceServices', align:'center'},
		           {name: 'tracePeriod', index: 'tracePeriod', align:'center', width: '50'},
		           {name: 'activeFlag', index: 'activeFlag', align:'center', width: '20', formatter: active_type}
		           ],
		jsonReader: {
			root: 'releaseTrackList',
			repeatitems: false
		},
		rowNum: -1,	// 不分页情况，请设置为-1，否则该参数会自动传到后台，可能导致数据显示不完整
		rownumbers: true,
		height: '250',
		pager: '#pager2',
		autowidth: true,
		ondblClickRow: function(rowid, iRow, iCol, e) {
			editTrackList(rowid);
		}
		
	});
	
	//grid2.setGridWidth(w*0.96);
	
	
	var grid3 = $("#list2Vip");
	grid3.jqGrid({
		colNames: ['id', 'service名称', '是否VIP'],
		colModel: [
				   {name: 'id', index: 'id', align:'center', hidden: true},
		           {name: 'serviceApiName', index: 'serviceApiName'},
		           {name: 'serviceVip', index: 'serviceVip', align:'center', width:'20', formatter: active_type}
		           ],
		multiselect: true,
		rownumbers: true,
		height: '221',
		pager: '#pager2Vip',
		jsonReader: {
			root:"pager.records",
			total: "total",
			page: "page",
			records: "records",
			repeatitems: false
		}
		
	});
	
	grid3.setGridWidth(w*0.96);
	
	var showErrorMsg = function(id,type,message){
		addMessageInfo(type,message);
		var messageType = ".message."+type+"";
		$(messageType).hide();
		$("#"+id).show();
		$("#"+id).children(messageType).show();
	}
	
	/**
     * 添加到结果列表（类似购物车）
     */
    function addToMart(container, name, id) {
    	/**
    	 * 防止重复加入
    	 */
    	var lis = container.find('tr');
    	var isExist = false;
    	$.each(lis, function(){
    		var nm = $(this).find('td').text();
    		if(nm == name) {
    			isExist = true;
    			return false;
    		}
    	});
    	
    	if(isExist == true) {
    		addMessageInfo("red",'要添加service已存在。');
    		return ;
    	}
    	
    	/**
    	 * 加入到结果列表
    	 */
    	var t_d = $("<td></td>");
    	t_d.text(name);
    	t_d.attr('id',id);
    	var t_r = $("<tr></tr>").append(t_d);
    	t_r.appendTo(container);
    	t_r.click(function(){
    		
    		$(this).fadeTo(400, 0, function(){
    			$(this).remove();
    		});
    		
    	});
    }
	
</script>
</body>
</html>
