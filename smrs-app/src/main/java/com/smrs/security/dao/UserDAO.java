package com.smrs.security.dao;



import com.jof.framework.dao.BaseDAO;
import com.jof.framework.util.Pager;
import com.smrs.security.model.UserModel;
import com.smrs.security.vo.UserSearchModel;

/**
 * 用户接口
 * @author WangXuzheng
 *
 */
public interface UserDAO extends BaseDAO<UserModel,Long>{
	/**
	 * 通过用户
	 * @param id
	 * @return
	 */
	public UserModel getUserByName(String name);
	
	public Pager<UserModel> searchUser(UserSearchModel model);
}
