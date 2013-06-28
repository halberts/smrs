<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>
	<link rel="stylesheet" href="${staticURL}/css/style.css"/>
	<link rel="stylesheet" href="${staticURL}/css/forms.css"/>
	<link rel="stylesheet" href="${staticURL}/css/forms-btn.css"/>
	<link rel="stylesheet" href="${staticURL}/css/style_text.css"/>
	<link rel="stylesheet" href="${staticURL}/css/system-message.css"/>
	
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
			修改密码
			<span class="hide"></span>
		</div>
		<div class="content">
			<s:if test="hasActionErrors()||hasFieldErrors()">
	          	<div class="message inner red">
	          		<span><b>修改错误：</b>
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
          	<s:elseif test="hasActionMessages()">
				<div class="message inner green">
					<span><b>操作提示：</b>
						<s:iterator value="actionMessages">
				             <s:property/>
				      	</s:iterator>
			      	</span>
				</div>
			</s:elseif> 
          	<s:else>
          		<div class="hiddendiv"></div>
          	</s:else>
			<s:form action="retrieveUpdatePassword" namespace="/security" method="post" id="retrievepasswordForm">
	            <s:hidden name="encode"/>
	            <div class="row">
					<label style="padding:0 30px;width:70px;">用户名</label>
					<div class="right"><s:textfield name="name"/></div>
				</div>
				<div class="row">
					<label style="padding:0 30px;width:70px;">新密码</label>
					<div class="right"><s:password name="password"/></div>
				</div>
				<div class="row">
					<label style="padding:0 30px;width:70px;">确认密码</label>
					<div class="right"><s:password name="confirmpassword"/></div>
				</div>
              	<div class="row">
					<div class="right">
						<button type="submit" class="white"><span>提交</span></button>
						<button type="reset"><span>重置</span></button>
					</div>
				</div>
            </s:form>
		</div>
	</div>
</div>
</body>
</html>