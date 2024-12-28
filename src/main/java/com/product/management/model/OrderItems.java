package com.product.management.model;

import com.product.management.enumuration.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems implements Serializable {

    private Long orderItemId;

    private Long productId;

    private String productName;

    private String description;

    private Category category;

    private BigDecimal price;

    private Integer quantity;
}
