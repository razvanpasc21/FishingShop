package com.mycompany;

import com.mycompany.product.Product;
import com.mycompany.product.ProductRepository;
import com.mycompany.user.LoginRequest;
import com.mycompany.user.RegisterRequest;
import com.mycompany.user.User;
import com.mycompany.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {
    ProductRepository productRepository;
    UserRepository userRepository;

    @Autowired
    MainController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:8083")
    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/products/{id}")
    public Optional<Product> getProductById(@PathVariable Integer id) {
        return productRepository.findById(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return product;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/users")
    public void addUser(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword((registerRequest.getPassword()));
        user.setGender(registerRequest.getGender());
        userRepository.save(user);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public String verifyIfUserExists(@RequestBody LoginRequest loginRequest) {
        Iterable<User> usersList = getAllUsers();
        for(User user : usersList) {
            if (user.getEmail().equals(loginRequest.getEmail())) {
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    return UUID.randomUUID().toString();
                }
            }
        }
        return "null";
    }

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
    @PutMapping("/users")
    public boolean updateUser(@RequestBody User user) {
        boolean userExists = userRepository.existsById(user.getId());
        if(userExists) {
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/products/{id}")
    public void updateProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/users/{id}")
    public void updateUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("")
    public String showHomePage() {
        return "index";
    }
}
