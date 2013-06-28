package com.smrs.security.service;

import java.util.List;

import com.jof.framework.security.LoginContext;
import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;

import com.smrs.security.model.UserModel;
import com.smrs.security.vo.UserSearchModel;

/**
 * @author WangXuzheng
 *
 */
public interface UserService {
	/**
	 * 创建用户信息
	 * @param user
	 * @return
	 */
	public ExecuteResult<UserModel> createUser(UserModel user);
	
	/**
	 * 根据用户id获取用户信息
	 * @param id
	 * @return
	 */
	public UserModel getUserById(Long id);
	
	/**
	 * 查询用户信息
	 * @param userSearchModel
	 * @return
	 */
	public Pager<UserModel> searchUser(UserSearchModel userSearchModel);
	
	/**
	 * 删除用户信息
	 * @param userId 用户id，不允许为空
	 * @return
	 */
	public ExecuteResult<?> deleteUser(Long userId); 
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public ExecuteResult<UserModel> updateUser(UserModel user);
	
	/**
	 * 用户登录操作
	 * @param userName
	 * @param password
	 * @return
	 */
	public ExecuteResult<UserModel> login(String userName,String password,String ipAddress);
	/**
	 * 更新密码
	 * @param userId
	 * @param password
	 */
	public void updatePassword(Long userId,String password);
	
	/**
	 * 判断用户密码是否需要提醒
	 * @param user
	 * @return
	 */
	public ExecuteResult<Boolean> shouldTipPassword(UserModel user);
	
	/**
	 * 用户登出操作
	 * @param context
	 * @return
	 */
	public ExecuteResult<UserModel> logout(LoginContext context);
	/**
	 * 检测用户输入的用户名、邮箱是否对应
	 * @param name
	 * @return
	 */
	public ExecuteResult<UserModel> getUserEmailByName(String name,String email);
	/**
	 * 检测用户找回密码的URL是否正常
	 * @param name 
	 * @param encode
	 * @return
	 */
	public ExecuteResult<UserModel> confirmUpdatePassword(String name,String encode,String password,String confirmpassword);
	/**
	 * 更新用户的Ecode
	 * @param name
	 * @return
	 */
	public ExecuteResult<UserModel> updateUserEncode(String name);
	/**
	 * 获取用户还有多少天过期
	 * @param user
	 * @return
	 */
	public String getExpiredDate(UserModel user); 
	/**
	 * 根据用户名获取用户信息
	 * @param user
	 * @return
	 */
	public UserModel getUserByName(String name);
	/**
	 * 获取所有用户信息
	 * @param user
	 * @return
	 */
	public List<UserModel> getAll();
	/**
	 * 更新失效账号信息
	 * @param user
	 * @return
	 */
	public ExecuteResult<UserModel> updateExpiredUser(UserModel user);
}
