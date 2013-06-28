<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="security" uri="/security-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="titleName"/>查询</title>
</head>
<body> 
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">基础数据管理</a></li>
		<li><a href="#"><s:property value="titleName"/>管理</a></li>
		<li class="last"><a href="#"><s:property value="titleName"/>查询</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="searchRegionStore" namespace="/basicdata" method="get" id="searchForm">
				<table>
					<tr>
						<th>名称：</th>
						<th><s:textfield name="store.name" /></th>
						<th>
							<button type="submit" class="orange" onclick="submitForm('searchRegionStore');"><span>查询</span></button>
							<button type="submit" class="blue" onclick="createStore()"><span>新建</span></button>
							<!-- 
								<button type="submit" class="green" onclick="submitForm('exportUserList')"><span>导出</span></button>
						 	-->
						</th>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
</div>

<%@include file="searchStoreInc.jsp"%>	

<script type="text/javascript">
//删除
function delStore(id){
	if (!confirm("确定要删除吗？")){
		return;
	}
	window.location.href = "${dynamicURL}/basicdata/deleteRegionStore.action?store.id="+id;
}
function createStore(){
	document.getElementById('searchForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/basicdata/addRegionStore.action";
}

function updateStore(id){
	window.location.href = "${dynamicURL}/basicdata/updateRegionStore.action?store.id="+id;
}

function submitForm(action){
	var form = document.getElementById("searchForm");
	form.action="${dynamicURL}/basicdata/"+action+".action";
	form.submit();
}
</script>
</body>
</html>
