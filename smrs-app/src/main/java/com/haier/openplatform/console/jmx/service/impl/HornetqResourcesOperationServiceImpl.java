package com.haier.openplatform.console.jmx.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.haier.openplatform.console.jmx.dao.impl.HornetqResourcesOperationDAOImpl;
import com.haier.openplatform.console.jmx.domain.HornetqResourcesAuditTrail;
import com.haier.openplatform.console.jmx.model.JmxSearchModel;
import com.haier.openplatform.console.jmx.service.HornetqResourcesOperationService;
import com.haier.openplatform.util.Pager;


/**
 * service implementation of HornetqResourcesOperationService
 * @author Jeffrey
 */ 
public class HornetqResourcesOperationServiceImpl implements HornetqResourcesOperationService {

	private static final Logger LOG = LoggerFactory.getLogger(HornetqResourcesOperationServiceImpl.class); 
	private HornetqResourcesOperationDAOImpl hornetqResourcesOperationDAO;  
	
	
	public void setHornetqResourcesOperationDAO(
			HornetqResourcesOperationDAOImpl hornetqResourcesOperationDAO) {
		this.hornetqResourcesOperationDAO = hornetqResourcesOperationDAO;
	}

	public List<HornetqResourcesAuditTrail> findAll() {
		LOG.info("method call findAll begins");
		//List<HornetqResourcesAuditTrail> audits = em.createNamedQuery("HornetqResourcesAuditTrail.findAll", HornetqResourcesAuditTrail.class).getResultList();
		List<HornetqResourcesAuditTrail> audits = hornetqResourcesOperationDAO.findAll();
		return audits;	
	}
	 
	public HornetqResourcesAuditTrail save(HornetqResourcesAuditTrail audit) {
		LOG.info("method call save begins"); 
		return audit;
	}
	
	
	@Transactional(readOnly=true)
	public Pager<HornetqResourcesAuditTrail> findAllByPage(JmxSearchModel jmxSearchModel) {
		LOG.info("method call findAllByPage begins"); 
		return hornetqResourcesOperationDAO.findAllByPage(jmxSearchModel);
	}	
	
}
