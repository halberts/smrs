package com.smrs.relationship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.SimpleHibernate3SupportDao;
import com.jof.framework.util.Pager;
import com.smrs.goods.model.GoodsSkuModel;
import com.smrs.relationship.dao.CustomerDao;
import com.smrs.relationship.model.CustomerModel;
import com.smrs.service.BaseService;

@Component(value="customerService")
public class CustomerService extends BaseService<CustomerModel,Long>{
	
	@Autowired
	private CustomerDao customerDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsItemDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<CustomerModel> getByNameLikePager(String name, Pager<CustomerModel> pager){
		//String name = model.getRegion().getName();
		Pager<CustomerModel> tempPager = getPerformDao().getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	
	public SimpleHibernate3SupportDao<CustomerModel, Long> getPerformDao() {		
		return customerDao;
	}

	
	
}
