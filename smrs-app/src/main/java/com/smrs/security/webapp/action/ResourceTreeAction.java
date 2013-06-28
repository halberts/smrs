package com.smrs.security.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.haier.openplatform.webapp.taglib.HopTreeNode;
import com.smrs.security.model.ResourceModel;

/**
 * json返回树形资源
 * @author jonathan
 *
 */
public class ResourceTreeAction extends BaseSecurityAction {
	private static final long serialVersionUID = 1845280091508533545L;
	private String id = "";
	private List<HopTreeNode> nodes = new ArrayList<HopTreeNode>();
	
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
		List<ResourceModel> roots = null;
		if(NumberUtils.isNumber(id)&& !"0".equals(id)){
			roots = resourceService.getChilden(Long.valueOf(id));
		}else{
			roots = resourceService.getRoot();
		}
		for(ResourceModel resource : roots){
			HopTreeNode treeNode = new HopTreeNode();
			treeNode.setId(resource.getId().toString());
			treeNode.setTitle(resource.getName());
			treeNode.setIsParent(true);
//			treeNode.setTitle(resource.getName());
//			treeNode.setState(TreeNode.NODE_STATE_CLOSED);
			this.nodes.add(treeNode);
		}
		return SUCCESS;
	}
}
