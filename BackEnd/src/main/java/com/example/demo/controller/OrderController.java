package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.ShopItem;
import com.example.demo.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/OrderController")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/findByOrderID")
    @CrossOrigin
    public Order findByOrderID(@RequestParam("orderID") Integer orderID) {
        Order order = orderService.findByOrderID(orderID);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByCustomerID")
    @CrossOrigin
    public List<Order> findByCustomerID(@RequestParam("customerID") Integer customerID) {
        List<Order> orderList = orderService.findByCustomerID(customerID);
        if (orderList != null) {
            return new ResponseEntity<>(orderList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(orderList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByMerchantID")
    @CrossOrigin
    public List<Order> findByMerchantID(@RequestParam("merchantID") Integer merchantID) {
        List<Order> orderList = orderService.findByMerchantID(merchantID);
        if (orderList != null) {
            return new ResponseEntity<>(orderList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(orderList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByMerchantIDAndShopID")
    @CrossOrigin
    public List<Order> findByMerchantIDAndShopID(@RequestParam("merchantID") Integer merchantID,
                                                 @RequestParam("shopID") Integer shopID) {
        List<Order> orderList = orderService.findByMerchantIDAndShopID(merchantID, shopID);
        if(orderList != null) {
            return new ResponseEntity<>(orderList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(orderList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByCustomerIDAndShopID")
    @CrossOrigin
    public Order findByCustomerIDAndShopIDAndStatus(@RequestParam("customerID") Integer customerID,
                                                 @RequestParam("shopID") Integer shopID) {
        Order order = orderService.findByCustomerIDAndShopIDAndStatus(customerID, shopID);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByMerchantIDAndStatus")
    @CrossOrigin
    public List<Order> findByMerchantIDAndStatus(@RequestParam("merchantID") Integer merchantID,
                                                 @RequestParam("status") Integer status) {
        List<Order> orderList = orderService.findByMerchantIDAndStatus(merchantID, status);
        if(orderList != null) {
            return new ResponseEntity<>(orderList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(orderList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PutMapping("/updateOrderStatus")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> updateOrderStatus(@RequestParam("orderID") Integer orderID,
                                               @RequestParam("status") Integer status) {
        int updated = orderService.updateOrderStatus(orderID, status);
        if (updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/cancelOrder")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> cancelOrder(@RequestParam("orderID") Integer orderID) {
        int updated = orderService.cancelOrder(orderID);
        if (updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/addOrder")
    @CrossOrigin
    @Transactional
    public Order addOrder(@RequestParam("customerID") Integer customerID,
                          @RequestParam("merchantID") Integer merchantID,
                          @RequestParam("shopID") Integer shopID,
                          @RequestParam("totalPrice") double totalPrice) {
        return orderService.addOrder(customerID, merchantID, shopID, totalPrice);
    }

}


