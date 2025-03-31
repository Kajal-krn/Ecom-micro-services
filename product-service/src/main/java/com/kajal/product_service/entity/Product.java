package com.kajal.product_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Random;

@Document(collection = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;

    @Indexed
    @NotBlank(message = "Invalid Name")
    private String name;

    @NotBlank(message = "Invalid Description")
    private String description;

    @Indexed
    @NotNull(message = "Invalid Price")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price > 0")
    private double price;

    @Min(value = 0, message = "Stock >= 0")
    private int stockQuantity;

    public Product(String name, String description, double price, int stockQuantity) {
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

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        return price;
    }

    public int getStockQuantity(){
        return stockQuantity;
    }

}