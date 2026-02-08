package com.jdriven.product_catalog.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProductEntity {
    @Id
    private Long id;
}
