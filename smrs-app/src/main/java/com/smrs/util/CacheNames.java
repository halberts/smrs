package com.smrs.util;


/**
 * 缓存名称常量
 * @author WangXuzheng
 *
 */
public final class CacheNames {
	//应用名称
	public static final String APPLICATION_NAME = Env.getProperty(Env.KEY_SERVER_NAME);
	//用户信息
	public static final String CACHE_NAME_USER = "user";
	public static final String USER_KEY_PREFIX = APPLICATION_NAME + ":" + CACHE_NAME_USER + ":";
	//资源信息
	public static final String CACHE_NAME_RESOURCE = "resource";
	public static final String RESOURCE_KEY_PREFIX = APPLICATION_NAME + ":" + CACHE_NAME_RESOURCE + ":";
	//角色信息
	public static final String CACHE_NAME_ROLE = "role";
	public static final String ROLE_KEY_PREFIX = APPLICATION_NAME + ":" + CACHE_NAME_ROLE + ":";
	//會話信息
	public static final String CACHE_NAME_SESSION = "session";
	private CacheNames() {
	}
}
