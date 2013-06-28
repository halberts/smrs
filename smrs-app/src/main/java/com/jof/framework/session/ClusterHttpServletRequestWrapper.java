package com.jof.framework.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.haier.openplatform.session.service.SessionService;

/**
 * 基于SessionService管理的httpRequest包装
 * @author WangXuzheng
 *
 */
public class ClusterHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private SessionService sessionService;
	private String sessionId;
	private HttpSession httpSession;

	public ClusterHttpServletRequestWrapper(HttpServletRequest request, String sessionId, SessionService sessionService) {
		super(request);
		this.sessionId = sessionId;
		this.sessionService = sessionService;
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (this.httpSession == null) {
			this.httpSession = new ClusterHttpSessionWrapper(this.sessionId, super.getSession(create),
					this.sessionService);
		}
		return this.httpSession;
	}

	@Override
	public HttpSession getSession() {
		if (this.httpSession == null) {
			this.httpSession = new ClusterHttpSessionWrapper(this.sessionId, super.getSession(), this.sessionService);
		}
		return this.httpSession;
	}
}
