package com.kajal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.*;

import java.util.Random;


@Data
@NoArgsConstructor  // JPA needs this
@AllArgsConstructor // Required for @Builder to work
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    private int productId;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @Size(max = 50000, message = "Description should not exceed 500 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private double price;

    @Min(value = 0, message = "Stock quantity must be zero or greater")
    private int stockQuantity;

    public Product(String name, String description, double price, int stockQuantity) {
        Random random = new Random();
        this.productId = random.nextInt(1000000);
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity){
        this.stockQuantity = stockQuantity;
    }

    public int getStockQuantity(){
        return stockQuantity;
    }

}