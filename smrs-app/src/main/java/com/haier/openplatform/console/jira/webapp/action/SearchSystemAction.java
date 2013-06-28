package com.haier.openplatform.console.jira.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.module.Issue;
import com.haier.openplatform.console.jira.module.AllSysCountModel;
import com.haier.openplatform.console.jira.module.AvetimeModel;
import com.haier.openplatform.console.jira.module.CakeRateModel;
import com.haier.openplatform.console.jira.module.CakeRateSearchModel;
import com.haier.openplatform.console.jira.module.HealthCountModel;
import com.haier.openplatform.console.jira.module.LineRateModel;
import com.haier.openplatform.console.jira.module.LineRateSearchModel;
import com.haier.openplatform.console.jira.module.ProSeleModel;
import com.haier.openplatform.console.jira.module.SysExctModel;
import com.haier.openplatform.console.jira.module.TimelyRateModel;
import com.haier.openplatform.util.Pager;

public class SearchSystemAction extends BaseJiraAction {
	private static final long serialVersionUID = 2986568372732421489L;
	//用于分页暂时没用
	private Pager<Issue> pager = new Pager<Issue>();
	//用于下拉
	private List<ProSeleModel> proList = new ArrayList<ProSeleModel>();
	//字符串显示
	private String proSele;
	//pager
	private Pager<CakeRateModel> pager1 = new Pager<CakeRateModel>();
	private Pager<LineRateModel> pager3 = new Pager<LineRateModel>();
	private List<List<String>> pager2 = new ArrayList<List<String>>();
	private List<List> pager4=new ArrayList<List>();
	private Map<String, Object> pram = new HashMap<String,Object>();
	//起始和结束时间
	private String date1;
	private String date2;
	private String val1;
	//所有开发及时率的总和
	private double count=0;
	//前台传入的系统下拉的值
	private String pkey1;
	private boolean[] flag=new boolean[15];
	private String starttime;
	private String startX;
	private String pname;
	private Pager<TimelyRateModel> pager6 = new Pager<TimelyRateModel>();
	private List<Map<String, Object>> pager7 = new ArrayList<Map<String, Object>>();
	private Date  startTime;
	private List<List> pager8 = new ArrayList<List>();
	private List<Map<String, Object>> pager9 = new ArrayList<Map<String, Object>>();
	private List<List> pager10 = new ArrayList<List>();
	private List<List> pager12 = new ArrayList<List>();
	private List<List> pager14 = new ArrayList<List>();
	private List<List> pager16 = new ArrayList<List>();
	private List<List> pager18 = new ArrayList<List>();
	private AllSysCountModel allSysCountModel=new AllSysCountModel();
	private List<AvetimeModel> pager19 = new ArrayList<AvetimeModel>();
	private List<SysExctModel> pager20 = new ArrayList<SysExctModel>();
	private List<HealthCountModel> pager21 = new ArrayList<HealthCountModel>();
	public Pager<Issue> getPager() {
		return pager;
	}
	public void setPager(Pager<Issue> pager) {
		this.pager = pager;
	}
	public List<ProSeleModel> getProList() {
		return proList;
	}
	public void setProList(List<ProSeleModel> proList) {
		this.proList = proList;
	}
	public String getProSele() {
		return proSele;
	}
	public void setProSele(String proSele) {
		this.proSele = proSele;
	}
	
	
	public Pager<CakeRateModel> getPager1() {
		return pager1;
	}
	public void setPager1(Pager<CakeRateModel> pager1) {
		this.pager1 = pager1;
	}
	public Map<String, Object> getPram() {
		return pram;
	}
	public void setPram(Map<String, Object> pram) {
		this.pram = pram;
	}
	
	
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	
	public String getVal1() {
		return val1;
	}
	public void setVal1(String val1) {
		this.val1 = val1;
	}
	
	public List<List<String>> getPager2() {
		return pager2;
	}
	public void setPager2(List<List<String>> pager2) {
		this.pager2 = pager2;
	}
	
	
	public String getPkey1() {
		return pkey1;
	}
	public void setPkey1(String pkey1) {
		this.pkey1 = pkey1;
	}
	
