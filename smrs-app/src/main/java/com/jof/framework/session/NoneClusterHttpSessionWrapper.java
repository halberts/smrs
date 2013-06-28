package com.jof.framework.session;

import javax.servlet.http.HttpSession;

/**
 * 非集群环境的session实现
 * @author WangXuzheng
 *
 */
public class NoneClusterHttpSessionWrapper extends HttpSessionWrapper {
	public NoneClusterHttpSessionWrapper(HttpSession session) {
		super(session);
	}
}
