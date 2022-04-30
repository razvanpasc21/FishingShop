package com.mycompany.user;

import com.mycompany.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    
}
