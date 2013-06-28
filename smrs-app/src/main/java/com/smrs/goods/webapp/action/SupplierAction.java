package com.smrs.goods.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.enums.StatusEnum;
import com.smrs.goods.model.SupplierModel;
import com.smrs.relationship.webapp.action.BaseRelationshipAction;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;



public class SupplierAction extends BaseRelationshipAction{
	private static final long serialVersionUID = 1L;
	protected SupplierModel supplier = new SupplierModel();
	protected Pager<SupplierModel> pager = new Pager<SupplierModel>(); 
	protected String titleName="供应商";
	

	public String addSupplier(){
		if(StringUtils.isEmpty(this.actionCommand)){
			//allSupplierList=supplierService.getAllActive();
			this.preparePageSelectList();
			return SUCCESS;
		}
		
		if(supplier==null || StringUtils.isEmpty(supplier.getName())){
			this.preparePageSelectList();			
			supplier = new SupplierModel();
			return SUCCESS;
		}
		supplier.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, supplier);
		this.supplierService.addModel(supplier);	
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchSupplier(){		
		pager=supplierService.getByNameLikePager(supplier.getName(),pager);
		return SUCCESS;
	}

	
	public String updateSupplier(){		
		if(StringUtils.isEmpty(this.actionCommand)){
			//allSupplierList=supplierService.getAllActive();			
			this.preparePageSelectList();	
			supplier = supplierService.getByPK(supplier.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, supplier);
			supplierService.updateModel(supplier);
			this.preparePageSelectList();	
			this.addActionMessage("修改成功");			
			
		}
		return SUCCESS;
	}
	
	public String deleteSupplier(){
		supplierService.deleteModel(supplier);
		return "search";
	}
	

	public Pager<SupplierModel> getPager() {
		return pager;
	}


	public void setPager(Pager<SupplierModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public SupplierModel getSupplier() {
		return supplier;
	}


	public void setSupplier(SupplierModel supplier) {
		this.supplier = supplier;
	}
}
