package com.haier.openplatform.console.issue.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.haier.openplatform.console.issue.domain.HopEmail;
import com.haier.openplatform.console.issue.module.HopEmailModel;
import com.haier.openplatform.console.issue.module.IssueModel;
import com.haier.openplatform.console.issue.module.IssueSummaryModel;
import com.haier.openplatform.hmc.domain.Email;
import com.haier.openplatform.hmc.domain.Recipient;
import com.haier.openplatform.hmc.domain.SMS;
import com.haier.openplatform.util.Pager;
import com.smrs.util.DataConvertUtil;
import com.smrs.util.Env;

/**
 * 
 * @author Aaron_Guan
 * 
 */
public class IssueBuilder {
	private static final String ENV_TYPE = "env.type";
	private static final String CHAR_CODE_ISO = "ISO-8859-1";
	private static final String CHAR_CODE_UTF = "UTF-8";

	private String decode(String contentText) {
		String textDecoded = "";
		if (!StringUtils.isEmpty(contentText)) {
			try {

				textDecoded = new String(contentText.getBytes(CHAR_CODE_ISO),
						CHAR_CODE_UTF);
			} catch (Exception ex) {
				/**
				 * ignore this exception deliberately
				 */
			}
		}

		return textDecoded;
	}

	public List<SMS> build(String appName, String serviceName,
			String ownerName, String ownerPhone, String sptOwnerName,
			String sptOwnerPhone, List<Long> issueIds) {
		List<SMS> smsz = new ArrayList<SMS>();

		StringBuffer smsHeader = new StringBuffer();
		smsHeader.append(String.format("您好  %s \n", ownerName));

		StringBuffer smsSptHeader = new StringBuffer();
		smsSptHeader.append(String.format("您好  %s \n", sptOwnerName));

		StringBuffer smsContent = new StringBuffer();
		smsContent.append(String.format("在[%s]环境中我们发现了以下的问题!\n",
				decode(Env.getProperty(ENV_TYPE))));
		smsContent.append(String.format("应用名称:%s\n", appName));
		smsContent.append(String.format("服务api名称:%s\n", serviceName));
		smsContent.append(String.format("问题数量:%s\n", issueIds.size()));
		for (Long issueId : issueIds) {
			smsContent.append(String.format("问题ID:%s\n", issueId));
		}

		SMS smsOwner = new SMS();
		smsOwner.setMsgCode(String.valueOf(System.currentTimeMillis()));

		smsOwner.setMsgContent(smsHeader.toString() + smsContent.toString());
		smsOwner.setPhoneNum(ownerPhone);
		smsz.add(smsOwner);

		SMS smsSpt = new SMS();
		smsSpt.setMsgCode(String.valueOf(System.currentTimeMillis()));
		smsSpt.setMsgContent(smsSptHeader.toString() + smsContent.toString());
		smsSpt.setPhoneNum(sptOwnerPhone);
		smsz.add(smsSpt);

		return smsz;
	}

	public Email build(IssueModel issueModel, String envType, String serviceEx) {
		Email email = new Email();
		List<Recipient> recipients = new ArrayList<Recipient>();
		recipients.add(new Recipient(issueModel.getOwnerEmail(), issueModel.getOwnerName()));
		recipients.add(new Recipient(issueModel.getSptOwnerEmail(), issueModel.getSptOwnerName()));
		email.setToRecipient(recipients);
		email.setSender(new Recipient("HOP监控平台@haier.com", "HOP监控平台"));

		StringBuffer bodyContent = new StringBuffer();
		bodyContent.append("您好 ");
		bodyContent.append(issueModel.getOwnerName()).append(",");
		bodyContent.append(issueModel.getSptOwnerName()==null ? "" : issueModel.getSptOwnerName());
		bodyContent.append("\n");
		bodyContent.append("我们发现了以下的问题!").append("\n");
		bodyContent.append("应用名称:").append(issueModel.getAppName()).append("\n");
		bodyContent.append("服务api名称:").append(issueModel.getServiceName()).append("\n");
		bodyContent.append("问题编码:").append(issueModel.getIssueId()).append("\n");
		bodyContent.append("问题详细:\n").append(serviceEx).append("\n");
		email.setBodyContent(bodyContent.toString(), false);

		email.setSubject(String.format("HOP监控告警邮件-%s-%s", envType, issueModel.getAppName()));

		return email;
	}

