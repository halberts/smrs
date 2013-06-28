<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/pagination-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限列表</title>
<meta content="security/" name="modulePath">
</head>
<body>
<br/>
<h2>权限列表</h2>
<div class="form">
	<s:form action="searchPermission" namespace="/security" method="get" id="searchPermissionForm">
        <fieldset>
            <dl>
                <dt><label for="email">权限名:</label></dt>
                <dd><s:textfield name="searchModel.permission.name" size="54" /></dd>
            </dl>
            <dl>
                <dt><label for="email">权限描述:</label></dt>
                <dd><s:textfield name="searchModel.permission.description" size="54" /></dd>
            </dl>
             <dl class="submit">
            	<dd><input type="submit" value="查询" ></dd>
             </dl>
        </fieldset>
	</s:form>
</div>
<table id="rounded-corner">
	<thead>
		<tr>
			<th class="rounded-company"></th>
			<th class="rounded">权限名</th>
			<th class="rounded">权限码</th>
			<th class="rounded">描述</th>
			<th class="rounded">创建时间</th>
			<th class="rounded">创建者</th>
			<th class="rounded">最后修改时间</th>
			<th class="rounded">最后修者</th>
			<th class="rounded">操作</th>
		</tr>
	</thead>
	<tbody>
	<s:iterator value="searchResult.results" var="user" status="status">
	<tr>
		<td><input type="checkbox" name="userId"></td>
		<td><a href='${dynamicURL}/updateUserInit.action?user.id=<s:property value="id"/>'/><s:property value="name"/></a></td>
		<td><s:property value="code"/></td>
		<td><s:property value="description"/></td>
		<td><s:property value="gmtCreate"/></td>
		<td><s:property value="createBy"/></td>
		<td><s:property value="gmtModified"/></td>
		<td><s:property value="lastModifiedBy"/></td>
		<td><a href="${dynamicURL}/user/deleteUser.action?userId=<s:property value="id"/>"><img border="0" title="" alt="" src="/css/mytheme/images/trash.png"></a></td>
	</tr>
	</s:iterator>
	</tbody>
</table>
<p:pagination pager="pager" formId="searchPermissionForm"></p:pagination> 
<a class="bt_red" href="#"><span class="bt_red_lft"></span><strong>删除权限</strong><span class="bt_red_r"></span></a>
<a class="bt_green" href="${dynamicURL}/security/createUserInit.action"><span class="bt_green_lft"></span><strong>创建权限</strong><span class="bt_green_r"></span></a>
</body>
</html>