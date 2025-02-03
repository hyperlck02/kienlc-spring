package org.kienlc.kienlc.authen.controller;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class TestController {
    private record Product(Integer id, String name, Double price) {}

    List<Product> products = new ArrayList<>(List.of(
            new Product(1, "iphone16", 100000d),
            new Product(2, "macbook", 2000000d),
            new Product(3, "shirt", 1000d)
    ));

    @GetMapping("")
    public List<Product> getProducts() {
        return products;
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }
}
