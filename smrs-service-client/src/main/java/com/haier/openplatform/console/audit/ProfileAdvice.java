package com.haier.openplatform.console.audit;

import org.aspectj.lang.JoinPoint;

import com.haier.openplatform.console.domain.Profile;
import com.haier.openplatform.console.domain.ProfileBean;

/**
 * 监控拦截器
 * @author WangXuzheng
 *
 */
public class ProfileAdvice {
	public void beforeExecute(JoinPoint thisJoinPoint){
		if(ProfileContextHolder.isEnabled()){
			long startTime = System.currentTimeMillis();
			Profile profile = ProfileContextHolder.get();
			String targetClassName = thisJoinPoint.getTarget().getClass().getName();
			String methodName = thisJoinPoint.getSignature().getName();
			
			ProfileBean parentProfileBean = profile.getInvokeStack().peek();//调用堆栈的上层信息
			//判断是否循环调用
			ProfileBean profileBean = parentProfileBean.getInvokedProfileBean(targetClassName, methodName);
			if(profileBean == null){
				profileBean = new ProfileBean();
				profileBean.setClassName(targetClassName);
				profileBean.setMethodName(methodName);
				profileBean.setTimes(1);
				parentProfileBean.addChild(profileBean);
			}else{
				profileBean.setTimes(profileBean.getTimes()+1);//循环调用次数+1
			}
			parentProfileBean.putInvokedProfileBean(targetClassName, methodName, profileBean);
			profileBean.setStartTime(startTime);
			profile.getInvokeStack().push(profileBean);
		}
	}
	public void afterExecute(JoinPoint thisJoinPoint){
		if(ProfileContextHolder.isEnabled()){
			long endTime = System.currentTimeMillis();
			Profile profile = ProfileContextHolder.get();
			ProfileBean profileBean = profile.getInvokeStack().pop();//出栈
			profileBean.setExecutionTime(endTime-profileBean.getStartTime()+profileBean.getExecutionTime());
		}
	}
}
