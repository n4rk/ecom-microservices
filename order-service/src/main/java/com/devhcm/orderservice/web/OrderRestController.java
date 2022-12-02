package com.devhcm.orderservice.web;

import com.devhcm.orderservice.entities.Order;
import com.devhcm.orderservice.model.Customer;
import com.devhcm.orderservice.model.Product;
import com.devhcm.orderservice.repositories.OrderRepository;
import com.devhcm.orderservice.repositories.ProductItemRepository;
import com.devhcm.orderservice.services.CustomerRestClientService;
import com.devhcm.orderservice.services.InventoryRestClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClientService customerRestClientService;
    private InventoryRestClientService inventoryRestClientService;

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Customer customer = customerRestClientService.customerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductsList().forEach(productItem -> {
            Product product = inventoryRestClientService.productById(productItem.getProductId());
            productItem.setProduct(product);
        });
        return order;
    }
}
