<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="col-1 pa"><strong id="mytime"></strong></div>
<div class="col-2">
	<ul>
		<!-- <li class="one">用戶管理</li> -->
		<li><a href="${dynamicURL}/security/searchUser.action">安全控制</a></li>
		<li><a href="${dynamicURL}/basic/searchDepartment.action">基础数据</a></li>
		<li><a href="${dynamicURL}/quartz/searchSchedulerState.action">任务监控</a>
		<li><a href="${dynamicURL}/quartz/memcached.action">缓存监控</a>
		<%-- <li><a href="${dynamicURL}/quartz/searchSchedulerState.action">缓存监控</a> --%>
		<li><a href="${dynamicURL}/quartz/searchLog.action">日志查看</a>
		<li><a href="${dynamicURL}/monitor/monitorCenter.action">监控中心</a>
	</ul>
</div>
<script type="text/javascript">
$(document).ready(function () {
	highlightTopMenu('<s:property value="#request['_module_']"/>');
});
function current_time(){
   var d, s = "";
   var c = ":";
   d = new Date();
   s += (d.getHours()>9?d.getHours():("0"+d.getHours())) + c;
   s += (d.getMinutes()>9?d.getMinutes():("0"+d.getMinutes())) + c;
   s += (d.getSeconds()>9?d.getSeconds():("0"+d.getSeconds()));
   document.getElementById("mytime").innerHTML=s;
}
window.setInterval(current_time, 1000);
</script>