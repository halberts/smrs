package com.haier.openplatform.console.issue.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.haier.openplatform.console.domain.Profile;
import com.haier.openplatform.console.issue.service.ProfileService;
import com.haier.openplatform.hmc.receive.AbstractMessageListener;

/**
 * action监控消息接收器
 * @author WangXuzheng
 *
 */
public class ActionTraceConsumer extends AbstractMessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionTraceConsumer.class);
	private MessageConverter messageConverter;
	private ProfileService profileService;
	@Override
	public void receiveMessage(Message msg) {
		try {
			LOGGER.info("ActionTraceConsumer : " + msg.toString());
			Profile profile = (Profile )messageConverter.fromMessage(msg);
			LOGGER.info("ActionTraceConsumer profile: " + profile.getSendMessageTime());
			profileService.saveProfileBeanInfo(profile);
		} catch (MessageConversionException e) {
			LOGGER.error(e.toString());
		} catch (JMSException e) {
			LOGGER.error(e.toString());
		}
	}
	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
}
