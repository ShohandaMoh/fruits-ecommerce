package com.project.fruits_ecommerce.services;

import com.project.fruits_ecommerce.dto.ProductRequest;
import com.project.fruits_ecommerce.dto.ProductResponse;
import com.project.fruits_ecommerce.entities.Product;
import com.project.fruits_ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class ProductServiceTest {

        @Mock
        private ProductRepository productRepository;

        @InjectMocks
        private ProductService productService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateProduct() throws IOException {
            // Mock the ProductRequest and MultipartFile
            ProductRequest productRequest = new ProductRequest();
            productRequest.setName("Test Product");
            productRequest.setUnit("kg");
            productRequest.setPrice(BigDecimal.valueOf(20.5));
            productRequest.setDescription("Test Description");

            MultipartFile imageFile = mock(MultipartFile.class);
            when(imageFile.isEmpty()).thenReturn(false);
            when(imageFile.getBytes()).thenReturn("test image".getBytes());

            // Act
            productService.createProduct(productRequest, imageFile);

            // Verify that save was called
            verify(productRepository, times(1)).save(any(Product.class));
        }

        @Test
        void testGetAllProducts() {
            // Mocking Pageable and Page
            int page = 0;
            int pageSize = 2;
            Pageable pageable = PageRequest.of(page, pageSize);

            Product product = new Product();
            product.setId(1L);
            product.setName("Test Product");
            product.setUnit("kg");
            product.setPrice(BigDecimal.valueOf(20.5));
            product.setDescription("Test Description");
            product.setImage("test image".getBytes());

            List<Product> productList = Collections.singletonList(product);
            Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());

            when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

            // Act
            List<ProductResponse> productResponses = productService.getAllProducts(page, pageSize);

            // Assert
            assertEquals(1, productResponses.size());
            assertEquals("Test Product", productResponses.get(0).getName());
            assertEquals("kg", productResponses.get(0).getUnit());
            assertEquals(BigDecimal.valueOf(20.5), productResponses.get(0).getPrice());
            assertEquals("Test Description", productResponses.get(0).getDescription());
            assertEquals(Base64.getEncoder().encodeToString("test image".getBytes()), productResponses.get(0).getImageBase64());

            // Verify that findAll was called
            verify(productRepository, times(1)).findAll(pageable);
        }
    }


