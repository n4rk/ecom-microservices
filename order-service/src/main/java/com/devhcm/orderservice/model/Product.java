package com.devhcm.orderservice.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private int quantity;
    private double price;
}
