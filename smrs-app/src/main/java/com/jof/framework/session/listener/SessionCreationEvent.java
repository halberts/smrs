package com.jof.framework.session.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 會話創建事件
 * @author WangXuzheng
 *
 */
public abstract class SessionCreationEvent extends ApplicationEvent {
	private static final long serialVersionUID = -3006171357970967626L;

	public SessionCreationEvent(Object source) {
		super(source);
	}

}
