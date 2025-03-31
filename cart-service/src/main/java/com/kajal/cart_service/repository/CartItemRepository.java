package com.kajal.cart_service.repository;

import com.kajal.cart_service.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartItemRepository  extends MongoRepository<CartItem, String> {
    Optional<CartItem> findByProductIdAndCart_Id(String productId, String cartId);
}
