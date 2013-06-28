package com.haier.openplatform.console.issue.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shanjing
 * 工具类
 */
public class MonitorUtil {

	private static final SimpleDateFormat TIMEID_FMT = new SimpleDateFormat("yyyyMMddHHmm");

	private static final SimpleDateFormat ACTION_FMT = new SimpleDateFormat("yyyy-MM-dd:HH:mm");

	/**
	 * 将日期转换为数据仓库中的TimeId
	 */
	public static long getTimeId(Date date) {
		String timeId = getTimeidFmt().format(date);
		return Long.parseLong(timeId.substring(0, timeId.length() - 1));
	}

	/**
	 * 转换页面输入的起始时间
	 */
	public static Date getDateFrom(String date, String hour, String minute) throws ParseException {
		if (date == null) {
			return null;
		}
		StringBuffer ds = new StringBuffer(date);
		if (hour != null && !"-1".equals(hour)) {
			ds.append(":").append(hour).append(":");
			if (minute != null && !"-1".equals(minute)) {
				ds.append(minute).append("0");
			} else {
				ds.append("00");
			}
		} else {
			ds.append(":00:00");
		}
		return getActionFmt().parse(ds.toString());
	}

	/**
	 * 转换页面输入的结束时间
	 */
	public static Date getDateTo(String date, String hour, String minute) throws ParseException {
		Date from = getDateFrom(date, hour, minute);
		Date to = null;
		if (hour == null || "-1".equals(hour)) {
			to = new Date(from.getTime() + 24 * 60 * 60 * 1000);
			return to;
		}
		if (minute == null || "-1".equals(minute)) {
			to = new Date(from.getTime() + 60 * 60 * 1000);
			return to;
		}
		to = new Date(from.getTime() + 10 * 60 * 1000);
		return to;
	}

	public static SimpleDateFormat getTimeidFmt() {
		return TIMEID_FMT;
	}

	public static SimpleDateFormat getActionFmt() {
		return ACTION_FMT;
	}

}
