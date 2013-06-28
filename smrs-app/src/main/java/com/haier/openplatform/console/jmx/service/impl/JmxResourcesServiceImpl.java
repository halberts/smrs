package com.haier.openplatform.console.jmx.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.hornetq.api.core.management.ObjectNameBuilder;
import org.hornetq.api.jms.management.ConnectionFactoryControl;
import org.hornetq.api.jms.management.JMSQueueControl;
import org.hornetq.api.jms.management.JMSServerControl;
import org.hornetq.api.jms.management.TopicControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.jmx.dao.HornetqResourcesOperationDAO;
import com.haier.openplatform.console.jmx.domain.HornetqResourcesAuditTrail;
import com.haier.openplatform.console.jmx.domain.MessageCounterInfo;
import com.haier.openplatform.console.jmx.service.JmxResourcesService;

/**
 * retrieve hornetQ information via JMX for display
 * 
 * @author Jeffrey Ko
 * 
 */
public class JmxResourcesServiceImpl implements JmxResourcesService {

	private static final Logger LOG = LoggerFactory.getLogger(JmxResourcesServiceImpl.class);

	private HornetqResourcesOperationDAO hornetqResourcesOperationDAO;

	private String jmxUrl;

	private MBeanServerConnection mBeanServerConnection;

	public JmxResourcesServiceImpl() {
		super();
	}

	public void setHornetqResourcesOperationDAO(HornetqResourcesOperationDAO hornetqResourcesOperationDAO) {
		this.hornetqResourcesOperationDAO = hornetqResourcesOperationDAO;
	}

	public String getJmxUrl() {
		return jmxUrl;
	}

	public void setJmxUrl(String jmxUrl) {
		LOG.info("method call setJmxUrl begins");
		this.jmxUrl = jmxUrl;
		LOG.info("jmxUrl=" + jmxUrl);
		MBeanServerConnection mbsc = null;
		try {
			//String jmxURL = "service:jmx:rmi:///jndi/rmi://127.0.0.1:4000/jmxrmi";  
			JMXServiceURL serviceURL = new JMXServiceURL(jmxUrl);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
			mbsc = connector.getMBeanServerConnection();
		} catch (Exception e) {
			LOG.error("创建JMX链接繁盛错误");
		}
		mBeanServerConnection = mbsc;
	}

	private MBeanServerConnection getMBeanServerConnection() {
		return mBeanServerConnection;
	}

