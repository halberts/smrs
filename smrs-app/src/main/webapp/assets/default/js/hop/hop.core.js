function removeErrors(){
	$('#messsage_box>span').html('<b>错误提示：</b>');
}
function handleErrors(event,data,handler) {
	removeErrors();
	var json = $.parseJSON(event.originalEvent.request.responseText);  
    if(json.actionErrors && json.actionErrors.length>0){//判断有没有actionErrors  
        $.each(json.actionErrors,function(index,data){
            $("#messsage_box>span").append(data);  
        });  
        handler.onFaild();
        $('#messsage_box').show();
        return;  
    }  
    if(json.fieldErrors && !isEmpty(json.fieldErrors)) {//判断有没有fieldError(LoginAction-validation.xml验证错误)  
        $.each(json.fieldErrors,function(index,value) {//index就是field的name,value就是该filed对应的错误列表，这里取第一个  
        	$("#messsage_box>span").append(value[0]);
            //$("#error_"+index).html(value[0]);  
            //$("#error_"+index).addClass("errorLabel");  
        });  
        handler.onFaild();
        $('#messsage_box').show();
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
//将错误消息输出到显示层中
function addMessageInfo(type,message){
	 var map = {"red":"错误提示：","blue":"操作提示：","orange":"警告提示：","green":"成功提示："};
	 var messageType = ".message."+type+"";
	 $(messageType).show();
	 var html = "<b>"+map[type]+"</b>" + message;
	 $(messageType+">span").html(html);  
}
//高亮并且展开左侧菜单
function highlightLeftMenu(){
	var fullUrl = window.location.href;
	var url = formatURL(fullUrl);
	var flag = false;
	$("div.box.submenu ul li ul li a").each(function(i){
		var href = formatURL(this.href);//alert(href);
		if(href==url){//alert($(this).next("ul").first().html());
			$(this).css("color","#D71249");
			$(this).parent().parent().toggle();
		}
	});
}
//获取当前请求链接（去除context,去除参数）
function formatURL(fullUrl){
	var paramIndex = fullUrl.indexOf("?");
	if(paramIndex == -1){
		paramIndex = fullUrl.length;
	}
	var url = fullUrl.substring(0,paramIndex);
	if(url.indexOf("#")!=-1){
		return url.substring(0,url.indexOf("#"));
	}
	return url
}