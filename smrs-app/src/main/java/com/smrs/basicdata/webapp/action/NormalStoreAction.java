package com.smrs.basicdata.webapp.action;

import java.util.List;

import com.smrs.basicdata.enums.StoreTypeEnum;
import com.smrs.basicdata.model.StoreModel;
import com.smrs.basicdata.vo.StoreSearchModel;

public class NormalStoreAction extends StoreAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titleName="门店";

	public String getTitleName() {
		return titleName;
	}
	
	public String addNormalStore(){
		parentStoreList = storeService.getRegionStoreList();
		return  addStore();
	}
	
	public String updateNormalStore(){
		parentStoreList = storeService.getRegionStoreList();
		
		return updateStore();
	}
	
	public String deleteNormalStore(){
		return deleteStore();
	}
	
	public String searchNormalStore(){
		StoreSearchModel sotreSearchModel = new StoreSearchModel();
		store.setStoreType(StoreTypeEnum.normal.getId());
		sotreSearchModel.setStore(store);	
		sotreSearchModel.setPager(pager);		
		pager=storeService.getByNameLikePager(sotreSearchModel);
		return SUCCESS;
	}
	
	
	
}
