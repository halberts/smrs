package com.smrs.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.GoodsItemAttributeDao;
import com.smrs.goods.model.GoodsSkuAttributeModel;
import com.smrs.service.BaseService;

@Component(value="goodsItemAttributeService")
public class GoodsItemAttributeService extends BaseService<GoodsSkuAttributeModel,Long>{
	
	@Autowired
	private GoodsItemAttributeDao goodsItemAttributeDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsItemDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<GoodsSkuAttributeModel> getByNameLikePager(String name, Pager<GoodsSkuAttributeModel> pager){
		//String name = model.getRegion().getName();
		Pager<GoodsSkuAttributeModel> tempPager = goodsItemAttributeDao.getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<GoodsSkuAttributeModel, Long> getPerformDao() {		
		return goodsItemAttributeDao;
	}

	
	
}
