package com.smrs.security.webapp.action;


/**
 * @author wangjian
 */
public class ToRetrieveUpdatePasswordAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -5051986184516205778L;
	
	private String encode;
	
    public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
}
