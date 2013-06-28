<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新组信息</title>
<style type="text/css">
.aui_content {
	padding: 0px 0px;
}

.aui_content {
	padding-bottom: 0px !important;
	padding-left: 0px !important;
	padding-right: 0px !important;
	padding-top: 0px !important;
}
</style>
<script type="text/javascript">
	var addAdminDialog = null;
	var searchUserInGroupDialog;
	var searchAdminInGroupDialog;
	var searchRoleInGroupDialog;
	function addAdminToGroup() {
		document.getElementById('addAdminToGroupInit').contentWindow.location.reload();  
    	searchAdminInGroupDialog = art.dialog({
					title : '添加组内管理员',
					content : document.getElementById('addAdminToGroup'),
					esc : true,
					height : '100px',
					width : '100px',
					close : function() {
						document.getElementById('searchAdminInGroup').contentWindow.location
								.reload();
					}
				});
	}
	function addUserToGroup() {
		document.getElementById('addUserToGroupInit').contentWindow.location.reload();  
		searchUserInGroupDialog = art.dialog({
					title : '添加组内人员',
					content : document.getElementById('addUserToGroup'),
					esc : true,
					height : '100px',
					width : '100px',
					close : function() {
						document.getElementById('searchUserInGroup').contentWindow.location
								.reload();
					}
				});
	}
	function addRoleToGroup() {
		document.getElementById('addRoleToGroupInit').contentWindow.location.reload();  
		searchRoleInGroupDialog = art
				.dialog({
					title : '添加组内角色',
					content : document.getElementById('addRoleToGroup'),
					esc : true,
					height : '100px',
					width : '100px',
					close : function() {
						document.getElementById('searchRoleInGroup').contentWindow.location
								.reload();
					}
				});
	}
	function addAdminToGroupBlock() {
		addAdminDialog = art.dialog({
			title : '设置组管理员',
			content : document.getElementById('searchAdminInGroup'),
			esc : true,
			height : '100px',
			width : '100px'
		});
	}
	function updateGroup() {  
			$("#updateGroupForm").submit();
	}
</script>
</head> 
<div id="breadcrumbs">
	<div style="overflow: hidden; position: relative; width: 750px;">
		<div>
			<ul style="width: 5000px;">
				<li class="first"></li>
				<li><a href="#">安全控制</a></li>
				<li><a href="#">组管理</a></li>
				<li class="last"><a href="#">更新组</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			创建新组<span class="hide"></span>
		</div>
		<div class="content"> 
			<s:form action="updateGroup" namespace="/security" method="get"
				id="createGroupForm">
				<div class="row">
					<div class="tableLeft">
						<label>组名称<span>*</span></label>
						<s:textfield name="group.name" id="thisgroupname" />
					</div>
					<div class="tableRight">
						<label>组描述:</label>
						<s:textfield name="group.description"
								id="thisgroupdescription" />
						<s:hidden name="group.id"/>
					</div> 
				</div>
					<%@include file="groupResourceInc.jsp"%>
					
				<div class="row">
				<input type="submit" value="更新"
							class="button orange" onclick="updateGroup()" />&nbsp;&nbsp;
					<button	type="reset" ><span>重置</span></button>
				</div> 
			</s:form> 
		</div>
		<div style="height: 40px"></div>
	</div>
</div>

</html>
