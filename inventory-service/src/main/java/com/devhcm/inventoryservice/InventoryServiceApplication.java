package com.devhcm.inventoryservice;

import com.devhcm.inventoryservice.entities.Product;
import com.devhcm.inventoryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository) {
		return args -> {
			Random random = new Random();
			for(int i=1;i<=10;i++)
				productRepository.saveAll(List.of(
							Product.builder().name("Computer " + i ).quantity(1+random.nextInt(200)).price(Math.random()*10000).build()
				));
			productRepository.findAll().forEach(System.out::println);
		};
	}
}
