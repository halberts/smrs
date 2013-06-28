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
<title>修改<s:property value="titleName"/>信息</title>
<script type="text/javascript"> 
function updateGoodsAttribute(){
	$("#updateGoodsAttributeForm").submit();
}

function cancelGoodsAttribute(){
	document.getElementById('updateGoodsAttributeForm').onsubmit=function(){ return false; };
	window.history.go(-1);
	return false;
}

function searchGoodsAttribute(){
	document.getElementById('updateGoodsAttributeForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/goods/searchGoodsAttribute.action";
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">基础数据</a></li>
		<li><a href="#">渠道管理</a></li>
		<li class="last"><a href="#">修改<s:property value="titleName"/>信息</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			修改<s:property value="titleName"/>信息
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="updateGoodsAttribute" namespace="/goods" method="post" id="updateGoodsAttributeForm">
				<s:hidden name="goodsAttribute.id"/>
				<s:hidden name="goodsAttribute.creator"/>
				<s:hidden name="actionCommand" value="update"/>
				
				<%@include file="goodsAttributeInc.jsp"%>	
				<div class="row">
					<div class="tableLeft">
						<label>创建者</label>
						<label><s:property value="goodsAttribute.creator" /></label>
					</div>
					<div class="tableRight">
						<label>创建时间</label>
						<label><s:date name="goodsAttribute.creationDate" format="yyyy-MM-dd HH:mm:ss" /></label>
					</div>
				</div>
				<div class="row">
					<input type="submit" value="更新" class="button orange" onclick="updateGoodsAttribute()" />&nbsp;&nbsp;
					<button	type="reset" onclick="cancelGoodsAttribute()"><span>取消</span></button>
					<button type="submit" class="blue" onclick="searchGoodsAttribute()"><span>查询</span></button>
				</div>			
			</s:form>
		</div>
	</div>
</div>

</body>


</html>