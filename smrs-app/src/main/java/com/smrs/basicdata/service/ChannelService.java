package com.smrs.basicdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.dao.ChannelDao;
import com.smrs.basicdata.model.ChannelModel;
import com.smrs.basicdata.vo.ChannelSearchModel;
import com.smrs.service.BaseService;

@Component(value="channelService")
public class ChannelService extends BaseService<ChannelModel,Long>{
	@Autowired
	private ChannelDao channelDao ;
	
	public ChannelDao getChannelDao() {
		return channelDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	public Pager<ChannelModel> getByNameLikePager(final ChannelSearchModel model){
		return channelDao.getByNameLikePager(model);
		
	}
	
	@Override
	public BaseDAOHibernateImpl<ChannelModel, Long> getPerformDao() {		
		return channelDao;
	}

	
	
}
