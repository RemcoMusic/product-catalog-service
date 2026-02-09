package com.jdriven.product_catalog.infrastructure.persistence;

import com.jdriven.product_catalog.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface PostgresRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);

    @Query(value = """
        SELECT *
        FROM products
        WHERE search_vector @@ plainto_tsquery('simple', :query)
        """, nativeQuery = true)
    List<ProductEntity> searchFullText(@Param("query") String query);
}
