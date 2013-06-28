<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>服务器健康状况</title>
<link rel="stylesheet" type="text/css"
	href="${staticURL}/css/hop/grid/custom-grid-style/jquery-ui-1.8.21.custom.css" />
<link type="text/css" rel="stylesheet"
	href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasAxisTickRenderer.min.js"></script>
<script
	src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pieRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script> 
<script type="text/javascript"
	src="${staticURL}/scripts/jqplot/jqplot.canvasTextRenderer.min.js"></script>
<%-- <script type="text/javascript"
	src="${staticURL}/scripts/jqplot/jqplot.canvasAxisLabelRenderer.min.js"></script>--%>
<style> 
/* 设置jqgrid表格字体 */
.ui-jqgrid {
	font-size: 12px;
	position: relative;
}

a#nodeListHidden-button {
	display: none;
}

.jqplot-table-legend{
	width:135px;
}
</style>

<script>
$(document).ready(function(){ 
/* 	  var a = [2, 1, 0];
	  var b = [0, 1, 2];
	  var c = [2, 1, 1];
	  
	   plot4 = $.jqplot('chart4_line', [a, b, c], {
	      title:'直线图',
	      
	      highlighter: {	//鼠标经过高亮显示每个点
	        show: true,
			tooltipAxes: 'y',
	        sizeAdjust: 7.5
	      },
		  
		  axes: {
			xaxis: {  
				renderer: $.jqplot.CategoryAxisRenderer,
				ticks: ['3月', '4月', '5月']
			},
			yaxis: { 
				renderer: $.jqplot.CategoryAxisRenderer,
				ticks: ['0', '1', '2']
			}
		  },
		  
		  legend: {
			show: true,
			labels: ['已知', '未知', '超时']
		  }
	  });  */
	
	   
	   
	var grid = $("#list1");
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		var appId = $("select[name='appSel']").find('option:selected').val();
		var url = 'serverNodeStatus.action?appId=' + appId;
		grid.jqGrid('setGridParam', {url: url}).trigger('reloadGrid');
		
		$("#content").show();
		//$("#content").css("height", "379");

	});
	
});
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div>
			<div>
				<ul>
					<li class="first"></li>
					<li><a href="#">监控中心</a></li>
					<li><a href="#">基础数据维护</a></li>
					<li class="last"><a href="#">业务分组</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面查看某应用系统下所有节点的健康状况。</span>
		</div>
	</div>
	<div class="section" id='cretial'>
		<div class="box">
			<div class="title">
				请选择业务系统： <span class="hide"></span>
			</div>
			<div class="content">
				<table>
					<tr>
						<th><s:select name="appSel" list="appLists" listKey="id"
								listValue="appName">
							</s:select>
							<button type="button" id="searchBtn" class="orange">
								<span>查询</span>
							</button> <%-- <select id="nodeListHidden" style='display: none'>
						</select> <select id="useEnableHidden" style='display: none'>
						</select> --%></th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id='result' class='section'>
		<div class="box">
			<div class="title">
				查询结果（注：绿色表示节点运行正常，红色表示运行异常，空表示初始化未检测） <span class="hide"></span>
			</div>
			<div class="content" style="display: none;" id="content">
				<table id='list1'></table>
				<div id='pager1'></div>
			</div>
		</div>
	</div>
	<div>
	<div class="section" id='cretialChart'>
		<div class="box">
			<div class="title">
				请选则业务系统和时间段： <span class="hide"></span>
			</div>
			<div class="content"> 
				<table>
					<tr>
						<th><s:select name="appPlotSel" list="appLists" listKey="id"
								listValue="appName">
							</s:select>
							从<sj:datepicker id="startTime" name="startTime"
								label="Select a Date/Time" timepicker="true"
								displayFormat="yy-mm-dd" /> 到 <sj:datepicker id="endTime"
								name="endTime" label="Select a Date/Time" timepicker="true"
								displayFormat="yy-mm-dd" />
							<button type="button" id="healthyPlotBtn" class="orange">
								<span>统计</span>
							</button>
						</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id='resultChart' class='section'>
		<div class="box">
			<div class="title">
				统计结果（注：未检测到正常之间均为未检测，正常到异常之间均为正常，异常以上为异常） <span class="hide"></span>
			</div>
			<div class="content" style="display: block"> 
					<div id="chart4_line" style="margin-top:20px; margin-left:1%; width:850px; height:450px;"></div>  
			</div>
		</div>
	</div> 
	</div>
	<!-- 生成分页表格 -->
	<script type='text/javascript'>
	var grid = $("#list1");
	grid.jqGrid({
		colNames: ['id', '结点IP','节点地址','节点状态','详细信息','是否启用','检查时间'],
		colModel: [
				   {name: 'id', index: 'id', align:'center', hidden:true },
   			       {name: 'appId', index: 'appId', align:'center', hidden:true},  
   			      /*  {name: 'nodeNm', index: 'nodeNm', align:'center', editable: true},   */
   				   {name: 'url', index: 'url', align:'center', editable: true},  
   		           {name: 'urlStatus', index: 'urlStatus', align:'center'},   
   		           {name: 'urlStatusMsg', index: 'urlStatusMsg', align:'center'},  
   			       {name: 'status', index: 'status', align:'center', editable: true, formatter: satus_type,edittype:"select",editoptions:{value:"1:启用;2:禁用"}},  
   			   	   {name: 'createDate', index: 'createDate', align:'center',
   			   		formatter : 'date',
   					formatoptions : {
   						srcformat : 'Y-m-d H:i:s',
   						newformat : 'Y-m-d H:i:s'
   					},
   					editoptions : {
   						size : 20
   					}}
		           ],
		pager: '#pager1',
		rowList: '',
		pgbuttons: false,
		pgtext: null,
		viewrecords: false,
		height: '265',
		rownumbers: true,
		jsonReader: {
			root:"appHealthDetail",
			repeatitems: false
		},
		rowNum: -1,
		editurl: 'urlCrud.action',
		afterInsertRow: function(rowid, aData){
			var sts = parseInt(aData.urlStatus);
			var img;
			if(sts == "2") {
				img = "<img src='${staticURL}/style/images/green.png' />";
			} else  if(sts == "3") {
				img = "<img src='${staticURL}/style/images/red.png' />";
			}			
			
			grid.jqGrid('setRowData', rowid, {urlStatus: img});
		}
		
	});
	
	var w = $("#result").width();
	grid.setGridWidth(w*0.95);
	
	grid.jqGrid('navGrid', '#pager1', {edit:true, del:true, add:true, search:false},
			
			{	// edit option
				reloadAfterSubmit: true,
	 		 	onclickSubmit: function(params, posdata) {
	 				var rowid = posdata.list1_id;
					var rowdata = grid.jqGrid('getRowData', rowid); 				
					var id = rowdata.id;
					//alert(useEnable.value);
					var useEnable = $("#tr_status > td.DataTD > select").find('option:selected').val();
					return {checkerId: id,useEnable: useEnable.value};
				}
				
			},
			
			{	// add option
				reloadAfterSubmit: true,
				onclickSubmit: function(params, posdata) {
					var appId = $("select[name='appSel']").find('option:selected').val();
					//var nodeId = $("#tr_nodeName > td.DataTD > select").find('option:selected').val();
					var useEnable = $("#tr_status > td.DataTD > select").find('option:selected').val();
					//alert(useEnable)
					return {appId: appId, useEnable: useEnable.value};
				}
				
				
			},	
			
			{	// del option
				reloadAfterSubmit: true,
				onclickSubmit: function(params, rowid) {
					var rowdata = grid.jqGrid('getRowData', rowid); 				
					var id = rowdata.id;
					return {checkerId: id};
				}
			}
			
		);
	
	grid.jqGrid('navGrid', '#pager1');
	var STATUS_TYPE = {1: '启用', 2: '禁用'}
	function satus_type(cellvalue, options, rowObject) {
		return STATUS_TYPE[cellvalue];
	}
	
	
	$("#healthyPlotBtn").click(function(){
		$('#chart4_line').empty();
		var appIdPlot = $("select[name='appPlotSel']").find('option:selected').val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' && endTime == ''){
			alert('开始、结束时间不能为空');
		}else{
		//alert(appIdPlot);
		var labelsContent = new Array();
		var lables = new Array();
		var nodeContent = new Array();
		var linesContent = new Array();
		var nodeCheck = null;
		var linesParam = null;
		$.ajaxSetup ({ 
			async : false,
			cache: false 
			});
		$.getJSON("searchNodeName.action?appId="+appIdPlot,
				function call(data){ 
				 ifModified:true;
				 var dataFlag = true;
				 for(i=0;i<data.urlList.length;i++){
					 if(nodeContent.length>0){
						 //nodeContent.splice(0,nodeContent.length);
						 nodeContent = new Array();
					 } 
					//alert(data.nodeName[i]);
				        $.ajaxSetup ({ 
				        	async : false,
							cache: false 
							});
						$.getJSON("healthyPlot.action?&startTime="+startTime+"&endTime="+endTime+"&urlId="+data.urlList[i].id,
								function call(healthyPlot){ 
								 ifModified:true;
								 for(j=0;j<healthyPlot.healthDetailList.length;j++){
									 var status = null;
									 if(healthyPlot.healthDetailList[j].status == 2){
										 status = 1;  
									 }else{
										 status = healthyPlot.healthDetailList[j].urlStatus;
									 }
									 //alert('['+healthyPlot.serverChecker[j].checkTime+','+status+']');
									 var time = healthyPlot.healthDetailList[j].createDateStr.replace('T',' ');   
									/*  if(j==0){
										 nodeContent = [time ,status]; 
									 }else{
										 nodeContent = [nodeContent,[time ,status]]; 
									 } */
									// alert(j);
									 nodeContent[j] = [time ,status+i/10];
								    // alert(nodeContent[j]);
									 dataFlag = false;
								 }
								// nodeCheck = [nodeContent];  
								 //alert(nodeContent);
						});  
						
						linesContent[i] = nodeContent; 
						labelsContent[i] = data.urlList[i].nodeName;  
				 }  
				 if(dataFlag){
					 alert('本段时间无节点运行信息存在');  
				 }
				
				 //alert('labelsContent'+labelsContent); 
				 });

		   //开始时间与结束时间之间的整点,以及每个节点的检查次数
		  //获取节点名称以及节点节点状态序列 ，
		/*   var startTime = $("#startTime").val(); 
		  var endTime = $("#endTime").val(); */
		/*   var a = [['2012-10-21 12:05:01',1],['2012-10-22 12:10:01',2],['2012-10-23 12:15:01',3]];
		  var b = [['2012-10-21 12:05:01',2],['2012-10-22 12:10:01',2],['2012-10-23 12:15:01',3]];
		  var c = []; */
			/* var b = [[1, 1],[3,1],[5,1],[7,1],[9,1],[11,1]];
			var c = [[1, 3],[3,3],[5,3],[7,3],[9,3],[11,3]]; */
			//var a = [['2012-10-21 12:05:01',1],['2012-10-22 12:10:01',2],['2012-10-23 12:15:01',3]],[['2012-10-21 12:05:01',1],['2012-10-22 12:10:01',2],['2012-10-23 12:15:01',3]];
			$.jqplot('chart4_line',  linesContent,
					{ title:'服务器健康状况统计',
					 /*  axes: { 
						xaxes:{xaxis:{renderer:$.jqplot.DateAxisRenderer}},
						yaxis: {
							min: 0,
							max: 6,
							ticks: [[0, ''], [1, '未检测'], [2, '正常'], [3, '异常'], [4, '']]
						}
					  },
					  series:[{color:'#5FAB78'}] */
				    
						 /*  yaxis: {
								min: 0,
								max: 6,
								ticks: [[0, ''], [1, '未检测'], [2, '正常'], [3, '异常'], [4, '']]
							} */
				axes:{       
					xaxis:{           
						renderer:$.jqplot.DateAxisRenderer,   
		                tickRenderer: $.jqplot.CanvasAxisTickRenderer,  
						tickOptions:{formatString:'%Y-%m-%d %H:%M',angle: 30},
						//min:'2012-10-21 11:30:00', 
						//max:'2012-10-21 12:30:00',
						min:startTime, 
						max:endTime
						//tickInterval:'1 Hour'        
					 },
					 yaxis: {
							min: 0,
							max: 6,
							ticks: [[0, ''], [1, '未检测'], [2, '正常'], [3, '异常'], [4, '']]
						}
					}, 
				        legend: {
						show: true,
						placement: 'outside',
						marginRight:30, 
						labels: labelsContent
					  }  
					});
		}
	}); 
</script>
</body>
</html>
