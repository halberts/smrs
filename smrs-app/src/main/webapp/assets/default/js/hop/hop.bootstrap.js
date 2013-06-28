/**页面导航栏自动锁定效果*/
$(document).ready(function(){ 
	//锁定头部导航条
	window.onscroll = function(){
		var scrollTop = document.documentElement.scrollTop + document.body.scrollTop;
		var Height = 55;//menu能够随着页面运动的高度 .
   		if(scrollTop>Height){
   			document.getElementById("menu").style.position="fixed";
			document.getElementById("menu").style.top ="0px";
			document.getElementById("menu").style.left ="0.5%";
			document.getElementById("menu").style.right ="0.5%";
   		}
   		else{
			document.getElementById("menu").style.position = "static";
		}
	};
	
});

//获取当前请求链接（去除context,去除参数）
function formatURL(fullUrl){
	var paramIndex = fullUrl.indexOf("?");
	if(paramIndex == -1){
		paramIndex = fullUrl.length;
	}
	return fullUrl.substring(0,paramIndex);
}
//高亮头部导航导航栏当前选中的tab
function highlightTopMenu(namespace){
	var fullUrl = window.location.href;
	var url = formatURL(fullUrl);
	//1.进行url完全匹配
	var hightlight = false;
	$(".sf-js-enabled li a").each(function(i){
		var href = formatURL(this.href);
		if(href==url){
			$(this).parent("li").addClass("current");
			hightlight = true;
			return;
		}
	});
	//2.进行namespace匹配
	if(!hightlight){
		$(".sf-js-enabled li a").each(function(i){
			if(this.href.indexOf("/"+namespace+"/")!=-1){
				$(this).parent("li").addClass("current");
				hightlight = true;
				return;
			}
		});
	}
}