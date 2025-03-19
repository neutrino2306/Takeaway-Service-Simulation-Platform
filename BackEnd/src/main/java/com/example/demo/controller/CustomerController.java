package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/CustomerController")
public class CustomerController {

    @Resource
    private CustomerService customerService;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/CustomerLogin")
    @CrossOrigin
    public ResponseEntity<Customer> CustomerLogin(@RequestParam("customerPhone") String customerPhone,
                                                  @RequestParam("customerPassword") String customerPassword) {
        Customer customer = customerService.findByCustomerPhoneAndCustomerPassword(customerPhone, customerPassword);
        if(customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findByCustomerPhone")
    @CrossOrigin
    public ResponseEntity<Customer> findCustomerByPhone(@RequestParam("customerPhone") String customerPhone) {
        Customer customer = customerService.findByCustomerPhone(customerPhone);
        if(customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateCustomer")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> updateCustomer(@RequestParam("customerID") Integer customerID,
                                            @RequestParam("customerName") String newName,
                                            @RequestParam("customerAddress") String newAddress,
                                            @RequestParam("customerPassword") String newPassword,
                                            @RequestParam("customerPhone") String newPhone) {
        int updated = customerService.updateCustomerInfo(customerID, newName, newAddress, newPassword, newPhone);
        if(updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/addCustomer")
    @CrossOrigin
    @Transactional
    public Customer addCustomer(@RequestParam("customerPhone") String customerPhone,
                                @RequestParam("customerPassword") String customerPassword,
                                @RequestParam("customerName") String customerName,
                                @RequestParam("customerAddress") String customerAddress) {
        return customerService.addCustomer(customerPhone, customerPassword, customerName, customerAddress);
    }

}
