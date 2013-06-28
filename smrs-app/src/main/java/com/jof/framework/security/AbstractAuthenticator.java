package com.jof.framework.security;

/**
 * 认证接
 * @author jonathan_bian
 *
 */
public abstract class AbstractAuthenticator {
	private final Authentication authentication;
	
	public AbstractAuthenticator(Authentication authentication) {
		this.authentication = authentication;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	/**
	 * 是否拥有对url的访问
	 * @param url
	 * @return
	 */
	public abstract boolean hasUrlAuth(String url);
	
	/**
	 * 是否具有对某个资源的访问权限
	 * @param componentCode
	 * @return
	 */
	public boolean hasComponentAuth(String componentCode){
		return authentication.getComponentResources().contains(componentCode);
	}
}
