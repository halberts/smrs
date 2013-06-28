package com.smrs.basicdata.model;

import java.util.Date;

import com.smrs.model.BaseModel;

public class StoreModel extends BaseModel<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String storeCode;
	private String shortName;
	private Integer storeType;
	
	private String province;
	private String city;
	private String zone;
	private String address;
	private String manager;
	private String tel;
	private String areaId;
	private Integer channelId;
	private double rebateRate;
	private Long parentId;

	private Date openDate;
	private Date closeDate;
	private Character storeLevel;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Character getStoreLevel() {
		return storeLevel;
	}
	public void setStoreLevel(Character storeLevel) {
		this.storeLevel = storeLevel;
	}
	public Integer getStoreType() {
		return storeType;
	}
	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public double getRebateRate() {
		return rebateRate;
	}
	public void setRebateRate(double rebateRate) {
		this.rebateRate = rebateRate;
	}
	
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	
} 
