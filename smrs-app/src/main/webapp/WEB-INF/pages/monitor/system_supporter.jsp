<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设定系统负责人</title>
<script>
function postData(data) {
	// 给隐藏变量赋值
	$("#ownerName").val(data.ownerName);
	$("#ownerEmail").val(data.ownerEmail);
	$("#sptOwnerName").val(data.sptOwnerName);
	$("#sptOwnerEmail").val(data.sptOwnerEmail);
	$("#sptOwnerPhone").val(data.sptOwnerPhone);
	$("#sptEmail").val(data.sptEmail);
	$("#sptPhone").val(data.sptPhone);
	
	
	// 以表单方式提交数据到后台
	$("#hidForm").submit();
	
}

$(document).ready(function(){
	/**
	 * 清空
	 */
/* 	$("#supporterTbl input").val(''); 
	$("#supporterTbl textarea").val(''); */
	
	/**
	 * 只读
	 */
/* 	$("#supporterTbl input").attr('disabled', true);
	$("#supporterTbl textarea").attr('disabled', true); */
	
	$("#appId").val($("select[name='appSel']").find('option:selected').val());
	/**
	 * 下垃框选择事件
	 */
	$("select[name='appSel']").change(function(){
		
		$("#ownerName").val('');
		$("#ownerEmail").val('');
		$("#ownerPhone").val('');
		$("#sptOwnerName").val('');
		$("#sptOwnerEmail").val('');
		$("#sptOwnerPhone").val('');
		$("#sptEmail").val('');
		$("#sptPhone").val('');
		
		$("#appId").val($("select[name='appSel']").find('option:selected').val());
	});
	
	
	
	/**
	 * 编辑按钮
	 */
/* 	$("#editBtn").click(function(){
		// 以表单方式提交数据到后台
		var frm = $("#hidForm");
		var appId = $("select[name='appSel']").find('option:selected').val();
		var sptId = $("#sptId").val();
		var url = 'crudSupporter.action?appId=' + appId  + '&supporterId=' + sptId;
		frm.attr('action', url);alert(url);
		$("#hidForm").submit();	
	}); */
	
	
	/**
	 * 查询按钮
	 */
	$("#searchBtn").click(function(){
		var appId = $("select[name='appSel']").find('option:selected').val();
		var url = 'getIssueSupporters.action?appId=' + appId;
		$.ajax({
			url: url,
			type: 'GET',
			cache: false,
			success: function(data){
				if(data.supporterList.length == 0) {	// 没有负责人
					
				} else {
					var issueSupporter = data.supporterList[0];
					$("#sptId").val(issueSupporter.id);
					$("#ownerName").val(issueSupporter.ownerName);
					$("#ownerEmail").val(issueSupporter.ownerEmail);
					$("#ownerPhone").val(issueSupporter.ownerPhone);
					$("#sptOwnerName").val(issueSupporter.sptOwnerName);
					$("#sptOwnerEmail").val(issueSupporter.sptOwnerEmail);
					$("#sptOwnerPhone").val(issueSupporter.sptOwnerPhone);
					$("#sptEmail").val(issueSupporter.sptEmail);
					$("#sptPhone").val(issueSupporter.sptPhone);
				}
			},
			
			error: function(){
				$("#info").text('ajax获取负责人信息出错。');
				return ;
			}
		});
	});
	
	
	/**
	 * 添加按钮
	 */
	$("#addBtn").click(function(){
		var appName = $("#appName").val();
		if($.trim(appName) == '') {
			alert('系统名不能为空。');
			return ;
		}
		
		var url = 'addAppName.action?appName=' + appName;
		
		$.ajax({
			url: url,
			type: 'GET',
			cache: false,
			success: function(data) {
				if(data.appAdded == false) {
				addMessageInfo("red",'该应用系统已经存在，无法重复添加。');
				} else {
					addMessageInfo("blue",'添加成功。');
					location.reload();
				}
			},
			
			error: function() {
				addMessageInfo("red",'添加应用系统出错。');
				return ;
			}
		});
		
		
	});
	
	
});
</script>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">监控中心</a></li>
		<li><a href="#">基础数据维护</a></li>
		<li class="last"><a href="#">设定系统负责人</a></li>
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
			<form onsubmit="return false;">
				<table>
					<tr><th>
						<s:select name='appSel' list='appLists' listKey='id' listValue='appName' ></s:select>
						<button id="searchBtn" class="orange">
							<span>查询</span>
						</button>
						
						<input size="40" type='text' id='appName' placeholder="若系统不在下拉框中，请在此输入系统名来添加"/>
						<button id="addBtn" class="blue">
							<span >添加</span>
						</button>
					</th></tr>
				</table>
			</form>
		</div>
	</div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="box">
		<div class="title">
			负责人信息
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="crudSupporter" namespace="/montor" method="post">
				<div class="row">
					<div class="tableLeft">
						<label>第一负责人姓名<span>*</span></label>
						<input placeholder="通常设置为系统的海尔接口负责人" class="inputInMiddle" type='text' id='ownerName' name='issueSupporter.ownerName' class='evenCol'/>
					</div>
					<div class="tableRight">
						<label>第二负责人姓名</label>
						<input placeholder="通常设置为开发商负责人" type='text' id='sptOwnerName' name='issueSupporter.sptOwnerName' class='evenCol'/>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>第一负责人电话<span>*</span></label>
						<input placeholder="用来接收告警短信的手机号" type='text' id='ownerPhone' name='issueSupporter.ownerPhone' class='evenCol'/>
					</div>
					<div class="tableRight">
						<label>第二负责人电话</label>
						<input placeholder="用来接收告警短信的手机号" type='text' id='sptOwnerPhone' name='issueSupporter.sptOwnerPhone'/>
					</div>
				</div>
				<div class="row">
					<div class="tableLeft">
						<label>第一负责人邮件<span>*</span></label>
						<input placeholder="用来接收告警信息的邮箱地址" type='text' id='ownerEmail' name='issueSupporter.ownerEmail' class='evenCol'/>
					</div>
					<div class="tableRight">
						<label>第二负责人邮件</label>
						<input placeholder="用来接收告警信息的邮箱地址" type='text' id='sptOwnerEmail' name='issueSupporter.sptOwnerEmail'/>
					</div>
				</div>
				<div class="row">
					<label>开发商支持人员邮件</label>
					<div class="right"><textarea placeholder="可以设置多个邮箱地址，中间用';'分割"  id='sptEmail' name='issueSupporter.sptEmail' class='evenCol'></textarea></div>
				</div>
				<div class="row">
					<label>开发商支持人员电话</label>
					<div class="right"><textarea placeholder="可以设置多个手机号，中间用';'分割" id='sptPhone' name='issueSupporter.sptPhone' class='evenCol'></textarea></div>
				</div>
				<div class="row">
					<div class="right">
							<button type="submit" class="orange"><span>保存</span></button>
						<button type="reset"><span>重置</span></button>
					</div>
				</div>
				<input type='hidden' name="appId" id='appId' />
				<input type="hidden" id="sptId" name="supporterId"/>
			</s:form>
		</div>
	</div>
</div>
</body>
</html>
