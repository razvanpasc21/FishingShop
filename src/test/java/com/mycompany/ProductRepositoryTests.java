package com.mycompany;

import com.mycompany.product.Product;
import com.mycompany.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
    @Autowired private ProductRepository repo;

    @Test
    public void testAddNew() {
        Product product = new Product();
        product.setName("Undita Accapulco 2.1m");
        product.setDescription("Aceasta undita este folosita pe lacuri de dimensiunsi mici");
        product.setPrice(99.99);
        product.setImage("images/test.png");
        product.setQuantity(20);

        Product savedProduct = repo.save(product);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        
    }
}
