package com.smrs.security.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.haier.openplatform.hmc.domain.Email;
import com.haier.openplatform.hmc.domain.Recipient;
import com.haier.openplatform.hmc.sender.email.SendEmailService;
import com.haier.openplatform.i18n.I18nResolver;
import com.haier.openplatform.i18n.I18nResolverFactory;
import com.jof.framework.security.LoginContext;
import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.session.listener.MaxSessionUtil;
import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.jof.framework.util.PasswordUtil;
import com.smrs.basicdata.dao.DepartmentDAO;
import com.smrs.basicdata.dao.StoreDao;
import com.smrs.basicdata.model.Department;
import com.smrs.basicdata.model.StoreModel;
import com.smrs.security.dao.ResourceDAO;
import com.smrs.security.dao.RoleDAO;
import com.smrs.security.dao.UserDAO;
import com.smrs.security.model.UserModel;
import com.smrs.security.service.UserService;
import com.smrs.security.vo.UserSearchModel;
import com.smrs.util.CacheNames;
import com.smrs.util.Env;

/**
 * @author jonathan
 * 
 */
public class UserServiceImpl implements UserService {
	//Logger logger = LogFactory.getLog(UserServiceImpl.class);
	final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final I18nResolver I18N_RESOLVER = I18nResolverFactory.getDefaultI18nResolver(UserServiceImpl.class);
	private static final int PASSWORD_TIP_DAYS = 10;//密码过期10天提前提醒
	private static final int DAY_MILLISECONDS = 24 * 60 * 60 * 1000;//一天的毫秒数
	private UserDAO userDAO;
	private RoleDAO roleDAO;
	@Autowired
	private StoreDao storeDao;
	private DepartmentDAO departmentDAO;
	private ResourceDAO resourceDAO;
	private CacheManager cacheManager;
	private SendEmailService emailSender;
	
	public SendEmailService getEmailSender() {
		return emailSender;
	}
	public void setEmailSender(SendEmailService emailSender) {
		this.emailSender = emailSender;
	}
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	public ExecuteResult<UserModel> createUser(UserModel user) {
		ExecuteResult<UserModel> executeResult = new ExecuteResult<UserModel>();
		// 重名检查
		UserModel existedUser = userDAO.getUserByName(StringUtils.trim(user.getName()));
		if(existedUser != null){
			executeResult.addErrorMessage(I18N_RESOLVER.getMessage("user.existed", user.getName()));
			return executeResult;
		}
		// 关联角色
		resetUserRoleInfo(user);
		// 关联部门
		resetUserStoreInfo(user, user);
		// 保存
		user.setName(StringUtils.trim(user.getName()));
		user.setNickName(StringUtils.trim(user.getNickName()));
		user.setCreationDate(new Date());
		user.setLastModifyDate(new Date());
		user.setCreator(LoginContextHolder.get().getUserName());
		user.setModifiedBy(LoginContextHolder.get().getUserName());
		user.setPassword(PasswordUtil.encrypt(user.getPassword()));
		user.setLoginAttemptTimes(0);
		user.setPasswordExpireTime(calculatePasswordExpireDate());
		user.setPasswordModifiedFlag('N');
		userDAO.save(user);
		executeResult.setResult(user);
		return executeResult;
	}

	private void resetUserRoleInfo(UserModel user) {
		/*
		if(user.getRoles() != null && !user.getRoles().isEmpty()){
			Set<RoleModel> roles = new HashSet<RoleModel>();
			for(RoleModel role : user.getRoles()){
				roles.add(roleDAO.get(role.getId()));
			}
			user.setRoles(roles);
		}
		*/
	}


	public Pager<UserModel> searchUser(UserSearchModel userSearchModel) {
		return userDAO.searchUser(userSearchModel);
	}

	@Override
	public UserModel getUserById(Long id) {
		return (UserModel)userDAO.get(id);
	}

