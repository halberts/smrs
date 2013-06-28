package com.haier.openplatform.console.jira.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.haier.openplatform.console.jira.dao.JiraOracleBaseDAO;
import com.haier.openplatform.console.jira.module.AvetimeModel;
import com.haier.openplatform.console.jira.module.HealthCountModel;
import com.haier.openplatform.console.jira.module.SysExctModel;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.util.Pager;

/**
 * @author ben
 */
public class JiraOracleBaseDAOImpl extends BaseDAOHibernateImpl<Object, Long> implements JiraOracleBaseDAO {

	@Override
	public List<Map<String, Object>> getSuggest(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql ="select * from (select service_api_id,service_api_nm,max(gmt_create)-min(gmt_create) yday from (" +
				" select  hid.service_api_id ,ba.service_api_nm ,hid.issue_type_id ,it.nm," +
				" to_date(to_char(hid.gmt_create,'yyyy-mm-dd'),'yyyy-mm-dd') gmt_create from hop_issue_detail hid" +
				" join business_service  bs on bs.id = HID.SERVICE_API_ID " +
				"  join  issue_type it on it.id = hid.ISSUE_TYPE_ID left join  BUSINESS_SERVICE ba on ba.id = hid.service_api_id "+
				" where hid.ISSUE_TYPE_ID in ('1','2') and bs.appid  in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey").toString()+"') " +
				"  and hid.gmt_create>to_date('"+pram.get("start").toString()+"','yyyy-mm-dd') and hid.gmt_create<to_date('"+pram.get("end").toString()+"','yyyy-mm-dd')"+
				" group by hid.issue_type_id,hid.service_api_id ,to_char(hid.gmt_create,'yyyy-mm-dd'),it.nm,ba.service_api_nm)group by service_api_id,service_api_nm)" +
				" where yday>0 order by yday desc";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	@Override
	public List<Map<String, Object>> getSUrlStatus(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql = "select * from ( select url_id,errday,(max(errtime)-min(errtime))*24*60 errtime from (" +
				" select url_id,to_date(to_char(tb1.last_modified_date,'yyyy-mm-dd'),'yyyy-mm-dd') errday," +
				"  to_date(to_char(tb1.last_modified_date, 'HH24:MI'), 'HH24:MI') errtime" +
				"  from app_health_history tb1 join APP_HEALTH_URL tb2 on tb2.id = TB1.URL_ID" +
				" where app_id in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey").toString()+"') and url_status='3'" +
				" and TO_char(tb1.last_modified_date , 'HH24:MI')  BETWEEN '08:30' AND '19:00'" +
				" and tb1.last_modified_date >to_date('"+pram.get("start").toString()+"','yyyy-mm-dd') " +
				" and tb1.last_modified_date < to_date('"+pram.get("end").toString()+"','yyyy-mm-dd')"+
				" group by url_id,tb1.last_modified_date)  group by errday,url_id) where errtime > 1";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	@Override
	public int getErrorCount(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql = " select  nvl(count(tb1.id),0) idcount  from app_health_history tb1" +
				" join APP_HEALTH_URL tb2 on tb2.id = TB1.URL_ID where " +
				" app_id in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey").toString()+"') and url_status='3'" +
				" and tb1.last_modified_date >to_date('"+pram.get("start").toString()+"','yyyy-mm-dd') " +
				" and tb1.last_modified_date < to_date('"+pram.get("end").toString()+"','yyyy-mm-dd')"+
				" group by tb1.url_id";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(q.list().size()==0){
			return 0;
		}
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("IDCOUNT").toString());
	}


	@Override
	public double getServiceBfb(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql = " select nvl((( select count(*) from business_service " +
				" where ot_lv > 'LV3'  and appid in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey").toString()+"') ))" +
				" /(case when (select count(*) from business_service where appid in " +" (select hopconsoleid from soa_sys_contrast where umpkey='"+pram.get("pkey").toString()+"'))=0 then 1 end),0) " +
				" as servicbfb from dual";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		double d = Double.parseDouble((map.get("SERVICBFB").toString()))*100;
		return d;//( select count(*) from business_service where appid in " +" (select hopconsoleid from soa_sys_contrast where umpkey='"+pram.get("pkey").toString()+"') )
	}


	@Override
	public int getAverageTime(Map<String, Object> pram) {
		// TODO Auto-generated method stub
	String sql = "	select nvl(averageTime,0)  from actionage where upper(umpkey)='"+pram.get("pkey").toString()+"' AND averageTime>2000" +
				" and gmt_create >to_date('"+pram.get("start").toString()+"','yyyy-mm-dd') " +
				" and gmt_create < to_date('"+pram.get("end").toString()+"','yyyy-mm-dd')";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(q.list().size()>0){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int getErrCount(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql = "select  nvl(count(*),0) issueCount from hop_issue_detail hid " +
				" join business_service  bs on bs.id = HID.SERVICE_API_ID where hid.ISSUE_TYPE_ID in ('1','2') and " +
				" appid in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey").toString()+"') ";//
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("ISSUECOUNT").toString());
	}

	@Override
	public int getBugCount(Map<String, Object> pram) {
		// TODO Auto-generated method stub
//		System.out.println("----------------------------------"+pram.get("pkey"));
//		System.out.println("----------------------------------"+pram.get("start"));
//		System.out.println("----------------------------------"+pram.get("end"));
		String sql="select tt1.bugcount+tt2.bugcount bugcount " 
				+ " 	from " 
				+ "	(select count(1) bugcount from " 
				+ " 		(select count(1),ex_id from " 
				+ "			(select distinct to_char(hid.gmt_create,'yyyy-MM-dd') createdate,hid.ex_id " 
				+ "				from hop_issue_detail hid " 
				+ " 				join business_service bs on bs.id = HID.SERVICE_API_ID " 
				+ " 				where hid.ISSUE_TYPE_ID in ('1','2') " 
				+ " 					and appid in " 
				+ " 						(select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey")+"') " 
				+ " 					and hid.gmt_create between to_date('"+pram.get("start")+"','yyyy-MM-dd') and to_date('"+pram.get("end")+"','yyyy-MM-dd')" 
				+ "			) group by ex_id having count(1)>3" 
				+ "		)" 
				+ "	) tt1 " 
				+ " 	join " 
				+ " 	(select case when t2.totalcount=0 then 0" 
				+ " 				when floor((t1.badcount/t2.totalcount)*100)<10 then 0 " 
				+ " 				else floor((t1.badcount/t2.totalcount)*100)-10 " 
				+ " 				end bugcount " 
				+ " 		from " 
				+ " 		(select count(1) badcount " 
				+ "			from app_health_history ahh join app_health_url ahu on ahh.url_id=ahu.id " 
				+ " 			where ahu.app_id in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey")+"') " 
				+ " 			and ahh.last_modified_date between to_date('"+pram.get("start")+"','yyyy-MM-dd') and to_date('"+pram.get("end")+"','yyyy-MM-dd') " 
				+ " 			and ahh.url_status=3) t1 " 
				+ " 		join " 
				+ " 		(select count(1) totalcount " 
				+ "			from app_health_history ahh join app_health_url ahu on ahh.url_id=ahu.id " 
				+ " 			where ahu.app_id in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey")+"') " 
				+ " 			and ahh.last_modified_date between to_date('"+pram.get("start")+"','yyyy-MM-dd') and to_date('"+pram.get("end")+"','yyyy-MM-dd') " 
				+ " 		) t2 on 1=1" 
				+ "	) tt2 " 
				+ " 	on 1=1";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(q.list().size()>0){
			Map<String,Object> map = (Map<String, Object>)q.list().get(0);
			return Integer.parseInt(map.get("BUGCOUNT").toString());
		}
		return 0;
	}

	@Override
	public int getWarCount(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql = "select  nvl(count(*),0) warCount from hop_issue_detail hid " +
				" join business_service  bs on bs.id = HID.SERVICE_API_ID where hid.ISSUE_TYPE_ID ='3' and " +
				" appid in (select hopconsoleid from soa_sys_contrast where upper(umpkey)='"+pram.get("pkey").toString()+"')";//
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("WARCOUNT").toString());
	}

	@Override
	public double getFapi(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql = "select (1- ( SELECT COUNT(*) FROM (select distinct T.SERVICE_API_ID from service_api_status_detail t" +
				" join business_service bs on t.service_api_id = bs.id and bs.appid in (select hopconsoleid from soa_sys_contrast  where upper(umpkey)='"+pram.get("pkey").toString()+"'))" +
				" )/case when (SELECT COUNT(*) FROM business_service bs1 " +" where bs1.appid in (select hopconsoleid from soa_sys_contrast  where umpkey='"+pram.get("pkey").toString()+"'))=0 then 1 end) fapi from dual";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("FAPI").toString());//(SELECT COUNT(*) FROM business_service bs1 " +" where bs1.appid in (select hopconsoleid from soa_sys_contrast  where umpkey='"+pram.get("pkey").toString()+"'))
	}

	@Override
	public int getKqDay(Map<String, String> pram) {
		// TODO Auto-generated method stub
		String sql = "select count(1) kqday  from soa.i_kq_kqresult where" +
				" (to_date(xiabantime,'HH24:mi')-to_date(shangbantime,'HH24:mi'))*24>8 and " +
				"  empsn in ('"+pram.get("empsn").replaceAll(",", "','")+"') ";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("KQDAY").toString());
	}
	
	@Override
	public int getQjDay(Map<String, String> pram) {
		// TODO Auto-generated method stub
		String sql = "select count(1) qjday from soa.i_aws_wbkqyc where ZTBG='请假' and ygbh in ('"+pram.get("ygbh").replaceAll(",", "','")+"') ";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("QJDAY").toString());
	}
