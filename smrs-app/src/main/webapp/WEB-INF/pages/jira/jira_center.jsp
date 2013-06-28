<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Jira监控中心</title>
<link type="text/css" rel="stylesheet"
	href="${staticURL}/style/jquery.multiselect.css" />
<script src="${staticURL}/../scripts/jquery.multiselect.min.js"></script>
<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
<script src="${staticURL}/../scripts/jquery.ui.datepicker.js"></script>
<script language="javascript">
	var data,data1,currentFocus;
	$(document).ready(function(){
		//若已加载查询结果，则显示下一步按钮
		if($(".records input[name='price']").eq(0).val()==null){
			$("#nextStep").hide();
		}else{
			$("#nextStep").show();
		}
		$("#describe").hide();
		$.ajax({
			url:'selePro',
			type:'post',
			data:null,
			success:function(d) {
				data=d;
				data1=data;
				hehe(1);
			}
		});
		$("#pname").click(function(){
			$("#protab").show();
		});
		$("#pname").bind("input propertychange",function(){
			dataFilter();
		});
		$("#pagenum").bind("input propertychange",function(){
			hehe($("#pagenum").val());
		});
		//鼠标点击别处时项目名称选择框关闭
		$("#prodiv").click(function(event){
			event.stopPropagation();
		});
		$(document).click(function(){
			hidepro();
		});
		//字段约束
		$.each($(".records")
			,function(){
				setNumberType($(this).find("input[name='price']"));
				setNumberType($(this).find("input[name='ContractDays']"));
		});
		$.each($("#describe td input")
			,function(){
				setNumberType($(this));
		});
	});
	function hehe(count){
		if(!(/^[0-9]+$/).exec(count)){
			alert("请输入数字");
			return;
		}
		if(count<1){
			count=1;
			hehe(count);
		}else if(count>(data1.length/5+1)){
			count=parseInt(data1.length/5+1);
			hehe(count);
		}else{
		var index = 5*(count-1);
		var table = "<table>";
		for(var i=0;i<5;i++){
			table+="<tr>";
			if(index<data1.length){
				table+="<td><a href='javascript:proClick(\""
						+data1[index].pname+"\",\""+data1[index].pkey+"\")'>"
						+data1[index].pname+"</a></td>"
						+"<td><a href='javascript:proClick(\""
						+data1[index].pname+"\",\""+data1[index].pkey+"\")'>"
						+data1[index].pkey+"</a></td>";
				index++;
			}
			table+="</tr>";
		}
		table += "</table>";
		document.getElementById("tab").innerHTML=table;
		document.getElementById('nowpage').value = count; 
		}
		$("#pageinfo").html(parseInt(data1.length/5+1));
		$("#pagenum").val(count);
	}
	//项目名称模糊查询
	function dataFilter(){
		var val=uniencode($("#pname").val()).toLowerCase();
		//alert(val);
		var reg=new RegExp(val, "g");
		var data2=[];
		var index=0;
		for(var i=0;i<data.length;i++){
			var pname=uniencode(data[i].pname).toLowerCase();
			var pkey=data[i].pkey.toLowerCase();
			if(pname.match(reg) || pkey.match(reg)){
				data2[index++]=data[i];
			}
		}
		data1=data2;
		hehe(1);
	}
	function proClick(pname,pkey){
		$("#pname").val(pname);
		$("#proSele").val(pkey);
		$("#protab").hide();
	}
	function hidepro(){
		$('#protab').hide();
	}
	function showDescribe(){
		$("#result").hide();
		$("#describe").slideDown(500);
	}
	function showResult(){
		$("#describe").hide();
		$("#result").slideDown(300);
	}
	function submitResult(){
		var params="";
		var isOk=true;
		if($(".records").size()<1){
			alert("请先进行查询");
			return;
		}
		$.each($(".records"),function(n,value){
			var name=$(this).find("#displayname").html();
			var price=$(this).find("input[name='price']").val();
			if(price==""){
				price=0;
			}
			if(price==0){
				alert(name+"的人天单价不可为0，请重新填写。");
				isOk=false;
				return;
			}
			var contractDays=$(this).find("input[name='ContractDays']").val();
			var priceinfo=$(this).find("input[name='priceinfo']").val();
			params+=("price="+price+"&ContractDays="+contractDays+"&priceinfo="+priceinfo+"&");
		});
		if(isOk==false){
			return;
		}
		//alert(params);
		$.ajax({
			url:'<%=path %>/jira/sysEval.action',
			type:'post',
			async:false,
			data:params,
			success:function(data) {
				$("#count1").html(data);
				//$("#nextStep").show();
				$("#countMsg").show(600);
			},
			dataType:"json"
		});
	}
	//约束一个input只能输入数字、退格、ctrl和tab，不能使用输入法。传入参数为一个jquery对象
	function setNumberType(ele){
		ele.bind({
			keydown:function(e){
				var keyCode;
				if ($.browser.msie) {  // 判断浏览器
					keyCode=e.keyCode;
				} else {
					keyCode=e.which;
				}
				if ( ((keyCode > 47) && (keyCode < 58)) || (keyCode == 8) || (keyCode == 17) || (keyCode == 9)) { 　// 判断键值  
					return true;
				}else {
					return false;
				}
			},
			focus:function() {
                this.style.imeMode='disabled';   // 禁用输入法,禁止输入中文字符
            }
		});
	}
	//将汉字转换为unicode
	function uniencode(text){
		text = escape(text.toString()).replace(/\+/g, "%2B");
		var matches = text.match(/(%([0-9A-F]{2}))/gi);
		if (matches){
			for (var matchid = 0; matchid < matches.length; matchid++){
				var code = matches[matchid].substring(1,3);
	 			if (parseInt(code, 16) >= 128){
	  				text = text.replace(matches[matchid], '%u00' + code);
	 			}
	  		}
	 	}
		text = text.replace('%25', '%u0025');
		return text;
	}

