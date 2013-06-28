/**
 * 
 */
package com.haier.openplatform.console.issue.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.haier.openplatform.console.domain.ExCaptureBean;
import com.haier.openplatform.console.domain.InvokeSumBean;
import com.haier.openplatform.console.domain.InvokeSumBeans;
import com.haier.openplatform.console.issue.dao.QueueDAO;
import com.haier.openplatform.console.issue.domain.HopIssueDetailEx;
import com.haier.openplatform.console.issue.domain.HopIssueQueue;
import com.haier.openplatform.console.issue.domain.ServiceApiStatusQueue;
import com.haier.openplatform.console.issue.service.IssueQueueService;
import com.haier.openplatform.console.util.HOPCONS;

/**
 * @author mk
 * 
 */
public class IssueQueueServiceImpl implements IssueQueueService {
	private static Log log = LogFactory.getLog(IssueQueueServiceImpl.class);

	private QueueDAO queueDAO;

	@Override
	public void saveInvokeSumQueue(InvokeSumBeans isbs) {
		List<InvokeSumBean> isbList = isbs.getSumBeans();

		SimpleDateFormat threadSafeDateFormat = (SimpleDateFormat) HOPCONS.SIMPLE_DATE_FORMAT
				.clone();
		for (InvokeSumBean invokeSumBean : isbList) {		
			ServiceApiStatusQueue sasq = new ServiceApiStatusQueue();
			sasq.setAppName(invokeSumBean.getAppNM());
			sasq.setCallNum(invokeSumBean.getCallNum());
			try {
				sasq.setGmtCreate(threadSafeDateFormat.parse(invokeSumBean
						.getGmtCreate()));
				sasq.setGmtModified(threadSafeDateFormat.parse(invokeSumBean
						.getGmtCreate()));
			} catch (ParseException e) {
				log.error("设置调用统计：时间发生异常::" + invokeSumBean.getGmtCreate()
						+ " From::" + invokeSumBean.getAppNM() + " NodeName::"
						+ invokeSumBean.getNodeIP(), e);
			} catch (Exception e) {
				log.error("设置调用统计：时间发生异常::" + invokeSumBean.getGmtCreate()
						+ " From::" + invokeSumBean.getAppNM() + " NodeName::"
						+ invokeSumBean.getNodeIP(), e);
			}
			sasq.setNodeIp(invokeSumBean.getNodeIP());
			sasq.setNodeName(invokeSumBean.getNodeNM());
			sasq.setResponseTimeSum(invokeSumBean.getResponseTime());
			sasq.setServiceApiName(invokeSumBean.getApiNM());
			queueDAO.saveServiceQueue(sasq);
		}
	}

	@Override
	public HopIssueQueue saveIssueQueue(ExCaptureBean ecb) {
		HopIssueQueue hiq = new HopIssueQueue();

		hiq.setActionName(ecb.getActionName());
		hiq.setAppName(ecb.getAppName());
		hiq.setCurrentThreadNum(ecb.getCurrentThreadNumber() == null ? 0 : Long
				.valueOf(ecb.getCurrentThreadNumber()));
		hiq.setErrorCode(ecb.getErrorCD());
		hiq.setExecuteTime(ecb.getExcuteTime() == null ? 0 : Long.valueOf(ecb
				.getExcuteTime()));
		hiq.setExceptionName(ecb.getExceptionNM());

		SimpleDateFormat threadSafeDateFormat = (SimpleDateFormat) HOPCONS.SIMPLE_DATE_FORMAT
				.clone();
		try {
			hiq.setGmtCreate(threadSafeDateFormat.parse(ecb.getGmtCreate()));
			hiq.setGmtModified(threadSafeDateFormat.parse(ecb.getGmtCreate()));
		} catch (ParseException e) {
			log.error("设置错误异常统计：时间发生异常::" + ecb.getGmtCreate() + " From::"
					+ ecb.getAppName() + " NodeName::" + ecb.getNodeIP()
					+ " NodeType::" + ecb.getIssueTypeName(), e);
		} catch (Exception e) {
			log.error("设置错误异常统计：时间发生异常::" + ecb.getGmtCreate() + " From::"
					+ ecb.getAppName() + " NodeName::" + ecb.getNodeIP()
					+ " NodeType::" + ecb.getIssueTypeName(), e);
		}
		hiq.setIssueTypeName(ecb.getIssueTypeName());
		hiq.setNodeIp(ecb.getNodeIP());
		hiq.setNodeName(ecb.getNodeName());
		hiq.setServiceApiName(ecb.getServiceAPIName());
		queueDAO.saveIssueQueue(hiq);

		if (!HOPCONS.AUDIT_OT.equals(ecb.getIssueTypeName())) {
			HopIssueDetailEx hex = new HopIssueDetailEx();
			hex.setId(hiq.getId());
			hex.setInsight(ecb.getInsight());
			hex.setInValue(ecb.getInputValue());
			hex.setServiceEx(ecb.getServiceException());
			queueDAO.saveHopIssueDetailEx(hex);
		}
		return hiq;
	}

	public QueueDAO getQueueDAO() {
		return queueDAO;
	}

	public void setQueueDAO(QueueDAO queueDAO) {
		this.queueDAO = queueDAO;
	}

	@Override
	public void saveHopIssueDetailEx(HopIssueDetailEx ex) {
		queueDAO.saveHopIssueDetailEx(ex);

	}

}
