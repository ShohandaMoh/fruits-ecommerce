package com.project.fruits_ecommerce.services;

import com.project.fruits_ecommerce.dto.ProductRequest;
import com.project.fruits_ecommerce.dto.ProductResponse;
import com.project.fruits_ecommerce.entities.Product;
import com.project.fruits_ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest, MultipartFile imageFile) throws IOException {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setUnit(productRequest.getUnit());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImage(imageFile.getBytes());
        }

        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts(int page, int pageSize) {
        Pageable pageable =  PageRequest.of(page, pageSize);
        Page<Product> productPage = productRepository.findAll( pageable);
        return productPage.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setUnit(product.getUnit());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());
        response.setImageBase64(Base64.getEncoder().encodeToString(product.getImage()));
        return response;
    }
}


