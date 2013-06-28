package com.haier.openplatform.console.audit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.checkcode.action.CheckCodeAction;
import com.haier.openplatform.console.domain.Profile;
import com.haier.openplatform.console.domain.ProfileBean;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.session.service.impl.SessionServiceImpl;

public class ProfileTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileTest.class);
	@Test
	public void testToString() {
		Profile profile = new Profile();
		profile.setStartTime(System.currentTimeMillis());
		profile.setSystem("HOP");
		profile.setHopVersion(HOPCONS.HOP_RELEASE_VERSION);
		ProfileBean bean = new ProfileBean();
		bean.setClassName(CheckCodeAction.class.getName());
		bean.setMethodName("execute");
		bean.setExecutionTime(10L);
		bean.setTimes(1);
		profile.setProfileBean(bean);
		LOGGER.error(profile.toString());
	}

	@Test
	public void testProfileBeanToString(){
		ProfileBean actionBean = new ProfileBean();
		actionBean.setClassName(CheckCodeAction.class.getName());
		actionBean.setMethodName("execute");
		actionBean.setExecutionTime(10L);
		actionBean.setTimes(1);
		
		ProfileBean service1 = new ProfileBean();
		service1.setClassName(SessionServiceImpl.class.getName());
		service1.setMethodName("updateSession");
		service1.setExecutionTime(10L);
		service1.setTimes(1);
		actionBean.addChild(service1);
		
		ProfileBean service2 = new ProfileBean();
		service2.setClassName(ProfileConvertTest.class.getName());
		service2.setMethodName("get");
		service2.setExecutionTime(10L);
		service2.setTimes(1);
		actionBean.addChild(service2);
		
		ProfileBean dao1 = new ProfileBean();
		dao1.setClassName(ProfileTest.class.getName());
		dao1.setMethodName("getUserById");
		dao1.setExecutionTime(10L);
		dao1.setTimes(1);
		service1.addChild(dao1);
		
		ProfileBean dao2 = new ProfileBean();
		dao2.setClassName(ProfileTest.class.getName());
		dao2.setMethodName("getUserByName");
		dao2.setExecutionTime(10L);
		dao2.setTimes(1);
		service1.addChild(dao2);
		
		LOGGER.error(actionBean.toString());
	}
}
