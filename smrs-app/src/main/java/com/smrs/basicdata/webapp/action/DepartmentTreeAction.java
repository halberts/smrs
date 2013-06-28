package com.smrs.basicdata.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.haier.openplatform.webapp.taglib.HopTreeNode;
import com.smrs.basicdata.model.Department;

/**
 * 显示部门树
 * @author WangXuzheng
 *
 */
public class DepartmentTreeAction extends BaseBasicDataAction {
	private static final long serialVersionUID = 8900303964679796305L;
	private String id = "";
	private List<HopTreeNode> nodes = new ArrayList<HopTreeNode>();
	private String titleName="部门";
	
	public String getTitleName() {
		return titleName;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<HopTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<HopTreeNode> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String execute() throws Exception {
		List<Department> roots = null;
		if(NumberUtils.isNumber(id)&& !"0".equals(id)){
			roots = departmentService.getChilden(Long.valueOf(id));
		}else{
			roots = departmentService.getRoot();
		}
		for(Department repartment : roots){
			HopTreeNode treeNode = new HopTreeNode();
			treeNode.setId(repartment.getId().toString());
			treeNode.setTitle(repartment.getName());
			treeNode.setIsParent(true);
			this.nodes.add(treeNode);
		}
		return SUCCESS;
	}
}
