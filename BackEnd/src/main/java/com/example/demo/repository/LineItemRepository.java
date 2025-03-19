package com.example.demo.repository;

import com.example.demo.entity.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer> {

    // 根据订单ID查找
    public List<LineItem> findByOrderID(Integer orderID);

    // 根据商品ID查找
    public List<LineItem> findByItemID(Integer itemID);

    // 根据顾客ID和门店ID查找
    public List<LineItem> findByCustomerIDAndShopID(Integer customerID, Integer shopID);

    // 根据商家ID查找
    public List<LineItem> findByMerchantID(Integer merchantID);

    public LineItem findByLineItemID(Integer lineItemID);

    public LineItem findByOrderIDAndItemID(Integer orderID, Integer itemID);

    @Modifying
    @Transactional
    @Query("update LineItem li set li.quantity = ?2, li.total = ?3 where li.lineItemID = ?1")
    public int updateLineItemQuantityAndTotal(Integer lineItemId, Integer quantity, double total);

}


