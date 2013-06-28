<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<page:apply-decorator name="content">
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新组信息</title> 
<script type="text/javascript"> 
function deleteRoleFromGroup(){
	var _items = document.getElementsByName('grouprolecheck');  
	var itemvalue = new Array(); 
	var id;
	var j = 0;
	for ( var i = 0; i < _items.length; i++) {
		if (_items[i].checked) {
			j = j + 1; 
			itemvalue[i] = _items[i].value; 
			if(j==1){
				id = itemvalue[i];
			}else{
				id = id+","+itemvalue[i];
			}  
		}
		}     
	var groupid = parent.document.getElementById('thisgroupid').value; 
	if(j==0){
		parent.art.dialog({
			title: '警告信息',
		    content: '至少选择一条记录',  
		    esc: true,
		    icon: 'warning' 
		});   
	} else{ 
		if(!confirm("确定要从组中删除这"+j+"个角色吗？")){
		return;
		}  
		var delRoleLoading = art.dialog({title: '请稍后',content: '删除中请稍后...'});
		$.ajaxSetup ({ 
			cache: false 
			}); 
		$.getJSON("${dynamicURL}/security/deleteRoleFromGroup.action?group.id="+groupid+"&roleId="+id, 
				function call(data){ 
				 ifModified:true;
				 cache: false;
				delRoleLoading.close();
				if(data.actionMessages != null && data.actionMessages != ""){
					parent.art.dialog({
						title: '成功提示',
					    content: ''+data.actionMessages,  
					    esc: true,
					    icon: 'succeed' ,
					    close: function () {  
				    		var form = document.getElementById("searchRoleForm");
				    		form.submit();
				        },
				        time: 2
					});   
				}else{
					parent.art.dialog({
						title: '失败提示',
					    content: ''+data.actionErrors,   
					    esc: true,
					    icon: 'error' 
					});   
				}
		});
	}
	
  //window.location.href = "${dynamicURL}/security/deleteRoleFromGroup.action?group.id="+groupid+"&group.groupid="+groupgroupid+"&group.name"+groupname+"&roleId="+id;  
}  
</script>
</head>
<dd class="tab1">
	<div style="width: 100%; margin-right: 10PX; font-size: 12px;">
		<s:form namespace="/security" method="post" id="searchRoleForm"
			action="searchRoleInGroup">
			<table class="form_table">
				<th>角色名:</th>
				<td><s:textfield name="role.name" /></td>
				<th>角色描述:</th>
				<td><s:textfield name="role.description" />
					<s:hidden name="group.id" /> <s:hidden name="group.description" />
					<s:hidden name="group.name" /></td>
				<td><input type="submit" value="查询" class="abn db l" /></td>
			</table>
		</s:form>
	</div>
	<div style="margin-top: 3px;">
		<input type="button" value="移除" onclick="deleteRoleFromGroup()"
			class="abn db"
			style="font-size: 12px; margin-left: 2px; margin-bottom: 3px" /> <input
			type="button" value="新增" onclick="window.parent.addRoleToGroup()"
			class="abn db" style="margin-bottom: 3PX" />
	</div>
	<div style="width: 100%; margin-right: 10PX;">
		<table id="rounded-corner" class="color_table">
			<thead>
				<tr>
					<th nowrap="nowrap"><input name="allgrouprolecheck"
						type="checkbox" id="allgrouprolecheck"
						onclick="checkAll(this.checked, 'grouprolecheck')" /></th>
					<th class="rounded">角色名</th>
					<th class="rounded">描述</th>
					<th class="rounded">创建者</th>
					<th class="rounded">创建时间</th>
					<th class="rounded">最后修改者</th>
					<th class="rounded">最后修改时间</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="pagerRole.records" var="role" status="status">
					<tr>
						<td style="width: 12PX;"><input name="grouprolecheck"
							type="checkbox" id="grouprolecheck<s:property value='id'/>"
							value="<s:property value='id'/>"
							onclick="chkeckSingle('grouprolecheck', 'allgrouprolecheck')" /></td>
						<td><s:property value="name" /></td>
						<td><s:property value="description" /></td>
						<td><s:property value="createBy" /></td>
						<td><s:date name="gmtCreate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><s:property value="lastModifiedBy" /></td>
						<td><s:date name="gmtModified" format="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<div id="addRoleToGroup" style="display: none;">
			<iframe scrolling="no"
				src="${dynamicURL}/security/addRoleToGroupInit.action?group.id=<s:property value='group.id'/>"
				frameborder="0" width="850px" height="450px"></iframe>
		</div>
		<div id="footer-container" class="dd-fd"
			style="float: left; width: 100%; margin-right: 10px;">
			<p:pagination pager="pagerRole" formId="searchRoleForm"  theme="default"></p:pagination>
		</div>
	</div>
	</html>
</page:apply-decorator>