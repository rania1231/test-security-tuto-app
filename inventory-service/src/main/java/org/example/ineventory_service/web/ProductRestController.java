package org.example.ineventory_service.web;

import org.example.ineventory_service.entities.Product;
import org.example.ineventory_service.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
 @RequestMapping("/api")
public class ProductRestController {
  private ProductRepository productRepository;

  public ProductRestController(ProductRepository productRepository) {
   this.productRepository = productRepository;
  }
  @GetMapping("/products")
  public List<Product> getAllProducts(){
   return productRepository.findAll();
  }
  @GetMapping("/products/{id}")
  public Product getProductById(@PathVariable String id){
   return productRepository.findById(id).orElseThrow();
  }

}
