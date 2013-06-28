<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta name="modulePath" content="security/"/>
</head>
<body>
<br/>
<h2>查看用户信息</h2>
<table id="rounded-corner">
	<tr>
		<th width="150">用户ID：</th>
		<td><s:property value="user.id"/></td>
	</tr>
	<tr>
		<th>用户名：</th>
		<td><s:property value="user.name"/></td>
	</tr>
	<tr>
		<th>密码：</th>
		<td>******</td>
	</tr>
	<tr>
		<th>邮箱：</th>
		<td><s:property value="user.email"/></td>
	</tr>
	<tr>
		<th>状态：</th>
		<td><s:property value="user.status"/></td>
	</tr>
	<tr>
		<th>用户类型：</th>
		<td><s:property value="user.type"/></td>
	</tr>
	<tr>
		<th>创建者：</th>
		<td><s:property value="user.createBy"/></td>
	</tr>
	<tr>
		<th>创建时间：</th>
		<td><s:date name="user.gmtCreate" format="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<th>最后修改时间：</th>
		<td><s:date name="user.gmtModified" format="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<th>创最后修改者：</th>
		<td><s:property value="user.lastModifiedBy"/></td>
	</tr>
</table>
</body>
</html>