package com.wail.inventoryservice;

import com.wail.inventoryservice.entities.Product;
import com.wail.inventoryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.stream.Stream;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration) {
		restConfiguration.exposeIdsFor(Product.class);
		return args -> {
			Stream.of("TV set", "Gameboy", "MacBook Pro", "iPhone 11", "Samsung Galaxy S20")
					.forEach(product -> {
						productRepository.save(Product.builder()
								.name(product)
								.price(Math.random() * 1000)
								.quantity((int) (Math.random() * 100))
								.build());
					});
		};
	}
}
