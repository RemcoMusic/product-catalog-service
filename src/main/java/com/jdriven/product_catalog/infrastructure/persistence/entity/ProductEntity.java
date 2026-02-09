package com.jdriven.product_catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private String name;

    private String description;

    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String currency;
}
