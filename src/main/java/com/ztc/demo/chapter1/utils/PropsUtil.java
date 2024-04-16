package com.ztc.demo.chapter1.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @ClassName PropsUtil
 * @Description 獲取配置文件內容工具類
 * @Author ztc
 * @Date 2024/4/16 11:09
 */
public final class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /***
     * @Author ztc
     * @Description 讀取配置文件中的內容
     * @Date 2024/4/16
     * @param fileName
     * @return java.util.Properties
     *
    **/
    public static Properties loadProps(String fileName){
        Properties prop = null;
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(Objects.isNull(in)){
                throw new FileNotFoundException(fileName + " file is not found");
            }
            prop = new Properties();
            prop.load(in);
        } catch (Exception e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if(!Objects.isNull(in)){
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure",e);
                }
            }
        }
        return prop;
    }

    /***
     * @Author ztc
     * @Description 獲取字符型數據的屬性值(默認值為空串)
     * @Date 2024/4/16
     * @param prop
     * @param key
     * @return java.lang.String
     *
    **/
    public static String getString(Properties prop,String key){
        return getString(prop,key,"");
    }

    /***
     * @Author ztc
     * @Description 獲取字符屬性 可以指定默認值
     * @Date 2024/4/16
     * @param prop
     * @param key
     * @param defaultValue
     * @return java.lang.String
     *
    **/
    public static String getString(Properties prop,String key,String defaultValue){
        String value = defaultValue;
        if(prop.containsKey(key)){
            value = prop.getProperty(key);
        }
        return value;
    }

    /***
     * @Author ztc
     * @Description 獲取數值型屬性 默認值0
     * @Date 2024/4/16
     * @param prop
     * @param key
     * @return int
     *
    **/
    public static int getInt(Properties prop,String key){
         return getInt(prop,key,0);
    }

    /***
     * @Author ztc
     * @Description 獲取數值型屬性 可以指定默認值
     * @Date 2024/4/16
     * @param prop
     * @param key
     * @param defaultValue 
     * @return int
     * 
    **/
    public static int getInt(Properties prop,String key,int defaultValue){
        int value = defaultValue;
        if(prop.containsKey(key)){
            value = CastUtil.castInt(prop.getProperty(key));
        }
        return value;
    }

    /***
     * @Author ztc
     * @Description 獲取boolean屬性 默認值 false
     * @Date 2024/4/16
     * @param prop
     * @param key
     * @return int
     *
     **/
    public static boolean getBoolean(Properties prop,String key){
        return getBoolean(prop,key,false);
    }

    /***
     * @Author ztc
     * @Description 獲取boolean屬性 可以指定默認值
     * @Date 2024/4/16
     * @param prop
     * @param key
     * @param defaultValue
     * @return int
     *
     **/
    public static boolean getBoolean(Properties prop,String key,boolean defaultValue){
        boolean value = defaultValue;
        if(prop.containsKey(key)){
            value = CastUtil.castBoolean(prop.getProperty(key));
        }
        return value;
    }
}
