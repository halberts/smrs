<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="security" uri="/security-tags" %>
<div class="box submenu">
	<div class="content">
		<ul> 
			<li><a href="${dynamicURL}/security/searchUser.action">用户管理</a></li>
			<li><a href="${dynamicURL}/security/createUserInit.action">创建用户</a></li>
			<li><a href="${dynamicURL}/security/viewUser.action">我的信息</a></li>
			<li><a href="${dynamicURL}/security/updatePasswordInit.action">修改密码</a></li>
			<li><a href="${dynamicURL}/security/searchGroup.action">用户组管理</a></li>
			<li><a href="${dynamicURL}/security/searchResource.action">功能查询</a></li>
			<!-- 
			<li><a href="${dynamicURL}/security/createResourceInit.action">创建资源</a></li>			
			<li><a href="${dynamicURL}/security/createRoleInit.action">创建角色</a></li>
			<li><a href="${dynamicURL}/security/searchRole.action">角色查询</a></li>
			 -->
		</ul>
	</div>
</div>