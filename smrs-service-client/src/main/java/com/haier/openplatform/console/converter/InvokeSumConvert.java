package com.haier.openplatform.console.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.haier.openplatform.console.domain.InvokeSumBeans;
import com.thoughtworks.xstream.XStream;

public class InvokeSumConvert implements MessageConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvokeSumConvert.class);
	
	/**
	 * XML解析工具
	 */
	private XStream xstream;
	
	public InvokeSumBeans fromMessage(Message message) throws JMSException,
			MessageConversionException {
		xstream.processAnnotations(InvokeSumBeans.class);
		TextMessage txtMsg=(TextMessage)message;
		InvokeSumBeans isb=(InvokeSumBeans)xstream.fromXML(txtMsg.getText());
		return isb;
	}

	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		xstream.processAnnotations(InvokeSumBeans.class);
		TextMessage txtMsg = session.createTextMessage();
		txtMsg.setText(xstream.toXML((InvokeSumBeans)object));
		LOGGER.info("Sum Sending::::"+txtMsg.toString());
		return txtMsg;
	}
	
	public void setXstream(XStream xstream) {
		this.xstream = xstream;
	}	

}
