package test.com.smrs;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.haier.openplatform.security.LoginContext;
import com.haier.openplatform.security.LoginContextHolder;
import com.haier.openplatform.test.dbunit.BaseHopTestCase;
import com.smrs.util.CacheNames;
import com.smrs.util.SysconfigInitListener;

/**
 * @author WangXuzheng
 * @author Vilight_Wu
 * 
 */
//@ContextConfiguration(locations = { "classpath*:/console/audit/console-audit.xml","classpath*:/hmc/hornetq/hmc-jms-hornetq.xml","classpath*:/console/jms/console-jms-*.xml",
//		"classpath:/spring/spring-datasource.xml","classpath:/spring/spring-transaction.xml", "classpath:/spring/spring-common.xml","classpath:/spring/spring-jms.xml","classpath:/spring/cache/spring-*.xml",
//		"classpath:/spring/security/spring-user.xml", "classpath:/spring/security/spring-resource.xml","classpath:/spring/quartz/spring-quartz.xml","classpath:/spring/quartz/spring-quartz-biz.xml",
//		"classpath:/spring/basic/spring-department.xml", "classpath:/spring/security/spring-role.xml", "classpath:/spring/security/spring-user.xml",  
//		"classpath:/spring/security/spring-group.xml","classpath:/spring/spring-jmx.xml","classpath:/spring/spring-monitor.xml",
//		"classpath:/spring/issue/spring-issue.xml","classpath:/spring/jira/spring-jira.xml","classpath:/spring/sonar/spring-sonar.xml"})


@ContextConfiguration(locations = { 
		"classpath:/spring/cache/spring-*.xml",
		"classpath:/spring/security/spring-*.xml", 
		"classpath:/spring/spring-*.xml"
		})


@Ignore
@Transactional
public class BaseTestCase extends BaseHopTestCase {
	@Resource
	protected CacheManager spyMemcachedCacheManager;
	protected Cache userCache;
	protected Cache roleCache;
	protected Cache resourceCache;
	protected Cache sessionCache;
	@Before
	public void prepareCache(){
		userCache = spyMemcachedCacheManager.getCache(CacheNames.CACHE_NAME_USER);
		roleCache = spyMemcachedCacheManager.getCache(CacheNames.CACHE_NAME_ROLE);
		resourceCache = spyMemcachedCacheManager.getCache(CacheNames.CACHE_NAME_RESOURCE);
		sessionCache = spyMemcachedCacheManager.getCache(CacheNames.CACHE_NAME_SESSION);
	}
	@Before
	public void prepareLoginContext() {
		LoginContext context = new LoginContext();
		context.setUserId(99999999L);
		context.setUserName("UNIT_TEST");
		LoginContextHolder.put(context);
	}
	@BeforeClass
	public static void initEnv(){
		SysconfigInitListener sysconfigInitListener = new SysconfigInitListener();
		ServletContextEvent contextEvent = new ServletContextEvent(new MockServletContext());
		sysconfigInitListener.onStartup(contextEvent);
	}
}