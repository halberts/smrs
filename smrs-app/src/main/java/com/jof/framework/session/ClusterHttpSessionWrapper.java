package com.jof.framework.session;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.haier.openplatform.session.service.SessionService;

/**
 * HttpSession的包装类，链接SessionService组件对session进行操作
 * @author WangXuzheng
 *
 */
public class ClusterHttpSessionWrapper extends HttpSessionWrapper {
	private SessionService sessionService;
	private Map<Object,Object> map;
	private String sessionId;
	private long creationTime;
	private long lastAccessedTime;
	public ClusterHttpSessionWrapper(String sessionId,HttpSession session,SessionService sessionService) {
		super(session);
		this.sessionId = sessionId;
		this.sessionService = sessionService;
		this.map = this.sessionService.getSession(sessionId);
		this.creationTime = System.currentTimeMillis();
		this.lastAccessedTime = this.creationTime;
	}
	
	@Override
	public Object getAttribute(String key) {
		resetLastAccessTime();
		return this.map.get(key);
	}
	
	@Override
	public Enumeration<Object> getAttributeNames() {
		resetLastAccessTime();
		return Collections.enumeration(this.map.keySet());
	}
	
	@Override
	public void invalidate() {
		resetLastAccessTime();
		this.map.clear();
		this.sessionService.removeSession(this.sessionId);
	}
	
	@Override
	public void removeAttribute(String key) {
		resetLastAccessTime();
		this.map.remove(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		resetLastAccessTime();
		this.map.put(key, value);
	}
	
	public void saveSession(){
		this.sessionService.updateSession(this.sessionId, this.map);
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}
	
	private void resetLastAccessTime(){
		this.lastAccessedTime = System.currentTimeMillis();
	}
}
