package com.smrs.basicdata.model;

import com.smrs.model.BaseModel;

public class DictSystemModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dictId;

	private String dictType;
	private String dictDesc;
	private String dictValue;
	private DictSystemModel parent;
	
	
	private int dictOrder;
	private char currentLevel;
	private int maxLevel;
	
	
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDictDesc() {
		return dictDesc;
	}
	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public DictSystemModel getParent() {
		return parent;
	}
	public void setParent(DictSystemModel parent) {
		this.parent = parent;
	}
	public int getDictOrder() {
		return dictOrder;
	}
	public void setDictOrder(int dictOrder) {
		this.dictOrder = dictOrder;
	}
	public char getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(char currentLevel) {
		this.currentLevel = currentLevel;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
}
