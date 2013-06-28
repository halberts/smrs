package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.ReleaseTracerConf;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 设定上线追踪
 */
public class ReleaseTrackAction extends BaseIssueAction {

	private static final long serialVersionUID = -9165991581009370951L;

	/**
	 * service分页对象
	 */
	private Pager<BusinessService> pager = new Pager<BusinessService>();

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 上线追踪列表
	 */
	private List<ReleaseTracerConf> releaseTrackList = new ArrayList<ReleaseTracerConf>();

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * service名
	 */
	private String serviceName;

	/**
	 * app名
	 */
	private String appName;

	/**
	 * service名字列表
	 */
	private String traceServices;

	/**
	 * 追踪时间段
	 */
	private String tracePeriod;

	/**
	 * 追踪记录的id（用于更新）
	 */
	private String trackId;

	/**
	 * 激活标志位（用于更新）
	 */
	private String activeFlag;

	/**
	 * 要设置vip的service id
	 */
	private String serviceIds;

	/**
	 * 要设置vip的service names
	 */
	private String serviceNames;

	public String getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}

	public String getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(String serviceNames) {
		this.serviceNames = serviceNames;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTracePeriod() {
		return tracePeriod;
	}

	public void setTracePeriod(String tracePeriod) {
		this.tracePeriod = tracePeriod;
	}

	public String getTraceServices() {
		return traceServices;
	}

	public void setTraceServices(String traceServices) {
		this.traceServices = traceServices;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<ReleaseTracerConf> getReleaseTrackList() {
		return releaseTrackList;
	}

	public void setReleaseTrackList(List<ReleaseTracerConf> releaseTrackList) {
		this.releaseTrackList = releaseTrackList;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	public Pager<BusinessService> getPager() {
		return pager;
	}

	public void setPager(Pager<BusinessService> pager) {
		this.pager = pager;
	}

	/**
	 * 激活VIP
	 * 
	 * @return
	 * @throws Exception
	 */
	public String activeVip() throws Exception {
		if (appId != null && appName != null) {
			getBaseInfoService().activeServiceVip(Long.valueOf(appId), appName);
		}
		return SUCCESS;
	}

	/**
	 * 激活LEVEL
	 * 
	 * @return
	 * @throws Exception
	 */
	public String activeLevel() throws Exception {
		if (appId != null && appName != null) {
			getBaseInfoService().activeServiceLv(Long.valueOf(appId), appName);
		}
		return SUCCESS;
	}

	/**
	 * 保存VIP
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveServiceVip() throws Exception {
		if (appName != null && serviceIds != null && serviceNames != null) {
			String[] tmp = serviceNames.split(",");
			List<String> svcNms = new ArrayList<String>();
			for (String str : tmp) {
				svcNms.add(str);
			}
			String[] tmp2 = serviceIds.split(",");
			List<Long> svcIds = new ArrayList<Long>();
			for (String id : tmp2) {
				svcIds.add(Long.valueOf(id));
			}
			getBaseInfoService().saveServiceVip(appName, svcNms, svcIds);
		}
		return SUCCESS;
	}

	/**
	 * 激活上线追踪
	 * 
	 * @return
	 * @throws Exception
	 */
	public String activeReleaseTrack() throws Exception {
		if (trackId != null && appName != null) {
			ReleaseTracerConf rtc = getBaseInfoService().getReleaseTraceConf(Long.valueOf(trackId));
			rtc.setActiveFlag("1"); // 激活为1
			// 保存跟踪
			getBaseInfoService().activeReleaseTraceConf(appName, rtc);
		}
		return SUCCESS;
	}

	/**
	 * 更新上线追踪
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateReleaseTrack() throws Exception {
		if (trackId != null && appId != null) {
			ReleaseTracerConf rtc = getBaseInfoService().getReleaseTraceConf(Long.valueOf(trackId));
			float tp = Float.parseFloat(tracePeriod);
			long tpMs = (long) (tp * 60 * 60 * 1000); // 转成毫秒
			rtc.setTracePeriod(String.valueOf(tpMs));
			rtc.setTraceServices(traceServices);
			rtc.setTraceApp(Long.valueOf(appId));
			rtc.setActiveFlag(activeFlag);
			// 保存跟踪
			getBaseInfoService().updateReleaseTraceConf(rtc);
		}
		return SUCCESS;
	}

	/**
	 * 新增上线追踪
	 * 
	 * @return
	 * @throws Exception
	 */
	public String svReleaseTrack() throws Exception {
		if (traceServices != null && appId != null && tracePeriod != null) {
			ReleaseTracerConf rtc = new ReleaseTracerConf();
			float tp = Float.parseFloat(tracePeriod);
			long tpMs = (long) (tp * 60 * 60 * 1000); // 转成毫秒
			rtc.setTracePeriod(String.valueOf(tpMs));
			rtc.setTraceServices(traceServices);
			rtc.setTraceApp(Long.valueOf(appId));
			rtc.setActiveFlag(activeFlag);
			// 保存跟踪
			getBaseInfoService().saveReleaseTraceConf(rtc);
		}
		return SUCCESS;
	}

	/**
	 * 新增上线追踪（查找service）
	 * 
	 * @return json数据，填充前台表格
	 * @throws Exception
	 */
	public String newReleaseTrack() throws Exception {
		if (appId != null) {
			pager.setCurrentPage(getPage());
			pager.setPageSize(getRows());
			if (StringUtils.isNotEmpty(getSidx())) {
				pager.setOrderProperty(getSidx());
				pager.setOrder(StringUtils.defaultIfEmpty(getSord(), "asc"));
			}
			pager = getBaseInfoService().getBusinessService(Long.valueOf(appId), serviceName, pager);
			this.setTotal(pager.getTotalPages());
			this.setRecords(pager.getTotalRecords());
		}
		return SUCCESS;
	}

	/**
	 * 查找上线追踪
	 * 
	 * @return json数据，填充前台表格
	 * @throws Exception
	 */
	public String searchTrackList() throws Exception {
		if (appId != null) {
			releaseTrackList = getBaseInfoService().getReleaseTraceConfs(Long.valueOf(appId));
			int size = 0;
			for (ReleaseTracerConf rtc : releaseTrackList) {
				String tp = rtc.getTracePeriod();
				// 转成小时
				long tpMs = Long.valueOf(tp);
				float tpHour = (float) tpMs / (60 * 60 * 1000);
				rtc.setTracePeriod(String.valueOf(tpHour));
				size++;
			}
			setRows(Long.valueOf(size));
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}
}
