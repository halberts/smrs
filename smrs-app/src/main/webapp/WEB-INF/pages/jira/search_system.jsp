<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
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
<link rel="stylesheet" type="text/css" href="${staticURL}/style/hopCss/custom-grid-style/jquery-ui-1.8.21.custom.css" />
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pieRenderer.min.js"></script>
<script src="${staticURL}/scripts/flot/jquery.flot.js"></script>
<script type="text/javascript" src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script>
<style>
.jqplot-axis {
	font-size: 1em;
}

table.jqplot-table-legend,table.jqplot-cursor-legend {
	background-color: rgba(255, 255, 255, 0.6);
	border: 1px solid #CCCCCC;
	font-size: 1em;
	position: relative;
}

table.jqplot-table-legend td {
	padding: 2px;
	white-space: nowrap;
}

.jqplot-point-label {
	font-size: 1em;
	z-index: 2;
}


table.jqplot-table-legend {
  width: auto !important;
}




</style>
<script language="javascript">
var data1,data2,date3,data4,a1,b1,c1;
var linesContent = new Array();
var labelsContent = new Array();
$(document).ready(function(){
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
//表的ajax	

	
	
	//及时率饼状图点击事件
	$("#work").click(function(){
	
		var url='workflow';
		var title='开发及时率全业务对比';
		$('#cakeTit').html(title);
		huhu(url,title);
	//$("#_chartWidgetTemplate_").css("display","block");
	$("#line").css("display","none");
	$("#table").css("display","none");
	});
	
	//系统异常数饼状图点击事件
	$("#exct").click(function(){
		$("#line").css("display","none");
		$("#table").css("display","none");
		var url='sysExctCake';
		var title='系统异常数全业务对比';
		$('#cakeTit').html(title);
		huhu(url,title);
	//$("#_chartWidgetTemplate_").css("display","block");
	});
	//服务器健康异常数饼状图点击事件
	$("#healthWork").click(function(){
		$("#line").css("display","none");
		$("#table").css("display","none");
		var url='healthWorkCake';
		var title='服务器健康异常数量全业务对比';
		$('#cakeTit').html(title);
		huhu(url,title);
	//$("#_chartWidgetTemplate_").css("display","block");
	
	});
	//业务平均响应时间饼状图点击事件
	$("#aveTimeCake").click(function(){
		$("#line").css("display","none");
		$("#table").css("display","none");
		var url='aveTimeCake';
		var title='业务平均响应时间全业务对比';
		$('#cakeTit').html(title);
		huhu(url,title);
	//$("#_chartWidgetTemplate_").css("display","block");
	});	
//及时率线形图点击事件
$("#diagram").click(function(){
	
	
	var url='sameterm';
	var title='开发及时率近六个月走势';
	$('#lineTit').html(title);
	diagram(url,title);
		$("#_chartWidgetTemplate_").css("display","none");
		$("#line").css("display","block");
		$("#table").css("display","none");
	});
//系统异常数线形图点击事件
$("#exctLine").click(function(){

	
	var url='exctLine';
	var title='系统异常数近六个月走势';
	$('#lineTit').html(title);
	diagram(url,title);
		$("#_chartWidgetTemplate_").css("display","none");
	$("#line").css("display","block");
	$("#table").css("display","none");
	});
	//服务器健康异常数线形图点击事件
$("#healthLine").click(function(){
	
	
	var url='healthLine';
	var title='服务器健康异常数近六个月走势';
	$('#lineTit').html(title);
	diagram(url,title);
		$("#_chartWidgetTemplate_").css("display","none");
	$("#line").css("display","block");
	$("#table").css("display","none");
	});
//业务平均响应时间线形图点击事件
$("#aveTimeLine").click(function(){
	
	var url='aveTimeLine';
	var title='业务平均响应时间近六个月走势';
	$('#lineTit').html(title);
	diagram(url,title);
		$("#_chartWidgetTemplate_").css("display","none");
	$("#line").css("display","block");
	$("#table").css("display","none");
	});
//业务平均响应时间表格点击事件
	$("#aveTimeTable").click(function(){
		
		a1='Action名字';
		b1='执行时间';
		c1='优化时间';
		$('#tbTit').html('业务平均响应时间Top优化');
		table('aveTimeTable');
		$("#_chartWidgetTemplate_").css("display","none");
		$("#line").css("display","none");
		$("#table").css("display","block");
	});
	//系统异常数量表格点击事件
	$("#exctTable").click(function(){
	
		a1='异常出现位置';
		b1='异常数量';
		c1='优化个数';
		$('#tbTit').html('系统异常数量Top优化');
		table('exctTable');
		$("#_chartWidgetTemplate_").css("display","none");
		$("#line").css("display","none");
		$("#table").css("display","block");
	});
	
	//服务器健康异常数量表格点击事件
	$("#healthTable").click(function(){
	
		a1='异常出现时间点(天)';
		b1='异常数量';
		c1='优化个数';
		$('#tbTit').html('服务器健康异常数量Top优化');
		table('healthTable');
		$("#_chartWidgetTemplate_").css("display","none");
		$("#line").css("display","none");
		$("#table").css("display","block");
	});
});

