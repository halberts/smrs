package com.haier.openplatform.console.consumer;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;

import com.haier.openplatform.console.audit.AuditInfoCollector;
import com.haier.openplatform.console.audit.VIPBeanProcessing;
import com.haier.openplatform.console.converter.ExConfigureConvert;
import com.haier.openplatform.console.domain.VIPBean;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.hmc.receive.AbstractMessageListener;

public class ExConfTopicConsumer extends AbstractMessageListener{

	private static final Logger LOGGER = LoggerFactory.getLogger(ExConfTopicConsumer.class);
	private ExConfigureConvert exConfTopicConverter;
	
	@Override
	public void receiveMessage(Message msg) {
		
		try {
			VIPBean exb = exConfTopicConverter.fromMessage(msg);
			
			if (exb.isCheckingStatus()) {
				if (AuditInfoCollector.getAppNM().equalsIgnoreCase(exb.getAppName())){
					LOGGER.warn(HOPCONS.QUEUE_LOGGER_PREFIX + AuditInfoCollector.getAppNM() + HOPCONS.QUEUE_LOGGER_SEG
						+ exb.getCheckStatusQueueName() + HOPCONS.QUEUE_LOGGER_SEG+"Reported by"
						+Thread.currentThread().getId());
				}
				return;
			}
			LOGGER.info("-Consumer::::ExConfTopicConsumer-");
			LOGGER.info(exb.toString());
			VIPBeanProcessing.refreshVIPs(exb);
		} catch (MessageConversionException e) {
			LOGGER.error("Can't convert configure message", e);
		} catch (JMSException e) {
			LOGGER.error("JMS exception happened in ExConfTopicConsumer", e);
		} catch (Throwable e){
			LOGGER.error("Exception happened in ExConfTopicConsumer", e);
		}
	}

	public void setExConfTopicConverter(ExConfigureConvert exConfTopicConverter) {
		this.exConfTopicConverter = exConfTopicConverter;
	}

}
