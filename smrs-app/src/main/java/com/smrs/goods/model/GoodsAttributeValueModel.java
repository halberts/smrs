package com.smrs.goods.model;


public class GoodsAttributeValueModel extends GoodsBaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GoodsAttributeModel goodsAttribute;
	private String value;
	private String attributeCode;
	
	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	public GoodsAttributeModel getGoodsAttribute() {
		return goodsAttribute;
	}

	public void setGoodsAttribute(GoodsAttributeModel goodsAttribute) {
		this.goodsAttribute = goodsAttribute;
	}
	
	
}
