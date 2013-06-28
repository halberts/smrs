package com.haier.openplatform.console.jira.module;

public class JiraUserGroupModel {
	private long id;
	private long pid;
	private String username;
	private String displayname;
	private String active;
	private String emailaddress;
	private String guoupid;
	private String groupname;
	private String projectkey;
	private String projectname;
	private String price;
	private String contractdays;
	private String adminprice;
	private double groupprice;
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	public String getContractdays() {
		return contractdays;
	}

	public void setContractdays(String contractdays) {
		this.contractdays = contractdays;
	}

	public double getGroupprice() {
		return groupprice;
	}

	public void setGroupprice(double groupprice) {
		this.groupprice = groupprice;
	}

	public String getAdminprice() {
		return adminprice;
	}

	public void setAdminprice(String adminprice) {
		this.adminprice = adminprice;
	}

	public long getPid() {
		return pid;
	}
	
	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public void setProjectkey(String projectkey) {
		this.projectkey = projectkey;
	}
	public String getProjectkey() {
		return projectkey;
	}
	public long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getDisplayname() {
		return displayname;
	}
	public String getActive() {
		return active;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public String getGuoupid() {
		return guoupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public void setGuoupid(String guoupid) {
		this.guoupid = guoupid;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	
	

}
