package com.smrs.basicdata.webapp.action;

import com.smrs.basicdata.enums.StoreTypeEnum;
import com.smrs.basicdata.vo.StoreSearchModel;

public class RegionStoreAction extends StoreAction{

	private static final long serialVersionUID = 1L;
	protected String titleName="区域仓";
	protected int storeType=StoreTypeEnum.region.getId();
	
	public int getStoreType() {
		return storeType;
	}

	public void setStoreType(int storeType) {
		this.storeType = storeType;
	}

	public String getTitleName() {
		return titleName;
	}
	
	public String addRegionStore(){
		parentStoreList = storeService.getMajorStoreList();
		return addStore();
	}
	
	public String searchRegionStore(){
		StoreSearchModel sotreSearchModel = new StoreSearchModel();
		sotreSearchModel.setStore(store);		
		sotreSearchModel.setPager(pager);		
		pager=storeService.getByNameLikeAndTypePager(sotreSearchModel,StoreTypeEnum.region);
		return SUCCESS;
		
	}
	
	public String updateRegionStore(){
		parentStoreList = storeService.getMajorStoreList();
		return updateStore();
	}
}
