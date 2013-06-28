package com.jof.framework.util;

public class PageSort {
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private final String property;
	private final String dir;

	public PageSort(String property, String dir) {
		this.property = property;
		this.dir = dir;
	}

	public String getProperty() {
		return property;
	}

	public String getDir() {
		return dir;
	}
}
