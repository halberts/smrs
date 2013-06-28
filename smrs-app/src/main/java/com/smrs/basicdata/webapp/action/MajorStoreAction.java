package com.smrs.basicdata.webapp.action;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.smrs.basicdata.model.StoreModel;

public class MajorStoreAction extends StoreAction{

	private static final long serialVersionUID = 1L;
	private StoreModel store;
	private String titleName="总仓";
	
	public String getTitleName() {
		return titleName;
	}
	public String updateMajorStore(){
		if(StringUtils.isEmpty(actionCommand)){
			store = storeService.getMajorStore();
		}else{
			StoreModel tempStore = storeService.getByPK(store.getId());
			//tempStore.setAddress(st)
			tempStore.setLastModifyDate(new Date());
			tempStore.setModifiedBy(this.getUserNameFromSession());
			tempStore.setAddress(store.getAddress());
			tempStore.setCity(store.getCity());
			tempStore.setProvince(store.getProvince());
			tempStore.setZone(store.getZone());
			tempStore.setName(store.getName());
			tempStore.setShortName(store.getShortName());
			tempStore.setStoreCode(store.getStoreCode());
			tempStore.setManager(store.getManager());
			tempStore.setTel(store.getTel());
			storeService.updateModel(tempStore);
			this.addActionMessage("更新成功");
		}
		return SUCCESS;
	}
	
	
	public StoreModel getStore() {
		return store;
	}
	public void setStore(StoreModel store) {
		this.store = store;
	}
	
}