//画饼
function huhu(urlto,title){

	$.ajax({
		url:urlto,
		type:'post',
		data:'date1='+$('#start').val()+'&date2='+$('#end').val()+'&pkey1='+$('#proSele').val()+'&pname='+$('#pname').val(),
		 beforeSend:function(){
		       $('#wait1').css("display","block");
		    },
		    complete:function(){
		    	$('#wait1').css("display","none");
		    },
		success:function(data) {
			data2=data;
			drawCake(title);
		}
	});
	
}
function drawCake(title){
	var crts = $("#right");
    // 动态添加每个pie chart
    	var charRowDiv = null;
    		charRowDiv = createChartRow(2/2);
    		$(charRowDiv).remove();
    		$(charRowDiv).appendTo(crts);
    	var widetDivId = "div" +2;
    	var widgetDiv = createChartWidget(widetDivId,title);
    	$(widgetDiv).remove();
    	$(widgetDiv).appendTo(charRowDiv);
    	var chartArea = $(widgetDiv).find(".flot-graph")[0];
    	chartArea.id="pieChart"+2;
	$.jqplot("pieChart"+2, [data2], {
				seriesDefaults: {
		  			shadow: true, //阴影效果
		  			renderer: $.jqplot.PieRenderer, 
		  			rendererOptions: { 
		  				showDataLabels: true
		  			}
		  		},
		  		legend: {
		  			show: true
		  		}
		  		
			}
	);	
} 

//画线形图
function diagram(urlto,lineTitle){
	
	$("#placeholder").empty();
	$.ajax({
		url:urlto,
		type:'post',
		data:'pkey1='+$('#proSele').val(),
		 beforeSend:function(){
		       $('#wait3').css("display","block");
		    },
		    complete:function(){
		    	$('#wait3').css("display","none");
		    },
		success:function(data) {
			data3=data;
			drawLine(lineTitle);
		}
	});
}

