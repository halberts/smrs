package com.smrs.basicdata.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.ChannelModel;
import com.smrs.basicdata.model.RegionModel;
import com.smrs.basicdata.vo.RegionSearchModel;

public class RegionDao extends BaseDAOHibernateImpl<RegionModel, Long>{
	
	public Pager<RegionModel> getByNameLikePager(RegionSearchModel model){
		String name = model.getRegion().getName();
		String hsql="";
		
		if(StringUtils.isNotEmpty(name)){
		   hsql = " from RegionModel where name like '%"+name+"%'"; 
		}else{
			hsql = " from RegionModel ";
		}
		Pager<RegionModel> result = Pager.cloneFromPager(model.getPager());
		Long totalCount=this.countHQLUniqueResule(hsql, new Object[]{});
		result.setTotalRecords(totalCount);		
		Query query = this.getSession().createQuery(hsql);
		this.setPageParameterToQuery(query, model.getPager());
		result.setRecords(query.list());
		return result;		
	}
}
