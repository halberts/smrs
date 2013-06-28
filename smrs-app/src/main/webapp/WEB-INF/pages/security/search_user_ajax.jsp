<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="security" uri="/security-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户查询</title>

<link rel="stylesheet" type="text/css" href="${staticURL}/style/hopCss/hop.css" />
<link rel="stylesheet" type="text/css" href="${staticURL}/style/hopCss/custom-grid-style/jquery-ui-1.8.21.custom.css" />
<script src="${staticURL}/scripts/jquery-1.7.2.min.js"></script>
<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
<script src="${staticURL}/scripts/jquery.ui.datepicker.js"></script>
<script src="${staticURL}/scripts/hop.js" type="text/javascript"></script>

<!-- 因为在style.css中定义了表格样式，故在此除去，防止干扰jqGrid默认样式 -->
<style>
.bd .col-2 dl dd table td{ padding:2px 2px 2px 0px;}
.bd .col-2 dl dd table th{ padding-left:0px;}
</style>


</head>
<body>
<dt>
	<h3>用户列表</h3>
</dt>
<dd class="tab1">
<jsp:include page="/common/messages.jsp"/>
 <s:form method="get" id="searchUserForm" onsubmit="return false;">
	<table class="form_table">
		<tr>
			<th>登录名：</th>
			<td>
			<s:textfield name="user.name" id="name"/></td>
			<th>用戶名：</th>
			<td>
			<s:textfield name="user.nickName" id="nickname"/></td>
			<th>Email：</th>
			<td><s:textfield name="user.email" id="email"/></td>
			<td>
			<!-- 这里引用jquery-1.7.2，所以将sj:submit替换了 -->
			&nbsp;&nbsp;<input type="submit" value="查询" onclick="searchBtnClicked()" class="abn db"  />&nbsp;&nbsp;
			<input type="button" value="新建" onclick="creatUser()" class="abn db" />&nbsp;&nbsp;
			<input type="submit" value="导出" onclick="doExport();" class="abn db"/>&nbsp;&nbsp;
			<input type="button" value="隐藏列" id="hideBtn" class="abn db" />
			<input type="button" value="提交" id="sbm" class="abn db" />
			<input type="button" value="图表" id="chart" class="abn db" />
			</td>
		</tr>
	</table>
</s:form>


</dd>

<div id='contaier'>
		<s:select id="struts2select" list="{'aa','bb','cc'}" theme="simple"></s:select>
	 	<table id="list1"></table>
		<div id="pager1"></div>		
</div>	
<script type="text/javascript">
$("#chart").click(function(){
	var frm = $("<form></form>");
	frm.attr('action', '${dynamicURL}/security/searchUserJsonAction_getChartData.action');
	frm.attr('method', 'post');
	frm.appendTo('body');
	frm.css('display', 'none');
	frm.submit();
});


$("#sbm").click(function(){
	var grid = $("#list1");
	var frm = $("<form></form>");
	frm.attr('action', '${dynamicURL}/security/searchUserJson.action');
	frm.attr('method', 'post');
	var ids = grid.jqGrid('getDataIDs');
	for(var i = 0; i < ids.length; i++) {
		var rowId = ids[i];
		var rowdata = grid.jqGrid('getRowData', rowId);
		alert(rowdata.email);
		var input1 = "<input type='hidden' name='myusers["+i+"].name' value='"+rowdata.name+"'/>";
		frm.append(input1);
		
		var input2 = "<input type='hidden' name='myusers["+i+"].nickName' value='"+rowdata.nickName+"'/>";
		frm.append(input2);
		
		var input3 = "<input type='hidden' name='myusers["+i+"].email' value='"+rowdata.email+"'/>";
		frm.append(input3);
		
		var input6 = "<input type='hidden' name='myusers["+i+"].currentLoginIp' value='"+rowdata.currentLoginIp+"'/>";
		frm.append(input6);
		
	}
	frm.appendTo('body');
	frm.css('display', 'none');
	frm.submit();
	
});

