<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全业务级别分布比较</title>
<link rel="stylesheet" type="text/css"
	href="${staticURL}/css/hop/grid/custom-grid-style/jquery-ui-1.8.21.custom.css" />
<link type="text/css" rel="stylesheet"
	href="${staticURL}/style/jquery.jqplot.min.css" />
<style type="text/css">
.jqplot-table-legend{
	width:55px;
	margin-top: 1px;
}
</style>
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.canvasAxisTickRenderer.min.js"></script>
<script type="text/javascript" src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>

<script
	src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pieRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script> 
<script src="${staticURL}/scripts/jqplot/jqplot.ohlcRenderer.js"></script>
<script type="text/javascript"
	src="${staticURL}/scripts/jqplot/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){  
	var levelData = null;
	var ytick = new Array();
	var labelsContent = new Array();
	$.ajaxSetup ({ 
		async : false,
		cache: false 
		});
	$.getJSON("levelLocated.action",
			function call(data){ 
			 	ifModified:true;
			    for(k=0;k<data.timeoutLevelList.length;k++){
			    	//alert(data.timeoutLevelList[k].lvName);
					labelsContent[k] = data.timeoutLevelList[k].lvName; 
				}  
				for(j=0;j<=data.appLevelList.length;j++){
					levelData = data.appLevelList;
				}
				for(i=0;i<=data.appList.length+1;i++){
					var ytickeContent =  new Array(); 
					if(i==0){
						ytickeContent[0] = -1;
						ytickeContent[1] = "";
					}else if(i==data.appList.length+1){
						ytickeContent[0] = data.appList.length;
						ytickeContent[1] = "";
					}else{ 
					ytickeContent[0] = i-1;
					ytickeContent[1] = data.appList[i-1].appName;
				}
					ytick[i] = ytickeContent;
				} 
	});  
	//plot4 = $.jqplot('chart4', [[[2,1], [6,2], [7,3], [10,4]], [[7,1], [5,2],[3,3],[2,4]], [[14,1], [9,2], [9,3], [8,4]]], {      
	//plot4 = $.jqplot('chart4', [[[0,"${HIBC.SERVER.NAME}"],[3,"AUTHENTICATION"],[4,"BASICDATA"],[5,"BASICDATA-APP"]]], {    
      plot4 = $.jqplot('chartContent', levelData, {
    //plot4 = $.jqplot('chart4', [[[2,1], [6,2], [7,3], [10,4]], [[7,1], [5,2],[3,3],[2,4]], [[14,1], [9,2], [9,3], [8,4]]], {      
		stackSeries: true,            
		captureRightClick: true,            
		seriesDefaults:{               
			renderer:$.jqplot.BarRenderer,               
			shadowAngle: 135,               
			rendererOptions: {                   
				barDirection: 'horizontal',                   
				highlightMouseDown: true                  
				},               
				pointLabels: {show: true, 
					formatString: '%d'}           
				},            
				legend: {               
					show: true,               
					location: 'e',               
					//placement: 'outside',
					labels: labelsContent
					},            
					axes: {               
						yaxis: {                   
							//renderer: $.jqplot.CategoryAxisRenderer,
							//ticks:[[0,'0U'],[1,'0U'],[2,'0U'],[3,'0U'],[4,'0U'],[5,'0U'],[6,'0U'],[7,'0U'],[8,'0U'],[9,'0U'],[10,'0U'],[11,'0U'],[12,'0U'],[13,'0U'],[14,'0U'],[15,'0U'],[16,'0U'],[17,'0U'],[18,'0U'],[19,'0U'],[20,'0U'],[21,'0U'],[22,'0U'],[23,'0U'],[24,'0U'],[25,'0U']]
							ticks:ytick
						}
						}
					});   
 
