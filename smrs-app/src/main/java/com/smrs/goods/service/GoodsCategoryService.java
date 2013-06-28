package com.smrs.goods.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.goods.dao.GoodsCategoryDao;
import com.smrs.goods.model.GoodsAttributeModel;
import com.smrs.goods.model.GoodsCategoryModel;
import com.smrs.service.BaseService;

@Component(value="goodsCategoryService")
public class GoodsCategoryService extends BaseService<GoodsCategoryModel,Long>{
	
	@Autowired
	private GoodsCategoryDao goodsCategoryDao ;
	

	
	/*
	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = goodsCategoryDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	*/
	public Pager<GoodsCategoryModel> getByNameLikePager(String name, Pager<GoodsCategoryModel> pager){
		//String name = model.getRegion().getName();
		Pager<GoodsCategoryModel> tempPager = goodsCategoryDao.getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<GoodsCategoryModel, Long> getPerformDao() {		
		return goodsCategoryDao;
	}

	public Set<GoodsAttributeModel> getAllContainAttributesByCategoryId(Long id){
		GoodsCategoryModel temp = this.getByPK(id);
		Set<GoodsAttributeModel> tempSubSet=new HashSet<GoodsAttributeModel>();
		if(temp.getParent()!=null && temp.getParent().getId()!=null && temp.getParent().getId().longValue()!=0l){
			tempSubSet= getAllContainAttributesByCategoryId(temp.getParent().getId());
		}
		Set<GoodsAttributeModel> tempSet = temp.getAttributes();
		tempSubSet.addAll(tempSet);		
		return tempSubSet;
	}
	
	public List<GoodsCategoryModel> getSubCategoryByParent(Long parentId,Long level){
		List<GoodsCategoryModel> list=goodsCategoryDao.getSubCategoryByParent(parentId,level);
		return list;
	}
	
}