	@Override
	public ExecuteResult<?> deleteUser(Long userId) {
		ExecuteResult<?> result = new ExecuteResult<Object>();
		// 检查是否还存在引用
		userDAO.delete(userId);
		return result;
	}
	@Override
	public ExecuteResult<UserModel> confirmUpdatePassword(String name, String encode,String password,String confirmpassword) {
		ExecuteResult<UserModel> executeResult = new ExecuteResult<UserModel>();
		UserModel dbUser = (UserModel) userDAO.getUserByName(name);
		if(dbUser == null){
			executeResult.addErrorMessage("用户名输入错误");
			return executeResult; 
		}
		if(!password.equals(confirmpassword)){
			executeResult.addErrorMessage("两次密码输入不一致");
			return executeResult; 
		}
		if(!encode.equals(dbUser.getEncode())){
			executeResult.addErrorMessage("修改密码链接失效");
			return executeResult; 
		}
		if(!PasswordUtil.isValidPassword(password)){
			executeResult.addErrorMessage("帐号密码至少8位，须符合大小写字母、数字、特殊字符四选三的要求.");
			return executeResult; 
		}else{
			//修改密码 
			String newPassword = PasswordUtil.encrypt(confirmpassword);
			dbUser.setPassword(newPassword);
			dbUser.setEncode(null);//清空验证码
			//dbUser.setGmtModified(new Date());
			dbUser.setModifiedBy(name);
			dbUser.setPasswordExpireTime(calculatePasswordExpireDate());
			dbUser.setPasswordModifiedFlag('Y');
			this.userDAO.update(dbUser); 
			return executeResult;  
		}  
	 }
    @Override
	public ExecuteResult<UserModel> getUserEmailByName(String name, String email) {
    	ExecuteResult<UserModel> executeResult = new ExecuteResult<UserModel>();
		UserModel dbUser = (UserModel) userDAO.getUserByName(name);
        //判断输入的邮箱和用户的邮箱是否相同 
		if(dbUser == null){
			executeResult.addErrorMessage("输入的用户名不存在");
			return executeResult;
		}else if(!email.equals(dbUser.getEmail())) {
			executeResult.addErrorMessage("输入的用户名和邮箱不匹配");
			return executeResult;
		} else{ 
			return executeResult;
		} 
	}
    @Override
	public ExecuteResult<UserModel> updateUserEncode(String name) { 
    	ExecuteResult<UserModel> executeResult = new ExecuteResult<UserModel>();
		UserModel dbUser = (UserModel) userDAO.getUserByName(name);
    	//生成随机验证码
		String encode = UUID.randomUUID().toString();
		//更新用户的验证码  
		dbUser.setEncode(encode);//修改验证码
		//dbUser.setGmtModified(new Date());
		dbUser.setModifiedBy(name);
		dbUser.setPasswordExpireTime(calculatePasswordExpireDate());
		dbUser.setPasswordModifiedFlag('Y'); 
	    this.userDAO.update(dbUser); 
 
	    //增加发送邮件方法后使用到的链接
	    String retirevepasswordurl = Env.getProperty(Env.APP_URL);
		//组装对应的更改密码地址
		//增加发送邮件方法后使用到的链接
	    String updatePwdUrl = retirevepasswordurl+"/security/toRetrieveUpdatePassword.action?encode="+encode; 
		//调用发送邮件方法将地址发送给用户 
	    Email email = new Email();
	    Recipient recipient = new Recipient(); 
	    recipient.setUserName("HopAdmin");
	    recipient.setEmailAddress("wangjian.psi@haier.com");
	    email.setSender(recipient); 
	    Recipient toRecipient = new Recipient(); 
	    toRecipient.setUserName(name);
	    toRecipient.setEmailAddress(dbUser.getEmail());
	    List<Recipient> toRecipientList = new ArrayList<Recipient>();
	    toRecipientList.add(toRecipient); 
	    email.setToRecipient(toRecipientList);
	    email.setSubject("密码修改提示");
	    email.setBodyContent("亲,您的"+Env.getProperty(Env.APP_NAME.isEmpty()||Env.APP_NAME == null ? "" : Env.APP_NAME)+"系统密码已经申请重置,请复制以下链接到浏览器进行新密码设置"+updatePwdUrl,false);
	    emailSender.sendMsg(email);
		return executeResult;
    }
	@Override
	public ExecuteResult<UserModel> updateUser(UserModel user) {
		ExecuteResult<UserModel> result = new ExecuteResult<UserModel>();
		UserModel dbUser = (UserModel)userDAO.get(user.getId());
		if(dbUser == null){
			result.addErrorMessage("该用户不存在或已经被删除。");
			return result;
		}
		//重名检查
		if(!dbUser.getName().equals(user.getName())){
			UserModel namedUser = userDAO.getUserByName(user.getName());
			if(namedUser != null){
				result.addErrorMessage("用户名："+user.getName()+"已经存在。");
				return result;
			}
		}
		// 关联角色
		/*
		if(user.getRoles() != null && !user.getRoles().isEmpty()){
			Set<RoleModel> roles = new HashSet<RoleModel>();
			for(RoleModel role : user.getRoles()){
				roles.add(roleDAO.get(role.getId()));
			}
			dbUser.setRoles(roles);
		}
		*/
		// 关联部门
		resetUserStoreInfo(user, dbUser);
		//修改
		//dbUser.setGmtModified(new Date());
		dbUser.setEmail(user.getEmail());
		dbUser.setResponsibility(user.getResponsibility());
		dbUser.setMemo(user.getMemo());
		dbUser.setName(user.getName());
		dbUser.setNickName(StringUtils.trim(user.getNickName()));
		if(LoginContextHolder.get()!=null){
			dbUser.setModifiedBy(LoginContextHolder.get().getUserName());
		}
		dbUser.setStatus(user.getStatus());
		dbUser.setExpiredTime(user.getExpiredTime());
		dbUser.setLastLoginIp(user.getLastLoginIp());
		dbUser.setLastLoginTime(user.getLastLoginTime());
		//dbUser.setPassword(PasswordUtil.encodePasswordMD5(user.getPassword()));
		userDAO.update(dbUser);
		result.setResult(user);
		return result;
	}

