package com.smrs.basicdata.service;

import java.util.LinkedList;
import java.util.List;

import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.Department;


/**
 * @author WangXuzheng
 *
 */
public interface DepartmentService {
	/**
	 * 创建部门
	 * @param department
	 */
	public ExecuteResult<Department> createDepartment(Department department);
	
	/**
	 * 更新部门信息
	 * @param department
	 */
	public ExecuteResult<Department> updateDepartment(Department department);
	
	/**
	 * 删除部门
	 * @param departmentId
	 * @return
	 */
	public ExecuteResult<Department> deleteDepartment(Long departmentId);
	
	/**
	 * 通过id获取部门信息
	 * @param departmentId
	 * @return
	 */
	public Department getDepartmentById(Long departmentId);
	
	/**
	 * 获取部门的子部门
	 * @param departmentId
	 * @return
	 */
	public List<Department> getChilden(Long departmentId);
	
	/**
	 * 获取某个节点的所有直接父节点列表
	 * @param departmentId
	 * @return
	 */
	public LinkedList<Department> getParentsChain(Long departmentId);
	
	/**
	 * 获取系统根部门
	 * @return
	 */
	public List<Department> getRoot();
	
	/**
	 * 获取系统中所有的部门列表
	 * @return
	 */
	public List<Department> getAll();
	
	/**
	 * 分页查询部门信息
	 * @param pager
	 * @param department
	 * @return
	 */
	public Pager<Department> searchDepartments(Pager<?> pager,Department department);
	/**
	 * 获取用户所在的部门列表
	 * @param userId
	 * @return
	 */
	public List<Department> getUserDepartments(Long userId);
}
