package com.haier.openplatform.console.issue.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;

import com.haier.openplatform.console.converter.InvokeSumConvert;
import com.haier.openplatform.console.domain.InvokeSumBean;
import com.haier.openplatform.console.domain.InvokeSumBeans;
import com.haier.openplatform.console.domain.VIPBean;
import com.haier.openplatform.console.issue.service.IssueQueueService;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.hmc.domain.MessageModule;
import com.haier.openplatform.hmc.receive.AbstractMessageListener;
import com.haier.openplatform.hmc.sender.SendMsgService;

public class InvokeSumConsumer extends AbstractMessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvokeSumConsumer.class);
	private IssueQueueService issueQueueService;
	private SendMsgService exConfTopicSender;

	private InvokeSumConvert invokeSumConvert;

	@Override
	public void receiveMessage(Message msg) {
		try {
			InvokeSumBeans isbs = invokeSumConvert.fromMessage(msg);
			if (isbs.isCheckingStatus()) {
				InvokeSumBean isb = isbs.getSumBeans().get(0);
				LOGGER.warn(HOPCONS.QUEUE_LOGGER_PREFIX + isb.getAppNM() + HOPCONS.QUEUE_LOGGER_SEG + isb.getNodeNM()
						+ HOPCONS.QUEUE_LOGGER_SEG + isb.getNodeIP() + HOPCONS.QUEUE_LOGGER_SEG
						+ isbs.getCheckStatusQueueName()+HOPCONS.QUEUE_LOGGER_SEG+isb.getApiNM());

				VIPBean vipb = new VIPBean();
				vipb.setAppName(isb.getAppNM());
				vipb.setVipType(VIPBean.VIP);
				vipb.setCheckingStatus(true);
				vipb.setCheckStatusQueueName(MessageModule.QUEUE_NM.API_CALL_SUM_QUEUE.name());
				exConfTopicSender.sendMsg(vipb);
				return;
			}
			LOGGER.info("---Consumer::::Invoke Sum Beans---");
			LOGGER.info(isbs.toString());
			issueQueueService.saveInvokeSumQueue(isbs);
		} catch (MessageConversionException e) {
			LOGGER.error("消息转换错误",e);
		} catch (JMSException e) {
			LOGGER.error("JMS消息异常",e);
		} catch (Throwable e) {
			LOGGER.error("线程异常",e);
		}
	}

	public void setInvokeSumConvert(InvokeSumConvert invokeSumConvert) {
		this.invokeSumConvert = invokeSumConvert;
	}

	public void setIssueQueueService(IssueQueueService issueQueueService) {
		this.issueQueueService = issueQueueService;
	}

	public void setExConfTopicSender(SendMsgService exConfTopicSender) {
		this.exConfTopicSender = exConfTopicSender;
	}
}
