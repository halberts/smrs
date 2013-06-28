package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.domain.AppHealthUrl;
import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.AppHealthDetail;
import com.haier.openplatform.security.LoginContextHolder;

/**
 * @author Vilight_Wu 节点健康状况
 */
public class HealthyStatusAction extends BaseIssueAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(HealthyStatusAction.class);
	private static final long serialVersionUID = -2608229911492950672L;
	/**
	 * AppServerChecker列表
	 */
	private List<AppHealthDetail> appHealthDetail = new ArrayList<AppHealthDetail>();  

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();
	
	/**
	 * appchecker表主键
	 */
	private String checkerId;

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 操作类型
	 */
	private String oper;

	/**
	 * 设置的url
	 */
	private String url;
	
	private String status;
	
	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	} 

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	} 

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	public List<AppHealthDetail> getAppHealthDetail() {
		return appHealthDetail;
	}

	public void setAppHealthDetail(List<AppHealthDetail> appHealthDetail) {
		this.appHealthDetail = appHealthDetail;
	} 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 增删改 分组名字及备注信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String urlCrud() throws Exception {
		try{
			if ("add".equals(oper)) {
				AppHealthUrl appHealthUrl = new AppHealthUrl();
				appHealthUrl.setAppId(Long.valueOf(appId));
				appHealthUrl.setUrl(url);
				String[] urlString = url.split("/");
				String nodeName = urlString[2];
				appHealthUrl.setNodeName(nodeName);
				appHealthUrl.setStatus(Integer.parseInt(status));
				appHealthUrl.setLastModifiedBy(LoginContextHolder.get().getUserName());
				appHealthUrl.setCreateBy(LoginContextHolder.get().getUserName());
				Date curDate = new Date();
				appHealthUrl.setLastModifiedDate(curDate);
				appHealthUrl.setCreateDate(curDate);
				getAppMonitorService().addHealthUrl(appHealthUrl);
			} else if ("edit".equals(oper)) {
				AppHealthUrl ahu = getAppMonitorService().getAppHealthUrlById(Long.valueOf(checkerId)); 
				ahu.setUrl(url);
				ahu.setNodeName(url.split("/")[2]);
				ahu.setStatus(Integer.parseInt(status));
				ahu.setLastModifiedBy(LoginContextHolder.get().getUserName());
				ahu.setLastModifiedDate(new Date());
				getAppMonitorService().updateHealthUrl(ahu); // 更新操作
			} else { // 删除操作
				getAppMonitorService().deleteHealthUrl(Long.valueOf(checkerId));
			}
		}catch(Exception e){
			LOGGER.error("urlCrud error,oper=" + oper + ",url=" + (url == null ? "" : url) + ",appId=" + appId,e);
		}
		return SUCCESS;
	}

	/**
	 * 获取指定应用系统下的所有nodeIp列表及运行状况
	 * 
	 * @return
	 */
	public String serverNodeStatus() {
		if (appId != null) {
			appHealthDetail = getAppMonitorService().getLastAppHealthDetailByAppId(Long.valueOf(appId));
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}
	
	/**
	 *统计指定应用系统下的所有nodeIp列表及运行状况
	 * 
	 * @return
	 */
	public String statusPlot() {
//		if (appId != null) { 
			//appServerChecker = getAppMonitorService().getStatusPlot(Long.valueOf(appId)); // 获取健康状况
			//获取到最近十次检查
//		}
		return SUCCESS;
	}

}
