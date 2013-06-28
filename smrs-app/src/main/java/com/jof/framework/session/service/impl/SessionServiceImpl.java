package com.jof.framework.session.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

import com.haier.openplatform.session.service.SessionService;

/**
 * 基于memcach实现的session存储操作
 * @author WangXuzheng
 *
 */
public class SessionServiceImpl implements SessionService,InitializingBean {
	private CacheManager cacheManager;
	private String cacheName = "session";

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getSession(String sessionId) {
		Cache cache = getCache();
		ValueWrapper valueWrapper = cache.get(sessionId);
		if (valueWrapper == null) {
			Map<Object,Object> session = new HashMap<Object,Object>();
			addSession(sessionId,session);
			return session;
		}else{
			return (Map<Object, Object>) valueWrapper.get();
		}
	}

	@Override
	public void updateSession(String sessionId, Map<Object, Object> session) {
		Cache cache = getCache();
		cache.put(sessionId, session);
	}

	private Cache getCache(){
		Cache cache = cacheManager.getCache(this.cacheName);
		Assert.notNull(cache);
		return cache;
	}
	@Override
	public void removeSession(String sessionId) {
		Cache cache = getCache();
		cache.evict(sessionId);
	}

	@Override
	public void addSession(String id, Map<Object, Object> session) {
		Cache cache = getCache();
		cache.put(id, session);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cacheManager);
		Assert.notNull(cacheName);
		Assert.hasText(cacheName);
	}
}
