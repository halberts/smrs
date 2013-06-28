package com.haier.openplatform.console.jmx.model;

import com.haier.openplatform.console.jmx.domain.HornetqResourcesAuditTrail;
import com.haier.openplatform.util.SearchModel;
/**
 * jmx搜索封装对象
 * @author WangJian
 *
 */
public class JmxSearchModel extends SearchModel<HornetqResourcesAuditTrail>{
 
	private static final long serialVersionUID = 1599371478353629047L;
	private HornetqResourcesAuditTrail hornetqResourcesAuditTrail= new HornetqResourcesAuditTrail();
	public HornetqResourcesAuditTrail getHornetqResourcesAuditTrail() {
		return hornetqResourcesAuditTrail;
	}
	public void setHornetqResourcesAuditTrail(
			HornetqResourcesAuditTrail hornetqResourcesAuditTrail) {
		this.hornetqResourcesAuditTrail = hornetqResourcesAuditTrail;
	} 
}
