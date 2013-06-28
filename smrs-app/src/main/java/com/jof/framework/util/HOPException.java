/**
 * 
 */
package com.jof.framework.util;

/**
 * Please refer class com.haier.openplatform.util.HOPERR about how to define
 * business error
 * 
 * @author mk
 * 
 */
public class HOPException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7071306164354524157L;

	private String hopErrMsg = "NOT_DEFINED";

	/**
	 * @param message
	 * @param cause
	 */
	public HOPException(String message, Throwable cause) {
		super(cause);
		if (null != message){
			hopErrMsg = message;
		}
	}

	public String getHopErrMsg() {
		return hopErrMsg;
	}
}
