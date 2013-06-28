package com.smrs.logtrace.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.logtrace.model.LogTraceModel;
import com.smrs.logtrace.model.LogTraceSearchModel;

public class LogTraceDao extends BaseDAOHibernateImpl<LogTraceModel, Long>{
	
	public Pager<LogTraceModel> getByNameTypeLikePager(LogTraceSearchModel searchModel,final Pager<LogTraceModel> pageRequest){
		//String name = model.getRegion().getName();
		String hsql="";
		String name = searchModel.getName();
		
		if(StringUtils.isNotEmpty(name)){
		   hsql = " from "+this.entityClass.getSimpleName()+ " where name like '%"+searchModel.getName()+"%'"; 
		   hsql=hsql+" and creationDate>= ? and creationDate<?";   
		}else{
			hsql = " from "+this.entityClass.getSimpleName() + " ";
			hsql=hsql+" where creationDate>= ? and creationDate<?";
		}
		
		if(searchModel.getType()!=null){
			hsql = hsql +" and type=? ";
		}
		Pager<LogTraceModel> result = Pager.cloneFromPager(pageRequest);
		Object[] values = null;
		if(searchModel.getType()!=null){
			 values=new Object[]{searchModel.getStartDate(),searchModel.getEndDate(),searchModel.getType()};
		}else{
			 values=new Object[]{searchModel.getStartDate(),searchModel.getEndDate()};
		}
		Long totalCount=countHQLUniqueResule(hsql,values);
		result.setTotalRecords(totalCount);		
		Query query = this.createHQLQuery(hsql, values);		
		this.setPageParameterToQuery(query, pageRequest);
		result.setRecords(query.list());
		return result;		
	}
	
}
