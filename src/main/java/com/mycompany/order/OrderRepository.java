package com.mycompany.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}
