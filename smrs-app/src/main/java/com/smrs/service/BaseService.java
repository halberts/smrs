package com.smrs.service;

import java.io.Serializable;
import java.util.List;

import com.jof.framework.dao.hibernate.SimpleHibernate3SupportDao;
import com.jof.framework.util.Reflections;

public abstract class BaseService<T,PK extends Serializable> {
	protected Class<T> entityClass;
	
	public void addModel(T object){
		this.getPerformDao().save(object);
	}
	
	public void deleteModel(T object){
		this.getPerformDao().delete(object);
	}
	
	public T getByPK(Serializable id){
		return getPerformDao().get((PK)id);
	}
	
	public void updateModel(T object){
		this.getPerformDao().update(object);
	}
	
	public T load(PK id){
		return getPerformDao().load(id);
	}
	public List<T> getAllActive(Class<T> tClass){
		//String hql = " from "+tClass +" where activeFlag='Y'";
		//String classModelName=StringUtils.substringAfterLast(tClass.getSimpleName(), ".");
		String hql=" from " + tClass.getSimpleName()+" where status='Y'";
		List<T> list=getPerformDao().findByHQL(hql);
		return list;
	}

	public List<T> getByName(String name){
		String hql=" from " + entityClass.getSimpleName()+" where name='"+name+"'";		
		List<T> list=getPerformDao().findByHQL(hql);		
		return list;
	}

	public List<T> getByCode(String code){
		String hql=" from " + entityClass.getSimpleName()+" where code='"+code+"'";		
		List<T> list=getPerformDao().findByHQL(hql);		
		return list;
	}
	
	public List<T> getAllActive(){
		String hql=" from " + entityClass.getSimpleName()+" where status='Y'";
		List<T> list=getPerformDao().findByHQL(hql);
		return list;
	}
	
	
	
	public BaseService(){
		this.entityClass = Reflections.getSuperClassGenricType(getClass());
	}

	
	public abstract SimpleHibernate3SupportDao<T,PK> getPerformDao();
	
}
