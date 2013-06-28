<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>各业务系统响应级别横比</title>
<link rel="stylesheet" type="text/css" href="${staticURL}/style/hopCss/custom-grid-style/jquery-ui-1.8.21.custom.css" />
<link type="text/css" rel="stylesheet" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pieRenderer.min.js"></script>
<style>
.jqplot-axis {
	font-size: 1em;
}

table.jqplot-table-legend,table.jqplot-cursor-legend {
	background-color: rgba(255, 255, 255, 0.6);
	border: 1px solid #CCCCCC;
	font-size: 1em;
	position: absolute;
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

<script>
$(document).ready(function(){
	var datas = <s:property value="pieDatas" />;
    var names = <s:property value="pieNames" />;
    var weeks = <s:property value="weekNum" />;
    var months = <s:property value="monthNum" />;
    var len = names.length;
    var crts = $("#right");
    // 动态添加每个pie chart
    for(var i = 0; i < len; i++) {
    	var charRowDiv = null;
    	if(i%2 == 0){//2个图标一行
    		charRowDiv = createChartRow(i/2);
    		$(charRowDiv).appendTo(crts);
    	}else{
    		var index = (i == 1)?0:(i-1)/2;
    		charRowDiv = $("#charRowDiv"+index);
    	}
    	var widetDivId = "div" + i;
    	var widgetDiv = createChartWidget(widetDivId,names[i] + '各响应级别service数量',weeks[i],months[i]);
    	$(widgetDiv).appendTo(charRowDiv);
    	var chartArea = $(widgetDiv).find(".flot-graph")[0];
    	chartArea.id="pieChart"+i;
    	$.jqplot("pieChart"+i, [datas[i]], {
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
});

function createChartWidget(id,title,week,month){
	var widgetDiv = $("#_chartWidgetTemplate_").clone();
	widgetDiv.id=id;
	$($(widgetDiv).find(".chart-caption")[0]).html("本周:"+week+",本月:"+month);
	$(widgetDiv).html($(widgetDiv).html().replace("_CHART_TITLE_",title));
	$(widgetDiv).css("display","block");
	return widgetDiv;
}
function createChartRow(id){
	return $("<div class='section' id='charRowDiv"+id+"'></div>");
}
</script>
</head>
<body>
<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">监控中心</a></li>
		<li><a href="#">综合监控报告</a></li>
		<li class="last"><a href="#">响应级别横向比较</a></li>
	</ul></div></div>
</div>
<div class="section">
	<jsp:include page="/common/messages.jsp" />
	<div class="message blue">
		<span><b>说明：</b>本页面横向比较所有业务系统各响应级别的service数量，以及它们本周本月的超时数量。</span>
	</div>
</div>

<div class="half" id="_chartWidgetTemplate_" style="display: none;">
	<div class="box">
		<div class="title">
			_CHART_TITLE_
			<span class="hide"></span>
		</div>
		<div class="content">
			<div class="chart-caption"></div>
			<div class="flot-graph" style="width: 100%; height: 350px; padding: 0px; position: relative;"></div>
		</div>
	</div>
</div>
</body>
</html>