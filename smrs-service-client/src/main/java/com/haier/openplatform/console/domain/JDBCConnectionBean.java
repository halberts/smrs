package com.haier.openplatform.console.domain;

import com.haier.openplatform.hmc.domain.MessageModule;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("JDBCConnectionBean")
public class JDBCConnectionBean extends MessageModule{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5881138071486742097L;
	/**
	 * 
	 */
	@XStreamAlias("appName")
	private String appName;
	/**
	 * 
	 */
	@XStreamAlias("nodeIP")
	private String nodeIP;
	/**
	 * 
	 */
	@XStreamAlias("poolState")
	private boolean poolState;
	
	/**
	 * 
	 */
	@XStreamAlias("activeConnectionsCurrentCount")
	private int activeConnectionsCurrentCount;
	
	/**
	 * 
	 */
	@XStreamAlias("waitingForConnectionCurrentCount")
	private int waitingForConnectionCurrentCount;
	
	/**
	 * 
	 */
	@XStreamAlias("waitSecondsHighCount")
	private int waitSecondsHighCount;
	
	/**
	 * 
	 */
	@XStreamAlias("maxCapacity")
	private int maxCapacity;
	
	/**
	 * 
	 */
	@XStreamAlias("leakedConnectionCount")
	private int leakedConnectionCount;
	/**
	 * 
	 */
	@XStreamAlias("gmtCreate")
	private String gmtCreate;
	
	public boolean isPoolState() {
		return poolState;
	}

	public void setPoolState(boolean poolState) {
		this.poolState = poolState;
	}

	public int getActiveConnectionsCurrentCount() {
		return activeConnectionsCurrentCount;
	}

	public void setActiveConnectionsCurrentCount(int activeConnectionsCurrentCount) {
		this.activeConnectionsCurrentCount = activeConnectionsCurrentCount;
	}

	public int getWaitingForConnectionCurrentCount() {
		return waitingForConnectionCurrentCount;
	}

	public void setWaitingForConnectionCurrentCount(
			int waitingForConnectionCurrentCount) {
		this.waitingForConnectionCurrentCount = waitingForConnectionCurrentCount;
	}

	public int getWaitSecondsHighCount() {
		return waitSecondsHighCount;
	}

	public void setWaitSecondsHighCount(int waitSecondsHighCount) {
		this.waitSecondsHighCount = waitSecondsHighCount;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getLeakedConnectionCount() {
		return leakedConnectionCount;
	}

	public void setLeakedConnectionCount(int leakedConnectionCount) {
		this.leakedConnectionCount = leakedConnectionCount;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getNodeIP() {
		return nodeIP;
	}

	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}

}
