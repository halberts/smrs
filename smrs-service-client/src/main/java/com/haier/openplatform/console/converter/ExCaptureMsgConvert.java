package com.haier.openplatform.console.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.haier.openplatform.console.domain.ExCaptureBean;
import com.thoughtworks.xstream.XStream;

/**
 * @author mk
 * Covert exception message which used by sender and consumer
 */
public class ExCaptureMsgConvert implements MessageConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger( ExCaptureMsgConvert.class );
	/**
	 * XML解析工具
	 */
	private XStream xstream;

	public ExCaptureBean fromMessage(Message message) throws JMSException, MessageConversionException {
		xstream.processAnnotations(ExCaptureBean.class);
		TextMessage txtMsg = (TextMessage) message;
		ExCaptureBean exb = (ExCaptureBean) xstream.fromXML(txtMsg.getText());
		return exb;
	}

	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		xstream.processAnnotations(ExCaptureBean.class);
		TextMessage txtMsg = session.createTextMessage();
		txtMsg.setText(xstream.toXML((ExCaptureBean) object));
		LOGGER.info("Exception Sending::::" + txtMsg.toString());
		return txtMsg;
	}

	public void setXstream(XStream xstream) {
		this.xstream = xstream;
	}

}
