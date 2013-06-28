<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>系统登录</title>
	<link rel="shortcut icon" href="${staticURL}/images/haier.ico"/>
	<link rel="stylesheet" href="${staticURL}/css/style.css"/>
	<link rel="stylesheet" href="${staticURL}/css/forms.css"/>
	<link rel="stylesheet" href="${staticURL}/css/forms-btn.css"/>
	<link rel="stylesheet" href="${staticURL}/css/menu.css"/>
	<link rel="stylesheet" href="${staticURL}/css/style_text.css"/>
	<link rel="stylesheet" href="${staticURL}/css/datatables.css"/>
	<link rel="stylesheet" href="${staticURL}/css/fullcalendar.css"/>
	<link rel="stylesheet" href="${staticURL}/css/pirebox.css"/>
	<link rel="stylesheet" href="${staticURL}/css/modalwindow.css"/>
	<link rel="stylesheet" href="${staticURL}/css/statics.css"/>
	<link rel="stylesheet" href="${staticURL}/css/tabs-toggle.css"/>
	<link rel="stylesheet" href="${staticURL}/css/system-message.css"/>
	<link rel="stylesheet" href="${staticURL}/css/tooltip.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wizard.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wysiwyg.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wysiwyg.modal.css"/>
	<link rel="stylesheet" href="${staticURL}/css/wysiwyg-editor.css"/>
	<link rel="stylesheet" href="${staticURL}/css/handheld.css"/>
	<script src="${staticURL}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.backgroundPosition.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.placeholder.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.ui.1.8.17.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.ui.select.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.ui.spinner.js" type="text/javascript"></script>
	<script src="${staticURL}/js/superfish.js" type="text/javascript"></script>
	<script src="${staticURL}/js/supersubs.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.datatables.js" type="text/javascript"></script>
	<script src="${staticURL}/js/fullcalendar.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.smartwizard-2.0.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/pirobox.extended.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.tipsy.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.elastic.source.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.jBreadCrumb.1.1.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.customInput.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.metadata.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.filestyle.mini.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.filter.input.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.flot.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.flot.pie.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.flot.resize.min.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.graphtable-0.2.js" type="text/javascript"></script>
	<script src="${staticURL}/js/jquery.wysiwyg.js" type="text/javascript"></script>
	<script src="${staticURL}/js/controls/wysiwyg.image.js" type="text/javascript"></script>
	<script src="${staticURL}/js/controls/wysiwyg.link.js" type="text/javascript"></script>
	<script src="${staticURL}/js/controls/wysiwyg.table.js" type="text/javascript"></script>
	<script src="${staticURL}/js/plugins/wysiwyg.rmFormat.js" type="text/javascript"></script>
	<script src="${staticURL}/js/costum.js" type="text/javascript"></script>
</head>
<body>
<div id="change">
	<a href="#"><img src="${staticURL}/gfx/fluid.png" alt="使用反馈" /></a>
</div>
<div id="wrapper" class="login">
	
	<div class="box">
		<div class="title">
			海尔HOP监控系统
			<span class="hide"></span>
		</div>
		<div class="content">
			<div class="message inner blue">
				<span><b>提示：</b>请使用最新版chrome或firefox浏览器进行浏览
				</span>
			</div>
			<s:if test="hasActionErrors()||hasFieldErrors()">
				<div class="message inner red">
					<span><b>登录错误：</b>
						<s:iterator value="actionErrors">
					        <s:property/>
					      </s:iterator>
					      <s:iterator value="fieldErrors">
					          <s:iterator value="value">
					             <s:property/>
					          </s:iterator>
					      </s:iterator>
					</span>
				</div>
          	</s:if>
			<s:form action="login" namespace="/security" method="post" id="loginForm">
				<div class="row">
					<label style="padding:0 30px;width:70px;">用户名</label>
					<div class="right"><s:textfield name="user.name"/></div>
				</div>
				<div class="row">
					<label style="padding:0 30px;width:70px;">密码</label>
					<div class="right"><s:password name="user.password" /></div>
				</div>
				<div class="row">
					<label style="padding:0 30px;width:70px;">验证码</label>
					<div class="right"><s:textfield name="checkCode" cssClass = "inputInMiddle" style="width:60%;"/>&nbsp;<img class="image" id="checkCodeImg" src="${dynamicURL}/checkCode.action" onclick="changeValidateCode()" style="cursor: pointer;"/></div>
				</div>
				<!-- <input type="hidden" name="checkCodeEnable" value="false"/> -->
				<div class="row">
					<div class="right">
						<button type="submit" class="white"><span>登陆</span></button>
						<button type="reset"><span>取消</span></button>
						<a href="./retrieveUserPassword.action" style="padding:0 30px;text-decoration:none;">忘记密码</a>
					</div>
					
				</div>
			</s:form>
		</div>
	</div>
	
</div>
 <div style="display: none; opacity: 0.5;" class="piro_overlay"></div><table style="left: 424.5px; top: -194px; display: none;" class="piro_html ui-draggable" cellpadding="0" cellspacing="0"><tbody><tr><td class="h_t_l"></td><td class="h_t_c" title="drag me!!"></td><td class="h_t_r"></td></tr><tr><td class="h_c_l"></td><td class="h_c_c"><div class="piro_loader" title="close"><span></span></div><div class="resize"><div style="display: none;" class="nav_container"><a href="#prev" class="piro_prev" title="previous"></a><a href="#next" class="piro_next" title="next"></a><div class="piro_prev_fake">prev</div><div class="piro_next_fake">next</div><div class="piro_close" title="close"></div></div><div style="display: none;" class="caption"></div><div class="div_reg"></div></div></td><td class="h_c_r"></td></tr><tr><td class="h_b_l"></td><td class="h_b_c"></td><td class="h_b_r"></td></tr></tbody></table>
<script type="text/javascript">
	function loginSubmit(){
		document.getElementById("loginForm").submit();
	}
	function reset(){
		document.getElementById("loginForm").reset();
	}
    function changeValidateCode() {  
        var timenow = new Date().getTime();  
        var obj = document.getElementById("checkCodeImg");
        obj.src="${dynamicURL}/checkCode.action?d="+timenow;  
    }  
</script>
</body>
</html>