function drawLine(lineTitle){
	
	//var mintime=data3[0][0].format('Y-m-d');
	var line1=[['2008-06-30 8:00AM',4], ['2008-7-30 8:00AM',6.5], ['2008-8-30 8:00AM',5.7], ['2008-9-30 8:00AM',9], ['2008-10-30 8:00AM',8.2]];
	 var plot1 =$.jqplot('placeholder', [data3], {
	      title:lineTitle,
	      gridPadding:{right:35},
	      axes:{
	        xaxis:{
	          renderer:$.jqplot.DateAxisRenderer,
	          tickOptions:{formatString:'%Y-%m-%d'},
	          min:data3[5][0],
	          tickInterval:'1 month'
	        }
	      },
	      series:[{lineWidth:4, markerOptions:{style:'square'}}]
	  });
}
//画表格
function table(urlto,a,b,c){
	
	$.ajax({
		url:urlto,
		type:'post',
		data:'date1='+$('#start').val()+'&date2='+$('#end').val()+'&pkey1='+$('#proSele').val()+'&pname='+$('#pname').val(),
		 beforeSend:function(){
		       $('#wait2').css("display","block");
		    },
		    complete:function(){
		    	$('#wait2').css("display","none");
		    },
		success:function(data) {
			data4=data;
			
			drawTable(1);
		}
	});
}
function drawTable(count){
	if(!(/^[0-9]{1,20}$/).exec(count)){
		alert("请输入数字");
		return;
	}
	if(count<1){
		count=1;
		drawTable(count,a,b,c);
	}else if(count>(data4.length/10+1)){
		count=parseInt(data4.length/10+1);
		drawTable(count,a,b,c);
	}else{
		var index = 10*(count-1);
		var table = "<div><div class='dataTables_filter'></div></div><table class='sorting'>";
		table+="<thead><tr><th>序号</th><th>"+a1+"</th><th>"+b1+"</th><th>优化状态</th><th>"+c1+"</th><th>优化率</th></th></tr></thead><tbody>"
		for(var i=0;i<10;i++){
			table+="<tr>";
				if(index<data4.length){
					table+="<td>"+(index+1)+"</td>";
					table+="<td>"+data4[index].name+"</td>";
					table+="<td>"+data4[index].tc+"</td>";
					table+="<td>"+0+"</td>";
					table+="<td>"+0+"</td>";
					table+="<td>"+0+"</td>";
					index++;
				}
			table+="</tr>";
		}
		table += "</tbody></table>";
	document.getElementById("table2").innerHTML=table;
	document.getElementById('nowpage1').value = count; 
	}
	$("#pageinfo1").html("共"+parseInt(data4.length/10+1)+"页,当前第"+count+"页");
}


