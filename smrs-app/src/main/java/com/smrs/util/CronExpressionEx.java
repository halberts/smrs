package com.smrs.util;

import java.text.ParseException;
import java.util.Set;
import java.util.StringTokenizer;
import org.quartz.CronExpression;

/**
 *Cron表达式生成bean
 * @author WangJian
 */
public class CronExpressionEx extends CronExpression { 
  public static final Integer ALL_SPEC = Integer.valueOf(ALL_SPEC_INT); 
  public static final int NO_SPEC_INT = 98; // '?'
  private static final long serialVersionUID = -4272522788001616933L;

  private String secondsExp;
  private String minutesExp;
  private String hoursExp;
  private String daysOfMonthExp;
  private String monthsExp;
  private String daysOfWeekExp;

  public CronExpressionEx(String cronExpression) throws ParseException {
    super(cronExpression);

    StringTokenizer exprsTok = new StringTokenizer(cronExpression, " \t", false);
    secondsExp = exprsTok.nextToken().trim();
    minutesExp = exprsTok.nextToken().trim();
    hoursExp = exprsTok.nextToken().trim();
    daysOfMonthExp = exprsTok.nextToken().trim();
    monthsExp = exprsTok.nextToken().trim();
    daysOfWeekExp = exprsTok.nextToken().trim();
  }

  @SuppressWarnings("rawtypes")
public Set getSecondsSet() {
    return seconds;
  }

  public String getSecondsField() {
    return getExpressionSetSummary(seconds);
  }

  @SuppressWarnings("rawtypes")
public Set getMinutesSet() {
    return minutes;
  }

  public String getMinutesField() {
    return getExpressionSetSummary(minutes);
  }

  @SuppressWarnings("rawtypes")
public Set getHoursSet() {
    return hours;
  }

  public String getHoursField() {
    return getExpressionSetSummary(hours);
  }

  @SuppressWarnings("rawtypes")
public Set getDaysOfMonthSet() {
    return daysOfMonth;
  }

  public String getDaysOfMonthField() {
    return getExpressionSetSummary(daysOfMonth);
  }

  @SuppressWarnings("rawtypes")
public Set getMonthsSet() {
    return months;
  }

  public String getMonthsField() {
    return getExpressionSetSummary(months);
  }

  @SuppressWarnings("rawtypes")
public Set getDaysOfWeekSet() {
    return daysOfWeek;
  }

  public String getDaysOfWeekField() {
    return getExpressionSetSummary(daysOfWeek);
  }

  public String getSecondsExp() {
    return secondsExp;
  }

  public String getMinutesExp() {
    return minutesExp;
  }

  public String getHoursExp() {
    return hoursExp;
  }

  public String getDaysOfMonthExp() {
    return daysOfMonthExp;
  }

  public String getMonthsExp() {
    return monthsExp;
  }

  public String getDaysOfWeekExp() {
    return daysOfWeekExp;
  }
}
