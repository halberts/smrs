package com.haier.openplatform.console.jmx.dao;

import java.util.List;

import com.haier.openplatform.console.jmx.domain.HornetqResourcesAuditTrail;
import com.haier.openplatform.console.jmx.model.JmxSearchModel;
import com.haier.openplatform.dao.BaseDAO;
import com.haier.openplatform.util.Pager;

/** 
 * HornetQ消息记录持久层接口
 * @author WangJian
 */ 
public interface HornetqResourcesOperationDAO extends BaseDAO<HornetqResourcesAuditTrail, Long>{ 
	public List<HornetqResourcesAuditTrail> findAll();
 
	public HornetqResourcesAuditTrail saveHornetQ(HornetqResourcesAuditTrail audit);
 
	public Pager<HornetqResourcesAuditTrail> findAllByPage(JmxSearchModel jmxSearchModel);
}