package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemID;
    private String itemName;
    private String itemDescription;
    private String itemImg;
    private double itemPrice;
    private Integer merchantID;
    private Integer shopID;
    private Integer shopItemID;

    @Transient
    private Merchant merchant;
    @Transient
    private Shop shop;
    @Transient
    private ShopItem shopItem;

}
