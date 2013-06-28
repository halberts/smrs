<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="p" uri="/pagination-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门查询</title>
<script type="text/javascript">
function doSearch(){
	var url = "${dynamicURL}/basic/searchDepartment.action";
	document.forms[0].action = url;
    document.forms[0].submit();
}
function doDel(departmentId){
	if(!window.confirm("确定要删除吗？")){
		return;
	}
	window.location.href = "${dynamicURL}/basic/deleteDepartment.action?id="+departmentId;
	//document.forms[0].action = "${dynamicURL}/basic/deleteDepartment.action?id="+departmentId;
    //document.forms[0].submit();
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">基础数据</a></li>
		<li><a href="#">部门管理</a></li>
		<li class="last"><a href="#">查询部门</a></li>
	</ul></div></div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<jsp:include page="/common/messages.jsp"/>
			<s:form namespace="/basic" method="post" id="searchDepartmentForm" action="searchDepartment">
		        <table>
		        	<tr>
						<th>部门名:</th>
						<th><s:textfield name="department.name"/></th>
						<th>Code:</th>
						<th><s:textfield name="department.code" /></th>
						<th>部门描述:</th>
						<th><s:textfield name="department.description" /></th>
						<th>
							<button type="button" onclick="doSearch();" class="orange"><span>查询</span></button>
							<button type="button" class="blue" onclick="window.location.href='${dynamicURL}/basic/createDepartmentInit.action'"><span>新建</span></button>
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
			<div class="dataTables_wrapper">
				<div>
					<div class="dataTables_filter">
					</div>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="sorting">
					<thead>
						<tr>
							<th class="rounded">部门名</th>
							<th class="rounded">Code</th>
							<th class="rounded">描述</th>
							<th class="rounded">创建者</th>
							<th class="rounded">创建时间</th>
							<th class="rounded">最后修改者</th>
							<th class="rounded">最后修改时间</th>
							<th class="rounded">操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="pager.records" var="department" status="status">
							<tr>
								<td>
									<a href="${dynamicURL}/basic/updateDepartmentInit.action?department.id=<s:property value="id"/>"><s:property value="name"/></a>
								</td>
								<td><s:property value="code"/></td>
								<td><s:property value="description"/></td>
								<td><s:property value="createBy"/></td>
								<td><s:date name="gmtCreate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td><s:property value="lastModifiedBy"/></td>
								<td><s:date name="gmtModified" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td><a href="#"><img border="0" title="" alt="" src="${staticURL}/images/trash.png" onclick="doDel(<s:property value="id"/>)"></a></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<p:pagination pager="pager" formId="searchDepartmentForm" theme="default"></p:pagination>
			</div>
		</div>
	</div>
</div>
</body>
</html>
