package com.smrs.security.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.smrs.security.model.UserModel;

/**
 * @author WangXuzheng
 *
 */
public class SearchUserJsonAction extends SearchUserAction {
	private static final long serialVersionUID = -893264320795167383L;
	private Long rows = 0L;
	private Long page = 0L;
	private Long total = 0L;
	private Long records = 0L;
	private String sord;
	private String sidx;
	private String searchField;
	private String searchString;
	private String searchOper;
	private String order;
	private boolean loadonce = false;
	private boolean search = true;
	
	
	private List<UserModel> myusers;	// 该变量仅为演示前台表格数据转换成后台bean而设定，无实际意义。
	
	private String linedata;
	private String bardata;
	private String columndata;




	public String getLinedata() {
		return linedata;
	}

	public void setLinedata(String linedata) {
		this.linedata = linedata;
	}

	public String getBardata() {
		return bardata;
	}

	public void setBardata(String bardata) {
		this.bardata = bardata;
	}

	public String getColumndata() {
		return columndata;
	}

	public void setColumndata(String columndata) {
		this.columndata = columndata;
	}

	public List<UserModel> getMyusers() {
		return myusers;
	}

	public void setMyusers(List<UserModel> myusers) {
		this.myusers = myusers;
	}


	public Long getRows() {
		return rows;
	}

	public void setRows(Long rows) {
		this.rows = rows;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}
	
	
	
	
	@Override
	public String execute() throws Exception {
		getPager().setCurrentPage(page);
		getPager().setPageSize(rows);
		if(StringUtils.isNotEmpty(sidx)){
			getPager().setOrderProperty(sidx);
			getPager().setOrder(StringUtils.defaultIfEmpty(sord,"asc"));
		}
		String result = super.execute();
		this.setTotal(this.getPager().getTotalPages());
		this.setRecords(this.getPager().getTotalRecords());
		
		
		
		return result;
	}
	
	
	public String getChartData() throws Exception {
		
	
		/**
		 * chart1 直线图
		 */
		List<HopPointData> list1 = new ArrayList<HopPointData>();
		list1.add(new HopPointData("'2008-05-23'","578.55"));
		list1.add(new HopPointData("'2008-06-20'","566.5"));
		list1.add(new HopPointData("'2008-06-25'","480.88"));
		list1.add(new HopPointData("'2008-08-22'","509.84"));
		list1.add(new HopPointData("'2008-09-26'","454.13"));
		list1.add(new HopPointData("'2008-10-24'","379.75"));
		list1.add(new HopPointData("'2008-11-21'","303"));
		list1.add(new HopPointData("'2008-12-26'","308.45"));
		list1.add(new HopPointData("'2009-01-23'","299.13"));
		list1.add(new HopPointData("'2009-02-20'","326.51"));
		list1.add(new HopPointData("'2009-03-20'","325.99"));
		list1.add(new HopPointData("'2009-04-24'","386.15"));
		
		linedata = HopChartUtil.formatPointData(list1);	// 将一组数据点  格式化为图表所需格式
		
		
		/**
		 * chart2 横向条形组图
		 */
		List<String> cd = new ArrayList<String>();
		List<HopPointData> p1 = new ArrayList<HopPointData>();	// 第一组数据
		p1.add(new HopPointData("12", "1"));
		p1.add(new HopPointData("4", "2"));
		p1.add(new HopPointData("6", "3"));
		p1.add(new HopPointData("3", "4"));
		
		
		List<HopPointData> p2 = new ArrayList<HopPointData>();	// 第二组数据
		p2.add(new HopPointData("5", "1"));
		p2.add(new HopPointData("1", "2"));
		p2.add(new HopPointData("3", "3"));
		p2.add(new HopPointData("4", "4"));
		
		// 将两组数据都格式化后放入list中
		cd.add(HopChartUtil.formatPointData(p1));	
		cd.add(HopChartUtil.formatPointData(p2));
		
		bardata = cd.toString();
		
		
		/**
		 * chart3 柱状图
		 */
		
	    List<HopValueData> v1 = new ArrayList<HopValueData>();
	    v1.add(new HopValueData("12"));
	    v1.add(new HopValueData("62"));
	    v1.add(new HopValueData("37"));
	    v1.add(new HopValueData("80"));
	    v1.add(new HopValueData("98"));
	    
	    List<HopValueData> v2 = new ArrayList<HopValueData>();
	    v2.add(new HopValueData("27"));
	    v2.add(new HopValueData("35"));
	    v2.add(new HopValueData("63"));
	    v2.add(new HopValueData("52"));
	    v2.add(new HopValueData("79"));
	    
	    List<String> cd3 = new ArrayList<String>();
	    cd3.add(HopChartUtil.formatValueData(v1));
	    cd3.add(HopChartUtil.formatValueData(v2));
	    columndata = cd3.toString();
	    
		return SUCCESS;
	}
}
