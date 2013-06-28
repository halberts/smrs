<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="hop" uri="/hoptree-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建角色</title>
<script>
//此函数为ztree中callback函数，请确保该函数定义在hop:tree之前，一般定义在<head>的<script>中即可
function zTreeOnCheck(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("depTree");
	var nodes = treeObj.getCheckedNodes(true);
	var ids = new Array();
	for(var i =0;i < nodes.length; i++){
		ids.push(nodes[i].id);
	}
	$("#resourceIds").val(ids.join());
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">角色管理</a></li>
		<li class="last"><a href="#">创建角色</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			创建角色
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form namespace="/security" method="post" id="createRole" action="createRole">
				<div class="row">
					<label>角色名称<span>*</span></label>
					<div class="right"><s:textfield name="role.name" /></div>
				</div>
				<div class="row">
					<label>角色描述</label>
					<div class="right"><s:textarea name="role.description"></s:textarea></div>
				</div>
				<div class="row">
					<label>关联资源</label>
					<div class="right">
						<hop:tree url="${dynamicURL}/security/resourceTree.action"
								expandUrl="${dynamicURL}/security/resourceTree.action" 
								async="true" 
								chkType="check" 
								id="depTree"
								theme="default"
								setting="{check: {enable: true}, data: {simpleData: { enable: true}}, async: {enable:true, url: getTreeExpandUrl},callback:{onCheck: zTreeOnCheck} };"
								>
						</hop:tree>
					</div>
				</div>
				<div class="row">
					<sj:submit value="创建" id="submit"
							onBeforeTopics="setResourceIds"
							targets="formResult" 
							cssClass="button orange"
							onCompleteTopics="handleResult"/>&nbsp;&nbsp;<button type="reset"><span>重置</span></button>
				</div>
				<input type="hidden" id="resourceIds" name="resourceIds"/>
			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript">
	$.subscribe('handleResult',function(event, data) {
		handleErrors(event,data,{
			onSuccess : function() {
				window.location.href = '${dynamicURL}/security/searchRole.action';
			},
			onFaild : function() {}
		});
	});
</script>
</body>
</html>