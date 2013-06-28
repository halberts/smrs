package com.smrs.security.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.security.dao.GroupDAO;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.GroupUser;
import com.smrs.security.model.RoleModel;
import com.smrs.security.model.UserModel;
import com.smrs.security.vo.GroupSearchModel;
import com.smrs.security.vo.RoleSearchModel;
import com.smrs.security.vo.UserInGroupSearchModel;

/** 
 * @author WangJian
 *
 */
public class GroupDAOImpl extends BaseDAOHibernateImpl<GroupModel,Long>implements GroupDAO{
    
	@Override
	public Pager<GroupModel> getAllGroupsByPager(final GroupSearchModel model) { 
		Pager<GroupModel> p = this.findPage(model.getPager(), this.createCriterions(model));
		return p;
	}  
	
	
	public Pager<GroupModel> getGroupsByNamePager(final GroupSearchModel model) {  
		//Map<String, Long> parameters = new HashMap<String, Long>();
		//parameters.put("userId", userId);
		String groupName = model.getGroup().getName();
		String hsql="";
		if(StringUtils.isNotEmpty(groupName)){
		   hsql = " from GroupModel where name like '%"+model.getGroup().getName()+"%'"; 
		}else{
			hsql = " from GroupModel ";
		}
		Pager<GroupModel> result = Pager.cloneFromPager(model.getPager());
		Long totalCount=this.countHQLUniqueResule(hsql, new Object[]{});
		result.setTotalRecords(totalCount);
		//this.getHibernateTemplate().find(hsql);
		Query query = this.getSession().createQuery(hsql);
		this.setPageParameterToQuery(query, model.getPager());
		result.setRecords(query.list());
		return result;
		}
		
		
		
	
	
	@Override
	public Pager<GroupModel> getAdminGroupsByPager(final GroupSearchModel model, Long userId) {  
		Map<String, Long> parameters = new HashMap<String, Long>();
		parameters.put("userId", userId);
		
		
		return this.findPageByNamedQuery("group.AdminGroupsByPager",model.getPager(), parameters);
	}  
	
	private Criterion[] createCriterions(GroupSearchModel model){
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(StringUtils.isNotBlank(model.getGroup().getDescription())){
			criterions.add(Restrictions.like("description", model.getGroup().getDescription()));
		}
		if(StringUtils.isNotBlank(model.getGroup().getName())){
			criterions.add(Restrictions.like("name", model.getGroup().getName(),MatchMode.ANYWHERE)); 
		}  
		
		Criterion[] result = new Criterion[criterions.size()];
		for(int i = 0; i < criterions.size(); i++){
			result[i] = criterions.get(i);
		}
		return result;
	}  

	@Override
	public UserModel getUserById(Long userid) {
		String hql = "from User where id=:userId";
		final Map<String, Long> param = new HashMap<String, Long>();
		param.put("userId", userid);
		return this.findUniqueByHQL(hql, param); 
	}
	
	@Override
	public List<UserModel> getUsersByGroupId(Long groupId){
		Map<String, String> parameters = new HashMap<String, String>();
		String sql = " select * from user_info ui,(select * from user_group ug where ug.group_id = ?) gui where ui.id = gui.user_id";
		return this.findBySQL(sql, parameters);
	}
	
	@Override
	public List<RoleModel> getRolesByGroupId(Long groupId){    
		Map<String, Long> values = new HashMap<String, Long>();
		values.put("groupid", groupId); 
		List<RoleModel> list = this.findByNamedQuery("group.getRolesByGroupId", values);  
		return list;
	}
	
	@Override
	public Pager<GroupUser> getUsersByGroupId(UserInGroupSearchModel model, Long groupId) {
		Map<String, String> parameters = new HashMap<String, String>();
		/*
		parameters.put("groupid", String.valueOf(groupId));
		if(model.getGroupUser().getName() != null){
			parameters.put("userName", model.getGroupUser().getName());
		}else{
			parameters.put("userName", "");
		}
		
		if(model.getGroupUser().getNickName() != null){
			parameters.put("nickName", model.getGroupUser().getNickName());
		}else{ 
			parameters.put("nickName", "");	
		}
		
		if(model.getGroupUser().getEmail() != null){ 
			parameters.put("email", model.getGroupUser().getEmail());
		}else{  
			parameters.put("email", "");
		}
		*/
		String hql="select user from UserModel as user join user.groups g where  g.id=" +groupId;
		Long countTotal = this.countHQLResult(hql, parameters);
		Query query = this.createHQLQuery(hql, parameters);
		this.setPageParameterToQuery(query, model.getPager());
		Pager<GroupUser> result = Pager.cloneFromPager(model.getPager());
		result.setTotalRecords(countTotal);
		result.setRecords(query.list());
		return result;
	} 
	
