package com.product.management.service;

import com.product.management.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductServiceInterface {
    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }
}
