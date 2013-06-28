<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HornetQ监控</title>
<script src="${staticURL}/scripts/artdialog/jquery.artDialog.js?skin=opera"></script>
<script type="text/javascript">
	function getValue(id){
		alert($("#"+id).text()); 
	}
</script>
</head>
<body>
<%-- 	<h3><a href="#" >test</a></h3>
	  <s:iterator value="map" id="entry">
		<s:property value="#entry" /><br/>
        <span style="font-style: normal;color: red;">key:</span> <s:property value="key" />  <br/>
     	<span style="font-style: normal;color: red;">value:</span> <s:property value="value" /><br/>
     	 <s:iterator value="value" id="content">
	     	  <span style="font-style: normal;color: green;">key:</span> <s:property value="key" />  &nbsp; &nbsp; &nbsp;
	     	  <span style="font-style: normal;color: green;">value:</span> <s:property value="value" /><br/>
     	 </s:iterator> 
       </s:iterator>  --%>
       
 <div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">系统监控</a></li>
		<li><a href="#">消息监控</a></li>
		<li class="last"><a href="#">HornetqJms属性</a></li>
	</ul></div></div>
</div>
 <div class="section">
<div class="box">
<div class="title">
	queue列表
	<span class="hide"></span>
</div>
<div class="content" style="overflow-x: scroll;">
		<table id="rounded-corner" class="color_table" style="overflow: scroll;">
			<thead>
				<tr>
					<th>名称</th>
					<th colspan="50">属性(点击查看详细)</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="map" id="entry">
					<tr style="background-color: expression('#F8F8F8,#EFEFEF' . split(',')[rowIndex%2]);">
						<td><span style="font-weight: bold;"> <s:property
									value="key" />
						</span></td>
						<s:iterator value="value" id="content">
							<s:set name="number" value="#content.value.size"/>
							<td><span style="cursor: pointer;" onclick="getValue('<s:property value="key"/><s:property value="#entry.value.size"/>')"><s:property value="key"/></span>
							<s:hidden type="hidden" value="#entry.value.size" name="contentsize"/>  
							<div style="display: none;" id='<s:property value="key"/><s:property value="#entry.value.size"/>'>    
								<s:iterator value="value" id="queueContent"> 
												<div style="float: left;">
														<s:property value="value"/> 
												</div>
											</s:iterator> 
							</div> 
							</td>							
						</s:iterator>
					</tr>
				</s:iterator>
			</tbody>
		</table> 
	</div>
	</div>
	</div>
	
</body> 
</html>