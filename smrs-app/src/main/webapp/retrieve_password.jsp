<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码</title>
<link href="${staticURL}/style/retrieve_password.css" rel="stylesheet"/>
<link rel="shortcut icon" href="${staticURL}/images/haier.ico"/>
</head>
<body style="background-color:#ffffff;">
<div style="height:135px;background-image: url('${staticURL}/style/images/header_bg.jpg');background-repeat: repeat-x;">
	<a style="position: absolute;top:21px;left:200px;display: block;background-image: url('${staticURL}/style/images/logo_big.jpg');width: 205px;height: 47px;"></a>
	<div class="retrievePasswordSysName">HOP演示系统</div>
</div>
<div class="main">
  <div class="big">
    <div class="retrievePasswordBgWrap">
      <div class="retrievePasswordtopBG"><span>找回密码</span></div>
      <div class="mbg">
        <div class="retrievePassword">
          
          <div class="form"> 
          	<s:if test="hasActionErrors()||hasFieldErrors()">
	          	<div class="hiddendiv">
	              <div id="errortips">
	              	<s:iterator value="actionErrors">
				        <s:property/>
				      </s:iterator>
				      <s:iterator value="fieldErrors">
				          <s:iterator value="value">
				             <s:property/>
				          </s:iterator>
				      </s:iterator>
	              </div>
	            </div>
          	</s:if>
          	<s:elseif test="hasActionMessages()">
				<div class="valid_box" style="font-size: 12px;font-weight: bolder;background-color: #FF60AF;height: 20px;width: 350px;padding-left: 15px;padding-top: 7px" id="_action_message_box">
					<s:iterator value="actionMessages">
			             <s:property/>
			      	</s:iterator>
				</div>
			</s:elseif> 
          	<s:else>
          		<div class="hiddendiv"></div>
          	</s:else>
            <s:form action="retrievePassword" namespace="/security" method="post" id="retrievepasswordForm">
              <div style="height: 10px">
              </div>
              <div class="user"> <span class="text">用户名：</span>
               <s:textfield name="user.name" value="" cssClass="input"/>
              </div>
              <div class="user"> <span class="text">邮箱：</span>
                <s:textfield name="user.email"  cssClass="input"/>
              </div> 
              <div class="retrievePasswordBUTTON">
              	<a onClick="retrievepasswordSubmit()" class="retrievePasswordBOTN">提交</a>  
              	<a onClick="reset()" class="retrievePasswordRESET">重置</a><input type="submit" style="display: none;"/>
              </div>
            </s:form>
          </div>
        </div>
      </div>
      <div class="retrievePasswordbotBG"> </div>
    </div>
   </div>
   <div style="height: 120px"></div>
   </div>
   <div style="width: 100%; margin: 0 auto; text-align: center; background-image: url('${staticURL}/style/images/footer_bg.jpg');background-repeat: repeat-x; height: 89px; position: relative; bottom: 0; line-height: 89px;">
	<div style="padding-top: 20px">Copyright © 2012 海尔集团版权所有</div>
</div>
<script type="text/javascript">
	function retrievepasswordSubmit(){
		document.getElementById("retrievepasswordForm").submit();
	}
	function reset(){
		document.getElementById("retrievepasswordForm").reset();
	}
	<!-- 
    function changeValidateCode() {  
        var timenow = new Date().getTime();  
        var obj = document.getElementById("checkCodeImg");
        obj.src="${dynamicURL}/checkCode.action?d="+timenow;  
    }  
	-->
</script>
</body>
</html>