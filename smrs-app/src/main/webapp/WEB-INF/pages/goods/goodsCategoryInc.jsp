<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="tableLeft">
		<label>名称<span>*</span></label>
		<s:textfield name="goodsCategory.name"></s:textfield>
	</div>
	<div class="tableRight">
		<label>编码:<span>*</span></label>
		<s:textfield name="goodsCategory.code" id="thisgroupdescription" />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>排序位<span></span></label>		
		<s:select name="goodsCategory.sortIndex" list="sortIndexList" listKey="id" listValue="name" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>状态:</label>
		<s:select name="goodsCategory.status" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>父类:</label>	
		<s:select name="goodsCategory.parent.id" list="allGoodsCategoryList" listKey="id" listValue="name"  cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>描述:</label>
		<s:textfield name="goodsCategory.description" id="thisgroupdescription" />		
	</div>
</div>

<div class="section">
	<div class="box">
		<div class="title">
			属性列表 <span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper">
	    <div>
			<div class="dataTables_filter"></div>
		</div>
		<div style="width: 100%;font-size: 11px; " id="userListDiv">
			<table>
			<% int index=0; %>
			<tr>
				<td colspan='5'>
					继承属性
				</td>
			</tr>
			<s:iterator value="heritageGoodsAttributeList" var ='dep'>
			<% 
				if(index==0){
					out.println("<tr>");
				}
			%>
		       	  <td>
		       	  	<s:checkbox name="defaultSelectedAttribute" value="<s:property value='#dep.id'/>" disabled="true" checked="checked"/><s:property value='#dep.name'/>
                  </td>
                    
               <%
					index++;
               		if(index==5){
               			out.println("</tr>");
               			index=0;
               		}
			
               %>
            </s:iterator>
                <%
                if(index<5&& index!=0){
                	out.println("</tr>");
                }
                index=0;
                %>   
                
             <tr>
				<td colspan='5'>
					已有属性
				</td>
			</tr>
			<s:iterator value="selectedGoodsAttributeList" var ='dep'>
			<% 
				if(index==0){
					out.println("<tr>");
				}
			%>
		       	  <td>
		       	  	<s:checkbox name="selectedAttribute" fieldValue="%{#dep.id}"  checked="checked"/><s:property value='#dep.name'/>
                  </td>
                    
               <%
					index++;
               		if(index==5){
               			out.println("</tr>");
               			index=0;
               		}
			
               %>
            </s:iterator>
                <%
                if(index<5&& index!=0){
                	out.println("</tr>");
                }
                index=0;
                %>     
                
                
                
             <tr>
				<td colspan='5'>
					可选属性
				</td>
			</tr> 	
  			<s:iterator value="availableGoodsAttributeList" var ='dep'>
			<% 
			//System.out.println("test index"+index);	
			if(index==0){
					out.println("<tr>");
				}
			%>
		       	  <td>
		       	    <s:checkbox name="selectedAttribute"  fieldValue="%{#dep.id}"/><s:property value='#dep.name'/>
                  </td>
                    
               <%
					index++;
               		if(index==5){
               			out.println("</tr>");
               			index=0;
               		}
				
               %>
            </s:iterator>
                <%
                if(index<5&& index!=0){
                	out.println("</tr>");
                }
                index=0;
                %> 
                    	
               </table> 
			</div>
		</div>
	</div>
</div>

