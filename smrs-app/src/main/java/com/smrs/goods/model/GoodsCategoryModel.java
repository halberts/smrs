package com.smrs.goods.model;

import java.util.HashSet;
import java.util.Set;

import com.smrs.util.DictConstants;


public class GoodsCategoryModel extends GoodsBaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long level;
	private GoodsCategoryModel parent;
	private Set<GoodsAttributeModel> attributes = new HashSet<GoodsAttributeModel>();
	
	/*
	public GoodsCategoryModel(){
		
	}
	public GoodsCategoryModel(Long id,String name){
		this.setId(id);
		this.setName(name);
	}
	*/
	public Set<GoodsAttributeModel> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<GoodsAttributeModel> attributes) {
		this.attributes = attributes;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public GoodsCategoryModel getParent() {
		return parent;
	}

	public void setParent(GoodsCategoryModel parent) {
		this.parent = parent;
	}
	
	
}
