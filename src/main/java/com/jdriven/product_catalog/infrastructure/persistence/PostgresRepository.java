package com.jdriven.product_catalog.infrastructure.persistence;

import com.jdriven.product_catalog.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface PostgresRepository extends JpaRepository<ProductEntity, Long> {
}
