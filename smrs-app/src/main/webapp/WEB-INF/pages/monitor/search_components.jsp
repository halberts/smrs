<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript"
	src="${staticURL}/scripts/fixed_header_column_table.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	 var obj=document.getElementById('component').getElementsByTagName('td');
	    for (i=0;i<obj.length;i++){
	    	var imgObj = obj[i].getElementsByTagName('img');
	    	if(obj[i].innerHTML.length>5 && imgObj.length != 1){
	       	 	obj[i].innerHTML=obj[i].innerHTML.substring(0,5)+'…';
	    	}
	    }
	    FixTable("component", 8, "100%", "500", "scroll", "scroll");   
}); 
</script>
</head>
<body>
	<div id="breadcrumbs">
	<div><div><ul>
		<li class="first"></li>
		<li><a href="#">首页</a></li>
		<li><a href="#">组件使用情况统计</a></li>
	</ul></div></div>
	</div>
	<s:form action="searchComponents" id="searchComponents"   namespace="/monitor"> 
		<s:hidden name="model.field"/>
		<!-- <input type="hidden" >   <input type="submit" value="查询"/></input> -->
	</s:form>
	<div class="section">
	<div class="box">
		<div class="title">
			查询结果
			<span class="hide"></span>
		</div>
		<div class="content" style="overflow: scroll;">
			<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div><table id="component" > 
				<thead>
					<tr>
						<th nowrap="nowrap">领域</th>
						<th nowrap="nowrap">组件</th>   
						<th nowrap="nowrap">价值</th>   
						<!-- <th nowrap="nowrap">样板点</th>   
						<th nowrap="nowrap">对口业务负责部门</th>    -->
						<th nowrap="nowrap">应推广数</th>   
						<th nowrap="nowrap">实推广数</th>   
						<th nowrap="nowrap">差异</th>   
						<th nowrap="nowrap">差异说明</th>   
						<th nowrap="nowrap">实现时间</th>    
						<s:iterator value="pager.records[0]" var="component" status="status">
							<s:iterator value="listModel" var="sys" status="status">
								<s:if test="#"></s:if>
									<th nowrap="nowrap"><s:property value="appName"/></th>
							</s:iterator>
						</s:iterator>
					</tr>
				</thead>
				
				<tbody>
					<!-- 数据行 -->
					<s:iterator value="pager.records" var="component" status="status">
						<tr>
						 	<td title="<s:property value="field"/>"><s:property value="field"/></td>
						 	<td nowrap="nowrap" title="<s:property value="components"/>"><s:property value="components"/></td>
						 	<td nowrap="nowrap" title="<s:property value="value"/>"><s:property value="value"/></td>
						 	<%-- <td nowrap="nowrap"><s:property value="template"/></td>
						 	<td nowrap="nowrap"><s:property value="system"/></td> --%>
						 	<td nowrap="nowrap" title="<s:property value="planPromotion"/>"><s:property value="planPromotion"/></td>
						 	<td nowrap="nowrap" title="<s:property value="realPromotion"/>"><s:property value="realPromotion"/></td>
						 	<td nowrap="nowrap" title="<s:property value="differences"/>"><s:property value="differences"/></td>
						 	<td style="width: 100px;" title="<s:property value="differencesDescription"/>"><s:property value="differencesDescription"/></td>
						 	<td nowrap="nowrap" title="<s:property value="realizeTime"/>"><s:property value="realizeTime"/></td>
							<s:iterator value="listModel" var="sys" status="status">
									<td>  
									<s:if test="#sys.status == 1">
										<img title="已推广" src="${staticURL}/style/images/yjc.png" /> 
									</s:if>
									<s:if test="#sys.status == 0">
										<img title="未推广" src="${staticURL}/style/images/wjc.png"/> 
									</s:if>
									<s:if test="#sys.status == 2">
										<img title="不需推广" src="${staticURL}/style/images/wxjc.png"/> 
									</s:if>
							</s:iterator>
						</tr>
					</s:iterator>
				</tbody>
				</table>
			</div>
		</div>
		<%--  <p:pagination pager="pager" theme="default" formId="searchComponents"></p:pagination>   --%>
	</div>
</div>
</body>
</html>