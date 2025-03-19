package com.example.demo.service;

import com.example.demo.entity.ShopItem;
import com.example.demo.repository.ShopItemRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShopItemService {

    @Resource
    private ShopItemRepository shopItemRepository;

    // 根据商家ID查找
    public List<ShopItem> findByMerchantID(Integer merchantID) {
        return shopItemRepository.findByMerchantID(merchantID);
    }

    public ShopItem findByShopItemIDAndMerchantID(Integer shopItemID, Integer merchantID) {
        return shopItemRepository.findByShopItemIDAndMerchantID(shopItemID, merchantID);
    }

    public ShopItem findByShopItemID(Integer shopItemID) {
        return shopItemRepository.findByShopItemID(shopItemID);
    }

    public List<ShopItem> findByMerchantIDAndShopItemName(Integer merchantID, String shopItemName) {
        return shopItemRepository.findByMerchantIDAndShopItemName(merchantID, shopItemName);
    }

    // 根据商家ID和名称（模糊查询）查找
    public List<ShopItem> findByMerchantIDAndShopItemNameContaining(Integer merchantID, String shopItemName) {
        return shopItemRepository.findByMerchantIDAndShopItemNameContaining(merchantID, shopItemName);
    }

    // 根据状态查找
    public List<ShopItem> findByMerchantIDAndStatus(Integer merchantID, Integer status) {
        return shopItemRepository.findByMerchantIDAndStatus(merchantID, status);
    }

    public int updateShopItemDetails(Integer shopItemID, double shopItemPrice, String shopItemName, String shopItemDescription) {
        return shopItemRepository.updateShopItemDetails(shopItemID, shopItemPrice, shopItemName, shopItemDescription);
    }

    // 添加新店铺商品
    public ShopItem addShopItem(Integer merchantID, String shopItemName, double shopItemPrice, String shopItemDescription) {
        ShopItem newShopItem = new ShopItem();
        newShopItem.setMerchantID(merchantID);
        newShopItem.setShopItemName(shopItemName);
        newShopItem.setShopItemPrice(shopItemPrice);
        newShopItem.setStatus(0);
        newShopItem.setShopItemDescription(shopItemDescription);
        return shopItemRepository.save(newShopItem); // 直接保存新的店铺商品
    }

}
