package com.kajal.cart_service.controller;

import com.kajal.cart_service.dto.CartItemRequest;
import com.kajal.cart_service.dto.CartResponse;
import com.kajal.cart_service.service.AuthService;
import com.kajal.cart_service.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthService authService;

    @GetMapping()
    public ResponseEntity<?> getCart(HttpServletRequest request) {
        try{
            String userId = authService.getUserIdWithValidation(request);
            CartResponse cart = cartService.getCartByUserId(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (RuntimeException e){
//            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestBody CartItemRequest cartItemRequest, HttpServletRequest request) {
        try{
            String userId = authService.getUserIdWithValidation(request);
            CartResponse cart = cartService.addToCart(cartItemRequest, userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
