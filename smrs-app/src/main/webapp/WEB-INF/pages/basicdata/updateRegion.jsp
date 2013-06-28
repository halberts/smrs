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
function updateRegion(){
	$("#updateRegionForm").submit();
}

function cancelRegion(){
	document.getElementById('updateRegionForm').onsubmit=function(){ return false; };
	window.history.go(-1);
	return false;
}

function searchRegion(){
	document.getElementById('updateRegionForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/basicdata/searchRegion.action";
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">基础数据</a></li>
		<li><a href="#"><s:property value="titleName"/>管理</a></li>
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
			<s:form action="updateRegion" namespace="/basicdata" method="post" id="updateRegionForm">
				<s:hidden name="region.id"/>
				<s:hidden name="region.creator"/>
				<s:hidden name="actionCommand" value="update"/>
				
				<div class="row">
					<div class="tableLeft">
						<label><s:property value="titleName"/>名<span>*</span></label>
						<s:textfield name="region.name"/>
					</div>
					<div class="tableRight">
						<label>状态<span>*</span></label>
						<s:select name="region.status" list="#{'Y':'正常','N':'禁用'}" cssClass="rightSelect"/>
					</div>
				</div>

				<div class="row">
						<label>描述:</label>
						<div class="right">
							<s:textarea name="region.description" rows="5" cols="45"></s:textarea>
						</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>创建者</label>
						<label><s:property value="region.creator" /></label>
					</div>
					<div class="tableRight">
						<label>创建时间</label>
						<label><s:date name="region.creationDate" format="yyyy-MM-dd HH:mm:ss" /></label>
					</div>
				</div>


				<div class="row">
				<input type="submit" value="更新" class="button orange" onclick="updateRegion()" />&nbsp;&nbsp;
				<button	type="reset" onclick="cancelRegion()"><span>取消</span></button>
				<button type="submit" class="blue" onclick="searchRegion()"><span>查询</span></button>
				</div>			
			</s:form>
		</div>
	</div>
</div>

</body>


</html>