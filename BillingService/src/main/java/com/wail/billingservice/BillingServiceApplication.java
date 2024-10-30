package com.wail.billingservice;

import com.wail.billingservice.entities.Bill;
import com.wail.billingservice.entities.ProductItem;
import com.wail.billingservice.feign.CustomerRestClient;
import com.wail.billingservice.feign.ProductRestClient;
import com.wail.billingservice.model.Customer;
import com.wail.billingservice.model.Product;
import com.wail.billingservice.repositories.BillRepository;
import com.wail.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							ProductRestClient productRestClient
	) {
		return args -> {
			Customer customer = customerRestClient.getCustomerById(1L);
			Bill bill = Bill.builder()
					.billingDate(new Date())
					.customerID(customer.getId())
					.build();
			billRepository.save(bill);
			PagedModel<Product> productPagedModel = productRestClient.pageProducts(0, 20);
			productPagedModel.forEach(p -> {
				ProductItem productItem = ProductItem.builder()
						.price(p.getPrice())
						.quantity(1+new Random().nextInt(100))
						.bill(bill)
						.productID(p.getId())
						.build();
				productItemRepository.save(productItem);
			});
		};
	}
}
