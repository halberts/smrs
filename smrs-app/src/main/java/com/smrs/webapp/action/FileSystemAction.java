package com.smrs.webapp.action;


public class FileSystemAction extends BaseConsoleAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2291263354263091650L;
	
	private String fileSystemManagementUrl;
	
	@Override
	public String execute() throws Exception {
		
		return SUCCESS;
	}

	public String getFileSystemManagementUrl() {
		return fileSystemManagementUrl;
	}

	public void setFileSystemManagementUrl(String fileSystemManagementUrl) {
		this.fileSystemManagementUrl = fileSystemManagementUrl;
	}

}
