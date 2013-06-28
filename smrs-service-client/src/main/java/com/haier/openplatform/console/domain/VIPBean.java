package com.haier.openplatform.console.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.hmc.domain.MessageModule;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("VIPBean")
public class VIPBean extends MessageModule{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4895803377541475637L;
	public final static int VIP = 1;
	public final static int VIPTemp = 2;
	public final static int SERVIC_LV = 3;
	private final static String SEGMENT = "=";

	@XStreamAlias("AppName")
	private String appName = null;

	@XStreamAlias("ServiceAPIName")
	private List<String> serviceAPINM;

	@XStreamAlias("VIPType")
	private int vipType;

	@XStreamAlias("TimeInterval")
	private long timeInterval;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<String> getServiceAPINM() {
		return serviceAPINM;
	}

	public void setServiceAPINM(List<String> serviceAPINM) {
		this.serviceAPINM = serviceAPINM;
	}

	public int getVipType() {
		return vipType;
	}

	public void setVipType(int vipType) {
		this.vipType = vipType;
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void setServiceLV(String snm, String slv) {
		if (serviceAPINM == null){
			serviceAPINM = new ArrayList<String>();
		}

		serviceAPINM.add(snm + SEGMENT + slv);
	}
	
	public void getServiceLV (Map<String, String> snmap){
		
		int p = 0;
		int l = 0;
		
		for (String snm : serviceAPINM) {
			p = snm.indexOf(SEGMENT);
			l = snm.length();
			snmap.put(snm.substring(0,p), snm.substring(p+1, l));
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder("\r\n//==========VIPBean=================\r\n");
		try {
			Class<? extends VIPBean> class1 = this.getClass();
			Field afield[] = class1.getDeclaredFields();
			for (int i = 0; i < afield.length; i++){
				s.append(afield[i].getName() + ":" + afield[i].get(this) + "\r\n");
			}
		} catch (Exception exception) {
			exception.printStackTrace(System.out);
		}
		s.append("============VIPBean============\r\n");
		return s.toString();
	}

}