function subgo(){
	alert('ssss');
}
 	$(document).ready(function(){ 		
 		$("#contaier").width($(".tab1").width());
 		
		$("#list1").jqGrid({
			url: 'searchUserJson.action',
			colNames: ['登录名', '用户名', '邮箱', '类型', '状态', '最近登陆IP', '最近登陆时间', '可编辑文本框', '日期控件', '自定义复选框','自定义下拉框','超链接', '自定义按钮'],
			colModel: [
				{name:'name',index:'name', frozen:true, align:"center", editable:false},
		   		{name:'nickName',index:'nickName', align:"center"},
		   		{name:'email',index:'email', align:"center", width: 200, editable: true},
		   		{name:'type',index:'type',  align:"center", formatter: userType},
		   		{name:'status',index:'status', align:"center", formatter: satus_type},	
		   		{name:'currentLoginIp',index:'currentLoginIp',align:"center"},	
		   		{name:'lastLoginTime',index:'lastLoginTime', align:"center", formatter:"date", formatoptions:{srcformat: 'Y-m-dTH:i:s', newformat:'Y-m-d H:i:s'}, width: 200},
		   		{name:'editTextbox',index:'currentLoginIp',align:"center", editable:true},
		   		{name:'sdate',index:'sdate',width:90, editable:true}, 
		   		{name:'checkbox',index:'checkbox',align:"center"},
		   		{name:'select',index:'select',align:"center"},
		   		{name:'alink',index:'alink',align:"center"},
		   		{name:'btn',index:'btn',align:"center"}
		   	],
		   	multiselect: true,
			autowidth:true,
		   	shrinkToFit: false,
			height: 420,
			rowNum: 20,
			pager: '#pager1',
			jsonReader:{
				root:"pager.records",
				total: "total",
				page: "page",
				records: "records",
				repeatitems: false,
				id:"0"
			},
			
			gridComplete: function() {
		        var grid = jQuery("#list1");
		        var ids = grid.jqGrid('getDataIDs');	// 获取表格所有 行id
		        for (var i = 0; i < ids.length; i++) {
		            var rowId = ids[i];
		            var btn = "<input type='button' value='操作' onclick='operate()'/>";
		       //     var sel = '<select id="sel_' + rowId + '"  ><option value="volvo">Volvo</option><option value="saab">Saab</option><option value="mercedes">Mercedes</option><option value="audi">Audi</option></select>';
		            var sel = '<select>'+getOptionsFromSelectTag("struts2select")+'</select>';
		            var chk = "<input type='checkbox' />";
		            var alk = "<a href='#'><u>删除</u></a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='subgo()'><u>特价</u></a>";
		            grid.jqGrid('setRowData', rowId, { btn: btn,  select: sel, checkbox: chk, alink: alk});
		            
		        }
		        
		    },
		    cellEdit: true,
		    cellsubmit: 'clientArray',
		    
		    afterEditCell: function (id,name,val,iRow,iCol){ 
          		if(name=='sdate') { 
          			jQuery("#"+iRow+"_sdate","#list1").datepicker({dateFormat:"yy-mm-dd"}); 
      			} 
          	}
				
		});
	
		$("#list1").jqGrid('setColProp', 'name', {frozen:true});
		$("#list1").jqGrid('setGridParam', {cellEdit: false});
		$("#list1").jqGrid('setFrozenColumns');
		$("#list1").jqGrid('setGridParam', {cellEdit: true});
		$("#hideBtn").click(function(){
			var options = [];
			$("#list1").setColumns(options);
			
		});
	});
 	

 	
 	
function operate(){
 	alert('操作自定义');
}
 	
//删除
function delUser(id){
	if (!confirm("确定要删除吗？")){
		return;
	}
	window.location.href = "${dynamicURL}/security/deleteUser.action?userId="+id;
}
function creatUser(){
	window.location.href = "${dynamicURL}/security/createUserInit.action";
}
function submitForm(action){
	$('#gridtable').trigger('reloadGrid');
	return false;
}
function doExport(){
	var form = document.getElementById("searchUserForm");
	form.action="${dynamicURL}/security/exportUserList.action";
	form.onSubmit=function(){
		return true;
	}
	form.submit();
	form.onsubmit=function(){
		return false;
	}
}
var TYPE_MAP = {0:'普通账号',1:'域账号'};

function userType(cellvalue, options, rowObject) {
	return TYPE_MAP[cellvalue];
}

var STATUS_TYPE = {0: '禁用', 1: '启用'}
function satus_type(cellvalue, options, rowObject) {
	return STATUS_TYPE[cellvalue];
}

/*搜索按钮*/
function searchBtnClicked(){
	var query = $('#searchUserForm').serialize(); // 序列化参数
	$("#list1").jqGrid('setGridParam',{url: 'searchUserJson.action?'+query}).trigger("reloadGrid");
}


</script>
</body>
</html>