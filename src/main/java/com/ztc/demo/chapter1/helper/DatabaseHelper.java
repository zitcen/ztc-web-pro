package com.ztc.demo.chapter1.helper;

import com.ztc.demo.chapter1.service.CustomerService;
import com.ztc.demo.chapter1.utils.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * @ClassName DatabaseHelper
 * @Description 提取每一個要使用數據庫鏈接和關閉鏈接的公共部分
 *               數據庫操作助手類
 * @Author ztc
 * @Date 2024/4/16 16:59
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    /**
     * 初始化數據庫鏈接信息
     */
    static {
        Properties props = PropsUtil.loadProps("jdbcConfig.properties");
        DRIVER = props.getProperty("jdbc.driver");
        URL = props.getProperty("jdbc.url");
        USERNAME = props.getProperty("jdbc.username");
        PASSWORD = props.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /***
     * @Author ztc
     * @Description 獲取數據庫鏈接的公共方法
     * @Date 2024/4/16
     * @return java.sql.Connection
     *
    **/
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure ", e);
        }
        return null;
    }

    /***
     * @Author ztc
     * @Description 關閉數據庫鏈接
     * @Date 2024/4/16
     *
     **/
    public static void closeConnection(Connection conn){
        if (!Objects.isNull(conn)) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close conn stream failure",e);
            }
        }
    }
}
