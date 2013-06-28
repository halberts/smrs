package com.smrs.basicdata.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.Department;


/**
 * @author WangXuzheng
 *
 */
public class DepartmentDAOImpl extends BaseDAOHibernateImpl<Department, Long>implements DepartmentDAO {
	@Override
	public Pager<Department> searchDepartments(Pager pager,Department department) {
		return this.findPage(pager, this.createCriterions(department));
	}
	private Criterion[] createCriterions(Department department){
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(StringUtils.isNotBlank(department.getName())){
			criterions.add(Restrictions.like("name", department.getName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(department.getCode())){
			criterions.add(Restrictions.like("code", department.getCode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(department.getDescription())){
			criterions.add(Restrictions.like("description", department.getDescription(),MatchMode.ANYWHERE));
		}
		return this.list2Array(criterions);
	}
	@Override
	public List<Department> getChildren(Long departmentId) {
		String hql = "from Department where parent.id=:parentId and parent.id != id";
		final Map<String, Long> param = new HashMap<String, Long>();
		param.put("parentId", departmentId);
		return this.findByHQL(hql, param);
	}
	@Override
	public LinkedList<Department> getParentsChain(Long departmentId) {
		LinkedList<Department> departments = new LinkedList<Department>();
		Department resource = get(departmentId);
		if(resource == null){
			return departments;
		}
		Department tmpDepartment = resource;
		while(tmpDepartment.getParent() != null && !tmpDepartment.getParent().getId().equals(tmpDepartment.getId())){
			departments.addFirst(tmpDepartment.getParent());
			tmpDepartment = tmpDepartment.getParent();
			initProxyObject(tmpDepartment);
		}
		departments.addLast(resource);
		return departments;
	}
	@Override
	public List<Department> getRoots() {
		String hql = "from Department where parent.id = id";
		return this.findByHQL(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getUserDepartments(Long userId) {
		Criteria criteria = this.createCriteria();
		criteria.setFetchMode("users", FetchMode.JOIN);
		criteria.createAlias("users", "users");
		criteria.add(Restrictions.eq("users.id", userId));
		return criteria.list();
	}
}
