package com.smrs.security.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.haier.openplatform.excel.AbstractExcelExportTemplate;
import com.jof.framework.util.Pager;
import com.jof.framework.util.SpringApplicationContextHolder;
import com.smrs.security.enums.UserStatusEnum;
import com.smrs.security.enums.UserTypeEnum;
import com.smrs.security.model.UserModel;
import com.smrs.security.service.UserService;
import com.smrs.security.vo.UserSearchModel;

/**
 * 导出用户信息到excel中-单sheet
 * @author WangXuzheng
 *
 */
public class ExportUserListTemplate extends AbstractExcelExportTemplate<UserModel> {
	private final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public String[] getSheetNames() {
		return new String[]{"用户列表"};
	}

	@Override
	public String[][] getTitles() {
		return new String[][]{{"ID","登录名","用户名","邮箱","账号类型","账号状态","创建时间","修改时间","创建人","修改人"}};
	}

	@Override
	public String[] getCaptions() {
		return new String[]{"用户列表明细"};
	}

	@Override
	protected void buildBody(int sheetIndex) {
		//查出要符合结果的记录集
		Pager<UserModel> pager = new Pager<UserModel>();
		pager.setPageSize(Long.valueOf(Integer.MAX_VALUE));
		pager.setCountTotal(false);
		UserSearchModel userSearchModel = new UserSearchModel();
		userSearchModel.setPager(pager);
		userSearchModel.setUser(this.parameters);
		UserService userService = SpringApplicationContextHolder.getBean("userService");
		Pager<UserModel> users = userService.searchUser(userSearchModel);
		List<UserModel> userList = users.getRecords();
		
		Sheet sheet = getSheet(sheetIndex);
		final int size = userList.size();
		int startIndex = this.getBodyStartIndex(sheetIndex);
		for(int i = 0; i < size; i++){
			Row row = sheet.createRow(i+startIndex); 
			row.setHeight((short)300);
			int index = 0;
			UserModel user = userList.get(i);
			createStyledCell(row,index++,user.getId().toString(),this.bodyRowStyle);
			createStyledCell(row,index++,user.getName(),this.bodyRowStyle);
			createStyledCell(row,index++,user.getNickName(),this.bodyRowStyle);
			createStyledCell(row,index++,user.getEmail(),this.bodyRowStyle);
			createStyledCell(row,index++,UserTypeEnum.toEnum(user.getType()).getName(),this.bodyRowStyle);
			createStyledCell(row,index++,UserStatusEnum.toEnum(user.getStatus()+"").getDescription(),this.bodyRowStyle);
			createStyledCell(row,index++,defaultDateFormat.format(user.getCreationDate()),this.bodyRowStyle);
			createStyledCell(row,index++,defaultDateFormat.format(user.getLastModifyDate()),this.bodyRowStyle);
			createStyledCell(row,index++,user.getCreator(),this.bodyRowStyle);
			createStyledCell(row,index++,user.getModifiedBy(),this.bodyRowStyle);
		}
	}
}
