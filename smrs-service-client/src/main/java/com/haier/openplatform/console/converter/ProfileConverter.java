package com.haier.openplatform.console.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.haier.openplatform.console.domain.Profile;
import com.thoughtworks.xstream.XStream;

/**
 * @author WangXuzheng
 * @see Profile
 * @since 1.4.10
 */
public class ProfileConverter implements MessageConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger( ProfileConverter.class );
	/**
	 * XML解析工具
	 */
	private XStream xstream;
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		xstream.processAnnotations(Profile.class);
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText(xstream.toXML(object));

		LOGGER.info("Profile Sending::::" + textMessage.toString());
		return textMessage;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		xstream.processAnnotations(Profile.class);
		TextMessage txtMsg = (TextMessage) message;
		Profile profile = (Profile) xstream.fromXML(txtMsg.getText());
		return profile;
	}
	public void setXstream(XStream xstream) {
		this.xstream = xstream;
	}
}
