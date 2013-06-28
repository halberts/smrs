<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="security" uri="/security-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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
		<li><a href="#">日志查询</a></li>
		<li><a href="#"><s:property value="titleName"/></a></li>
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
			<s:form action="searchLogTrace" namespace="/logtrace" method="get" id="searchForm">
				<table>
					<tr>
						<th>类型：</th>
						<th><s:select name="logTraceType" list="logTraceTypeList" listKey="id" listValue="name"/></th>
						<th>开始日期：</th>
						<th><sj:datepicker name="startDate" timepicker="false" displayFormat="yy-mm-dd"/></th>
						<th>结束日期：</th>
						<th><sj:datepicker name="endDate" timepicker="false" displayFormat="yy-mm-dd"/></th>
						
						<th>
							<button type="submit" class="orange" onclick="submitForm('searchLogTrace');"><span>查询</span></button>
							
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
						<th>描述</th>   
						<th>类型</th>
						<th>创建人</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				
				<tbody>
					<!-- 数据行 -->
					<s:iterator value="pager.records" var="logtraceCustomer" status="status">
						<tr>
							<td ><s:property value="traceAction" /></a></td>
							<td><s:property value="description" /></td>							
							<td><s:property value="@com.smrs.logtrace.enums.LogTraceTypeEnum@toEnum(type).name" /></td>
							<td><s:property value="creator" /></td>
							<td><s:date name="creationDate" format="yyyy-MM-dd HH:mm:ss" /></td>
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
	window.location.href = "${dynamicURL}/logtrace/deleteCustomer.action?customer.id="+id;
}

//删除
function updateForm(id){
	window.location.href = "${dynamicURL}/logtrace/updateCustomer.action?customer.id="+id;
}

function createForm(){
	document.getElementById('searchForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/logtrace/addCustomer.action";
}
function submitForm(action){
	var form = document.getElementById("searchForm");
	form.action="${dynamicURL}/logtrace/"+action+".action";
	form.submit();
}
</script>
</body>
</html>
