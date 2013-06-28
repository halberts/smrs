<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="security" uri="/security-tags" %>
<div class="box submenu">
	<div class="content">
		<ul>
			<li class="current">
				<a href="${dynamicURL}/jira/jiraCenter.action">SLA报告</a>
				<ul style="display:none">
				</ul>
				<a href="${dynamicURL}/jira/searchSystem.action">系统全业务展示</a>
				<ul style="display:none">
				</ul>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	//$("div.box.submenu ul>li>ul").hide();
	$("div.box.submenu ul li>a").click(function(){
		$("div.box.submenu ul>li>ul").hide();
		$(this).next('ul').toggle();
	});
	highlightLeftMenu();
});
</script>