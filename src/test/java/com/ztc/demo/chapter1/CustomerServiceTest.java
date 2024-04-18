package com.ztc.demo.chapter1;

import com.ztc.demo.chapter1.helper.DatabaseHelper;
import com.ztc.demo.chapter1.model.Customer;
import com.ztc.demo.chapter1.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CustomerServiceTest
 * @Description 客戶servlet的單元測試類
 * @Author ztc
 * @Date 2024/4/15 17:58
 */
public class CustomerServiceTest {

    private final CustomerService customerService;


    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    /***
     * @Author ztc
     * @Description 初始化數據庫
     * @Date 2024/4/16
     *
     **/
    @Before
    public void initDb() throws IOException {
        DatabaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    @Test
    public void getCustomerListTest() {
        List<Customer> customerList = customerService.getCustomerList();
        for (Customer customer : customerList) {
            System.out.println(customer.toString());
        }
        Assert.assertEquals(2, customerList.size());
    }

    @Test
    public void getCustomerTest() {
        Long id = 1L;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest() {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "White");
        fieldMap.put("contact", "White1");
        fieldMap.put("telephone", "13612345678");
        boolean createResult = customerService.createCustomer(fieldMap);
        Assert.assertTrue(createResult);
    }

    @Test
    public void editCustomerTest() {
        Map<String, Object> fieldMap = new HashMap<>();
        Long id = 3L;
        fieldMap.put("name", "Eric");
        fieldMap.put("contact", "Eric1");
        boolean editResult = customerService.editCustomer(id, fieldMap);
        Assert.assertTrue(editResult);
    }

    @Test
    public void delCustomerTest() {
        Long id = 3L;
        boolean delResult = customerService.deleteCustomer(id);
        Assert.assertTrue(delResult);

    }

}
