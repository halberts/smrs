<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="hop" uri="/hoptree-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建用户</title>
<script type="text/javascript" src="${staticURL}/scripts/hop.js"></script>
<script src="${staticURL}/scripts/jquery.ui.datepicker.js"></script>
<script>
//此函数为ztree中callback函数，请确保该函数定义在hop:tree之前，一般定义在<head>的<script>中即可
function zTreeOnCheck(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("depTree");
	var nodes = treeObj.getCheckedNodes(true);
	var ids = new Array();
	for(var i =0;i < nodes.length; i++){
		ids.push(nodes[i].id);
	}
	$("#storeIds").val(ids.join());
}

</script> 
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">用户管理</a></li>
		<li class="last"><a href="#">创建用户</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			创建用户
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form id="upUserForm" name="upUserForm" action="createUser" namespace="/security" method="post">
				<s:hidden id="roleIds" name="roleIds" />
				<s:hidden id="storeIds" name="storeIds" />
				<div class="row">
					<div class="tableLeft">
						<label>登录名<span>*</span></label>
						<s:textfield name="user.name"/>
					</div>
					<div class="tableRight">
						<label>真实姓名<span>*</span></label>
						<s:textfield name="user.trueName" />
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>密码<span>*</span></label>
						<s:password name="user.password" />
					</div>
					<div class="tableRight">
						<label>确认密码<span>*</span></label>
						<s:password name="confirmPassword" />
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>性别<span>*</span></label>
						<s:select name="user.gender" list="genderTypeList" listKey="id" listValue="name" cssClass="leftSelect"/>
					</div>
					<div class="tableRight">
						<label>状态<span>*</span></label>
						<s:select name="user.status" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>用户类型<span>*</span></label>
						<s:select name="user.userType" list="userTypeList" listKey="id" listValue="name" cssClass="leftSelect"/>
					</div>
					<div class="tableRight">
						<label>店员类型<span>*</span></label>
						<s:select name="user.clerkType" list="clerkTypeList" listKey="id" listValue="name" cssClass="rightSelect"/>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>邮箱<span>*</span></label>
						<s:textfield name="user.email" />
					</div>
					<div class="tableRight">
						<label>手机<span>*</span></label>
						<s:textfield name="user.mobile" />
						
					</div>
				</div>
				<div class="row">
					<label>职责:</label>
					<div class="right">
						<s:textarea name="user.responsibility" rows="3" cols="45"></s:textarea>
					</div>
				</div>
				<div class="row">
					<label>备注:</label>
					<div class="right">
						<s:textarea name="user.memo" rows="3" cols="45"></s:textarea>
					</div>
				</div>

				<%@include file="userGroupInc.jsp"%>	

				<div class="row">
					<label>门店</label>
					<div class="right">
						
						<hop:tree url="${dynamicURL}/basicdata/storeTree.action?id=0"
								expandUrl="${dynamicURL}/basicdata/storeTree.action" 
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
						<sj:submit value="创建" id="submit" onClickTopics="click"
							targets="formResult"  onBeforeTopics="setDepartmentIds"
							onCompleteTopics="handleResult" cssClass="button orange"/>&nbsp;&nbsp;<button
						type="reset" ><span>重置</span></button>
				</div>
			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript">
	$.subscribe('handleResult',
		function(event, data) {
			handleErrors(event,data,{
				onFaild : function() {
					return;
				},
				onSuccess : function() {
					window.location.href = '${dynamicURL}/security/searchUser.action';
				}
		});
	});
</script>
</body>
</html>