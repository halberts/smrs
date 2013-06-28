package com.smrs.logtrace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.logtrace.dao.LogTraceDao;
import com.smrs.logtrace.model.LogTraceModel;
import com.smrs.logtrace.model.LogTraceSearchModel;
import com.smrs.service.BaseService;

@Component(value="logTraceService")
public class LogTraceService extends BaseService<LogTraceModel,Long>{
	
	@Autowired
	private LogTraceDao logTraceDao ;
	

	

	public Pager<LogTraceModel> getByNameLikePager(String name,  Pager<LogTraceModel> pager){
		//String name = model.getRegion().getName();
		Pager<LogTraceModel> tempPager = logTraceDao.getByNameLikePager(name,pager); 
		return tempPager;
		//return null;
	}

	public Pager<LogTraceModel> getByNameTypeLikePager(LogTraceSearchModel searchModel,  Pager<LogTraceModel> pager){
		
		Pager<LogTraceModel> tempPager = logTraceDao.getByNameTypeLikePager(searchModel,pager); 
		return tempPager;		
	}
	
	@Override
	public BaseDAOHibernateImpl<LogTraceModel, Long> getPerformDao() {		
		return logTraceDao;
	}

	
	
}
