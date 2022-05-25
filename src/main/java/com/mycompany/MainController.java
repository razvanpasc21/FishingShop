package com.mycompany;

import com.mycompany.cart.*;
import com.mycompany.order.Order;
import com.mycompany.order.OrderRepository;
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
    OrderRepository orderRepository;

    @Autowired
    MainController(ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
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
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id);
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
        user.setAdmin(0);
        user.setBalance(10000000.0);
        userRepository.save(user);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public Optional<User> verifyIfUserExists(@RequestBody LoginRequest loginRequest) {
        Iterable<User> usersList = getAllUsers();
        for(User user : usersList) {
            if (user.getEmail().equals(loginRequest.getEmail())) {
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
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
    @DeleteMapping("/cart/{id}")
    public void deleteProductFromCart(@PathVariable Integer id) {
        cartRepository.deleteById(id);
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
        List<ProductWithCartId> productWithCartIdList = new ArrayList<>();
        Double totalCartPrice = 0.0;
        for(ProductInCart productInCart : allProductsInCarts) {
            if(productInCart.getUserId() == userId) {
                Product product = productRepository.findById((productInCart.getProductId())).get();
                ProductWithCartId productWithCartId = new ProductWithCartId();
                productWithCartId.setId(product.getId());
                productWithCartId.setDescription(product.getDescription());
                productWithCartId.setName(product.getName());
                productWithCartId.setQuantity(product.getQuantity());
                productWithCartId.setCartId(productInCart.getId());
                productWithCartId.setPrice(product.getPrice());
                productWithCartIdList.add(productWithCartId);
                totalCartPrice += product.getPrice();
            }
        }
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductList(productWithCartIdList);
        cart.setTotalPrice(totalCartPrice);

        return cart;
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/orders/{id}")
    public boolean addOrder(@PathVariable Integer id) {
        Order order = new Order();
        User user = getUserById(id).get();
        Cart cart = getCart(id);
        Double sumOfAllCartProducts = 0.0;
        for(ProductWithCartId productWithCartId : cart.getProductList()) {
            sumOfAllCartProducts += productWithCartId.getPrice();
        }
        if (user.getBalance() - sumOfAllCartProducts < 0) {
            return false;
        }
        for(ProductWithCartId productWithCartId : cart.getProductList()) {
            deleteProductFromCart(productWithCartId.getCartId());
        }
        order.setClientId(user.getId());
        order.setPrice(sumOfAllCartProducts);
        orderRepository.save(order);
        user.setBalance(user.getBalance() - sumOfAllCartProducts);
        updateUser(user);
        return true;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/orders/{id}")
    public List<Order> getAllOrdersForUser(@PathVariable Integer id) {
        Iterable<Order> allOrders = orderRepository.findAll();
        List<Order> allUserOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getClientId() == id) {
                allUserOrders.add(order);
            }
        }
        return allUserOrders;
    }


    @CrossOrigin(origins = "*")
    @GetMapping("")
    public String showHomePage() {
        return "index";
    }
}
