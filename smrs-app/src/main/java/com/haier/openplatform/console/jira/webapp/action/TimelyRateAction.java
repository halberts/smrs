package com.haier.openplatform.console.jira.webapp.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.jira.module.JiraUserGroupModel;
import com.haier.openplatform.console.jira.module.ManagermentModel;
import com.haier.openplatform.console.jira.module.ProSeleModel;
import com.haier.openplatform.console.jira.module.QualityModel;
import com.haier.openplatform.console.jira.module.QualitySearchModel;
import com.haier.openplatform.console.jira.module.SoaRelationModel;
import com.haier.openplatform.console.jira.module.SuggestModel;
import com.haier.openplatform.console.jira.module.SumColModel;
import com.haier.openplatform.console.jira.module.SysPingceModel;
import com.haier.openplatform.console.jira.module.TimeWorkedModel;
import com.haier.openplatform.console.jira.module.TimeWorkedSearchModel;
import com.haier.openplatform.console.jira.module.TimelyRateModel;
import com.haier.openplatform.console.jira.module.TimelyRateSearchModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionSearchModel;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu Jira监控（首页）
 */
public class TimelyRateAction extends BaseJiraAction {

	private static final long serialVersionUID = 2986568372732421489L;
	/**
	 * service分页对象
	 */
	private Pager<TimelyRateModel> pager = new Pager<TimelyRateModel>();
	private Pager<UserSatisfactionModel> pagerSatis = new Pager<UserSatisfactionModel>();
	private List<ProSeleModel> proList = new ArrayList<ProSeleModel>();
	private QualityModel pagerQuality = new QualityModel();
	private Pager<TimeWorkedModel> pagerTimeWorked = new Pager<TimeWorkedModel>();
	private Pager<SuggestModel> suggest = new Pager<SuggestModel>();
	//private List<JiraUserGroupModel> displayList = new ArrayList<JiraUserGroupModel>();
	private String adminPrice = "";
	private double groupPrice = 0;
	private String searchpkey;
	private String searchstart;
	private String searchend;
	private String sysdate;
	private JiraUserGroupModel jiraUserGroup;
	private String extype;
	private SumColModel sumColModel = new SumColModel();
	private ManagermentModel managermentModel = new ManagermentModel();
	private SoaRelationModel soaRelationModel = new SoaRelationModel();
	private TimeWorkedModel timeWorkedModel  = new TimeWorkedModel();
	private Map<String, Object> pram = new HashMap<String,Object>();
	private SysPingceModel sysPingceModel = new SysPingceModel();

	public Pager<SuggestModel> getSuggest() {
		return suggest;
	}

	public void setSuggest(Pager<SuggestModel> suggest) {
		this.suggest = suggest;
	}

	public JiraUserGroupModel getJiraUserGroup() {
		return jiraUserGroup;
	}

	public void setJiraUserGroup(JiraUserGroupModel jiraUserGroup) {
		this.jiraUserGroup = jiraUserGroup;
	}

