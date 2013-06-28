package com.haier.openplatform.console.audit.interceptor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.haier.openplatform.console.audit.AuditInfoCollector;
import com.haier.openplatform.console.audit.ProfileContextHolder;
import com.haier.openplatform.console.domain.Profile;
import com.haier.openplatform.console.domain.ProfileBean;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.hmc.sender.SendMsgService;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 用来进行action监控的拦截器
 * @author WangXuzheng
 *
 */
public class ActionProfileInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 6586406215062388727L;
	/**
	 * 被忽略不希望被监控的action集合列表
	 */
	private Set<String> excludeActions = new HashSet<String>();
	private Set<String> includeActions = new HashSet<String>();
	private SendMsgService actionSender;
	/**
	 * 是否启用监控,默认为true
	 */
	private boolean enabled = true;
	/**
	 * 用来执行发送jms消息的线程池
	 */
	private ExecutorService executorService = Executors.newCachedThreadPool();
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//检查总控开关是否开启
		if(!enabled){
			return invocation.invoke();
		}
		//忽略不被监控的url列表
		String nameSpace = invocation.getProxy().getNamespace();
		StringBuilder actionUrlBuilder = new StringBuilder(nameSpace);
		if(!nameSpace.endsWith("/")){
			actionUrlBuilder.append("/");
		}
		actionUrlBuilder.append(invocation.getProxy().getActionName());
		String actionUrl = actionUrlBuilder.toString();
		if(excludeActions.contains(actionUrl)){//在不拦截的连接列表中
			return invocation.invoke();
		}
		if(!includeActions.isEmpty() && !includeActions.contains(actionUrl)){//不在指定拦截的url列表中
			return invocation.invoke();
		}
		//执行正常的监控流程
		ProfileContextHolder.clear();
		Profile profile = createProfile();
		ProfileBean actionBean = new ProfileBean();
		actionBean.setClassName(invocation.getAction().getClass().getName());
		actionBean.setMethodName(invocation.getProxy().getMethod());   
		actionBean.setTimes(1);//action执行一次
		actionBean.setStartTime(System.currentTimeMillis());
		profile.setProfileBean(actionBean);
		profile.getInvokeStack().push(actionBean);//将action压入调用栈中
		ProfileContextHolder.put(profile);//将性能监控对象放置到线程中
        String result = invocation.invoke();
        profile.getInvokeStack().pop();//action出栈
        long endTime = System.currentTimeMillis();
        profile.setEndTime(endTime);
        profile.getProfileBean().setExecutionTime(endTime-profile.getProfileBean().getStartTime());
        doSend(profile);//发送程序调用信息
        ProfileContextHolder.clear();
        return result;
	}
	
	/**
	 * 创建好Profile,各个程序做的一些个性化的需求
	 * @param profile
	 * @see HOPCONS#HOP_RELEASE_VERSION
	 * @see AuditInfoCollector#getAppNM()
	 */
	protected Profile createProfile(){
		Profile profile = new Profile();
		profile.setSystem(AuditInfoCollector.getAppNM());
		profile.setHopVersion(HOPCONS.HOP_RELEASE_VERSION);
		profile.setStartTime(System.currentTimeMillis());
		profile.setType(1);//action监控
		return profile;
	}
	
	/**
	 * 将每次调用信息发送到监控中心
	 * @param profile
	 */
	protected void doSend(final Profile profile){
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				actionSender.sendMsg(profile);
			}
		});
	}

	public void setExcludeActions(Set<String> excludeActions) {
		this.excludeActions = excludeActions;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setActionSender(SendMsgService actionSender) {
		this.actionSender = actionSender;
	}

}
