package com.kajal.product_service.controller;


import com.kajal.product_service.dto.ProductRequest;
import com.kajal.product_service.dto.ProductUpdateRequest;
import com.kajal.product_service.entity.Product;
import com.kajal.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(
            @RequestBody @Valid ProductRequest productRequest) {
        Product createdProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable int productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable int productId,
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {

        Product updatedProduct = productService.updateProduct(productId, productUpdateRequest);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PatchMapping("/updateStock/{productId}")
    public ResponseEntity<?> updateStock(@PathVariable int productId, @RequestBody int amount){
        Product updatedProduct = productService.updateStock(productId, amount);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable int productId) {
        Product product = productService.fetchProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

}
