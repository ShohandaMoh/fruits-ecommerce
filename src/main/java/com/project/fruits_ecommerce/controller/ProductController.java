package com.project.fruits_ecommerce.controller;

import com.project.fruits_ecommerce.dto.ProductRequest;
import com.project.fruits_ecommerce.dto.ProductResponse;
import com.project.fruits_ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductResponse> products = productService.getAllProducts(page, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createProduct(
            @ModelAttribute ProductRequest productRequest,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            productService.createProduct(productRequest, imageFile);
            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to create product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

