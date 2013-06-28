package com.jof.framework.url;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLUtils {
	
	private static final String REQ_HEADER="HEAD";
	private static final int URL_CHECKER_EXCEPTION=-99;
	private static final int TIMEOUT_COUNT = 5000;
	private static final String SEG=",";
	private static final Logger LOGGER = LoggerFactory.getLogger( URLUtils.class );
	private static final int[] HTTP_STATUS={HttpURLConnection.HTTP_OK,HttpURLConnection.HTTP_MOVED_TEMP,HttpURLConnection.HTTP_MOVED_PERM};
	
	public static URLStatusBean getURLStatus(String urlName){
	
		if (urlName==null){
			return new URLStatusBean(URLCheckStatusEnum.EXCEPTION.getStatus(),URL_CHECKER_EXCEPTION+SEG+"URL is null");
		}
		int urlStatus;
		String urlStatusMsg;
		
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con=(HttpURLConnection)new URL(urlName).openConnection();
			con.setRequestMethod(REQ_HEADER);
			con.setConnectTimeout(TIMEOUT_COUNT);
			con.setReadTimeout(TIMEOUT_COUNT);
			if (httpStatusChecking(con.getResponseCode())){
				urlStatus=URLCheckStatusEnum.ACTIVE.getStatus();
			}else{
				urlStatus=URLCheckStatusEnum.EXCEPTION.getStatus();
			}
			
			urlStatusMsg=con.getResponseCode()+ SEG + con.getResponseMessage();
			
			return new URLStatusBean(urlStatus,urlStatusMsg);
		} catch (MalformedURLException e) {
			LOGGER.error("Can new URL for::"+urlName, e);
			return new URLStatusBean(3,URL_CHECKER_EXCEPTION+SEG+"Exception");
		} catch (IOException e) {
			LOGGER.error("Can not get status for::"+urlName, e);
			return new URLStatusBean(3,URL_CHECKER_EXCEPTION+SEG+"Exception");
		}
		
	}
	
	public static boolean httpStatusChecking(int in){
		for (int hs : HTTP_STATUS) {
			if (in==hs){
				return true;
			}
		}
		return false;
	}
}
