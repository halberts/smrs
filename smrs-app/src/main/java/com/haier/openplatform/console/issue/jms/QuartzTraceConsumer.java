package com.haier.openplatform.console.issue.jms;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.haier.openplatform.console.quartz.service.QuartzListenerService;

/**
 * 监控quartz执行信息的
 * @author WangXuzheng
 *
 */
public class QuartzTraceConsumer implements MessageListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzTraceConsumer.class);
	private MessageConverter messageConverter = new SimpleMessageConverter();
	private QuartzListenerService quartzListenerService;
	
	public void setQuartzListenerService(QuartzListenerService quartzListenerService) {
		this.quartzListenerService = quartzListenerService;
	}

	@Override
	public void onMessage(Message message) {
		try {
			@SuppressWarnings("unchecked")
			Map<String,String> dataMap = (Map<String,String>) messageConverter.fromMessage(message);
			quartzListenerService.execute(dataMap);
		} catch (MessageConversionException e) {
			LOGGER.error(e.toString());
		} catch (JMSException e) {
			LOGGER.error(e.toString());
		}
	}
}
