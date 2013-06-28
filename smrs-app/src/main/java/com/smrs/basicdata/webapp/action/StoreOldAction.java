package com.smrs.basicdata.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.smrs.basicdata.model.StoreModel;
import com.smrs.basicdata.service.StoreService;
import com.smrs.basicdata.vo.StoreSearchModel;

public class StoreOldAction extends BaseBasicDataAction{
	
	private static final long serialVersionUID = 1L;
	
	private List<KeyValueModel> warehouseLevelList =new ArrayList<KeyValueModel>();
	private List<StoreModel> parentList = new ArrayList<StoreModel>();
	private List<StoreSearchModel> warehouseList;
	private Integer warehouseParentId;
	private String warehouseLevel;
	private String queryName;
	private String titleName="门店";
	


	//private WarehouseModel warehouserModel;
	@Autowired
	private StoreService storeService;
	private StoreSearchModel warehouseVO;
	
	public String execute() throws Exception{
		if(warehouseVO==null){
			//warehouseLevelList = DictionaryUtil.getInstance().getWarehouseLevelList();
			parentList = storeService.getAll();
			return toInput;		
		}		
		return SUCCESS;
	}
	
	public void preparePageList(){
		//warehouseLevelList = DictionaryUtil.getInstance().getWarehouseLevelList();
		parentList = storeService.getAll();
		return ;
	}
	/*
	public String addWarehouse() throws Exception{
		if(warehouseVO==null){
			warehouseLevelList = DictionaryUtil.getInstance().getWarehouseLevelList();
			parentList = storeService.getAll();
			return toInput;		
		}
		if(warehouseVO!=null){
			if(StringUtils.isEmpty(warehouseVO.getName())){
				this.addActionError("创库名不能为空");
				preparePageList();	
				
				return toInput;
			}
			List<StoreModel> list = storeService.findByName(warehouseVO.getName());
			if(CollectionUtils.isNotEmpty(list)){
				this.addActionError("创库名:"+warehouseVO.getName()+"已经存在");
				 preparePageList();
				 return toInput;
			}
			StoreModel model = new StoreModel();
			/*
			model.setActiveFlag(JofConstant.ACTIVE_FLAG_Y);
			model.setDescription(warehouseVO.getDescription());
			model.setLevel(warehouseLevel.charAt(0));
			model.setName(warehouseVO.getName());
			model.setParentId(warehouseParentId);
			this.warehouseService.addModel(model);
		
			preparePageList();
			this.addActionMessage("添加创库 "+warehouseVO.getName() +" 成功");
		}
		return SUCCESS;
	}
	*/
	
	public String queryWarehouse(){
		if(StringUtils.isEmpty(queryName)){
			//pageInfo.setTotalRecords(1000l);
			return toInput;
		}
		if(StringUtils.isNotEmpty(queryName)){
			//warehouseList = storeService.findByNameLike(queryName,pageInfo);
			logger.info(" warehouseList="+ warehouseList);
		}
		return SUCCESS;
	}
	
	public String editWarehouse(){
		/*
		if(id==null){
			return toInput;
		}
		*/
		//WarehouseModel model = new WarehouseModel();
		Long id=0l;
		StoreModel model =(StoreModel) storeService.getByPK(id);
		//this.warehouserModel = model;
		//warehouseVO.setActiveFlag(model.getActiveFlag());
		//warehouseVO.setDescription(model.getDescription());
		//warehouseVO.setId(model.getId());
		warehouseVO  = new StoreSearchModel();
		BeanUtils.copyProperties(model, warehouseVO);
		preparePageList();	
		//String message = this.getText("admin.warehouse.update.success",warehouseVO.getName());
		//this.addActionMessage(message);
		return SUCCESS;
	}
	
	
	public String updateWarehouse(){
		if(warehouseVO==null){
			return toInput;
		}
		StoreModel model = new StoreModel();
		BeanUtils.copyProperties(warehouseVO, model);
		storeService.updateModel(model);
		preparePageList();	
		ArrayList<String> list = new ArrayList<String>();
		//list.add(warehouseVO.getName());
		String message = this.getText("admin.warehouse.update.success",list);
		this.addActionMessage(message);
		return SUCCESS;
	}
	
	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public List<StoreModel> getParentList() {
		return parentList;
	}

	public void setParentList(List<StoreModel> parentList) {
		this.parentList = parentList;
	}

	


	public List<KeyValueModel> getWarehouseLevelList() {
		return warehouseLevelList;
	}

	public void setWarehouseLevelList(List<KeyValueModel> warehouseLevelList) {
		this.warehouseLevelList = warehouseLevelList;
	}
	
	public StoreSearchModel getWarehouseVO() {
		return warehouseVO;
	}

	public void setWarehouseVO(StoreSearchModel warehouseVO) {
		this.warehouseVO = warehouseVO;
	}
	public Integer getWarehouseParentId() {
		return warehouseParentId;
	}


	public void setWarehouseParentId(Integer warehouseParentId) {
		this.warehouseParentId = warehouseParentId;
	}
	public String getWarehouseLevel() {
		return warehouseLevel;
	}


	public void setWarehouseLevel(String warehouseLevel) {
		this.warehouseLevel = warehouseLevel;
	}
	
	public List<StoreSearchModel> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<StoreSearchModel> warehouseList) {
		this.warehouseList = warehouseList;
	}
	
	
	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
