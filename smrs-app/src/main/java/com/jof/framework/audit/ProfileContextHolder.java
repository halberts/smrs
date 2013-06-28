package com.jof.framework.audit;

import com.haier.openplatform.console.domain.Profile;

/**
 * 监控上线文信息存储器，每个请求都保留在自己的线程变量中
 * @author WangXuzheng
 * @see ThreadLocal
 */
public class ProfileContextHolder {
	private static final ThreadLocal<Profile> CONTEXT = new ThreadLocal<Profile>();
	public static void put(Profile profile){
		CONTEXT.set(profile);
	}
	public static Profile get(){
		return CONTEXT.get();
	}
	public static void clear(){
		CONTEXT.remove();
	}
	/**
	 * 对方法是否启用aop拦截
	 * @return
	 */
	public static boolean isEnabled(){
		return get()!= null;
	}
}
