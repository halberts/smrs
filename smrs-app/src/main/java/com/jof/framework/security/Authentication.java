package com.jof.framework.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户认证信息
 * @author jonathan_bian
 *
 */
public class Authentication implements Serializable {
	private static final long serialVersionUID = -7582992830217184571L;
	private Set<String> urlResources = new HashSet<String>();
	private Set<String> componentResources = new HashSet<String>();
	public Set<String> getUrlResources() {
		return urlResources;
	}
	public void setUrlResources(Set<String> urlResources) {
		this.urlResources = urlResources;
	}
	public Set<String> getComponentResources() {
		return componentResources;
	}
	public void setComponentResources(Set<String> componentResources) {
		this.componentResources = componentResources;
	}
	
	/**
	 * 判断是否拥有指定url的访问权限
	 * @param url
	 * @return
	 */
	public boolean canAccessUrlResource(String url){
		return urlResources.contains(url);
	}
	
	/**
	 * 判断是否拥有访问指定compoent的权限
	 * @param componentCode
	 * @return
	 */
	public boolean canAccessComponentResource(String componentCode){
		return componentResources.contains(componentCode);
	}
}
