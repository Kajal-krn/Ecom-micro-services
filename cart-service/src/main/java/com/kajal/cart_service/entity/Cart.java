package com.kajal.cart_service.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Cart")
@Data
public class Cart {
    @Id
    private String id;

    @NotNull(message = "User ID == null")
    @NotBlank
    private String userId;

    @DBRef
    private List<CartItem> cartItems = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getUserId(){
        return userId;
    }

    public List<CartItem> getCartItems(){
        return cartItems;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void addCartItem(CartItem cartItem){
        cartItems.add(cartItem);
    }

}
