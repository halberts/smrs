<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${staticURL}/style/hopCss/hop.css" />	
<link rel="stylesheet" type="text/css" href="${staticURL}/style/hopCss/custom-grid-style/jquery-ui-1.8.21.custom.css" />

<script src="${staticURL}/scripts/hop.js"></script>
<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
<style>
.bd .col-2 dl dd table td{ padding:2px 2px 2px 0px;}
.bd .col-2 dl dd table th{ padding-left:0px;}
</style>
<script type="text/javascript"> 
var jmxGridList;
jQuery(document).ready(function (){  
	jmxGridList = {   
			url:"queueList.action",
			datatype: "json", 
			type:"POST", 
			rownumbers: true,
			colNames : [ "ID", "Time", "resourceKey", "resourceValue","resourceIdentifier"],
									colModel : [ {
										name : "id",
										index : "id",
										width : "10%",
										editable : true
									}, {
										name : "auditTimestamp",
										index : "auditTimestamp",
										width : "15%",
										editable : true,
										formatter : 'date',
										formatoptions : {
											srcformat : 'Y-m-d H:i:s',
											newformat : 'Y-m-d H:i:s'
										},
										editoptions : {
											size : 12
										}
									}, {
										name : "resourceKey",
										index : "resourceKey",
										width : "20%",
										editable : true, 
									}, {
										name : "resourceValue",
										index : "resourceValue",
										width : "10%",
										editable : true, 
									},  {
										name : "resourceIdentifier",
										index : "resourceIdentifier",
										width : "10%",
										editable : true, 
									}], 
									rowNum : 30,
									rowList : [ 10, 20, 30 ],
									pager : "#jmxPager",
									sortname : "createtime",
									viewrecords : true,
									multiselect : false,
									sortorder : "desc", 
									width : "100%",
									height: 420, 
									jsonReader : {
										root: 'pager.records',
										repeatitems: false,
										total: "total",
										page: "page",
										records: "records"
									}
								}; 
	 					jQuery("#jmxList").jqGrid(jmxGridList);
						jQuery("#jmxList").setGridWidth(
								jQuery("#jmxDiv").width() * 0.9999);
					});
</script>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">系统监控</a></li>
		<li><a href="#">消息监控</a></li>
		<li class="last"><a href="#">HornetqQueue列表</a></li>
	</ul></div></div>
</div>
<div class="section">
<div class="box">
<div class="title">
	queue列表
	<span class="hide"></span>
</div>
<div class="content">
<div style="width: 100%;font-size: 11px; " id="jmxDiv">
<table id="jmxList"></table> 
  <div id="jmxPager"></div>   
</div>  
</div>
</div>
</div>
</body>
</html>