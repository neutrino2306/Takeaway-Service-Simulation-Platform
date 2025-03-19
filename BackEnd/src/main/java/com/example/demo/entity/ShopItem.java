package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopItemID;
    private Integer merchantID;
    private Integer status;
    private String shopItemName;
    private String shopItemDescription;
    private String shopItemImg;
    private double shopItemPrice;

    @Transient
    private Merchant merchant;
}
