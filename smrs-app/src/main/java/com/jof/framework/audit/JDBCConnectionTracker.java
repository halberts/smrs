package com.jof.framework.audit;

public interface JDBCConnectionTracker {

	public void queryJDBCPoolLists();
	
	public void checkJDBCPoolStatus();
	
	public String getRootReason();
	
	public String getDetailReason();
	
}
