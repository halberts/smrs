package com.smrs.goods.model;



public class GoodsSaleStoreModel extends GoodsBaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long skuId;
	protected Long storeId;
	protected Double salePrice;
	
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	
}
