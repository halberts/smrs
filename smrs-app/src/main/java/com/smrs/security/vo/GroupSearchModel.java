package com.smrs.security.vo;
 

import com.jof.framework.util.SearchModel;
import com.smrs.security.model.GroupModel;

/**
 * 组信息查询参数封装对象
 * @author WangJian
 *
 */
public class GroupSearchModel extends SearchModel<GroupModel>{ 
	private static final long serialVersionUID = 1438306561743499006L;
	private GroupModel group = new GroupModel();
	public GroupModel getGroup() {
		return group;
	}
	public void setGroup(GroupModel group) {
		this.group = group;
	} 
}