	public static void main(final String[] args) {
		//System.out.println("method main begins");
		//new JmxResourcesServiceImpl().getAllResourcesParameters();
		JmxResourcesServiceImpl jmxResourcesServiceImplnew = new JmxResourcesServiceImpl();
		jmxResourcesServiceImplnew.setJmxUrl("service:jmx:rmi:///jndi/rmi://127.0.0.1:4000/jmxrmi");
		jmxResourcesServiceImplnew.getAllResourcesParameters();
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Map> getAllResourcesParameters() {
		LOG.info("method call getAllResourcesParameters begins");
		JMSServerControl serverControl;
		try {
			serverControl = (JMSServerControl) MBeanServerInvocationHandler.newProxyInstance(
					getMBeanServerConnection(), ObjectNameBuilder.DEFAULT.getJMSServerObjectName(),
					JMSServerControl.class, false);
		} catch (Exception e) {
			LOG.error("获取jmsServerControl代理实例出错");
			return null;
		}

		Map<String, Object> mapS = new HashMap<String, Object>();
		mapS.put("started", serverControl.isStarted());
		mapS.put("version", serverControl.getVersion());
		mapS.put("connectionFactoryNames", Arrays.toString(serverControl.getConnectionFactoryNames()));
		try {
			mapS.put("connectionIDs", Arrays.toString(serverControl.listConnectionIDs()));
		} catch (Exception e) {
			LOG.error("获取链接id列表发生异常");
		}
		try {
			mapS.put("remoteAddresses", Arrays.toString(serverControl.listRemoteAddresses()));
		} catch (Exception e) {
			LOG.error("获取远程地址发生异常");
		}
		mapS.put("topicNames", Arrays.toString(serverControl.getTopicNames()));
		mapS.put("queueNames", Arrays.toString(serverControl.getQueueNames()));

		@SuppressWarnings("unchecked")
		Map<String, Map> map = new LinkedHashMap();

		map.put("server", mapS);

		for (String name : serverControl.getConnectionFactoryNames()) {
			map.put(name, getConnectionFactoryParameters(name));
		}

		for (String name : serverControl.getTopicNames()) {
			// add prefix to key of this map to identify it is a jms topic
			map.put("jms.topic." + name, getTopicParameters(name));
		}

		for (String name : serverControl.getQueueNames()) {
			// add prefix to key of this map to identify it is a jms queue
			map.put("jms.queue." + name, getQueueParameters(name));
		}

		return map;

	}

	public Map<String, Object> getConnectionFactoryParameters(String cfName) {
		LOG.info("method call getConnectionFactoryParameters begins");
		ObjectName on;
		try {
			on = ObjectNameBuilder.DEFAULT.getConnectionFactoryObjectName(cfName);
		} catch (Exception e) {
			LOG.error("获取链接工厂对象名称时发生异常");
			return null;
		}
		ConnectionFactoryControl connFactoryControl = (ConnectionFactoryControl) MBeanServerInvocationHandler
				.newProxyInstance(getMBeanServerConnection(), on, ConnectionFactoryControl.class, false);

		Map<String, Object> map = new HashMap<String, Object>();

		String connectionFactoryName = connFactoryControl.getName();
		map.put("name", connectionFactoryName);
		String[] jndiBindings2 = connFactoryControl.getJNDIBindings();
		map.put("JNDI Bindings", Arrays.toString(jndiBindings2));
		String clientID = connFactoryControl.getClientID();
		map.put("client ID", clientID);
		long connectionTTL = connFactoryControl.getConnectionTTL();
		map.put("connectionTTL", connectionTTL);
		int consumerMaxRate = connFactoryControl.getConsumerMaxRate();
		map.put("consumerMaxRate", consumerMaxRate);
		int dupsOKBatchSize = connFactoryControl.getDupsOKBatchSize();
		map.put("dupsOKBatchSize", dupsOKBatchSize);
		int factoryType = connFactoryControl.getFactoryType();
		map.put("factoryType", factoryType);
		long maxRetryInterval = connFactoryControl.getMaxRetryInterval();
		map.put("maxRetryInterval", maxRetryInterval);
		int threadPoolMaxSize = connFactoryControl.getThreadPoolMaxSize();
		map.put("threadPoolMaxSize", threadPoolMaxSize);

		return map;
	}

	public Map<String, Object> getTopicParameters(String topicName) {
		LOG.info("method call getTopicParameters begins");

		ObjectName on;
		try {
			on = ObjectNameBuilder.DEFAULT.getJMSTopicObjectName(topicName);
		} catch (Exception e) {
			LOG.error("获取hornetQ TopicNmae时发生异常");
			return null;
		}

		TopicControl topicControl = (TopicControl) MBeanServerInvocationHandler.newProxyInstance(
				getMBeanServerConnection(), on, TopicControl.class, false);

		Map<String, Object> map = new HashMap<String, Object>();

		String name = topicControl.getName();
		map.put("name", name);
		String address = topicControl.getAddress();
		map.put("address", address);
		int durableSubscriptionCount = topicControl.getDurableSubscriptionCount();
		map.put("durableSubscriptionCount", durableSubscriptionCount);
		int durableMessageCount = topicControl.getDurableMessageCount();
		map.put("durableMessageCount", durableMessageCount);
		long nonDurableMessageCount = topicControl.getNonDurableMessageCount();
		map.put("nonDurableMessageCount", nonDurableMessageCount);
		String[] jndiBindings = topicControl.getJNDIBindings();
		map.put("jndiBindings", Arrays.toString(jndiBindings));
		// String topicControlName = topicControl.getName();
		int deliveringCount = topicControl.getDeliveringCount();
		map.put("deliveringCount", deliveringCount);
		long messagesAdded = topicControl.getMessagesAdded();
		map.put("messagesAdded", messagesAdded);
		long messageCount = 0; 
		try {
			messageCount = topicControl.getMessageCount();
		} catch (Exception e) {
			LOG.error("获取JMSTopic数量时发生异常");
		}
		map.put("messageCount", messageCount);
		long nonDurableSubscriptionCount = topicControl.getNonDurableSubscriptionCount();
		map.put("nonDurableSubscriptionCount", nonDurableSubscriptionCount);
		long subscriptionCount = topicControl.getSubscriptionCount();
		map.put("subscriptionCount", subscriptionCount);
		return map;
	}

	public Map<String, Object> getQueueParameters(String queueName) {
		LOG.debug("method call getQueueParameters begins");
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectName on = null;
		try {
			on = ObjectNameBuilder.DEFAULT.getJMSQueueObjectName(queueName);
		} catch (Exception e) {
			LOG.error("获取hornetq中queue name时发生异常");
			return null;
		}

		JMSQueueControl queueControl = (JMSQueueControl) MBeanServerInvocationHandler.newProxyInstance(
				getMBeanServerConnection(), on, JMSQueueControl.class, false);

		String address = queueControl.getAddress();
		//System.out.println("address=" + address);
		map.put("address", address);
		int consumerCount = queueControl.getConsumerCount();
		//System.out.println("consumerCount=" + consumerCount);
		map.put("consumerCount", consumerCount);
		String expiryAddress = queueControl.getExpiryAddress();
		//System.out.println("expiryAddress=" + expiryAddress);
		map.put("expiryAddress", expiryAddress);
		String[] jndiBindings1 = queueControl.getJNDIBindings();
		//System.out.println("jndiBindings=" + Arrays.toString(jndiBindings1));
		map.put("jndiBindings", Arrays.toString(jndiBindings1));
		String queueControlName = queueControl.getName();
		//System.out.println("queueControlName=" + queueControlName);
		map.put("name", queueControlName);
		int deliveringCount = queueControl.getDeliveringCount();
		//System.out.println("deliveringCount=" + deliveringCount);
		map.put("deliveringCount", deliveringCount);
		long messagesAdded = queueControl.getMessagesAdded();
		//System.out.println("messagesAdded=" + messagesAdded);
		map.put("messagesAdded", messagesAdded);
		long messageCount = 0;
		try {
			messageCount = queueControl.getMessageCount();
		} catch (Exception e1) {
			LOG.error("获取hornetQ queue数量时发生异常",e1);
		}
		//System.out.println("messageCount=" + messageCount);
		map.put("messageCount", messageCount);
		String selector = queueControl.getSelector();
		//System.out.println("selector=" + selector);
		map.put("selector", selector);
		boolean isPaused = false;
		try {
			isPaused = queueControl.isPaused();
		} catch (Exception e) {
			LOG.error("获取queue暂停状态时发生异常");
		}
		map.put("isPaused", isPaused);

		String counters = null;
		try {
			counters = queueControl.listMessageCounter();
		} catch (Exception e) {
			LOG.error("获取queue中消息个数时发生异常");
		}
		MessageCounterInfo messageCounter = null;
		try {
			messageCounter = MessageCounterInfo.fromJSON(counters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (messageCounter != null) {
			map.put("messageCounterName", messageCounter.getName());
			map.put("messageCounterUpdateTimestamp", messageCounter.getUdpateTimestamp());
			// map.put("message counter count (messages added to queue)",
			// messageCounter.getCount());
			map.put("messageCounterCount", messageCounter.getCount());
			// map.put("message counter count delta",
			// messageCounter.getCountDelta());
			map.put("messageCounterCountDelta", messageCounter.getCountDelta());

			// map.put("message counter depth (messages in queue)",
			// messageCounter.getDepth());
			map.put("messageCounterDepth", messageCounter.getDepth());

			// map.put("message counter count since last sample",
			// messageCounter.getDepthDelta());
			map.put("messageCounterDepthDelta", messageCounter.getDepthDelta());

			// map.put("message counter last add timestamp",
			// messageCounter.getLastAddTimestamp());
			map.put("messageCounterLastAddTimestamp", messageCounter.getLastAddTimestamp());
		}
		return map;
	}

	public int saveAllResourcesParameters() {
		LOG.info("method call saveAllResourcesParameters begins");
		int count = 0;
		@SuppressWarnings("rawtypes")
		Map<String, Map> map = getAllResourcesParameters();

		Date dt = new Date();

		for (@SuppressWarnings("rawtypes")
		Map.Entry<String, Map> entry : map.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			String resourceIdentifier = entry.getKey();
			@SuppressWarnings("unchecked")
			Map<String, Object> map2 = entry.getValue();
			for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
				HornetqResourcesAuditTrail trail = new HornetqResourcesAuditTrail();
				String resourceKey = entry2.getKey();
				String resourceValue = "" + entry2.getValue();
				trail.setResourceIdentifier(resourceIdentifier);
				trail.setResourceKey(resourceKey);
				trail.setResourceValue(resourceValue);
				trail.setAuditTimestamp(dt);
				hornetqResourcesOperationDAO.saveHornetQ(trail);
				count++;
			}
		}
		return count;
	}

}
