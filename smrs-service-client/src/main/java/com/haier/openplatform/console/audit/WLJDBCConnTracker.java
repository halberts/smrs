package com.haier.openplatform.console.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.util.HOPCONS;

public class WLJDBCConnTracker implements JDBCConnectionTracker {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( WLJDBCConnTracker.class );
	
	private static final String RUN_TIME_SERVICE="com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
	private static final String RUN_TIME_JDBC_POOL="Type=JDBCDataSourceRuntime";
	private static final String MBEAN_SERVER_ADDR="java:comp/env/jmx/runtime";
	private static final String OBJECT_NM="ObjectName::";
	private static final String OBJECT_VL=" Value::";

	private static InitialContext ctx = null;
	private static MBeanServer server = null;
	private static final ObjectName service;  
	private static List<ObjectInstance> jdbcPools=new ArrayList<ObjectInstance>();
	private String rootReason=HOPCONS.TO_BE_DEFINED;
	private String detailReason=HOPCONS.TO_BE_DEFINED;
	
    // Initializing the object name for DomainRuntimeServiceMBean  
    // so it can be used throughout the class.  
    static {  
        try {  
            service = new ObjectName(RUN_TIME_SERVICE); 
        } catch (MalformedObjectNameException e) {  
            throw new AssertionError(e.getMessage());  
        }  
    } 	
	
	public WLJDBCConnTracker(){
		if (!JDBCConnectionPoolConf.isEnableJDBCConnectionTracker()){
			return;
		}
		
		try {
			ctx = new InitialContext();
			server = (MBeanServer)ctx.lookup(MBEAN_SERVER_ADDR);
		} catch (NamingException e) {
			LOGGER.error("JDBC Monitor: Can not get mbean server address",e);
		}	
	}

    /*  
    * Print an array of ServerRuntimeMBeans. 
    * This MBean is the root of the runtime MBean hierarchy, and 
    * each server in the domain hosts its own instance. 
    */  
    public static ObjectName[] getServerRuntimes() throws Exception { 
    	Object a=server.getAttribute(service, "ServerRuntime");
    	return getObjectNmArray(a);
    } 
    
    private static ObjectName[] getObjectNmArray(Object o){
    	ObjectName[] re=new ObjectName[1];
    	if (o instanceof ObjectName){
    		re[0]=(ObjectName) o;
    	}
    	
    	if (o instanceof ObjectName[]){
    		re=(ObjectName[]) o;
    	}
    	
    	return re;
    }
	
	/**
	 * Function is called regularly to refresh jdbc pool instances <br>
	 * All of jdbc pools reference is cleared to avoid dead jdbc configration
	 */
	public void queryJDBCPoolLists(){
		if (server==null){
			return;
		}
		
		Set<ObjectInstance> mbeans=server.queryMBeans(null, null);
		if (mbeans==null || mbeans.size()<1){
			return;
		}

		List<ObjectInstance> jdbcP=new ArrayList<ObjectInstance>();
		
		try {
			for (ObjectInstance objectInstance : mbeans) {
				if (objectInstance.getObjectName().getCanonicalName().indexOf(RUN_TIME_JDBC_POOL)>-1){
					jdbcP.add(objectInstance);
				}
			}
			
			LOGGER.info(HOPCONS.QUEUE_LOGGER_SEG+"Refreshing JDBC Pool, size::"+jdbcP.size());
			jdbcPools.clear();
			jdbcPools.addAll(jdbcP);
		} catch (Exception e) {
			LOGGER.error("JDBC Monitor: Can not refresh JDBC pool information",e);
		}
	}

