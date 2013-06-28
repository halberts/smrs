package com.smrs.relationship.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.enums.StatusEnum;
import com.smrs.relationship.model.CustomerModel;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;



public class CustomerAction extends BaseRelationshipAction{
	private static final long serialVersionUID = 1L;
	protected CustomerModel customer = new CustomerModel();
	protected Pager<CustomerModel> pager = new Pager<CustomerModel>(); 
	protected String titleName="客户";
	

	public String addCustomer(){
		if(StringUtils.isEmpty(this.actionCommand)){
			//allCustomerList=customerService.getAllActive();
			this.preparePageSelectList();
			return SUCCESS;
		}
		
		if(customer==null || StringUtils.isEmpty(customer.getName())){
			this.preparePageSelectList();			
			customer = new CustomerModel();
			return SUCCESS;
		}
		customer.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, customer);
		this.customerService.addModel(customer);	
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchCustomer(){		
		pager=customerService.getByNameLikePager(customer.getName(),pager);
		return SUCCESS;
	}

	
	public String updateCustomer(){		
		if(StringUtils.isEmpty(this.actionCommand)){
			//allCustomerList=customerService.getAllActive();			
			this.preparePageSelectList();	
			customer = customerService.getByPK(customer.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, customer);
			customerService.updateModel(customer);
			this.preparePageSelectList();	
			this.addActionMessage("修改成功");			
			
		}
		return SUCCESS;
	}
	
	public String deleteCustomer(){
		customerService.deleteModel(customer);
		return "search";
	}
	

	public Pager<CustomerModel> getPager() {
		return pager;
	}


	public void setPager(Pager<CustomerModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public CustomerModel getCustomer() {
		return customer;
	}


	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}
}
