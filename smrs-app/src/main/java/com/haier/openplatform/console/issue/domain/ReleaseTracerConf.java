package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing
 * 持久化类:在线追踪记录表
 */
public class ReleaseTracerConf extends BaseDomain<Long> {

	private static final long serialVersionUID = 6672980295232003832L;

	/**
	 * 追踪Service:按逗号分隔
	 */
	private String traceServices;

	/**
	 * 追踪时间段
	 */
	private String tracePeriod;

	/**
	 * 追踪应用系统ID
	 */
	private long traceApp;

	/**
	 * 激活标记位
	 */
	private String activeFlag;

	public String getTraceServices() {
		return traceServices;
	}

	public void setTraceServices(String traceServices) {
		this.traceServices = traceServices;
	}

	public String getTracePeriod() {
		return tracePeriod;
	}

	public void setTracePeriod(String tracePeriod) {
		this.tracePeriod = tracePeriod;
	}

	public long getTraceApp() {
		return traceApp;
	}

	public void setTraceApp(long traceApp) {
		this.traceApp = traceApp;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	@Override
	public String toString() {
		return "ReleaseTraceConf [traceServices=" + traceServices + ", tracePeriod=" + tracePeriod + ", traceApp="
				+ traceApp + ", activeFlag=" + activeFlag + "]";
	}

}
