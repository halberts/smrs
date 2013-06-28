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
				<s:form action="addRoleToGroupInit" namespace="/security"
					method="get" id="searchRoleForm">
					<s:hidden name="group.id" id="thisgroupid" />
					<table>
						<tr>
							<th>登录名：</th>
							<th><s:textfield name="role.name" /></th>
							<th>用戶名：</th>
							<th><s:textfield name="role.description" /></th> 
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
					<div style="width: 100%;font-size: 11px; " id="roleListDiv">
					<table id="roleList"></table> 
					  <div id="rolePager"></div>   
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
			<s:form action="addRoleToGroupInit" namespace="/security" method="get"
				id="searchRoleInGroupForm">
				<table class="form_table">
					<tr>
						<th>登录名：</th>
						<th><s:textfield name="roleInGroup.name" /></th>
						<th>用戶名：</th>
						<tH><s:textfield name="roleInGroup.nickName" /></th>
						<th>Email：</th>
						<tH><s:textfield name="roleInGroup.email" /> <s:hidden
								name="group.id" /> <s:hidden name="group.description" /> <s:hidden
								name="group.name" /></th>
						<th><button type="button" class="orange" onclick="searchRoleInGroupBtnClicked()"><span>查询</span></button>&nbsp;&nbsp;</th>
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
				<div style="width: 100%;font-size: 11px; " id="roleInGroupListDiv">
					<table id="roleInGroupList"></table> 
				  <div id="roleInGroupPager"></div>   
				</div> 
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"> 
var roleGridList;
var roleInGroupGridList;
jQuery(document).ready(function (){  
	var groupid = document.getElementById('thisgroupid').value; 
	roleInGroupGridList = {   
			url: 'searchRoleInGroup.action?group.id='+groupid,
			colNames: ['id','登录名', '用户名', '创建者','操作'],
			colModel: [
	            {name:'id',index:'id', frozen:true, align:"left", editable:false,hidden:true},
				{name:'name',index:'name', frozen:true, align:"left", editable:false},
		   		{name:'description',index:'description', align:"left",width:300},
		   		{name:'createBy',index:'createBy', align:"left", editable: true}, 
		   		{name:'btn',index:'btn',align:"left"}
		   	], 
			autowidth:true,
		   	shrinkToFit: false,
			height: 230,
			width:"100%",
			rowNum: 10,
			pager: '#roleInGroupPager',
			jsonReader:{
				root:"pagerRoleInGroup.records",
				total: "total",
				page: "page",
				records: "records",
				repeatitems: false,
				id:"0" },
				gridComplete: function() {
			        var grid = jQuery("#roleInGroupList");
			        var ids = grid.jqGrid('getDataIDs');	// 获取表格所有 行id
			        for (var i = 0; i < ids.length; i++) {
			            var rowId = ids[i];
			            var rowdata = grid.jqGrid('getRowData', rowId);
			            var btn = "<img title='从组中删除角色' border='0' src='${staticURL}/images/trash.png' onclick='delRoleFromGroup("+rowdata.id+")'>";  
			            grid.jqGrid('setRowData', rowId, { btn: btn}); 
			        } 
			    }
			}
			jQuery("#roleInGroupList").jqGrid(roleInGroupGridList);
			jQuery("#roleInGroupList").setGridWidth(jQuery("#roleInGroupListDiv").width() * 0.9999);
		 
	var TYPE_MAP = {0:'普通账号',1:'域账号'};
	
	function userType(cellvalue, options, rowObject) {
		return TYPE_MAP[cellvalue];
	}
	
	var STATUS_TYPE = {0: '禁用', 1: '启用'}
	function satus_type(cellvalue, options, rowObject) {
		return STATUS_TYPE[cellvalue];
	}
	roleGridList = {   
			url: 'searchRoleJson.action',
			colNames: ['id','角色名', '描述', '创建者', '操作'],
			colModel: [
	            {name:'id',index:'id', frozen:true, align:"left", editable:false,hidden:true},
				{name:'name',index:'name', frozen:true, align:"left", editable:false},
		   		{name:'description',index:'description', align:"left",width:300},
		   		{name:'createBy',index:'createBy', align:"left", editable: true}, 
		   		{name:'btn',index:'btn',align:"left"}
		   	], 
			autowidth:true,
		   	shrinkToFit: false,
		   	height: 230,
			width:"100%",
			rowNum: 10,
			pager: '#rolePager',
			jsonReader:{
				root:"pager.records",
				total: "total",
				page: "page",
				records: "records",
				repeatitems: false,
				id:"0" },
				gridComplete: function() {
			        var grid = jQuery("#roleList");
			        var ids = grid.jqGrid('getDataIDs');	// 获取表格所有 行id
			        for (var i = 0; i < ids.length; i++) {
			            var rowId = ids[i];
			            var rowdata = grid.jqGrid('getRowData', rowId);
			            var btn = "<img title='将角色添加到组' border='0' src='${staticURL}/images/group.png' onclick='addRoleToGroup("+rowdata.id+")'>";  
			            grid.jqGrid('setRowData', rowId, { btn: btn}); 
			        } 
			    }
			}
			jQuery("#roleList").jqGrid(roleGridList);
			jQuery("#roleList").setGridWidth(jQuery("#roleListDiv").width() * 0.9999);
			
	});
	
	var TYPE_MAP = {0:'普通账号',1:'域账号'};
	
	function userType(cellvalue, options, rowObject) {
		return TYPE_MAP[cellvalue];
	}
	
	var STATUS_TYPE = {0: '禁用', 1: '启用'}
	function satus_type(cellvalue, options, rowObject) {
		return STATUS_TYPE[cellvalue];
	} 
	
	function addRoleToGroup(id){
		var groupid = document.getElementById('thisgroupid').value; 
		window.location.href = "${dynamicURL}/security/addRoleToGroup.action?group.id="+groupid+"&roleId="+id; 
	}
	
	function delRoleFromGroup(id){
		var groupid = document.getElementById('thisgroupid').value; 
		window.location.href = "${dynamicURL}/security/deleteRoleFromGroup.action?group.id="+groupid+"&roleId="+id; 
	}
	
	/*搜索按钮*/
	function searchBtnClicked(){
		var query = $('#searchRoleForm').serialize(); // 序列化参数
		$("#roleList").jqGrid('setGridParam',{url: 'searchRoleJson.action?'+query}).trigger("reloadGrid");
	}
	
	/*搜索按钮*/
	function searchRoleInGroupBtnClicked(){
		var query = $('#searchRoleInGroupForm').serialize(); // 序列化参数
		$("#roleInGroupList").jqGrid('setGridParam',{url: 'searchRoleInGroup.action?'+query}).trigger("reloadGrid");
	}
</script> 
</body>
</html>
 
				<%-- 	<th>角色名</th>
					<th>描述</th>
					<th>创建者</th>
					<th>创建时间</th>
					<th>最后修改者</th>
					<th>最后修改时间</th> 
						<td><s:property value="name" /></td>
						<td><s:property value="description" /></td>
						<td><s:property value="createBy" /></td>
						<td><s:date name="gmtCreate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><s:property value="lastModifiedBy" /></td>
						<td><s:date name="gmtModified" format="yyyy-MM-dd HH:mm:ss" /></td>  --%>