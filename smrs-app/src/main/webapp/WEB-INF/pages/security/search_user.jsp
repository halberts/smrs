<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="security" uri="/security-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户查询</title>
</head>
<body> 
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">用户管理</a></li>
		<li class="last"><a href="#">用户查询</a></li>
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
			<s:form action="searchUser" namespace="/security" method="get" id="searchUserForm">
				<table>
					<tr>
						<th>登录名：</th>
						<th><s:textfield name="user.name" /></th>
						<th>用戶名：</th>
						<th><s:textfield name="user.nickName" /></th>
						<th>Email：</th>
						<th><s:textfield name="user.email" /></th>
						<th>
							<button type="submit" class="orange" onclick="submitForm('searchUser');"><span>查询</span></button>
							<button type="submit" class="blue" onclick="creatUser()"><span>新建</span></button>
							<button type="submit" class="green" onclick="submitForm('exportUserList')"><span>导出</span></button>
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
			<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div><table class="sorting"> 
				<thead>
					<tr>
						<th>登录名</th>
						<th>用户名</th>   
						<th>邮箱</th>
						<th>关联门店</th> 
						<th>状态</th>
						<th>最近登陆IP</th>
						<th>最近登陆时间</th>
						<th>操作</th>
					</tr>
				</thead>
				
			<tbody>
					<!-- 数据行 -->
					<s:iterator value="pager.records" var="user" status="status">
						<tr>
							<td ><a href='${dynamicURL}/security/updateUserInit.action?user.id=<s:property value="id"/>'><s:property value="name" /></a></td>
							<td><s:property	value="trueName" /></td>
							<td><s:property value="email" /></td>
							<td><s:iterator value="stores" var="st">
									<s:property value="#st.name" />&nbsp;
							    </s:iterator>
							</td>
							<%-- <td style="font-size: 12px;" nowrap="nowrap"><s:property value="@com.haier.openplatform.console.security.domain.enu.UserTypeEnum@toEnum(type).description"/></td> --%>
							<td><s:property value="@com.smrs.enums.StatusEnum@toEnum(status).name" /></td>
							<td><s:property value="currentLoginIp" /></td>
							<td><s:date name="lastLoginTime" format="yyyy-MM-dd HH:mm:ss" /></td>
							<td>
								<img title="删除" border="0" src="${staticURL}/images/trash.png"
										onclick="delUser(<s:property value="id"/>)">
								<a title="重置密码"
										href="${dynamicURL}/security/resetPasswordInit.action?userId=<s:property value='id'/>"><img
										border="0" src="${staticURL}/images/user.gif" /></a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
				</table>
				<p:pagination pager="pager" formId="searchUserForm" theme="default"></p:pagination>
				</div>
		</div>
	</div>
</div>

	<script type="text/javascript">
//删除
function delUser(id){
	if (!confirm("确定要删除吗？")){
		return;
	}
	window.location.href = "${dynamicURL}/security/deleteUser.action?userId="+id;
}
function creatUser(){
	document.getElementById('searchUserForm').onsubmit=function(){return false;};
	window.location.href = "${dynamicURL}/security/createUserInit.action";
}
function submitForm(action){
	var form = document.getElementById("searchUserForm");
	form.action="${dynamicURL}/security/"+action+".action";
	form.submit();
}
</script>
</body>
</html>