	/**
	 * Function is called regularly to refresh jdbc pool status <br>
	 */
	public void checkJDBCPoolStatus() {
		if (jdbcPools.size() < 1){
			queryJDBCPoolLists();
			if (jdbcPools.size() < 1){
				return;
			}
		}
		
		LOGGER.warn(JDBCConnectionPoolConf.getFailuresToReconnectCount()+HOPCONS.QUEUE_LOGGER_SEG
				+JDBCConnectionPoolConf.getLeakedConnectionCount()+HOPCONS.QUEUE_LOGGER_SEG
				+JDBCConnectionPoolConf.getWaitingForConnectionCurrentCount()+HOPCONS.QUEUE_LOGGER_SEG
				+JDBCConnectionPoolConf.getNotificationEmailAddress()+HOPCONS.QUEUE_LOGGER_SEG
				+JDBCConnectionPoolConf.getNotificationPhoneNum());
		try {
			SimpleDateFormat threadSafeDateFormat=(SimpleDateFormat)HOPCONS.SIMPLE_DATE_FORMAT.clone();
			StringBuilder sb=new StringBuilder();
			StringBuilder smssb=new StringBuilder();
			boolean errHappened=false;
			for (ObjectInstance objectIns : jdbcPools) {
				boolean errorHappened=false;
				AttributeList als = server.getAttributes(
						objectIns.getObjectName(),
						JDBCConnectionPoolConf.jdbcAttributes);
				sb.append(HOPCONS.QUEUE_LOGGER_SEG+HOPCONS.QUEUE_LOGGER_SEG
						+objectIns.getObjectName()+HOPCONS.QUEUE_LOGGER_SEG+HOPCONS.QUEUE_LOGGER_SEG);
				sb.append(HOPCONS.LOGGER_RETURN);
				smssb.append(objectIns.getObjectName());
				smssb.append(HOPCONS.LOGGER_RETURN);
				for (Iterator<Object> iterator = als.iterator(); iterator.hasNext();) {
					Attribute object = (Attribute) iterator.next();
					if (isProblemHappening(object,smssb)){
						errorHappened=true;
						sb.append(HOPCONS.QUEUE_LOGGER_WARNING+OBJECT_NM+object.getName()+OBJECT_VL+object.getValue());
					}else
						sb.append(HOPCONS.QUEUE_LOGGER_SEG+OBJECT_NM+object.getName()+OBJECT_VL+object.getValue());
					sb.append(HOPCONS.LOGGER_RETURN);
				}
				if (errorHappened){
					sb.append(HOPCONS.QUEUE_LOGGER_SEG+threadSafeDateFormat.format(new Date(System.currentTimeMillis())));
					sb.append(HOPCONS.LOGGER_RETURN);
					smssb.append(threadSafeDateFormat.format(new Date(System.currentTimeMillis())));
					smssb.append(HOPCONS.LOGGER_RETURN);
					errHappened=true;
				}
			}
			if (errHappened){
				sb.insert(0,AuditInfoCollector.printServerSign());
				smssb.insert(0, AuditInfoCollector.printServerSign());
				LOGGER.error(sb.toString());
				detailReason=sb.toString();
				rootReason=smssb.toString();
			}else{
				rootReason=HOPCONS.TO_BE_DEFINED;
				detailReason=HOPCONS.TO_BE_DEFINED;
			}
		} catch (InstanceNotFoundException e) {
			LOGGER.error("CAN NOT PROPERLY CHECK JDBC STATUS",e);
		} catch (ReflectionException e) {
			LOGGER.error("CAN NOT PROPERLY CHECK JDBC STATUS",e);
		}
	}
	
	public String getRootReason() {
		return rootReason;
	}

	public String getDetailReason() {
		return detailReason;
	}

	private boolean isProblemHappening(Attribute attri,StringBuilder sb){
		
		if (attri.getName()==null || attri.getValue()==null)
			return false;
		
		if (JDBCConnectionPoolConf.failureRC.equals(attri.getName()) 
			&& (Integer)(attri.getValue()) > JDBCConnectionPoolConf.getFailuresToReconnectCount()){
			sb.append("数据库已经离线!!");
			return true;
		}
		
		if (JDBCConnectionPoolConf.leakedCC.equals(attri.getName()) 
			&& (Integer)(attri.getValue()) > JDBCConnectionPoolConf.getLeakedConnectionCount()){
			sb.append("有5个以上的jdbc池链接无法释放!!");
			return true;
		}
		
		if (JDBCConnectionPoolConf.waitingCC.equals(attri.getName()) 
			&& (Integer)(attri.getValue()) > JDBCConnectionPoolConf.getWaitingForConnectionCurrentCount()){
			sb.append("有大量的jdbc连接获取等待!!");
			return true;
		}
		
		return false;
	}


}
