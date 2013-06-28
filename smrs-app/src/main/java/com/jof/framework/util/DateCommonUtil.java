package com.jof.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期计算工具类
 * @author Vilight_Wu
 *
 */
public class DateCommonUtil {

	// 计算当月最后一天,返回字符串   
	public String getDefaultDay(String dateformat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号   
		lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号   
		lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天   

		str = sdf.format(lastDate.getTime());
		return str;
	}


	//获取当月第一天   
	public String getFirstDayOfMonth(String dateformat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号   
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期     
	public String getCurrentWeekday(String dateformat) {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	//获取当天时间    
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式      
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数   
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......   
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; //因为按中国礼拜一作为第一天所以这里减1   
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	//获得本周一的日期   
	public String getMondayOFWeek(String dateformat) {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		String preMonday = sdf.format(monday);
		return preMonday;
	}


}
