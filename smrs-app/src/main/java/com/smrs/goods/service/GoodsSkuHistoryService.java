package com.smrs.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.GoodsSkuHistoryDao;
import com.smrs.goods.model.GoodsSkuHistoryModel;
import com.smrs.service.BaseService;

@Component(value="goodsSkuHistoryService")
public class GoodsSkuHistoryService extends BaseService<GoodsSkuHistoryModel,Long>{
	
	@Autowired
	private GoodsSkuHistoryDao goodsSkuHistoryDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsItemDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<GoodsSkuHistoryModel> getByNameLikePager(String name, Pager<GoodsSkuHistoryModel> pager){
		//String name = model.getRegion().getName();
		Pager<GoodsSkuHistoryModel> tempPager = getPerformDao().getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<GoodsSkuHistoryModel, Long> getPerformDao() {		
		return goodsSkuHistoryDao;
	}

	
	
}