</script>
<style type="text/css">
	#protab{
		display: none;
		position: absolute;  
		width: 320px;
		background-color: white;  
		z-index:1002;  
		overflow: auto;
		text-align:center;
		background-attachment:fixed;
		border:1px #E9E9E9 solid;
		padding-bottom:5px;
	}
	/*
	#protab h3{padding-left:8px;padding-top:3px;text-align:left;height:35px;line-height:35px;color:#000;background-color:#E9E9E9;}
	*/
	#tab{margin-top:10px;height:160px;}
	#tab table td{border:0}
	#protab a{text-decoration: none;}
	.button1 {float:left;margin-left:10px;background-color: #E8E8E8; border: 1px solid #CCC; color: #000;  display: block; padding:0 3px; text-align: center; width: 60px;height:22px;line-height:22px;}
	#pagenum{width:21px;}
	#bottom a img{position:relative;top:3px}
	#bottom{margin-top:10px;background-color:#A7A7A7}
</style>
</head>
<body>
	<div id="breadcrumbs">
		<div>
			<div>
				<ul>
					<li class="first"></li>
					<li><a href="#">监控中心</a></li>
					<li><a href="#">应用系统监控</a></li>
					<li class="last"><a href="#">监控中心</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section" >
		<div class="box">
		<div class="title">
			查询条件
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:form action="getdisplayname" namespace="/jira" method="post" id="searchUserForm">
				<table>
					<tr>
						<th>项目名称：</th>
						<th> 
							<div id="prodiv" name="prodiv">
							<input type="text" id="pname" name="pname" value="<s:property value='pname'/>"/>
							<input type="hidden" id="proSele" name="proSele" value="<s:property value='proSele'/>"/>
							<div id="protab" name="protab">
								<div id="tab" name="tab">
								</div>
								<div id="bottom" name="bottom">
									<a href="javascript:hehe(1)">
										<img src="${staticURL}/../style/images/FristPage.png">
									</a>
									<a href="javascript:hehe(parseInt(document.getElementById('nowpage').value)-1)">
										<img src="${staticURL}/../style/images/PreviousPage.png">
									</a>
									<input type="text" id="pagenum" name="pagenum" style="height:10px;"/>
									共<span id="pageinfo" name="pageinfo"></span>页
									<a href="javascript:hehe(parseInt(document.getElementById('nowpage').value)+1)">
										<img src="${staticURL}/../style/images/NextPage.png">
									</a>
									<a href="javascript:hehe(parseInt(document.getElementById('pageinfo').innerHTML))">
										<img src="${staticURL}/../style/images/LastPage.png">
									</a>
									<input type="hidden" id="nowpage" name="nowpage" value=1/>
								</div>
							</div>
							</div>
						</th>
						<th>开始时间：</th>
						<th>
							<sj:datepicker   id="start" name="start" displayFormat="yy-mm-dd" />
						</th>
						
						<th>结束时间：</th>
						<th>
							<sj:datepicker   id="end" name="end" displayFormat="yy-mm-dd" />
						</th>
					
						<th>
							<button type="submit" ><span>查询</span></button>
						</th>
					</tr>
				</table>
			</s:form>
		</div>
		</div>
	</div>
	<s:form  action="javascript:;" namespace="/jira" method="post">
	<div id='result' class='section'>
	<div class="box">
	<div class="title">
		查询结果<span class="hide"></span>
	</div>
	<div class="content" style="overflow: scroll;">
	<div class="dataTables_wrapper"><div class="dataTables_filter"></div>
		<table class="sorting"> 
			<thead>
				<tr>
					<th>项目成员</th>
					<th>人天单价</th>
					<th>合同人天</th>
				</tr>
			</thead>
			<tbody>
				<div class="message inner blue" style="width:800px;" id="countMsg">
					<span>
						<b>提示：</b>成功影响<div id="count1" style="display:inline;">0</div>条数据
					</span>
				</div>	
				<s:iterator value="userGroupList.records" status="status">
					<tr class="records">
					 	<td id="displayname"><s:property value="displayname"/></td>
					 	<td><input name="price" type="text" value="<s:property value="price"/>" /></td>
					 	<td><input name="ContractDays" type="text" value="<s:property value="contractdays"/>" maxlength="3" /></td>
					 	<input type="hidden" name="priceinfo" value="<s:property value="id"/>,<s:property value="username"/>,<s:property value="projectkey"/>,<s:property value="pid"/>">
					</tr>  
				</s:iterator>
			</tbody>
			<tfoot>
				<tr>
					<td colspan = "3"  align='right'>
						<button onclick="submitResult()"><span>保存</span></button>
						<button id="nextStep" onclick="showDescribe()" ><span>下一步</span></button>
					</td>
				</tr>
			</tfoot>
		</table>	
	</div>
	</div>
	</div>
	</div>
	</s:form>
	<s:form action="timelyrate" namespace="/jira" method="post" id="timelyrateForm" >
	<div id='describe' class='section'>
		<span style="display:none" id="hidspan">	
			<s:textfield name="searchpkey" id="searchpkey"/>
			<s:textfield name="searchstart" id="searchstart"/>
			<s:textfield name="searchend" id="searchend"/>
		</span>
		<div class="box">
		<div class="title">
			<span class="hide"></span>
		</div>
		<div class="content">
			<table>
			<thead>
				<tr>
					<th>评测描述</th>
					<th>次数</th> 
				</tr>
			</thead>
				<tr>
					<td>*评测周期内宕机事故次数：</td>
					<td colspan=2><s:textfield name="sysPingceModel.dangJ" id="sysPingceModel.dangJ" maxlength="3" value="0" /></td>
				</tr>
				<tr>
					<td>*测评周期内开发人员表扬次数：</td>
					<td colspan=2><s:textfield name="sysPingceModel.biaoY" id="sysPingceModel.biaoY" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td>*测评周期内开发人员投诉次数：</td>
					<td colspan=2><s:textfield name="sysPingceModel.touS" id="sysPingceModel.touS" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td>*工作日志、文档等产出物不合规次数：</td>
					<td colspan=2><s:textfield name="sysPingceModel.workB" id="sysPingceModel.workB" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td>*需求文档未及时更新次数：</td>
					<td colspan=2><s:textfield name="sysPingceModel.xuqWupdate" id="sysPingceModel.xuqWupdate" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td>*设计文档未及时更新次数：</td>
					<td><s:textfield name="sysPingceModel.sjiWupdate" id="sysPingceModel.sjiWupdate" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td>*代码未及时更新次数：</td>
					<td><s:textfield name="sysPingceModel.daimWupdate" id="sysPingceModel.daimWupdate" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td>*未提交代码次数：</td>
					<td><s:textfield name="sysPingceModel.DaimweiTijiao" id="sysPingceModel.DaimweiTijiao" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td>*工作日志及文档不符合规范次数：</td>
					<td><s:textfield name="sysPingceModel.workBgf" id="sysPingceModel.workBgf" maxlength="3" value="0"/></td>
				</tr>
				<tr>
					<td align="right" colspan=2>
					<button  type="submit"  onclick="submitForm('timelyrate');"><span>计算</span></button>
					<button type="button" onclick="showResult()"><span>上一步</span></button>
					</td>
				</tr>
			</table>
		</div>
		</div>
	</div>
	</s:form>
	<script type="text/javascript">
		function submitForm(action){
			$("#searchpkey").val($('#proSele').val());
			$("#searchstart").val($('#start').val());
			$("#searchend").val($('#end').val());
			$.each($("#describe td input")
					,function(){
						if($(this).val()==""){
							$(this).val(0);
						}
				});
			//var form = document.getElementById("timelyrateForm");
			//form.action="${dynamicURL}/jira/"+action+".action";
			//form.submit();
		}
	</script>
</body>
</html>
