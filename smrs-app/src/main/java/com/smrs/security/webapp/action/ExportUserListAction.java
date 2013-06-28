package com.smrs.security.webapp.action;

import com.haier.openplatform.excel.ExcelExportTemplate;
import com.haier.openplatform.util.Servlets;
import com.smrs.security.model.UserModel;
import com.smrs.security.service.impl.ExportUserListTemplate;

/**
 * 将用户列表导出到excel中
 * @author WangXuzheng
 *
 */
public class ExportUserListAction extends SearchUserAction {
	private static final long serialVersionUID = -6090751127379506159L;

	@Override
	public String execute() throws Exception {
		getResponse().setContentType(Servlets.EXCEL_TYPE);
		Servlets.setFileDownloadHeader(getRequest(),getResponse(), "用户列表.xlsx");
		ExcelExportTemplate<UserModel> exportUserListTemplate = new ExportUserListTemplate();
		exportUserListTemplate.doExport(getResponse().getOutputStream(), this.getUser());
		return SUCCESS;
	}
}
