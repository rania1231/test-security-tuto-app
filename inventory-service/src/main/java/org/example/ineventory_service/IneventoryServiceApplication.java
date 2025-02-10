package org.example.ineventory_service;

import org.example.ineventory_service.entities.Product;
import org.example.ineventory_service.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class IneventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IneventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ProductRepository productRepository){
		return args -> {
			productRepository.save(new Product(UUID.randomUUID().toString(),"mohamed",2.01,10));
			productRepository.save(new Product(UUID.randomUUID().toString(),"amine",12.01,50));
			productRepository.save(new Product(UUID.randomUUID().toString(),"yosra",22.01,80));

		};
	}

}
