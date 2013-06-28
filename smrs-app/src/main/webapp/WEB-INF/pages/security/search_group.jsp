<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询组信息</title>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">用户组管理</a></li>
		<li class="last"><a href="#">组查询</a></li>
	</ul></div></div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="searchGroup" namespace="/security" method="get" id="searchGroupForm">
				<table>
					<tr>
						<th>组名称：</th>
						<th><s:textfield name="group.name" /></th>
						<th>组描述：</th>
						<th><s:textfield name="group.description" /></th>
						<th>
							<button type="submit" class="orange"><span>查询</span></button>&nbsp;&nbsp;
							<button type="submit" class="blue" onclick="createGroup();"><span>新建</span></button>
						</th>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			查询结果
			<span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div>
				<table class="sort"> 
					<thead>
						<tr> 
							<th>组名称</th>
							<th>组描述</th> 
							<%-- <s:if test="#displayDeleteButton==true"> --%><th>操作</th><%-- </s:if> --%>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="pager.records" var="group" status="status">
							<tr>  
								<td nowrap="nowrap"><a href="${dynamicURL}/security/updateGroupInit.action?group.id=<s:property value='id'/>"><s:property value="name" /></a></td>
								<td nowrap="nowrap"><s:property value="description" /></td>
								<td><img title="删除" border="0" src="${staticURL}/images/delete.png"	onclick="delGroup(<s:property value='id'/>)" >
			  		                <img title="组内人员信息" border="0" src="${staticURL}/images/member.png" onclick="maintainUser(<s:property value='id'/>)" >
									<img title="组内功能信息" border="0" src="${staticURL}/images/function.png"  onclick="maintainFunction(<s:property value='id'/>)" >
								</td>
							</tr>
						</s:iterator>
					</tbody>
			</table>
			<p:pagination pager="pager" formId="searchGroupForm" theme="default"></p:pagination>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function createGroup(){
		document.getElementById('searchGroupForm').onsubmit=function(){return false;};
		window.location.href="${dynamicURL}/security/createGroupInit.action"
	}
	function maintainUser(id){ 
		window.location.href="${dynamicURL}/security/addUserToGroupInit.action?group.id="+id;
	}
	function maintainRole(id){ 
		window.location.href="${dynamicURL}/security/addRoleToGroupInit.action?group.id="+id;
	}
	function delGroup(id){
		if (!confirm("确定要删除吗？")){
			return;
		}
		window.location.href = "${dynamicURL}/security/deleteGroup.action?groupIds=" + id;
	}
	
	function addUserToGroupInit(id){ 
		window.location.href = "${dynamicURL}/security/addUserToGroupInit.action?groupIds=" + id;
	}
</script>
</body>
</html>