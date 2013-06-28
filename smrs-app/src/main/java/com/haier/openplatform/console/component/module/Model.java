package com.haier.openplatform.console.component.module;
/**
 * 组件对象
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Model{
	private Long id;
	private String field;
	private String components;
	private String value;
	private Date realizeTime;
	private String differencesDescription;
	private String template;
	private String system;
	private int planPromotion;
	private int realPromotion;
	private int differences;
	private List<RealModel> listModel = new ArrayList<RealModel>();
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public int getPlanPromotion() {
		return planPromotion;
	}
	public void setPlanPromotion(int planPromotion) {
		this.planPromotion = planPromotion;
	}
	public int getRealPromotion() {
		return realPromotion;
	}
	public void setRealPromotion(int realPromotion) {
		this.realPromotion = realPromotion;
	}
	public int getDifferences() {
		return differences;
	}
	public void setDifferences(int differences) {
		this.differences = differences;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	} 
	public List<RealModel> getListModel() {
		return listModel;
	}
	public void setListModel(List<RealModel> listModel) {
		this.listModel = listModel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getComponents() {
		return components;
	}
	public void setComponents(String components) {
		this.components = components;
	}
	public Date getRealizeTime() {
		return realizeTime;
	}
	public void setRealizeTime(Date realizeTime) {
		this.realizeTime = realizeTime;
	}
	public String getDifferencesDescription() {
		return differencesDescription;
	}
	public void setDifferencesDescription(String differencesDescription) {
		this.differencesDescription = differencesDescription;
	}
}
