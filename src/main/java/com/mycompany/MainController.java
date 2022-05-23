package com.mycompany;

import com.mycompany.cart.AddToCartRequest;
import com.mycompany.cart.Cart;
import com.mycompany.cart.ProductInCart;
import com.mycompany.cart.CartRepository;
import com.mycompany.product.Product;
import com.mycompany.product.ProductRepository;
import com.mycompany.user.LoginRequest;
import com.mycompany.user.RegisterRequest;
import com.mycompany.user.User;
import com.mycompany.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {
    ProductRepository productRepository;
    UserRepository userRepository;
    CartRepository cartRepository;

    @Autowired
    MainController(ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
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
    public Integer verifyIfUserExists(@RequestBody LoginRequest loginRequest) {
        Iterable<User> usersList = getAllUsers();
        for(User user : usersList) {
            if (user.getEmail().equals(loginRequest.getEmail())) {
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    return user.getId();
                }
            }
        }
        return -1;
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
    @PostMapping("/cart")
    public ProductInCart addToCart(@RequestBody AddToCartRequest addToCartRequest) {
        ProductInCart productInCart = new ProductInCart();
        productInCart.setProductId(addToCartRequest.getProductId());
        productInCart.setUserId(addToCartRequest.getUserId());
        ProductInCart testProductInCart = cartRepository.save(productInCart);

        return testProductInCart;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cart")
    public Cart getCart(@RequestParam Integer userId) {
        Iterable<ProductInCart> allProductsInCarts = cartRepository.findAll();
        List<Product> products = new ArrayList<>();
        Double totalCartPrice = 0.0;
        for(ProductInCart productInCart : allProductsInCarts) {
            if(productInCart.getUserId() == userId) {
                Product product = productRepository.findById((productInCart.getProductId())).get();
                products.add(product);
                totalCartPrice += product.getPrice();
            }
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductList(products);
        cart.setTotalPrice(totalCartPrice);

        return cart;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("")
    public String showHomePage() {
        return "index";
    }
}
