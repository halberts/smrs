package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.ProfileBeanInfo;
import com.haier.openplatform.console.issue.service.ProfileService;

/**
 * ajax请求，用来获取某个profileBean下的子节点信息
 * @author WangXuzheng
 *
 */
public class GetChildrenProfileBeanAction extends BaseIssueAction {
	private static final long serialVersionUID = -7866393312997251625L;
	private List<ProfileBeanInfo> profileBeanInfoList = new ArrayList<ProfileBeanInfo>(0);
	private ProfileService profileService;
	private Long parentId;
	@Override
	public String execute() throws Exception {
		List<ProfileBeanInfo> list = profileService.getChildProfileBeanInfoList(parentId);
		Map<Long, ProfileBeanInfo> nodeMap = new HashMap<Long, ProfileBeanInfo>();
		List<ProfileBeanInfo> rootElements = new ArrayList<ProfileBeanInfo>();//根节点
		//建立父子关系
		for(ProfileBeanInfo bean : list){
			nodeMap.put(bean.getId(), bean);
			ProfileBeanInfo parent = nodeMap.get(bean.getParent().getId());
			if(parent != null&& !parent.getId().equals(bean.getId())){
				parent.getChildren().add(bean);
			}
			if(bean.getId().equals(parentId)){
				rootElements.add(bean);//根节点列表
			}
		}
		
		for(ProfileBeanInfo bean : rootElements){
			buildList(bean,profileBeanInfoList,1);
		}
		return SUCCESS;
	}
	private void buildList(ProfileBeanInfo treeNode,List<ProfileBeanInfo> nodeList,int depth){
		treeNode.setClassName(new StringBuilder(generateSpace(depth)).append("|-").append(treeNode.getClassName()).toString());
		nodeList.add(treeNode);
		for(ProfileBeanInfo node : treeNode.getChildren()){
			buildList(node,nodeList,depth+1);
		}
	}
	/**
	 * 生成n个空格
	 * @param depth
	 * @return
	 */
	private String generateSpace(int depth){
		int size = (depth-1) *2;
		StringBuilder rs = new StringBuilder(size);
		for(int i = 0; i < size; i++){
//			rs.append("&nbsp;&nbsp");
			rs.append("  ");
		}
		return rs.toString();
	}
	public List<ProfileBeanInfo> getProfileBeanInfoList() {
		return profileBeanInfoList;
	}
	public void setProfileBeanInfoList(List<ProfileBeanInfo> profileBeanInfoList) {
		this.profileBeanInfoList = profileBeanInfoList;
	}
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
