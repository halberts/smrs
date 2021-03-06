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
<title>创建组信息</title> 
<style>  
.tab1 {
	width: 100%;
	margin-left: 0px;
}
</style>
<script type="text/javascript">
	function delAdminFromGroup() {
		var _items = document.getElementsByName('usercheck');
		var itemvalue = new Array();
		var id;
		var j = 0;
		for ( var i = 0; i < _items.length; i++) {
			if (_items[i].checked) {
				j = j + 1;
				itemvalue[i] = _items[i].value;
				if (j == 1) {
					id = itemvalue[i];
				} else {
					id = id + "," + itemvalue[i];
				}
			}
		}
		if (j == 0) {
			art.dialog({
				title : '警告信息',
				content : '至少选择一条记录',
				esc : true,
				icon : 'warning'
			});
		} else {
			if (!confirm("确定要删除这" + j + "个人员到组吗？")) {
				return;
			}
			var delAdminLoading = art.dialog({title: '请稍后',content: '删除中请稍后...'});
			var groupid = document.getElementById('thisgroupid').value;
			$.ajaxSetup({
				cache : false
			});
			$.getJSON(
					"${dynamicURL}/security/deleteAdminFromGroup.action?group.id="
							+ groupid + "&admin=" + id, function call(data) {
						ifModified: true;
						cache: false;
							delAdminLoading.close();
						if (data.actionMessages != null
								&& data.actionMessages != "") {
							art.dialog({
								title : '成功提示',
								content : '' + data.actionMessages,
								esc : true,
								icon : 'succeed',
								close : function() {
									var form = document
											.getElementById('searchAdminForm');
									form.submit();
								},
								time: 2
							});
						} else {
							art.dialog({
								title : '失败提示',
								content : '' + data.actionErrors,
								esc : true,
								icon : 'error'
							});
						}
					});
		}
	}
</script>
</head>
<body>
	<!-- <dt>
	<h3>用户列表</h3>
</dt> -->
	<dd class="tab1">
		<div id="searchUser" style="display: block;">
			<div style="width: 100%;">
				<s:hidden name="group.id" id="thisgroupid" />
				<s:form action="searchAdminInGroup" namespace="/security"
					method="get" id="searchAdminForm">
					<table class="form_table">
						<tr>
							<th nowrap="nowrap">登录名：</th>
							<td><s:hidden name="group.id" id="thisgroupid" /> <s:textfield
									name="groupUser.name" /></td>
							<th>用戶名：</th>
							<td><s:textfield name="groupUser.nickName" /></td>
						</tr>
						<tr>
							<th>Email：</th>
							<td><s:textfield name="groupUser.email" /></td>
							<td colspan="2"><input type="submit" value="查询"
								class="abn db" /></td>
						</tr>
					</table>
				</s:form>
				<div class="h5"></div>
				<input type="button" value="移除" onclick="delAdminFromGroup()"
					class="abn db" style="margin-bottom: 3px;margin-left: 2px" /> <input type="button"
					value="新增" onclick="window.parent.addAdminToGroup()" class="abn db"
					style="margin-bottom: 3px;" />

				<table id="rounded-corner" class="color_table" cellspacing="0"
					width="100%">
					<thead>
						<tr>
							<th nowrap="nowrap"><input name="allusercheck"
								type="checkbox" id="allusercheck"
								onclick="checkAll(this.checked, 'usercheck')" /></th>
							<th style="width: 100px; font-size: 12px;">登录名</th>
							<th style="width: 100px; font-size: 12px;">用户名</th>
							<th style="width: 100px; font-size: 12px;" nowrap="nowrap">
								邮箱</th>
							<th style="width: 100px; font-size: 12px;" nowrap="nowrap">
								类型</th>
							<th style="width: 200px; font-size: 12px;" nowrap="nowrap">
								状态</th>
						</tr>
					</thead>
					<tbody>
						<!-- 数据行 -->
						<s:iterator value="pagerGroupUser.records" var="user"
							status="status">
							<tr>
								<td style="width: 12PX;"><input name="usercheck"
									type="checkbox" id="usercheck<s:property value='id'/>"
									value="<s:property value='id'/>"
									onclick="chkeckSingle('usercheck', 'allusercheck')" /></td>
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
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<div id="footer-container" class="dd-fd"
					style="float: left; width: 100%">
					<p:pagination pager="pagerGroupUser" formId="searchAdminForm"></p:pagination>
				</div>
			</div>
		</div>
	</dd>
</body>
	</html>
</page:apply-decorator>