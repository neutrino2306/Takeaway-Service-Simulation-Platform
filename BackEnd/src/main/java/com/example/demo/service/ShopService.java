package com.example.demo.service;

import com.example.demo.entity.Shop;
import com.example.demo.repository.ShopRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Resource
    private ShopRepository shopRepository;

    public List<Shop> findAllShops() {
        return shopRepository.findAll();
    }

    // 查找特定商家ID的商店
    public List<Shop> findByMerchantID(Integer merchantID) {
        return shopRepository.findByMerchantID(merchantID);
    }

    // 根据商店名称精确查找商店
    public Shop findByShopName(String shopName) {
        return shopRepository.findByName(shopName);
    }

    // 根据包含的商店名称查找商店
    public List<Shop> findByShopNameContaining(String shopName) {
        return shopRepository.findByNameContaining(shopName);
    }

    // 根据包含的商店地址查找商店
    public List<Shop> findByShopAddressContaining(String shopAddress) {
        return shopRepository.findByAddressContaining(shopAddress);
    }

    // 根据商店ID查找单个商店
    public Shop findByShopID(Integer shopID) {
        return shopRepository.findByShopID(shopID);
    }

    // 更新商店的名称、地址和描述
    public int updateShopDetails(Integer shopID, String shopName, String shopAddress, String shopDescription) {
        return shopRepository.updateShopDetails(shopID, shopName, shopAddress, shopDescription);
    }

    // 商家新增商店
    public Shop addShop(Integer merchantID, String name, String address, String shopDescription) {
        Shop newShop = new Shop();
        newShop.setMerchantID(merchantID);
        newShop.setName(name);
        newShop.setAddress(address);
        newShop.setShopDescription(shopDescription);
        return shopRepository.save(newShop); // 保存新的商店
    }

}
