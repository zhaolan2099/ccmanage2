package com.cc.manage.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class DateUtil {

    public final static String DATE_FORMAT_DEFAULT_YEAR = "yyyy";

    public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";

    public final static String TIME_FORMAT_DEFAULT = "HH:mm:ss";

    public final static String DATE_FORMAT_SOLIDUS = "yyyy/MM/dd";

    public final static String DATE_FORMAT_COMPACT = "yyyyMMdd";
    public final static String DATE_FORMAT_SHORT_COMPACT = "yyMMdd";

    public final static String DATE_FORMAT_COMPACT_MONTH = "yyyyMM";

    public final static String DATE_FORMAT_UTC_DEFAULT = "MM-dd-yyyy";

    public final static String DATE_FORMAT_UTC_SOLIDUS = "MM/dd/yyyy";

    public final static String DATE_FORMAT_TAODA_DIRECTORY = "yyyyMMdd HHmmss";

    public final static String DATE_FORMAT_TAODA_DIRECTORY_T = "yyyyMMddHHmmss";

    public final static String  DATE_FORMAT_TAODA_DIRECTORY_Y = "yyyyMMddHHmm";

    public final static String DATE_FORMAT_TAODA_DIRECTORY_S = "yyyyMMddHHmmssSSS";

    public final static String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";

    public final static String DATE_TIME_FORMAT_COMPACT = "yyyyMMdd hhmmss";

    public final static String DATE_TIME_FORMAT_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

    public final static String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_TIME_FORMAT_DEFAULT_MONET = "yyyy-MM-dd HH:mm";

    public final static String DATE_TIME_FORMAT_DEFAULT_S = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String DATE_TIME_FORMAT_TAODA_DIRECTORY = "HHmmss";

    public final static String DATE_TIME_FORMAT_SOLIDUS = "yyyy/MM/dd HH:mm:ss";

    public final static String DATE_TIME_FORMAT_UTC_DEFAULT = "MM-dd-yyyy HH:mm:ss";

    public final static String DATE_TIME_FORMAT_UTC_SOLIDUS = "MM/dd/yyyy HH:mm:ss";

    public final static String DATE_TIME_FORMAT_DEFAULT_3 = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public final static String DATE_TIME_FORMAT_DEFAULT_4 = "HH:mm";

    public final static String DATE_TIME_FORMAT_DEFAULT_5 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static Map<String, String> dateFormatRegisterMap = new HashMap<String, String>();

    private static Map<String, SimpleDateFormat> dateFormatMap = new HashMap<String, SimpleDateFormat>();

    public final static String DATE_FORMAT_DEFAULT_MONTH = "yyyy-MM";

    private static final String minDateStrTemp = "2020-04-14";

    public static final String BANCK_TIME_SHOW = "yyyyMMdd HH:mm:ss";

    private static String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

    static {
        // 各种日期格式注册，有新需要请在此处添加 ，无需其它改动
        dateFormatRegisterMap.put(DATE_FORMAT_COMPACT, "^\\d{8}$");
        dateFormatRegisterMap.put(DATE_FORMAT_DEFAULT, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
        dateFormatRegisterMap.put(DATE_FORMAT_SOLIDUS, "^\\d{4}/\\d{1,2}/\\d{1,2}$");
        dateFormatRegisterMap.put(DATE_FORMAT_UTC_DEFAULT, "^\\d{1,2}-\\d{1,2}-\\d{4}$");
        dateFormatRegisterMap.put(DATE_FORMAT_UTC_SOLIDUS, "^\\d{1,2}/\\d{1,2}/\\d{4}$");
        dateFormatRegisterMap.put(DATE_TIME_FORMAT_DEFAULT, "^\\d{4}-\\d{1,2}-\\d{1,2}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");
        dateFormatRegisterMap.put(DATE_TIME_FORMAT_SOLIDUS, "^\\d{4}/\\d{1,2}/\\d{1,2}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");
        dateFormatRegisterMap.put(DATE_TIME_FORMAT_UTC_DEFAULT, "^\\d{1,2}-\\d{1,2}-\\d{4}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");
        dateFormatRegisterMap.put(DATE_TIME_FORMAT_UTC_SOLIDUS, "^\\d{1,2}/\\d{1,2}/\\d{4}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$");

        dateFormatMap.put(DATE_FORMAT_DEFAULT, new SimpleDateFormat(DATE_FORMAT_DEFAULT));
        dateFormatMap.put(DATE_FORMAT_SOLIDUS, new SimpleDateFormat(DATE_FORMAT_SOLIDUS));
        dateFormatMap.put(DATE_FORMAT_COMPACT, new SimpleDateFormat(DATE_FORMAT_COMPACT));
        dateFormatMap.put(DATE_FORMAT_UTC_DEFAULT, new SimpleDateFormat(DATE_FORMAT_UTC_DEFAULT));
        dateFormatMap.put(DATE_FORMAT_UTC_SOLIDUS, new SimpleDateFormat(DATE_FORMAT_UTC_SOLIDUS));
        dateFormatMap.put(DATE_TIME_FORMAT_DEFAULT, new SimpleDateFormat(DATE_TIME_FORMAT_DEFAULT));
        dateFormatMap.put(DATE_TIME_FORMAT_SOLIDUS, new SimpleDateFormat(DATE_TIME_FORMAT_SOLIDUS));
        dateFormatMap.put(DATE_TIME_FORMAT_UTC_DEFAULT, new SimpleDateFormat(DATE_TIME_FORMAT_UTC_DEFAULT));
        dateFormatMap.put(DATE_TIME_FORMAT_UTC_SOLIDUS, new SimpleDateFormat(DATE_TIME_FORMAT_UTC_SOLIDUS));
    }

    /**
     * @param formatStr String
     * @return String
     * @Description: 获得当前日期的格式化表示
     */
    public static String getCurrentDateStr(String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date());
    }


    public static String getNowDate(String format) {
        return formatDate(new Date(), format);
    }


    public static String formatDate(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }


    public static String format(String formatString, Date date) {
        return new SimpleDateFormat(formatString).format(date);
    }


    public static Date parseStrToDate(String formatString, String dateString) {
        try {
            return new SimpleDateFormat(formatString).parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static long parseDateToLong(String date) {
        date = date.replace("-", "");
        Long result = Long.valueOf(date);
        return result;
    }


    public static String formatDate(String date, String format, String newFormat) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date d = sf.parse(date);
        sf = new SimpleDateFormat(newFormat);
        return sf.format(d);
    }


    public static Date add(Date date, int n, int calendarField) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, n);

        return c.getTime();
    }


    public static String addDate(Date date, int n, String format) {
        return DateUtil.formatDate(DateUtils.addDays(date, n), format);
    }


    public static String addMonths(Date date, int n, String format) {
        return DateUtil.formatDate(DateUtils.addMonths(date, n), format);
    }


    public static String addMinutes(Date date, int n, String format) {
        return DateUtil.formatDate(DateUtils.addMinutes(date, n), format);
    }


    public static String addYears(Date date, int n, String format) {
        return DateUtil.formatDate(DateUtils.addYears(date, n), format);
    }

    /**
     * 获取 是否是一般工作日(周一至周五)
     * @return
     */
    public static boolean isWorkingDate(Date date) {
        final int dayNames[] = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));// 获取中国的时区
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0){
            dayOfWeek = 0;
        }
        int week = dayNames[dayOfWeek];
        if (week > 5) {
            return false;
        }
        return true;
    }


    /**
     * 取得两个时间段的时间间隔
     * @param t1 时间1
     * @param t2 时间2
     * @return t2 与t1的间隔天数
     * @throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     * @author color
     */
    public static int getBetweenDays(String t1, String t2, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, c1.get(Calendar.YEAR) + 1);
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }


    /**
     * 获取当天日期的星期
     * @return
     */
    public static int getNowWeek() {
        Date date = new Date();
        final int dayNames[] = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));// 获取中国的时区
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0){
            dayOfWeek = 0;
        }
        return dayNames[dayOfWeek];
    }


    /**
     * 获取几年的周一到周五的日期
     * @return
     */
    public static List<String> getWorkDs(int year) {
        List<String> dates = new ArrayList<String>();
        for (int j = 1; j <= 12; j++) {
            getDates(dates, year, j);
        }
        return dates;
    }


    /**
     * 计算两个日期之间相差的天数
     * @param sdate 较小的时间
     * @return 相差天数
     * @throws Exception
     */
    public static int daysBetween(Date sdate, Date edate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(edate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }


    /**
     * 计算两个日期之间相差的秒数
     * @param sdate 较小的时间
     * @return 相差天数
     * @throws Exception
     */
    public static long secondsBetween(Date sdate, Date edate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(edate);
        long time2 = cal.getTimeInMillis();
        long betweenSeconds = time2 - time1;
        return betweenSeconds;
    }


    public static Date calMonth(Date date, String format, int n) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        return parseStrToDate(format, sdf.format(calendar.getTime()));
    }


    public static Date calWeek(Date date, String format, int n) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEDNESDAY, n);
        return parseStrToDate(format, sdf.format(calendar.getTime()));
    }


    public static boolean dateMatcher(String date) {
        return Pattern.matches(rexp, date);
    }


    private static List<String> getDates(List<String> dates, int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);

        while (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) < month) {
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                Date date = (Date) cal.getTime().clone();
                dates.add(DateUtil.format(DateUtil.DATE_FORMAT_DEFAULT, date));
            }
            cal.add(Calendar.DATE, 1);
        }
        return dates;

    }


    /**
     * 字符串转日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date fomatDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return sdf.parse(date);

    }


    /**
     * 判断某一时间是否在一个区间内
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
    }


    /**
     * 计算两个日期之间相差的 秒差
     * @param sdate 较小的时间
     * @return 相差天数
     * @throws Exception
     */
    public static int secondBetween(Date sdate, Date edate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(edate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / 1000;
        return Integer.parseInt(String.valueOf(betweenDays));
    }


    /**
     * 获取当前日期字符串，格式"yyyyMMddHHmmss"
     * @return
     */
    public static String getNow2() {
        Calendar today = Calendar.getInstance();
        return DateFormatUtils.format(today.getTime(), DATE_FORMAT_TAODA_DIRECTORY_T);
    }


    /**
     * 获取当前日期字符串，格式"yyyyMMddHHmmssSSS"
     * @return
     */
    public static String getNow3() {
        Calendar today = Calendar.getInstance();
        return DateFormatUtils.format(today.getTime(), DATE_FORMAT_TAODA_DIRECTORY_S);
    }


    /**
     * 获取当前日期字符串，格式"yyyy-MM-dd"
     * @return
     */
    public static String getNow4() {
        Calendar today = Calendar.getInstance();
        return DateFormatUtils.format(today.getTime(), DATE_FORMAT_DEFAULT);
    }


    /**
     * 获取当前时间的指定格式
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }


    /**
     * 把日期转换为字符串
     */
    public static String dateToString(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }


    /**
     * Date日期转化为格林威治天文台标准台时间
     * @return
     */
    public static String getISO8601Timestamp(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }


    /**
     * 把符合日期格式的字符串转换为日期类型
     */
    public static Date stringtoDate(String dateStr) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd", "yyyy-MM", "yyyy年MM月dd日"});
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return date;
    }


    /**
     * 把符合日期格式的字符串转换为日期类型在转化为符合条件的日期格式字符串
     */
    public static String stringToDateToString(String dateStr, String format) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd", "yyyy-MM", "yyyy年MM月dd日"});
        } catch (ParseException e) {
            return "长期";
        }
        return dateToString(date, format);
    }


    /**
     * @param dateStr
     * @param format
     * @return
     * @Title: dateMatcher
     * @Description: 日期格式验证
     */
    public static boolean dateMatcher(String dateStr, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setLenient(false);
        try {
            df.parse(dateStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 将日期转换为字符串
     * @param source
     * @param outfmt
     * @return
     */
    public static String getDateFmtStr(Date source, String outfmt) {
        String result = "";
        // 输出格式
        DateFormat outfomater = new SimpleDateFormat(outfmt);
        try {
            result = outfomater.format(source);
        } catch (Exception e) {
            result = outfomater.format(new Date());
        }
        return result;
    }


    /**
     * @return String 时间字符串
     * @throws
     * @Description: 获取当前系统时间戳 格式:yyyy-mm-dd HH:mm:ss
     */
    public static String getCurrentTimeStamp() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }


    /**
     * @param format 日期格式
     * @return String 日期字符串
     * @throws
     * @Description: 获取当前系统日期
     */
    public static String getCurrentDate(String format) {
        return getCurrentDateTimeStr(format);
    }


    /**
     * @param format 日期时间格式
     * @return String 日期时间字符串
     * @throws
     * @Description: 获取当前日期时间字符串
     */
    public static String getCurrentDateTimeStr(String format) {
        DateFormat formater = new SimpleDateFormat(format);
        return formater.format(new Date());
    }


    /**
     * @throws
     * @Description: long转为date
     */
    public static Date longToNowDate(long time) {
        long t1 = System.currentTimeMillis();
        time = t1 + time;
        return new Date(time);
    }


    public static Date stringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date thisDate = null;
        try {
            thisDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return thisDate;
        }
    }


    /**
     * 2个日期之间的天数
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(Date date1, Date date2) {
        int result = 0;
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);
        result = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
        return result == 0 ? 1 : Math.abs(result);
    }


    /**
     * 获取两月之间所有的时间
     * @param minDateStr
     * @param maxDateStr
     * @return
     */
    public static List<String> showAllTime(String minDateStr, String maxDateStr) {
        List<String> list = new ArrayList<String>();
        Date thisDate = new Date();
        Date minDateTemp = DateUtil.stringToDate(minDateStrTemp, DateUtil.DATE_FORMAT_DEFAULT_MONTH);
        Date minDate = DateUtil.stringToDate(minDateStr, DateUtil.DATE_FORMAT_DEFAULT_MONTH);
        if (minDate == null) {
            minDate = minDateTemp;
        } else {
            if (minDateTemp.after(minDate)) {
                minDate = minDateTemp;
            }
        }
        Date maxDate = DateUtil.stringToDate(maxDateStr, DateUtil.DATE_FORMAT_DEFAULT_MONTH);
        if (maxDate == null) {
            maxDate = thisDate;
        } else {
            if (maxDate.after(thisDate)) {
                maxDate = thisDate;
            }
        }
        int month = getMonthSpace(minDate, maxDate);
        for (int i = 0; i <= month; i++) {
            Date dateTemp = DateUtils.addMonths(minDate, i);
            if(thisDate.after(dateTemp)){
                list.add(DateUtil.dateToString(dateTemp, DateUtil.DATE_FORMAT_COMPACT_MONTH));
            }
        }
        if (list.isEmpty()) {
            list.add(DateUtil.dateToString(thisDate, DateUtil.DATE_FORMAT_COMPACT_MONTH));
        }
        return list;
    }


    /**
     * 将数据类型的时间字符串转换成格式字符串
     * @param date
     * @param beforeFormat
     * @param afterFormat
     * @return
     */
    public static String formatConversion(String date, String beforeFormat, String afterFormat) {
        return DateUtil.formatDate(DateUtil.parseStrToDate(beforeFormat, date), afterFormat);
    }


    /**
     * 比较两个日期的大小
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean comparingDate(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return true;
        }
        return false;
    }


    /**
     * 获取当前时间的前24小时
     * @param nowDate
     * @param beforeNum
     * @return
     */
    public static Date getBeforeDate(Date nowDate, int beforeNum){
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(nowDate);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -beforeNum);  //设置为前beforeNum天
        return calendar.getTime();   //得到前beforeNum天的时间
    }


    /**
     * 获取两个日期之间的所有日期
     * @param startTime     开始日期
     * @param endTime     结束日期
     * @return
     */
    public static List<String> getDays(String startTime, String endTime) {
        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }


    /** 
     * 字符串转换成日期 
     * @param str 
     * @return date 
     */
    public static Date strToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
