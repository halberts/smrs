package com.jof.framework.session.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 會話銷毀事件
 * @author WangXuzheng
 *
 */
public abstract class SessionDestroyedEvent extends ApplicationEvent {
	private static final long serialVersionUID = 3029403982208547889L;
	public SessionDestroyedEvent(Object source) {
		super(source);
	}
	/**
     * @return 要銷毀的sessionID.
     */
    public abstract String getId();
}
