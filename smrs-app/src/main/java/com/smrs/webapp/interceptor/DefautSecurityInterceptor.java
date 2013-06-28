package com.smrs.webapp.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.security.AbstractAuthenticator;
import com.haier.openplatform.security.Authentication;
import com.haier.openplatform.security.interceptor.AbstractAuthenticationInterceptor;
import com.smrs.security.enums.ResourceTypeEnum;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.service.ResourceService;

/**
 * @author WangXuzheng
 * 
 */
public class DefautSecurityInterceptor extends AbstractAuthenticationInterceptor {
	private static final long serialVersionUID = -7145007423822243392L;
	private static final String SUFFIX = ".action"; 
	private ResourceService resourceService; 
	/**
	 * 被忽略不被安全检查的url
	 */ 
	private List<String> ignoralList = new ArrayList<String>();
	 

	public List<String> getIgnoralList() {
		return ignoralList;
	}

	public void setIgnoralList(List<String> ignoralList) {
		this.ignoralList = ignoralList;
	} 

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Override
	public Authentication getAuthentication(Long userId) { 
		List<ResourceModel> resources = resourceService.getGroupResourceByUserId(userId); 
		Authentication authentication = new Authentication();
		for(ResourceModel resource : resources){
			ResourceTypeEnum resourceType = ResourceTypeEnum.toEnum(resource.getType());
			if(resourceType == ResourceTypeEnum.URL_RESOURCE){
				authentication.getUrlResources().add(resource.getUrl());
				if(StringUtils.isNotEmpty(resource.getCode())){
					authentication.getComponentResources().add(resource.getCode());
				}
			}else if(resourceType == ResourceTypeEnum.COMPONENT_RESOURCE){
				authentication.getComponentResources().add(resource.getCode());
			}
		}
		return authentication;
	}
	
	@Override
	protected AbstractAuthenticator getAuthenticator(Authentication authentication) {
		return new AbstractAuthenticator(authentication) {
			@Override
			public boolean hasUrlAuth(String url) {
				String actualUrl = StringUtils.defaultIfEmpty(url, "/");
				if(!actualUrl.startsWith("/")){
					actualUrl = "/" + url;
				}
				for(String resource : getAuthentication().getUrlResources()){
					if(actualUrl.startsWith(resource) && resource.contains(SUFFIX)){
						return true;
					}
				}
				for(String resource : ignoralList){
					if(actualUrl.startsWith(resource)){
						return true;
					}
				}
				if(!url.contains(SUFFIX)){
					return true;
				}
				return false;
			}
		};
	} 
}
