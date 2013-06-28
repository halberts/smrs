<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="tableLeft">
		<label>名称<span>*</span></label>
		<s:textfield name="goodsAttribute.name"></s:textfield>
	</div>
	<div class="tableRight">
		<label>编码:<span>*</span></label>
		<s:textfield name="goodsAttribute.code" id="thisgroupdescription" />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>排序位<span></span></label>		
		<s:textfield name="goodsAttribute.sortIndex" id="thisgroupdescription" />		
	</div>
	<div class="tableRight">
		<label>状态:</label>
		<s:select name="goodsAttribute.status" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>
<div class="row">
		<label>描述:</label>
		<div class="right">
			<s:textarea name="goodsAttribute.description" rows="5" cols="45"></s:textarea>
		</div>
</div>
