package com.haier.openplatform.console.audit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.audit.ReqNotes.REQ_KEY;
import com.haier.openplatform.console.domain.ExCaptureBean;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.hmc.sender.SendMsgService;
import com.haier.openplatform.util.HOPConstant;
import com.haier.openplatform.util.HOPException;

/**
 * Spring AspectJ class handling exception and data statics
 * @author mk
 *
 */
public class HOPServiceAuditHandler {

   	private static final Logger LOGGER = LoggerFactory.getLogger( HOPServiceAuditHandler.class ); 
   	
	// Table CONSOLE.BUSINESS_SERVICE.SERVICE_VIP
	private static List<String> servicVIP = new ArrayList<String>();
	// Table CONSOLE.RELEASE_TRACE_CONF.TRACE_SERVICE
	private static List<String> serviceTempVIP = new ArrayList<String>();
	
	private static long expiredTime=0;

	private SendMsgService excaptureSender;
	

	public void beforeExecute(JoinPoint thisJoinPoint) {
		
		String serviceNM = AuditInfoCollector.getClassNm(thisJoinPoint.getTarget(), HOPCONS.PKG_PREFIX) + "."
				+ thisJoinPoint.getSignature().getName();
		LOGGER.debug("Spring AOP ..... Touching " + HOPCONS.QUEUE_LOGGER_SEG + serviceNM);
		
		ReqNotes.setStatus(ReqNotes.STATUS_KEY.IS_AUDITED, ReqNotes.H_YES);
		
		if (isExcludedService(serviceNM)){
			ReqNotes.setStatus(ReqNotes.STATUS_KEY.IS_EXCLUDED, ReqNotes.H_YES);
			return;
		} else{
			ReqNotes.setStatus(ReqNotes.STATUS_KEY.IS_EXCLUDED, ReqNotes.H_NO);
		}
		
		// Only record first including service into thread local
		if (ReqNotes.isInvokeEmpty()) {
			ReqNotes.ERRNotes.resetNotes();
			ReqNotes.SQLNotes.resetNotes();
			ReqNotes.setInvokeNotes(serviceNM, thisJoinPoint.hashCode() ,System.currentTimeMillis());
			ReqNotes.setStatus(ReqNotes.STATUS_KEY.IS_INCLUDE_AUDITED, ReqNotes.H_YES);
			LOGGER.debug("Spring AOP  ..... Recording " + HOPCONS.QUEUE_LOGGER_SEG + serviceNM);
		}
	}

	public void afterExecute(JoinPoint thisJoinPoint) {

		if (!ReqNotes.isInvokeEmpty()) {
			String sNM = AuditInfoCollector.getClassNm(thisJoinPoint.getTarget(), HOPCONS.PKG_PREFIX) + "."
					+ thisJoinPoint.getSignature().getName();
			long bf = ReqNotes.getInvokeNotes().getStartTime();
			long af = System.currentTimeMillis();
			// To avoid any children service call statistics
			
			if (isExcludedService(sNM)){
				if (!ReqNotes.getStatus(ReqNotes.STATUS_KEY.IS_INCLUDE_AUDITED)){
					ReqNotes.resetEXNotes();
				}
				return;
			}
			
			if (!sNM.equals(ReqNotes.getInvokeNotes().getApiNM())){
				return;
			}else {
				if (thisJoinPoint.hashCode()!=ReqNotes.getJointHashCode()){
					return;
				}
				try {
					InvokeSum.add(ReqNotes.getInvokeNotes().getApiNM(), af - bf);
				} catch (Exception e) {
					LOGGER.error("Can not set value into memory summary queue", e);
				} finally {
					ReqNotes.clearInvokeNotes();
					ReqNotes.resetEXNotes();
				}
			}
		}
	}

