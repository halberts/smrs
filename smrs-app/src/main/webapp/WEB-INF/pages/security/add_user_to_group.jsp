<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="security" uri="/security-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组内用户查询</title> 

</head>
<body>

	<div id="breadcrumbs">
		<div style="overflow: hidden; position: relative; width: 750px;">
			<div>
				<ul style="width: 5000px;">
					<li class="first"></li>
					<li><a href="#">安全控制</a></li>
					<li><a href="#">用户组管理</a></li>
					<li class="last"><a href="#">添加组内人员</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
	<jsp:include page="/common/messages.jsp" />
		<div class="box">
			<div class="title">
				查询条件 <span class="hide"></span>
			</div>
			<div class="content">
				<s:form action="addUserToGroupInit" namespace="/security"
					method="get" id="searchUserForm">
					<s:hidden name="group.id" id="thisgroupid" />
					<table>
						<tr>
							<th>登录名：</th>
							<th><s:textfield name="user.name" /></th>
							<th>用戶名：</th>
							<th><s:textfield name="user.trueName" /></th>
							<th>Email：</th>
							<th><s:textfield name="user.email" /></th>
							<th><button type="button" class="orange" onclick="searchBtnClicked()"><span>查询</span></button>&nbsp;&nbsp;</th>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="box">
			<div class="title">
				人员列表 <span class="hide"></span>
			</div>
			<div class="content">
				<div class="dataTables_wrapper">
					<div>
						<div class="dataTables_filter"></div>
					</div>
					<div style="width: 100%;font-size: 11px; " id="userListDiv">
					<table id="userList"></table> 
					  <div id="userPager"></div>   
					</div> 
				</div>
			</div>
		</div>
	</div>
	
	
	
	<!-- -------------------------------------------------------------------------------- -->
	<div class="section">
	<div class="box">
		<div class="title">
			查询条件 <span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="addUserToGroupInit" namespace="/security" method="get"
				id="searchUserInGroupForm">
				<table class="form_table">
					<tr>
						<th>登录名：</th>
						<th><s:textfield name="userInGroup.name" /></th>
						<th>用戶名：</th>
						<tH><s:textfield name="userInGroup.trueName" /></th>
						<th>Email：</th>
						<tH><s:textfield name="userInGroup.email" /> <s:hidden
								name="group.id" /> <s:hidden name="group.description" /> <s:hidden
								name="group.name" /></th>
						<th><button type="button" class="orange" onclick="searchUserInGroupBtnClicked()"><span>查询</span></button>&nbsp;&nbsp;</th>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			组内人员列表 <span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper">
				<div>
					<div class="dataTables_filter"></div>
				</div> 
				<div style="width: 100%;font-size: 11px; " id="userInGroupListDiv">
					<table id="userInGroupList"></table> 
				  <div id="userInGroupPager"></div>   
				</div> 
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"> 
var userGridList;
var userInGroupGridList;
jQuery(document).ready(function (){  
	var groupid = document.getElementById('thisgroupid').value; 
	userInGroupGridList = {   
			url: 'searchUserInGroup.action?group.id='+groupid,
			colNames: ['id','登录名', '用户名', '邮箱', '状态','操作'],
			colModel: [
	            {name:'id',index:'id', frozen:true, align:"left", editable:false, hidden:true},
				{name:'name',index:'name', frozen:true, align:"left", editable:false},
		   		{name:'trueName',index:'trueName', align:"left"},
		   		{name:'email',index:'email', align:"left",editable: true}, 
		   		{name:'status',index:'status', align:"left", formatter: satus_type},
		   		{name:'btn',index:'btn',align:"left"}
		   	], 
			autowidth:true,
		   	shrinkToFit: false,
			height: 230,
			width:"100%",
			rowNum: 10,
			pager: '#userInGroupPager',
			jsonReader:{
				root:"pagerUserInGroup.records",
				total: "total",
				page: "page",
				records: "records",
				repeatitems: false,
				id:"0" },
				gridComplete: function() {
			        var grid = jQuery("#userInGroupList");
			        var ids = grid.jqGrid('getDataIDs');	// 获取表格所有 行id
			        for (var i = 0; i < ids.length; i++) {
			            var rowId = ids[i];
			            var rowdata = grid.jqGrid('getRowData', rowId);
			            var isGroupAdmin;
			            /*
			            if(rowdata.isAdmin=="是"){
			            	isGroupAdmin = "<img title='取消管理员资格' border='0' src='${staticURL}/images/user.gif' onclick='deleteAdminToGroup("+rowdata.id+")'>"
			            }else{
			            	isGroupAdmin = "<img title='设置为管理员' border='0' src='${staticURL}/images/user.gif' onclick='addAdminToGroup("+rowdata.id+")'>"
			            }
			            */
			            var btn = "<img title='从组中删除' border='0' src='${staticURL}/images/trash.png' onclick='deleteUserFromGroup("+rowdata.id+")'>&nbsp;&nbsp;";  
			            grid.jqGrid('setRowData', rowId, { btn: btn}); 
			        } 
			    }
			}
			jQuery("#userInGroupList").jqGrid(userInGroupGridList);
			jQuery("#userInGroupList").setGridWidth(jQuery("#userInGroupListDiv").width() * 0.9999);
		 
	var TYPE_MAP = {'1':'管理人员','2':'店员'};
	
	function userType(cellvalue, options, rowObject) {
		return TYPE_MAP[cellvalue];
	}
	
	var STATUS_TYPE = {'N': '禁用', 'Y': '启用'}
	function satus_type(cellvalue, options, rowObject) {
		return STATUS_TYPE[cellvalue];
	}
	var ISADMIN_TYPE = {0: '否', 1: '是'} 
	function isAdmin_type(cellvalue, options, rowObject) {
		return ISADMIN_TYPE[cellvalue];
	}
	
	userGridList = {   
			url: 'searchUserJson.action',
			colNames: ['id','登录名', '用户名', '邮箱', '类型', '状态','操作'],
			colModel: [
	            {name:'id',index:'id', frozen:true, align:"left", editable:false, width:80,hidden:true},
				{name:'name',index:'name', frozen:true, align:"left", editable:false, width:80},
		   		{name:'trueName',index:'trueName', align:"left", width:80},
		   		{name:'email',index:'email', align:"left", width: 200, editable: true},
		   		{name:'userType',index:'userType',  align:"left", formatter: userType, width:80},
		   		{name:'status',index:'status', align:"left", formatter: satus_type, width:80},
		   		{name:'btn',index:'btn',align:"left"}, 
		   	], 
			autowidth:true,
		   	shrinkToFit: false,
		   	height: 230,
			width:"100%",
			rowNum: 10,
			pager: '#userPager',
			jsonReader:{
				root:"pager.records",
				total: "total",
				page: "page",
				records: "records",
				repeatitems: false,
				id:"0" },
				gridComplete: function() {
			        var grid = jQuery("#userList");
			        var ids = grid.jqGrid('getDataIDs');	// 获取表格所有 行id
			        for (var i = 0; i < ids.length; i++) {
			            var rowId = ids[i];
			            var rowdata = grid.jqGrid('getRowData', rowId);
			            var btn = "<img title='添加到组' border='0' src='${staticURL}/images/group.png' onclick='addUserToGroup("+rowdata.id+")'>";   
			            grid.jqGrid('setRowData', rowId, { btn: btn}); 
			        } 
			    }
			}
			jQuery("#userList").jqGrid(userGridList);
			jQuery("#userList").setGridWidth(jQuery("#userListDiv").width() * 0.9999);
			
	});
	
	var TYPE_MAP = {0:'普通账号',1:'域账号'};
	
	function userType(cellvalue, options, rowObject) {
		return TYPE_MAP[cellvalue];
	}
	
	var STATUS_TYPE = {'N': '禁用', 'Y': '启用'}
	function satus_type(cellvalue, options, rowObject) {
		return STATUS_TYPE[cellvalue];
	}
	 
	/*搜索按钮*/
	function searchBtnClicked(){
		var query = $('#searchUserForm').serialize(); // 序列化参数
		$("#userList").jqGrid('setGridParam',{url: 'searchUserJson.action?'+query}).trigger("reloadGrid");
	}
	
	/*搜索按钮*/
	function searchUserInGroupBtnClicked(){
		var query = $('#searchUserInGroupForm').serialize(); // 序列化参数
		$("#userInGroupList").jqGrid('setGridParam',{url: 'searchUserInGroup.action?'+query}).trigger("reloadGrid");
	}
	
	function deleteAdminToGroup(){ 
		var groupid = document.getElementById('thisgroupid').value; 
		window.location.href = "${dynamicURL}/security/deleteAdminFromGroup.action?group.id="+groupid+"&admin="+id; 
	}
</script>













	<script type="text/javascript"> 
	function addUserToGroup(id){
		var groupid = document.getElementById('thisgroupid').value; 
		window.location.href = "${dynamicURL}/security/addUserToGroup.action?group.id="+groupid+"&userId="+id; 
	} 
	function deleteUserFromGroup(id){
		var groupid = document.getElementById('thisgroupid').value; 
		window.location.href = "${dynamicURL}/security/deleteUserFromGroup.action?group.id="+groupid+"&userId="+id; 
	} 
	function searchUserInGroup(){ 
		$("searchUserInGroupForm").submit();
	} 
	function searchUser(){ 
		$("searchUserForm").submit();
	} 
	function addAdminToGroup(id){
		var groupid = document.getElementById('thisgroupid').value; 
		window.location.href = "${dynamicURL}/security/addAdminToGroup.action?group.id="+groupid+"&admin="+id; 
	} 
	
</script>
</body>
</html>
