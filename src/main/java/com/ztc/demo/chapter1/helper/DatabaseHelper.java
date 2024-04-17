package com.ztc.demo.chapter1.helper;

import com.ztc.demo.chapter1.service.CustomerService;
import com.ztc.demo.chapter1.utils.PropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    //保證綫程安全將數據庫鏈接信息保存到當前綫程下
    private static final ThreadLocal<Connection> CONNECTION_HOLDER =
            new ThreadLocal<Connection>();

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
     * @Edit ztc 保證綫程安全的修改
    **/
    public static Connection getConnection(){
//        Connection conn = null;
        Connection conn = CONNECTION_HOLDER.get();
        if (Objects.isNull(conn)) {
            try {
                conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get connection failure ", e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }


        return conn;
    }

    /***
     * @Author ztc
     * @Description 關閉數據庫鏈接
     * @Date 2024/4/16
     *
     **/
//    public static void closeConnection(Connection conn){
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if (!Objects.isNull(conn)) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close conn stream failure",e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


    /***
     * @Author ztc
     * @Description 使用apache的工具簡化列表查詢
     * @Date 2024/4/16
     * @param conn
     * @param entityClass
     * @param sql
     * @param params
     * @return java.util.List<T>
     *
    **/
    public static <T> List<T> queryEntityList(Class<T> entityClass,
                                              String sql, Object... params){
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(getConnection(),sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }
}
