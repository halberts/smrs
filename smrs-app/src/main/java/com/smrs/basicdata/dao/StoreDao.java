package com.smrs.basicdata.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.PageInfo;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.enums.StoreTypeEnum;
import com.smrs.basicdata.model.StoreModel;

@Component(value="storeDao")
public class StoreDao extends BaseDAOHibernateImpl<StoreModel,Long>{
	
	public List<StoreModel> findByWarehouseName(String name){
		String hql = " from WarehouseModel a where a.name = '"+name+"'";
		@SuppressWarnings("unchecked")
		List<StoreModel> list = findByHQL(hql);			
		return list;
	}

	public List<StoreModel> findByStoreModelNameLike(String name,PageInfo pageInfo){
		String hql = " from StoreModel a where a.name like '%"+name+"%' and a.id<>-1";
		String sqlCount = "select count(*) from StoreModel a where a.name like '%"+name+"%' and a.id<>-1";
		Query countQuery = getSession().createQuery(sqlCount);
		List<Long> list2 = (List<Long>)countQuery.list();
		Long count = list2.get(0);
		pageInfo.setTotalRecords(count);
		Query query = getSession().createQuery(hql);
		//this.setPageParameterToQuery(query, pageInfo);
		
		@SuppressWarnings("unchecked")
		List<StoreModel> list = query.list();	
		
		return list;
	}
	
	public List<StoreModel> getStoreByStoreType(StoreTypeEnum storeType){
		String queryString = " from StoreModel a where a.storeType = " +storeType.getId();
		Query query = this.createHQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<StoreModel> list = query.list();
		return list;
	}
	
	public List<StoreModel> getMajorStore(){
		String queryString = " from StoreModel a where a.storeType = " +StoreTypeEnum.major.getId();
		Query query = this.createHQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<StoreModel> list = query.list();
		if(CollectionUtils.isNotEmpty(list)){
			return list;
			//StoreModel model = list.get(0);;
			//return model;
		}
		return null;
	}
	
	public Pager<StoreModel> getByNameLikeAndTypePager(String name,final Pager<StoreModel> pageRequest,StoreTypeEnum storeType){
		//String name = model.getRegion().getName();
		String hsql="";
		
		if(StringUtils.isNotEmpty(name)){
		   hsql = " from "+this.entityClass.getSimpleName()+ " where storeType="+storeType.getId()+" and name like '%"+name+"%'"; 
		}else{
			hsql = " from "+this.entityClass.getSimpleName()+" where storeType="+storeType.getId();
		}
		Pager<StoreModel> result = Pager.cloneFromPager(pageRequest);
		Long totalCount=this.countHQLUniqueResule(hsql, new Object[]{});
		result.setTotalRecords(totalCount);		
		Query query = this.getSession().createQuery(hsql);
		this.setPageParameterToQuery(query, pageRequest);
		result.setRecords(query.list());
		return result;		
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<StoreModel> getChilden(Long id){
		String hsql = " from StoreModel where parentId = " + id;
		Query query = this.getSession().createQuery(hsql);
		List list = query.list();
		return list;
	}  
}
