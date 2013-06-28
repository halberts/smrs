package com.smrs.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.SimpleHibernate3SupportDao;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.SupplierDao;
import com.smrs.goods.model.SupplierModel;
import com.smrs.service.BaseService;

@Component(value="supplierService")
public class SupplierService extends BaseService<SupplierModel,Long>{
	
	@Autowired
	private SupplierDao supplierDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsItemDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<SupplierModel> getByNameLikePager(String name, Pager<SupplierModel> pager){
		//String name = model.getRegion().getName();
		Pager<SupplierModel> tempPager = getPerformDao().getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	
	public SimpleHibernate3SupportDao<SupplierModel, Long> getPerformDao() {		
		return supplierDao;
	}

	
	
}
