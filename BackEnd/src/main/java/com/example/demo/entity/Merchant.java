package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer merchantID;
    private String merchantName;
    private String merchantPhone;
    private String merchantPassword;
    private String merchantImg;
    private String merchantDescription;

    @Transient
    private List<Shop> shopList;

}
