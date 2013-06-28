package com.smrs.basicdata.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.smrs.basicdata.enums.DictTypeEnum;


public class DictBaseDao<T,ID extends Serializable> extends BaseDAOHibernateImpl<T,ID>{
	public List<T> getByDictType(DictTypeEnum dictType,String parentId){
		String queryString="";
		if(StringUtils.isEmpty(parentId)){
			queryString = " from "+this.entityClass.getSimpleName() + " a where a.dictType= '"+dictType.getId()+"'" ;
		}else{
			queryString = " from "+this.entityClass.getSimpleName() + " a where a.dictType= '"+dictType.getId()+"' and parent_id='" + parentId+"'" ;
		}
		Query query=this.createHQLQuery(queryString);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		return list;
	}
	
	
}
