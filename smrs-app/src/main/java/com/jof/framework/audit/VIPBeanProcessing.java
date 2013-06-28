package com.jof.framework.audit;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.domain.VIPBean;
import com.haier.openplatform.console.util.HOPCONS;

public class VIPBeanProcessing {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( VIPBeanProcessing.class );
	
	public static void refreshVIPs(VIPBean vipb){
		
		// Only consume configuration changes used for current application. like: OMS, HMMS
		if (vipb==null || AuditInfoCollector.getAppNM()==null || !AuditInfoCollector.getAppNM().equalsIgnoreCase(vipb.getAppName())){
			return;
		}
		
		LOGGER.warn(HOPCONS.QUEUE_LOGGER_SEG + " UPDATING VIP-VIPTMP-OTLV " + HOPCONS.QUEUE_LOGGER_SEG);
		LOGGER.warn(vipb.toString());
		
		if (vipb.getVipType()==VIPBean.VIP){
			HOPServiceAuditHandler.setServicVIP(vipb.getServiceAPINM());
		}else if (vipb.getVipType()==VIPBean.VIPTemp){
			HOPServiceAuditHandler.setServiceTempVIP(vipb.getServiceAPINM());
			HOPServiceAuditHandler.setExpiredTime(System.currentTimeMillis()+vipb.getTimeInterval());
		}else if(vipb.getVipType()==VIPBean.SERVIC_LV){
			Map<String, String> slv=new HashMap<String, String>();
			vipb.getServiceLV(slv);
			InvokeSum.setOtLV(slv);
		}
	}
}
