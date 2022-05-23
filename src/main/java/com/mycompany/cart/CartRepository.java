package com.mycompany.cart;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<ProductInCart, Integer> {

}
