<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建<s:property value="titleName"/>信息</title> 
<sj:head locale='zh_CN' jqueryui="true"/> 
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow: hidden; position: relative; width: 750px;">
		<div>
			<ul style="width: 5000px;">
				<li class="first"></li>
				<li><a href="#">基础数据</a></li>
				<li><a href="#"><s:property value="titleName"/>管理</a></li>
				<li class="last"><a href="#">创建<s:property value="titleName"/></a></li>
			</ul>
		</div>
	</div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			创建<s:property value="titleName"/><span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="addRegionStore" namespace="/basicdata" method="get" id="createStoreForm">
				<s:hidden name="store.storeType" value="2"/>
				<%@include file="addStoreInc.jsp"%>	
				<!-- 
				<div class="row">
						<label>地址:</label>
						<div class="right">
							<s:textarea name="store.address" rows="5" cols="45"></s:textarea>
						</div>
						<div class="tableRight">
							<label>门店状态:</label>
						<div class="right"><s:select name="store.status" list="statusMap"/></div>						
					</div>
				</div>			
				 -->	
				<div class="row">
				<input type="submit" value="创建" class="button orange" onclick="createStore()" />&nbsp;&nbsp;
					<button type="reset" ><span>重置</span></button>
					<button type="submit" class="blue" onclick="searchStore()"><span>查询</span></button>
				</div> 
			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript"> 
function createStore(){
	$("#createStoreForm").submit();
}
function searchStore(){
	document.getElementById('createStoreForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/basicdata/searchStore.action";
}
</script>
</body>
</html>
