package com.smrs.goods.dao;

import java.util.List;

import org.hibernate.Query;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.smrs.goods.model.GoodsCategoryModel;

public class GoodsCategoryDao extends BaseDAOHibernateImpl<GoodsCategoryModel, Long>{
	public List<GoodsCategoryModel> getSubCategoryByParent(Long parentId,Long level){
		String hsql ="";
		Object[] values =null;
		if(parentId==null || parentId.equals(0l)){
			hsql = " from GoodsCategoryModel where  level=1";
			values = new Object[]{};
		}
		if(parentId!=null){
			hsql = " from GoodsCategoryModel where parent.id=? and level=?";
			values = new Object[]{parentId,level};
		}
		Query query = this.createHQLQuery(hsql, values);
		return query.list();
	}
}
