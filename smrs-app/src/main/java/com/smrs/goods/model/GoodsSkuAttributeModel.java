package com.smrs.goods.model;

import org.apache.commons.lang3.StringUtils;

import com.smrs.model.BaseModel;
import com.smrs.util.DictConstants;


public class GoodsSkuAttributeModel extends BaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long skuId;
	Long itemId;
	Long attributeId;
	String attributeCode;
	String attributeValue;
	String attributeIdCode;
	
	public String getAttributeIdCode() {
		attributeIdCode =  attributeId + DictConstants.SEPARATE_KEY+attributeCode;
		return attributeIdCode;
	}
	
	public void setAttributeIdCode(String attributeIdCode) {
		if(StringUtils.isNotEmpty(attributeIdCode)){
			String[] result = attributeIdCode.split(DictConstants.SEPARATE_KEY);
			attributeId = Long.parseLong(result[0]);
			attributeCode = result[1];
		}
		this.attributeIdCode = attributeIdCode;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeCode() {
		return attributeCode;
	}
	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
}
