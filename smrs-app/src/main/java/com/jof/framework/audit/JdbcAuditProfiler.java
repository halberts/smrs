package com.jof.framework.audit;

import com.haier.openplatform.jdbc.JdbcLogContext;
import com.haier.openplatform.jdbc.JdbcProfiler;


public class JdbcAuditProfiler implements JdbcProfiler {

	private boolean enabled = true;
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void log(JdbcLogContext jdbcLogContext) {
		ReqNotes.putSQL(jdbcLogContext.toString());
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
