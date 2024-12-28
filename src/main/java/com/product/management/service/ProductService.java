package com.product.management.service;

import com.product.management.exception.ProductNotFoundException;
import com.product.management.model.OrderItems;
import com.product.management.model.Orders;
import com.product.management.model.Product;
import com.product.management.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long id) {
        log.info("Getting product details for Id {}", id);
        return Optional.of(productRepository.findById(id)).get().orElseThrow(() -> new ProductNotFoundException("No Product available in this id", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Getting all product details from DB");
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ProductNotFoundException("No data found in DB", HttpStatus.NOT_FOUND);
        }
        return productList;
    }

    @Override
    public Product addProduct(Product product) {
        log.info("Adding product into DB and the request is {}", product);
        return productRepository.save(product);
    }

    @Override
    public List<Product> addAllProduct(List<Product> product) {
        log.info("Adding product into DB and the request is {}", product);
        return productRepository.saveAll(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        log.info("Updating product into DB and the id {} and request is {}", id, product);
        Product productForId = Optional.of(productRepository.findById(id)).get().orElseThrow(() -> new ProductNotFoundException("No Product available in this id", HttpStatus.NOT_FOUND));
        productForId = Product.builder()
                .id(id)
                .productName(product.getProductName() != null ? product.getProductName() : productForId.getProductName())
                .description(product.getDescription() != null ? product.getDescription() : productForId.getDescription())
                .category(product.getCategory() != null ? product.getCategory() : productForId.getCategory())
                .availableQuantity(product.getAvailableQuantity() != null ? product.getAvailableQuantity() : productForId.getAvailableQuantity())
                .isAvailable(product.getIsAvailable() != null ? product.getIsAvailable() : productForId.getIsAvailable())
                .build();
        return productRepository.save(productForId);
    }

    @Override
    public Product deleteProduct(Long id) {
        log.info("Deleting product details for Id {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product details not found in DB", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        return product.get();
    }

    public List<Product> updateProductQuantityAfterOrderPlaced(Orders order) {
        List<OrderItems> orderItems = Optional.of(order).map(Orders::getOrderItems)
                .orElseThrow(() -> new ProductNotFoundException("No OrderItems Found in the order", HttpStatus.NOT_FOUND));
        List<Product> productList = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            Product product = Optional.of(productRepository.findById(orderItem.getProductId())).get()
                    .orElseThrow(() -> new ProductNotFoundException("No Product available in this id", HttpStatus.NOT_FOUND));
            product.setAvailableQuantity(product.getAvailableQuantity() - orderItem.getQuantity());
            productList.add(product);
        });
        return productRepository.saveAll(productList);
    }
}
