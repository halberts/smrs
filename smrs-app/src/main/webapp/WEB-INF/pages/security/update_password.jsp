<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">用户管理</a></li>
		<li class="last"><a href="#">修改密码</a></li>
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
			<s:form action="updatePassword" namespace="/security" method="post" >
				<div class="row">
					<label>旧密码</label>
					<div class="right"><input type="password" name="password" value="${password}"/></div>
				</div>
				<div class="row">
					<label>新密码</label>
					<div class="right"><input type="password" name="newPassword" value="${newPassword}"/></div>
				</div>
				<div class="row">
					<label>确认密码</label>
					<div class="right"><input type="password" name="confirmPassword" value="${confirmPassword}"/></div>
				</div>
				<div class="row">
					<div class="right">
						<button type="submit"><span>提交</span></button>
						<button type="reset"><span>重置</span></button>
					</div>
				</div>
			</s:form>
		</div>
	</div>
</div>
</body>
</html>