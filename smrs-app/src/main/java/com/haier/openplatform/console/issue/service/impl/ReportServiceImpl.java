package com.haier.openplatform.console.issue.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.dao.ReportDAO;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.BusinessServiceGrp;
import com.haier.openplatform.console.issue.module.ApiCallGroupByLevel;
import com.haier.openplatform.console.issue.module.AppApiIssue;
import com.haier.openplatform.console.issue.module.AppCall;
import com.haier.openplatform.console.issue.module.AppIssueSearcher;
import com.haier.openplatform.console.issue.module.AppOvertime;
import com.haier.openplatform.console.issue.module.IssueType;
import com.haier.openplatform.console.issue.module.ServiceGroupIssue;
import com.haier.openplatform.console.issue.module.ShowIssue;
import com.haier.openplatform.console.issue.service.ReportService;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 */
public class ReportServiceImpl implements ReportService {

	private ReportDAO reportDAO;

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	@Override
	public List<ApiCallGroupByLevel> getAllApiOvertimeGroupByLevel() {
		return reportDAO.getAllApiOvertimeGroupByLevel();
	}

	@Override
	public List<ApiCallGroupByLevel> getAllApiOvertimeTopTen(Date from, Date to, Long topNum) {
		return reportDAO.getAllApiOvertimeTopTen(from, to, topNum);
	}

	@Override
	public Pager<AppOvertime> getAppOvertimeTopTen(Long appId, Date from, Date to, Pager<AppOvertime> pager) {
		return reportDAO.getAppOvertimeTopTen(appId, from, to, pager);
	}

	@Override
	public List<AppCall> getAllApiCallGroupByApp(Long appId, Date from, Date to) {
		return reportDAO.getAllApiCallGroupByApp(appId, from, to);
	}

	@Override
	public Map<IssueType, Map<String, ShowIssue>> getAppIssue(Long appId, Date from, Date to) {
		List<AppCall> rs = reportDAO.getAppIssue(appId, from, to);
		Map<IssueType, Map<String, ShowIssue>> map = new HashMap<IssueType, Map<String, ShowIssue>>();
		if (rs == null || rs.size() == 0) {
			return map;
		}
		for (AppCall ac : rs) {
			if (ac.getAppId() < 50) {
				continue;
			}

			if (map.containsKey(IssueType.getIssueType(ac.getIssueTypeId().intValue()))) {
				Map<String, ShowIssue> subMap = map.get(IssueType.getIssueType(ac.getIssueTypeId().intValue()));
				if (!subMap.containsKey(ac.getAppName())) {
					subMap.put(ac.getAppName(), new ShowIssue(ac.getAppId(), ac.getNum()));
				}
			} else {
				Map<String, ShowIssue> subMap = new HashMap<String, ShowIssue>();
				subMap.put(ac.getAppName(), new ShowIssue(ac.getAppId(), ac.getNum()));
				map.put(IssueType.getIssueType(ac.getIssueTypeId().intValue()), subMap);
			}
		}
		return map;
	}

	@Override
	public Pager<AppApiIssue> getAppApiIssueTopTen(AppIssueSearcher searcher) {
		return reportDAO.getAppApiIssueTopTen(searcher);
	}

	@Override
	public void addBusinessServiceGrp(String serviceGrpNm, String serviceGrpDetail, long appId) {
		reportDAO.addBusinessServiceGrp(serviceGrpNm, serviceGrpDetail, appId);
	}

	@Override
	public void updateBusinessServiceGrp(BusinessServiceGrp pojo) {
		reportDAO.updateBusinessServiceGrp(pojo);
	}

	@Override
	public void deleteBusinessServiceGrp(long id) {
		reportDAO.deleteBusinessServiceGrp(id);
	}

	@Override
	public BusinessServiceGrp getBusinessServiceGrp(long id) {
		return reportDAO.getBusinessServiceGrp(id);
	}

	@Override
	public List<BusinessServiceGrp> getAllBusinessServiceGrp(long appId) {
		return reportDAO.getAllBusinessServiceGrp(appId);
	}

	@Override
	public List<ServiceGroupIssue> getServiceGroupIssue(Date from, Date to, long appId) {
		return reportDAO.getServiceGroupIssue(from, to, appId);
	}

	@Override
	public List<BusinessService> getBusinessService(List<Long> businessServiceIds) {
		return reportDAO.getBusinessService(businessServiceIds);
	}

	@Override
	public BusinessService getBusinessService(Long id) {
		return reportDAO.getBusinessService(id);
	}

	@Override
	public void updateBusinessService(BusinessService bs) {
		reportDAO.updateBusinessService(bs);
	}

	@Override
	public List<BusinessService> getBusinessService(Long appId, Long groupId) {
		return reportDAO.getBusinessService(appId, groupId);
	}

}
