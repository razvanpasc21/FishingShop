package com.mycompany;

import com.mycompany.product.Product;
import org.jboss.jandex.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

@SpringBootApplication
public class MywebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MywebappApplication.class, args);
        //MainController mainController = new MainController();
    }

}
