package com.project.fruits_ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String unit;
    private BigDecimal price;
    private String description;
    private String imageBase64;
}
