package com.smrs.security.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.checkcode.action.CheckCodeAction;
import com.jof.framework.security.SessionSecurityConstants;
import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.IpUtil;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import com.smrs.logtrace.model.LogTraceModel;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;

/**
 * 用户登录
 * @author WangXuzheng
 *
 */
public class LoginAction extends BaseSecurityAction {
	private static final long serialVersionUID = -8752697553632656205L;
	/**
	 * 标志位，标示是否启用checkcode
	 */
	private boolean checkCodeEnabled = false;
	/**
	 * 标志位，是否启用多语言
	 */
	private boolean localeEnabled = false;
	private UserModel user;
	private String checkCode;
	private String localeInfo;
	/**
	 * 登录成功后跳转的url
	 */
	private String redirectURL = "";
	public UserModel getUser() {
		return user;
	}

	public boolean isCheckCodeEnabled() {
		return checkCodeEnabled;
	}

	public void setCheckCodeEnabled(boolean checkCodeEnabled) {
		this.checkCodeEnabled = checkCodeEnabled;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public boolean isLocaleEnabled() {
		return localeEnabled;
	}

	public void setLocaleEnabled(boolean localeEnabled) {
		this.localeEnabled = localeEnabled;
	}

	public String getLocaleInfo() {
		return localeInfo;
	}

	public void setLocaleInfo(String localeInfo) {
		this.localeInfo = localeInfo;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	@Override
	public String execute() throws Exception { 
		String ipAddress = IpUtil.getIpAddress(getRequest());
		logger.info(" ipAddress=" +ipAddress);
		ExecuteResult<UserModel> result = userService.login(user.getName(), user.getPassword(), ipAddress);
		if (!result.isSuccess()) {			
			addActionErrorsFromResult(result);
			addLogTrace(user,result);
			return INPUT;
		}

		final UserModel dbUser = result.getResult();
		//取当前时间查看账号是否被锁定
		Date nowDate = new Date();
		if(dbUser.getExpiredTime() != null && nowDate.after(dbUser.getExpiredTime())){ 
			ExecuteResult<UserModel> executeResult = new ExecuteResult<UserModel>();
			executeResult.addErrorMessage("您的账号已经过期!");
			addActionErrorsFromResult(executeResult); 
			addLogTrace(user,executeResult);			
			return INPUT;
		}else{
			writeSession(dbUser);
			addLogTrace(user,result);
			//设置跳转
			//首先从requestParameter中读取要跳转到的url,不存在则读取session中保存的最近一次访问url
			final String redirectURLResult = "redirectURL";
			if(StringUtils.isNotBlank(this.getRedirectURL())){
				return redirectURLResult;
			}else{
				String lastVisitURL = (String)getSession().getAttribute(SessionSecurityConstants.KEY_LAST_VISIT_URL);
				if(StringUtils.isNotBlank(lastVisitURL)){
					this.setRedirectURL(lastVisitURL);
					return redirectURLResult;
				}
			}
			return SUCCESS;
		}
	}

	public void addLogTrace(UserModel user,ExecuteResult<UserModel> result){
		LogTraceModel logTrace = new LogTraceModel();
		logTrace.setName("用户登录");
		logTrace.setTraceAction("loginAction");
		if(result.isSuccess()==true){
			logTrace.setDescription("登录成功!");
		}else{
			logTrace.setDescription(result.getErrorMessagesString());
		}
		AppUtil.setCreateUserInfo(user, logTrace);
		logTraceService.addModel(logTrace);
	}
	
	private void writeSession(UserModel user) {
		getSession().setAttribute(SessionSecurityConstants.KEY_USER_ID, user.getId());
		getSession().setAttribute(SessionSecurityConstants.KEY_USER_NAME, user.getName());
		getSession().setAttribute(SessionSecurityConstants.KEY_LAST_LOGIN_IP, user.getLastLoginIp()); 
		getSession().setAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME, user.getNickName());
		getSession().setAttribute(SessionSecurityConstants.KEY_USER_MODEL, user);
		if(userService.getExpiredDate(user) != null){
			getSession().setAttribute("expiredDate", userService.getExpiredDate(user));
		}
		configLocaleSession();
		ExecuteResult<Boolean> passwordTip = userService.shouldTipPassword(user);
		if(!passwordTip.isSuccess()){
			getSession().setAttribute(SessionSecurityConstants.KEY_PASSWORD_TIP, errorMsg(passwordTip.getErrorMessages()));
		}
		getSession().removeAttribute(CheckCodeAction.CHECKCODE_KEY);//验证码过期
	}

	private String errorMsg(List<String> errors){
		StringBuilder stringBuilder = new StringBuilder();
		for(String error : errors){
			stringBuilder.append(error);
			stringBuilder.append(";");
		}
		if(stringBuilder.length() > 0){
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
		}
		return stringBuilder.toString();
	}
	private void configLocaleSession() {
		String[] localeStr = getLocaleArray();
		Locale locale = new Locale(localeStr[0],localeStr[1]);//默认中文
		getSession().setAttribute(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, locale);
	}

	private String[] getLocaleArray(){
		String[] locale = StringUtils.defaultIfEmpty(localeInfo, "zh_CN").split("_");
		if(locale == null || locale.length!=2){
			return new String[]{"zh",""};
		}
		return locale;
	}
	@Override
	public void validate() {
		super.validate();
		validateCheckCode();
		validateLocale();
	}

	private void validateLocale() {
		if (localeEnabled) {
			if (StringUtils.isEmpty(localeInfo)) {
				addActionError("请选择语言.");
			}
		}
	}

	private void validateCheckCode() {
		if (checkCodeEnabled) {
			if (StringUtils.isEmpty(checkCode)) {
				addActionError("验证码不能为空.");
			} else {
				String sessionCheckCode = (String) getSession().getAttribute(SessionSecurityConstants.KEY_CHECKCODE);
				if (!StringUtils.equalsIgnoreCase(sessionCheckCode, this.checkCode)) {
					addActionError("验证码不正确.");
				}
			}
		}
	}
}
