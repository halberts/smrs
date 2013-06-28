<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript"> 
$(document).ready(function () {  
    /* 获取省份 */  
    $.post("queryProvinceDictArea.action", function (data) {  
        var jsonObj = eval( data );  
        for (var i = 0; i < jsonObj.length; i++) {  
            var $option = $("<option></option>");  
            $option.attr("value", jsonObj[i].id);  
            $option.text(jsonObj[i].name);  
            $("#province").append($option);  
        }  
    });  
    /* 根据省份获取城市 */  
    $("#province").change(function () {  
        $.post("queryCityDictArea.action", {provinceId:$("#province").val()}, function (data) {  
            /* 清空城市 */  
            $("#city option[value!='']").remove();  
            /* 清空乡镇 */  
            $("#zone option[value!='']").remove();  
            var jsonObj = eval(data);  
            for (var i = 0; i < jsonObj.length; i++) {  
                var $option = $("<option></option>");  
                $option.attr("value", jsonObj[i].id);  
                $option.text(jsonObj[i].name);  
                $("#city").append($option);  
            }  
        });  
    });  
    /* 根据城市获取乡镇 */  
    $("#city").change(function () {  
        $.post("queryZoneDictArea.action", {cityId:$("#city").val()}, function (data) {  
            /* 清空乡镇 */  
            $("#zone option[value!='']").remove();  
            var jsonObj = eval( data);  
            for (var i = 0; i < jsonObj.length; i++) {  
                var $option = $("<option></option>");  
                $option.attr("value", jsonObj[i].id);  
                $option.text(jsonObj[i].name);  
                $("#zone").append($option);  
            }  
        });  
    });  
}); 
</script>

<div class="row">
	<div class="tableLeft">
		<label>名称<span>*</span></label>
		<s:textfield name="supplier.name"></s:textfield>
	</div>
	<div class="tableRight">
		<label>编码:<span>*</span></label>
		<s:textfield name="supplier.code" id="thisgroupdescription" />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>类型:<span></span></label>		
		<s:select name="supplier.type" list="supplierTypeList" listKey="id" listValue="name" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>等级:</label>
		<s:select name="supplier.level" list="supplierLevelList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>所在省:<span></span></label>		
		<s:select name="supplier.province" list="provinceList" listKey="dictId" listValue="name" id="province" cssClass="leftSelect"/>
	</div>
	<div class="tableRight">
		<label>所在市:</label>
		<s:select name="supplier.city" list="cityList" listKey="dictId" listValue="name"  id="city"  cssClass="rightSelect"/>		
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>所在区(县):<span></span></label>	
		<s:select name="supplier.zone" list="zoneList" listKey="dictId" listValue="name"  id="zone" cssClass="leftSelect"/>			
	</div>
	<div class="tableRight">
		<label>地址:</label>
		<s:textfield name="supplier.address"  />
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>联系人:<span></span></label>		
		<s:textfield name="supplier.contact"  />
	</div>
	<div class="tableRight">
		<label>固定电话:</label>
		<s:textfield name="supplier.tel"  />
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>移动电话:<span></span></label>	
		<s:textfield name="supplier.mobile"  />		
	</div>
	<div class="tableRight">
		<label>所属区域:</label>
		<s:select name="supplier.regionId" list="regionList" listKey="id" listValue="name" cssClass="rightSelect"/>
	</div>
</div>

<div class="row">
			<label>状态:</label>
			<div class="right">
				<s:select name="supplier.status" list="statusList" listKey="id" listValue="name" cssClass="rightSelect"/>
			</div>
</div>