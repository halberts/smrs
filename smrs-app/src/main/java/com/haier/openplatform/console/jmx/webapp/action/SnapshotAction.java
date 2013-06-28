package com.haier.openplatform.console.jmx.webapp.action;

import java.util.Map;

import com.haier.openplatform.console.issue.webapp.action.BaseIssueAction;
import com.haier.openplatform.console.jmx.service.JmxResourcesService;
/**
 * 查询hornetQueue
 * @author WangJian
 *
 */
public class SnapshotAction extends BaseIssueAction{
 
	private static final long serialVersionUID = 4100702857266862227L;
	private JmxResourcesService jmxResourcesService; 
	@SuppressWarnings("rawtypes")
	private Map<String, Map> map;
	 
	@SuppressWarnings("rawtypes")
	public Map<String, Map> getMap() {
		return map;
	} 
	@SuppressWarnings("rawtypes")
	public void setMap(Map<String, Map> map) {
		this.map = map;
	} 
	public void setJmxResourcesService(JmxResourcesService jmxResourcesService) {
		this.jmxResourcesService = jmxResourcesService;
	}
 
	@Override
	public String execute() throws Exception {
		this.map = jmxResourcesService.getAllResourcesParameters();
		return SUCCESS;
	}
}
