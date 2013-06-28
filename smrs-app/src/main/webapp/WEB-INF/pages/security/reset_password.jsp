<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码</title>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">用户管理</a></li>
		<li class="last"><a href="#">重置密码</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			修改密码
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="resetPassword" namespace="/security" method="post">
				<div class="row">
					<div class="tableLeft">
						<label>登录名</label>
						<label><s:property value="user.name"/></label>
					</div>
					<div class="tableRight">
						<label>用户姓名</label>
						<label><s:property value="user.nickName" /></label>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>新密码<span>*</span></label>
						<input type="password" name="newPassword" value="${newPassword}"/>
					</div>
					<div class="tableRight">
						<label>确认密码<span>*</span></label>
						<input type="password" name="confirmPassword" value="${confirmPassword}"/>
					</div>
				</div>
				<div class="row">
						<sj:submit value="修改" id="submit"
							targets="formResult" onCompleteTopics="handleResult" cssClass="button orange"/>
						<button type="reset"><span>重置</span></button>
				</div>
				<s:hidden name="userId"/>
				<s:hidden name="password" value="1111"/>
			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript">
	$.subscribe('handleResult',
		function(event, data) {
			handleErrors(event,data,{
				onFaild : function() {
					return;
				},
				onSuccess : function() {
					window.location.href = '${dynamicURL}/security/searchUser.action';
				}
		});
	});
</script>
</body>
</html>