	private void resetUserStoreInfo(UserModel user, UserModel dbUser) {
		if(user.getStores() != null && !user.getStores().isEmpty()){
			Set<StoreModel> storeList = new HashSet<StoreModel>();
			for(StoreModel store : user.getStores()){
				storeList.add(storeDao.load(store.getId()));
			}
			dbUser.setStores(storeList);
		}else{
			dbUser.setStores(new HashSet<StoreModel>());
		}
	}

	
	public ExecuteResult<UserModel> login(String userName, String password,String ipAddress) {
		ExecuteResult<UserModel> executeResult = new ExecuteResult<UserModel>();
		UserModel userInfo = userDAO.getUserByName(userName);
		final String errorMsg = "用户名或密码错误，连续输错5次，账号会被禁用。";
		
		//用户名和密码以及用户状态是否匹配
		if(userInfo == null){
			executeResult.addErrorMessage(errorMsg);
			return executeResult; 
		}
		/*
		if(UserStatusEnum.toEnum(userInfo.getStatus()) == UserStatusEnum.INACTIVE){
			executeResult.addErrorMessage(errorMsg);
			return executeResult;
		}
		*/
		String encryptPassword = PasswordUtil.encrypt(password);
		if(!userInfo.getPassword().equals(encryptPassword)){
			doPasswordNotMatch(userInfo);
			executeResult.addErrorMessage(errorMsg);
			return executeResult;
		}
		
		//不允许同一账号在多处同时登陆
//		Cache sessionCache = cacheManager.getCache(CacheNames.CACHE_NAME_SESSION);
//		String key = MaxSessionUtil.generateMaxSessionKey(userInfo.getName());
//		ValueWrapper valueWrapper = sessionCache.get(key);
//		Map<String, Integer> sessionMap = null;
//		if(valueWrapper == null || valueWrapper.get() == null){
//			sessionMap = new HashMap<String, Integer>();
//		}else{
//			sessionMap = (Map<String, Integer>) valueWrapper.get();
//		}
//		boolean localLogin = sessionMap.containsKey(ipAddress);
		/*int size = sessionMap.keySet().size();
		if((localLogin && size > 1) ||(!localLogin && size >= 1)){//在除本机外的其他多个地方登录过
			executeResult.addErrorMessage("该账号已经在别处登录，请将其注销后再登录。");
			return executeResult;
		}*/
//		sessionMap.put(ipAddress, localLogin?sessionMap.get(ipAddress)+1:1);
//		sessionCache.put(key, sessionMap);
		//更新用户信息
		userInfo.setLastLoginIp(ipAddress);
		userInfo.setLastLoginTime(new Date());
		userInfo.setLoginFaildTime(null);
		userInfo.setLoginAttemptTimes(0);
		userInfo.setCurrentLoginIp(ipAddress);
		updateUser(userInfo);
		
		executeResult.setResult(userInfo);
		//清理缓存
//		cacheManager.getCache(CacheNames.CACHE_NAME_USER).evict(CacheNames.USER_KEY_PREFIX+userInfo.getId());
		return executeResult;
	}
	private void doPasswordNotMatch(UserModel userInfo) {
		final long accountLockTime = 60*60*1000L;//登陆1小时内尝试5次
		if(userInfo.getLoginFaildTime() == null){
			userInfo.setLoginFaildTime(new Date());
		}else if(userInfo.getLoginFaildTime().getTime()+accountLockTime<System.currentTimeMillis()){
			userInfo.setLoginFaildTime(new Date());
			userInfo.setLoginAttemptTimes(0);
		}
		userInfo.setLoginAttemptTimes((userInfo.getLoginAttemptTimes()==null?0:userInfo.getLoginAttemptTimes())+1);//错误次数累加
		final Integer loginTimes = 5;//最多可以连续登陆5次密码错误
		if(userInfo.getLoginAttemptTimes() >= loginTimes){
			//userInfo.setStatus(UserStatusEnum.INACTIVE.getStatus());//自动锁定
		}
	}
	
