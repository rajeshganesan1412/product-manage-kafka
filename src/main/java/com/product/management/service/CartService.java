package com.product.management.service;

import com.product.management.exception.ProductNotFoundException;
import com.product.management.model.Cart;
import com.product.management.model.CartProduct;
import com.product.management.model.Product;
import com.product.management.repository.CartProductRepository;
import com.product.management.repository.CartRepository;
import com.product.management.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class CartService implements CartServiceInterface{

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final CartProductRepository cartProductRepository;

    @Override
    @Transactional
    public Cart addItemsToCart(Long id, Integer quantity) {
        log.info("Adding items into cart");
        Product product =Optional.of(productRepository.findById(id)).get()
                .orElseThrow(() -> new ProductNotFoundException("No Product available in this id", HttpStatus.NOT_FOUND));
        List<CartProduct> cartProductList = cartProductRepository.saveAll(getCartProducts(quantity, product));
        Cart cart = Cart.builder()
                .cartProducts(cartProductList)
                .build();
        return cartRepository.save(cart);
    }

    private List<CartProduct> getCartProducts(Integer quantity, Product product) {
        log.info("Getting cart products list");
        List<CartProduct> cartProductList = new ArrayList<>();
        CartProduct cartProduct = CartProduct.builder()
                .cartProductId(generateCartProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .quantity(quantity)
                .price(product.getPrice())
                .build();
        cartProductList.add(cartProduct);
        return cartProductList;
    }


    @Override
    public Cart getCartItemsById(Long id) {
        log.info("Getting cart items by id");
        return Optional.of(cartRepository.findById(id)).get()
                .orElseThrow(() -> new ProductNotFoundException("No Product available in this id", HttpStatus.NOT_FOUND));
    }

    public Long generateCartProductId() {
        log.info("Generate random cart id");
        Random random = new Random();
        // Generate a random number between 1,000,000 and 9,999,999
        int min = 1000000;
        int max = 9999999;
        int randomNumber = random.nextInt(max - min + 1) + min;
        return (long) randomNumber;
    }
}
