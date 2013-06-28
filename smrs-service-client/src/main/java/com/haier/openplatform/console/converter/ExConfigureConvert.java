package com.haier.openplatform.console.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.haier.openplatform.console.domain.VIPBean;
import com.thoughtworks.xstream.XStream;

/**
 * @author mk
 * Covert exception message which used by sender and consumer
 */
public class ExConfigureConvert implements MessageConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger( ExConfigureConvert.class );
	/**
	 * XML解析工具
	 */
	private XStream xstream;

	public VIPBean fromMessage(Message message) throws JMSException, MessageConversionException {
		xstream.processAnnotations(VIPBean.class);
		TextMessage txtMsg = (TextMessage) message;
		VIPBean exb = (VIPBean) xstream.fromXML(txtMsg.getText());
		return exb;
	}

	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		xstream.processAnnotations(VIPBean.class);
		TextMessage txtMsg = session.createTextMessage();
		txtMsg.setText(xstream.toXML((VIPBean) object));

		LOGGER.info("VIPBean Sending::::" + txtMsg.toString());
		return txtMsg;
	}

	public void setXstream(XStream xstream) {
		this.xstream = xstream;
	}

}
