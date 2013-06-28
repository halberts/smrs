package com.smrs.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.GoodsItemDao;
import com.smrs.goods.model.GoodsItemModel;
import com.smrs.service.BaseService;

@Component(value="goodsItemService")
public class GoodsItemService extends BaseService<GoodsItemModel,Long>{
	
	@Autowired
	private GoodsItemDao goodsItemDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsItemDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<GoodsItemModel> getByNameLikePager(String name, Pager<GoodsItemModel> pager){
		//String name = model.getRegion().getName();
		Pager<GoodsItemModel> tempPager = goodsItemDao.getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<GoodsItemModel, Long> getPerformDao() {		
		return goodsItemDao;
	}

	
	
}
