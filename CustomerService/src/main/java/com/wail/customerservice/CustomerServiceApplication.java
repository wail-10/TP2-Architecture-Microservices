package com.wail.customerservice;

import com.wail.customerservice.entities.Customer;
import com.wail.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration) {
		restConfiguration.exposeIdsFor(Customer.class);
		return args -> {
			Stream.of("Mohammed", "Yassine", "Omar", "Imane", "Houda").forEach(name -> {
				Customer customer = Customer.builder()
						.name(name)
						.email(name.toLowerCase() + "@gmail.com")
						.build();
				customerRepository.save(customer);
			});
		};
	}
}
