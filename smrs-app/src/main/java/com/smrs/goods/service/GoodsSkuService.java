package com.smrs.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.GoodsSkuDao;
import com.smrs.goods.model.GoodsItemModel;
import com.smrs.goods.model.GoodsSkuModel;
import com.smrs.service.BaseService;

@Component(value="goodsSkuService")
public class GoodsSkuService extends BaseService<GoodsSkuModel,Long>{
	
	@Autowired
	private GoodsSkuDao goodsSkuDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsItemDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<GoodsSkuModel> getByNameLikePager(String name, Pager<GoodsSkuModel> pager){
		//String name = model.getRegion().getName();
		Pager<GoodsSkuModel> tempPager = getPerformDao().getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<GoodsSkuModel, Long> getPerformDao() {		
		return goodsSkuDao;
	}

	
	
}
