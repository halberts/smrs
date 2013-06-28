package com.smrs.goods.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.smrs.basicdata.enums.StoreLevelEnum;
import com.smrs.basicdata.service.DictAreaService;
import com.smrs.basicdata.service.RegionService;
import com.smrs.basicdata.service.StoreService;
import com.smrs.goods.enums.CategoryLevelEnum;
import com.smrs.goods.enums.PhysicalTypeEnum;
import com.smrs.goods.enums.StorageTypeEnum;
import com.smrs.goods.model.SupplierModel;
import com.smrs.goods.service.GoodsAttributeService;
import com.smrs.goods.service.GoodsAttributeValueService;
import com.smrs.goods.service.GoodsCategoryService;
import com.smrs.goods.service.GoodsItemService;
import com.smrs.goods.service.GoodsSkuService;
import com.smrs.goods.service.SupplierService;
import com.smrs.util.DictConstants;
import com.smrs.webapp.action.BaseConsoleAction;

/**
 * 基础数据模块基类action
 * @author jonathan
 *
 */
public abstract class BaseGoodsAction extends BaseConsoleAction {
	private static final long serialVersionUID = 8432181644174225351L;
	
	
	@Autowired
	protected GoodsAttributeService goodsAttributeService;

	@Autowired
	protected GoodsAttributeValueService goodsAttributeValueService;
	
	@Autowired
	protected GoodsCategoryService  goodsCategoryService; 
	
	@Autowired
	protected GoodsItemService  goodsItemService; 
	
	@Autowired
	protected  GoodsSkuService goodsSkuService;
	 
	@Autowired
	protected StoreService  storeService;
	
	@Autowired
	protected RegionService  regionService;
	
	@Autowired 
	protected DictAreaService dictAreaService;
	@Autowired 
	protected SupplierService supplierService;
	
	private List<StoreLevelEnum> storeLevelList = DictConstants.getInstance().getStoreLevelList();
	protected String actionCommand;
	protected String toInput="toInput";
	protected List<CategoryLevelEnum> categoryLevelList = DictConstants.getInstance().getCategoryLevelList();
	
	protected List<StorageTypeEnum> storageTypelList = DictConstants.getInstance().getStorageTypelList();
	
	protected List<PhysicalTypeEnum> physicalTypeList = DictConstants.getInstance().getPhysicalTypeList();
	
	//sortIndexList;

	protected List<SupplierModel> supplierList = new ArrayList<SupplierModel>();
	public List<SupplierModel> getSupplierList() {
		return supplierList;
	}


	public void setSupplierList(List<SupplierModel> supplierList) {
		this.supplierList = supplierList;
	}
	
	
	public List<StorageTypeEnum> getStorageTypelList() {
		return storageTypelList;
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
