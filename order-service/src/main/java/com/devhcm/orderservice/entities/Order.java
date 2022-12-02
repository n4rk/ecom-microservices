package com.devhcm.orderservice.entities;

import com.devhcm.orderservice.enums.OrderStatus;
import com.devhcm.orderservice.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long customerId;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productsList;

    public double getTotal() {
        double somme=0;
        for(ProductItem pi:productsList)
            somme+=pi.getAmount();
        return somme;
    }
}
