package com.kajal.product_service.service;

import com.kajal.product_service.dto.ProductRequest;
import com.kajal.product_service.dto.ProductUpdateRequest;
import com.kajal.product_service.entity.Product;
import com.kajal.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Component
@Service
public class ProductService {

    private ProductRepository productRepository;

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getStockQuantity()
        );
        return productRepository.save(product);
    }

    public void removeProduct(int productId) {
        Product product = fetchProductById(productId);
        productRepository.delete(product);
    }

    public Product fetchProductById(int productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
    }

    public Product updateProduct(int productId, ProductUpdateRequest productUpdateRequest) {
        Product product = fetchProductById(productId);

        if (productUpdateRequest.getName() != null) {
            product.setName(productUpdateRequest.getName());
        }
        if (productUpdateRequest.getDescription() != null) {
            product.setDescription(productUpdateRequest.getDescription());
        }
        if (productUpdateRequest.getPrice() > 0) {
            product.setPrice(productUpdateRequest.getPrice());
        }
        if (productUpdateRequest.getStockQuantity() >= 0) {
            product.setStockQuantity(productUpdateRequest.getStockQuantity());
        }

        return productRepository.save(product);
    }

    public Product updateStock(int productId, int amount) {
        Product product = fetchProductById(productId);
        int newQuantity = product.getStockQuantity() - amount;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Product ID " + productId + " is out of stock!");
        }
        product.setStockQuantity(newQuantity);
        return productRepository.save(product);
    }
}
