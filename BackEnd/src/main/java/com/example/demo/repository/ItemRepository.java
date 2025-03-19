package com.example.demo.repository;

import com.example.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {


    // 根据商品ID查找单个商品
    public Item findByItemID(Integer itemID);

    // 根据商品名称查找商品
    public List<Item> findByItemName(String itemName);

    public List<Item> findByItemNameAndMerchantID(String itemName, Integer merchantID);

    public List<Item> findByItemIDAndMerchantID(Integer itemID, Integer merchantID);

    // 根据门店ID查找商品
    public List<Item> findByShopID(Integer shopID);

    public List<Item> findByShopIDAndMerchantID(Integer shopID, Integer merchantID);

    // 根据商家ID查找商品
    public List<Item> findByMerchantID(Integer merchantID);

    // 根据商品名称模糊查询
    public List<Item> findByItemNameContaining(String itemName);

    public Item findByShopItemIDAndShopID(Integer shopItemID, Integer shopID);

    // 更新商品信息：名字、描述、价格
    @Modifying
    @Transactional
    @Query("update Item i set i.itemName = ?2, i.itemDescription = ?3, i.itemPrice = ?4 where i.itemID = ?1")
    public int updateItemDetails(Integer itemID, String itemName, String itemDescription, double itemPrice);

}
