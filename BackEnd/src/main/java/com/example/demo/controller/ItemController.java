package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.entity.ShopItem;
import com.example.demo.service.ItemService;
import com.example.demo.service.ShopItemService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ItemController")
public class ItemController {

    @Resource
    private ItemService itemService;
    private ShopItemService shopItemService;

    @PostMapping("/findByItemID")
    @CrossOrigin
    public ResponseEntity<Item> findByItemID(@RequestParam("itemID") Integer itemID) {
        Item item = itemService.findByItemID(itemID);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findByItemName")
    @CrossOrigin
    public List<Item> findByItemName(@RequestParam("itemName") String itemName) {
        List<Item> itemList = itemService.findByItemName(itemName);
        if(itemList != null) {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(itemList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByItemNameAndMerchantID")
    @CrossOrigin
    public List<Item> findByItemNameAndMerchantID(@RequestParam("itemName") String itemName,
                                                  @RequestParam("merchantID") Integer merchantID) {
        List<Item> itemList = itemService.findByItemNameAndMerchantID(itemName, merchantID);
        if(itemList != null) {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(itemList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByItemIDAndMerchantID")
    @CrossOrigin
    public List<Item> findByItemIDAndMerchantID(@RequestParam("itemID") Integer itemID,
                                                @RequestParam("merchantID") Integer merchantID) {
        List<Item> itemList = itemService.findByItemIDAndMerchantID(itemID, merchantID);
        if(itemList != null) {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(itemList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByShopItemIDAndShopID")
    @CrossOrigin
    public ResponseEntity<Item> findByShopItemIDAndShopID(@RequestParam("shopItemID") Integer shopItemID,
                                                          @RequestParam("shopID") Integer shopID) {
        Item item = itemService.findByShopItemIDAndShopID(shopItemID, shopID);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findByShopID")
    @CrossOrigin
    public List<Item> findByShopID(@RequestParam("shopID") Integer shopID) {
        List<Item> itemList = itemService.findByShopID(shopID);
        if (itemList != null) {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByShopIDAndMerchantID")
    @CrossOrigin
    public List<Item> findByShopIDAndMerchantID(@RequestParam("shopID") Integer shopID,
                                                @RequestParam("merchantID") Integer merchantID) {
        List<Item> itemList = itemService.findByShopIDAndMerchantID(shopID, merchantID);
        if(itemList != null) {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(itemList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByMerchantID")
    @CrossOrigin
    public List<Item> findByMerchantID(@RequestParam("merchantID") Integer merchantID) {
        List<Item> itemList = itemService.findByMerchantID(merchantID);
        if (itemList != null) {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByItemNameContaining")
    @CrossOrigin
    public List<Item> findByItemNameContaining(@RequestParam("itemName") String itemName){
        List<Item> itemList = itemService.findByItemNameContaining(itemName);
        if(itemList != null){
            return new ResponseEntity<>(itemList, HttpStatus.OK).getBody();
        }else{
            return new ResponseEntity<>(itemList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PutMapping("/updateItemDetails")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> updateItemDetails(@RequestParam("itemID") Integer itemID,
                                               @RequestParam("itemName") String itemName,
                                               @RequestParam("itemDescription") String itemDescription,
                                               @RequestParam("itemPrice") double itemPrice) {
        int updated = itemService.updateItemDetails(itemID, itemName, itemDescription, itemPrice);
        if (updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/addItem")
    @CrossOrigin
    @Transactional
    public Item addItem(@RequestParam("shopItemID") Integer shopItemID,
                        @RequestParam("merchantID") Integer merchantID,
                        @RequestParam("shopID") Integer shopID) {
        return itemService.addItem(shopItemID, merchantID, shopID);
    }

}
