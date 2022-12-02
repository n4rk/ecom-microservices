package com.devhcm.customerservice;

import com.devhcm.customerservice.entities.Customer;
import com.devhcm.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository) {
		return args -> {
			customerRepository.saveAll(
					List.of(
						Customer.builder().name("Hachmi Mohamed Amine").email("hachmi@amine.com").build(),
						Customer.builder().name("Ajana Hamza").email("ajana@hamza.com").build(),
						Customer.builder().name("Hachmi Houssine").email("hachmi@houssine.com").build(),
						Customer.builder().name("Belmoubarik Merouane").email("belmoubarik@merouane.com").build(),
						Customer.builder().name("Belmoubarik Mustapha").email("belmoubarik@mustapha.com").build()
					)
			);
			customerRepository.findAll().forEach(System.out::println);
		};
	}
}
