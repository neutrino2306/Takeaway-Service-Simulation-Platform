package com.example.demo.repository;

import com.example.demo.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

    public Merchant findByMerchantPhoneAndMerchantPassword(String merchantPhone, String merchantPassword);

    public Merchant findByMerchantPhone(String merchantPhone);

    public Merchant findByMerchantID(Integer merchantID);

    // 更新商家名称和描述
    @Modifying
    @Transactional
    @Query("update Merchant m set m.merchantName = ?2, m.merchantDescription = ?3, m.merchantPhone = ?4, m.merchantPassword = ?5 where m.merchantID = ?1")
    public int updateMerchantInfo(Integer merchantID, String merchantName, String merchantDescription, String merchantPhone, String merchantPassword);

}
