package com.kajal.product_service.controller;


import com.kajal.product_service.dto.ProductRequest;
import com.kajal.product_service.dto.ProductResponse;
import com.kajal.product_service.dto.ProductUpdateRequest;
import com.kajal.product_service.entity.Product;
import com.kajal.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        List<ProductResponse> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
            @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse createdProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable String productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {

        ProductResponse updatedProduct = productService.updateProduct(productId, productUpdateRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PutMapping("/updateStock/{productId}")
    public ResponseEntity<?> updateStock(@PathVariable String productId, @RequestBody int amount){
        ProductResponse updatedProduct = productService.updateStock(productId, amount);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        ProductResponse product = productService.getProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

}
