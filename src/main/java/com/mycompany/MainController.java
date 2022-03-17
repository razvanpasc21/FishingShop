package com.mycompany;

import com.mycompany.product.Product;
import com.mycompany.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class MainController {
    ProductRepository productRepository;

    @Autowired
    MainController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Optional<Product> getProductById(@PathVariable Integer id) {
        return productRepository.findById(id);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @PutMapping("/products")
    public boolean updateProduct(@RequestBody Product product) {
        boolean productExists = productRepository.existsById(product.getId());
        if(productExists) {
            productRepository.save(product);
            return true;
        }
        else {
            return false;
        }
    }

    @DeleteMapping("/products/{id}")
    public void updateProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }
}
