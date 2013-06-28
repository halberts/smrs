package com.smrs.basicdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.dao.RegionDao;
import com.smrs.basicdata.model.RegionModel;
import com.smrs.basicdata.vo.RegionSearchModel;
import com.smrs.service.BaseService;

@Component(value="regionService")
public class RegionService extends BaseService<RegionModel,Long>{
	@Autowired
	private RegionDao regionDao ;
	


	public Pager<RegionModel> getByNameLikePager(final RegionSearchModel model){
		String name = model.getRegion().getName();
		Pager<RegionModel> pager = regionDao.getByNameLikePager(name,model.getPager()); 
		return pager;
		//return null;
	}
	
	@Override
	public BaseDAOHibernateImpl<RegionModel, Long> getPerformDao() {		
		return regionDao;
	}

	
	
}