$("#levelPlotBtn").click(function(){
		$('#levelChartContent').empty();		
	/* 	var start = $("#start").val();
		var end = $("#end").val();
		var type = $("#type").val(); */
		var type = document.getElementById("date");
		var date = type.value;
 		//var date = type.options[type.selectedIndex].value;
		var levelData = null;
		var ytick = new Array();
		var labelsContent = new Array();
		
	    
	    
	   	var chartLevel = null;
		var chartytick = new Array();
		var chartlabelsContent = new Array();
		var appList = new Array();
		var allLevel = new Array();
	      $.ajaxSetup ({ 
	  		async : false,
	  		cache: false 
	  		});
	  	$.getJSON("levelStatistics.action?date="+date,
	  			function call(data){ 
	  			 	ifModified:true;
	  				appList = data.appLists;
	  				allLevel = data.levels;
	  			    for(k=0;k<data.returnLv.length;k++){
	  			    	/* alert(data.timeoutLevelList[k].lvName); */
	  					chartlabelsContent[k] = 'LV'+(k+1); 
	  				}   
	  				for(j=0;j<=data.levels.length;j++){
	  					chartLevel = data.levels;
	  				}
	  				for(i=0;i<=data.appLists.length+1;i++){
	  					var ytickeContent =  new Array(); 
	  					if(i==0){
	  						ytickeContent[0] = -1;
	  						ytickeContent[1] = "";
	  					}else if(i==data.appLists.length+1){
	  						ytickeContent[0] = data.appLists.length;
	  						ytickeContent[1] = "";
	  					}else{ 
	  					ytickeContent[0] = i-1;
	  					ytickeContent[1] = data.appLists[i-1].appName;
	  				}
	  					chartytick[i] = ytickeContent;
	  				} 
	  	});  
	  	//plot4 = $.jqplot('chart4', [[[2,1], [6,2], [7,3], [10,4]], [[7,1], [5,2],[3,3],[2,4]], [[14,1], [9,2], [9,3], [8,4]]], {      
	  	//plot4 = $.jqplot('chart4', [[[0,"${HIBC.SERVER.NAME}"],[3,"AUTHENTICATION"],[4,"BASICDATA"],[5,"BASICDATA-APP"]]], {    
	        plot4 = $.jqplot('levelChartContent', chartLevel, {
	      //plot4 = $.jqplot('chart4', [[[2,1], [6,2], [7,3], [10,4]], [[7,1], [5,2],[3,3],[2,4]], [[14,1], [9,2], [9,3], [8,4]]], {      
	  		stackSeries: true,            
	  		captureRightClick: true,            
	  		seriesDefaults:{               
	  			renderer:$.jqplot.BarRenderer,               
	  			shadowAngle: 135,               
	  			rendererOptions: {                   
	  				barDirection: 'horizontal',                   
	  				highlightMouseDown: true                  
	  				},               
	  				/* pointLabels: {show: true, 
	  					formatString: '%d'}    */        
	  				},            
	  				legend: {               
	  					show: true,               
	  					location: 'e',               
	  					//placement: 'outside',
	  					labels: chartlabelsContent
	  					},            
	  					axes: {               
	  						yaxis: {                   
	  							//renderer: $.jqplot.CategoryAxisRenderer,
	  							//ticks:[[0,'0U'],[1,'0U'],[2,'0U'],[3,'0U'],[4,'0U'],[5,'0U'],[6,'0U'],[7,'0U'],[8,'0U'],[9,'0U'],[10,'0U'],[11,'0U'],[12,'0U'],[13,'0U'],[14,'0U'],[15,'0U'],[16,'0U'],[17,'0U'],[18,'0U'],[19,'0U'],[20,'0U'],[21,'0U'],[22,'0U'],[23,'0U'],[24,'0U'],[25,'0U']]
	  							ticks:chartytick
	  						}
	  						}
	  					});   
	      
	  					 

	      
	      
	      //点击事件
	         $('#levelChartContent').bind('jqplotDataHighlight',
	            function (ev, seriesIndex, pointIndex, data) {
	    		    /* var appId = appIds[pointIndex];	// 获取点击柱子对应appid
	    			var appName = ticks[pointIndex]; // 获取点击柱子对应appname   */
	    			//var appid = appList[pointIndex].id;
	    		//	var oneLevel = allLeves[pointIndex];
	    			alert(data[0]);
	    			
	    			 /* for(k=0;k<data.returnLv.length;k++){
	   					chartlabelsContent[k] = 'LV'+(k+1); 
	   				}   */ 
	    			 
	   		 });  
	     }); 
	 }); 
</script>
</head>
<body>
<div id="breadcrumbs">
		<div>
			<div>
				<ul>
					<li class="first"></li>
					<li><a href="#">监控中心</a></li>
					<li><a href="#">综合监控报告</a></li>
					<li class="last"><a href="#">全业务级别分布比较</a></li>
				</ul>
			</div>
		</div>
	</div>
   <div class="section">
		<div class="message blue">
			<span><b>说明：</b>本页面显示系统level分布情况对比。</span>
		</div>
	</div>  
	
	<div class="section" id='cretial'> 
		<div class="box">
			<div class="title">
				全业务level分布统计图
				<span class="hide"></span>
			</div>
			<div class="content">
			    <div id="chartContent" style="width: 100%;height: 500px;"></div>
			</div>
		</div> 
	</div>  
	<div class="section" id='cretialChart'>
		<div class="box">
			<div class="title">
				请选则业务系统和时间段： <span class="hide"></span>
			</div>
			<div class="content"> 
				<table>
					<tr>
						<th> 
							<%-- 从<sj:datepicker id="start" name="start"
								label="Select a Date/Time" timepicker="true"
								displayFormat="yy-mm-dd" /> 到 <sj:datepicker id="end"
								name="end" label="Select a Date/Time" timepicker="true"
								displayFormat="yy-mm-dd" /> --%>
							<select id="date"><option value="1">一月</option><option value="2">一周</option><option value="3">一天</option></select>
							<button type="button" id="levelPlotBtn" class="orange">
								<span>统计</span>
							</button>
						</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
		<div class="section" id='cretial'> 
		<div class="box">
			<div class="title">
				全业务level平均分布统计图
				<span class="hide"></span>
			</div>
			<div class="content">
			    <div id="levelChartContent" style="width: 100%;height: 900px;"></div>
			</div>
		</div> 
	</div>
 
</body>
</html>