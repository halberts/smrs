package com.haier.openplatform.console.audit;

public interface JDBCConnectionTracker {

	public void queryJDBCPoolLists();
	
	public void checkJDBCPoolStatus();
	
	public String getRootReason();
	
	public String getDetailReason();
	
}
