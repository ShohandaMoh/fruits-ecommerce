package com.project.fruits_ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String unit;
    private BigDecimal price;
    private String description;
}
