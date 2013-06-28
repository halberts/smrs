<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>action监控</title>
	
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
		var appId = $("#appSel").val();
		//appId = 55;

		var sd = $("#sdate").datepicker('getDate');
		var ed = $("#edate").datepicker('getDate');
		var marchStart = new Date();
		marchStart.setHours(0);
		marchStart.setMinutes(0);
		marchStart.setSeconds(0);
		marchStart.setMilliseconds(0);
		marchStart.setMonth(2);
		marchStart.setDate(1);
		marchStart.setFullYear(2013);
		if(sd != null && marchStart-sd >0){
			addMessageInfo("red","只能查询2013年03月及之后的数据。");
			return ;
		}
		if(ed != null && marchStart-ed >0){
			addMessageInfo("red","只能查询2013年03月及之后的数据。");
			return ;
		}
		var diff = ed - sd;
		var days = diff / (1000 * 60 * 60 * 24);
		days =1;
		if(days <= 0) {
			addMessageInfo("red","结束日期必须大于起始日期。");
			return ;
		} else {
			var from = $("#sdate").val();
			var to = $("#edate").val();
			var className = $("#className").val();
			var url = 'searchProfile.action?searchModel.appId=' + appId + '&searchModel.from=' + from + '&searchModel.to=' + to + '&searchModel.className=' + className;
			
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
			<span><b>说明：</b>点击Action后的展开图标查看内部调用明细，查询时间段不支持跨月查询，查询数据从201303月开始，更早的数据请联系管理员通过数据库查询获取结果。</span>
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
							<s:select name='appSel' list='appList' listKey='id' listValue='appName'></s:select>
							时间：
							<sj:datepicker id="sdate" displayFormat="yy-mm-dd" showOn="focus"/> 
							到
							<sj:datepicker id="edate" displayFormat="yy-mm-dd" showOn="focus"/> 
							Action:
							<input size="40" type='text' id='className'/>
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
		colNames: ['id', 'Action类','Action方法','Action','耗时(ms)', '调用时间', '附加属性','','root'],
		colModel: [
				   {name:'id',index:'id', width:1,hidden:true,key:true},
 		           {name: 'profileBeanInfo.className', index: 'profileBeanInfo.className', align:'left', width: '380',hidden:true},
		           {name: 'profileBeanInfo.methodName', index: 'profileBeanInfo.methodName', align:'center',width:'70',hidden:true}, 
		           {
					   name:'action',key : true,index:'action',width:400,sortable:false,
					   formatter : function(value, options, rData){
						   return rData.profileBeanInfo.className + "."+rData.profileBeanInfo.methodName;
					   }
				   },
		           {name: 'executionTime', index: 'executionTime',sortable:true, align:'right',width:'40'},
		           {name: 'startTime', index: 'startTime',sortable:true, align:'center', width: '100', formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}},
		           {name: 'infomationMap', index: 'infomationMap',sortable:false, align:'center', width: '40'},
		           {
		        	   name:'profileBeanId',index:'profileBeanId',width:0,hidden:true,
					   formatter : function(value, options, rData){
						   return rData.profileBeanInfo.id;
					   }
		           },
		           {name: 'profileBeanInfo.id', index: 'profileBeanInfo.id', align:'center', width: '10',hidden:true}
		           ],
		pager: '#pager1',
		prmNames:{page:"pager.currentPage",rows:"pager.pageSize"},
		height: '221',
		rownumbers: false,
		viewrecords: true,
		jsonReader: {
			root:"pager.records",
			total: "pager.totalPages",
			page: "pager.currentPage",
			records: "pager.totalRecords",
			repeatitems: false
		},
		subGrid : true,
		//subGridModel: [{ name  : ['ID','方法','调用次数','执行时间'],width : [10,380,80,80]}],
		//subGridUrl: '${dynamicURL}/monitor/getChildrenProfileBean.action?parentId=175',
		subGridRowExpanded: function(subgrid_id, row_id) {
			var subgrid_table_id, pager_id;
			subgrid_table_id = subgrid_id+"_t";
			pager_id = "p_"+subgrid_table_id;
			$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table><div id='"+pager_id+"'></div>");
			var row = grid.jqGrid('getRowData', row_id);
			jQuery("#"+subgrid_table_id).jqGrid({
				url: '${dynamicURL}/monitor/getChildrenProfileBean.action?parentId='+row.profileBeanId,
				colNames: ['方法名','方法名','方法名','调用次数','执行时间'],
				colModel: [
					{name:"className",index:"className",width:1,hidden:true},
					{name:"methodName",index:"methodName",width:1,hidden:true},
					{
					   name:'method',key : true,index:'method',width:450,
					   formatter : function(value, options, rData){
						   return rData.className + "."+rData.methodName;
					   }
				    },
					{name:"times",index:"times",width:30,align:"right"},
					{name:"executionTime",index:"executionTime",width:30,align:"right"}
				],
				height:'auto',
				viewrecords: true,
				jsonReader: {
					root:"profileBeanInfoList",
					repeatitems: false
				}
			});
			jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false}).setGridWidth($("#right").width()*0.87);;
		}
	});
	
	var w = $("#right").width();
	grid.setGridWidth(w*0.94);
</script>


</body>
</html>
