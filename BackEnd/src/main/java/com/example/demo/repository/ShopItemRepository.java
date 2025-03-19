package com.example.demo.repository;

import com.example.demo.entity.Item;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, Integer> {

    // 根据商家ID查找
    public List<ShopItem> findByMerchantID(Integer merchantID);

    public ShopItem findByShopItemID(Integer shopItemID);

    public ShopItem findByShopItemIDAndMerchantID(Integer shopItemID, Integer merchantID);

    public List<ShopItem> findByMerchantIDAndShopItemName(Integer merchantID, String shopItemName);

    public List<ShopItem> findByMerchantIDAndShopItemNameContaining(Integer merchantID, String shopItemName);

    public List<ShopItem> findByMerchantIDAndStatus(Integer merchantID, Integer status);

    // 同时更新店铺商品的价格、名称和描述
    @Modifying
    @Transactional
    @Query("update ShopItem si set si.shopItemPrice = ?2, si.shopItemName = ?3, si.shopItemDescription = ?4 where si.shopItemID = ?1")
    public int updateShopItemDetails(Integer shopItemID, double shopItemPrice, String shopItemName, String shopItemDescription);


}
