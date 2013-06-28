package com.smrs.basicdata.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.ChannelModel;
import com.smrs.basicdata.vo.ChannelSearchModel;
import com.smrs.security.model.GroupModel;

@Component(value="channelDao")
public class ChannelDao extends BaseDAOHibernateImpl<ChannelModel, Long>{
	
	public Pager<ChannelModel> getByNameLikePager(ChannelSearchModel model){
		String name = model.getChannel().getName();
		String hsql="";
		
		if(StringUtils.isNotEmpty(name)){
		   hsql = " from ChannelModel where name like '%"+name+"%'"; 
		}else{
			hsql = " from ChannelModel ";
		}
		Pager<ChannelModel> result = Pager.cloneFromPager(model.getPager());
		Long totalCount=this.countHQLUniqueResule(hsql, new Object[]{});
		result.setTotalRecords(totalCount);		
		Query query = this.getSession().createQuery(hsql);
		this.setPageParameterToQuery(query, model.getPager());
		result.setRecords(query.list());
		return result;		
	}
	
	
}
