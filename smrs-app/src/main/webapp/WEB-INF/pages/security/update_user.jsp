<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="hop" uri="/hoptree-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改用户信息</title>
<script> 	
//此函数为ztree中callback函数，请确保该函数定义在hop:tree之前，一般定义在<head>的<script>中即可

function zTreeOnCheck(event, treeId, treeNode) {
	if(treeNode.checked == true){
		$("#storeIds").val(treeNode.id);
	}else{
		$("#storeIds").val('');
	}
}
/*
function zTreeOnCheck(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("depTree");
	var nodes = treeObj.getCheckedNodes(true);
	var ids = new Array();
	for(var i =0;i < nodes.length; i++){
		ids.push(nodes[i].id);
	}
	var joinIds=ids.join();
	$("#storeIds").val(joinIds);
	//alert($("#storeIds").val());
}
*/
</script>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">用户管理</a></li>
		<li class="last"><a href="#">修改用户信息</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			修改用户信息
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="updateUser" namespace="/security" method="post" >
				<s:hidden id="storeIds" name="storeIds" />
				<s:hidden id="roleIds" name="roleIds" />
				<s:hidden name="user.id"/>
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


				
				
				<div class="row">
					<div class="tableLeft">
						<label>创建者</label>
						<label><s:property value="user.createBy" /></label>
					</div>
					<div class="tableRight">
						<label>创建时间</label>
						<label><s:date name="user.gmtCreate" format="yyyy-MM-dd HH:mm:ss" /></label>
					</div>
				</div>
				
				<div class="row">
					<div class="tableLeft">
						<label>上次登陆IP</label>
						<label><s:property value="user.lastLoginIp" /></label>
					</div>
					<div class="tableRight">
						<label>上次登陆时间</label>
						<label><s:date name="user.lastLoginTime" format="yyyy-MM-dd HH:mm:ss" /></label>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>上次登陆失败时间</label>
						<label><s:date name="user.loginFaildTime" format="yyyy-MM-dd HH:mm:ss" /></label>
					</div>
					<div class="tableRight">
						<label>登陆失败次数</label>
						<label><s:property value="user.loginAttemptTimes" /></label>
					</div>
				</div>
				
				<%@include file="userGroupInc.jsp"%>	
				
				<div class="row">
					<label>部门<span>*</span></label>
					<div class="right">
						<hop:tree url="${dynamicURL}/basicdata/displayStoreTree.action?id=0"
								expandUrl="${dynamicURL}/basicdata/displayStoreTree.action" 
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
						<!--
						<sj:submit value="保存" id="submit" onClickTopics="click" targets="formResult" onCompleteTopics="handleResult" cssClass="button orange" />
						
						<sj:submit value="保存" id="submit" onClickTopics="click" targets="formResult" onCompleteTopics="handleResult" cssClass="button orange" />
						-->  
						 <input type="submit" value="更新" class="button orange"  onClick="setSelected('rightList')"/>&nbsp;&nbsp;
						
						<button>
							<span>取消</span>
						</button>
				</div>

			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript">
 	

/*
	$.subscribe('click', function(event, data) {
		
		setSelected('rightList');
		
		var roleIds = "";
		$("[name='checkbox']:checked").each(function() {
			roleIds += $(this).val() + ",";
		})
		$("#storeIds").val(roleIds);
		
	});
	*/
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
	
	// 选中上级目录
 	$(function () {
 		var treeObj = $.fn.zTree.getZTreeObj("depTree");
 		var nodes = treeObj.getNodes();

 		<s:iterator value="user.stores" var ='dep'>
	 		var depId = <s:property value='#dep.id'/>;
			var prt = findNode(nodes, depId);	// 查找父节点
			treeObj.checkNode(prt, true);	// 选中父节点
			treeObj.selectNode(prt);	// 选择（高亮显示）节点 
			treeObj.expandNode(prt, true);	// 展开节点
	 	</s:iterator> 
 		var checkNode = treeObj.getCheckedNodes(true);
		$("#storeIds").val(checkNode[0].id);  
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