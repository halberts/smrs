package com.smrs.goods.model;

import java.util.Date;

import com.smrs.model.BaseModel;


public class GoodsSkuHistoryModel extends BaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long skuId;
	private Double costPrice;
	private Date  belongMonth;
	
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Date getBelongMonth() {
		return belongMonth;
	}
	public void setBelongMonth(Date belongMonth) {
		this.belongMonth = belongMonth;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	

}
