<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="hasActionErrors()||hasFieldErrors()">
	<div class="message red" id="messsage_box">
		<span><b>错误提示：</b>
		<s:iterator value="actionErrors">
	        <s:property/>&nbsp;
	      </s:iterator>
	      <s:iterator value="fieldErrors">
	          <s:iterator value="value">
	             <s:property/>&nbsp;
	          </s:iterator>
	      </s:iterator>
	      </span>
	</div>
</s:if>
<s:else>
	<div id="messsage_box" style="display: none;" class="message red">
		<span><b>错误提示：</b>
		</span>
	</div>
</s:else>
<s:if test="hasActionMessages()">
	<div class="message green" id="messsage_box">
		<span><b>成功提示：</b>
		<s:iterator value="actionMessages">
           <s:property/>
      	</s:iterator>
      	</span>
	</div>
</s:if> 
<s:else>
	<div id="messsage_box" class="message green" style="display: none;">
		<span><b>成功提示：</b>
		</span>
	</div>
</s:else>