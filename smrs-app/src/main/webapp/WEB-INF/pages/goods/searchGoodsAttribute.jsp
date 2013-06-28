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
		<li><a href="#">商品管理</a></li>
		<li><a href="#">属性管理</a></li>
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
			<s:form action="searchGoodsAttribute" namespace="/goods" method="get" id="searchForm">
				<table>
					<tr>
						<th>名称：</th>
						<th><s:textfield name="goodsAttribute.name" /></th>
						<th>
							<button type="submit" class="orange" onclick="submitForm('searchGoodsAttribute');"><span>查询</span></button>
							<button type="submit" class="blue" onclick="creatGoodsAttribute()"><span>新建</span></button>
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
						<th>状态</th>
						<th>修改时间</th>
						<th>操作</th>
					</tr>
				</thead>
				
				<tbody>
					<!-- 数据行 -->
					<s:iterator value="pager.records" var="goodsAttribute" status="status">
						<tr>
							<td ><a	href='${dynamicURL}/goods/updateGoodsAttribute.action?goodsAttribute.id=<s:property value="id"/>'><s:property value="name" /></a></td>
							<td><s:property value="description" /></td>							
							<td><s:property value="@com.smrs.enums.StatusEnum@toEnum(status).description" /></td>
							<td><s:date name="lastModifyDate" format="yyyy-MM-dd HH:mm:ss" /></td>
							<td>
								<img title="删除" border="0" src="${staticURL}/images/trash.png" 	onclick="delGoodsAttribute(<s:property value="id"/>)">								
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
function delGoodsAttribute(id){
	if (!confirm("确定要删除吗？")){
		return;
	}
	window.location.href = "${dynamicURL}/goods/deleteGoodsAttribute.action?goodsAttribute.id="+id;
}
function creatGoodsAttribute(){
	document.getElementById('searchForm').onsubmit=function(){ return false; };
	window.location.href = "${dynamicURL}/goods/addGoodsAttribute.action";
}
function submitForm(action){
	var form = document.getElementById("searchForm");
	form.action="${dynamicURL}/goods/"+action+".action";
	form.submit();
}
</script>
</body>
</html>
