<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
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
    <%@ include file="/common/meta.jsp" %>
    <title><decorator:title default="海尔开放平台-showcase系统"/></title>
    <jsp:include page="/common/css_and_js.jsp" />
    
    <script src="${staticURL}/scripts/monitor.js"></script>
    <link rel="stylesheet" type="text/css" href="${staticURL}/style/monitor/contentbox.css" />
    <link rel="stylesheet" type="text/css" href="${staticURL}/style/monitor/notification.css" />
    <link rel="stylesheet" type="text/css" href="${staticURL}/style/monitor/style.css" />
    <style>
    
    #clickDiv {
		z-index: 1;
		cursor: pointer;
		border: 1px solid #F6F6F6; 
		width: 13px; 
		height: 13px; 
		background-color: #CCDDFF;
		float: left;
	}
    
    </style>
    
    <script>
    

    /**
     * 添加到结果列表（类似购物车）
     */
    function addToMart(container, name, id) {
    	/**
    	 * 防止重复加入
    	 */
    	var lis = container.find('li');
    	var isExist = false;
    	$.each(lis, function(){
    		var nm = $(this).text();
    		if(nm == name) {
    			isExist = true;
    			return false;
    		}
    	});
    	
    	if(isExist == true) {
    		return ;
    	}
    	
    	
    	/**
    	 * 加入到结果列表
    	 */
    	var li = $("<li class='selectlist-item'></li>").appendTo(container);
    	li.text(name);
    	li.attr('id', id);
    	li.click(function(){
    		
    		$(this).fadeTo(400, 0, function(){
    			$(this).remove();
    		});
    		
    	});
    }

    
    
  	//高亮头部导航栏按钮（重写）
    function highlightTopMenu(namespace){
    	var fullUrl = window.location.href;
    	var url = formatURL(fullUrl);
    	//1.进行url完全匹配
    	var hightlight = false;
    	$(".col-2 li a").each(function(i){
    		var href = formatURL(this.href);
    		if(href==url){
    			$(this).addClass("one");
    			hightlight = true;
    		}
    	});
    	//2.进行namespace匹配
    	if(!hightlight){
    		$(".col-2 li>a").each(function(i){
    			var ns = namespace + '/monitor';
    			if(this.href.indexOf("/"+ns+"/")!=-1){
    				$(this).addClass("one");
    			}
    		});
    	}
    }
    
    
    /**
     * 生成导航条项目
     */
    function addBreadCrumb(menus){
    	
    	var nav = $(".nav_outer");
    	nav.empty();
    	
    	var fa = $("<a class='nav_new' href='${dynamicURL}/monitor/monitorCenter.action'>&nbsp;&nbsp;&nbsp;监控中心</a>&nbsp;>>").appendTo(nav);
    	
    	for(var i = 0; i < menus.length; i++){
    		if(i != menus.length-1) {
    			$("<a class='nav_new' href='#'>"+ menus[i] +"</a>" ).appendTo(nav);
    			$("<span>&nbsp;&gt;&gt;</span>").appendTo(nav);
    			
    		} else {
    			$("<span>"+ menus[i] +"</span>" ).appendTo(nav);
    		}
    		
    	}
    	
    }
    
    $(document).ready(function(){
		$(".notification .close img").attr('src', '${staticURL}/style/monitor/images/cross_grey_small.png');
	
		$("#sidebarNav h2").click(function(){
			var subitems = $(this).next(); 
			subitems.toggle();
			if(subitems.is(":hidden")) {
				$(this).css({'background': 'transparent url(${staticURL}/style/monitor/images/arrow-right.png) no-repeat right center'});	
			} else {
				$(this).css({'background': 'transparent url(${staticURL}/style/monitor/images/arrow-down.png) no-repeat right center'});
			}
			
		});
		
	 	$("#clickDiv").hover(function(){
    		$(this).css({"background-color": "#FFCC66"});
    	}, function(){
    		$(this).css({"background-color": "#CCDDFF"});
    	});
	 	
	 	
	 	$(".nav_outer").dblclick(function(){
	 		$("#clickDiv").trigger('click');
	 	});
	 	
    	$("#clickDiv").toggle(function(){
    		$("#headerNav").hide();
    		$("#sidebarNav").hide();
    		$(".header").hide();
    		$("#bdwrapper").css({
    			'left': '-178px',
    			'top': '-10px'
    		});
    		$("#rightWrapper").width('87%');
    		
    		
    	}, function(){
    		$("#headerNav").show();
    		$("#sidebarNav").show();
    		$(".header").show();
    		$("#bdwrapper").css({
    			'left': '0px',
    			'top': '76px'
    		});
    		$("#rightWrapper").width('86%');
    		
    	});
		
    	
    });
    

    </script>
    
   	<decorator:head/>
    </head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/> >
     <div class="wp">
     	  <!-- 公共头部 -->
		  <div class="hd pr header">
		  	<jsp:include page="/common/header_monitor.jsp"/>
		  </div>
		 
		 <div id='headerNav'>
		 	<jsp:include page="/common/navigation.jsp" />
		 </div>
		 
		 <div id='bdwrapper'>
			
		  <div id='allcontent'>
		  	<div id='leftWrapper'>
		  		<div id='sidebarNav'>
		  			<h2>基础数据维护</h2>
		  			<ul>
		  				<li><a href="${dynamicURL}/monitor/levelSetup.action">设定响应级别</a></li>
						<li><a href="${dynamicURL}/monitor/alarmSetup.action">设定告警阀值</a></li>
						<li><a href="${dynamicURL}/monitor/systemSupporter.action">设定系统负责人</a></li>
						<li><a href="${dynamicURL}/monitor/releaseTrack.action">设定上线追踪</a></li>
						<li><a href="${dynamicURL}/monitor/groupManage.action">业务分组</a></li>
		  			</ul>
		  			
		  			<h2>应用系统监控</h2>
		  			<ul>
		  				<li><a href="${dynamicURL}/monitor/levelSearch.action">系统响应查询</a></li>
						<li><a href="${dynamicURL}/monitor/overtimeInquiry.action">超时查询</a></li>
						<li><a href="${dynamicURL}/monitor/exceptionInquiry.action">异常查询</a></li>
						<li><a href="${dynamicURL}/monitor/apiInquiry.action">API频次查询</a></li>
						<li><a href="${dynamicURL}/monitor/apiInquiryDaily.action">API频次—24小时查询</a></li>
		  			</ul>
		  			
		  			<h2>综合监控报告</h2>
		  			<ul>
		  				<li><a href="${dynamicURL}/monitor/levelCompare.action">响应级别横向比较</a></li>
						<li><a href="${dynamicURL}/monitor/overtimeTopChart.action">全业务超时比较</a></li>
						<li><a href="${dynamicURL}/monitor/appCallSummary.action">全业务调用汇总</a></li>
						<li><a href="${dynamicURL}/monitor/appExceptionSummary.action">全业务异常汇总</a></li>
						<li><a href="${dynamicURL}/monitor/groupSummary.action">单业务分组汇总</a></li>
		  			</ul>
		  			
		  			<h2>消息监控</h2>
		  			<ul>
		  				<li><a href="${dynamicURL}/monitor/smsInquiry.action">短信监控</a></li>
						<li><a href="${dynamicURL}/monitor/emailInquiry.action">邮件监控</a></li>
		  			</ul>
		  		</div>
		  	</div>
		  	
		  	<div id='rightWrapper'>
		  		 <div id='clickDiv'>
		  		 </div> 
		  		<div class="nav_outer">
		  		</div>
		  		
		  		<div id='rightContent'>
		  			<decorator:body/>
		  			<br /><br />
		  		</div>
		  	
	  			<div id='footer'>
	  				<span>Copyright © 2012 海尔集团版权所有</span> 
	  			</div>
	  		</div>
	  			
		  </div>
		 </div> 
		  
	  	</div>
</body>
</html>
