package com.kajal.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateRequest {
    private String cartItemId;
    private int quantity;

    public String getCartItemId() {
        return cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
