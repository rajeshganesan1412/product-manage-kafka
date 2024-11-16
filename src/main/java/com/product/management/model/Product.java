package com.product.management.model;

import com.product.management.enumuration.Category;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@Entity
public class Product implements Serializable {

    private Long id;

    private String productName;

    private String description;

    private Category category;

    private BigDecimal price;

    private Integer availableQuantity;

    private Boolean isAvailable;

}
