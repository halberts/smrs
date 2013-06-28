package com.haier.openplatform.console.issue.webapp.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.console.issue.domain.ProfileInfo;
import com.haier.openplatform.console.issue.module.ProfileSearchModel;
import com.haier.openplatform.console.issue.service.ProfileService;
import com.haier.openplatform.util.Pager;

/**
 * @author WangXuzheng
 *
 */
public class SearchProfileAction extends BaseIssueAction {
	private static final long serialVersionUID = 7538032557576016767L;
	private ProfileSearchModel searchModel = new ProfileSearchModel();
	private Pager<ProfileInfo> pager = new Pager<ProfileInfo>();
	private ProfileService profileService;
	private Map<String,String> propertyMap = new HashMap<String, String>(){
		private static final long serialVersionUID = -6457809167204856410L;
		{
			put("executionTime", "execution_time");
			put("startTime", "start_time");
		}
	};
	@Override
	public String execute() throws Exception {
		//设置排序规则
		if(StringUtils.isNotBlank(this.getSidx())){
			pager.setOrder(this.getSord());
			pager.setOrderProperty(this.propertyMap.get(this.getSidx()));
		}else{
			pager.setOrder("desc");
			pager.setOrderProperty("start_time");
		}
		searchModel.setType(1);//查询action
		searchModel.setPager(pager);
		Pager<ProfileInfo> pager1 = profileService.searchProfileList(searchModel);
		this.setPager(pager1);
		return SUCCESS;
	}
	public ProfileSearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(ProfileSearchModel searchModel) {
		this.searchModel = searchModel;
	}
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	public Pager<ProfileInfo> getPager() {
		return pager;
	}
	public void setPager(Pager<ProfileInfo> pager) {
		this.pager = pager;
	}
}
