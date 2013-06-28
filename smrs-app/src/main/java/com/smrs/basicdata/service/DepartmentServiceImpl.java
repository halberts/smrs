package com.smrs.basicdata.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.security.LoginContext;
import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.dao.DepartmentDAO;
import com.smrs.basicdata.enums.DepartmentStatusEnum;
import com.smrs.basicdata.model.Department;


/**
 * @author WangXuzheng
 *
 */
public class DepartmentServiceImpl implements DepartmentService {
	private DepartmentDAO departmentDAO;
	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}
	@Override
	public Pager<Department> searchDepartments(Pager<?> pager,Department department) {
		return departmentDAO.searchDepartments(pager, department);
	}
	@Override
	public ExecuteResult<Department> createDepartment(Department department) {
		ExecuteResult<Department> executeResult = new ExecuteResult<Department>();
		if(isCodeExisted(department, executeResult) || isNameExisted(department, executeResult)){
			return executeResult;
		}
		LoginContext context = LoginContextHolder.get();
		department.setGmtCreate(new Date());
		department.setGmtModified(new Date());
		department.setStatus(DepartmentStatusEnum.ENABLED.getStatus());//默认为活动状态
		department.setCreateBy(context.getUserName());
		department.setLastModifiedBy(context.getUserName());
		resetParentDepartment(department);
		departmentDAO.save(department);
		executeResult.setResult(department);
		return executeResult;
	}
	private void resetParentDepartment(Department department) {
		//设置parent
		if(department.getParent() != null && department.getParent().getId()!=null && department.getParent().getId() > 0){
			department.setParent(departmentDAO.load(department.getParent().getId()));
		}else{
			department.setParent(department);
		}
	}
	@Override
	public ExecuteResult<Department> updateDepartment(Department department) {
		ExecuteResult<Department> executeResult = new ExecuteResult<Department>();
		Department dbDepartment = departmentDAO.get(department.getId());
		if(dbDepartment == null){
			executeResult.addErrorMessage("该部门不存在或已经被删除。");
			return executeResult;
		}
		// code重复检查
		if(!StringUtils.trim(department.getCode()).equals(dbDepartment.getCode())){
			if(isCodeExisted(department, executeResult)){
				return executeResult;
			}
		}
		// 名称重名检查
		if(!StringUtils.trim(department.getName()).equals(dbDepartment.getName())){
			if(isNameExisted(department, executeResult)){
				return executeResult;
			}
		}
		dbDepartment.setCode(StringUtils.trim(department.getCode()));
		dbDepartment.setName(StringUtils.trim(department.getName()));
		dbDepartment.setDescription(department.getDescription());
		dbDepartment.setStatus(department.getStatus());
		LoginContext context = LoginContextHolder.get();
		dbDepartment.setLastModifiedBy(context.getUserName());
		dbDepartment.setGmtModified(new Date());
		if(department.getParent() != null && department.getParent().getId() != null){
			if(!dbDepartment.getParent().getId().equals(department.getParent().getId())){//重新修改了parent
				dbDepartment.setParent(departmentDAO.load(department.getParent().getId()));
			}
		}else{
			dbDepartment.setParent(dbDepartment);
		}
		return executeResult;
	}
	private boolean isNameExisted(Department department, ExecuteResult<Department> executeResult) {
		Department existedNameDepartment = departmentDAO.findUniqueBy("name", StringUtils.trim(department.getName()));
		if(existedNameDepartment != null){
			executeResult.addErrorMessage("已经存在名称为["+department.getName()+"]的部门。");
			return true;
		}
		return false;
	}
	private boolean isCodeExisted(Department department, ExecuteResult<Department> executeResult) {
		Department existedCodeDepartment = departmentDAO.findUniqueBy("code", StringUtils.trim(department.getCode()));
		if(existedCodeDepartment != null){
			executeResult.addErrorMessage("已经存在code为["+department.getCode()+"]的部门。");
			return true;
		}
		return false;
	}
	@Override
	public ExecuteResult<Department> deleteDepartment(Long departmentId) {
		ExecuteResult<Department> executeResult = new ExecuteResult<Department>();
		//是否关联用户
		Department department = departmentDAO.get(departmentId);
		if(department == null){
			return executeResult;
		}
		if(department.getUsers() != null && !department.getUsers().isEmpty()){
			executeResult.addErrorMessage("该部门下还关联了"+department.getUsers().size()+"个员工，不能删除。");
			return executeResult;
		}
		//是否关联子节点
		List<Department> children = getChilden(departmentId);
		if(!children.isEmpty()){
			executeResult.addErrorMessage("该部门下还设置了"+children.size()+"个下属部门，不能删除。");
			return executeResult;
		}
		departmentDAO.delete(department);
		return executeResult;
	}
	
	@Override
	public List<Department> getUserDepartments(Long userId) {
		return departmentDAO.getUserDepartments(userId);
	}
	@Override
	public Department getDepartmentById(Long departmentId) {
		return departmentDAO.get(departmentId);
	}
	@Override
	public List<Department> getChilden(Long departmentId) {
		return departmentDAO.getChildren(departmentId);
	}
	@Override
	public LinkedList<Department> getParentsChain(Long departmentId) {
		return departmentDAO.getParentsChain(departmentId);
	}
	@Override
	public List<Department> getRoot() {
		return departmentDAO.getRoots();
	}
	@Override
	public List<Department> getAll() {
		return departmentDAO.getAll();
	}
}