	public List<List> getPager4() {
		return pager4;
	}
	public void setPager4(List<List> pager4) {
		this.pager4 = pager4;
	}
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}	
	public List<List> getPager8() {
		return pager8;
	}
	public void setPager8(List<List> pager8) {
		this.pager8 = pager8;
	}
	
	public List<List> getPager10() {
		return pager10;
	}
	public void setPager10(List<List> pager10) {
		this.pager10 = pager10;
	}
	
	public List<List> getPager12() {
		return pager12;
	}
	public void setPager12(List<List> pager12) {
		this.pager12 = pager12;
	}
	
	public List<List> getPager14() {
		return pager14;
	}
	public void setPager14(List<List> pager14) {
		this.pager14 = pager14;
	}
	
	public List<List> getPager16() {
		return pager16;
	}
	public void setPager16(List<List> pager16) {
		this.pager16 = pager16;
	}
	
	public List<List> getPager18() {
		return pager18;
	}
	public void setPager18(List<List> pager18) {
		this.pager18 = pager18;
	}
	
	public AllSysCountModel getAllSysCountModel() {
		return allSysCountModel;
	}
	public void setAllSysCountModel(AllSysCountModel allSysCountModel) {
		this.allSysCountModel = allSysCountModel;
	}
	
	
	public List<AvetimeModel> getPager19() {
		return pager19;
	}
	public void setPager19(List<AvetimeModel> pager19) {
		this.pager19 = pager19;
	}
	
	public List<SysExctModel> getPager20() {
		return pager20;
	}
	public void setPager20(List<SysExctModel> pager20) {
		this.pager20 = pager20;
	}
	
	public List<HealthCountModel> getPager21() {
		return pager21;
	}
	public void setPager21(List<HealthCountModel> pager21) {
		this.pager21 = pager21;
	}
	public String execute() throws Exception {
		// 获取应用系统列表
	//	appLists = getBaseInfoService().getAppLists();
		
		if(proSele==null){
			proSele="";
		}
		return SUCCESS;
	}
	public String searchTimelyRate(){
		pram.put("pname", pname);
		pram.put("pkey", pkey1);
		pram.put("start", date1);
		pram.put("end", date2);
		pram.put("effissuestatus", 10006);
		pram.put("effissuestatus_1", "5");
		pram.put("effissuestatus_2", "6");
		CakeRateSearchModel searchModel = new CakeRateSearchModel();
		searchModel.setPager(pager1);
		pager1 = getLfSqlService().getCakeRate(pram, searchModel);
		
		for(int i=0;i<pager1.getRecords().size();i++){
			count =count+pager1.getRecords().get(i).getJsl();
		}
		if(pager1.getRecords().size()<11){
			List list =new ArrayList();
			double jslrt=100/count;
			list.add(pkey1);
			list.add(jslrt);
			pager2.add(list);
		}
		for(int i=0;i<pager1.getRecords().size();i++){
			List list =new ArrayList();
			double jslrt=pager1.getRecords().get(i).getJsl()/count;
			String[] s=(jslrt*100+"").split("\\.");
			String xtmrt=pager1.getRecords().get(i).getXtm()+" 所占比例"+(s[0]+"%");
			list.add(xtmrt);
			list.add(jslrt);
			pager2.add(list);
		}
		
		return SUCCESS;
	}
	
	
	public String sameTerm() throws Exception{
		List list1 =new ArrayList();
		List list2=new ArrayList();
		List list3=new ArrayList();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-01");
		startX=new SimpleDateFormat("yyyy-MM-01").format(new Date());
		list1.add(startX);
		Date d2=sdf.parse(startX);
		Calendar c2=Calendar.getInstance();
		c2.setTime(d2);
		System.out.println(pkey1);
		for(int i=0;i<6;i++){
			System.out.println(-i);
			if(i==0){
				c2.add(Calendar.MONTH,0);	
			}else{
				c2.add(Calendar.MONTH,-1);
			}
			
			Date d1=c2.getTime();
			startX=sdf.format(d1);
			starttime=sd.format(d1);
			List list =new ArrayList();
		Map<String, Object> pramtmp = new HashMap<String,Object>();
		pramtmp.put("pkey",pkey1);
		pramtmp.put("effissuestatus", 10006);
		pramtmp.put("effissuestatus_1", "5");
		pramtmp.put("effissuestatus_2", "6");
		pramtmp.put("starttime", starttime);
		LineRateSearchModel searchModel = new LineRateSearchModel();
		searchModel.setPager(pager3);
		pager3 = getLfSqlService().getLineRate(pramtmp,searchModel);
		if(pager3.getRecords().size()==0){
			list.add(startX);
			list.add(0.0);
			pager4.add(list);
		}else{
			double jsl=pager3.getRecords().get(0).getJsl();
			list.add(startX);
			list.add(jsl);
			pager4.add(list);
		}
	}
		return SUCCESS;
	
	}
	//查询所有系统的再某个时间段内的所有统计数量
	public String searchjsl(){
		pram.put("pname","");
		pram.put("pkey",pkey1);
		pram.put("start", date1);
		pram.put("end", date2);
		pram.put("effissuestatus", 10006);
		pram.put("effissuestatus_1", "5");
		pram.put("effissuestatus_2", "6");
		pram.put("key", pkey1);
		double sysjsl=0.0;
		double  sysave=0.0;
		int sysexct=0;
		int sysheal=0;
		CakeRateSearchModel searchModel = new CakeRateSearchModel();
		searchModel.setPager(pager1);
		pager1 = getLfSqlService().getCakeRate(pram, searchModel);
		if(pager1.getRecords().size()==0){
		sysjsl=0.0;
		}else{
		sysjsl=pager1.getRecords().get(0).getJsl();
		}
		List<Map<String, Object>> sysCount =getLfSqlService().getSysAllCount(pram); 
		if(sysCount.size()==0){
			sysave=0.0;
			sysexct=0;
			sysheal=0;
		}else{
			 sysave=Double.parseDouble(sysCount.get(0).get("AVETIME").toString());
			 sysexct=Integer.parseInt(sysCount.get(0).get("SYSEXCT").toString());
			 sysheal=Integer.parseInt(sysCount.get(0).get("HEALTHCOUNT").toString());
		}
		 allSysCountModel.setSysave(sysave);
		 allSysCountModel.setSysexct(sysexct);
		 allSysCountModel.setSysheal(sysheal);
		 allSysCountModel.setSysjsl(sysjsl);
		return SUCCESS;
	}
	//系统异常数饼状图
	public String sysExctCake(){
		boolean flag=false;
		Map<String, Object> pramSys = new HashMap<String,Object>();
		pramSys.put("pname", pname);
		pramSys.put("pkey", pkey1);
		pramSys.put("start", date1);
		pramSys.put("end", date2);
		pramSys.put("effissuestatus", 10006);
		pramSys.put("effissuestatus_1", "5");
		pramSys.put("effissuestatus_2", "6");
		pager7 = getLfSqlService().getSysExct(pramSys);
		Map map = new HashMap<String,String>();
		map.put("keyWord", val1);
		List<ProSeleModel> proList1 = getProSeleService().getProSele(map); 
		Map map1 =new HashMap();
		for(int i=0;i<proList1.size();i++){
			String pkey=proList1.get(i).getPkey();
			String pname=proList1.get(i).getPname();
			map1.put(pkey, pname);
		}
		if(pager7.size()==0){
		List list=new ArrayList();
		list.add(map1.get(pkey1)+" 所占比例100%");
		list.add(100.0);
		pager8.add(list);
		}else{
			for(int i=0;i<pager7.size();i++){
				double rate=Double.parseDouble(pager7.get(i).get("RATE").toString());
				String[] s=(rate*100+"").split("\\.");
				String pname2 =(String) map1.get(pager7.get(i).get("UMPKEY").toString())+" 所占比例"+s[0]+"%";
				System.out.println(pname2);
				if(pager7.get(i).get("UMPKEY").equals(pkey1)){
					flag=true;
				}
				
				List list=new ArrayList();
				list.add(pname2);
				list.add(rate);
				pager8.add(list);
			}
			if(!flag){
			List list=new ArrayList();
			list.add(map1.get(pkey1)+" 所占比例0.0%");
			list.add(0.0);
			pager8.add(list);
			}
		}
		return SUCCESS;
	}
	public String exctLine()throws Exception{
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-01");
		startX=new SimpleDateFormat("yyyy-MM-01").format(new Date());
		Date d2=sdf.parse(startX);
		Calendar c2=Calendar.getInstance();
		c2.setTime(d2);
		System.out.println(pkey1);
		for(int i=0;i<6;i++){
			System.out.println(-i);
			if(i==0){
				c2.add(Calendar.MONTH,0);
			}else{
				c2.add(Calendar.MONTH,-1);
			}
			
			Date d1=c2.getTime();
			startX=sdf.format(d1);
			starttime=sd.format(d1);
			List list =new ArrayList();
		Map<String, Object> pramtmp = new HashMap<String,Object>();
		pramtmp.put("pkey",pkey1);
		pramtmp.put("effissuestatus", 10006);
		pramtmp.put("effissuestatus_1", "5");
		pramtmp.put("effissuestatus_2", "6");
		pramtmp.put("starttime", starttime);
		pager9 = getLfSqlService().getExctLine(pramtmp);
		if(pager9.size()==0){
			list.add(startX);
			list.add(0);
			pager10.add(list);
		}else{
			int  errCount=Integer.parseInt(pager9.get(0).get("ERRCOUNT").toString());
			list.add(startX);
			list.add(errCount);
			pager10.add(list);
		}
	}
		
		
		
		
		return SUCCESS;
	}
	public String healthWorkCake(){
		boolean flag=false;
		Map<String, Object> pramheal = new HashMap<String,Object>();
		pramheal.put("pname", pname);
		pramheal.put("pkey", pkey1);
		pramheal.put("start", date1);
		pramheal.put("end", date2);
		pramheal.put("effissuestatus", 10006);
		pramheal.put("effissuestatus_1", "5");
		pramheal.put("effissuestatus_2", "6");
		List<Map<String, Object>> pager11  = getLfSqlService().getHealthExct(pramheal);
		Map map = new HashMap<String,String>();
		map.put("keyWord", val1);
		List<ProSeleModel> proList1 = getProSeleService().getProSele(map); 
		Map map1 =new HashMap();
		for(int i=0;i<proList1.size();i++){
			String pkey=proList1.get(i).getPkey();
			String pname=proList1.get(i).getPname();
			map1.put(pkey, pname);
		}
		if(pager11.size()==0){
		List list=new ArrayList();
		list.add(map1.get(pkey1)+" 所占比例100%");
		list.add(100.0);
		pager12.add(list);
		}else{
			for(int i=0;i<pager11.size();i++){
				double rate=Double.parseDouble(pager11.get(i).get("RATE").toString());
				String[] s=(rate*100+"").split("\\.");
				String pname2 =(String) map1.get(pager11.get(i).get("UMPKEY").toString())+" 所占比例"+s[0]+"%";
				System.out.println(pname2);
				if(pager11.get(i).get("UMPKEY").equals(pkey1)){
					flag=true;
				}
				
				List list=new ArrayList();
				list.add(pname2);
				list.add(rate);
				pager12.add(list);
			}
			if(!flag){
			List list=new ArrayList();
			list.add(map1.get(pkey1)+" 所占比例0.0%");
			list.add(0.0);
			pager12.add(list);
			}
		}
		
		return SUCCESS;
	}
	
	public String healthLine()throws Exception{
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-01");
		startX=new SimpleDateFormat("yyyy-MM-01").format(new Date());
		Date d2=sdf.parse(startX);
		Calendar c2=Calendar.getInstance();
		c2.setTime(d2);
		System.out.println(pkey1);
		for(int i=0;i<6;i++){
			System.out.println(-i);
			if(i==0){
				c2.add(Calendar.MONTH,0);
			}else{
				c2.add(Calendar.MONTH,-1);
			}
			
			Date d1=c2.getTime();
			startX=sdf.format(d1);
			starttime=sd.format(d1);
			List list =new ArrayList();
		Map<String, Object> pramtmp = new HashMap<String,Object>();
		pramtmp.put("pkey",pkey1);
		pramtmp.put("effissuestatus", 10006);
		pramtmp.put("effissuestatus_1", "5");
		pramtmp.put("effissuestatus_2", "6");
		pramtmp.put("starttime", starttime);
		List<Map<String, Object>> pager13 = getLfSqlService().getHealthLine(pramtmp);
		if(pager13.size()==0){
			list.add(startX);
			list.add(0);
			pager14.add(list);
		}else{
			int  errCount=Integer.parseInt(pager13.get(0).get("ERRCOUNT").toString());
			list.add(startX);
			list.add(errCount);
			pager14.add(list);
		}
	}
		
		
		
		
		return SUCCESS;
	}
	
	//业务平均响应时间饼状图
		public String aveTimeCake(){
			boolean flag=false;
			Map<String, Object> pramheal = new HashMap<String,Object>();
			pramheal.put("pname", pname);
			pramheal.put("pkey", pkey1);
			pramheal.put("start", date1);
			pramheal.put("end", date2);
			pramheal.put("effissuestatus", 10006);
			pramheal.put("effissuestatus_1", "5");
			pramheal.put("effissuestatus_2", "6");
			List<Map<String, Object>> pager15  = getLfSqlService().getAveTimeCake(pramheal);
			Map map = new HashMap<String,String>();
			map.put("keyWord", val1);
			List<ProSeleModel> proList1 = getProSeleService().getProSele(map); 
			Map map1 =new HashMap();
			for(int i=0;i<proList1.size();i++){
				String pkey=proList1.get(i).getPkey();
				String pname=proList1.get(i).getPname();
				map1.put(pkey, pname);
			}
			if(pager15.size()==0){
			List list=new ArrayList();
			list.add(map1.get(pkey1)+" 所占比例100%");
			list.add(100.0);
			pager18.add(list);
			}else{
				for(int i=0;i<pager15.size();i++){
					double rate=Double.parseDouble(pager15.get(i).get("RATE").toString());
					String[] s=(rate*100+"").split("\\.");
					String pname2 =(String) map1.get(pager15.get(i).get("UMPKEY").toString())+" 所占比例"+s[0]+"%";
					System.out.println(pname2);
					if(pager15.get(i).get("UMPKEY").equals(pkey1)){
						flag=true;
					}
					
					List list=new ArrayList();
					list.add(pname2);
					list.add(rate);
					pager16.add(list);
				}
				if(!flag){
				List list=new ArrayList();
				list.add(map1.get(pkey1)+" 所占比例0.0%");
				list.add(0.0);
				pager16.add(list);
				}
			}
			return SUCCESS;
		}
		//业务平均响应时间线形图
		
		public String aveTimeLine()throws Exception{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-01");
			startX=new SimpleDateFormat("yyyy-MM-01").format(new Date());
			Date d2=sdf.parse(startX);
			Calendar c2=Calendar.getInstance();
			c2.setTime(d2);
			System.out.println(pkey1);
			for(int i=0;i<6;i++){
				System.out.println(-i);
				if(i==0){
					c2.add(Calendar.MONTH,0);
				}else{
					c2.add(Calendar.MONTH,-1);
				}
				
				Date d1=c2.getTime();
				startX=sdf.format(d1);
				starttime=sd.format(d1);
				List list =new ArrayList();
			Map<String, Object> pramtmp = new HashMap<String,Object>();
			pramtmp.put("pkey",pkey1);
			pramtmp.put("effissuestatus", 10006);
			pramtmp.put("effissuestatus_1", "5");
			pramtmp.put("effissuestatus_2", "6");
			pramtmp.put("starttime", starttime);
			List<Map<String, Object>> pager17 = getLfSqlService().getAveTimeLine(pramtmp);
			if(pager17.size()==0){
				list.add(startX);
				list.add(0);
				pager18.add(list);
			}else{
				double errCount=Double.parseDouble(pager17.get(0).get("ERRCOUNT").toString());
				list.add(startX);
				list.add(errCount);
				pager18.add(list);
			}
		}
			return SUCCESS;
		}
		
		public String aveTimeTable(){
			pram.put("pname", pname);
			pram.put("pkey", pkey1);
			pram.put("start", date1);
			pram.put("end", date2);
			pram.put("effissuestatus", 10006);
			pram.put("effissuestatus_1", "5");
			pram.put("effissuestatus_2", "6");
			pager19 = getLfSqlService().getAveTimeTable(pram);
			return SUCCESS;
		}
		public String exctTable(){
			pram.put("pname", pname);
			pram.put("pkey", pkey1);
			pram.put("start", date1);
			pram.put("end", date2);
			pram.put("effissuestatus", 10006);
			pram.put("effissuestatus_1", "5");
			pram.put("effissuestatus_2", "6");
			pager20 = getLfSqlService().getExctTable(pram);
			return SUCCESS;
		}
		
		public String healthTable(){
			pram.put("pname", pname);
			pram.put("pkey", pkey1);
			pram.put("start", date1);
			pram.put("end", date2);
			pram.put("effissuestatus", 10006);
			pram.put("effissuestatus_1", "5");
			pram.put("effissuestatus_2", "6");
			pager21 = getLfSqlService().getHealthTable(pram);
			return SUCCESS;
		}
}
