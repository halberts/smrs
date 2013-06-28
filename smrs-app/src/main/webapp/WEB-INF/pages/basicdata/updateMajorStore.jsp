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
<title>修改总仓信息</title>
<script type="text/javascript"> 
function updateChannel(){
	$("#updateChannelForm").submit();
}

function cancelChannel(){
	document.getElementById('updateStoreForm').onsubmit=function(){ return false; };
	window.history.go(-1);
	return false;
}

function searchChannel(){
	document.getElementById('updateStoreForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/basicdata/searchChannel.action";
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">基础数据</a></li>
		<li><a href="#">总仓管理</a></li>
		<li class="last"><a href="#">修改总仓信息</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			修改总仓信息
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="updateMajorStore" namespace="/basicdata" method="post" id="updateStoreForm">
				<s:hidden name="store.id"/>
				<s:hidden name="store.creator"/>
				<s:hidden name="store.creationDate"/>
				<s:hidden name="store.openDate"/>
				<s:hidden name="store.closeDate"/>
				<s:hidden name="actionCommand" value="update"/>				
				<div class="row">
					<div class="tableLeft">
						<label>总仓名<span>*</span></label>
						<s:textfield name="store.name"/>
					</div>
					<div class="tableRight">
						<label>总仓代码<span>*</span></label>
						<s:textfield name="store.storeCode"/>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>总仓简称<span>*</span></label>
						<s:textfield name="store.shortName"/>
					</div>
					<div class="tableRight">
						<label>总仓省<span>*</span></label>
						<s:textfield name="store.province"/>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>总仓市<span></span></label>
						<s:textfield name="store.city"/>
					</div>
					<div class="tableRight">
						<label>总仓区县<span></span></label>
						<s:textfield name="store.zone"/>
					</div>					
				</div>
				
				<div class="row">
						<label>地址:</label>
						<div class="right">
							<s:textarea name="store.address" rows="5" cols="45"></s:textarea>
						</div>
				</div>
				
				<div class="row">
					<div class="tableLeft">
						<label>联系人</label>
						<s:textfield name="store.manager"/>
					</div>
					<div class="tableRight">
						<label>电话</label>
						<s:textfield name="store.tel"/>
					</div>
				</div>


				<div class="row">
				<input type="submit" value="更新" class="button orange" onclick="updateChannel()" />&nbsp;&nbsp;
				<button	type="reset" onclick="cancelChannel()"><span>取消</span></button>
				
				</div>			
			</s:form>
		</div>
	</div>
</div>

</body>


</html>