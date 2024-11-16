package com.product.management.service;

import com.product.management.model.Product;

import java.util.List;

public interface ProductServiceInterface {

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product addProduct(Product product);

    Product updateProduct(Product product, Long id);

    Product deleteProduct(Long id);
}
