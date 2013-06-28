<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="security" uri="/security-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>资源查询</title>
<script type="text/javascript">
	//删除
	function delStation(id) {
		if (!confirm("确定要删除吗？")) {
			return;
		}
		window.location.href = "${dynamicURL}/security/deleteResource.action?resourceId="+ id;
	}
	function creatResource(){
		document.getElementById('searchResourceForm').onsubmit=function(){return false;};
		window.location.href = "${dynamicURL}/security/createResourceInit.action";
	}
</script>
</head>
<body>
<s:set name="displayDeleteButton" value="false"/>
<security:auth code="SECURITY_DELETE_RESOURCE">
	<s:set name="displayDeleteButton" value="true"/>
</security:auth>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">资源管理</a></li>
		<li class="last"><a href="#">资源查询</a></li>
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
			<s:form action="searchResource" namespace="/security" method="get" id="searchResourceForm">
				<table>
					<tr>
						<th>资源名称：</th>
						<th><s:textfield name="resource.name" /></th>
						<th>资源类型：</th>
						<th>
							<select id="typeSL" name="resource.type" >
								<option value="9">全部</option>
								<option value="0">URL资源</option>
								<option value="1">组件资源</option>
							</select>
						</th>
						<th>
							<button type="submit" class="orange" onclick="submitForm('searchUser');"><span>查询</span></button>&nbsp;&nbsp;
							<button type="submit" class="blue" onclick="creatResource();"><span>新建</span></button>
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
							<th>资源名称</th>
							<th>资源描述</th>
							<th>标识码</th>
							<th>类型</th>
							<th>状态</th>
							<th>创建时间</th>
							<th>最后修改时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="pager.records" var="resource" status="status">
							<tr>
								<td><a
									href='${dynamicURL}/security/updateResourceInit.action?resource.id=<s:property value="id"/>'>
										<s:property value="name" /> </a>
								</td>
								<td><s:property value="description" />
								</td>
								<td><s:property value="code" />
								</td>
								<td><s:property
										value="@com.haier.openplatform.console.security.domain.enu.ResourceTypeEnum@toEnum(type).description" />
								</td>
								<td><s:property
										value="@com.haier.openplatform.console.security.domain.enu.ResourceStatusEnum@toEnum(status).description" />
								</td>
								<td><s:date name="gmtCreate" format="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td><s:date name="gmtModified" format="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td><img
									border="0" title="" alt="" src="${staticURL}/images/trash.png" onclick="delStation(<s:property value="id"/>)">
								</td>
							</tr>
						</s:iterator>
					</tbody>
			</table>
			<p:pagination pager="pager" formId="searchResourceForm" theme="default"></p:pagination>
			</div>
		</div>
	</div>
</div>
</body>
</html>