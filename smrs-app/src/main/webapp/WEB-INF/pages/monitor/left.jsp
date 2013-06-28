<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="security" uri="/security-tags" %>
<div class="box submenu">
	<div class="content">
		<ul>
			<li class="current">
				<a href="#">基础数据维护</a>
				<ul style="display:none">
					<li><a href="${dynamicURL}/monitor/levelSetup.action">设定响应级别</a></li>
					<li><a href="${dynamicURL}/monitor/alarmSetup.action">设定告警阀值</a></li>
					<li><a href="${dynamicURL}/monitor/systemSupporter.action">设定系统负责人</a></li>
					<li><a href="${dynamicURL}/monitor/releaseTrack.action">设定上线追踪</a></li>
					<li><a href="${dynamicURL}/monitor/groupManage.action">业务分组</a></li>
				</ul>
				<a href="#">应用系统监控</a>
				<ul style="display:none">
					<li><a href="${dynamicURL}/monitor/monitorCenter.action">监控查询</a></li>
					<li><a href="${dynamicURL}/monitor/levelSearch.action">系统响应查询</a></li>
					<li><a href="${dynamicURL}/monitor/overtimeInquiry.action">超时查询</a></li>
					<li><a href="${dynamicURL}/monitor/exceptionInquiry.action">异常查询</a></li>
					<li><a href="${dynamicURL}/monitor/apiInquiry.action">API频次查询</a></li>
					<li><a href="${dynamicURL}/monitor/apiInquiryDaily.action">API频次—24小时查询</a></li>
					<li><a href="${dynamicURL}/monitor/healthyStatus.action">服务器健康状况</a></li>
					<li><a href="${dynamicURL}/monitor/searchProfileInit.action">Action监控<font color='red'>(新)</font></a></li>
				</ul>
				<a href="#">综合监控报告</a>
				<ul style="display:none">
					<li><a href="${dynamicURL}/monitor/appSummaryInit.action">应用统计信息<font color='red'>(新)</font></a></li>
					<li><a href="${dynamicURL}/monitor/levelCompare.action">响应级别横向比较</a></li>
					<li><a href="${dynamicURL}/monitor/levelLocate.action">应用API级别分布</a></li>
					<li><a href="${dynamicURL}/monitor/overtimeTopChart.action">全业务超时比较</a></li>
					<li><a href="${dynamicURL}/monitor/appCallSummary.action">全业务调用汇总</a></li>
					<li><a href="${dynamicURL}/monitor/getActionAverageTimeInit.action">Action平均响应时间</a></li>
					<li><a href="${dynamicURL}/monitor/appExceptionSummary.action">全业务异常汇总</a></li>
					<li><a href="${dynamicURL}/monitor/groupSummary.action">单业务分组汇总</a></li>
				</ul>
				<a href="#">消息监控</a>
				<ul style="display:none">
					<li><a href="${dynamicURL}/monitor/smsInquiry.action">短信监控</a></li>
					<li><a href="${dynamicURL}/monitor/emailInquiry.action">邮件监控</a></li>
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