package com.haier.openplatform.console.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.haier.openplatform.console.domain.JDBCConnectionBean;
import com.thoughtworks.xstream.XStream;

/**
 * @author mk
 * Covert JDBCConnectionBean which used by sender and consumer
 */
public class JdbcConnTrackerConverter implements MessageConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger( JdbcConnTrackerConverter.class );
	/**
	 * XML解析工具
	 */
	private XStream xstream;

	public JDBCConnectionBean fromMessage(Message message) throws JMSException, MessageConversionException {
		xstream.processAnnotations(JDBCConnectionBean.class);
		TextMessage txtMsg = (TextMessage) message;
		JDBCConnectionBean jdbcc = (JDBCConnectionBean) xstream.fromXML(txtMsg.getText());
		return jdbcc;
	}

	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		xstream.processAnnotations(JDBCConnectionBean.class);
		TextMessage txtMsg = session.createTextMessage();
		txtMsg.setText(xstream.toXML((JDBCConnectionBean) object));

		LOGGER.info("JDBCConnectionBean Sending::::" + txtMsg.toString());
		return txtMsg;
	}

	public void setXstream(XStream xstream) {
		this.xstream = xstream;
	}

}
