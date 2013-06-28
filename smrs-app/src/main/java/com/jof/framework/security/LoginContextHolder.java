package com.jof.framework.security;


/**
 * 将当期登录者信息保存到线程上下文中
 * @author WangXuzheng
 *
 */
public final class LoginContextHolder {
	private static final ThreadLocal<LoginContext> CONTEXT = new ThreadLocal<LoginContext>();
	private LoginContextHolder(){
	}
	public static void put(LoginContext context){
		CONTEXT.set(context);
	}
	public static LoginContext get(){
		return CONTEXT.get();
	}
	public static void clear(){
		CONTEXT.remove();
	}
}
