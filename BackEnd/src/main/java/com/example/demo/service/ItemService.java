package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.entity.ShopItem;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ShopItemRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Resource
    private ItemRepository itemRepository;
    @Resource
    private ShopItemRepository shopItemRepository;

    // 根据商品ID查找单个商品
    public Item findByItemID(Integer itemID) {
        return itemRepository.findByItemID(itemID);
    }

    // 根据商品名称查找商品
    public List<Item> findByItemName(String itemName) {
        return itemRepository.findByItemName(itemName);
    }

    // 根据商品名称和商家ID查找商品
    public List<Item> findByItemNameAndMerchantID(String itemName, Integer merchantID) {
        return itemRepository.findByItemNameAndMerchantID(itemName, merchantID);
    }

    // 根据商品ID和商家ID查找商品
    public List<Item> findByItemIDAndMerchantID(Integer itemID, Integer merchantID) {
        return itemRepository.findByItemIDAndMerchantID(itemID, merchantID);
    }

    public Item findByShopItemIDAndShopID(Integer shopItemID, Integer shopID) {
        return itemRepository.findByShopItemIDAndShopID(shopItemID, shopID);
    }

    // 根据门店ID查找商品
    public List<Item> findByShopID(Integer shopID) {
        return itemRepository.findByShopID(shopID);
    }

    // 根据门店ID和商家ID查找商品
    public List<Item> findByShopIDAndMerchantID(Integer shopID, Integer merchantID) {
        return itemRepository.findByShopIDAndMerchantID(shopID, merchantID);
    }

    // 根据商家ID查找商品
    public List<Item> findByMerchantID(Integer merchantID) {
        return itemRepository.findByMerchantID(merchantID);
    }

    // 根据商品名称模糊查询
    public List<Item> findByItemNameContaining(String itemName) {
        return itemRepository.findByItemNameContaining(itemName);
    }

    // 更新商品信息：名字、描述、价格
    public int updateItemDetails(Integer itemID, String itemName, String itemDescription, double itemPrice) {
        return itemRepository.updateItemDetails(itemID, itemName, itemDescription, itemPrice);
    }

    // 添加新商品
    public Item addItem(Integer shopItemID, Integer merchantID, Integer shopID) throws CustomException {
        // 检查是否已有相同的ItemID在这个店铺中
        Item existingItem = findByShopItemIDAndShopID(shopItemID, shopID);
        if (existingItem != null) {
            throw new CustomException("An item with the same ShopItemID already exists in this shop");
        }

        ShopItem shopItem = shopItemRepository.findByShopItemIDAndMerchantID(shopItemID, merchantID);

        if (shopItem == null) {
            throw new CustomException("ShopItem does not exist");
        } else {
            Item newItem = new Item();
            newItem.setItemName(shopItem.getShopItemName());
            newItem.setItemPrice(shopItem.getShopItemPrice());
            newItem.setItemDescription(shopItem.getShopItemDescription());
            newItem.setItemImg(shopItem.getShopItemImg());
            newItem.setMerchantID(merchantID);
            newItem.setShopID(shopID);
            newItem.setShopItemID(shopItemID);

            itemRepository.save(newItem);

            return newItem;
        }
    }

}
class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}