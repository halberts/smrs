package com.smrs.security.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.security.dao.RoleDAO;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.RoleSearchModel;

/**
 * @author WangXuzheng
 *
 */
public class RoleDAOImpl extends BaseDAOHibernateImpl<RoleModel,Long> implements RoleDAO {

	@Override
	public RoleModel getRoleByName(String name) {
		return this.findUniqueBy("name", name);
	}

	@Override
	public Pager<RoleModel> searchRoles(final RoleSearchModel searchModel) {
		Criterion[] criterions = createCriterions(searchModel);
		return this.findPage(searchModel.getPager(), criterions);
	}
	
	private Criterion[] createCriterions(RoleSearchModel model){
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(StringUtils.isNotBlank(model.getRole().getName())){
			criterions.add(Restrictions.like("name", model.getRole().getName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getRole().getDescription())){
			criterions.add(Restrictions.like("description", model.getRole().getDescription(),MatchMode.ANYWHERE));
		}
		Criterion[] result = new Criterion[criterions.size()];
		for(int i = 0; i < criterions.size(); i++){
			result[i] = criterions.get(i);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleModel> getUserRoles(final Long userId) {
		Criteria criteria = this.createCriteria();
		criteria.setFetchMode("users", FetchMode.JOIN);
		criteria.createAlias("users", "users");
		criteria.add(Restrictions.eq("users.id", userId));
		return criteria.list();
	}
}