	@SuppressWarnings("unchecked")
	public ExecuteResult<UserModel> logout(LoginContext context) {
		//清理用户资源缓存
		List<String> moduleNames = resourceDAO.getmoduleNames();
		for(String moduleName : moduleNames){
			if(moduleName != null && !moduleName.isEmpty()){
				cacheManager.getCache(CacheNames.CACHE_NAME_RESOURCE).evict(Env.getProperty(Env.KEY_SERVER_NAME)+":resource:"+context.getUserId()+":"+moduleName);
			} 
		} 
		ExecuteResult<UserModel> executeResult = new ExecuteResult<UserModel>(); 
		Cache sessionCache = cacheManager.getCache(CacheNames.CACHE_NAME_SESSION);
		String key = MaxSessionUtil.generateMaxSessionKey(context.getUserName());
		ValueWrapper valueWrapper = sessionCache.get(key);
		if(valueWrapper == null){
			return executeResult;
		}
		Map<String, Integer> sessionMap = (Map<String, Integer>) valueWrapper.get();
		if(sessionMap != null){
			Integer sessions = sessionMap.get(context.getIp());
			if(sessions == null || sessions <= 1){
				sessionMap.remove(context.getIp());
			}else{
				sessionMap.put(context.getIp(), sessions - 1);
			}
			sessionCache.put(key, sessionMap);
		}
		return executeResult;
	}
	private Date calculatePasswordExpireDate(){
		Calendar clendar = Calendar.getInstance();
		clendar.add(Calendar.MONTH, 2);//2个月后密码过期
		return clendar.getTime();
	}

