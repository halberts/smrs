package com.jof.framework.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 分页组件
 * 
 *
 */
public class PageInfo implements Serializable{
	public static final Long DEFAULT_PAGE_SIZE = 20L;
	private static final long serialVersionUID = 609222305391683918L;
	/**
	 * 结果集
	 */
	//private List<T> records = new ArrayList<T>();
	/**
	 * 记录总数
	 */
	private Long totalRecords = 0L;
	/**
	 * 当前页 
	 */
	private Long currentPage = 1L;
	/**
	 * 每页记录数,默认20条
	 */
	private Long pageSize = DEFAULT_PAGE_SIZE;
	/**
	 * 排序字段名称,多个字段中间使用,分隔
	 */
	private String orderProperty = "";
	/**
	 * 排序方式asc或desc,多个字段中间使用,分隔
	 */
	private String order = "";
	/**
	 * 是否计算总数
	 */
	private boolean countTotal = true;
	/*
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	*/
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Long currentPage) {
		if(currentPage <= 0){
			this.currentPage = 1L;
		}else{
			this.currentPage = currentPage;
		}
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		if(pageSize <= 0){
			this.pageSize = 1L;
		}else{
			this.pageSize = pageSize;
		}
	}
	public boolean isCountTotal() {
		return countTotal;
	}
	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}
	/**
	 * 计算记录的总页数
	 */
	public Long getTotalPages(){
		if(getTotalRecords() == 0){
			return 1L;
		}
		Long div = getTotalRecords()/getPageSize();
		Long sub = getTotalRecords()%getPageSize();
		if(sub == 0){
			return div;
		}else{
			return div + 1;
		}
	}
	/**
	 * 是否设置了排序属性
	 * @return
	 */
	public boolean isOrderBySetted(){
		return StringUtils.isNotBlank(this.order) && StringUtils.isNotBlank(this.orderProperty);
	}
	/**
	 * 根据当前页获取记录开始号
	 * @return
	 */
	public Long getFirstResult(){
		return (getCurrentPage() - 1) * getPageSize();
	}
	public String getOrderProperty() {
		return orderProperty;
	}
	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		String lowcaseOrderDir = StringUtils.lowerCase(order);
		//检查order字符串的合法值
		String[] orderDirs = StringUtils.split(lowcaseOrderDir, ',');
		for (String orderDirStr : orderDirs) {
			if (!StringUtils.equals(PageSort.DESC, orderDirStr) && !StringUtils.equals(PageSort.ASC, orderDirStr)) {
				throw new IllegalArgumentException("排序方向" + orderDirStr + "不是合法值");
			}
		}
		this.order = lowcaseOrderDir;
	}
	/**
	 * 获得排序参数.
	 * @return
	 */
	public List<PageSort> getSort() {
		String[] orderBys = StringUtils.split(this.orderProperty, ',');
		String[] orderDirs = StringUtils.split(this.order, ',');
		Validate.isTrue(orderBys.length == orderDirs.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

		List<PageSort> orders = new ArrayList<PageSort>();
		for (int i = 0; i < orderBys.length; i++) {
			orders.add(new PageSort(orderBys[i], orderDirs[i]));
		}
		return orders;
	}
	
	/**
	 * 复制pager的基本信息，totalRecords,currentPage,pageSize,orderProperty,order,countTotal
	 * @param pager
	 * @return
	 */
	/*
	public static <X,M> PageInfo<M> cloneFromPager(PageInfo<X> pager){
		PageInfo<M> result = new PageInfo<M>();
		result.setCountTotal(pager.isCountTotal());
		result.setCurrentPage(pager.getCurrentPage());
		result.setOrder(pager.getOrder());
		result.setOrderProperty(pager.getOrderProperty());
		result.setPageSize(pager.getPageSize());
		result.setTotalRecords(pager.getTotalRecords());
		return result;
	}
	*/
	/**
	 * 复制pager的基本信息，totalRecords,currentPage,pageSize,orderProperty,order,countTotal,
	 * 重新设置records，totalRecords属性
	 * @param pager
	 * @return
	 */
	/*
	public static <X> PageInfo<X> cloneFromPager(PageInfo<X> pager,long totalRecords,List<X> records){
		PageInfo<X> result = cloneFromPager(pager);
		result.setTotalRecords(totalRecords);
		result.setRecords(records);
		return result;
	}
	*/

}
