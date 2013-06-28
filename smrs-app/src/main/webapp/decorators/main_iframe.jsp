<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<% 
	String module = ActionContext.getContext().getActionInvocation()
		.getProxy().getNamespace();
	if(module.startsWith("/")){
		module = module.substring(1,module.length());
	}
	request.setAttribute("_module_", module);
%>
<html>
<head>
    <sj:head compressed="true"/>
	<link rel="stylesheet" href="${staticURL}/css/style.css"/>
	<link rel="stylesheet" href="${staticURL}/css/forms.css"/>
	<link rel="stylesheet" href="${staticURL}/css/forms-btn.css"/>
	<link rel="stylesheet" href="${staticURL}/css/menu.css"/>
	<link rel="stylesheet" href="${staticURL}/css/style_text.css"/>
	<link rel="stylesheet" href="${staticURL}/css/datatables.css"/>
	<link rel="stylesheet" href="${staticURL}/css/fullcalendar.css"/>
	<link rel="stylesheet" href="${staticURL}/css/pirebox.css"/>
	<link rel="stylesheet" href="${staticURL}/css/modalwindow.css"/>
	<link rel="stylesheet" href="${staticURL}/css/statics.css"/>
	<link rel="stylesheet" href="${staticURL}/css/tabs-toggle.css"/>
	<link rel="stylesheet" href="${staticURL}/css/system-message.css"/>
	<link rel="stylesheet" href="${staticURL}/css/tooltip.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wizard.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wysiwyg.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wysiwyg.modal.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wysiwyg-editor.css"/>
	<link rel="stylesheet" href="${staticURL}/css/handheld.css"/>
	<link rel="stylesheet" type="text/css" href="${staticURL}/css/hop/grid/grid.css" />	
	<link rel="stylesheet" type="text/css" href="${staticURL}/css/hop/grid/custom-grid-style/jquery-ui-1.8.21.custom.css" />
	<%-- <script src="${staticURL}/js/jquery-1.7.1.min.js" type="text/javascript"></script> --%>
	<script src="${staticURL}/js/jquery.backgroundPosition.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.placeholder.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.ui.1.8.17.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.ui.select.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.ui.spinner.js" type="text/javascript"></script>
	<script src="${staticURL}/js/superfish.js" type="text/javascript"></script>
	<script src="${staticURL}/js/supersubs.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.datatables.js" type="text/javascript"></script>
	<script src="${staticURL}/js/fullcalendar.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.smartwizard-2.0.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/pirobox.extended.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.tipsy.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.elastic.source.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.jBreadCrumb.1.1.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.customInput.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.metadata.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.filestyle.mini.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.filter.input.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.flot.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.flot.pie.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.flot.resize.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.graphtable-0.2.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.wysiwyg.js" type="text/javascript"></script>
	<script src="${staticURL}/js/controls/wysiwyg.image.js" type="text/javascript"></script>
	<script src="${staticURL}/js/controls/wysiwyg.link.js" type="text/javascript"></script>
	<script src="${staticURL}/js/controls/wysiwyg.table.js" type="text/javascript"></script>
	<script src="${staticURL}/js/plugins/wysiwyg.rmFormat.js" type="text/javascript"></script>
	<script src="${staticURL}/js/costum.js" type="text/javascript"></script>
	<link rel="shortcut icon" href="${staticURL}/images/haier.ico"/>
	<script type="text/javascript" src="${staticURL}/scripts/hop.js"></script>
	<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
   	<decorator:head/>
   	<title><decorator:title default="海尔HOP监控系统"/></title>
   	<style>
   		#wrapper #container {
   			background:#FFFFFF;
   		}
   	</style>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/> >
	<div id="wrapper">
		<div id="container">
			<div id="top">
				<h1 id="logo"><label class="logoLabel">海尔HOP监控系统</label></h1>
				<div id="labels">
					<ul>
						<li><a class="user" href="${dynamicURL}/security/viewUser.action"><span class="bar">欢迎您 ,<s:property value="#session['_user_nick_name']"/></span></a></li>
						<li><a class="settings" href="${dynamicURL}/security/updatePasswordInit.action" title="修改密码"></a></li>
						<li class="subnav">
							<a class="messages" href="#"></a>
							<ul>
								<li><a href="#">New message</a></li>
								<li><a href="#">Inbox</a></li>
								<li><a href="#">Outbox</a></li>
								<li><a href="#">Trash</a></li>
							</ul>
						</li>
						<li><a class="logout" href="${dynamicURL}/security/logout.action" title="退出系统"></a></li>
					</ul>
				</div>
				<div id="menu">
					<ul class="sf-js-enabled"> 
						<li><a href="${dynamicURL}/monitor/searchComponents.action">首页</a></li> 
						<li><a href="${dynamicURL}/security/searchUser.action">安全控制</a></li> 
						<li><a href="${dynamicURL}/quartz/quartzMonitor.action">任务监控</a></li>
						<%-- <li><a href="${dynamicURL}/quartz/memcached.action">缓存监控</a></li> --%>
						<%-- <li><a href="${dynamicURL}/quartz/searchLog.action">日志查看</a></li> --%>
						<li><a href="${dynamicURL}/monitor/monitorCenter.action">系统监控</a></li>
						<li><a href="${dynamicURL}/jira/jiraCenter.action">项目监控</a></li>
						<li><a href="#">文件系统管理</a></li>
					</ul>
				</div>
			</div>
			
			<decorator:body/>
			
			<div id="footer">
				<div class="split">Copyright &copy;2012 海尔集团 版权所有</div>
				<div class="split right">Powered by <a title="海尔HOP框架" target="_blank" href="#">海尔集团流程系统创新部&nbsp;${hopInfo}</a></div>
			</div>
		</div>
	</div>
	<div class="piro_overlay" style="display: none; opacity: 0.5;"></div>
	<table cellspacing="0" cellpadding="0" class="piro_html ui-draggable" style="left: 424.5px; top: 1403.5px; display: none;"><tbody><tr><td class="h_t_l"></td><td title="drag me!!" class="h_t_c"></td><td class="h_t_r"></td></tr><tr><td class="h_c_l"></td><td class="h_c_c"><div title="close" class="piro_loader"><span></span></div><div class="resize"><div class="nav_container" style="display: none;"><a title="previous" class="piro_prev" href="#prev"></a><a title="next" class="piro_next" href="#next"></a><div class="piro_prev_fake">prev</div><div class="piro_next_fake">next</div><div title="close" class="piro_close"></div></div><div class="caption" style="display: none;"></div><div class="div_reg"></div></div></td><td class="h_c_r"></td></tr><tr><td class="h_b_l"></td><td class="h_b_c"></td><td class="h_b_r"></td></tr></tbody></table>
	<ul id="undefined-menu" aria-labelledby="undefined-button" role="listbox" aria-hidden="true" class="ui-selectmenu-menu ui-widget ui-widget-content ui-corner-bottom entries ui-selectmenu-menu-dropdown" aria-activedescendant="ui-selectmenu-item-281" style="left: 455.5px; top: 1427.2px;"><li role="presentation" style=""><a aria-selected="false" role="option" tabindex="-1" href="#">5</a></li><li role="presentation" class="ui-selectmenu-item-selected"><a aria-selected="true" role="option" tabindex="-1" href="#" id="ui-selectmenu-item-281">10</a></li><li role="presentation"><a aria-selected="false" role="option" tabindex="-1" href="#">25</a></li><li role="presentation"><a aria-selected="false" role="option" tabindex="-1" href="#">50</a></li><li role="presentation" class="ui-corner-bottom"><a aria-selected="false" role="option" tabindex="-1" href="#">100</a></li></ul>
<script src="${staticURL}/js/hop/hop.bootstrap.js" type="text/javascript"></script>
<script src="${staticURL}/js/hop/hop.core.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){ 
		highlightTopMenu('<%=request.getAttribute("_module_")%>');
	});
</script>
</body>
</html>
