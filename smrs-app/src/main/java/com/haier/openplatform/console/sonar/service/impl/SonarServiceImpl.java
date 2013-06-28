package com.haier.openplatform.console.sonar.service.impl;

import hudson.plugins.jira.soap.RemoteIssue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.MetricQuery;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

import com.haier.openplatform.console.jira.domain.SoaSysContrast;
import com.haier.openplatform.console.jira.service.JiraService;
import com.haier.openplatform.console.sonar.dao.SonarDAO;
import com.haier.openplatform.console.sonar.domain.SonarMetric;
import com.haier.openplatform.console.sonar.domain.SonarProject;
import com.haier.openplatform.console.sonar.domain.SonarProjectMetric;
import com.haier.openplatform.console.sonar.service.SonarService;
import com.smrs.util.Env;

public class SonarServiceImpl implements SonarService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SonarServiceImpl.class);
	
	private JiraService jiraService;
	private SonarDAO sonarDAO;
	private String sonarUrl;
	private String sonarUser;
	private String sonarPassword;

	public void setJiraService(JiraService jiraService) {
		this.jiraService = jiraService;
	}

	public void setSonarDAO(SonarDAO sonarDAO) {
		this.sonarDAO = sonarDAO;
	}

	public void setSonarUrl(String sonarUrl) {
		this.sonarUrl = sonarUrl;
	}

	public void setSonarUser(String sonarUser) {
		this.sonarUser = sonarUser;
	}

	public void setSonarPassword(String sonarPassword) {
		this.sonarPassword = sonarPassword;
	}

	@Override
	public void checkProjectIssue(){
		//不是生产环境则不检查sonar及创建jira问题
		if(!"production".equalsIgnoreCase(Env.getProperty(Env.ENV_TYPE))){
			return;
		}
		//检查sonar上是否有新的项目加入
		addProject();
		//查询设置过指标的项目
		Map<String,Map<String,SonarProjectMetric>> pmMap = queryProjectMetric();
		if(pmMap == null || pmMap.isEmpty()){
			return;
		}
		Sonar sonar = Sonar.create(sonarUrl, sonarUser, sonarPassword);
		Iterator<Entry<String, Map<String, SonarProjectMetric>>> entryIt = pmMap.entrySet().iterator();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 7);
		while(entryIt.hasNext()){
			Entry<String, Map<String, SonarProjectMetric>> entry = entryIt.next();
			String[] metrics = entry.getValue().keySet().toArray(new String[0]);
			if(metrics == null || metrics.length < 1){
				continue;
			}
			//从sonar上查询项目的指标数据
			Resource tmpRes = sonar.find(ResourceQuery.createForMetrics(entry.getKey(),metrics));
			if(tmpRes == null || tmpRes.getMeasures() == null || tmpRes.getMeasures().isEmpty()){
				continue;
			}
			//遍历查询到的指标数据
			List<Measure> listMeasure = tmpRes.getMeasures();
			for(Measure mea : listMeasure){
				SonarProjectMetric spm = entry.getValue().get(mea.getMetricKey());
				String actualAppName = entry.getKey().substring(entry.getKey().lastIndexOf(":") + 1);
				SoaSysContrast ssc = jiraService.queryJiraByAppName(actualAppName);
				if(ssc == null){
					break;
				}
				if(spm.getMinValue() != null && NumberUtils.compare(spm.getMinValue(),mea.getValue()) > 0){
					//sonar上的指标数据比设定的最低值还小,则jira上创建问题
					String desc = "Hudson监控平台显示"+actualAppName+"系统"+spm.getMetricName() +  "低于" + spm.getMinValue() + "(当前值" + mea.getValue() + ")，请加强系统质量管理，提高测试脚本质量。";
					RemoteIssue result = jiraService.createIssue(ssc.getJiraKey(), spm.getMetricName()+"不达标", desc,spm.getIssueTypeId(),cal);
					if(result != null){
						LOGGER.error(desc);
					}
				}
				if(spm.getMaxValue() != null && NumberUtils.compare(spm.getMaxValue(),mea.getValue()) < 0){
					//sonar上的指标数据比设定的最高值还大,则jira上创建问题
					String desc = "Hudson监控平台显示"+actualAppName+"系统"+spm.getMetricName() +  "高于" + spm.getMaxValue() + "(当前值" + mea.getValue() + ")，请加强系统质量管理，提高测试脚本质量。";
					RemoteIssue result = jiraService.createIssue(ssc.getJiraKey(), spm.getMetricName()+"不达标", desc,spm.getIssueTypeId(),cal);
					if(result != null){
						LOGGER.error(desc);
					}
				}
			}
		}
		
	}
	
	@Override
	public void addProject(){
		Sonar sonar = Sonar.create(sonarUrl, sonarUser, sonarPassword);
		ResourceQuery rq = new ResourceQuery();
		rq.setIncludeTrends(true);
		rq.setVerbose(true);
		List<Resource> listResource = sonar.findAll(rq);
		SonarProject pro = new SonarProject();
		for(Resource res : listResource){
			if(sonarDAO.queryProjectByKey(res.getKey()) != null){
				continue;
			}
			pro.setKey(res.getKey());
			pro.setName(res.getName());
			pro.setCreateBy("01311917");
			pro.setLastModifiedBy("01311917");
			sonarDAO.saveSonarProject(pro);
		}
	}
	
	@Override
	public void addMetric(){
		Sonar sonar = Sonar.create(sonarUrl, sonarUser, sonarPassword);
		List<Metric> listMetric = sonar.findAll(MetricQuery.all());
		SonarMetric sm = new SonarMetric();
		for(Metric me : listMetric){
			if(sonarDAO.queryMetricByKey(me.getKey()) != null){
				continue;
			}
			sm.setKey(me.getKey());
			sm.setName(me.getName());
			sm.setCreateBy("01311917");
			sm.setLastModifiedBy("01311917");
			sonarDAO.saveSonarMetric(sm);
		}
	}

	@Override
	public Map<String,Map<String,SonarProjectMetric>> queryProjectMetric() {
		Map<String,Map<String,SonarProjectMetric>> map = new HashMap<String,Map<String,SonarProjectMetric>>();
		List<SonarProjectMetric> list = sonarDAO.queryProjectMetric();
		if(list == null || list.isEmpty()){
			return null;
		}
		for(SonarProjectMetric spm : list){
			if(!map.containsKey(spm.getProjectKey())){
				Map<String,SonarProjectMetric> spmMap = new HashMap<String,SonarProjectMetric>();
				map.put(spm.getProjectKey(), spmMap);
			}
			map.get(spm.getProjectKey()).put(spm.getMetricKey(), spm);
		}
		return map;
	}

	@Override
	public void saveSonarProjectMetric(SonarProjectMetric sonarProjectMetric) {
		sonarDAO.saveSonarProjectMetric(sonarProjectMetric);
	}

	@Override
	public int delSonarProjectMetric(Long id) {
		return sonarDAO.delSonarProjectMetric(id);
	}

}
