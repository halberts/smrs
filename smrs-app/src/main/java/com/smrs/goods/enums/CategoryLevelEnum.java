package com.smrs.goods.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 部门状态枚举列表
 * @author WangXuzheng
 *
 */
public enum CategoryLevelEnum {
	levelOne(1l,"一级"),levelTwo(2l,"二级"),levelThree(3l,"三级"),levelFour(4l,"四级");
	
	
	private Long id;
	private String name;
	private CategoryLevelEnum(Long status, String description) {
		this.id = status;
		this.name = description;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}
	
	
	private static final Map<Long, CategoryLevelEnum> CACHE = new HashMap<Long, CategoryLevelEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(CategoryLevelEnum enu : CategoryLevelEnum.values()){
				put(enu.id, enu);
			}
		}
	};
	public static CategoryLevelEnum toEnum(Integer status){
		return CACHE.get(status);
	}
	
	public static CategoryLevelEnum getMaxLevel(){
		return levelFour;
	}
}
