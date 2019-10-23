package cn.xiongdi.ocdm.common.utils;

import cn.xiongdi.ocdm.common.constants.Constant;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 汪礼
 * @创建时间 2018-09-12
 * @描述 日期处理
 */
public class DateUtil {

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间
	 */
	public static final Date now() {
		return new Date();
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间
	 */
	public static final Timestamp nowSqlTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间
	 */
	public static final Calendar nowCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 当前时间
	 */
	public static final java.sql.Date nowSqlData() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * <p>
	 * 获取某年某月的最大天数
	 * </p>
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return int 当月最大天数
	 */
	public static final int dayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		cal.add(Calendar.DATE, -1);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 是否是今天，根据System.currentTimeMillis() / 1000 / 60 / 60 / 24计算
	 * 
	 * @param date 
	 * @return boolean
	 */
	public static boolean isToday(Date date) {
		long day = date.getTime() / 1000 / 60 / 60 / 24;
		long currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
		return day == currentDay;
	}

	/**
	 * 判断是否是本周，取出日期，依据日期取出该日所在周所有日期，在依据这些日期是否和本日相等
	 * 
	 * @param date 日期
	 * @return boolean
	 */
	public static boolean isThisWeek(Date date) {
		List<Date> dates = dateToWeek(date);
		Boolean flag = false;
		for (Date d : dates) {
			if (isToday(d)) {
				flag = true;
				break;
			} else {
				continue;
			}
		}
		return flag;
	}

	/**
	 * 判断是否是本月的日期
	 * 
	 * @param date 日期
	 * @return boolean
	 */
	public static boolean isThisMonth(Date date) {
		long year = date.getYear();
		long month = date.getMonth();
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime().getYear() == year
				&& calendar.getTime().getMonth() == month;
	}

	/**
	 * 判断是否是本年的日期
	 * 
	 * @param date 日期
	 * @return boolean
	 */
	public static boolean isThisYear(Date date) {
		long year = date.getYear();
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime().getYear() == year;
	}

	/**
	 * 取出该日的一周所有日期
	 * 
	 * @param mdate 日期
	 * @return 返回该周日期列表
	 */
	public static List<Date> dateToWeek(Date mdate) {
		long day = mdate.getDay();
		Date fdate;
		List<Date> list = new ArrayList<Date>();
		long fTime = mdate.getTime() - day * 24 * 3600000;
		for (int i = 0; i < 7; i++) {
			fdate = new Date();
			fdate.setTime(fTime + (((long) i) * 24 * 3600000));
			list.add(i, fdate);
		}
		return list;
	}

	/**
	 * 计算两个日期之间的相隔天数
	 * 
	 * @param begin 开始日期
	 * @param end 结束日期
	 * @return 返回相隔天数
	 */
	public static Double diffTwoDate(Date begin, Date end) {
		if (begin == null || end == null) {
			return 0.0;
		}
		return (end.getTime() - begin.getTime()) / 1000 / 3600.0/24;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date 日期
	 * @param format 日期格式
	 * @return 返回格式化日期
	 */
	public static String parseDate(Date date, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		String dateString;
		dateString = formater.format(date);
		return dateString;
	}

	/**
	 * 格式化日期
	 *
	 * @param date 日期
	 * @param format 日期格式
	 * @return 返回格式化日期
	 */
	public static Date parseDate(String date, String format) throws ParseException {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		Date result = formater.parse(date);
		return result;
	}

	/**
	 * 格式化日期
	 *
	 * @param date 日期
	 * @param format 日期格式
	 * @return 返回格式化日期
	 */
	public static Date parseDateStrict(String date, String format) throws ParseException {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		formater.setLenient(false);
		Date result = formater.parse(date);
		return result;
	}

	/**
	 * 计算相隔天数的日期
	 * 
	 * @param date 日期
	 * @param after 相隔天数
	 * @return date
	 */
	public static Date afterDate(Date date, Integer after) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + after);
		return calendar.getTime();
	}

	/**
	 * 获取相隔年份日期
	 * @param establistCode 相隔年份
	 * @return 计算后的日期
	 */
	public static String getEstablistDate(String establistCode){
		if(!StringUtils.isBlank(establistCode)){
			StringBuffer dateBuff=new StringBuffer();
			Calendar now = Calendar.getInstance();
			if("0".equals(establistCode)){
				int year = now.get(Calendar.YEAR)-1;
				dateBuff.append(year);
			}else{
				int year = now.get(Calendar.YEAR)-Integer.parseInt(establistCode);
				dateBuff.append(year);
			}
			int month=now.get(Calendar.MONTH)+1;
			int day=now.get(Calendar.DAY_OF_MONTH);
			dateBuff.append(Constant.DATESEPCHAR+month+Constant.DATESEPCHAR+day);
			return dateBuff.toString();
		}
		return null;
	}
	
	/**
	 * 获取输入年月的第一天和最后一天
	 * @param year 年
	 * @param month 月
	 * @return 第一天和最后一天的数组
	 */
	public static Date[] getMonthStartAndEnd(int year,int month){
		Date[] dat = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, 1);
		dat[0] = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		dat[1] = cal.getTime();
		return dat;
	}
	
	/**
	 * 日期格式转换成字符串
	 * @param date 日期
	 * @param pattern 格式化模式
	 * @return 返回转换后的字符串
	 */
	public static String dateFormat(Date date,String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 日期格式转换成字符串
	 * @param date
	 * @param pattern
	 * @param pattern_str
	 * @return
	 * @throws ParseException
	 */
	public static String strToDateFormat(String date,String pattern,String pattern_str) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setLenient(false);
		Date newDate= formatter.parse(date);
		formatter = new SimpleDateFormat(pattern_str);
		return formatter.format(newDate);
	}

	/**
	 * 字符串日期转换指定格式字符串日期
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String DateFormat(String date,String pattern) throws ParseException {
		Date result = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		String newDate = new SimpleDateFormat(pattern).format(result);
		return newDate;
	}
}
