package com.jof.framework.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.haier.openplatform.console.domain.ExCaptureBean;
import com.haier.openplatform.console.domain.InvokeSumBean;
import com.haier.openplatform.console.domain.InvokeSumBeans;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.hmc.domain.MessageModule;
import com.haier.openplatform.hmc.sender.SendMsgService;
import com.jof.framework.audit.ReqNotes.REQ_KEY;

/**
 * @author mk
 * @author WnagXuzheng
 */
public class InvokeSum implements InitializingBean{

	private static final Logger LOGGER = LoggerFactory.getLogger( InvokeSum.class );
	private static LinkedList<InvokeSumBean> invokeQueue = new LinkedList<InvokeSumBean>();

	// Table CONSOLE.BUSINESS_SERVICE.OT_LV
	private static Map<String, String> otLV = new HashMap<String, String>();
	private static final String DEFAULT_LV = "LV3";

	private static InvokeSum is = new InvokeSum();
	private static InvokeSumSending sumSender = is.new InvokeSumSending();

	private SendMsgService invokeSumSender;
	private SendMsgService exCaptureMsgSender;
	
	public static void add(String snm, long rst) {
		if (snm == null || rst == 0){
			return;
		}
			
		invokeQueue.add(new InvokeSumBean(snm, rst));
//		if (!sumSender.finished && !sumSender.isAlive())
//			sumSender.start();
	}

	public static void cleanup() {
		sumSender.finished = true;
	}

	public static long getOTLVMSec(String in) {

		OTLV ms = null;
		try {
			ms = OTLV.valueOf(in);
		} catch (Exception e) {
			LOGGER.error("Can not get OT LV from InvokeSum:", e);
		}
		return ms != null ? ms.value() : -1;
	}

	/**
	 * Match data in table CONSOLE.SERVICE_TIMEOUT_LV<br>
	 * LV1(50):Million second
	 * @author mk
	 *
	 */
	enum OTLV {
		LV1(50), LV2(100), LV3(500), LV4(1000), LV5(2000), LV6(4000), LV7(10000), LV8(30000), LV9(60000), LV10(300000), LV11(600000), LV00(0);
		private long otLevel;

		OTLV(long otlv) {
			this.otLevel = otlv;
		}

		public long value() {
			return otLevel;
		}
	}

	/**
	 * Send call sum data to console
	 * @author mk
	 *
	 */
	class InvokeSumSending extends Thread {

		volatile boolean finished = false;
		// 15000 msec=15 s
		private static final long readInterval = 60000;
		private long checkInterval=0;

		public void run() {

			LinkedList<InvokeSumBean> tmpLink = null;
			while (!finished) {
				try {
					checkInterval++;
					if (checkInterval == 30) {
						checkExceptionQueueStatus();
						checkInterval = 0;
					}
					
					if (!invokeQueue.isEmpty()) {
						tmpLink = InvokeSum.invokeQueue;
						InvokeSum.invokeQueue = new LinkedList<InvokeSumBean>();
						sendToConsole(tmpLink);
					}
				} finally {
					try {
						Thread.sleep(readInterval);
					} catch (InterruptedException e) {
						LOGGER.error("InvokeSum thread can not sleep",e);
					}
				}
			}
		}

		private void checkExceptionQueueStatus(){
			ExCaptureBean ecb = new ExCaptureBean();
			ecb.setCheckingStatus(true);
			ecb.setCheckStatusQueueName(MessageModule.QUEUE_NM.EXCEPTION_CAPTURE_QUEUE.name());
			ecb.setServiceAPIName(JDBCConnectionStatusCollector.getEnvType()+HOPCONS.HOP_RELEASE_VERSION);
			ecb.setAppName(AuditInfoCollector.getAppNM());
			ecb.setNodeName(AuditInfoCollector.getServernm());
			ecb.setNodeIP(AuditInfoCollector.getServerip());
			exCaptureMsgSender.sendMsg(ecb);
			InvokeSumBean tmpISB = new InvokeSumBean();
			tmpISB.setApiNM(JDBCConnectionStatusCollector.getEnvType()+HOPCONS.HOP_RELEASE_VERSION);
			tmpISB.setCallNum();
			tmpISB.setAppNM(AuditInfoCollector.getAppNM());
			tmpISB.setNodeNM(AuditInfoCollector.getServernm());
			tmpISB.setNodeIP(AuditInfoCollector.getServerip());
			InvokeSumBeans tmpISBS = new InvokeSumBeans(tmpISB);
			tmpISBS.setCheckingStatus(true);
			tmpISBS.setCheckStatusQueueName(MessageModule.QUEUE_NM.API_CALL_SUM_QUEUE.name());
			invokeSumSender.sendMsg(tmpISBS);
		}
		
