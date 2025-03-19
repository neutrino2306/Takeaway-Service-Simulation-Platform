package com.example.demo.service;

import com.example.demo.entity.LineItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.Shop;
import com.example.demo.repository.LineItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShopRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private ShopRepository shopRepository;

    // 根据顾客ID查找订单
    public List<Order> findByCustomerID(Integer customerID) {
        return orderRepository.findByCustomerID(customerID);
    }

    // 根据商家ID查找订单
    public List<Order> findByMerchantID(Integer merchantID) {
        return orderRepository.findByMerchantID(merchantID);
    }

    public List<Order> findByMerchantIDAndShopID(Integer merchantID, Integer shopID) {
        return orderRepository.findByMerchantIDAndShopID(merchantID, shopID);
    }

    // 根据商家ID和订单状态查找订单
    public List<Order> findByMerchantIDAndStatus(Integer merchantID, Integer status) {
        return orderRepository.findByMerchantIDAndStatus(merchantID, status);
    }

    // 查找未支付订单
    public Order findByCustomerIDAndShopIDAndStatus(Integer customerID, Integer shopID) {
        return orderRepository.findByCustomerIDAndShopIDAndStatus(customerID, shopID, 0);
    }

    public Order findByOrderID(Integer orderID) {
        return orderRepository.findByOrderID(orderID);
    }

    // 商家根据订单ID更新订单状态
    public int updateOrderStatus(Integer orderID, Integer status) {
        return orderRepository.updateOrderStatus(orderID, status);
    }

    // 顾客取消未支付的订单
    public int cancelOrder(Integer orderID) {
        return orderRepository.updateOrderStatus(orderID, 3);
    }

    public Order addOrder(Integer customerID, Integer merchantID, Integer shopID, double totalPrice) {
        Order newOrder = new Order();
        newOrder.setCustomerID(customerID);
        newOrder.setMerchantID(merchantID);
        newOrder.setShopID(shopID);
        newOrder.setStatus(0);
        newOrder.setTotalPrice(totalPrice);
        return orderRepository.save(newOrder);
    }

    public Order getOrCreateUnpaidOrder(Integer customerID, Integer shopID) {
        Order order = orderRepository.findByCustomerIDAndShopIDAndStatus(customerID, shopID, 0);
        if (order == null) {
            order = new Order();
            Shop shop = shopRepository.findByShopID(shopID);
            order.setCustomerID(customerID);
            order.setShopID(shopID);
            order.setMerchantID(shop.getMerchantID());
            order.setStatus(0);
            order.setTotalPrice(0.0); // 初始化订单总价

            order = orderRepository.save(order);
        }
        return order;
    }

}
