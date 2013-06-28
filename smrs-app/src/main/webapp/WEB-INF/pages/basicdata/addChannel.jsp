<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建渠道信息</title> 
</head>
<div id="breadcrumbs">
	<div style="overflow: hidden; position: relative; width: 750px;">
		<div>
			<ul style="width: 5000px;">
				<li class="first"></li>
				<li><a href="#">基础数据</a></li>
				<li><a href="#">渠道管理</a></li>
				<li class="last"><a href="#">创建渠道</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			创建渠道<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="addChannel" namespace="/basicdata" method="get" id="createChannelForm">
				<div class="row">
					<div class="tableLeft">
						<label>渠道名称<span>*</span></label>
						<s:textfield name="channel.name"></s:textfield>
					</div>
					<div class="tableRight">
						<label>描述:</label>
						<s:textfield name="channel.description"
								id="thisgroupdescription" />
					</div>
				</div>
				<div class="row">
					<input type="submit" value="创建" class="button orange" onclick="createChannel()" />&nbsp;&nbsp;
					<button type="reset" ><span>重置</span></button>
					<button type="submit" class="blue" onclick="searchChannel()"><span>查询</span></button>
				</div> 
			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript"> 
function createChannel(){
	$("#createChannelForm").submit();
}
function searchChannel(){
	document.getElementById('createChannelForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/basicdata/searchChannel.action";
}
</script>
</html>
