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
function deleteUserFromGroup(){
	var _items = document.getElementsByName('groupusercheck');  
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
	} else {
		if(!confirm("确定要从组中删除这"+j+"个人员吗？")){ 
		return;
	    }  
		var delUserLoading = art.dialog({title: '请稍后',content: '删除中请稍后...'});
	$.ajaxSetup ({ 
		cache: false 
		}); 
	$.getJSON("${dynamicURL}/security/deleteUserFromGroup.action?group.id="+groupid+"&userId="+id, 
			function call(data){ 
			 ifModified:true;
			 cache: false;
			delUserLoading.close();
			if(data.actionMessages != null && data.actionMessages != ""){
				parent.art.dialog({
					title: '成功提示',
				    content: ''+data.actionMessages,  
				    esc: true,
				    icon: 'succeed', 
			    	close: function () {  
			    		var form = document.getElementById("searchUserForm");
			    		form.submit();
			        },
			        time: 2
				});   
			}else{
				parent.art.dialog({
					title: '失败提示',
				    content: ''+data.actionMessages,   
				    esc: true,
				    icon: 'error' 
				});   
			}
	}); 
	}
}  
</script>
</head>
<div id="breadcrumbs">
	<div style="overflow: hidden; position: relative; width: 750px;">
		<div>
			<ul style="width: 5000px;">
				<li class="first"></li>
				<li><a href="#">安全控制</a></li>
				<li><a href="#">用户组管理</a></li>
				<li class="last"><a href="#">添加组内人员</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			查询条件 <span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="searchUserInGroup" namespace="/security" method="get"
				id="searchUserForm">
				<table class="form_table">
					<tr>
						<th>登录名：</th>
						<th><s:textfield name="user.name" /></th>
						<th>用戶名：</th>
						<tH><s:textfield name="user.nickName" /></th>
						<th>Email：</th>
						<tH><s:textfield name="user.email" /> <s:hidden
								name="group.id" /> <s:hidden name="group.description" /> <s:hidden
								name="group.name" /></th>
						<th><input type="submit" value="查询" class="orange" /></th>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
</div>
<div class="section">
	<div class="box">
		<div class="title">
			组内人员列表 <span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper">
				<div>
					<div class="dataTables_filter"></div>
				</div>
				<table>
					<thead>
						<tr>
							<th>登录名</th>
							<th>用户名</th>
							<th nowrap="nowrap">邮箱</th>
							<th nowrap="nowrap">类型</th>
							<th nowrap="nowrap">状态</th>
							<th nowrap="nowrap">最近登陆IP</th>
							<th nowrap="nowrap">最近登陆时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<!-- 数据行 -->
						<s:iterator value="pagerUser.records" var="user" status="status">
							<tr>
								<td style="font-size: 12px;" nowrap="nowrap"><s:property
										value="name" /></td>
								<td style="font-size: 12px;" nowrap="nowrap"><s:property
										value="nickName" /></td>
								<td style="font-size: 12px;" nowrap="nowrap"><s:property
										value="email" /></td>
								<td style="font-size: 12px;" nowrap="nowrap"><s:property
										value="@com.haier.openplatform..security.domain.enu.UserTypeEnum@toEnum(type).description" /></td>
								<td style="font-size: 12px;" nowrap="nowrap"><s:property
										value="@com.haier.openplatform..security.domain.enu.UserStatusEnum@toEnum(status).description" /></td>
								<td style="font-size: 12px;" nowrap="nowrap"><s:property
										value="currentLoginIp" /></td>
								<td style="font-size: 12px;" nowrap="nowrap"><s:date
										name="lastLoginTime" format="yyyy-MM-dd HH:mm:ss" /></td>
								<td><img title="删除" border="0"
									src="${staticURL}/images/trash.png"
									onclick="addUserToGroup(<s:property value='id'/>)"> <img
									title="新增" border="0" src="${staticURL}/images/trash.png"
									onclick="addUserToGroup(<s:property value='id'/>)"></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<p:pagination pager="pagerUser" formId="searchUserForm"
					theme="default"></p:pagination>
			</div>
		</div>
	</div>
</div>
	</html>
</page:apply-decorator>