	public void afterThrowing(JoinPoint thisJoinPoint, Exception ex) throws Exception {

		String sNM = AuditInfoCollector.getClassNm(thisJoinPoint.getTarget(), HOPCONS.PKG_PREFIX) + "."
				+ thisJoinPoint.getSignature().getName();

		if (isExcludedService(sNM)){
			return;
		}
		
		if (!sNM.equals(ReqNotes.getInvokeNotes().getApiNM())){
			return;
		}

		if (thisJoinPoint.hashCode()!=ReqNotes.getJointHashCode()){
			return;
		}
		
		boolean isVIP = isVIP(sNM);
		boolean isTmpVIP = isTmpVIP(sNM);

		if (ex instanceof HOPException) {
			if (!isVIP && !isTmpVIP){
				return;
			}
		}

		long bf = ReqNotes.getInvokeNotes().getStartTime();
		long af = System.currentTimeMillis();
		ExCaptureBean ecb = new ExCaptureBean();
		ecb.setServiceAPIName(sNM);
		ecb.setAppName(AuditInfoCollector.getAppNM());
		ecb.setActionName(ReqNotes.getReqInfo(REQ_KEY.HOP_ACTION));
		ecb.setNodeName(AuditInfoCollector.getServernm());
		ecb.setNodeIP(AuditInfoCollector.getServerip());
		ecb.setInputValue(ReqNotes.getReqInfo(REQ_KEY.HOP_BEAN));
		ecb.setInsight(ReqNotes.getInsight());
		SimpleDateFormat threadSafeDateFormat=(SimpleDateFormat)HOPCONS.SIMPLE_DATE_FORMAT.clone();
		ecb.setGmtCreate(threadSafeDateFormat.format(new Date(System.currentTimeMillis())));
		ecb.setExcuteTime(String.valueOf(af - bf));

		handleException(ex, ecb, isTmpVIP);
		excaptureSender.sendMsg(ecb);
	}

	public void handleException(Throwable ex, ExCaptureBean inm, boolean isTmpVIP) {

		String exNM = null;
		String errNM = null;
		String exDetail = null;
		String issueTypeNM = null;
		StringWriter sw = new StringWriter();

		if (ex instanceof HOPException) {
			if (isTmpVIP){
				issueTypeNM = HOPCONS.AUDIT_BETA;
			}else{
				issueTypeNM = HOPCONS.AUDIT_EX_ERR;
			}
			if (((HOPException) ex).getHopErrMsg() != null) {
				HOPConstant.putLocalInfo(HOPConstant.ACTION_ERROR, ((HOPException) ex).getHopErrMsg());
				errNM = ((HOPException) ex).getHopErrMsg();
				errNM = errNM.substring(0, errNM.indexOf("::"));
			} else {
				errNM = HOPCONS.STR_NOT_DEFINED;
			}
			if (ex.getCause() != null) {
				exNM = ex.getCause().getClass().getSimpleName();
				ex.getCause().printStackTrace(new PrintWriter(sw));
				exDetail = sw.toString();
			} else {
				exNM = HOPCONS.STR_NOT_DEFINED;
				exDetail = HOPCONS.STR_NOT_DEFINED;
			}
		} else if (ex instanceof RuntimeException) {
			issueTypeNM = HOPCONS.AUDIT_EX;
			exNM = ex.getClass().getSimpleName();
			ex.printStackTrace(new PrintWriter(sw));
			exDetail = sw.toString();
		} else if (ex instanceof Throwable) {
			issueTypeNM = HOPCONS.AUDIT_EX;
			exNM = ex.getClass().getSimpleName();
			ex.printStackTrace(new PrintWriter(sw));
			exDetail = sw.toString();
		}
		inm.setErrorCD(errNM);
		inm.setExceptionNM(exNM);
		inm.setServiceException(exDetail);
		inm.setIssueTypeName(issueTypeNM);
	}

	private boolean isVIP(String serviceNM) {

		if (serviceNM != null && servicVIP != null && servicVIP.size() > 0) {
			return servicVIP.contains(serviceNM);
		} else {
			return false;
		}
	}

	private boolean isTmpVIP(String serviceNM) {

		//Beta trace is only validate in period
		if (System.currentTimeMillis() > expiredTime) {
			expiredTime = 0;
			serviceTempVIP = null;
			return false;
		}

		if (serviceNM != null && serviceTempVIP != null && serviceTempVIP.size() > 0) {
			return serviceTempVIP.contains(serviceNM);
		} else {
			return false;
		}
	}
	
	private boolean isExcludedService(String serviceNM) {
		if (serviceNM != null) {
			for (int i = 0; i < HOPCONS.EXCLUDE_SERVICE.length; i++) {
				if (serviceNM.equals(HOPCONS.EXCLUDE_SERVICE[i])){
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public static void setServicVIP(List<String> servicVIP) {
		HOPServiceAuditHandler.servicVIP = servicVIP;
	}

	public static void setServiceTempVIP(List<String> serviceTempVIP) {
		HOPServiceAuditHandler.serviceTempVIP = serviceTempVIP;
	}

	public static void setExpiredTime(long expiredTime) {
		HOPServiceAuditHandler.expiredTime = expiredTime;
	}

	public void setExcaptureSender(SendMsgService excaptureSender) {
		this.excaptureSender = excaptureSender;
	}

}