function createChartRow(id){
	return $("<div class='section' id='charRowDiv"+id+"'></div>");
}
function createChartWidget(id,title){
	
	var widgetDiv = $("#_chartWidgetTemplate_").clone();
	$("#_chartWidgetTemplate_").remove();
	widgetDiv.id=id;
	$(widgetDiv).html($(widgetDiv).html().replace("_CHART_TITLE_",title));
	$(widgetDiv).css("display","block");
	return widgetDiv;
}
function createLineRow(){
	return $("<div  id='placeholder'></div>");
}



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
	var table = "<table id='youhua'>";
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
.table1 tr td{border:0}
.table1 {width:70%}
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
	#tab a{text-decoration: none;}
	#protab a{text-decoration: none;}
	.button1 {float:left;margin-left:10px;background-color: #E8E8E8; border: 1px solid #CCC; color: #000;  display: block; padding:0 3px; text-align: center; width: 60px;height:22px;line-height:22px;}
	#pagenum{width:21px;}
	#bottom a img{position:relative;top:3px}
	#bottom{margin-top:10px;background-color:#A7A7A7}
	#table2 table thead tr th{padding:3px}
	#table2 table tbody td{padding:3px}
	
</style>
</head>
<body>
	<div id="breadcrumbs">
		<div>
			<div>
				<ul>
					<li class="first"></li>
					<li><a href="#">项目监控</a></li>
					<li><a href="#">jira监控</a></li>
					<li class="last"><a href="#">系统全业务展示</a></li>
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
				<table >
					<tr>
						<th>选择系统：</th>
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
							<button type="button" id="search"><span>查询</span></button>
						</th>
					</tr>
				</table>
				
				</div>
				</div>
				</div>
	<div class="section" id="Top" style="display: none">
	<div class="box">
		<div class="title">
			查询结果
			<span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div><table class="table1" width="50%"> 
				
					<tr>
					<td>业务平均响应时间：</td>
					<td width="10px"><input id="time"  style="border: 0" name="time" value="" readonly/></td>
					<td><input id="aveTimeLine" type="button" value="同期"></td>
					<td><input id="aveTimeCake" type="button" value="全业务"></td>
					<td><input id="aveTimeTable" type="button" value="优化" ></td>
					</tr>
					<tr>
					<td>系统异常数量：</td>
					<td width="10px"><input  id="sysCount" name="sysCount"  style="border: 0" value="" readonly/></td>
					<td><input id="exctLine" type="button" value="同期"></td>
					<td><input id="exct" type="button" value="全业务"></td>
					<td><input id="exctTable" type="button" value="优化"></td>
					</tr>
					<tr>
					<td>服务器健康异常数量：</td>
					<td width="10px"><input id="healthCount" name="healthCount"  style="border: 0" value="" readonly/></td>
					<td><input id="healthLine" type="button" value="同期"></td>
					<td><input id="healthWork" type="button" value="全业务"></td>
					<td><input id="healthTable" type="button" value="优化"></td>
					</tr>
					<tr>
					<td>开发及时率：</td>
					<td width="10px"><input id="rate" name="rate" style="border: 0" value="" readonly/></td>
					<td><input id="diagram" type="button" value="同期"></td>
					<td><input id="work" type="button" value="全业务"></td>
					</tr>
				</table>
				
				</div>
		</div>
	</div>
</div>
<!-- 饼状图 -->
<div  id="_chartWidgetTemplate_" style="display: none;">
	<div class="box">
		<div id="cakeTit" class="title">
			_CHART_TITLE_
			<span class="hide"></span>
		</div>
		<div  class="content">
			<div class="chart-caption"></div>
			<div class="flot-graph" style="width: 100%;height:350px; padding: 0px; position: relative;"></div>
			<div  id="wait1" style="left:50%;position:fixed; display:none; top:50%;"><img src="${staticURL}/../style/images/waitting.gif"></div>
		</div>
	</div>
</div>
<!-- 表格 -->
<div id="table" class="section" style="display: none">
	<div class="box">
		<div id="tbTit" class="title">
			业务平均相应时间业务优化
			<span class="hide"></span>
		</div>
		<div class="content" style="padding:0">
			<div id="table2" >
				
			</div>
			<div id="wait2" style="left:40%;position:relative;display:none; top:45%;"><img src="${staticURL}/../style/images/waitting.gif"></div>
				<div    align="right" id="bottom1" name="bottom1">
				<input style="width:30px" id="turnpage" name="turnpage"/>
				<input  type="button" id="turn" name="turn" onclick="drawTable(parseInt(document.getElementById('turnpage').value))" value="跳转"/>
				<span id="pageinfo1" name="pageinfo1"></span>
	<input  align="right" type="hidden" id="nowpage1" name="nowpage1" value=1/>
	<input  align="right" type="button"  id="last1" name="last1" onclick="drawTable(parseInt(document.getElementById('nowpage1').value)-1)" value="上一页"/>
	<input  align="right" type="button"  id="next1" name="next1" onclick="drawTable(parseInt(document.getElementById('nowpage1').value)+1)" value="下一页"/>
				</div>
		</div>
	</div>
</div>
<!-- 线形图 -->
<div class="section" id="line" style="display: none">
<div class="box">
<div id="lineTit" class="title">
			业务功能平均响应时间近六个月走势
			<span class="hide"></span>
		</div>
		<div class="content">
		<div id="wait3" style="left:45%;position:relative;display:none; top:45%;"><img src="${staticURL}/../style/images/waitting.gif"></div>
<div  id="placeholder" ></div>
</div>
</div>
</div>
	<script type="text/javascript">
	var data4;
function submitForm(action){
	$("#searchpkey").val($('#proSele').val());
	$("#searchstart").val($('#start').val());
	$("#searchend").val($('#end').val());
	//var form = document.getElementById("timelyrateForm");
	//form.action="${dynamicURL}/jira/"+action+".action";
	//form.submit();

} 

$("#search").click(function(){
	$("#_chartWidgetTemplate_").css("display","none");
	$("#line").css("display","none");
	$("#table").css("display","none");
	if($('#end').val()==''||$('#start').val()==''||$('#pname').val()==''){
		alert('请将查询条件输入完整(包括选择系统、开始时间、结束时间)');
		return;
	}
	
	$.ajax({
		url:'searchjsl',
		type:'post',
		data:'date1='+$('#start').val()+'&date2='+$('#end').val()+'&pkey1='+$('#proSele').val(),
		success:function(data) {
			data4=data;
			search();
		
		}
	});
	
});
	
	function search(){
	
		$("#Top").show();
		$("#rate").val(data4.sysjsl+"%");
		$("#time").val(data4.sysave+"s");
		$("#sysCount").val(data4.sysexct+"个");
		$("#healthCount").val(data4.sysheal+"个");
	}
	//document.getElementById("Top").style.display="block";

function optimize(){
	document.getElementById("table").style.display="block";
}

</script>
</body>
</html>
