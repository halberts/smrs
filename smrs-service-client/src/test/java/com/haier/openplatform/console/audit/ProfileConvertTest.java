package com.haier.openplatform.console.audit;

import org.junit.BeforeClass;
import org.junit.Test;

import com.haier.openplatform.checkcode.action.CheckCodeAction;
import com.haier.openplatform.console.converter.ProfileConverter;
import com.haier.openplatform.console.domain.Profile;
import com.haier.openplatform.console.domain.ProfileBean;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.session.service.impl.SessionServiceImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ProfileConvertTest {
	private static ProfileConverter profileConvert;
	private static XStream xStream;
	@BeforeClass
	public static void beforeClass(){
		xStream = new XStream(new DomDriver());
		xStream.setMode(1001);
		
		profileConvert = new ProfileConverter();
		profileConvert.setXstream(xStream);
	}
	@Test
	public void testToMessage() {
		Profile profile = new Profile();
		profile.setStartTime(System.currentTimeMillis());
		profile.setEndTime(System.currentTimeMillis()+2000);
		profile.setType(1);
		profile.setHopVersion(HOPCONS.HOP_RELEASE_VERSION);
		profile.setSystem("HOP");
		ProfileBean actionBean = new ProfileBean();
		actionBean.setClassName(CheckCodeAction.class.getName());
		actionBean.setMethodName("execute");
		actionBean.setExecutionTime(10L);
		actionBean.setTimes(1);
		profile.setProfileBean(actionBean);
		
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
		xStream.processAnnotations(Profile.class);
		String text = xStream.toXML(profile);
		System.out.println(text);
	}

	@Test
	public void testFromMessage() {
	}

}
