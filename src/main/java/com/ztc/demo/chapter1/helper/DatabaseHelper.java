package com.ztc.demo.chapter1.helper;

import com.ztc.demo.chapter1.model.Customer;
import com.ztc.demo.chapter1.service.CustomerService;
import com.ztc.demo.chapter1.utils.PropsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

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
    private static final QueryRunner QUERY_RUNNER;
    //保證綫程安全將數據庫鏈接信息保存到當前綫程下
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    //數據庫連接池
    private static final BasicDataSource DATA_SOURCE;

    /**
     * 初始化數據庫鏈接信息
     */
    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();
        Properties props = PropsUtil.loadProps("jdbcConfig.properties");
        DRIVER = props.getProperty("jdbc.driver");
        URL = props.getProperty("jdbc.url");
        USERNAME = props.getProperty("jdbc.username");
        PASSWORD = props.getProperty("jdbc.password");
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);



//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("can not load jdbc driver", e);
//        }
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
//                conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                conn = DATA_SOURCE.getConnection();
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
//    public static void closeConnection(){
//        Connection conn = CONNECTION_HOLDER.get();
//        if (!Objects.isNull(conn)) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                LOGGER.error("close conn stream failure",e);
//            }finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }


    /***
     * @Author ztc
     * @Description 使用apache的工具簡化列表查詢
     * @Date 2024/4/16
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
            entityList = QUERY_RUNNER.query(getConnection(),sql,
                    new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return entityList;
    }

    /***
     * @Author ztc
     * @Description 使用apache的工具簡化實例查詢
     * @Date 2024/4/16
     * @param entityClass
     * @param sql
     * @param params
     * @return java.util.List<T>
     *
     **/
    public static <T> T queryEntity(Class<T> entityClass,String sql, Object... params){
        T entity;
        try {
            entity = QUERY_RUNNER.query(getConnection(),sql,
                    new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return entity;
    }

    /***
     * @Author ztc
     * @Description 执行更新语句（insert, update, insert）
     * @Date 2024/4/17
     * @param sql
     * @param params 
     * @return int
     * 
    **/
    public static int executeUpdate(String sql, Object... params){
        int rows = 0;
        try {
            rows = QUERY_RUNNER.update(getConnection(),sql,params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        }
//        finally {
//            closeConnection();
//        }
        return rows;
    }


    /***
     * @Author ztc
     * @Description 通用插入操作
     * @Date 2024/4/17
     * @param entityClass
     * @param fieldMap
     * @return boolean
     *
    **/
    public static <T> boolean insertEntity(Class<T> entityClass,
                                           Map<String,Object> fieldMap){
        if (fieldMap.isEmpty()) {
            LOGGER.error("can not insert entity : fieldMap is null");
            return false;
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(getTableName(entityClass)) ;

        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql.append(columns).append("VALUES ").append(values) ;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql.toString(),params) == 1;

    }

    /***
     * @Author ztc
     * @Description 通用修改实体的方法
     * @Date 2024/4/17
     * @param entity
     * @param id
     * @param fieldMap
     * @return boolean
     *
    **/
    public static <T> boolean updateEntity(Class<T> entity,long id, Map<String,Object> fieldMap){
        if (fieldMap.isEmpty()) {
            LOGGER.error("can not update entity : fieldMap is null");
            return false;
        }
        StringBuilder sql = new StringBuilder("UPDATE ").append(getTableName(Customer.class));
        sql.append(" set ");
        for (String fieldName : fieldMap.keySet()) {
            sql.append(fieldName).append("= ?,");
        }
        sql.replace(sql.lastIndexOf(","),sql.length()," ");
        sql.append("where id = ?;");
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql.toString(), params) == 1;
    }


    /***
     * @Author ztc
     * @Description 通用根据id删除实体
     * @Date 2024/4/17
     * @param id
     * @return boolean
     *
    **/
    public static <T> boolean delEntity(long id){

        StringBuilder sql = new StringBuilder("DELETE FROM ").
                append(getTableName(Customer.class)).append(" where id =?;");
        return executeUpdate(sql.toString(), id) == 1;
    }

    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();

    }
}
