package com.smrs.security.webapp.action;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.haier.openplatform.webapp.taglib.HopTreeNode;
import com.smrs.security.model.ResourceModel;

/**
 * 展开树形菜单
 * @author WangXuzheng
 *
 */
public class DisplayResourceTreeAction extends BaseSecurityAction{
	private static final long serialVersionUID = -4764880714915143344L;
	private List<HopTreeNode> nodes = new ArrayList<HopTreeNode>();
	private String id = "";
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
		List<ResourceModel> roots = resourceService.getRoot();
		List<ResourceModel> allResources = resourceService.getAll();
		LinkedList<ResourceModel> resourceList = this.listToLinkedList(allResources);
		for(ResourceModel resource : roots){
			HopTreeNode treeNode = buildNode(resource);
			buildTree(resourceList, treeNode);
			nodes.add(treeNode);
		}
		return SUCCESS;
	}
	
	/**
	 * 构建资源树
	 * @param resources 所有的资源列表
	 * @param parentNode 父节点
	 */
	private void buildTree(LinkedList<ResourceModel> resources,HopTreeNode parentNode){
		if(resources.isEmpty()){
			return;
		}
		Collection<HopTreeNode> children = parentNode.getChildren();
		if(children == null){
			children = new ArrayList<HopTreeNode>();
			parentNode.setChildren(children);
		}
		Long parentNodeId = Long.valueOf(parentNode.getId());
		for(ResourceModel resource : resources){
			//设置该资源的直接子元素-剔除自身
			if(resource.getParent().getId().equals(parentNodeId) && !parentNodeId.equals(resource.getId())){
				HopTreeNode treeNode = buildNode(resource);
				parentNode.getChildren().add(treeNode);
			}
		}
		//从列表中删除父节点
		deleteParentResourceFromList(resources,parentNodeId);
		//对子节点遍历
		for(HopTreeNode treeNode : parentNode.getChildren()){
			buildTree(resources,treeNode);
		}
	}
	
	private HopTreeNode buildNode(ResourceModel resource) {
		HopTreeNode treeNode = new HopTreeNode();
		treeNode.setId(resource.getId().toString());
		treeNode.setTitle(resource.getName());
		treeNode.setIsParent(true);
		treeNode.setChecked(false);
		return treeNode;
	}
	
	private void deleteParentResourceFromList(LinkedList<ResourceModel> resources,Long parentId){
		for(ResourceModel resource : resources){
			if(resource.getId().equals(parentId)){
				resources.remove(resource);
				return;
			}
		}
	}
	
	private LinkedList<ResourceModel> listToLinkedList(List<ResourceModel> resources){
		if(resources instanceof LinkedList){
			return (LinkedList<ResourceModel>)resources;
		}
		LinkedList<ResourceModel> result = new LinkedList<ResourceModel>();
		for(ResourceModel resource : resources){
			result.add(resource);
		}
		return result;
	}
}
