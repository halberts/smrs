package com.smrs.basicdata.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.basicdata.enums.DictTypeEnum;
import com.smrs.basicdata.model.ChannelModel;
import com.smrs.basicdata.model.DictAreaModel;
import com.smrs.basicdata.model.RegionModel;
import com.smrs.basicdata.model.StoreModel;
import com.smrs.basicdata.vo.StoreSearchModel;
import com.smrs.enums.StatusEnum;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;
import com.smrs.util.DateCommonUtil;

public class StoreAction extends BaseBasicDataAction{

	private static final long serialVersionUID = 1L;
	protected StoreModel store = new StoreModel();
	
	protected Pager<StoreModel> pager = new Pager<StoreModel>(); 
	protected String titleName="门店";
	protected List<ChannelModel> channelList= new ArrayList<ChannelModel>();
	protected List<RegionModel> regionList = new ArrayList<RegionModel>();
	protected List<StoreModel>  regionStoreList = new ArrayList<StoreModel>();
	protected List<DictAreaModel> provinceList = new ArrayList<DictAreaModel>();
	protected List<DictAreaModel> cityList = new ArrayList<DictAreaModel>();
	protected List<DictAreaModel> zoneList = new ArrayList<DictAreaModel>();
	
	protected List<StoreModel> parentStoreList;
	
	public List<StoreModel> getParentStoreList() {
		return parentStoreList;
	}

	public void setParentStoreList(List<StoreModel> parentStoreList) {
		this.parentStoreList = parentStoreList;
	}
	
	public List<DictAreaModel> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<DictAreaModel> provinceList) {
		this.provinceList = provinceList;
	}

	public List<DictAreaModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<DictAreaModel> cityList) {
		this.cityList = cityList;
	}

	public List<DictAreaModel> getZoneList() {
		return zoneList;
	}

	public void setZoneList(List<DictAreaModel> zoneList) {
		this.zoneList = zoneList;
	}

	public List<ChannelModel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<ChannelModel> channelList) {
		this.channelList = channelList;
	}

	public List<RegionModel> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<RegionModel> regionList) {
		this.regionList = regionList;
	}

	public List<StoreModel> getRegionStoreList() {
		return regionStoreList;
	}

	public void setRegionStoreList(List<StoreModel> regionStoreList) {
		this.regionStoreList = regionStoreList;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public void preparePageSelectList(){
		channelList =channelService.getAllActive();
		regionList = regionService.getAllActive();
		regionStoreList = storeService.getRegionStoreList();
		provinceList = dictAreaService.getByDictType(DictTypeEnum.AREA_PROVINCE,null);
		cityList = dictAreaService.getByDictType(DictTypeEnum.AREA_CITY,null);
		zoneList = dictAreaService.getByDictType(DictTypeEnum.AREA_ZONE,null);
	}
	
	public String updateStore(){
		preparePageSelectList();
		if(StringUtils.isEmpty(actionCommand)){
			store = storeService.getByPK(store.getId());
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
	
	public String deleteStore(){
		this.storeService.deleteModel(store);
		return this.SEARCH;
	}
	
	public String addStore(){
		if(store!=null && StringUtils.isEmpty(store.getName())){
			preparePageSelectList();
			return SUCCESS;
		}
		store.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, store);
		if(store.getCloseDate()==null){
			store.setCloseDate(DateCommonUtil.getMaxDate());
		}
		this.storeService.addModel(store);
		this.addActionMessage("创建成功");
		preparePageSelectList();
		return SUCCESS;
	}
	

	
	public String searchStore(){
		StoreSearchModel sotreSearchModel = new StoreSearchModel();
		sotreSearchModel.setStore(store);		
		sotreSearchModel.setPager(pager);		
		pager=storeService.getByNameLikePager(sotreSearchModel);
		return SUCCESS;
	}
	
	
	
	public StoreModel getStore() {
		return store;
	}
	public void setStore(StoreModel store) {
		this.store = store;
	}
	public Pager<StoreModel> getPager() {
		return pager;
	}
	public void setPager(Pager<StoreModel> pager) {
		this.pager = pager;
	}
	public String getTitleName() {
		return titleName;
	}

}
