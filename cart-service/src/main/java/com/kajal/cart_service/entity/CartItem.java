package com.kajal.cart_service.entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CartItem")
@Data
public class CartItem {
    @Id
    private String id;

    @DBRef
    private Cart cart;

    @NotNull(message = "Product ID == null")
    @NotBlank
    private String productId;

    @Min(value = 1, message = "Quantity < 1")
    private int quantity;

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setCart(Cart cart){
        this.cart = cart;
    }
}
