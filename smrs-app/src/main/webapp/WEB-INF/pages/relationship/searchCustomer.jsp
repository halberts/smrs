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
		<li><a href="#">客户供应商数据</a></li>
		<li><a href="#"><s:property value="titleName"/>信息管理</a></li>
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
			<s:form action="searchCustomer" namespace="/relationship" method="get" id="searchForm">
				<table>
					<tr>
						<th>名称：</th>
						<th><s:textfield name="customer.name" /></th>
						<th>
							<button type="submit" class="orange" onclick="submitForm('searchCustomer');"><span>查询</span></button>
							<button type="submit" class="blue" onclick="createForm()"><span>新建</span></button>
						</th>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			查询结果
			<span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div>
			<table class="sorting"> 
				<thead>
					<tr>
						<th>名称</th>
						<th>编码</th>   
						<th>类型</th>
						<th>等级</th>
						<th>修改时间</th>
						<th>操作</th>
					</tr>
				</thead>
				
				<tbody>
					<!-- 数据行 -->
					<s:iterator value="pager.records" var="relationshipCustomer" status="status">
						<tr>
							<td ><a	href='#' onclick="updateForm(<s:property value="id"/>)"><s:property value="name" /></a></td>
							<td><s:property value="code" /></td>							
							<td><s:property value="@com.smrs.relationship.enums.CustomerTypeEnum@toEnum(type).name" /></td>
							<td><s:property value="level" /></td>
							<td><s:date name="lastModifyDate" format="yyyy-MM-dd HH:mm:ss" /></td>
							<td>
								<img title="删除" border="0" src="${staticURL}/images/trash.png" 	onclick="delForm(<s:property value="id"/>)">								
							</td>
						</tr>
					</s:iterator>
				</tbody>
				</table>
				<p:pagination pager="pager" formId="searchForm" theme="default"></p:pagination>
				</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//删除
function delForm(id){
	if (!confirm("确定要删除吗？")){
		return;
	}
	window.location.href = "${dynamicURL}/relationship/deleteCustomer.action?customer.id="+id;
}

//删除
function updateForm(id){
	window.location.href = "${dynamicURL}/relationship/updateCustomer.action?customer.id="+id;
}

function createForm(){
	document.getElementById('searchForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/relationship/addCustomer.action";
}
function submitForm(action){
	var form = document.getElementById("searchForm");
	form.action="${dynamicURL}/relationship/"+action+".action";
	form.submit();
}
</script>
</body>
</html>
