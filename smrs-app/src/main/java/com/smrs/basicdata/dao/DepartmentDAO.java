package com.smrs.basicdata.dao;

import java.util.LinkedList;
import java.util.List;

import com.jof.framework.dao.BaseDAO;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.Department;


/**
 * 部门 表操作
 * @author WangXuzheng
 *
 */
public interface DepartmentDAO extends BaseDAO<Department, Long>{
	/**
	 * 分页查询部门信息
	 * @param department
	 * @return
	 */
	public Pager<Department> searchDepartments(Pager<?> pager,Department department);
	/**
	 * 查询子部门 
	 * @param departmentId
	 * @return
	 */
	public List<Department> getChildren(Long departmentId);
	
	/**
	 * 获取某个节点的所有直接父节点列表
	 * @param departmentId
	 * @return
	 */
	public LinkedList<Department> getParentsChain(Long departmentId);
	
	/**
	 * 获取根节点部门 信息列表
	 * @return
	 */
	public List<Department> getRoots();
	
	/**
	 * 获取用户所在的部门信息
	 * @param userId
	 * @return
	 */
	public List<Department> getUserDepartments(Long userId);
}
