package com.smrs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Cron日期时间格式化
 *
 * @author WangJian
 */
public abstract class DateCommonUtil {

  private static final ThreadLocal<Map<String, DateFormat>> THREADLOCAL = new ThreadLocal<Map<String, DateFormat>>() {

    protected Map<String, DateFormat> initialValue() {
      return new HashMap<String, DateFormat>();
    }
  };

  public static DateFormat getDateFormat(String pattern) {
    DateFormat dateFormat = THREADLOCAL.get().get(pattern);
    if (dateFormat == null) {
      dateFormat = new SimpleDateFormat(pattern);
      THREADLOCAL.get().put(pattern, dateFormat);
    }
    return dateFormat;
  }

  public static Date parse(String pattern, String textDate) throws ParseException {
    return getDateFormat(pattern).parse(textDate);
  }

  public static String format(String pattern, Date date) {
    return getDateFormat(pattern).format(date);
  }
  /**
   * 取得当前日期的上/下几周的周几
   * @param week
   * @return
   */
  public static Date getDayOfWeekDate(int week,int dayOfWeek){
	  Calendar cal = Calendar.getInstance();
	  cal.setFirstDayOfWeek(Calendar.MONDAY);
	  cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
	  cal.add(Calendar.WEEK_OF_MONTH, week);
	  return cal.getTime();
  }
  
	public static Date getMaxDate(){//9999-12-31
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 9999);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();		
	}
}
