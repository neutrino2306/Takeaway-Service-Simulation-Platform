package com.example.demo.controller;

import com.example.demo.entity.Shop;
import com.example.demo.service.ShopService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ShopController")
public class ShopController {

    @Resource
    private ShopService shopService;

    @PostMapping("/listAllShops")
    @CrossOrigin
    public List<Shop> listAllShops() {
        List<Shop> shopList = shopService.findAllShops();
        if (shopList != null) {
            return new ResponseEntity<>(shopList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(shopList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByMerchantID")
    @CrossOrigin
    public List<Shop> findByMerchantID(@RequestParam("merchantID") Integer merchantID) {
        List<Shop> shopList = shopService.findByMerchantID(merchantID);
        if (shopList != null) {
            return new ResponseEntity<>(shopList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(shopList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByShopID")
    @CrossOrigin
    public ResponseEntity<Shop> findByShopID(@RequestParam("shopID") Integer shopID) {
        Shop shop = shopService.findByShopID(shopID);
        if (shop != null) {
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findByShopName")
    @CrossOrigin
    public ResponseEntity<Shop> findByShopName(@RequestParam("shopName") String shopName) {
        Shop shop = shopService.findByShopName(shopName);
        if (shop != null) {
            return new ResponseEntity<>(shop, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findByShopNameContaining")
    @CrossOrigin
    public List<Shop> findByShopNameContaining(@RequestParam("shopName") String shopName){
        List<Shop> shopList = shopService.findByShopNameContaining(shopName);
        if(shopList != null){
            return new ResponseEntity<>(shopList, HttpStatus.OK).getBody();
        }else{
            return new ResponseEntity<>(shopList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PostMapping("/findByShopAddressContaining")
    @CrossOrigin
    public List<Shop> findByShopAddressContaining(@RequestParam("shopAddress") String shopAddress){
        List<Shop> shopList = shopService.findByShopAddressContaining(shopAddress);
        if(shopList != null){
            return new ResponseEntity<>(shopList, HttpStatus.OK).getBody();
        }else{
            return new ResponseEntity<>(shopList, HttpStatus.NOT_FOUND).getBody();
        }
    }

    @PutMapping("/updateShopDetails")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> updateShopDetails(@RequestParam("shopID") Integer shopID,
                                               @RequestParam("shopName") String shopName,
                                               @RequestParam("shopAddress") String shopAddress,
                                               @RequestParam("shopDescription") String shopDescription) {
        int updated = shopService.updateShopDetails(shopID, shopName, shopAddress, shopDescription);
        if (updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/addShop")
    @CrossOrigin
    @Transactional
    public Shop addShop(@RequestParam("merchantID") Integer merchantID,
                        @RequestParam("shopName") String shopName,
                        @RequestParam("shopAddress") String shopAddress,
                        @RequestParam("shopDescription") String shopDescription) {
        return shopService.addShop(merchantID, shopName, shopAddress, shopDescription);
    }

}
