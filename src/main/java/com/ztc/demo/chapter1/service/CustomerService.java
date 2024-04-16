package com.ztc.demo.chapter1.service;

import com.ztc.demo.chapter1.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CustomerService
 * @Description 客戶控制的服務層，提供客戶數據給控制器
 * @Author ztc
 * @Date 2024/4/15 17:42
 */
public class CustomerService {
    /***
     * @Author ztc
     * @Description 獲取客戶數據列表
     * @Date 2024/4/15
     * @return java.util.List<com.ztc.demo.chapter1.model.Customer>
     *
    **/
    public List<Customer> getCustomerList(){
        return null;
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
