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
    <sj:head locale='zh_CN' jqueryui="true" compressed="true"/> 
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
   	<script>
   	/**
	   	$(document).ready(function(){
	   		
			 * 最大化时重新设定表格宽度
			 
			var flag = true;
		 	$(".hide-btn").click(function(){
		 		var w = $("#right").width();
		 		if(flag){
		 			grid.setGridWidth(w*1.16);
		 			flag = false;
		 		}else{
		 			grid.setGridWidth(w*0.76);
		 			flag = true;
		 		}
			}); 
	   	});*/
	   	/**
	   		隐藏或者显示错误信息和成功信息
	   	*/
	   	var hideOrShowRedAndGreen = function(cls){
	   		$(".message.red").hide();
	   		$(".message.green").hide();
	   		if(cls != ''){
	   			$(".message."+cls).show();
	   		}
	   	}
   	</script>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/> >
	<div id="wrapper">
		<div id="container">
	
			<div original-title="Close sidebar" class="hide-btn top tip-s"></div>
			<div original-title="Close sidebar" class="hide-btn center tip-s"></div>
			<div original-title="Close sidebar" class="hide-btn bottom tip-s"></div>
			
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
<%-- 						<li class="">
							<a href="${dynamicURL}/basic/searchDepartment.action">基础数据</a>
							<ul style="float: none; width: 15em; display: none; visibility: hidden;" class="sf-js-enabled"> 
								<li style="white-space: normal; float: left; width: 100%;"><a href="#" style="float: none; width: auto;">First item</a></li>
								<li class="current" style="white-space: normal; float: left; width: 100%;">
									<a href="#" style="float: none; width: auto;">Second item</a>
									<ul style="left: 15em; float: none; width: 15em; display: none; visibility: hidden;" class="sf-js-enabled"> 
										<li style="white-space: normal; float: left; width: 100%;"><a href="#" style="float: none; width: auto;">First item</a></li>
										<li class="current" style="white-space: normal; float: left; width: 100%;"><a href="#" style="float: none; width: auto;">Second item</a></li>
										<li style="white-space: normal; float: left; width: 100%;"><a href="#" style="float: none; width: auto;">Third item</a></li>
										<li style="white-space: normal; float: left; width: 100%;"><a href="#" style="float: none; width: auto;">Fourth item</a></li>
									</ul> 
								</li>
								<li style="white-space: normal; float: left; width: 100%;"><a href="#" style="float: none; width: auto;">Third item</a></li>
								<li style="white-space: normal; float: left; width: 100%;"><a href="#" style="float: none; width: auto;">Fourth item</a></li>
							</ul>
						</li> --%>
						<li><a href="${dynamicURL}/goods/searchGoodsAttribute.action">商品管理</a></li>
						<li><a href="${dynamicURL}/basicdata/searchRegion.action">基础数据管理</a></li>
						<li><a href="${dynamicURL}/relationship/searchCustomer.action">客户供应商管理</a></li>
						<li><a href="${dynamicURL}/inventory/searchInventory.action">库存管理</a></li>
						<li><a href="${dynamicURL}/fileSystem/fileSystem.action">文件系统管理</a></li>
						<li><a href="${dynamicURL}/logtrace/searchLogTrace.action">日志管理</a></li>
					</ul>
				</div>
			</div>
			
			<div id="left">
				<!-- <div class="box submenu">
					<div class="content">
						<ul>
							<li><a href="#">应用系统监控</a></li>
							<li class="current">
								<a href="#">应用系统监控</a>
								<ul>
									<li><a href="#">应用系统监控</a></li>
									<li class="current"><a href="#">应用系统监控</a></li>
									<li><a href="#">应用系统监控</a></li>
									<li><a href="#">应用系统监控</a></li>
								</ul>
							</li>
							<li><a href="#">应用系统监控</a></li>
							<li><a href="#">应用系统监控</a></li>
						</ul>
					</div>
				</div> -->
				
	  			<decorator:usePage id="thePage" /> 
				<s:action name="staticLeftMenuAction" namespace="/" executeResult="true">
					<s:param name="namespace" value="#request['_module_']"></s:param>
				</s:action>

				<%-- <div class="box submenu">
					<div class="content">
						<ul>
							<li><a href="${dynamicURL}/security/searchUser.action">用户管理</a></li>
							<li><a href="${dynamicURL}/security/createUserInit.action">创建用户</a></li>
							<li><a href="${dynamicURL}/security/viewUser.action">我的信息</a></li>
							<li><a href="${dynamicURL}/security/updatePasswordInit.action">修改密码</a></li>
							<li><a href="${dynamicURL}/security/createResourceInit.action">创建资源</a></li>
							<li><a href="${dynamicURL}/security/createResourceInit.action">创建资源</a></li>
							<li></s><a href="${dynamicURL}/security/searchResource.action">资源查询</a></li>
						</ul>
					</div>
				</div> --%>

				<%-- <div class="box togglemenu">
					<div class="content">
						<ul class="ui-accordion ui-widget ui-helper-reset ui-accordion-icons" role="tablist">
							<li class="ui-accordion-li-fix"><h2>Toggle Menu</h2></li>
							<li class="title ui-accordion-li-fix ui-accordion-header ui-helper-reset ui-state-default ui-state-active ui-corner-top" role="tab" aria-expanded="true" aria-selected="true" tabindex="0"><span class="ui-icon ui-icon-triangle-1-s"></span>Toggle item 01</li>
							<li class="content ui-accordion-li-fix ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active" role="tabpanel">
								Aenean vulputate condime pellent
								que. Sed ornare semper purus
								sollicitudin. Vivamus nisi dui,
								pharetra ac condimentum id.
							</li>
							<li class="title ui-accordion-li-fix ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" role="tab" aria-expanded="false" aria-selected="false" tabindex="-1"><span class="ui-icon ui-icon-triangle-1-e"></span>Toggle item 02</li>
							<li class="content ui-accordion-li-fix ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" role="tabpanel" style="display: none;">
								Aenean vulputate condime pellent
								que. Sed ornare semper purus
								sollicitudin. Vivamus nisi dui,
								pharetra ac condimentum id.
							</li>
							<li class="title ui-accordion-li-fix ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" role="tab" aria-expanded="false" aria-selected="false" tabindex="-1"><span class="ui-icon ui-icon-triangle-1-e"></span>Toggle item 03</li>
							<li class="content ui-accordion-li-fix ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" role="tabpanel" style="display: none;">
								Aenean vulputate condime pellent
								que. Sed ornare semper purus
								sollicitudin. Vivamus nisi dui,
								pharetra ac condimentum id.
							</li>
						</ul>
					</div>
				</div> --%>
			</div>
			
			<div id="right">
			
				<%-- <div class="btn-box">
					<div class="content">
						<a class="item" href="#">
							<img alt="Dashboard" src="${staticURL}/gfx/icons/big/dashboard.png">
							<span>首页</span>
							Back to the dashboard
						</a>
						
						<a class="item" href="#">
							<img alt="Messages" src="${staticURL}/gfx/icons/big/messages.png">
							<span>Messages</span>
							Go to your inbox
						</a>
						
						<a class="item" href="#">
							<img alt="Settings" src="${staticURL}/gfx/icons/big/settings.png">
							<span>Settings</span>
							Change your settings
						</a>
						
						<a class="item" href="#">
							<img alt="Support" src="${staticURL}/gfx/icons/big/support.png">
							<sp${staticURL}/gfxSupport</span>
							Need some support?
						</a>
						
						<a class="item" href="#">
							<img alt="Statics" src="${staticURL}/gfx/icons/big/statics.png">
							<span>Statics</span>
							See your statistics
						</a>
						
					</div>
				</div> --%>
			
				<%-- <div class="section">
					<div class="box">
						<div class="title">
							Chart
							<span class="hide"></span>
						</div>
						<div class="content">
							<div class="chart-caption">Statistics February</div> 
							<div class="space"></div><div class="flot-graph" style="width: 100%; height: 200px; padding: 0px; position: relative;"><canvas class="base" width="668" height="200"></canvas><canvas class="overlay" width="668" height="200" style="position: absolute; left: 0px; top: 0px;"></canvas><div style="font-size:smaller" class="tickLabels"><div style="color:#545454" class="xAxis x1Axis"><div style="position:absolute;text-align:center;left:14px;top:193px;width:21px" class="tickLabel">1</div><div style="position:absolute;text-align:center;left:35px;top:193px;width:21px" class="tickLabel">2</div><div style="position:absolute;text-align:center;left:56px;top:193px;width:21px" class="tickLabel">3</div><div style="position:absolute;text-align:center;left:78px;top:193px;width:21px" class="tickLabel">4</div><div style="position:absolute;text-align:center;left:99px;top:193px;width:21px" class="tickLabel">5</div><div style="position:absolute;text-align:center;left:120px;top:193px;width:21px" class="tickLabel">6</div><div style="position:absolute;text-align:center;left:142px;top:193px;width:21px" class="tickLabel">7</div><div style="position:absolute;text-align:center;left:163px;top:193px;width:21px" class="tickLabel">8</div><div style="position:absolute;text-align:center;left:184px;top:193px;width:21px" class="tickLabel">9</div><div style="position:absolute;text-align:center;left:206px;top:193px;width:21px" class="tickLabel">10</div><div style="position:absolute;text-align:center;left:227px;top:193px;width:21px" class="tickLabel">11</div><div style="position:absolute;text-align:center;left:248px;top:193px;width:21px" class="tickLabel">12</div><div style="position:absolute;text-align:center;left:270px;top:193px;width:21px" class="tickLabel">13</div><div style="position:absolute;text-align:center;left:291px;top:193px;width:21px" class="tickLabel">14</div><div style="position:absolute;text-align:center;left:312px;top:193px;width:21px" class="tickLabel">15</div><div style="position:absolute;text-align:center;left:334px;top:193px;width:21px" class="tickLabel">16</div><div style="position:absolute;text-align:center;left:355px;top:193px;width:21px" class="tickLabel">17</div><div style="position:absolute;text-align:center;left:376px;top:193px;width:21px" class="tickLabel">18</div><div style="position:absolute;text-align:center;left:398px;top:193px;width:21px" class="tickLabel">19</div><div style="position:absolute;text-align:center;left:419px;top:193px;width:21px" class="tickLabel">20</div><div style="position:absolute;text-align:center;left:440px;top:193px;width:21px" class="tickLabel">21</div><div style="position:absolute;text-align:center;left:462px;top:193px;width:21px" class="tickLabel">22</div><div style="position:absolute;text-align:center;left:483px;top:193px;width:21px" class="tickLabel">23</div><div style="position:absolute;text-align:center;left:504px;top:193px;width:21px" class="tickLabel">24</div><div style="position:absolute;text-align:center;left:526px;top:193px;width:21px" class="tickLabel">25</div><div style="position:absolute;text-align:center;left:547px;top:193px;width:21px" class="tickLabel">26</div><div style="position:absolute;text-align:center;left:568px;top:193px;width:21px" class="tickLabel">27</div><div style="position:absolute;text-align:center;left:590px;top:193px;width:21px" class="tickLabel">28</div><div style="position:absolute;text-align:center;left:611px;top:193px;width:21px" class="tickLabel">29</div><div style="position:absolute;text-align:center;left:632px;top:193px;width:21px" class="tickLabel">30</div><div style="position:absolute;text-align:center;left:654px;top:193px;width:21px" class="tickLabel">31</div></div><div style="color:#545454" class="yAxis y1Axis"><div style="position:absolute;text-align:right;top:181px;right:650px;width:18px" class="tickLabel">0</div><div style="position:absolute;text-align:right;top:154px;right:650px;width:18px" class="tickLabel">50</div><div style="position:absolute;text-align:right;top:128px;right:650px;width:18px" class="tickLabel">100</div><div style="position:absolute;text-align:right;top:102px;right:650px;width:18px" class="tickLabel">150</div><div style="position:absolute;text-align:right;top:76px;right:650px;width:18px" class="tickLabel">200</div><div style="position:absolute;text-align:right;top:50px;right:650px;width:18px" class="tickLabel">250</div><div style="position:absolute;text-align:right;top:24px;right:650px;width:18px" class="tickLabel">300</div><div style="position:absolute;text-align:right;top:-2px;right:650px;width:18px" class="tickLabel">350</div></div></div><div class="legend"><table style="position:absolute;top:4px;right:4px;; margin:-27px 0 0 -5px;font-size:11px;color:#545454"><tbody><tr><td class="legendColorBox"><div style="border:1px solid none;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(225, 0, 0);overflow:hidden"></div></div></td><td class="legendLabel">unique visitors</td><td class="legendColorBox"><div style="border:1px solid none;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(0, 0, 0);overflow:hidden"></div></div></td><td class="legendLabel">pageviews</td></tr></tbody></table></div></div>
						</div>
					</div>
				</div> --%>
	
				<%-- <div class="section">
					<div class="half">
						<div class="box">
							<div class="title">
								Recent comments
								<span class="hide"></span>
							</div>
							<div class="content">
								<ul class="comments">
									<li>
										<a href="#"><img alt="User" src="${staticURL}/gfx/user-photo.gif"></a>
										<a href="#"><b>John do</b></a> says:<br>
										Vestibulum at metus mauris, sed iaculis. 
										Vestibulum hendrerit mattis blandit. 
									</li>
									<li>
										<a href="#"><img alt="User" src="${staticURL}/gfx/user-photo.gif"></a>
										<a href="#"><b>John do</b></a> says:<br>
										Vestibulum at metus mauris, sed iaculis. 
										Vestibulum hendrerit mattis blandit. 
									</li>
									<li>
										<a href="#"><img alt="User" src="${staticURL}/gfx/user-photo.gif"></a>
										<a href="#"><b>John do</b></a> says:<br>
										Vestibulum at metus mauris, sed iaculis. 
										Vestibulum hendrerit mattis blandit. 
									</li>
									<li>
										<a href="#"><img alt="User" src="${staticURL}/gfx/user-photo.gif"></a>
										<a href="#"><b>John do</b></a> says:<br>
										Vestibulum at metus mauris, sed iaculis. 
										Vestibulum hendrerit mattis blandit. 
									</li>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="half">
						<div class="box">
							<div class="title">
								Quick page insert
								<span class="hide"></span>
							</div>
							<div class="content">
								<form action="">
									<div class="row">
										<label>Title</label>
										<div class="right"><input type="text" value=""></div>
									</div>
									<div class="row">
										<label>Your tags</label>
										<div class="right"><input type="text" value=""></div>
									</div>
									<div class="row">
										<label>Page content</label>
										<div class="right"><textarea style="height : 144px;" cols="" rows=""></textarea></div>
									</div>
									<div class="row">
										<label></label>
										<div class="right"><button type="submit"><span>Sumbit</span></button></div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div> --%>
				<decorator:body/>
			</div>
			
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
