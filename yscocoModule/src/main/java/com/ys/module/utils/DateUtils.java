package com.ys.module.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class DateUtils {
    /**
     * 时间转换，
    * @param time 毫秒数，为null为获取当前时间
     * @param formatType 获取的数据类型
    */
    public static String TimeInMillsTrasToDate(Long time, int formatType) {
        Date now = new Date();
        if (time == null) {
            time = now.getTime();
        }

        DateFormat formatter = null;
        switch (formatType) {
            case 1:
                formatter = new SimpleDateFormat("yyyy年MM月dd日");
                break;
            case 2:
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 3:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 4:
                formatter = new SimpleDateFormat("yyyy.MM.dd");
                break;
            case 5:
                formatter = new SimpleDateFormat("HH:mm");
                break;
            case 6:
                formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                break;
            case 7:
                formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                break;
            case 8:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Calendar calendar = null;
        if (time != null) {
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
        }
        return formatter.format(calendar.getTime());
    }
    public static String TimeInMillsTrasToDateOne(Long time, int formatType) {
        if (time == null) {
            return "";
        }

        DateFormat formatter = null;
        switch (formatType) {
            case 1:
                formatter = new SimpleDateFormat("yyyy年MM月dd日");
                break;
            case 2:
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case 3:
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case 4:
                formatter = new SimpleDateFormat("yyyy.MM.dd");
                break;
            case 5:
                formatter = new SimpleDateFormat("HH:mm");
                break;
        }
        Calendar calendar = null;
        if (time != null) {
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
        }
        return formatter.format(calendar.getTime());
    }
    /**
     * �
     *
     * @param year
     * @param month
     * @param day
     * @return 返回当前毫秒数
     * @author Sean.guo
     */
    public static Long DateTransTimeInMills(int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = year + "-" + month + "-" + day;
        Long reDate = null;
        try {
            reDate = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reDate;
    }

    public static Long strTransTimeInMinlls(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long reDate = null;
        try {
            reDate = sdf.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reDate;
    }

    public static boolean isBetweenDate(String beforDateTime, String afterDateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        boolean p = false;
        try {
            p = simpleDateFormat.parse(beforDateTime).before(simpleDateFormat.parse(afterDateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public static boolean isBeforeNow(String beforDateTime, int type) {
        SimpleDateFormat simpleDateFormat = null;
        switch (type) {
            case 1:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                break;
            case 2:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                break;
            default:
                break;
        }
        boolean a = false;
        try {
            a = simpleDateFormat.parse(beforDateTime).after(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public static boolean compareTo(String beforDateTime, String afterDateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            if (simpleDateFormat.parse(beforDateTime).compareTo(simpleDateFormat.parse(afterDateTime)) == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 得到年龄
     *
     * @param mDate
     * @return
     * @throws ParseException
     */
    public static String getAge(Date mDate) {
        Date date = new Date();
        long day = (date.getTime() - mDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
        String age = new java.text.DecimalFormat("#").format(day / 365f);
        return age;
    }


    /**
     * 得到年龄
     *
     * @param mdate
     * @return
     * @throws ParseException
     */
    public static String getAge(String mdate) {
        if(StringUtils.isEmpty(mdate))
            return 20+"";
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date mydate = null;
        try {
            mydate = myFormatter.parse(mdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000) + 1;
        String age = new java.text.DecimalFormat("#").format(day / 365f);
        return age;
    }
    public static Date transformDate(String dataString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dataString);
        } catch (Exception e) {
        }
        return null;
    }

    /*获取当前日期yyyy-MM-dd*/
    public static String getDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String date = dateFormat.format(now);
//        LogUtils.e("date" + date);
        return date;
    }


    /**
     * 格式化时间
     *
     * @param mTime
     * @return
     */
    public static String getTimeRange(String mTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /**获取当前时间*/
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = sdf.format(curDate);
        Date startTime = null;
        try {
            /**将时间转化成Date*/
            curDate = sdf.parse(dataStrNew);
            startTime = sdf.parse(mTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**除以1000是为了转换成秒*/
        long between = (curDate.getTime() - startTime.getTime()) / 1000;
        int elapsedTime = (int) (between);
        if (elapsedTime < seconds_of_1minute) {

            return "刚刚";
        }
        if (elapsedTime < seconds_of_30minutes) {

            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1hour) {

            return "半小时前";
        }
        if (elapsedTime < seconds_of_1day) {

            return elapsedTime / seconds_of_1hour + "小时前";
        }
        if (elapsedTime < seconds_of_15days) {

            return elapsedTime / seconds_of_1day + "天前";
        }
        if (elapsedTime < seconds_of_30days) {

            return "半个月前";
        }
        if (elapsedTime < seconds_of_6months) {

            return elapsedTime / seconds_of_30days + "月前";
        }
        if (elapsedTime < seconds_of_1year) {

            return "半年前";
        }
        if (elapsedTime >= seconds_of_1year) {

            return elapsedTime / seconds_of_1year + "年前";
        }
        return "";
    }

    /**
     * 设置每个阶段时间
     */
    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_15days = seconds_of_1day * 15;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;

    public static String getNext(String key,int nextday) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(key));
            calendar.add(Calendar.DATE, nextday);
             return String.format("%04d-%02d-%02d",calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        }catch (Exception e){
            return key;
        }
    }
}
