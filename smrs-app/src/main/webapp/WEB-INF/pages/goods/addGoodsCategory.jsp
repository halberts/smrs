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
<title>创建<s:property value="titleName"/>信息</title> 
</head>
<div id="breadcrumbs">
	<div style="overflow: hidden; position: relative; width: 750px;">
		<div>
			<ul style="width: 5000px;">
				<li class="first"></li>
				<li><a href="#">商品管理</a></li>
				<li><a href="#">属性管理</a></li>
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
			<s:form action="addGoodsCategory" namespace="/goods" method="post" id="createForm">
				<s:hidden name="actionCommand" value="add"/>
				<%@include file="goodsCategoryInc.jsp"%>	
				<div class="row">
				<input type="submit" value="创建" class="button orange" onclick="createModel()" />&nbsp;&nbsp;
					<button type="reset" ><span>重置</span></button>
					<button type="submit" class="blue" onclick="searchForm()"><span>查询</span></button>
				</div> 
			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript"> 
function createModel(){
	$("#createForm").submit();
}
function searchForm(){
	document.getElementById('createForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/goods/searchGoodsCategory.action";
}
</script>
</html>
