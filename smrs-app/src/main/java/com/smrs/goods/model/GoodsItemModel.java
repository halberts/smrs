package com.smrs.goods.model;

import org.apache.commons.lang3.StringUtils;

import com.smrs.model.BaseModel;
import com.smrs.util.DictConstants;


public class GoodsItemModel extends BaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String shortName;
	private String code;
	private String description;	
	protected int sortIndex;
	private String mfgSkuCode;
	private Long categoryId1;
	private String categoryName1;
	protected String categoryIdName1;	
	private Long categoryId2;
	private String categoryName2;
	protected String categoryIdName2;
	private Long categoryId3;
	private String categoryName3;
	protected String categoryIdName3;
	private Long categoryId4;
	private String categoryName4;	
	protected String categoryIdName4;
	private Long supplierId;


	private String brand;
	private String salesUnit;
	private Character batchFlag;
	private Character validFlag;
	private Integer  storageType;
	private Integer  physicalType;
	
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
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
	public Long getCategoryId1() {
		return categoryId1;
	}
	public void setCategoryId1(Long categoryId1) {
		this.categoryId1 = categoryId1;
	}
	public String getCategoryName1() {
		return categoryName1;
	}
	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}
	public Long getCategoryId2() {
		return categoryId2;
	}
	public void setCategoryId2(Long categoryId2) {
		this.categoryId2 = categoryId2;
	}
	public String getCategoryName2() {
		return categoryName2;
	}
	public void setCategoryName2(String categoryName2) {
		this.categoryName2 = categoryName2;
	}
	public Long getCategoryId3() {
		return categoryId3;
	}
	public void setCategoryId3(Long categoryId3) {
		this.categoryId3 = categoryId3;
	}
	public String getCategoryName3() {
		return categoryName3;
	}
	public void setCategoryName3(String categoryName3) {
		this.categoryName3 = categoryName3;
	}
	public Long getCategoryId4() {
		return categoryId4;
	}
	public void setCategoryId4(Long categoryId4) {
		this.categoryId4 = categoryId4;
	}
	public String getCategoryName4() {
		return categoryName4;
	}
	public void setCategoryName4(String categoryName4) {
		this.categoryName4 = categoryName4;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
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
	
	public String getNameAndCode(){
		String temp = this.getName() +DictConstants.SEPARATE_KEY +this.getCode();
		return temp;
	}
	
	public String getCategoryIdName1() {
		categoryIdName1 = this.categoryId1 + DictConstants.SEPARATE_KEY+this.categoryIdName1;
		return categoryIdName1;
	}
	public void setCategoryIdName1(String categoryIdName1) {
		if(StringUtils.isNotEmpty(categoryIdName1)){
			String[] result = categoryIdName1.split(DictConstants.SEPARATE_KEY);
			categoryId1 = Long.parseLong(result[0]);
			categoryName1 = result[1];
		}
		this.categoryIdName1 = categoryIdName1;
	}
	
	public String getCategoryIdName2() {
		categoryIdName2 = this.categoryId2 + DictConstants.SEPARATE_KEY+this.categoryIdName2;
		return categoryIdName2;
	}
	public void setCategoryIdName2(String categoryIdName2) {
		if(StringUtils.isNotEmpty(categoryIdName2)){
			String[] result = categoryIdName2.split(DictConstants.SEPARATE_KEY);
			categoryId2 = Long.parseLong(result[0]);
			categoryName2 = result[1];
		}
		this.categoryIdName2 = categoryIdName2;
	}
	public String getCategoryIdName3() {
		categoryIdName3 = this.categoryId3 + DictConstants.SEPARATE_KEY+this.categoryIdName3;
		return categoryIdName3;
	}
	public void setCategoryIdName3(String categoryIdName3) {
		if(StringUtils.isNotEmpty(categoryIdName3)){
			String[] result = categoryIdName3.split(DictConstants.SEPARATE_KEY);
			categoryId3 = Long.parseLong(result[0]);
			categoryName3 = result[1];
		}
		this.categoryIdName3 = categoryIdName3;
	}
	public String getCategoryIdName4() {
		categoryIdName4 = this.categoryId4 + DictConstants.SEPARATE_KEY+this.categoryIdName4;
		return categoryIdName4;
	}
	public void setCategoryIdName4(String categoryIdName4) {
		if(StringUtils.isNotEmpty(categoryIdName4)){
			String[] result = categoryIdName4.split(DictConstants.SEPARATE_KEY);
			categoryId4 = Long.parseLong(result[0]);
			categoryName4 = result[1];
		}
		this.categoryIdName4 = categoryIdName4;
	}
}
