package com.jof.framework.util;
import javax.servlet.http.HttpServletRequest;

/**
 * @author WangXuzheng
 *
 */
public class IpUtil{
	private static final String[] HEADERS = new String[]{"x-forwarded-for","HEADER_X_FORWARDED_FOR","Proxy-Client-IP","WL-Proxy-Client-IP"};
	/**
	 * 获取request的原始IP,如通过f5,nginx等
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
	   for(String header : HEADERS){
			String ip = request.getHeader(header);
			if(isValidIp(ip)){
				return ip;
			}
	   }
       return request.getRemoteAddr();
   }
   
   private static boolean isValidIp(String ip){
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			return false;
		}
		return true;
   }
}