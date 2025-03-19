package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopID;
    private Integer merchantID;
    private String name;
    private String address;
    private String shopDescription;
    private String shopImg;

    @Transient
    private Merchant merchant;

}
