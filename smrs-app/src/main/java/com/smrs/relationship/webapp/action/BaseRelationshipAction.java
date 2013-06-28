package com.smrs.relationship.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.smrs.basicdata.enums.DictTypeEnum;
import com.smrs.basicdata.enums.StoreLevelEnum;
import com.smrs.basicdata.model.DictAreaModel;
import com.smrs.basicdata.model.RegionModel;
import com.smrs.basicdata.service.DictAreaService;
import com.smrs.basicdata.service.RegionService;
import com.smrs.goods.enums.CategoryLevelEnum;
import com.smrs.goods.enums.PhysicalTypeEnum;
import com.smrs.goods.enums.StorageTypeEnum;
import com.smrs.goods.service.SupplierService;
import com.smrs.relationship.enums.CustomerLevelEnum;
import com.smrs.relationship.enums.CustomerTypeEnum;
import com.smrs.relationship.service.CustomerService;
import com.smrs.util.DictConstants;
import com.smrs.webapp.action.BaseConsoleAction;

/**
 * 基础数据模块基类action
 * @author jonathan
 *
 */
public abstract class BaseRelationshipAction extends BaseConsoleAction {
	private static final long serialVersionUID = 8432181644174225351L;
	
	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected SupplierService supplierService;

	
	
	
	@Autowired
	protected RegionService  regionService;
	
	@Autowired 
	protected DictAreaService dictAreaService;
	
	private List<StoreLevelEnum> storeLevelList = DictConstants.getInstance().getStoreLevelList();
	protected String actionCommand;
	protected String toInput="toInput";
	protected List<CategoryLevelEnum> categoryLevelList = DictConstants.getInstance().getCategoryLevelList();
	
	protected List<StorageTypeEnum> storageTypelList = DictConstants.getInstance().getStorageTypelList();
	
	protected List<PhysicalTypeEnum> physicalTypeList = DictConstants.getInstance().getPhysicalTypeList();
	
	
	protected List<CustomerTypeEnum> customerTypeList = DictConstants.getInstance().getCustomerTypeList();
	protected List<CustomerLevelEnum> customerLevelList = DictConstants.getInstance().getCustomerLevelList();
	
	protected List<CustomerLevelEnum> supplierLevelList = DictConstants.getInstance().getCustomerLevelList();
	protected List<CustomerTypeEnum> supplierTypeList = DictConstants.getInstance().getCustomerTypeList();
	
	protected List<RegionModel>  regionList = new ArrayList<RegionModel>();
	
	protected List<DictAreaModel> provinceList = new ArrayList<DictAreaModel>();
	protected List<DictAreaModel> cityList = new ArrayList<DictAreaModel>();
	protected List<DictAreaModel> zoneList = new ArrayList<DictAreaModel>();
	
	
	//sortIndexList;

	public void preparePageSelectList(){		
		regionList = regionService.getAllActive();		
		provinceList = dictAreaService.getByDictType(DictTypeEnum.AREA_PROVINCE,null);
		cityList = dictAreaService.getByDictType(DictTypeEnum.AREA_CITY,null);
		zoneList = dictAreaService.getByDictType(DictTypeEnum.AREA_ZONE,null);
	}
	
	
	public List<CustomerLevelEnum> getSupplierLevelList() {
		return supplierLevelList;
	}


	public List<CustomerTypeEnum> getSupplierTypeList() {
		return supplierTypeList;
	}


	public List<DictAreaModel> getProvinceList() {
		return provinceList;
	}

	public List<DictAreaModel> getCityList() {
		return cityList;
	}

	public List<DictAreaModel> getZoneList() {
		return zoneList;
	}

	public List<RegionModel> getRegionList() {
		
		return regionList;
	}

	public void setRegionList(List<RegionModel> regionList) {
		this.regionList = regionList;
	}

	public List<StorageTypeEnum> getStorageTypelList() {
		return storageTypelList;
	}

	public List<CustomerLevelEnum> getCustomerLevelList() {
		return customerLevelList;
	}

	public List<CustomerTypeEnum> getCustomerTypeList() {
		return customerTypeList;
	}

	public List<PhysicalTypeEnum> getPhysicalTypeList() {
		return physicalTypeList;
	}

	public List<CategoryLevelEnum> getCategoryLevelList() {
		return categoryLevelList;
	}
	public List<StoreLevelEnum> getStoreLevelList() {
		
		return storeLevelList;
	}
	public void setStoreLevelList(List<StoreLevelEnum> storeLevelList) {
		this.storeLevelList = storeLevelList;
	}

	

	public String getActionCommand() {
		return actionCommand;
	}
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public abstract String getTitleName();

	

	
	
}
