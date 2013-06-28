<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript"> 
$(document).ready(function () {  
     $.post("queryGoodsCategory.action", function (data) {  
        var jsonObj = eval( data );  
        $("#categoryLevel1 option[value!='']").remove();  
        for (var i = 0; i < jsonObj.length; i++) {  
            var $option = $("<option></option>");  
            $option.attr("value", jsonObj[i].id);  
            $option.text(jsonObj[i].name);  
            $("#categoryLevel1").append($option);  
        }  
    });  
    /* 根据省份获取城市 */  
    $("#categoryLevel1").change(function () {  
        $.post("queryGoodsCategory.action", {parentCategoryId:$("#categoryLevel1").val(),queryLevel:2}, function (data) {  
            /* 清空城市 */  
            $("#categoryLevel2 option[value!='']").remove();  
            /* 清空乡镇 */  
            $("#categoryLevel3 option[value!='']").remove();  
            
            $("#categoryLevel4 option[value!='']").remove();  
            var jsonObj = eval(data);  
            /*
            var $option2 = $("<option></option>");  
            $option2.attr("value", "--");  
            $option2.text("请选择");  
            $("#categoryLevel2").append($option2);
            */
            for (var i = 0; i < jsonObj.length; i++) {  
                var $option = $("<option></option>");  
                $option.attr("value", jsonObj[i].id);  
                $option.text(jsonObj[i].name);  
                $("#categoryLevel2").append($option);  
            }  
        });  
    });  
    
    $("#categoryLevel2").change(function () {  
        $.post("queryGoodsCategory.action", {parentCategoryId:$("#categoryLevel2").val(),queryLevel:3}, function (data) {  
            /* 清空城市 */  
            $("#categoryLevel3 option[value!='']").remove();  
            /* 清空乡镇 */  
            $("#categoryLevel4 option[value!='']").remove();  
            var jsonObj = eval(data);  
            for (var i = 0; i < jsonObj.length; i++) {  
                var $option = $("<option></option>");  
                $option.attr("value", jsonObj[i].id);  
                $option.text(jsonObj[i].name);  
                $("#categoryLevel3").append($option);  
            }  
        });  
    });  
    
    /* 根据城市获取乡镇 */  
    $("#categoryLevel3").change(function () {  
        $.post("queryGoodsCategory.action", {parentCategoryId:$("#categoryLevel3").val(),queryLevel:4}, function (data) {  
            /* 清空乡镇 */  
            $("#categoryLevel4 option[value!='']").remove();  
            var jsonObj = eval( data);  
            for (var i = 0; i < jsonObj.length; i++) {  
                var $option = $("<option></option>");  
                $option.attr("value", jsonObj[i].id);  
                $option.text(jsonObj[i].name);  
                $("#categoryLevel3").append($option);  
            }  
        });  
    });  
}); 
</script>


<div class="row">
	<div class="tableLeft">
		<label>名称<span>*</span></label>
		<s:textfield name="goodsItem.name"></s:textfield>
	</div>
	<div class="tableRight">
		<label>编码:<span>*</span></label>
		<s:textfield name="goodsItem.code" id="thisgroupdescription" />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>简称:<span></span></label>		
		<s:textfield name="goodsItem.shortName"  />
	</div>
	<div class="tableRight">
		<label>厂家编码:</label>
		<s:textfield name="goodsItem.mfgSkuCode"  />
	</div>
</div>


<div class="row">
	<div class="tableLeft">
		<label>描述:</label>	
		<s:textfield name="goodsItem.description"  />
	</div>
	<div class="tableRight">
		<label>供应商:</label>
		<s:select id="supplier2" name="goodsItem.supplierId" list="supplierList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>一级类目:<span></span></label>		
		<s:select id="categoryLevel1" name="goodsItem.categoryIdName1" list="allGoodsCategoryList" listKey="idName" listValue="nameCode" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>二级类目:</label>
		<s:select id="categoryLevel2" name="goodsItem.categoryIdName2" list="allGoodsCategoryList" listKey="idName" listValue="nameCode" cssClass="rightSelect"/>
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>三级类目:<span></span></label>		
		<s:select id="categoryLevel3" name="goodsItem.categoryIdName3" list="allGoodsCategoryList" listKey="idName" listValue="nameCode" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>四级类目:</label>
		<s:select id="categoryLevel4" name="goodsItem.categoryIdName4" list="allGoodsCategoryList" listKey="idName" listValue="nameCode" cssClass="rightSelect"/>
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>品牌:<span></span></label>		
		<s:textfield name="goodsItem.brand"  />
	</div>
	<div class="tableRight">
		<label>计量单位:</label>
		<s:textfield name="goodsItem.salesUnit"  />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>批次管理标志:<span></span></label>		
		<s:select name="goodsItem.batchFlag" list="statusList" listKey="id" listValue="name" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>效期管理标志:</label>
		<s:select name="goodsItem.validFlag" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>物理属性:<span></span></label>		
		<s:select name="goodsItem.physicalType" list="physicalTypeList" listKey="id" listValue="name" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>存储属性:</label>
		<s:select name="goodsItem.storageType" list="storageTypelList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>

