package com.jdriven.product_catalog.infrastructure.persistence;

import com.jdriven.product_catalog.application.ports.ProductRepositoryPort;
import com.jdriven.product_catalog.domain.Product;
import com.jdriven.product_catalog.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    PostgresRepository postgresRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = postgresRepository
                .save(PersistenceProductMapper.INSTANCE.productToProductEntity(product));
        return PersistenceProductMapper.INSTANCE.productEntityToProduct(productEntity);
    }
}
