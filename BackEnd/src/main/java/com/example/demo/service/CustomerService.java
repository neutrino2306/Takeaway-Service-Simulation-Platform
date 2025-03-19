package com.example.demo.service;

import com.example.demo.controller.CustomerController;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {

    @Resource
    private CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);


    public Customer findByCustomerPhoneAndCustomerPassword(String customerPhone, String customerPassword) {
        log.debug("service: CustomerLogin called with customerPhone: {}, customerPassword: {}", customerPhone, customerPassword);

        return customerRepository.findByCustomerPhoneAndCustomerPassword(customerPhone, customerPassword);
    }

    // 根据电话号码查询客户
    public Customer findByCustomerPhone(String customerPhone) {
        return customerRepository.findByCustomerPhone(customerPhone);
    }

    // 更新客户的用户名和地址
    @Transactional
    public int updateCustomerInfo(Integer customerID, String newName, String newAddress, String newPassword, String newPhone) {
        return customerRepository.updateCustomerInfo(customerID, newName, newAddress, newPassword, newPhone);
    }

    // 添加新顾客
    public Customer addCustomer(String customerPhone, String customerPassword, String customerName, String customerAddress) {
        // 检查是否已存在具有相同电话号码的顾客
        if (customerRepository.findByCustomerPhone(customerPhone) == null) {
            Customer newCustomer = new Customer();
            newCustomer.setCustomerPhone(customerPhone);
            newCustomer.setCustomerPassword(customerPassword);
            newCustomer.setCustomerName(customerName);
            newCustomer.setCustomerAddress(customerAddress);
            return customerRepository.save(newCustomer); // 保存新顾客
        }
        return null; // 如果已存在相同电话号码的顾客，则返回null
    }

}
