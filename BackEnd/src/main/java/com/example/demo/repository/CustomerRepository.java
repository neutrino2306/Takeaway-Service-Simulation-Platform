package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Customer findByCustomerPhoneAndCustomerPassword(String customerPhone, String customerPassword);

    public Customer findByCustomerPhone(String customerPhone);

    @Modifying
    @Transactional
    @Query("update Customer c set c.customerName = ?2, c.customerAddress = ?3, c.customerPassword = ?4, c.customerPhone = ?5 where c.customerID = ?1")
    public int updateCustomerInfo(Integer customerID, String newName, String newAddress, String newPassword, String newPhone);

}