	public Email build(List<IssueSummaryModel> issueSumModels) {
		Email email = new Email();

		long issueCounts = 0;
		StringBuffer sumDetail = new StringBuffer();
		Map<String, String> owners = new HashMap<String, String>();
		for (IssueSummaryModel issueSumModel : issueSumModels) {
			if (!owners.containsKey(issueSumModel.getOwnerEmail())) {
				owners.put(issueSumModel.getOwnerEmail(),
						issueSumModel.getOwnerName());
			}

			sumDetail.append(String.format("服务api: %s \n",
					issueSumModel.getApiName()));
			sumDetail.append(String.format("平均执行时间: %s \n",
					issueSumModel.getDuration()));
			sumDetail.append(String.format("所属api共%s个超时问题, 明细: ", issueSumModel
					.getIssueIds().size()));

			for (Long issueId : issueSumModel.getIssueIds()) {
				sumDetail.append(String.format("%s; ", issueId));
				issueCounts++;
			}
		}

		List<Recipient> recipients = new ArrayList<Recipient>();
		StringBuffer ownerNames = new StringBuffer();
		Iterator<String> ownerVals = owners.values().iterator();
		for (String ownerEmail : owners.keySet()) {
			String ownerName = ownerVals.next();

			Recipient recipient = new Recipient(ownerEmail, ownerName);
			recipients.add(recipient);

			ownerNames.append(ownerName);
			ownerNames.append(" ");
		}

		StringBuffer bodyContent = new StringBuffer();
		bodyContent.append(String.format("您好 %s \n", ownerNames.toString()));
		bodyContent.append(String.format("共发现%s个超时问题如下:\n\n", issueCounts));
		bodyContent.append(sumDetail.toString());
		email.setToRecipient(recipients);
		email.setSender(new Recipient("HOP监控平台@haier.com", "HOP监控平台"));
		email.setSubject(String.format("HOP监控超时邮件-%s",
				decode(Env.getProperty(ENV_TYPE))));
		email.setBodyContent(bodyContent.toString(), false);

		return email;
	}

	public Pager<HopEmailModel> build(Pager<HopEmail> emailPager) {
		Pager<HopEmailModel> emailModelPager = new Pager<HopEmailModel>();
		List<HopEmailModel> emailModels = new ArrayList<HopEmailModel>();
		emailModelPager.setRecords(emailModels);
		emailModelPager.setOrder(emailPager.getOrder());
		emailModelPager.setOrderProperty(emailPager.getOrderProperty());
		emailModelPager.setCountTotal(emailPager.isCountTotal());
		emailModelPager.setCurrentPage(emailPager.getCurrentPage());
		emailModelPager.setPageSize(emailPager.getPageSize());
		emailModelPager.setTotalRecords(emailPager.getTotalRecords());

		/**
		 * convert clob to string type
		 */
		for (HopEmail email : emailPager.getRecords()) {
			String content = DataConvertUtil.doClobToString(email
					.getClobContent());

			HopEmailModel emailModel = new HopEmailModel();
			emailModel.setId(email.getId());
			emailModel.setSubject(email.getSubject());
			emailModel.setContent(content);
			emailModel.setError(email.getError());
			emailModel.setEmailSendTime(email.getEmailSendTime());
			emailModel.setMsgSendTime(email.getMsgSendTime());
			emailModel.setSendTime(email.getSendTime());
			emailModel.setSystem(email.getSystem());
			emailModel.setCreateBy(email.getCreateBy());

			emailModels.add(emailModel);
		}

		return emailModelPager;
	}

	public Pager<HopEmail> buildPager(Pager<HopEmailModel> pagerModels) {
		Pager<HopEmail> emailModelPager = new Pager<HopEmail>();
		emailModelPager.setOrder(pagerModels.getOrder());
		emailModelPager.setOrderProperty(pagerModels.getOrderProperty());
		emailModelPager.setCountTotal(pagerModels.isCountTotal());
		emailModelPager.setCurrentPage(pagerModels.getCurrentPage());
		emailModelPager.setPageSize(pagerModels.getPageSize());
		emailModelPager.setTotalRecords(pagerModels.getTotalRecords());

		return emailModelPager;
	}
}
