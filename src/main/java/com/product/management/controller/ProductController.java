package com.product.management.controller;

import com.product.management.model.Product;
import com.product.management.service.ProductServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("api/v1")
public class ProductController {

    private final ProductServiceInterface  productServiceInterface;

    @GetMapping("/product")
    public List<Product> getAllProducts() {
        log.info("Entering controller to get all product details");
        return productServiceInterface.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        log.info("Entering controller to get product details for {}", id);
        return productServiceInterface.getProductById(id);
    }

    @PostMapping("/product")
    public Product addProductDetails(@RequestBody Product product) {
        log.info("Entering controller to add product details and the request is: {}", product);
        return productServiceInterface.addProduct(product);
    }

    @PutMapping("/product/{id}")
    public Product updateProductDetails(@RequestBody Product product, @PathVariable Long id) {
        log.info("Entering controller to update product details and the request is: {}", product);
        return productServiceInterface.updateProduct(product, id);
    }

    @DeleteMapping("/product/{id}")
    public Product deleteByProductId(@PathVariable Long id) {
        log.info("Entering controller to delete product details for {}", id);
        return productServiceInterface.deleteProduct(id);
    }
}
