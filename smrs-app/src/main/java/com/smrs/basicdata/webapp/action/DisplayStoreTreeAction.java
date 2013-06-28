package com.smrs.basicdata.webapp.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.haier.openplatform.webapp.taglib.HopTreeNode;
import com.smrs.basicdata.model.Department;
import com.smrs.basicdata.model.StoreModel;

/**
 * 展开树形菜单
 * @author WangXuzheng
 *
 */
public class DisplayStoreTreeAction extends StoreAction{
	private static final long serialVersionUID = -1033577806063303484L;
	private List<HopTreeNode> nodes = new ArrayList<HopTreeNode>();
	private String id = "";
	private String titleName="部门";
	
	public String getTitleName() {
		return titleName;
	}
	public List<HopTreeNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<HopTreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String execute() throws Exception {
		if(!"0".equals(this.id)){//选中树节点时不返回任何数据
			return SUCCESS;
		}
		//构建树结构
		//List<Department> roots = departmentService.getRoot();
		List<StoreModel> roots = storeService.getMajorStoreList();
		
		//List<Department> allDepartments = departmentService.getAll();
		List<StoreModel> allStoreModels = storeService.getAllActive();
		
		//LinkedList<StoreModel> departmentList = this.listToLinkedList(allDepartments);
		LinkedList<StoreModel> departmentList = this.listToLinkedList(allStoreModels);
		for(StoreModel department : roots){
			HopTreeNode treeNode = buildNode(department);
			buildTree(departmentList, treeNode);
			nodes.add(treeNode);
		}
		return SUCCESS;
	}
	
	/**
	 * 构建资源树
	 * @param departments 所有的资源列表
	 * @param parentNode 父节点
	 */
	private void buildTree(LinkedList<StoreModel> departments,HopTreeNode parentNode){
		if(departments.isEmpty()){
			return;
		}
		Collection<HopTreeNode> children = parentNode.getChildren();
		if(children == null){
			children = new ArrayList<HopTreeNode>();
			parentNode.setChildren(children);
		}
		Long parentNodeId = Long.valueOf(parentNode.getId());
		for(StoreModel department : departments){
			//设置该资源的直接子元素-剔除自身
			if(department.getParentId()!=null && department.getParentId().equals(parentNodeId) && !parentNodeId.equals(department.getId())){
				HopTreeNode treeNode = buildNode(department);
				parentNode.getChildren().add(treeNode);
			}
		}
		//从列表中删除父节点
		deleteParentDepartmentFromList(departments,parentNodeId);
		//对子节点遍历
		for(HopTreeNode treeNode : parentNode.getChildren()){
			buildTree(departments,treeNode);
		}
	}
	
	/**
	 * 构建资源树
	 * @param departments 所有的资源列表
	 * @param parentNode 父节点
	 */
	/*
	private void buildTree(LinkedList<Department> departments,HopTreeNode parentNode){
		if(departments.isEmpty()){
			return;
		}
		Collection<HopTreeNode> children = parentNode.getChildren();
		if(children == null){
			children = new ArrayList<HopTreeNode>();
			parentNode.setChildren(children);
		}
		Long parentNodeId = Long.valueOf(parentNode.getId());
		for(Department department : departments){
			//设置该资源的直接子元素-剔除自身
			if(department.getParent().getId().equals(parentNodeId) && !parentNodeId.equals(department.getId())){
				HopTreeNode treeNode = buildNode(department);
				parentNode.getChildren().add(treeNode);
			}
		}
		//从列表中删除父节点
		deleteParentDepartmentFromList(departments,parentNodeId);
		//对子节点遍历
		for(HopTreeNode treeNode : parentNode.getChildren()){
			buildTree(departments,treeNode);
		}
	}
	*/
	private HopTreeNode buildNode(StoreModel department) {
		HopTreeNode treeNode = new HopTreeNode();
		treeNode.setId(department.getId().toString());
		treeNode.setTitle(department.getName());
		treeNode.setIsParent(true);
//		treeNode.setState(TreeNode.NODE_STATE_OPEN);
		return treeNode;
	}
	
	private HopTreeNode buildNode(Department department) {
		HopTreeNode treeNode = new HopTreeNode();
		treeNode.setId(department.getId().toString());
		treeNode.setTitle(department.getName());
		treeNode.setIsParent(true);
//		treeNode.setState(TreeNode.NODE_STATE_OPEN);
		return treeNode;
	}
	
	private void deleteParentDepartmentFromList(LinkedList<StoreModel> departments,Long parentId){
		for(StoreModel department : departments){
			if(department.getId().equals(parentId)){
				departments.remove(department);
				return;
			}
		}
	}
	
	private LinkedList<StoreModel> listToLinkedList(List<StoreModel> departments){
		if(departments instanceof LinkedList){
			return (LinkedList<StoreModel>)departments;
		}
		LinkedList<StoreModel> result = new LinkedList<StoreModel>();
		for(StoreModel department : departments){
			result.add(department);
		}
		return result;
	}
}