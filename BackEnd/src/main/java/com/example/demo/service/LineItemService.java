package com.example.demo.service;

import com.example.demo.entity.LineItem;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.repository.LineItemRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LineItemService {

    @Resource
    private LineItemRepository lineItemRepository;
    @Resource
    private ItemRepository itemRepository;
    @Resource
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager entityManager;

    // 根据订单ID查找
    public List<LineItem> findByOrderID(Integer orderID) {
        return lineItemRepository.findByOrderID(orderID);
    }

    // 根据商品ID查找
    public List<LineItem> findByItemID(Integer itemID) {
        return lineItemRepository.findByItemID(itemID);
    }

    // 根据顾客ID和店铺ID查找
    public List<LineItem> findByCustomerIDAndShopID(Integer customerID, Integer shopID) {
        List<LineItem> allLineItems = lineItemRepository.findByCustomerIDAndShopID(customerID, shopID);
        List<LineItem> unpaidLineItems = new ArrayList<>();

        for (LineItem lineItem : allLineItems) {
            Order order = orderRepository.findById(lineItem.getOrderID()).orElse(null);

            if (order != null && order.getStatus() == 0) {
                unpaidLineItems.add(lineItem);
            }
        }

        return unpaidLineItems;
    }

    // 根据商家ID查找
    public List<LineItem> findByMerchantID(Integer merchantID) {
        return lineItemRepository.findByMerchantID(merchantID);
    }

    public LineItem findByLineItemID(Integer lineItemID) {
        return lineItemRepository.findByLineItemID(lineItemID);
    }

    public LineItem findByOrderIDAndItemID(Integer orderID, Integer itemID) {
        return lineItemRepository.findByOrderIDAndItemID(orderID, itemID);
    }

    @Transactional
    public void deleteLineItem(Integer lineItemID) {
        // 检查 lineItem 是否存在
        LineItem lineItem = lineItemRepository.findById(lineItemID)
                .orElseThrow(() -> new RuntimeException("LineItem not found with id " + lineItemID));

        // 删除 lineItem
        lineItemRepository.delete(lineItem);
    }

    @Transactional
    public int updateLineItemQuantityAndTotal(Integer lineItemID, Integer itemID, Integer quantity) {
        Item item = itemRepository.findByItemID(itemID);
        double total = item.getItemPrice() * quantity;
        int result = lineItemRepository.updateLineItemQuantityAndTotal(lineItemID, quantity, total);

        entityManager.clear(); // 清除一级缓存
        entityManager.flush(); // 强制将更新推到数据库

        return result;
    }

    @Transactional
    public LineItem addLineItem(Integer orderID, Integer itemID, Integer customerID, Integer shopID, Integer quantity) {
        Item item = itemRepository.findByItemID(itemID);
        double total = item.getItemPrice() * quantity;

        LineItem newLineItem = new LineItem();
        newLineItem.setOrderID(orderID);
        newLineItem.setItemID(itemID);
        newLineItem.setCustomerID(customerID);
        newLineItem.setMerchantID(item.getMerchantID());
        newLineItem.setShopID(shopID);
        newLineItem.setQuantity(quantity);
        newLineItem.setTotal(total);
        return lineItemRepository.save(newLineItem);
    }

}
