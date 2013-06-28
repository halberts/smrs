package com.haier.openplatform.console.audit;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.security.SessionSecurityConstants;

public class HOPAuditFilter implements Filter {

   	private static final Logger LOGGER = LoggerFactory.getLogger( HOPAuditFilter.class ); 
   	
   	private static final String SEG="::";
   	
   	private static final String RETURN="\r\n";
   	private static final String SESSION_PARAMETER_START = "========session parameters::\r\n";
   	private static final String REQUEST_PARAMETER_STAET = "========request parameters::\r\n";
   	/**
   	 * 监控拦截器默认忽略掉的列表
   	 */
   	private static final String IGNORAL_URL_LIST = ".*\\.(png|gif|jpg|css|js|ico|jpeg|htm|html)$";
   	/**
   	 * 用来设置档异常出现时，需要从session中获取哪些字段的值
   	 */
   	private Set<String> sessionAttributeSet = new HashSet<String>();
   	/**
	 * 忽略不被解析的url
	 */
	private Pattern requestUriIgnorePattern;
   	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//默认取用户id,name,nick_name的值
		sessionAttributeSet.add(SessionSecurityConstants.KEY_USER_ID);
		sessionAttributeSet.add(SessionSecurityConstants.KEY_USER_NAME);
		sessionAttributeSet.add(SessionSecurityConstants.KEY_USER_NICK_NAME);
		String sessionAttributes = StringUtils.defaultIfBlank(filterConfig.getInitParameter("sessionAttributes"),"");
		String[] attributeArray = StringUtils.split(sessionAttributes,",");
		for(String attribute : attributeArray){
			sessionAttributeSet.add(attribute);
		}
		String ignorUrlPattern = StringUtils.defaultIfEmpty(filterConfig.getInitParameter("requestUriIgnorePattern"), IGNORAL_URL_LIST);
		this.requestUriIgnorePattern = Pattern.compile(ignorUrlPattern, Pattern.CASE_INSENSITIVE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//忽略对静态资源请求的拦截
		String requestUrl = request.getRequestURI();
		if (this.requestUriIgnorePattern.matcher(requestUrl).matches()) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		ReqNotes.resetNotes();
		LOGGER.info("Exception Capture Thread local is cleaned up " + HOPCONS.QUEUE_LOGGER_SEG);
		//将session中的信息保存的输入参数列表中
		StringBuilder stringBuilder = new StringBuilder(SESSION_PARAMETER_START);
		HttpSession session = request.getSession();
		for(String attribute : this.sessionAttributeSet){
			stringBuilder.append(attribute);
			stringBuilder.append(SEG);
			stringBuilder.append(session.getAttribute(attribute));
			stringBuilder.append(RETURN);
		}
		stringBuilder.append(REQUEST_PARAMETER_STAET);
		ReqNotes.putReqInfo(ReqNotes.REQ_KEY.HOP_ACTION, request.getServletPath());
		ReqNotes.putReqInfo(ReqNotes.REQ_KEY.HOP_BEAN, stringBuilder.append(printParaMap(request.getParameterMap())).toString());
		/*if (ServletActionContext.getRequest() != null) {
			ReqNotes.putReqInfo(ReqNotes.REQ_KEY.HOP_URL, request.getServerName() + ":"
					+ ServletActionContext.getRequest().getServerPort()
					+ ServletActionContext.getRequest().getServletPath() + "?"
					+ ServletActionContext.getRequest().getQueryString());
		}*/
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
	
	private static String printParaMap(Map<String,Object> m) {
		if (m==null){
			return HOPCONS.STR_NOT_DEFINED;
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<String,Object>> iter = m.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,Object> entry = (Map.Entry<String,Object>) iter.next();
			String key = entry.getKey().toString();
			Object obj = entry.getValue();
			String val = "";
			if (obj != null) {
				if (obj instanceof String[]) {
					String[] strs = (String[]) obj;
					val = Arrays.toString(strs);
				} else {
					val = obj.toString();
				}
			}
			sb.append(key);
			sb.append(SEG);
			sb.append(val);
			sb.append(RETURN);
		}
		return sb.toString();
	}
}
