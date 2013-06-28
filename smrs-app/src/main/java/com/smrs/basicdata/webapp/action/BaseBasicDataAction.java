package com.smrs.basicdata.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.smrs.basicdata.enums.StoreLevelEnum;
import com.smrs.basicdata.service.ChannelService;
import com.smrs.basicdata.service.DictAreaService;
import com.smrs.basicdata.service.RegionService;
import com.smrs.basicdata.service.StoreService;
import com.smrs.webapp.action.BaseConsoleAction;

/**
 * 基础数据模块基类action
 * @author jonathan
 *
 */
public abstract class BaseBasicDataAction extends BaseConsoleAction {
	private static final long serialVersionUID = 8432181644174225351L;
	
	
	@Autowired
	protected ChannelService channelService;
	
	@Autowired
	protected StoreService  storeService;
	
	@Autowired
	protected RegionService  regionService;
	
	@Autowired 
	protected DictAreaService dictAreaService;
	
	private List<StoreLevelEnum> storeLevelList;
	protected String actionCommand;
	protected String toInput="toInput";
	
	public List<StoreLevelEnum> getStoreLevelList() {
		storeLevelList =  new ArrayList<StoreLevelEnum>();
		storeLevelList.add(StoreLevelEnum.A);
		storeLevelList.add(StoreLevelEnum.B);
		storeLevelList.add(StoreLevelEnum.C);
		storeLevelList.add(StoreLevelEnum.D);
		storeLevelList.add(StoreLevelEnum.E);
		return storeLevelList;
	}
	public void setStoreLevelList(List<StoreLevelEnum> storeLevelList) {
		this.storeLevelList = storeLevelList;
	}
	public ChannelService getChannelService() {
		return channelService;
	}
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
	public String getActionCommand() {
		return actionCommand;
	}
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public abstract String getTitleName();

	

	
	
}
