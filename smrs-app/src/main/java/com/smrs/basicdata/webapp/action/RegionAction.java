package com.smrs.basicdata.webapp.action;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.RegionModel;
import com.smrs.basicdata.vo.RegionSearchModel;
import com.smrs.enums.StatusEnum;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;

public class RegionAction extends BaseBasicDataAction{
	private static final long serialVersionUID = 1L;
	
	protected Pager<RegionModel> pager = new Pager<RegionModel>();
	
	private RegionModel region = new RegionModel();
	protected String titleName="区域";
	




	public String addRegion(){
		if(region!=null && StringUtils.isEmpty(region.getName())){
			return SUCCESS;
		}
		region.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, region);

		regionService.addModel(region);
		//this.addActionError();
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchRegion(){
		/*
		if(pager.getPageSize()==20){
			pager.setPageSize(10L);
		} 
		*/
		RegionSearchModel regionSearchModel = new RegionSearchModel();
		regionSearchModel.setRegion(region);		
		regionSearchModel.setPager(pager);		
		pager=regionService.getByNameLikePager(regionSearchModel);
		return SUCCESS;
	}
	
	public String updateRegion(){
		if(StringUtils.isEmpty(this.actionCommand)){
			region = regionService.getByPK(region.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, region);
			regionService.updateModel(region);
			this.addActionMessage("修改成功");
		}
		return SUCCESS;
	}
	
	public String deleteRegion(){
		regionService.deleteModel(region);
		return this.SEARCH;
	}
	
	public Pager<RegionModel> getPager() {
		return pager;
	}

	public void setPager(Pager<RegionModel> pager) {
		this.pager = pager;
	} 
	
	public RegionModel getRegion() {
		return region;
	}


	public void setRegion(RegionModel region) {
		this.region = region;
	}
	
	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}	
