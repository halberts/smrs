<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="tableLeft">
		<label>名称<span>*</span></label>
		<s:textfield name="goodsAttributeValue.name"></s:textfield>
	</div>
	<div class="tableRight">
		<label>值:<span>*</span></label>
		<s:textfield name="goodsAttributeValue.value"  />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>排序位<span></span></label>		
		<s:textfield name="goodsAttributeValue.sortIndex" />			
	</div>
	<div class="tableRight">
		<label>状态:</label>
		<s:select name="goodsAttributeValue.status" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>所属属性:<span></span></label>		
		<s:select name="goodsAttributeValue.goodsAttribute.id" list="goodsAttributeList" listKey="id" listValue="name" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>&nbsp;&nbsp;<span></span></label>	
		&nbsp;&nbsp;
	</div>
</div>

