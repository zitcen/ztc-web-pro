package com.ztc.demo.chapter1.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @ClassName CastUtil
 * @Description 即轉型工具
 * @Author ztc
 * @Date 2024/4/16 11:07
 */
public final class CastUtil {
    /***
     * @Author ztc
     * @Description 轉換成字符串類型值，默認值為空串
     * @Date 2024/4/16
     * @param obj
     * @return java.lang.String
     *
     **/
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /***
     * @Author ztc
     * @Description 轉換成字符串類型，可以設置默認值
     * @Date 2024/4/16
     * @param obj
     * @param defaultValue
     * @return java.lang.String
     *
     **/
    public static String castString(Object obj, String defaultValue) {

        return Objects.isNull(obj) ? defaultValue : String.valueOf(obj);
    }

    /***
     * @Author ztc
     * @Description 轉換成double 類型，默認值為0
     * @Date 2024/4/16
     * @param obj
     * @return double
     *
     **/
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /***
     * @Author ztc
     * @Description 轉換成double 類型，可以設置默認值
     * @Date 2024/4/16
     * @param obj
     * @param defaultValue
     * @return double
     *
     **/
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if (!Objects.isNull(obj)) {
            String doubleStr = castString(obj);
            if (StringUtils.isNotEmpty(doubleStr)) {
                try {
                    value = Double.parseDouble(doubleStr);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /***
     * @Author ztc
     * @Description 轉換成long 類型，默認值為0
     * @Date 2024/4/16
     * @param obj
     * @return double
     *
     **/
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /***
     * @Author ztc
     * @Description 轉換成long 類型，可以設置默認值
     * @Date 2024/4/16
     * @param obj
     * @param defaultValue
     * @return double
     *
     **/
    public static long castLong(Object obj, long defaultValue) {
        long value = defaultValue;
        if (!Objects.isNull(obj)) {
            String doubleStr = castString(obj);
            if (StringUtils.isNotEmpty(doubleStr)) {
                try {
                    value = Long.parseLong(doubleStr);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /***
     * @Author ztc
     * @Description 轉換成int 類型，默認值為0
     * @Date 2024/4/16
     * @param obj
     * @return double
     *
     **/
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /***
     * @Author ztc
     * @Description 轉換成int 類型，可以設置默認值
     * @Date 2024/4/16
     * @param obj
     * @param defaultValue
     * @return double
     *
     **/
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (!Objects.isNull(obj)) {
            String doubleStr = castString(obj);
            if (StringUtils.isNotEmpty(doubleStr)) {
                try {
                    value = Integer.parseInt(doubleStr);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /***
     * @Author ztc
     * @Description 轉換成boolean 類型，默認值為 false
     * @Date 2024/4/16
     * @param obj
     * @return double
     *
     **/
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }


    /***
     * @Author ztc
     * @Description 轉換成boolean 類型，可設置默認值
     * @Date 2024/4/16
     * @param obj
     * @param defaultValue
     * @return boolean
     *
    **/
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value = defaultValue;
        if (!Objects.isNull(obj)) {
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }
}
