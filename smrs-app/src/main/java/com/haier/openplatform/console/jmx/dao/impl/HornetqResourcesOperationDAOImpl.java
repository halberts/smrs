package com.haier.openplatform.console.jmx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.haier.openplatform.console.jmx.dao.HornetqResourcesOperationDAO;
import com.haier.openplatform.console.jmx.domain.HornetqResourcesAuditTrail;
import com.haier.openplatform.console.jmx.model.JmxSearchModel;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.util.Pager;

/**
 * HornetQ接口持久层管理实现 
 * @author WangJian
 */ 
public class HornetqResourcesOperationDAOImpl extends BaseDAOHibernateImpl<HornetqResourcesAuditTrail,Long> implements HornetqResourcesOperationDAO {

	public List<HornetqResourcesAuditTrail> findAll(){
		return null; 
	} 
	
	public HornetqResourcesAuditTrail saveHornetQ(HornetqResourcesAuditTrail audit){
		super.save(audit);
		return audit;
	} 
	
	@Override
	public Pager<HornetqResourcesAuditTrail> findAllByPage(JmxSearchModel model){  
			Pager<HornetqResourcesAuditTrail> p = this.findPage(model.getPager(), this.createCriterions(model));
			return p; 
	}
	
	private Criterion[] createCriterions(JmxSearchModel model){
		List<Criterion> criterions = new ArrayList<Criterion>();    
		if(StringUtils.isNotBlank(model.getHornetqResourcesAuditTrail().getResourceKey())){
			criterions.add(Restrictions.like("resourceKey", model.getHornetqResourcesAuditTrail().getResourceKey(),MatchMode.ANYWHERE));
		}
		Criterion[] result = new Criterion[criterions.size()]; 
		for(int i = 0; i < criterions.size(); i++){
			result[i] = criterions.get(i);
		}
		return result;
	}  

}