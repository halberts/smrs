package com.smrs.goods.model;

import com.smrs.model.BaseModel;


public class GoodsSkuModel extends BaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String shortName;
	private String code;
	private String description;	
	protected int sortIndex;
	private String mfgSkuCode;
	private Long itemId;
	private String itemCode;
	private String salesUnit;
	private Character batchFlag;
	private Character validFlag;
	private Integer  storageType;
	private Integer  physicalType;
	private String barCode1;
	private String barCode2;
	private String barCode3;
	private Long supplierId;
	private Double purchasePrice;
	private Double labelPrice;
	private Double marketPrice;
	private Double costPrice;
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMfgSkuCode() {
		return mfgSkuCode;
	}
	public void setMfgSkuCode(String mfgSkuCode) {
		this.mfgSkuCode = mfgSkuCode;
	}
	public int getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getSalesUnit() {
		return salesUnit;
	}
	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}
	public Character getBatchFlag() {
		return batchFlag;
	}
	public void setBatchFlag(Character batchFlag) {
		this.batchFlag = batchFlag;
	}
	public Character getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(Character validFlag) {
		this.validFlag = validFlag;
	}
	public Integer getStorageType() {
		return storageType;
	}
	public void setStorageType(Integer storageType) {
		this.storageType = storageType;
	}
	public Integer getPhysicalType() {
		return physicalType;
	}
	public void setPhysicalType(Integer physicalType) {
		this.physicalType = physicalType;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getBarCode1() {
		return barCode1;
	}
	public void setBarCode1(String barCode1) {
		this.barCode1 = barCode1;
	}
	public String getBarCode2() {
		return barCode2;
	}
	public void setBarCode2(String barCode2) {
		this.barCode2 = barCode2;
	}
	public String getBarCode3() {
		return barCode3;
	}
	public void setBarCode3(String barCode3) {
		this.barCode3 = barCode3;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Double getLabelPrice() {
		return labelPrice;
	}
	public void setLabelPrice(Double labelPrice) {
		this.labelPrice = labelPrice;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	

}
