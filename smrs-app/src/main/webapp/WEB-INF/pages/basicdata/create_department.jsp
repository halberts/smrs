<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.thd">
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="hop" uri="/hoptree-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建部门</title>
<script>
//此函数为ztree中callback函数，请确保该函数定义在hop:tree之前，一般定义在<head>的<script>中即可
function zTreeOnCheck(event, treeId, treeNode) {
	if(treeNode.checked == true){
		$("#parent_department_id").val(treeNode.id);
	}else{
		$("#parent_department_id").val('');
	}
}
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
			<li class="first"></li>
			<li><a href="#">基础数据</a></li>
			<li><a href="#">部门管理</a></li>
			<li class="last"><a href="#">创建部门</a></li>
		</ul></div></div>
	</div>
	<div class="section">
		<jsp:include page="/common/messages.jsp"/>
		<div class="box">
			<div class="title">
				创建部门
				<span class="hide"></span>
			</div>
			<div class="content">
				<s:form action="createDepartment" namespace="/basic" method="post">
					<div class="row">
						<label>部门名称<span>*</span></label>
						<div class="right">
							<s:textfield name="department.name"></s:textfield>
						</div>
					</div>
					<div class="row">
						<label>部门Code<span>*</span></label>
						<div class="right">
							<s:textfield name="department.code"></s:textfield>
						</div>
					</div>
			        <div class="row">
						<label>上级部门:</label>
						<div class="right">
							<hop:tree url="${dynamicURL}/basic/departmentTree.action"
									expandUrl="${dynamicURL}/basic/departmentTree.action" 
									async="true" 
									chkType="radio" 
									id="depTree"
									setting="{check: {enable: true, chkStyle: 'radio', radioType: 'all'}, data: {simpleData: { enable: true}}, async: {enable:true, url: getTreeExpandUrl},callback:{onCheck: zTreeOnCheck} };"
									theme="default">
							</hop:tree>
		                	<s:hidden name="department.parent.id" id="parent_department_id"/>
						</div>
					</div>
					<div class="row">
						<label>部门状态<span>*</span></label>
						<div class="right">
							<s:select name="department.status" list="#{1:'启用',0:'禁用'}" />
						</div>
					</div>
					<div class="row">
						<label>描述:</label>
						<div class="right">
							<s:textarea name="department.description" rows="5" cols="45"></s:textarea>
						</div>
					</div>
					<div class="row">
						<sj:submit value="创建" id="submit"
									targets="formResult" 
									onCompleteTopics="handleResult" cssClass="button orange"/>
						<button type="reset">
							<span>重置</span>
						</button>
					</div>
				</s:form>
			</div>
		</div>
	</div>
<script type="text/javascript">
	//页面加载完毕后选中当前资源的父资源
	$.subscribe('handleResult',function(event, data) {
		handleErrors(event,data,{
			onSuccess : function() {
				window.location.href = '${dynamicURL}/basic/searchDepartment.action';
			},
			onFaild : function(){
				
			}
		});
	});

	function findNode(nodes, pId) {
		if(nodes != null) {
			for (var i = 0; i < nodes.length; i++){
				if (nodes[i].id == pId) {
					return nodes[i];
				} else {
					var children = nodes[i].children;
					var found = findNode(children, pId);
					if(found != null) {
						return found;
					}
				}
			}
		}
		return null;
	}
</script>
</body>
</html>