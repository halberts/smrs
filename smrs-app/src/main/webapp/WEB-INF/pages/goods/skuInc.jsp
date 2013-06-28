<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="tableLeft">
		<label>名称<span>*</span></label>
		<s:textfield name="goodsSku.name"></s:textfield>
	</div>
	<div class="tableRight">
		<label>编码:<span>*</span></label>
		<s:textfield name="goodsSku.code" id="thisgroupdescription" />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>简称:<span></span></label>		
		<s:textfield name="goodsSku.shortName"  />
	</div>
	<div class="tableRight">
		<label>厂家编码:</label>
		<s:textfield name="goodsSku.mfgSkuCode"  />
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>款式:<span></span></label>		
		<s:select name="goodsSku.itemId" list="allGoodsItemList" listKey="id" listValue="nameAndCode" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>产品条码1:</label>
		<s:textfield name="goodsSku.barCode1"  />
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>产品条码2:<span></span></label>		
		<s:textfield name="goodsSku.barCode2"  />
	</div>
	<div class="tableRight">
		<label>产品条码3:</label>
		<s:textfield name="goodsSku.barCode3"  />
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>产品供应商:<span></span></label>		
		<s:textfield name="goodsSku.supplierId"  />
	</div>
	<div class="tableRight">
		<label>计量单位:</label>
		<s:textfield name="goodsSku.salesUnit"  />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>批次管理标志:<span></span></label>		
		<s:select name="goodsSku.batchFlag" list="statusList" listKey="id" listValue="name" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>效期管理标志:</label>
		<s:select name="goodsSku.validFlag" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>物理属性:<span></span></label>		
		<s:select name="goodsSku.physicalType" list="physicalTypeList" listKey="id" listValue="name" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>存储属性:</label>
		<s:select name="goodsSku.storageType" list="storageTypelList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>采购价:<span></span></label>		
		<s:textfield name="goodsSku.purchasePrice"  />
	</div>
	<div class="tableRight">
		<label>吊牌价:</label>
		<s:textfield name="goodsSku.labelPrice"  />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>市场价:<span></span></label>		
		<s:textfield name="goodsSku.marketPrice"  />
	</div>
	<div class="tableRight">
		<label>成本价(含税):</label>
		<s:textfield name="goodsSku.costPrice"  />
	</div>
</div>

<div class="row">
		<label>状态:</label>
		<div class="right">
			<s:select name="goodsSku.status" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
		</div>
</div>

<s:hidden name="goodsSku.itemCode" />

<div  class="content">
		<label>选择属性:</label>
		<table class="">
		<tr>
		<td>	
			<s:checkboxlist name="selectedAttributes" list="allGoodsAttributeList" listKey="id" listValue="name" cssClass="rightSelect"/>
		</td>
		</tr>
		</table>
</div>



