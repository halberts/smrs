$(document).ready(function () {  
    /* 获取省份 */  
    $.post("linkageAction_getProvince.action", function (data) {  
        var jsonObj = eval("(" + data + ")");  
        for (var i = 0; i < jsonObj.length; i++) {  
            var $option = $("<option></option>");  
            $option.attr("value", jsonObj[i].pid);  
            $option.text(jsonObj[i].pname);  
            $("#province").append($option);  
        }  
    });  
    /* 根据省份获取城市 */  
    $("#province").change(function () {  
        $.post("linkageAction_getCityByPid.action", {pid:$("#province").val()}, function (data) {  
            /* 清空城市 */  
            $("#city option[value!='']").remove();  
            /* 清空乡镇 */  
            $("#town option[value!='']").remove();  
            var jsonObj = eval("(" + data + ")");  
            for (var i = 0; i < jsonObj.length; i++) {  
                var $option = $("<option></option>");  
                $option.attr("value", jsonObj[i].cid);  
                $option.text(jsonObj[i].cname);  
                $("#city").append($option);  
            }  
        });  
    });  
    /* 根据城市获取乡镇 */  
    $("#city").change(function () {  
        $.post("linkageAction_getTownByCid", {cid:$("#city").val()}, function (data) {  
            /* 清空乡镇 */  
            $("#town option[value!='']").remove();  
            var jsonObj = eval("(" + data + ")");  
            for (var i = 0; i < jsonObj.length; i++) {  
                var $option = $("<option></option>");  
                $option.attr("value", jsonObj[i].tid);  
                $option.text(jsonObj[i].tname);  
                $("#town").append($option);  
            }  
        });  
    });  
}); 