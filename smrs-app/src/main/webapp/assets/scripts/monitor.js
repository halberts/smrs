/**
 * 监控页面公用js 
 * author: infosys_vilight
 */



$(document).ready(function(){
	/**
	 * 导航条（左侧及头部）
	 */
	
	
	$("#sidebarNav ul li a").click(function(){
		
		$("#sidebarNav ul li").removeClass('current');
		$(this).parent().addClass('current');
	
		// parent li's index
		var index = $(this).parent().index();
		var pidx = $(this).parent().parent().prev().index();
		var str = pidx + '-' + index;
		addToCookie(str);
		
		
		// 头部导航
		var menus = [];
		menus.push(' ' + $(this).parent().parent().prev().text());
		menus.push(' ' + $(this).text());
		addBreadCrumb(menus);
	});
	
	
	$("#sidebarNav ul li").hover(function(){
		$(this).addClass('liHover');
		var liA = $(this).children('a');
		liA.addClass('liAHover');
	 	liA.css('padding-right', '35px');
		
	}, function(){
		$(this).removeClass('liHover');
		var liA = $(this).children('a');
		liA.removeClass('liAHover');
	 	liA.css('padding-right', '20px');
	});
	
	
	
	/**
	 * 内容框标题栏双击收起
	 */
	$(".content-box-header").dblclick(function(){
		$(this).parent().slideUp();
	});
	
	
	/**
	 * 提示框关闭按钮
	 */
	$("div.notification a.close").click(function () {
			$(this).parent().fadeTo(400, 0, function () { 
				$(this).slideUp(400);
			});
			return false;
		}
	);
	
	/**
	 * 提示框双击收起
	 */
	$("div.notification").dblclick(function () {
			$(this).fadeTo(400, 0, function () { 
				$(this).slideUp(400);
			});
			return false;
		}
	);
	
	
	/**
	 *  横向时间选择条
	 */
	$("ul.inline-ul li span").click(function(){
		$(this).parent().siblings().find("span").removeClass("current");
		$(this).addClass("current");
	});
	
});


/**
 * 给js string加个trim方法，用于除去字符串两边空格
 */
String.prototype.trim = function() { 
	return this.replace(/^\s+/g,"").replace(/\s+$/g,"");  
}


/**
 * 将导航条列表加入cookie中
 */
function addToCookie(str){
	document.cookie="hopMonitorMenu="+str;
}

/**
 * 从cookie中读取导航条列表
 */
function getFromCookie(str){
	var allcookies = document.cookie.split(';');
	for(var i = 0; i < allcookies.length; i++){
		var cookie = allcookies[i].split('=');
		
		if(str == cookie[0].trim()) {	//因为加入cookie中后，有时key会被默认加上空格符，所以要去掉
			return cookie[1];
		}
	}
	return null;
}

/**
 * 获取某年某月最后一天
 */
function getLastDay(year,month)        
{        
 var new_year = year;    //取当前的年份        
 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）        
 var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天        
 return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期        
}  


/**
 * 读取左侧菜单及导航条
 */
function loadLeftMenuAndNav() {
	var menu = getFromCookie("hopMonitorMenu");
	
	if(menu == null) {
		return;
	}
	
	var path = menu.split('-');
	var index1 = parseInt(path[0])/2, index2 = parseInt(path[1]);
	
	if(index1 >= 2) {
		$("#sidebarNav h2:eq(0)").trigger('click');
	} else if(index1 == 0) {
		$("#sidebarNav h2:eq(2)").trigger('click');
	}
	
	
	var lv1 = $("#sidebarNav h2:eq("+index1+")");
	var lv2 = lv1.next().find("li:eq("+index2+")");
	lv2.find('a').trigger('click');
}



