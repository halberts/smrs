package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.haier.openplatform.console.issue.module.ActionAverageTime;
import com.haier.openplatform.console.issue.module.ActionAverageTimeSearchModel;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.Pager;

/**
 * 搜索超时前十的Action便于优化
 * @author WangJian
 *
 */
public class SearchTopTenProfileAction extends BaseIssueAction{
	private static final long serialVersionUID = 1197934112085526723L;
	private ActionAverageTimeSearchModel searchModel = new ActionAverageTimeSearchModel();
	private Pager<ActionAverageTime> pager = new Pager<ActionAverageTime>();
	private String time;
	private Long appId;
	private Map<String,String> propertyMap = new HashMap<String, String>(){
		private static final long serialVersionUID = -6457809167204856410L;
		{
			put("executionTime", "execution_time");
			put("startTime", "start_time");
		}
	};
	@Override
	public String execute() throws Exception {
		String start = null;
		String end = null;
		DateUtil dt = new DateUtil();
		String pattern = "yyyy-MM-dd";
		if (time != null && searchModel != null) {
			if ("week".equals(time)) { // 同期近三周
				// 本周一
				start = dt.getMondayOFWeek(pattern);
				// 本周日
				end = dt.getCurrentWeekday(pattern);
			} else {
				// 本月第一天
				start = dt.getFirstDayOfMonth(pattern);
				// 本月最后一天
				end = dt.getDefaultDay(pattern);
			}
		}
		//设置排序规则
		/*if(StringUtils.isNotBlank(this.getSidx())){
			pager.setOrder(this.getSord()+","+"desc");
			pager.setOrderProperty(this.propertyMap.get(this.getSidx())+","+"id");
		}else{
			pager.setOrder("desc");
			pager.setOrderProperty("id");
		}*/
		Date startDate =new SimpleDateFormat(pattern).parse(start);
		Date endDate =new SimpleDateFormat(pattern).parse(end);
		searchModel.setType(1);//查询action
		searchModel.setPager(pager);
		searchModel.setFrom(startDate);
		searchModel.setTo(endDate);
		searchModel.setAppId(appId);
		Pager<ActionAverageTime> pager1 = getProfileService().searchTopTenProfileList(searchModel);
		this.setPager(pager1);
		return SUCCESS;
	}
	public ActionAverageTimeSearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(ActionAverageTimeSearchModel searchModel) {
		this.searchModel = searchModel;
	}
	public Pager<ActionAverageTime> getPager() {
		return pager;
	}
	public void setPager(Pager<ActionAverageTime> pager) {
		this.pager = pager;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}
	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}
}