//系统异常数量饼状图
	@Override
	public List<Map<String, Object>> getSysExct(Map<String, Object> pram) {
		String sql ="select t1.umpkey,round(t1.errcount/t2.totalcount,2) rate from " +
				" (select upper(ssc.umpkey) umpkey,count(1) errcount from hop_issue_detail hid " +
				" join business_service bs on bs.id = HID.SERVICE_API_ID " +
				" join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc " + 
				" on bs.appid=ssc.hopconsoleid  " +
				" where hid.ISSUE_TYPE_ID in ('1','2')  " +
				" and hid.gmt_create between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd') " +
				" group by ssc.umpkey ) t1 " +
				" join " +
				" (select count(1) totalcount from hop_issue_detail hid " +
				" join business_service bs on bs.id = HID.SERVICE_API_ID " +
				" join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc " + 
				" on bs.appid=ssc.hopconsoleid  " +
				" where hid.ISSUE_TYPE_ID in ('1','2')  " +
				" and hid.gmt_create between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')) t2 " +
				" on 1=1" +
				" order by rate DESC";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	//系统异常数量线状图
	@Override
	public List<Map<String, Object>> getExctLine(Map<String, Object> pram) {
		String sql ="select upper(ssc.umpkey) umpkey,count(1) errcount from hop_issue_detail hid   " +
				" join business_service bs on bs.id = HID.SERVICE_API_ID   " +
				" join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc  " +  
				"  on bs.appid=ssc.hopconsoleid    " +
				" where hid.ISSUE_TYPE_ID in ('1','2')  " +  
				" and to_char(hid.gmt_create,'yyyy-MM')='"+pram.get("starttime").toString()+"' " +
				"and  upper(umpkey)='"+pram.get("pkey").toString()+"'" +
				" group by ssc.umpkey";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
//服务器健康异常数量饼状图
	@Override
	public List<Map<String, Object>> getHealthExct(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		String sql ="select t1.umpkey,round(t1.errcount/t2.totalcount,2) rate from  " +
				" (select upper(ssc.umpkey) umpkey,count(1) errcount from app_health_history ahh " +
				" join app_health_url ahu on ahh.url_id=ahu.id   " +
				" join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc on ahu.app_id=ssc.hopconsoleid  " +
				" where  ahh.last_modified_date between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')  " +
				" and ahh.url_status=3  " +
				" group by ssc.umpkey) t1  " +
				" join   " +
				" (select count(1) totalcount  from app_health_history ahh " +
				" join app_health_url ahu on ahh.url_id=ahu.id   " +
				" join (select distinct hopconsoleid from soa_sys_contrast) ssc on ahu.app_id=ssc.hopconsoleid  " +
				" where ahh.last_modified_date between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')  " +
				" and ahh.url_status=3) t2  " +
				" on 1=1" +
				" order by rate DESC";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	//服务器健康异常数量线性图
	@Override
	public List<Map<String, Object>> getHealthLine(Map<String, Object> pram) {
		String sql ="select upper(ssc.umpkey) umpkey,count(1) errcount from app_health_history ahh join app_health_url ahu on ahh.url_id=ahu.id   " +
				" join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc on ahu.app_id=ssc.hopconsoleid  " +
				" where  to_char(ahh.last_modified_date,'yyyy-MM')='"+pram.get("starttime").toString()+"'  " +
				" and ahh.url_status=3 and upper(ssc.umpkey)='"+pram.get("pkey").toString()+"'  " +
				" group by ssc.umpkey";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return  q.list();
	}
//平均响应时间饼状图
	@Override
	public List<Map<String, Object>> getAveTimeCake(Map<String, Object> pram) {
		String sql ="select t1.umpkey,round(t1.avetime/t2.sumtime,4) rate from   " +
				" (select umpkey,sum(nvl(averageTime,0)) avetime from actionage where  " +
				" gmt_create between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')  " + 
				" and upper(umpkey) is not null  group by umpkey)t1  " +
				" join   " +
				" (select sum(nvl(averageTime,0)) sumtime from actionage where   " +
				" gmt_create between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')  " +
				" and upper(umpkey) is not null)t2  " +
				" on 1=1 " +
				" order by rate DESC";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	//平均响应时间线状图
	@Override
	public List<Map<String, Object>> getAveTimeLine(Map<String, Object> pram) {
		String sql ="select umpkey,round(sum(nvl(averageTime,0))/count(actionname),2) errcount from actionage where  " +
				" to_char(gmt_create,'yyyy-MM')= '"+pram.get("starttime").toString()+"'  " +
				" and upper(umpkey) is not null and upper(umpkey)='"+pram.get("pkey").toString()+"'  " +
				" group by umpkey";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return  q.list();
	}
//查询的结果展示
	@Override
	public List<Map<String, Object>> getSysAllCount(Map<String, Object> pram) {
		String sql ="select nvl(t1.errcount,0) avetime,nvl(t2.errcount,0) healthcount, nvl(t3.errcount,0) sysexct from   " +
				" (select umpkey,round(sum(nvl(averageTime,0))/count(actionname),2) errcount from actionage " +
				" where gmt_create between  to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and  to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')    " +
				" and upper(umpkey) is not null and upper(umpkey)='"+pram.get("pkey").toString()+"'" +
				" group by umpkey)t1 " +
				" full join " +
				" (select upper(ssc.umpkey) umpkey,count(1) errcount from app_health_history ahh join app_health_url ahu on ahh.url_id=ahu.id  " +
				" join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc on ahu.app_id=ssc.hopconsoleid     " +
				"  where  ahh.last_modified_date between  to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and  to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')  " +    
				" and ahh.url_status=3 and upper(ssc.umpkey)='"+pram.get("pkey").toString()+"' " + 
				" group by ssc.umpkey) t2 on 1=1  " +
				" full join     " +
				" (select upper(ssc.umpkey) umpkey,count(1) errcount from hop_issue_detail hid " +   
				" join business_service bs on bs.id = HID.SERVICE_API_ID  " + 
				" join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc " + 
				" on bs.appid=ssc.hopconsoleid   " +    
				"  where hid.ISSUE_TYPE_ID in ('1','2')  " +     
				" and hid.gmt_create between  to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and  to_date('"+pram.get("end").toString()+"','yyyy-MM-dd')  " +
				" and  upper(umpkey)='"+pram.get("pkey").toString()+"' " + 
				" group by ssc.umpkey) t3  " +
				" on 1=1";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return  q.list();
	}
//平均响应时间报表
	@Override
	public List<AvetimeModel> getAveTimeTable(Map<String, Object> pram) {
		Pager<AvetimeModel> pagerAve = new Pager<AvetimeModel>();
		List<Map<String, Object>> aveTimeMap = new ArrayList<Map<String, Object>>();
		List<AvetimeModel> liave = new ArrayList<AvetimeModel>();
		String sql =" select nvl(actionname,0) actionname,round(nvl(max(averagetime),0),2) time from actionage where upper(umpkey)='"+pram.get("pkey").toString()+"' "+
				" and gmt_create between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd')and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd') group by actionname" +
						" order by time DESC";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		aveTimeMap=q.list();
		for(int i=0;i<aveTimeMap.size();i++){
			AvetimeModel avetimeModel=new AvetimeModel();
			String actname=aveTimeMap.get(i).get("ACTIONNAME").toString();
			double time=Double.parseDouble(aveTimeMap.get(i).get("TIME").toString());
			avetimeModel.setName(actname);
			avetimeModel.setTc(time);
			liave.add(avetimeModel);	
		}
		return liave;
		
	}
	//异常数报表
	@Override
	public List<SysExctModel> getExctTable(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		Pager<SysExctModel> pagerExc = new Pager<SysExctModel>();
		List<Map<String, Object>> errCountMap = new ArrayList<Map<String, Object>>();
		List<SysExctModel> liave = new ArrayList<SysExctModel>();
		String sql ="select bs.SERVICE_API_NM errname,count(SERVICE_API_NM) ERRCOUNT from hop_issue_detail hid "+ 
         " join business_service bs on bs.id = HID.SERVICE_API_ID   "+
         " join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc  "+  
         " on bs.appid=ssc.hopconsoleid "+
         " where hid.ISSUE_TYPE_ID in ('1','2')  "+  
         " and hid.gmt_create between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd') "+  
         " and upper(ssc.umpkey)='"+pram.get("pkey").toString()+"' "+
         " group by SERVICE_API_NM" +
         " order by ERRCOUNT DESC";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		errCountMap=q.list();
		for(int i=0;i<errCountMap.size();i++){
			SysExctModel sysExctModel=new SysExctModel();
			String errName=errCountMap.get(i).get("ERRNAME").toString();
			int errcount=Integer.parseInt(errCountMap.get(i).get("ERRCOUNT").toString());
			sysExctModel.setName(errName);
			sysExctModel.setTc(errcount);
			liave.add(sysExctModel);
		}
		return liave;
	}
	//健康异常数报表
	@Override
	public List<HealthCountModel> getHealthTable(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		Pager<HealthCountModel> pagerHeal = new Pager<HealthCountModel>();
		List<Map<String, Object>> errCountMap = new ArrayList<Map<String, Object>>();
		List<HealthCountModel> liave = new ArrayList<HealthCountModel>();
		String sql="select to_char(ahh.create_date,'yyyy-MM-dd') time,count(to_char(ahh.create_date,'yyyy-MM-dd')) errcount from app_health_history ahh "+  
				 " join app_health_url ahu on ahh.url_id=ahu.id "+    
				 " join (select distinct hopconsoleid,umpkey from soa_sys_contrast) ssc on ahu.app_id=ssc.hopconsoleid "+   
				 " where  ahh.last_modified_date between to_date('"+pram.get("start").toString()+"','yyyy-MM-dd') and to_date('"+pram.get("end").toString()+"','yyyy-MM-dd') "+   
				 " and ahh.url_status=3 and upper(ssc.umpkey)='"+pram.get("pkey").toString()+"' "+  
				 " group by to_char(ahh.create_date,'yyyy-MM-dd')" +
				 " order by time desc";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		errCountMap=q.list();
		for(int i=0;i<errCountMap.size();i++){
			HealthCountModel healthCountModel =new HealthCountModel();
			String time=errCountMap.get(i).get("TIME").toString();
			int errcount=Integer.parseInt(errCountMap.get(i).get("ERRCOUNT").toString());
			healthCountModel.setName(time);
			healthCountModel.setTc(errcount);
			liave.add(healthCountModel);
		}
		return liave;
	}
}

