package com.smrs.security.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.security.dao.UserDAO;
import com.smrs.security.model.UserModel;
import com.smrs.security.vo.UserSearchModel;

/**
 * 用户DAO的默认实现
 * @author WangXuzheng
 *
 */
public class UserDAOImpl extends BaseDAOHibernateImpl<UserModel,Long> implements UserDAO {
	public UserModel getUserByName(String name) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		UserModel user= this.findUniqueBy("name", name);
		return user;
	}

	@Override
	public UserModel get(Long id) {
		UserModel user = (UserModel)super.get(id);
		if(user != null){
			Hibernate.initialize(user.getStores());
		}
		return user;
	}

	@Override
	public Pager<UserModel> searchUser(final UserSearchModel model) {
		Pager<UserModel> users = this.findPage(model.getPager(), this.createCriterions(model));
		//Pager<UserModel> users=null;
		for(UserModel user : users.getRecords()){
			Hibernate.initialize(user.getStores());
			Hibernate.initialize(user.getGroups());
		}
		
//		StringBuffer stringBuffer = new StringBuffer();
//		for(User user : users.getRecords()){
//			stringBuffer.append(user.getId());
//			stringBuffer.append(",");
//		}
//		if(stringBuffer.length() != 0){
//			stringBuffer.deleteCharAt(stringBuffer.length()-1);
//			Map<String, String> parameters = new HashMap<String, String>();
//			List<Object[]> result = this.findByNamedQuery("department.getUserDepartments", parameters);
//			for(Object[] obj : result){
//				for(User user : users.getRecords()){
//					if(user.getId().equals(obj[0])){
//						
//					}
//				}
//			}
//		}
		return users;
	}
	
	private Criterion[] createCriterions(UserSearchModel model){
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(StringUtils.isNotBlank(model.getUser().getName())){
			criterions.add(Restrictions.like("name", model.getUser().getName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getUser().getNickName())){
			criterions.add(Restrictions.like("nickName", model.getUser().getNickName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getUser().getEmail())){
			criterions.add(Restrictions.like("email", model.getUser().getEmail(),MatchMode.ANYWHERE));
		}
		Criterion[] result = new Criterion[criterions.size()];
		for(int i = 0; i < criterions.size(); i++){
			result[i] = criterions.get(i);
		}
		return result;
	}
}
