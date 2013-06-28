package com.haier.openplatform.console.issue.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 分表表名生成器
 * @author WangXuzheng
 *
 */
public class TableSuffixGenerator {
	/**
	 * 生成以日期作为后缀的表名如profile_bean_info_201303
	 * @return
	 */
	public static String generateDateSuffix(){
		return generateDateSuffix(new Date());
	}
	
	public static String generateDateSuffix(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		return new StringBuilder(7).append("_").append(simpleDateFormat.format(date)).toString();
	}
	public static String generateNextMonthDateSuffix(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		return generateDateSuffix(calendar.getTime());
	}
}
