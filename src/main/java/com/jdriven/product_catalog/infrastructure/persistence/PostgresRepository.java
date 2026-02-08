package com.jdriven.product_catalog.infrastructure.persistence;

import com.jdriven.product_catalog.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface PostgresRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);
}
