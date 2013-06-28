package com.haier.openplatform.console.jmx.service;

import java.util.List;

import com.haier.openplatform.console.jmx.domain.HornetqResourcesAuditTrail;
import com.haier.openplatform.console.jmx.model.JmxSearchModel;
import com.haier.openplatform.util.Pager;

/** 
 * @author WangJian
 */
public interface HornetqResourcesOperationService { 
	public List<HornetqResourcesAuditTrail> findAll(); 
	
	public HornetqResourcesAuditTrail save(HornetqResourcesAuditTrail auditTrail);
 
	public Pager<HornetqResourcesAuditTrail> findAllByPage(JmxSearchModel jmxSearchModel);
}