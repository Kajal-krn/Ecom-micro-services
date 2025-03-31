package com.kajal.product_service.dto;

public class ProductUpdateRequest {
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
