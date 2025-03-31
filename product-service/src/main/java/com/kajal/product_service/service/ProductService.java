package com.kajal.product_service.service;

import com.kajal.product_service.dto.ProductRequest;
import com.kajal.product_service.dto.ProductResponse;
import com.kajal.product_service.dto.ProductUpdateRequest;
import com.kajal.product_service.entity.Product;
import com.kajal.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse getProductById(String productId){
        Product product = fetchProductById(productId);
        return mapToProductResponse(product);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getStockQuantity()
        );
        product = productRepository.save(product);
        return mapToProductResponse(product);
    }

    public void removeProduct(String productId) {
        Product product = fetchProductById(productId);
        productRepository.delete(product);
    }

    public ProductResponse updateProduct(String productId, ProductUpdateRequest productUpdateRequest) {
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
        product = productRepository.save(product);
        return mapToProductResponse(product);
    }

    public ProductResponse updateStock(String productId, int amount) {
        System.out.println("Here");
        Product product = fetchProductById(productId);
        int newQuantity = product.getStockQuantity() + amount;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Product ID " + productId + " is out of stock!");
        }
        product.setStockQuantity(newQuantity);
        product = productRepository.save(product);
        return mapToProductResponse(product);
    }

    public Product fetchProductById(String productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
    }

    private ProductResponse mapToProductResponse(Product product) {
       return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
