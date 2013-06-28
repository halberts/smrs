package com.haier.openplatform.console.jira.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.Issue;
import com.haier.openplatform.console.jira.module.JiraUserGroupModel;
import com.haier.openplatform.console.jira.module.ProSeleModel;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu Jira监控（首页）
 */
public class JiraCenterAction extends BaseJiraAction {

	private static final long serialVersionUID = 2986568372732421489L;

	/**
	 * service分页对象
	 */
	private Pager<Issue> pager = new Pager<Issue>();

	//private Pager<ServiceApi> apiPager = new Pager<ServiceApi>();
	private HashMap<String, Object> map = new HashMap<String , Object>();

	/**
	 * 换异常类型过滤
	 */
	//private String extype;

	/**
	 * 应用系统列表
	 * 
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private List<AppLists> appLists = new ArrayList<AppLists>();

	private String serviceName;
	
	private List<ProSeleModel> proList = new ArrayList<ProSeleModel>();
	
	private Pager<JiraUserGroupModel> userGroupList =  new Pager<JiraUserGroupModel>();
	
	private String proSele;
	
	private String pname;
	
	private String[] contractDays;
	
	private String[] priceinfo;
	
	private String[] price;
	
	private int count = 0;
	
	private String start;
	private String end;
	
	
	public String getPname() {
		return pname;
	}


	public void setPname(String pname) {
		this.pname = pname;
	}


	public String getStart() {
		if(null==start){
			start=sdf.format(new Date());
		}
		return start;
	}


	public void setStart(String start) {
		
		this.start = start;
	}


	public String getEnd() {
		if(null==end){
			end=sdf.format(new Date());
		}
		return end;
	}


	public void setEnd(String end) {
		
		this.end = end;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public void setContractDays(String[] contractDays) {
		this.contractDays = contractDays;
	}


	public String[] getContractDays() {
		return contractDays;
	}
	
	public String[] getPrice() {
		return price;
	}


	public void setPrice(String[] price) {
		this.price = price;
	}
	
	public void setPriceinfo(String[] priceinfo) {
		this.priceinfo = priceinfo;
	}

	public String[] getPriceinfo() {
		return priceinfo;
	}
	
	public String getProSele() {
		return proSele;
	}

	public void setProSele(String proSele) {
		this.proSele = proSele;
	}

	public Pager<JiraUserGroupModel> getUserGroupList() {
		return userGroupList;
	}
	
	public void setUserGroupList(Pager<JiraUserGroupModel> userGroupList) {
		this.userGroupList = userGroupList;
	}


	public List<ProSeleModel> getProList() {
		return proList;
	}

	
	
	public void setProList(List<ProSeleModel> proList) {
		this.proList = proList;
	}



	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public Pager<Issue> getPager() {
		return pager;
	}

	public void setPager(Pager<Issue> pager) {
		this.pager = pager;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}
	@Override
	public String execute() throws Exception {
		// 获取应用系统列表
	//	appLists = getBaseInfoService().getAppLists();
		
		if(proSele==null){
			proSele="";
		}
		
		return SUCCESS;
	}
	public String saveSuppliersDayPrice(){
		if(proSele==null){
			proSele="";
		}
		if(priceinfo!=null&&price!=null&&contractDays!=null){
			for(int i=0;i<priceinfo.length;i++){
				String[] j = priceinfo[i].split(","); 
				String sqlUpdate = "update SOA_SuppliersDayPrice set user_name='"+j[1]+"',project_key='"+j[2]+"',price='"+price[i]+"',createdate=now(),ContractDays='"+contractDays[i]+"',project_id='"+j[3]+"' where user_id='"+j[0]+"'" ;
				//System.out.println(sqlUpdate);
				String sqlInsert = "insert into SOA_SuppliersDayPrice(user_id,user_name,project_key,price,createdate,ContractDays,project_id) value('"+j[0]+"','"+j[1]+"','"+j[2]+"','"+price[i]+"',now(),'"+contractDays[i]+"','"+j[3]+"')";
				int count1 = 0;
				int count2 = 0;
				count1 = getUserGroupService().saveSuppliers(sqlUpdate);
				if (count1<=0){
					count2 = getUserGroupService().saveSuppliers(sqlInsert);
				}
				if(count1>0||count2>0){
					count++;
				}
			}
		
		}
		return SUCCESS;
		
	}
	
	public String getDisplayname(){
		if(proSele==null){
			proSele="";
		}
		map.put("groupname","供应商人员");
		map.put("projectkey",proSele);
		userGroupList = getUserGroupService().findByKW(map);
		//System.out.println(userGroupList);
		return SUCCESS;
	}

}
