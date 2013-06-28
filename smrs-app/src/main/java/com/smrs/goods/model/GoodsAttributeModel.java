package com.smrs.goods.model;

import java.util.HashSet;
import java.util.Set;

import com.smrs.security.model.ResourceModel;


public class GoodsAttributeModel extends GoodsBaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String description;
	private String code;
	private Set<GoodsCategoryModel> categories = new HashSet<GoodsCategoryModel>();
	
	public Set<GoodsCategoryModel> getCategories() {
		return categories;
	}

	public void setCategories(Set<GoodsCategoryModel> categories) {
		this.categories = categories;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		GoodsAttributeModel other = (GoodsAttributeModel) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code) || !id.equals(other.getId()) || !name.equals(other.getName()) ){
			return false;
		} else if(code.equals(other.code) && id.equals(other.getId()) && name.equals(other.getName())){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return  "id="+id+ " name=" +name +" code=" + code   ;
	}
	
	
	
}
