package com.smrs.basicdata.webapp.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.haier.openplatform.webapp.taglib.HopTreeNode;
import com.smrs.basicdata.model.Department;

/**
 * @author WangXuzheng
 *
 */
public class ExpandingDepartmentTreeAction extends DepartmentTreeAction {
	private static final long serialVersionUID = 8663128214001543452L;
	private String expandId;

	public String getExpandId() {
		return expandId;
	}

	public void setExpandId(String expandId) {
		this.expandId = expandId;
	}

	@Override
	public String execute() throws Exception {
		if(!StringUtils.isEmpty(this.getId())) {
			return super.execute();
		}
		
		List<Department> roots = departmentService.getRoot();
		final boolean isRoot = isRoot(roots);//当前要展开的是否为根节点
		LinkedList<Department> departments = null;
		if(!isRoot){
			departments = departmentService.getParentsChain(Long.valueOf(expandId));
		}
		for(Department department : roots){
			HopTreeNode node = new HopTreeNode();
			node.setId(department.getId().toString());
			node.setTitle(department.getName());
			node.setIsParent(true);
			
			if(!isRoot){
				Long parentId = departments.getFirst().getId();
				if(parentId.equals(department.getId())){
					buildChild(departments,node);
				}
			}
			
			
			this.getNodes().add(node);
		}
		return SUCCESS;
	}
	
	
	private void buildChild(LinkedList<Department> parentChain,HopTreeNode treeNode){
		if(parentChain.size() <= 1){
			return;
		}
		Long parentId = parentChain.removeFirst().getId();
		treeNode.setIsParent(true);
		
		List<Department> departments = departmentService.getChilden(parentId);
		List<HopTreeNode> childNodes = new ArrayList<HopTreeNode>();
		Long current = parentChain.getFirst().getId();
		for(Department department : departments){
			HopTreeNode node = new HopTreeNode();
			node.setId(department.getId().toString());
			node.setTitle(department.getName());
			node.setIsParent(true);
			if(current.equals(department.getId())){
				buildChild(parentChain,node);
			}
			childNodes.add(node);
		}
		treeNode.setChildren(childNodes);
	}
	

	private boolean isRoot(List<Department> roots){
		Long tmp = Long.valueOf(expandId);
		for(Department department : roots){
			if(department.getId().equals(tmp)){
				return true;
			}
		}
		return false;
	}
}
