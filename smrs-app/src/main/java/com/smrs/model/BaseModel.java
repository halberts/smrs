package com.smrs.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel <PK extends Serializable> implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	protected PK id;
	protected String name;
	protected Character status;
	protected String creator;
	protected Date creationDate =new Date();
	protected String modifiedBy;
	protected Date lastModifyDate = new Date();


	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
