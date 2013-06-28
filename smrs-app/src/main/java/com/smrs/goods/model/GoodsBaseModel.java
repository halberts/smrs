package com.smrs.goods.model;

import java.io.Serializable;
import java.util.StringTokenizer;

import com.smrs.model.BaseModel;
import com.smrs.util.DictConstants;

public class GoodsBaseModel<PK extends Serializable> extends BaseModel<PK>{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String description;
	private String code;
	protected Integer sortIndex;
	
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	public String getIdName(){
		String temp = id + DictConstants.SEPARATE_KEY+name;
		return temp;
	}
	
	public String getIdCode(){
		String temp = id + DictConstants.SEPARATE_KEY+code;
		return temp;
	}
	
	public String getNameCode(){
		String temp = name + DictConstants.SEPARATE_KEY+code;
		return temp;
	}
	
}
