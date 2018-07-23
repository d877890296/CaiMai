package com.hyf.tdlibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by LK on 2017/8/5 17:59.
 */

public class DateUtils {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  //private static final String startTime = " 00:00:00";
  //private static final String endTime = " 23:59:59";

  // 获得当前日期与本周日相差的天数
  private static int getMondayPlus() {
    Calendar cd = Calendar.getInstance();
    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;//因为按中国礼拜一作为第一天所以这里减1
    if (dayOfWeek == 1) {
      return 0;
    } else {
      return 1 - dayOfWeek;
    }
  }

  //获取当天日期
  public static String getDayStart(){
    Date now = new Date();
    //SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式
    return sdf.format(now);
  }

  //获取当天日期
  public static String getDayEnd(){
    Date now = new Date();
    //SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式
    return sdf.format(now);
  }

  //获得本周一的日期
  public static String getMondayOFWeek(){
    int weeks = 0;
    int mondayPlus = getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus);
    Date monday = currentDate.getTime();
    String preMonday = sdf.format(monday);
    return preMonday;
  }

  //获取当月第一天
  public static String getFirstDayOfMonth(){
    String str = "";
    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE,1);//设为当前月的1号
    str = sdf.format(lastDate.getTime());
    return str;
  }

  public static String getMonth(){
    String str = "";
    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE,1);//设为当前月的1号
    str = sdf.format(lastDate.getTime());
    return str.substring(0, 6);
  }

}
