package com.product.management.service;

import com.product.management.model.Cart;

public interface CartServiceInterface {

    Cart addItemsToCart(Long id, Integer Quantity);


    Cart getCartItemsById(Long id);
}
