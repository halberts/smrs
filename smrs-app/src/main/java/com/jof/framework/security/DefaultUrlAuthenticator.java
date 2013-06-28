package com.jof.framework.security;

/**
 * 默认的url认证器，判断url以XXX开头为验证条件
 * @author WangXuzheng
 *
 */
public class DefaultUrlAuthenticator extends AbstractAuthenticator {

	public DefaultUrlAuthenticator(Authentication authentication) {
		super(authentication);
	}

	@Override
	public boolean hasUrlAuth(String url) {
		if(url == null || url.isEmpty()){
			return false;
		}
		if(getAuthentication().getUrlResources().contains(url)){
			return true;
		}
		for(String resource : getAuthentication().getUrlResources()){
			if(url.startsWith(resource)){
				return true;
			}
		}
		return false;
	}

}
