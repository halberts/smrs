package com.smrs.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.GoodsAttributeDao;
import com.smrs.goods.model.GoodsAttributeModel;
import com.smrs.service.BaseService;

@Component(value="goodsAttributeService")
public class GoodsAttributeService extends BaseService<GoodsAttributeModel,Long>{
	
	@Autowired
	private GoodsAttributeDao goodsAttributeDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsAttributeDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<GoodsAttributeModel> getByNameLikePager(String name, Pager<GoodsAttributeModel> pager){
		//String name = model.getRegion().getName();
		Pager<GoodsAttributeModel> tempPager = goodsAttributeDao.getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<GoodsAttributeModel, Long> getPerformDao() {		
		return goodsAttributeDao;
	}

	
	
}
