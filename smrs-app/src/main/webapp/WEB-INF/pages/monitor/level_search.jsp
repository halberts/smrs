<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看系统响应信息</title>
<script type="text/javascript" src="${staticURL}/js/copyToClipboard.js"></script>
<script>
$(document).ready(function(){
	
	//loadLeftMenuAndNav();

	var grid = $("#list1");
	
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		$("#info").text('');
		
		var appId = $("select[name='appSel']").find("option:selected").val();
		var url = 'getServiceLevelDetail.action?appId=' + appId;
		
		// 先清空之前的值
		$("#todayCount").text("");
		$("#weekCount").text("");
		$("#monthCount").text("");
		
		$("#lvsum").hide();
		$("#lvtbody").empty();
		
		$.ajax({
			url: url,
			type: 'GET',
			cache: false,
			success: function(data){
				// 实际响应数量
				$("#todayCount").text(data.todayCount);
				$("#weekCount").text(data.weekCount);
				$("#monthCount").text(data.monthCount);
				
				
				// 响应汇总
				var lv = data.levelSummary;
				$("<tr><td>响应级别</td><td>数量（个）</td></tr>").appendTo("#lvtbody");
				for(prop in lv) {
					var tr = $("<tr></tr>").appendTo("#lvtbody");
					$("<td>" + prop + "</td>").appendTo(tr);
					$("<td>" + lv[prop] + "</td>").appendTo(tr);
				}
				
				$("#lvsum").show();
				
				grid.jqGrid('clearGridData');	//清空表格，使pager重置
				// 更新表格
				grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
			},
			
			error: function() {
				$("#info").text('ajax获取数据出错！');
				return;
			}
		});
	});
	
	/**
	 * 最大化时重新设定表格宽度
	 */
 	$("#clickDiv").click(function(){
 		var w = $("#cretial").width();
 		$("#list1").setGridWidth(w*0.7);
	}); 
	
});
</script>
</head>
<body>

	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面查看具体系统api响应级别和汇总情况，以及实际超时数量。</span>
		</div>
	</div>

	<!-- 查询条件 -->
	<div class="section" id='cretial'>
		<div class="box">
			<div class="title">
				请选择业务系统：
				<span class="hide"></span>
			</div>
			<div class="content">
			
		        <table>
		        	<tr>
						<th>
							请选择业务系统：
							<s:select name='appSel' list='appLists' listKey='id' listValue='appName'></s:select>
							<button type="button" id="searchBtn" class="orange"><span>查询</span></button>
						</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id='searchResult' class='section'>
	<!-- 按照Lv汇总后的结果 -->
		<div class="three-quarter">
			<div class="box">
				<div class="title">
					查询结果
					<span class="hide"></span>
				</div>
				<div class="content">
					<table id='list1'></table>
					<div id='pager1' style="width:100px;"></div>
				</div>
			</div>
		</div>
		<div class="quarter">
			<div class="box">
				<div class="title">
					实际超时数量
					<span class="hide"></span>
				</div>
				<div class="content">
					<table border="1">
						<tbody>
							<tr>
								<td>当天</td>
								<td><span id='todayCount'></span></td>
							</tr>
							<tr>
								<td>本周</td>
								<td><span id='weekCount'></span></td>
							</tr>
							<tr>
								<td>本月</td>
								<td><span id='monthCount'></span></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="box">
				<div class="title">
					所有api按响应级别汇总结果
					<span class="hide"></span>
				</div>
				<div class="content">
					<table border="1">
						<tbody id='lvtbody'>
				 			
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>	



<!-- 生成分页表格 -->
<script type='text/javascript'>
	var grid = $("#list1");
	grid.jqGrid({
		colNames: ['service名称', '响应级别'],
		colModel: [
		           {name: 'serviceApiName', index: 'serviceApiName'},
		           {name: 'overtimeLv', index: 'overtimeLv', align:'center', width: '20',formatter: change_title},
		           ],
		rownumbers: true,
		height: '221',
		hidegrid: false,
		pager: '#pager1',
		jsonReader: {
			root:"pager.records",
			total: "total",
			page: "page",
			records: "records",
			repeatitems: false
		},
		ondblClickRow: function(rowid, iRow, iCol, e) {
			var serviceApiName = grid.jqGrid('getRowData', rowid).serviceApiName;
			copyToClipboard(serviceApiName);
			//alert(serviceApiName);
		}
	});
	
	var w = $("#cretial").width();
	grid.setGridWidth(w*0.7);
	
	var lvsecond = null; 
	$.ajaxSetup ({ 
		async : false,
		cache: false 
		});
	$.getJSON("levelInfo.action",
			function call(data){ 
			ifModified:true; 
			lvsecond = data.timeoutLevelMap; 
			//alert((data.timeoutLevelMap)['LV1']);
		 
	});
	//根据等级获取到等级对应的级别
	
	
	function change_title(cellvalue, options, rowObject){
		return '<span title='+lvsecond[cellvalue]+'>'+cellvalue+'</span>'
	}
	//$("#lvsum").hide();
</script>


</body>
</html>