package com.smrs.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.GoodsAttributeValueDao;
import com.smrs.goods.model.GoodsAttributeValueModel;
import com.smrs.service.BaseService;

@Component(value="goodsAttributeValueService")
public class GoodsAttributeValueService extends BaseService<GoodsAttributeValueModel,Long>{
	
	@Autowired
	private GoodsAttributeValueDao goodsAttributeValueDao ;
	



	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsAttributeDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<GoodsAttributeValueModel> getByNameLikePager(String name, Pager<GoodsAttributeValueModel> pager){
		//String name = model.getRegion().getName();
		Pager<GoodsAttributeValueModel> tempPager = goodsAttributeValueDao.getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<GoodsAttributeValueModel, Long> getPerformDao() {		
		return goodsAttributeValueDao;
	}

	
	
}
