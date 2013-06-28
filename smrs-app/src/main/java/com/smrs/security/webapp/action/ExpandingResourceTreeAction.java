package com.smrs.security.webapp.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.webapp.taglib.HopTreeNode;
import com.smrs.security.model.ResourceModel;

/**
 * 展开指定节点的资源树
 * @author WangXuzheng
 *
 */
public class ExpandingResourceTreeAction extends ResourceTreeAction {
	private static final long serialVersionUID = -7004108799851790412L;
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
		
		List<ResourceModel> roots = resourceService.getRoot();
		final boolean isRoot = isRoot(roots);//当前要展开的是否为根节点
		LinkedList<ResourceModel> resources = null;
		if(!isRoot){
			resources = resourceService.getParentsChain(Long.valueOf(expandId));
		}
		for(ResourceModel resource : roots){
			HopTreeNode treeNode = new HopTreeNode();
			treeNode.setId(resource.getId().toString());
			treeNode.setTitle(resource.getName());
			treeNode.setIsParent(true);
//			treeNode.setState(TreeNode.NODE_STATE_CLOSED);
			if(!isRoot){
				Long parentId = resources.getFirst().getId();
				if(parentId.equals(resource.getId())){
					buildChild(resources,treeNode);
				}
			}
			this.getNodes().add(treeNode);
		}
		return SUCCESS;
	}
	
	private void buildChild(LinkedList<ResourceModel> parentChain,HopTreeNode treeNode){
		if(parentChain.size() <= 1){
			return;
		}
		Long parentId = parentChain.removeFirst().getId();
//		treeNode.setState(TreeNode.NODE_STATE_OPEN);
		List<ResourceModel> resources = resourceService.getChilden(parentId);
		List<HopTreeNode> childNodes = new ArrayList<HopTreeNode>();
		Long current = parentChain.getFirst().getId();
		for(ResourceModel resource : resources){
			HopTreeNode node = new HopTreeNode();
			node.setId(resource.getId().toString());
			node.setTitle(resource.getName());
			node.setIsParent(true);
//			node.setTitle(resource.getName());
//			node.setState(TreeNode.NODE_STATE_CLOSED);
			if(current.equals(resource.getId())){
				buildChild(parentChain,node);
			}
			childNodes.add(node);
		}
		treeNode.setChildren(childNodes);
	}
	private boolean isRoot(List<ResourceModel> roots){
		Long tmp = Long.valueOf(expandId);
		for(ResourceModel resource : roots){
			if(resource.getId().equals(tmp)){
				return true;
			}
		}
		return false;
	}
}
