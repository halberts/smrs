package com.jof.framework.session.listener;

import javax.servlet.http.HttpSession;

/**
 * @author WangXuzheng
 *
 */
public class HttpSessionCreatedEvent extends SessionCreationEvent{
	private static final long serialVersionUID = -5867480727372430377L;

	public HttpSessionCreatedEvent(HttpSession session) {
        super(session);
    }

    public HttpSession getSession() {
        return (HttpSession) getSource();
    }
}
