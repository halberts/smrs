package com.smrs.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.smrs.security.dao.ResourceDAO;
import com.smrs.security.dao.RoleDAO;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;
import com.smrs.security.service.RoleService;
import com.smrs.security.vo.RoleSearchModel;

/**
 * @author jonathan
 *
 */
public class RoleServiceImpl implements RoleService {
	private RoleDAO roleDAO;
	private ResourceDAO resourceDAO;

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	@Override
	public ExecuteResult<RoleModel> createRole(RoleModel role) {
		ExecuteResult<RoleModel> executeResult = new ExecuteResult<RoleModel>();
		RoleModel dbRole = roleDAO.getRoleByName(StringUtils.trim(role.getName()));
		if(dbRole != null){
			executeResult.addErrorMessage("角色["+role.getName()+"]已经存在.");
			return executeResult;
		}
		resetResource(role);
		role.setName(StringUtils.trim(role.getName()));
		//role.setGmtCreate(new Date());
		//role.setGmtModified(new Date());
		role.setCreator(LoginContextHolder.get().getUserName());
		role.setModifiedBy(LoginContextHolder.get().getUserName());
		roleDAO.save(role);
		executeResult.setResult(role);
		return executeResult;
	}

	private void resetResource(RoleModel role) {
		if(role.getResources() == null || role.getResources().isEmpty()){
			role.setResources(null);
		}else{
			final Set<ResourceModel> permissions = role.getResources();
			role.setResources(new HashSet<ResourceModel>());
			for(ResourceModel res : permissions){
				ResourceModel p = resourceDAO.load(res.getId());
				role.getResources().add(p);
			}
		}
	}

	@Override
	public ExecuteResult<RoleModel> updateRole(RoleModel role) {
		ExecuteResult<RoleModel> executeResult = new ExecuteResult<RoleModel>();
		RoleModel dbRole = roleDAO.get(role.getId());
		if(dbRole == null){
			executeResult.addErrorMessage("不存在的角色信息.");
			return executeResult;
		}
		if(!dbRole.getName().equals(role.getName())){
			if(roleDAO.getRoleByName(role.getName()) != null){
				executeResult.addErrorMessage("角色名["+role.getName()+"]已经存在.");
				return executeResult;
			}
		}
		resetResource(role);
		//dbRole.setLastModifiedBy(LoginContextHolder.get().getUserName());
		//dbRole.setGmtModified(new Date());
		dbRole.setDescription(role.getDescription());
		dbRole.setName(role.getName());
		dbRole.setResources(role.getResources());
		roleDAO.update(dbRole);
		return executeResult;
	}

	@Override
	public ExecuteResult<RoleModel> deleteRole(Long roleId) {
		ExecuteResult<RoleModel> executeResult = new ExecuteResult<RoleModel>();
		RoleModel role = roleDAO.get(roleId);
		if(role == null){
			executeResult.addErrorMessage("该角色信息已经删除.");
			return executeResult;
		}
		if(role.getResources() != null && !role.getResources().isEmpty()){
			executeResult.addErrorMessage("角色["+role.getName()+"]下有"+role.getResources().size()+"个资源，不能删除.");
			return executeResult;
		}
		roleDAO.delete(role);
		return executeResult;
	}

	@Override
	public RoleModel getRoleById(Long roleId) {
		return roleDAO.get(roleId);
	}

	@Override
	public Pager<RoleModel> searchRoles(RoleSearchModel searchModel) {
		return roleDAO.searchRoles(searchModel);
	}

	@Override
	public List<RoleModel> getRoles() {
		return roleDAO.getAll();
	}

	@Override
	public List<RoleModel> getUserRoles(Long userId) {
		return roleDAO.getUserRoles(userId);
	}
	
}
