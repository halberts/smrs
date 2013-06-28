<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script>
//此函数为ztree中callback函数，请确保该函数定义在hop:tree之前，一般定义在<head>的<script>中即可
function zTreeOnCheck(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("depTree");
	var nodes = treeObj.getCheckedNodes(true);
	var ids = new Array();
	for(var i =0;i < nodes.length; i++){
		ids.push(nodes[i].id);
	}
	$("#resourceIds").val(ids.join())
}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建组信息</title> 
</head>
<div id="breadcrumbs">
	<div style="overflow: hidden; position: relative; width: 750px;">
		<div>
			<ul style="width: 5000px;">
				<li class="first"></li>
				<li><a href="#">安全控制</a></li>
				<li><a href="#">组管理</a></li>
				<li class="last"><a href="#">创建新组</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			创建新组<span class="hide"></span>
		</div>
		<div class="content">

			<s:form action="createGroup" namespace="/security" method="get" id="createGroupForm">
				<div class="row">
					<div class="tableLeft">
						<label>组名称<span>*</span></label>
						<s:textfield name="group.name" id="thisgroupname" />
					</div>
					<div class="tableRight">
						<label>组描述:</label>
						<s:textfield name="group.description"
								id="thisgroupdescription" />
					</div> 
				</div>
				<%@include file="groupResourceInc.jsp"%>
				

				<div class="row">
				<input type="submit" value="创建"
							class="button orange" onclick="createGroup()" />&nbsp;&nbsp;<button
						type="reset" ><span>重置</span></button>
				</div> 
				<input id="resourceIds" type="hidden" name="resourceIds"/>
			</s:form>
		</div>
	</div>
</div>


<script type="text/javascript">
	
 	function createGroup(){
 		setSelected('rigthList');
 		$("#createGroupForm").submit();
 	}
</script>
</html>
