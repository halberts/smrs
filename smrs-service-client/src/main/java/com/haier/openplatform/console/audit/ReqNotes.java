/**
 * 
 */
package com.haier.openplatform.console.audit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.domain.InvokeSumBean;
import com.haier.openplatform.console.util.HOPCONS;


/**
 * thread local class, is holding
 * <ol>
 * <li>request input data in action layer</li>
 * <li>sql detail in dao layer</li>
 * <li>Error Info</li>
 * <li>useful path information from developer in service/dao layer</li>
 * </ol>
 * 
 * @author mk
 * 
 */
public class ReqNotes {

	public static final String H_YES="1";
	public static final String H_NO = "2";
	
	public static final void resetNotes() {
		SQLNotes.resetNotes();
		REQNotes.resetNotes();
		ERRNotes.resetNotes();
		InvokeNotes.resetNotes();
		NotestStatusNotes.resetNotes();
	}

	public static final void resetEXNotes() {
		SQLNotes.resetNotes();
		InvokeNotes.resetNotes();
		NotestStatusNotes.resetNotes();
	}
	
	public static final void putSQL(String inSQL) {
		if (null == inSQL || inSQL.isEmpty() || !getStatus(STATUS_KEY.IS_AUDITED) || !getStatus(STATUS_KEY.IS_INCLUDE_AUDITED)){
			return;
		}
		SQLNotes.getNotes().add(inSQL);
	}

	public static final void putMyTrace(String inTrace) {
		if (null == inTrace || inTrace.isEmpty() || !getStatus(STATUS_KEY.IS_AUDITED) || !getStatus(STATUS_KEY.IS_INCLUDE_AUDITED)){
			return;
		}
		SQLNotes.getNotes().add(inTrace);
	}

	public static final String getInsight() {
		if (SQLNotes.getNotes().isEmpty()){
			return HOPCONS.STR_NOT_DEFINED;
		}
		return SQLNotes.getNotes().toString();
	}

	public static final void putReqInfo(REQ_KEY rqKey, String inInfo) {
		if (null == inInfo || inInfo.isEmpty() || null == rqKey){
			return;
		}
		REQNotes.getNotes().put(rqKey.toString(), inInfo);
	}

	public static final String getReqInfo(REQ_KEY rqKey) {
		if (null == rqKey){
			return null;
		}
		return REQNotes.getNotes().get(rqKey.toString());
	}

	public static final void setStatus(STATUS_KEY k, String s) {
		NotestStatusNotes.getNotes().put(k.toString(), s);
	}

	public static final boolean getStatus(STATUS_KEY k) {
		String t=NotestStatusNotes.getNotes().get(k.toString());
		
		return !(t==null || H_NO.equals(t));
	}
	
	public static void reportErr(String inErr) {
		if (null == inErr || inErr.isEmpty() || !getStatus(STATUS_KEY.IS_AUDITED)){
			return;
		}
		ERRNotes.getNotes().add(inErr);
	}

	public static boolean isErrHappened() {
		return !ERRNotes.getNotes().isEmpty();
	}
	
	public static List<String> getErrs() {
		return ERRNotes.getNotes();
	}

	/** 
	 * @return
	 */
	public static boolean isErrHappened(String er) {
		if (null == er || er.isEmpty()){
			return false;
		}

		for (String s : ERRNotes.getNotes()) {
			if (er.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}

	public static void setInvokeNotes(String s, int j, long startTime) {
		InvokeNotes.setNotes(s, j, startTime);
	}
	
	public static int getJointHashCode() {
		return InvokeNotes.getJointHashCode();
	}

	public static boolean isInvokeEmpty() {
		return InvokeNotes.isEmpty();
	}

	public static void clearInvokeNotes() {
		InvokeNotes.clearNotes();
	}
	
	public static InvokeSumBean getInvokeNotes() {
		return InvokeNotes.getNotes();
	}

	public static enum REQ_KEY {
		HOP_ISSUETYPE, HOP_SERVICEAPI, HOP_APPNM, HOP_ACTION, HOP_NODENM, HOP_NODEIP, HOP_ERRCD, HOP_EXNM, HOP_BEAN, HOP_EX, HOP_INSIGHT, HOP_URL, HOP_EXCUTESEC, HOP_CURTHREAD;
	}
	
	public static enum STATUS_KEY{
		IS_EXCLUDED,IS_INCLUDE_AUDITED,IS_AUDITED;
	}

	/**
	 * thread safe class
	 * 
	 * @author mk
	 * 
	 */
	static final class SQLNotes {
		private static final ThreadLocal<LinkedList<String>> SQLHOPNotes = new InheritableThreadLocal<LinkedList<String>>() {
			@Override
			protected LinkedList<String> initialValue() {
				return new LinkedList<String>();
			}
		};

		static final LinkedList<String> getNotes() {
			return SQLHOPNotes.get();
		}

		static final void resetNotes() {
			SQLHOPNotes.remove();
		}
	}

	/**
	 * thread safe class
	 * 
	 * @author mk
	 * 
	 */
	static final class REQNotes {
		private static final ThreadLocal<Map<String, String>> REQHOPNotes = new InheritableThreadLocal<Map<String, String>>() {
			@Override
			protected Map<String, String> initialValue() {
				return new HashMap<String, String>();
			}
		};

		static final Map<String, String> getNotes() {
			return REQHOPNotes.get();
		}

		static final void resetNotes() {
			REQHOPNotes.remove();
		}
	}

	/**
	 * thread safe class
	 * 
	 * @author mk
	 * 
	 */
	static final class NotestStatusNotes {
		private static final ThreadLocal<Map<String, String>> StatusNotes = new InheritableThreadLocal<Map<String, String>>() {
			@Override
			protected Map<String, String> initialValue() {
				return new HashMap<String, String>();
			}
		};

		static final Map<String, String> getNotes() {
			return StatusNotes.get();
		}

		static final void resetNotes() {
			StatusNotes.remove();
		}
	}
	
	/**
	 * thread safe class
	 * 
	 * @author mk
	 * 
	 */
	static final class InvokeNotes {
		private static final ThreadLocal<InvokeSumBean> ISB = new InheritableThreadLocal<InvokeSumBean>() {
			@Override
			protected InvokeSumBean initialValue() {
				return new InvokeSumBean();
			}
		};
		
		static final InvokeSumBean getNotes() {
			return ISB.get();
		}

		static final void setNotes(String serviceAPINM, int jointHashCode, long startTime) {
			ISB.get().setApiNM(serviceAPINM);
			ISB.get().setAppNM(String.valueOf(jointHashCode));
			ISB.get().setStartTime(startTime);
		}
		
		static final int getJointHashCode() {
			return ISB.get().getAppNM()==null?0:Integer.valueOf(ISB.get().getAppNM()).intValue();
		}		

		static final boolean isEmpty() {
			return null == ISB.get().getApiNM();
		}

		static final void resetNotes() {
			ISB.remove();
		}
		
		static final void clearNotes(){
			ISB.get().setApiNM(null);
			ISB.get().setAppNM(null);
			ISB.get().setStartTime(0);
		}
	}

	/**
	 * thread safe class
	 * 
	 * @author mk
	 * 
	 */
	static final class ERRNotes {
		private static final ThreadLocal<LinkedList<String>> ERRHOPNotes = new InheritableThreadLocal<LinkedList<String>>() {
			@Override
			protected LinkedList<String> initialValue() {
				return new LinkedList<String>();
			}
		};
		
		private

		static final LinkedList<String> getNotes() {
			return ERRHOPNotes.get();
		}

		static final void resetNotes() {
			ERRHOPNotes.remove();
		}
	}
	
}
