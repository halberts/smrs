package com.smrs.basicdata.model;

import com.smrs.model.BaseModel;

public class DictAreaModel extends BaseModel<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dictId;
	
	private String dictType;
	private String dictValue;
	private String dictDesc;
	private Integer dictOrder;
	private DictAreaModel parent;
	private Integer dictStatus;
	
	
	public Integer getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(Integer dictStatus) {
		this.dictStatus = dictStatus;
	}
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public String getDictDesc() {
		return dictDesc;
	}
	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}
	public Integer getDictOrder() {
		return dictOrder;
	}
	public void setDictOrder(Integer dictOrder) {
		this.dictOrder = dictOrder;
	}
	
	public DictAreaModel getParent() {
		return parent;
	}
	public void setParent(DictAreaModel parent) {
		this.parent = parent;
	}
	
	
	
}
