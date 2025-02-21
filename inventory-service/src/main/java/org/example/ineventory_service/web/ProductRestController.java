package org.example.ineventory_service.web;

import org.example.ineventory_service.entities.Product;
import org.example.ineventory_service.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
 @RequestMapping("/api")
public class ProductRestController {
  private ProductRepository productRepository;

  public ProductRestController(ProductRepository productRepository) {
   this.productRepository = productRepository;
  }
  @GetMapping("/products")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<Product> getAllProducts(){
   return productRepository.findAll();
  }
  @GetMapping("/products/{id}")
  public Product getProductById(@PathVariable String id){
   return productRepository.findById(id).orElseThrow();
  }

    @GetMapping("/auth")
    public Authentication authentication() {
       return SecurityContextHolder.getContext().getAuthentication();

    }


}
