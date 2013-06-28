package com.haier.openplatform.console.issue.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;

import com.haier.openplatform.console.converter.ExCaptureMsgConvert;
import com.haier.openplatform.console.domain.ExCaptureBean;
import com.haier.openplatform.console.domain.VIPBean;
import com.haier.openplatform.console.issue.service.IssueQueueService;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.hmc.domain.MessageModule;
import com.haier.openplatform.hmc.receive.AbstractMessageListener;
import com.haier.openplatform.hmc.sender.SendMsgService;

public class ExCaptureMsgConsumer extends AbstractMessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExCaptureMsgConsumer.class);
	private IssueQueueService issueQueueService;
	private SendMsgService exConfTopicSender;
	private ExCaptureMsgConvert exCaptureMsgCvt;

	@Override
	public void receiveMessage(Message msg) {
		try {
			ExCaptureBean exb = exCaptureMsgCvt.fromMessage(msg);
			if (exb.isCheckingStatus()) {
				LOGGER.warn(HOPCONS.QUEUE_LOGGER_PREFIX + exb.getAppName() + HOPCONS.QUEUE_LOGGER_SEG
						+ exb.getNodeName() + HOPCONS.QUEUE_LOGGER_SEG + exb.getNodeIP() + HOPCONS.QUEUE_LOGGER_SEG
						+ exb.getCheckStatusQueueName()+HOPCONS.QUEUE_LOGGER_SEG+exb.getServiceAPIName());

				VIPBean vipb = new VIPBean();
				vipb.setAppName(exb.getAppName());
				vipb.setVipType(VIPBean.VIP);
				vipb.setCheckingStatus(true);
				vipb.setCheckStatusQueueName(MessageModule.QUEUE_NM.EXCEPTION_CAPTURE_QUEUE.name());
				exConfTopicSender.sendMsg(vipb);
				return;
			}
			LOGGER.info("---Consumer::::ExCaptureBean---");
			LOGGER.info(exb.toString());
			exb.setServiceException(exb.getServiceException());
			exb.setInputValue(exb.getInputValue());
			exb.setInsight(exb.getInsight());
			issueQueueService.saveIssueQueue(exb);
		} catch (MessageConversionException e) {
			LOGGER.error("消息转换错误",e);
		} catch (JMSException e) {
			LOGGER.error("JMS消息异常",e);
		} catch (Throwable e) {
			LOGGER.error("线程异常",e);
		}
	}

	public void setExCaptureMsgCvt(ExCaptureMsgConvert exCaptureMsgCvt) {
		this.exCaptureMsgCvt = exCaptureMsgCvt;
	}

	public void setIssueQueueService(IssueQueueService issueQueueService) {
		this.issueQueueService = issueQueueService;
	}

	public void setExConfTopicSender(SendMsgService exConfTopicSender) {
		this.exConfTopicSender = exConfTopicSender;
	}
}
