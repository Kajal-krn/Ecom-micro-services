package com.kajal.cart_service.dto;

import java.util.ArrayList;
import java.util.List;

public class CartResponse {
    private String cartId;
    private String userId;
    private List<CartItemResponse> cartItems = new ArrayList<>();

    public CartResponse(){
    }

    public CartResponse(String cartId, String userId, List<CartItemResponse> cartItems) {
        this.cartId = cartId;
        this.userId = userId;
        this.cartItems = cartItems;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }
}