	public String getSysdate() {
		java.util.Date dt = new java.util.Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     sysdate = sdf.format(dt);
		 return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getSearchpkey() {
		return searchpkey;
	}

	public void setSearchpkey(String searchpkey) {
		this.searchpkey = searchpkey;
	}

	public String getSearchstart() {
		return searchstart;
	}

	public void setSearchstart(String searchstart) {
		this.searchstart = searchstart;
	}

	public String getSearchend() {
		return searchend;
	}

	public void setSearchend(String searchend) {
		this.searchend = searchend;
	}

	public double getGroupPrice() {
		groupPrice = Double.parseDouble(String .format("%.2f",groupPrice));
		return groupPrice;
	}

	public void setGroupPrice(double groupPrice) {
		this.groupPrice = groupPrice;
	}

	
	public SysPingceModel getSysPingceModel() {
		return sysPingceModel;
	}

//	public void setSysPingceModel(SysPingceModel sysPingceModel) {
//		this.sysPingceModel = sysPingceModel;
//	}

	public String getAdminPrice() {
		adminPrice = String .format("%.2s",adminPrice);
		return adminPrice;
	}

	public void setAdminPrice(String adminPrice) {
		this.adminPrice = adminPrice;
	}

	public Pager<UserSatisfactionModel> getPagerSatis() {
		return pagerSatis;
	}

	public void setPagerSatis(Pager<UserSatisfactionModel> pagerSatis) {
		this.pagerSatis = pagerSatis;
	}

	public ManagermentModel getManagermentModel() {
		return managermentModel;
	}

	public void setManagermentModel(ManagermentModel managermentModel) {
		this.managermentModel = managermentModel;
	}
	
	public SoaRelationModel getSoaRelationModel() {
		return soaRelationModel;
	}

	public void setSoaRelationModel(SoaRelationModel soaRelationModel) {
		this.soaRelationModel = soaRelationModel;
	}
	
	public TimeWorkedModel getTimeWorkedModel() {
		return timeWorkedModel;
	}

	public void setTimeWorkedModel(TimeWorkedModel timeWorkedModel) {
		this.timeWorkedModel = timeWorkedModel;
	}
	
	
	public Pager<TimeWorkedModel> getPagerTimeWorked() {
		return pagerTimeWorked;
	}

	public void setPagerTimeWorked(Pager<TimeWorkedModel> pagerTimeWorked) {
		this.pagerTimeWorked = pagerTimeWorked;
	}

	public QualityModel getPagerQuality() {
		return pagerQuality;
	}

	public void setPagerQuality(QualityModel pagerQuality) {
		this.pagerQuality = pagerQuality;
	}

	public String getExtype() {
		return extype;
	}


	public void setExtype(String extype) {
		this.extype = extype;
	}



	public Pager<TimelyRateModel> getPager() {
		return pager;
	}

	public void setPager(Pager<TimelyRateModel> pager) {
		this.pager = pager;
	}

	public  SumColModel getSumColModel() {
		return sumColModel;
	}

	public void setSumColModel(SumColModel sumColModel) {
		this.sumColModel = sumColModel;
	}

	public List<ProSeleModel> getProList() {
		return proList;
	}

	public void setProList(List<ProSeleModel> proList) {
		this.proList = proList;
	}
/*	private String id ;
	private int contractdayscount;
	
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getContractdayscount() {
		return contractdayscount;
	}

	public void setContractdayscount(int contractdayscount) {
		this.contractdayscount = contractdayscount;
	}*/
;
	public void getManagerment(){
		if(getLfSqlService().getManagerment(pram).size()>0){
			Map<String,Object> manager = getLfSqlService().getManagerment(pram).get(0);
			managermentModel.setJjzcount(cNullToint(manager.get("JJZCOUNT")));
			managermentModel.setLsuedoczcount(cNullToint(manager.get("LSUEDOCZCOUNT")));
			managermentModel.setLtecdoczcount(cNullToint(manager.get("LTECDOCZCOUNT")));
			managermentModel.setLvezcount(cNullToint(manager.get("LVEZCOUNT")));
			managermentModel.setSuedoczcount(cNullToint(manager.get("SUEDOCZCOUNT")));
			managermentModel.setTecdoczcount(cNullToint(manager.get("TECDOCZCOUNT")));
			managermentModel.setVezcount(cNullToint(manager.get("VEZCOUNT")));
		}
	}
	@Override
	public String execute() throws Exception {
		// 获取应用系统列表
		pram.put("pkey",searchpkey);
		pram.put("start", searchstart);
		pram.put("end", searchend);
		pram.put("effissuestatus", 10006);
		pram.put("effissuestatus_1", "5");
		pram.put("effissuestatus_2", "6");
		pram.put("Transformdays", 28800);
		pram.put("issuedoc", 10040);
		pram.put("tecdoc", 10061);
		pram.put("forstart", 10026);
		pram.put("bugtype", "1");
		TimelyRateSearchModel searchModel = new TimelyRateSearchModel();
		searchModel.setPager(pager);
		pager = getLfSqlService().getTimelyRate(pram,searchModel);
		getManagerment();
		getUserSatisfaction();
		this.getpagerTimeWorked();
		this.getQuality();
		jiraUserGroup= getLfSqlService().getproadmin(pram);
		if(null==jiraUserGroup){
			adminPrice= "0";
		}else{
			adminPrice =  formatDouble(Double.parseDouble(jiraUserGroup.getAdminprice()));
		}
		groupPrice = Double.parseDouble(formatDouble(getLfSqlService().getgroupPrice(pram)));
		suggest = getLfSqlService().getSuggest(pram);
		return SUCCESS;
	}
	
	 public String formatDouble(double s){     
		 DecimalFormat fmt = new DecimalFormat("#");   
		 return fmt.format(s);  
	 }
	public double jskjl(double tql,int yqtimeworked,int tqday ){
		double kje = 0.00;
		if(tql>10){
			kje=yqtimeworked*1000*0.2;
		}else{
			kje = tqday*0.8*1000;
		}
		return kje;
	}
	public void getSumByName(){
		Map<String, Object> pramtmp = new HashMap<String,Object>();
		pramtmp.put("pkey",searchpkey);
		pramtmp.put("start", searchstart);
		pramtmp.put("end", searchend);
//		pram.put("pkey", "hmmsqy");
//		pram.put("start", "2011-10-01");
//		pram.put("end", "2012-12-31");
		pramtmp.put("effissuestatus", 10006);
		pramtmp.put("effissuestatus_1", "5");
		pramtmp.put("effissuestatus_2", "6");
		pramtmp.put("Transformdays", 28800);
		pramtmp.put("issuedoc", 10040);
		pramtmp.put("tecdoc", 10061);
		pramtmp.put("forstart", 10026);
		if(getLfSqlService().getColSum(pramtmp).size()>0){
			Map<String,Object> map = getLfSqlService().getColSum(pramtmp).get(0);
			sumColModel.setSumjjcount(cNullTodou(map.get("SUMJJCOUNT")));
			sumColModel.setSumtimeworked(cNullTodou(map.get("SUMTIMEWORKED")));
			sumColModel.setSumtqday(cNullToint(map.get("SUMTQDAY")));
			sumColModel.setSumtql(cNullTodou(map.get("SUMTQL")));
			sumColModel.setSumyqcount(cNullTodou(map.get("SUMYQCOUNT")));
			sumColModel.setSumyqtimeworked(cNullTodou(map.get("SUMYQTIMEWORKED")));
		}
	}
	public void getUserSatisfaction(){
		UserSatisfactionSearchModel userSatisfactionSearchModel = new UserSatisfactionSearchModel();
		userSatisfactionSearchModel.setPager(pagerSatis);
		pagerSatis = getLfSqlService().userSatisfaction(pram, userSatisfactionSearchModel);
	}
	public void getQuality(){
		QualitySearchModel qualitySearchModel = new QualitySearchModel();
		Map<String,Object> result = getLfSqlService().getQuality(pram, qualitySearchModel);
		pagerQuality.setBugcount(cNullToint(result.get("BUGCOUNT")));
		pagerQuality.setDowmtimecount(cNullToint(result.get("DOWNTIMECOUNT")));
		pagerQuality.setErrorcount(cNullToint(result.get("ERRORCOUNT")));
		pagerQuality.setFailurecount(cNullToint(result.get("FAILURECOUNT")));
		pagerQuality.setGjfalsecount(cNullToint(result.get("GJFALSECOUNT")));
		pagerQuality.setGoodprocount(cNullToint(result.get("GOODPROCOUNT")));
		pagerQuality.setInfocount(cNullToint(result.get("INFOCOUNT")));
		pagerQuality.setUnitcount(cNullToint(result.get("UNITCOUNT")));
		pagerQuality.setWarningcount(cNullToint(result.get("WARNINGCOUNT")));
		
	}
	public void getpagerTimeWorked(){
		TimeWorkedSearchModel timeWorkedSearchModel = new TimeWorkedSearchModel();
		pagerTimeWorked = getLfSqlService().getTimeWorked(pram, timeWorkedSearchModel);
	}
	public TimeWorkedModel checkKqDays(String id,int htdays){
		Map<String, String> param = new HashMap<String,String>();
		int qjday = 0;
		int noqjday = 0;
		param.put("userid",id);
		Map<String,Object> soamap = getLfSqlService().getUserNumber(param).get(0);
		if(null!=soamap.get("UMP_USERID"))	{
			soaRelationModel.setUmpUserId(soamap.get("UMP_USERID").toString());
			soaRelationModel.setHaierCaidId(soamap.get("HAIER_CARDID").toString());
			soaRelationModel.setIdmUserId(soamap.get("IDM_USERID").toString());
			soaRelationModel.setIdmUserInfaceEmpno(soamap.get("IDM_USER_INFACE_EMPNO").toString());
			soaRelationModel.setIdmUserMobile(soamap.get("IDM_USERMOBILE").toString());
			soaRelationModel.setIdmUserProsition(soamap.get("IDM_USERPOSITION").toString());
			
			param.put("empsn", soaRelationModel.getHaierCaidId());
			param.put("ygbh", soaRelationModel.getIdmUserId());
			if(getLfSqlService().getKqDay(param)<htdays){
				qjday = getLfSqlService().getQjDay(param);
				noqjday = htdays - qjday;
			}
		}else{
			noqjday = htdays;
		}
		
		timeWorkedModel.setVacationcount(qjday);
		timeWorkedModel.setNeglectworkcount(noqjday);
		return timeWorkedModel;
	}

}
