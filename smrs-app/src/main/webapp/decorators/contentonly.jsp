<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<html>
    <head>
        <%@ include file="/common/meta.jsp" %> 
        <decorator:head/> 
    	<jsp:include page="/common/group/group_css_and_js.jsp" />
   		<decorator:head/>
    </head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/> >
     <div class="wp">
		  <div class="bd mainBodyWrap">
	  		<!-- 动态核心内容部分 -->
	  		<div id="contentDiv" class="col-2 col-twoWrap" style="width: 100%;margin-left: 0px">
	  			<dl class="rightSideWrap" style="width: 100%;margin-left: 0px">
					<decorator:body/>
				</dl>
			</div>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function () {
	$("#contentDiv").css({margin: "0 0.5px 0 0.5px"});
});
</script>	
</body>
</html>