	@Override
	public Pager<GroupUser> getAdminByGroupId(UserInGroupSearchModel model, Long groupId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("groupid", groupId);
		if(model.getGroupUser().getName() != null){
			parameters.put("userName", model.getGroupUser().getName());
		}else{
			parameters.put("userName", "");
		}
		if(model.getGroupUser().getNickName() != null){
			parameters.put("nickName", model.getGroupUser().getNickName());
		}else{ 
			parameters.put("nickName", "");	
		}
		if(model.getGroupUser().getEmail() != null){ 
			parameters.put("email", model.getGroupUser().getEmail());
		}else{  
			parameters.put("email", "");
		}
		return this.findPageByNamedQuery("group.queryGroupAdminByGroupId",model.getPager(), parameters);
	}  
	
	@Override
	public Pager<RoleModel> getRolesByGroupId(RoleSearchModel model, Long groupId) { 
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("groupid", String.valueOf(groupId));
		if(model.getRole().getName() != null){
			parameters.put("roleName", model.getRole().getName());
		}else{
			parameters.put("roleName", "");
		}
		if(model.getRole().getDescription() != null){
			parameters.put("roleDescription", model.getRole().getDescription());
		}else{ 
			parameters.put("roleDescription", "");
		} 
		return this.findPageByNamedQuery("group.queryRolesByGroupId",model.getPager(), parameters);
	} 
	
	@Override
	public void deleteUserFromGroup(Long groupId, String userId){
		String sql = "delete from user_group ug where ug.group_id = ? and ug.user_id = ?";
		this.batchExecuteSQL(sql, groupId,userId);
	}

	@Override
	public void deleteUserFromAllGroup(Long userId){
		String sql = "delete from admin_user_group_rel  where  user_id = ?";
		this.batchExecuteSQL(sql,userId);
	}
	
	@Override
	public void deleteRoleFromGroup(Long groupId, String roleId){ 
		String sql = "delete from role_group ug where ug.group_id = ? and ug.role_id = ?";
		this.batchExecuteSQL(sql, groupId,roleId);
	}

	@Override
	public void addUserToGroup(Long groupId, String userId) {
		String sql = "insert into admin_user_group_rel (user_id,group_id) values (?,?)";
		this.batchExecuteSQL(sql,userId, groupId); 
	}
	
	@Override
	public void addRoleToGroup(Long groupId, String roleId) {
		String sql = "insert into role_group (role_id,group_id) values (?,?)";
		this.batchExecuteSQL(sql,roleId,groupId); 
	}

	@Override
	public void addAdminToGroup(Long groupId, String userId) {
		if(this.searchUserFromGroup(groupId, userId)){ 
			String sql = "update user_group set is_admin = 1 where user_id = ? and group_id = ? ";
			this.batchExecuteSQL(sql,userId,groupId);  
		} else{
			String sql = "insert into user_group (user_id,group_id,is_admin) values (?,?,1)";
			this.batchExecuteSQL(sql,userId,groupId);  
		}
	}
	
	@Override
	public void deleteAdminFromGroup(Long groupId, String userId) {
		String sql = "update user_group set is_admin = 0 where group_id = ? and user_id = ?";
		this.batchExecuteSQL(sql,groupId,userId);  
	}

	@Override
	public boolean searchUserFromGroup(Long groupId, String userId) {
		String sql = "select ug.user_id,ug.group_id from admin_user_group_rel ug where user_id = ? and group_id = ?";
		List<Object[]> userGroupList = this.findBySQL(sql, userId, groupId);
		return userGroupList.size() > 0;
	}
	
	@Override
	public boolean searchRoleFromGroup(Long groupId, String roleId) {
		String sql = "select rg.role_id,rg.group_id from role_group rg where role_id = ? and group_id = ?";
		List<Object[]> roleGroupList = this.findBySQL(sql, roleId, groupId);
		return roleGroupList.size() > 0;
	} 

	@Override
	public boolean isExistUser(Long id) {
		String sql = "select ug.user_id,ug.group_id from user_group ug where group_id = ?";
		List<Object[]> userList = this.findBySQL(sql, id);
		return userList.size() > 0;
	}

	@Override
	public boolean isExistRole(Long id) {
		String sql = "select ug.role_id,ug.group_id from role_group ug where group_id = ?";
		List<Object[]> roleList = this.findBySQL(sql, id);
		return roleList.size() > 0;
	}

	@Override
	public boolean isExistGroup(String groupName) {
		Map<String, String> values = new HashMap<String, String>();
		values.put("groupName", groupName);
		List<GroupModel> list = this.findByNamedQuery("group.getGroupByName", values);
		return list.size() > 0;
	}

	@Override
	public List<RoleModel> getRolesByUserId(Long userId) {
		Map<String, Long> values = new HashMap<String, Long>();
		values.put("userId", userId); 
		return this.findByNamedQuery("group.getRolesByUserId", values);    
	} 
}
