package com.haier.openplatform.console.jmx.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * hornetq消息对象 
 * @author WangJian
 */ 
public class HornetqResourcesAuditTrail implements Serializable {
 
	private static final long serialVersionUID = -669081675644596236L;
	private Long id;
	private String resourceIdentifier;
	private String resourceKey;
	private String resourceValue; 
	private Date auditTimestamp;

	public HornetqResourcesAuditTrail() {
	}
	
	
	public Date getAuditTimestamp() {
		return auditTimestamp;
	} 
	public void setAuditTimestamp(Date auditTimestamp) {
		this.auditTimestamp = auditTimestamp;
	} 
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public String getResourceKey() {
		return this.resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
 
	public String getResourceValue() {
		return this.resourceValue;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}
 
	public String getResourceIdentifier() {
		return this.resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}   
 
	public String toString() {
		return "HornetqResourcesAuditTrail [id=" + id + ", resourceIdentifier=" + resourceIdentifier + ", resourceKey="
				+ resourceKey + ", resourceValue=" + resourceValue +", auditTimstamp=" + auditTimestamp + "]";
	}
}
