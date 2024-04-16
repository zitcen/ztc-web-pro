package com.ztc.demo.chapter1.service;

import com.ztc.demo.chapter1.helper.DatabaseHelper;
import com.ztc.demo.chapter1.model.Customer;
import com.ztc.demo.chapter1.utils.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * @ClassName CustomerService
 * @Description 客戶控制的服務層，提供客戶數據給控制器
 * @Author ztc
 * @Date 2024/4/15 17:42
 */
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
//    private static final String DRIVER;
//    private static final String URL;
//    private static final String USERNAME;
//    private static final String PASSWORD;
//
//    /**
//     * 初始化數據庫鏈接信息
//     */
//    static {
//        Properties props = PropsUtil.loadProps("jdbcConfig.properties");
//        DRIVER = props.getProperty("jdbc.driver");
//        URL = props.getProperty("jdbc.url");
//        USERNAME = props.getProperty("jdbc.username");
//        PASSWORD = props.getProperty("jdbc.password");
//
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("can not load driver", e);
//        }
//    }


    /***
     * @Author ztc
     * @Description 獲取客戶數據列表
     * @Date 2024/4/15
     * @return java.util.List<com.ztc.demo.chapter1.model.Customer>
     *
    **/
    public List<Customer> getCustomerList(){
        Connection conn = null;
        try {
//            List<Customer> customers = new ArrayList<>();
            String sql = "select * from customer;";
            conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Customer customer = new Customer();
//                customer.setId(rs.getLong("id"));
//                customer.setName(rs.getString("name"));
//                customer.setContact(rs.getString("contact"));
//                customer.setTelephone(rs.getString("telephone"));
//                customer.setEmail(rs.getString("email"));
//                customer.setRemark(rs.getString("remark"));
//                customers.add(customer);
//            }
            List<Customer> customers = DatabaseHelper.queryEntityList(conn, Customer.class, sql);
            return customers;
        } catch (Exception e) {
            LOGGER.error("execute sql failure",e);
            return null;
        } finally {
//            if (!Objects.isNull(conn)) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error("close conn stream failure",e);
//                }
//            }
            DatabaseHelper.closeConnection(conn);
        }
    }

    /***
     * @Author ztc
     * @Description 根據id 獲取具體客戶數據列表
     * @Date 2024/4/15
     * @param id
     * @return com.ztc.demo.chapter1.model.Customer
     *
    **/
    public Customer getCustomer(Long id){
        return null;
    }

    /***
     * @Author ztc
     * @Description 創建客戶數據
     * @Date 2024/4/15
     * @param fieldMap
     * @return boolean
     *
    **/
    public boolean createCustomer(Map<String,Object> fieldMap){
        return false;
    }

    /***
     * @Author ztc
     * @Description 更新客戶數據
     * @Date 2024/4/15
     * @param fieldMap
     * @param id
     * @return boolean
     *
     **/
    public boolean editCustomer(long id,Map<String,Object> fieldMap){
        return false;
    }

    /***
     * @Author ztc
     * @Description 刪除客戶數據
     * @Date 2024/4/15
     * @param id
     * @return boolean
     *
     **/
    public boolean deleteCustomer(long id){
        return false;
    }
}
