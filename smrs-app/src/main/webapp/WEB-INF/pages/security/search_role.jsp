<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/pagination-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色查询</title>
<meta content="security/" name="modulePath">
<script type="text/javascript">
function doSearch(){
	var url = "${dynamicURL}/security/searchRole.action";
	document.forms[0].action = url;
    document.forms[0].submit();
}
function doDel(roleId){
	if(!window.confirm("确定要删除吗？")){
		return;
	}
	var url = "${dynamicURL}/security/deleteRole.action?roleId="+roleId;
	$("#roleId").val(roleId);
	document.forms[0].action = url;
    document.forms[0].submit();
}
function createRole(){
	var url = '${dynamicURL}/security/createRoleInit.action';
	window.location.href=url;
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">角色管理</a></li>
		<li class="last"><a href="#">角色查询</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="searchRole" namespace="/security" method="get" id="searchRoleForm">
				<table>
					<tr>
						<th>角色名称：</th>
						<th><s:textfield name="role.name" /></th>
						<th>角色描述：</th>
						<th><s:textfield name="role.description" /></th>
						<th>
							<button type="submit" class="orange" onclick="doSearch();"><span>查询</span></button>&nbsp;&nbsp;
							<button type="submit" class="blue" onclick="createRole()"><span>新建</span></button>
						</th>
					</tr>
				</table>
				<input type="hidden" name="roleId" id="roleId"/> 
			</s:form>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			资源列表
			<span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div>
				<table class="sorting"> 
					<thead>
						<tr>
							<th>角色名</th>
							<th>描述</th>
							<th>创建者</th>
							<th>创建时间</th>
							<th>最后修改者</th>
							<th>最后修改时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="pager.records" var="resource" status="status">
							<tr>
								<td><a href="${dynamicURL}/security/updateRoleInit.action?roleId=<s:property value="id"/>"/><s:property value="name"/></a></td>
								<td><s:property value="description"/></td>
								<td><s:property value="createBy"/></td>
								<td><s:date name="gmtCreate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td><s:property value="lastModifiedBy"/></td>
								<td><s:date name="gmtModified" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td><a href="#"><img border="0" title="" alt="" src="${staticURL}/images/trash.png" onclick="doDel('<s:property value="id"/>')"></a></td>
							</tr>
						</s:iterator>
					</tbody>
			</table>
			<p:pagination pager="pager" formId="searchRoleForm" theme="default"></p:pagination>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function createRole(){
		document.getElementById('searchRoleForm').onsubmit=function(){return false;};
		window.location.href="${dynamicURL}/security/createRoleInit.action"
	}
</script>
</body>
</html>