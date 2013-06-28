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
		<s:textfield name="store.name"></s:textfield>
	</div>
	<div class="tableRight">
		<label>代码:</label>
		<s:textfield name="store.storeCode"></s:textfield>
	</div>
</div>
<div class="row">
	<div class="tableLeft">
		<label>简称<span>*</span></label>
		<s:textfield name="store.shortName"></s:textfield>
	</div>
	<div class="tableRight">
		<label>门店状态:</label>
		<div class="right"><s:select name="store.status" list="statusMap"/></div>
	</div>
</div>

<div class="row">
	<div class="tableLeft">
		<label>返点率:<span>*</span></label>
		<s:textfield name="store.rebateRate"></s:textfield>
	</div>
	<div class="tableRight">
		<label>门店级别:</label>
		<div class="right"><s:select name="store.storeLevel" list="storeLevelList" listKey="id" listValue="name"/></div>	
	</div>
</div>				
<div class="row">
	<div class="tableLeft">
		<label>负责人:<span>*</span></label>
		<s:textfield name="store.manager"></s:textfield>
	</div>
	<div class="tableRight">
		<label>电话:</label>
		<s:textfield name="store.tel"></s:textfield>
	</div>
</div>	
<div class="row">
	<div class="tableLeft">
		<label>开店时间:<span>*</span></label>	
		<sj:datepicker name="store.openDate" timepicker="false" displayFormat="yy-mm-dd"/>
	</div>
	<div class="tableRight">
		<label>关店时间:</label>
		<sj:datepicker name="store.closeDate" timepicker="false" displayFormat="yy-mm-dd"/>
	</div>
</div>
<div class="row">
	<table>
		<tr>
			<td>区域:<span>*</span> </td> <td>  <s:select name="store.areaId" list="regionList" listKey="id" listValue="name"/></td>
			<td>所属区域(总)仓:<span>*</span> </td> <td> <s:select name="store.parentId" list="parentStoreList" listKey="id" listValue="name"/></td>
			<s:if test="storeType==2">
				<td><span></span></td> <td> </td>		
			</s:if>
			<s:else>
				<td>渠道:<span>*</span>  </td> <td> <s:select name="store.channelId" list="channelList" listKey="id" listValue="name"/></td>
			</s:else>
		</tr>
		<tr>
			<td>省(市):<span>*</span> </td> <td> <s:select name="store.province" list="provinceList" listKey="dictId" listValue="name" id="province" /></td>
			<td>市: <span>*</span>   </td> <td> <s:select name="store.city" list="cityList" listKey="dictId" listValue="name"  id="city" /></td>
			<td>区县:<span>*</span>  </td> <td> <s:select name="store.zone" list="zoneList" listKey="dictId" listValue="name"  id="zone" /></td>
		</tr>	
	</table>
</div>

<div class="row">
			<label>地址:</label>
			<div class="right">
				<s:textarea name="store.address" rows="1" cols="45"></s:textarea>
			</div>
</div>

