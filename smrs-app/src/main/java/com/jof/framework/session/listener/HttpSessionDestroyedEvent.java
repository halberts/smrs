package com.jof.framework.session.listener;

import javax.servlet.http.HttpSession;

/**
 * @author WangXuzheng
 *
 */
public class HttpSessionDestroyedEvent extends SessionDestroyedEvent{
	private static final long serialVersionUID = -508866280557266007L;

	public HttpSessionDestroyedEvent(HttpSession session) {
		super(session);
	}

	public HttpSession getSession() {
		return (HttpSession) getSource();
	}

	@Override
	public String getId() {
		return getSession().getId();
	}
}
