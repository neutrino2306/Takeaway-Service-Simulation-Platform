package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineItemID;
    private Integer orderID;
    private Integer itemID;
    private Integer customerID;
    private Integer merchantID;
    private Integer shopID;
    private Integer quantity;
    private double total;

    @Transient
    private Merchant merchant;
    @Transient
    private Customer customer;
    @Transient
    private Shop shop;
    @Transient
    private Item item;

}