		private void sendToConsole(LinkedList<InvokeSumBean> inl) {

			String tmplv = null;
			long tmplvSec = -1;
			Map<String, InvokeSumBean> tm = new HashMap<String, InvokeSumBean>();
			InvokeSumBean tmpISB = null;
			long currentThreadNumber = Thread.activeCount();
			SimpleDateFormat threadSafeDateFormat=(SimpleDateFormat)HOPCONS.SIMPLE_DATE_FORMAT.clone();
			String currentTime = threadSafeDateFormat.format(new Date(System.currentTimeMillis()));

			for (Iterator<InvokeSumBean> iterator = inl.iterator(); iterator.hasNext();) {
				InvokeSumBean isb = (InvokeSumBean) iterator.next();
				if (isb.getApiNM() != null) {
					tmplv = otLV.get(isb.getApiNM());
					tmplvSec = getOTLVMSec(tmplv == null ? DEFAULT_LV : tmplv.toUpperCase());
					if (tmplvSec < isb.getResponseTime()) {
						ExCaptureBean ecb = new ExCaptureBean();
						ecb.setServiceAPIName(isb.getApiNM());
						ecb.setAppName(AuditInfoCollector.getAppNM());
						ecb.setActionName(ReqNotes.getReqInfo(REQ_KEY.HOP_ACTION));
						ecb.setNodeName(AuditInfoCollector.getServernm());
						ecb.setNodeIP(AuditInfoCollector.getServerip());
						ecb.setIssueTypeName(HOPCONS.AUDIT_OT);
						ecb.setExcuteTime(String.valueOf(isb.getResponseTime()));
						ecb.setCurrentThreadNumber(String.valueOf(currentThreadNumber));
						ecb.setGmtCreate(currentTime);
						exCaptureMsgSender.sendMsg(ecb);
					}
				}
				if (tm.containsKey(isb.getApiNM())) {
					tm.get(isb.getApiNM()).setResponseTime(isb.getResponseTime());
					tm.get(isb.getApiNM()).setCallNum();
					tm.get(isb.getApiNM()).setGmtCreate(currentTime);
				} else {
					tmpISB = new InvokeSumBean();
					tmpISB.setApiNM(isb.getApiNM());
					tmpISB.setCallNum();
					tmpISB.setResponseTime(isb.getResponseTime());
					tmpISB.setAppNM(AuditInfoCollector.getAppNM());
					tmpISB.setNodeNM(AuditInfoCollector.getServernm());
					tmpISB.setNodeIP(AuditInfoCollector.getServerip());
					tmpISB.setGmtCreate(currentTime);
					tm.put(isb.getApiNM(), tmpISB);
				}
			}
			InvokeSumBeans isbs = new InvokeSumBeans(new ArrayList<InvokeSumBean>(tm.values()));
			// TODOMK: Using same sender here as exception capture
			invokeSumSender.sendMsg(isbs);
		}

	}

	public static void setOtLV(Map<String, String> otLV) {
		InvokeSum.otLV = otLV;
	}

	@Override
	public synchronized void afterPropertiesSet() throws Exception {
		if(!sumSender.isAlive()){
			sumSender.start();
		}
	}

	public void setInvokeSumSender(SendMsgService invokeSumSender) {
		this.invokeSumSender = invokeSumSender;
	}

	public void setExCaptureMsgSender(SendMsgService exCaptureMsgSender) {
		this.exCaptureMsgSender = exCaptureMsgSender;
	}
}
