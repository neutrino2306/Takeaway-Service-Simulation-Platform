package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.entity.ShopItem;
import com.example.demo.service.ShopItemService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ShopItemController")
public class ShopItemController {

    @Resource
    private ShopItemService shopItemService;

    @PostMapping("/findByMerchantID")
    @CrossOrigin
    public List<ShopItem> findByMerchantID(@RequestParam("MerchantID") Integer merchantID) {
        List<ShopItem> shopItemList = shopItemService.findByMerchantID(merchantID);
        if (shopItemList != null) {
            return new ResponseEntity<>(shopItemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(shopItemList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByShopItemID")
    @CrossOrigin
    public ShopItem findByShopItemID(@RequestParam("ShopItemID") Integer shopItemID) {
        ShopItem shopItem = shopItemService.findByShopItemID(shopItemID);
        if (shopItem != null) {
            return new ResponseEntity<>(shopItem, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(shopItem, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByMerchantIDAndShopItemName")
    @CrossOrigin
    public List<ShopItem> findByMerchantIDAndShopItemName(@RequestParam("MerchantID") Integer merchantID,
                                                          @RequestParam("ShopItemName") String shopItemName) {
        List<ShopItem> shopItem = shopItemService.findByMerchantIDAndShopItemName(merchantID, shopItemName);
        if(shopItem != null) {
            return new ResponseEntity<>(shopItem, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(shopItem, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByMerchantIDAndShopItemNameContaining")
    @CrossOrigin
    public List<ShopItem> findByMerchantIDAndShopItemNameContaining(@RequestParam("MerchantID") Integer merchantID,
                                                                    @RequestParam("ShopItemName") String shopItemName) {
        List<ShopItem> shopItem = shopItemService.findByMerchantIDAndShopItemNameContaining(merchantID, shopItemName);
        if(shopItem != null) {
            return new ResponseEntity<>(shopItem, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(shopItem, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByMerchantIDAndStatus")
    @CrossOrigin
    public List<ShopItem> findByMerchantIDAndStatus(@RequestParam("MerchantID") Integer merchantID,
                                                    @RequestParam("Status") Integer status) {
        List<ShopItem> shopItemList = shopItemService.findByMerchantIDAndStatus(merchantID, status);
        if(shopItemList != null) {
            return new ResponseEntity<>(shopItemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(shopItemList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PutMapping("/updateShopItem")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> updateShopItem(@RequestParam("shopItemID") Integer shopItemID,
                                            @RequestParam("shopItemPrice") double shopItemPrice,
                                            @RequestParam("shopItemName") String shopItemName,
                                            @RequestParam("shopItemDescription") String shopItemDescription) {
        int updated = shopItemService.updateShopItemDetails(shopItemID, shopItemPrice, shopItemName, shopItemDescription);
        if (updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/addShopItem")
    @CrossOrigin
    @Transactional
    public ShopItem addShopItem(@RequestParam("merchantID") Integer merchantID,
                                @RequestParam("shopItemName") String shopItemName,
                                @RequestParam("shopItemPrice") double shopItemPrice,
                                @RequestParam("shopItemDescription") String shopItemDescription) {
        return shopItemService.addShopItem(merchantID, shopItemName, shopItemPrice, shopItemDescription);
    }

}