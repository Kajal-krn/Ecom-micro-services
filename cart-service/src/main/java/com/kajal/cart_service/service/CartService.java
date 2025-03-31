package com.kajal.cart_service.service;

import com.kajal.cart_service.dto.CartItemRequest;
import com.kajal.cart_service.dto.CartItemResponse;
import com.kajal.cart_service.dto.CartResponse;
import com.kajal.cart_service.entity.Cart;
import com.kajal.cart_service.entity.CartItem;
import com.kajal.cart_service.repository.CartItemRepository;
import com.kajal.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public CartResponse getCartByUserId(String userId){
        Optional<Cart> existingCartOptional =  cartRepository.findByUserId(userId);

        if(existingCartOptional.isPresent()){
            Cart cart = existingCartOptional.get();
            return mapCartToResponse(cart);
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart = cartRepository.save(cart);

        return mapCartToResponse(cart);
    }

    public CartResponse addToCart(CartItemRequest cartItemRequest, String userId) {
        Optional<Cart> existingCartOptional = cartRepository.findByUserId(userId);

        Cart cart;
        if(existingCartOptional.isPresent()){
            cart = existingCartOptional.get();
        }else{
            cart = new Cart();
            cart.setUserId(userId);
        }

        Optional<CartItem> cartItemOptional = cartItemRepository.findByProductIdAndCart_Id(
                        cartItemRequest.getProductId(), cart.getId()
        );

        CartItem cartItem = null;
        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
        }else
        {
            cartItem = new CartItem();
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setQuantity(0);
            cartItem.setCart(cart);
        }

        cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
        cartItem = cartItemRepository.save(cartItem);

        if(cartItemOptional.isEmpty()){
            cart.addCartItem(cartItem);
        }

        cartRepository.save(cart);
        existingCartOptional = cartRepository.findByUserId(userId); // as cart is old data
        if(existingCartOptional.isPresent()){
            cart = existingCartOptional.get();
        }

        return mapCartToResponse(cart);
    }

    private CartResponse mapCartToResponse(Cart cart) {
        List<CartItemResponse> cartItemsResponse = cart.getCartItems().stream().map(this::mapCartItemsToResponse).toList();
        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                cartItemsResponse
        );
    }

    private CartItemResponse mapCartItemsToResponse(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProductId(),
                cartItem.getQuantity()
        );
    }
}
