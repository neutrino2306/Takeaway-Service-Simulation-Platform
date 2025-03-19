package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    public Order findByOrderID(Integer orderID);

    // 根据顾客ID查找订单
    public List<Order> findByCustomerID(Integer customerID);

    // 根据商家ID查找订单
    public List<Order> findByMerchantID(Integer merchantID);

    public List<Order> findByMerchantIDAndShopID(Integer merchantID, Integer shopID);

    // 根据门店ID查找订单
    public List<Order> findByShopID(Integer shopID);

    // 根据商家ID和订单状态查找订单
    public List<Order> findByMerchantIDAndStatus(Integer merchantID, Integer status);

    public Order findByCustomerIDAndShopIDAndStatus(Integer customerId, Integer shopId, Integer status);

    // 商家根据订单ID更新订单状态
    @Modifying
    @Transactional
    @Query("update Order o set o.status = ?2 where o.orderID = ?1")
    public int updateOrderStatus(Integer orderID, Integer status);

}