	@Override
	public void updatePassword(Long userId, String password) {
		String newPassword = PasswordUtil.encrypt(password);
		UserModel user = getUserById(userId);
		if(user == null){
			return;
		}
		user.setPassword(newPassword);
		//user.setModified(new Date());
		//user.setLastModifiedBy(LoginContextHolder.get().getUserName());
		user.setPasswordExpireTime(calculatePasswordExpireDate());
		user.setPasswordModifiedFlag('Y');
		this.userDAO.update(user);
	}
	@Override
	public ExecuteResult<Boolean> shouldTipPassword(UserModel user) {
		ExecuteResult<Boolean> executeResult = new ExecuteResult<Boolean>();
		Character flag = user.getPasswordModifiedFlag();
		if (flag==null || flag == 'N') {
			
			executeResult.addErrorMessage("首次登陆系统必须修改默认密码.");
			return executeResult;
		}
		if(user.getPasswordExpireTime() == null){
			return executeResult;
		}
		//密码是否即将过期，
		Date now = formatDate(new Date());
		Date expireDate = formatDate(user.getPasswordExpireTime());
		Date tipDay = DateUtils.addDays(expireDate, -PASSWORD_TIP_DAYS);
		long expiredTime = now.getTime() - tipDay.getTime();
		long exprireDays = expiredTime / DAY_MILLISECONDS;//到期天数
		if (exprireDays >= 0) {
			if(exprireDays < PASSWORD_TIP_DAYS){
				executeResult.addErrorMessage("您的密码还有" + (PASSWORD_TIP_DAYS - exprireDays) + "天过期，请及时更改密码.");
			}else{
				executeResult.addErrorMessage("您的密码已经过期，请及时更改密码.");
			}
		}
		return executeResult;
	}
	//去除时间中的时分秒和毫秒
	private Date formatDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	@Override
	public String getExpiredDate(UserModel user) {
		if(user.getExpiredTime() != null){
			SimpleDateFormat  dateFormat = new SimpleDateFormat ("yyMMdd");
			String expiredTime = dateFormat.format(user.getExpiredTime());
			String nowTime = dateFormat.format(new Date());
			long expiredDate = Long.parseLong(expiredTime)-Long.parseLong(nowTime);
			if(expiredDate>0 && expiredDate<7){ 
				String[] admin = Env.getProperty(Env.SYS_ADMIN).split(",");
				StringBuffer result = new StringBuffer();
				if (admin.length > 0) {
					for (String ad : admin) {
						UserModel adminInfo = this.getUserByName(ad);
						result = result.append("-" + adminInfo.getNickName()
								+ "(邮箱:" + adminInfo.getEmail() + ");");
					}
					String info = "您的账号将会在" + expiredDate + "天后过期,请联系管理员:"
							+ result;
					return info;
				} else {
					return null;
				}
			}else{
				return null;
			}
		} else{
			return null;
		}
	}
	
	@Override
	public UserModel getUserByName(String name) {
		return userDAO.getUserByName(name); 	
	}
	
	@Override
	public List<UserModel> getAll() { 
		return userDAO.getAll();
	}
	
	
	public ExecuteResult<UserModel> updateExpiredUser(UserModel user) {
		ExecuteResult<UserModel> result = new ExecuteResult<UserModel>();
		UserModel dbUser = (UserModel)userDAO.get(user.getId());
		if(dbUser == null){
			result.addErrorMessage("该用户不存在或已经被删除。");
			return result;
		}
		//重名检查
		if(!dbUser.getName().equals(user.getName())){
			UserModel namedUser = userDAO.getUserByName(user.getName());
			if(namedUser != null){
				result.addErrorMessage("用户名："+user.getName()+"已经存在。");
				return result;
			}
		}
		// 关联角色
		/*
		if(user.getRoles() != null && !user.getRoles().isEmpty()){
			Set<RoleModel> roles = new HashSet<RoleModel>();
			for(RoleModel role : user.getRoles()){
				roles.add(roleDAO.get(role.getId()));
			}
			dbUser.setRoles(roles);
		}
		*/
		// 关联部门
		resetUserStoreInfo(user, dbUser);
		//修改
		dbUser.setLastModifyDate(new Date());
		dbUser.setEmail(user.getEmail());
		dbUser.setName(user.getName());
		dbUser.setNickName(StringUtils.trim(user.getNickName()));
		dbUser.setModifiedBy("ShowcaseQuartz");
		dbUser.setStatus(user.getStatus());
		dbUser.setExpiredTime(user.getExpiredTime());
		//dbUser.setPassword(PasswordUtil.encodePasswordMD5(user.getPassword()));
		userDAO.update(dbUser);
		result.setResult(user);
		return result;
	}
}
