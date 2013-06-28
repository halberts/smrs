<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="logo pa">
  <a href="" style="float:left;"><img src="${staticURL}/images/logo.jpg" alt="" /></a>
  <div class="sysName">监控中心</div>
</div>
<div class="msg pa" style="width:auto; text-align:right; top:5px; line-height:18px;">
	
	
	 <a style="color: #0066FF;" href="${dynamicURL}/security/updatePasswordInit.action">[修改密码]</a>&nbsp;&nbsp;
	 <a style="color: #0066FF;" href="${dynamicURL}/security/logout.action" >[注销]</a>
	 <br />
	 <span>
	您好，<a href="${dynamicURL}/security/viewUser.action" title="我的信息"><span style="color: #0066FF;"><s:property value="#session['_user_nick_name']"/></span></a> &nbsp;欢迎来到海尔信息门户   &nbsp; 您上次登录IP：<span style="color: #0066FF;"><s:property value="#session['_user_last_login_ip']"/></span>
	</span>
</div>