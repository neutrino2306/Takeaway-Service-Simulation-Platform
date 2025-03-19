package com.example.demo.controller;

import com.example.demo.entity.Merchant;
import com.example.demo.service.MerchantService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/MerchantController")
public class MerchantController {

    @Resource
    private MerchantService merchantService;

    @PostMapping("/MerchantLogin")
    @CrossOrigin
    public ResponseEntity<Merchant> MerchantLogin(@RequestParam("merchantPhone") String merchantPhone,
                                                  @RequestParam("merchantPassword") String merchantPassword) {
        Merchant merchant = merchantService.findByMerchantPhoneAndMerchantPassword(merchantPhone, merchantPassword);
        if(merchant != null) {
            return new ResponseEntity<>(merchant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findByMerchantID")
    @CrossOrigin
    public ResponseEntity<Merchant> findByMerchantID(@RequestParam("merchantID") Integer merchantID) {
        Merchant merchant = merchantService.findByMerchantID(merchantID);
        if(merchant != null) {
            return new ResponseEntity<>(merchant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findByMerchantPhone")
    @CrossOrigin
    public ResponseEntity<Merchant> findByMerchantPhone(@RequestParam("merchantPhone") String merchantPhone) {
        Merchant merchant = merchantService.findByMerchantPhone(merchantPhone);
        if(merchant != null) {
            return new ResponseEntity<>(merchant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateMerchant")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> updateMerchant(@RequestParam("merchantID") Integer merchantID,
                                            @RequestParam("merchantName") String merchantName,
                                            @RequestParam("merchantDescription") String merchantDescription,
                                            @RequestParam("merchantPhone") String merchantPhone,
                                            @RequestParam("merchantPassword") String merchantPassword) {
        int updated = merchantService.updateMerchantInfo(merchantID, merchantName, merchantDescription, merchantPhone, merchantPassword);
        if (updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/addMerchant")
    @CrossOrigin
    @Transactional
    public Merchant addMerchant(@RequestParam("merchantPhone") String merchantPhone,
                                @RequestParam("merchantPassword") String merchantPassword,
                                @RequestParam("merchantName") String merchantName,
                                @RequestParam("merchantDescription") String merchantDescription) {
        return merchantService.addMerchant(merchantPhone, merchantPassword, merchantName, merchantDescription);
    }

}
