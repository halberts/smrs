<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="hop" uri="/hoptree-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改资源</title>
<script>
//此函数为ztree中callback函数，请确保该函数定义在hop:tree之前，一般定义在<head>的<script>中即可
function zTreeOnCheck(event, treeId, treeNode) {
	if(treeNode.checked == true){
		$("#parent_menu_id").val(treeNode.id);
	}else{
		$("#parent_menu_id").val('');
	}
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div style="overflow:hidden; position:relative;  width: 750px;"><div><ul style="width: 5000px;">
		<li class="first"></li>
		<li><a href="#">安全控制</a></li>
		<li><a href="#">资源管理</a></li>
		<li class="last"><a href="#">修改资源</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			创建资源
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="updateResource" namespace="/security" method="post">
				<div class="row">
					<label>资源名称<span>*</span></label>
					<div class="right"><s:textfield name="resource.name" size="54" /></div>
				</div>
				<div class="row">
					<label>父资源</label>
					<div class="right">
						<hop:tree url="${dynamicURL}/security/expandingResourceTree.action?expandId=${resource.id}"
								expandUrl="${dynamicURL}/security/expandingResourceTree.action" 
								async="true" 
								chkType="radio" 
								theme="default"
								id="depTree"
								setting="{check: {enable: true, chkStyle: 'radio', radioType: 'all'}, data: {simpleData: { enable: true}}, async: {enable:true, url: getTreeExpandUrl},callback:{onCheck: zTreeOnCheck} };"
								>
						</hop:tree>
					</div>
				</div>
				<div class="row">
					<label>所在模块<span>*</span></label>
					<div class="right"><s:select name="resource.moduleName" list="modules"/><label class="rightDescription">模块名与链接的namespace保持一致</label></div>
				</div>
				<div class="row">
					<label>访问链接</label>
					<div class="right"><s:textfield name="resource.url"/></div>
				</div>
				<div class="row">
					<label>是否在左侧菜单展示<span>*</span></label>
					<div class="right"><s:select name="resource.status" list="#{1:'是',0:'否'}"/>
                	<label class="rightDescription">只有状态为显示并且类型为URL资源的资源才可以展示在左侧菜单中</label></div>
				</div>
				<div class="row">
					<label>资源类型<span>*</span></label>
					<div class="right"><s:select name="resource.type" list="#{0:'URL资源',1:'组件资源'}"/></div>
				</div>
				<div class="row">
					<label>标示码</label>
					<div class="right"><s:textfield name="resource.code" size="54" cssClass="inputInMiddle"/>
                	<label class="rightDescription">为每个资源定义唯一的code(身份证)</label></div>
				</div>
				<div class="row">
					<label>配置项</label>
					<div class="right"><s:textarea name="resource.configuration" rows="1" cssClass="shortTextArea"></s:textarea></div>
				</div>
				<div class="row">
					<label>描述</label>
					<div class="right"><s:textarea name="resource.description" rows="1" cssClass="shortTextArea"></s:textarea></div>
				</div>
				<div class="row">
					<label>序号<span>*</span></label>
					<div class="right"><s:textfield name="resource.orderIndex" cssClass="inputInMiddle"></s:textfield>
            			<label class="rightDescription">排序号越小的资源显示越靠前</label>
            		</div>
				</div>
				<div class="row">
					<sj:submit value="修改" id="submit"
							targets="formResult" 
							cssClass="button orange"
							onCompleteTopics="handleResult"/><button type="reset"><span>重置</span></button>
					<s:hidden name="resource.id" />
					<s:hidden name="resource.parent.id" id="parent_menu_id"/>
				</div>
			</s:form>
		</div>
	</div>
</div>
<script type="text/javascript">
	$.subscribe('handleResult',function(event, data) {
		handleErrors(event,data,{
			onSuccess : function() {
				window.location.href = '${dynamicURL}/security/searchResource.action';
			},
			onFaild : function() {}
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
	
	// 选中上级目录
 	$(function () {
		var resourceId = "<s:property value='resource.parent.id'/>";
		var treeObj = $.fn.zTree.getZTreeObj("depTree");
		var nodes = treeObj.getNodes();
		var prt = findNode(nodes, resourceId);	// 查找父节点
		treeObj.checkNode(prt, true);	// 选中父节点
		treeObj.selectNode(prt);	// 选择（高亮显示）节点 
		treeObj.expandNode(prt, true);	// 展开节点
		
    }); 
</script>
</body>
</html>