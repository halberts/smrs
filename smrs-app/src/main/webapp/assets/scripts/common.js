function removeErrors(){
	$('#_error_message_box').html('').removeClass('errorMessage');
	$('.errorLabel').html('').removeClass('errorLabel');  
}
function handleErrors(event,data,handler) {
	removeErrors();
	var json = $.parseJSON(event.originalEvent.request.responseText);  
    if(json.actionErrors && json.actionErrors.length>0){//判断有没有actionErrors  
        $.each(json.actionErrors,function(index,data){  
            $("#_error_message_box").append(data+"<br/>");  
        });  
        $('#_error_message_box').addClass('errorMessage');
        handler.onFaild();
        return;  
    }  
    if(json.fieldErrors && !isEmpty(json.fieldErrors)) {//判断有没有fieldError(LoginAction-validation.xml验证错误)  
        $.each(json.fieldErrors,function(index,value) {//index就是field的name,value就是该filed对应的错误列表，这里取第一个  
        	$("#_error_message_box").append(value[0]+"<br/>");  
            //$("#error_"+index).html(value[0]);  
            //$("#error_"+index).addClass("errorLabel");  
        });  
        $('#_error_message_box').addClass('errorMessage');
        handler.onFaild();
        return;  
    }  
    handler.onSuccess();
}
//判断对象是否为空(处理Object obj = {}这种情况认为isEmpty=true)  
function isEmpty(obj){
    for(var p in obj){  
        return false;  
    }  
    return true;  
} 

//各行变色表格
$(document).ready(function(){  
    $(".color_table tr").mouseover(function(){  
     $(this).addClass("over");  
     $(this).removeClass("double");
    });  
    $(".color_table tr:even").mouseover(function(){  
        $(this).find(".double").removeClass("double");
    }); 
    $(".color_table tr").mouseout(function(){  
     $(this).removeClass("over");
     $(this).find(".double").addClass("double");
    });   
    $(".color_table tr:even").mouseout(function(){  
        $(this).addClass("double");
    });  
    $(".color_table tr:even").addClass("double");   
});  
//对象内部属性信息--便于调试
function display(obj){
	var result = "";
	for(var p in obj){
		result = result + p +"="+obj[p]+"\n";
	}
	alert(result);
}

//获取当前请求链接（去除context,去除参数）
function formatURL(fullUrl){
	var paramIndex = fullUrl.indexOf("?");
	if(paramIndex == -1){
		paramIndex = fullUrl.length;
	}
	return fullUrl.substring(0,paramIndex);
}
//左侧菜单高亮选中
function highlightLeftMenu(){
	var fullUrl = window.location.href;
	var url = formatURL(fullUrl);
	$(".user-app a").each(function(i){
		var href = formatURL(this.href);
		if(href==url){
			$(this).css({"color":"#D71249"});//高亮
			$(this).addClass("cut");
			//展开父节点
			if($(this).parent("li").length>0){
				var ul = $(this).parent("li").parent();
				ul.show();
				var icon = ul.prev().children("span");
				icon.removeClass("ico-ub");
				icon.addClass("ico-up");
			}
		}
	});
}
//高亮头部导航栏按钮
function highlightTopMenu(namespace){
	var fullUrl = window.location.href;
	var url = formatURL(fullUrl);
	//1.进行url完全匹配
	var hightlight = false;
	$(".col-2 li a").each(function(i){
		var href = formatURL(this.href);
		if(href==url){
			$(this).addClass("one");
			hightlight = true;
		}
	});
	//2.进行namespace匹配
	if(!hightlight){
		$(".col-2 li>a").each(function(i){
			if(this.href.indexOf("/"+namespace+"/")!=-1){
				$(this).addClass("one");
			}
		});
	}
}
//cookie操作
function setCookie(c_name,value,exdays){
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	document.cookie=c_name + "=" + c_value;
}
function getCookie(c_name){
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++){
	  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
	  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
	  x=x.replace(/^\s+|\s+$/g,"");
	  if (x==c_name){
	    return unescape(y);
	  }
	}
}