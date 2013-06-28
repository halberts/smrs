package com.jof.framework.session.service;

import java.util.Map;

/**
 * 对session进行操作的service接口
 * @author WangXuzheng
 *
 */
public interface SessionService {
	/**
	 * 根据sessionID获取session信息
	 * @param id
	 * @return
	 */
	public Map<Object,Object> getSession(String sessionId);
	/**
	 * 更新session信息
	 * @param id
	 * @param session
	 */
	public void updateSession(String id, Map<Object,Object> session);
	/**
	 * 删除session信息
	 * @param id
	 */
	public void removeSession(String id);
	
	/**
	 * 新增session信息
	 * @param id
	 * @param session
	 */
	public void addSession(String id, Map<Object,Object> session);
}
