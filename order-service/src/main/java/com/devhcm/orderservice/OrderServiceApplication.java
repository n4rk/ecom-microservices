package com.devhcm.orderservice;

import com.devhcm.orderservice.entities.Order;
import com.devhcm.orderservice.entities.ProductItem;
import com.devhcm.orderservice.enums.OrderStatus;
import com.devhcm.orderservice.model.Customer;
import com.devhcm.orderservice.model.Product;
import com.devhcm.orderservice.repositories.OrderRepository;
import com.devhcm.orderservice.repositories.ProductItemRepository;
import com.devhcm.orderservice.services.CustomerRestClientService;
import com.devhcm.orderservice.services.InventoryRestClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(OrderRepository orderRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClientService customerRestClientService,
							InventoryRestClientService inventoryRestClientService) {
		return args -> {
			List<Customer> customers = customerRestClientService.allCustomers().getContent().stream().toList();
			List<Product> products = inventoryRestClientService.allProducts().getContent().stream().toList();
			Long customerId = 1L;
			Random random = new Random();
			Customer c = customerRestClientService.customerById(customerId);
			for(int i=0; i<20; i++) {
				Order o = Order.builder()
						.customerId(customers.get(random.nextInt(customers.size())).getId())
						.status(Math.random()>0.5?OrderStatus.PENDING:OrderStatus.CREATED)
						.createdAt(new Date())
						.build();
				Order savedOrder = orderRepository.save(o);
				for (int j = 0; j < products.size(); j++) {
					if (Math.random() > 0.7) {
						ProductItem productItem = ProductItem.builder()
								.order(savedOrder)
								.productId(products.get(j).getId())
								.quantity(1 + random.nextInt(10))
								.price(products.get(j).getPrice())
								.discount(Math.random())
								.build();
						productItemRepository.save(productItem);
					}
				}
			}

		};
	}
}
