package com.example.demo.repository;

import com.example.demo.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    // 根据商家ID查找商店
    public List<Shop> findByMerchantID(Integer merchantID);

    // 根据商店名称查找商店
    public Shop findByName(String name);

    public List<Shop> findByNameContaining(String name);

    // 根据商店地址查找商店
    public List<Shop> findByAddressContaining(String address);

    public Shop findByShopID(Integer shopID);

    @Modifying
    @Transactional
    @Query("update Shop s set s.name = ?2, s.address = ?3, s.shopDescription = ?4 where s.shopID = ?1")
    public int updateShopDetails(Integer shopID, String name, String address, String shopDescription);

}
