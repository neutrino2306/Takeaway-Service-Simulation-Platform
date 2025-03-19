package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;
    private Integer customerID;
    private Integer merchantID;
    private Integer shopID;
    private Integer status;
    private double totalPrice;

    private LocalDateTime orderDate;

    @Transient
    private Merchant merchant;

    @Transient
    private Shop shop;

    @Transient
    private List<LineItem> lineItemList;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }

}
