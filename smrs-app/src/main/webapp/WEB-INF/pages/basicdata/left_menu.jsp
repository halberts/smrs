<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="security" uri="/security-tags" %>
<!--我的应用-->
<dl>
<dd class="user-app">
<s:set name="url" value="#"/>
<s:iterator value="resourceMap" id="resourceEntry">
	<s:set name="hasAuth" value="hasComponentAuth(key.code)"/>
	<!-- 无父菜单权限，子菜单为空，父菜单，子菜单都隐藏 -->
	<s:if test="hasAuth==false && resourceEntry.get(key).isEmpty()==true">
	</s:if><s:else>	
		<!-- 展示父菜单 -->
		<s:if test="hasAuth"><!-- 有权限时展示真正的url链接 -->
			<s:set name="url" value="key.url"/>
		</s:if>
		<h2>
			<s class="ico-1"></s><a href="<s:if test='hasAuth'>${dynamicURL}<s:property value='#url'/></s:if><s:else>#</s:else>"><s:property value="key.name"/></a>
			<span class="app-ico ico-ub"></span>	
		</h2>
		<!-- 展示子菜单 -->
		<s:if test="#resourceEntry.value.isEmpty()==false">
			<ul style="display: none;">
				<s:iterator value="#resourceEntry.value" var="resource">
					<li><a href="${dynamicURL}<s:property value="#resource.url"/>"><s:property value="#resource.name"/></a></li>
				</s:iterator>
			</ul>
		</s:if>
	</s:else>
</s:iterator>
</dd>
</dl>
<script type="text/javascript">
$(document).ready(function () {
	$(".ico-ub").live("click",function(){
		$(this).parent().parent().find('h2 span').each(function(){
			$(this).removeClass("ico-up");
			$(this).addClass("ico-ub");
		});
		$(this).parent().parent().find('ul').each(function(){
			$(this).hide();
		});
		$(this).parent().next('ul').show();
		$(this).removeClass("ico-ub");
		$(this).addClass("ico-up");
	});  
	function doHide(obj){
		$(obj).removeClass("ico-ub");
		$(obj).addClass("ico-up");
	}
	$(".ico-up").live("click",function(){
		$(this).parent().next('ul').hide();
		$(this).removeClass("ico-up");
		$(this).addClass("ico-ub");
	});  
	highlightLeftMenu();
});
</script>