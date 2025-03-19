package com.example.demo.service;

import com.example.demo.entity.Merchant;
import com.example.demo.repository.MerchantRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantService {

    @Resource
    private MerchantRepository merchantRepository;

    public Merchant findByMerchantPhoneAndMerchantPassword(String merchantPhone, String merchantPassword) {
        return merchantRepository.findByMerchantPhoneAndMerchantPassword(merchantPhone, merchantPassword);
    }

    public Merchant findByMerchantID(Integer merchantID) {
        return merchantRepository.findByMerchantID(merchantID);
    }

    public Merchant findByMerchantPhone(String merchantPhone){
        return merchantRepository.findByMerchantPhone(merchantPhone);
    }

    // 更新商家名称和描述
    @Transactional
    public int updateMerchantInfo(Integer merchantID, String merchantName, String merchantDescription, String merchantPhone, String merchantPassword) {
        return merchantRepository.updateMerchantInfo(merchantID, merchantName, merchantDescription, merchantPhone, merchantPassword);
    }

    // 添加新商家
    public Merchant addMerchant(String merchantPhone, String merchantPassword, String merchantName, String merchantDescription) {
        // 检查是否已存在具有相同电话号码的商家
        if (merchantRepository.findByMerchantPhone(merchantPhone) == null) {
            Merchant newMerchant = new Merchant();
            newMerchant.setMerchantPhone(merchantPhone);
            newMerchant.setMerchantPassword(merchantPassword);
            newMerchant.setMerchantName(merchantName);
            newMerchant.setMerchantDescription(merchantDescription);
            return merchantRepository.save(newMerchant); // 保存新商家
        }
        return null; // 如果已存在相同电话号码的商家，则返回null
    }

}
