package com.jdriven.product_catalog.infrastructure.persistence;

import com.jdriven.product_catalog.application.ports.ProductRepositoryPort;
import com.jdriven.product_catalog.domain.Product;
import com.jdriven.product_catalog.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final PostgresRepository postgresRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = postgresRepository
                .save(PersistenceProductMapper.INSTANCE.productToProductEntity(product));
        return PersistenceProductMapper.INSTANCE.productEntityToProduct(productEntity);
    }

    @Override
    public List<Product> findAllProducts() {
        return postgresRepository.findAll().stream()
                .map(PersistenceProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    public List<Product> findAllBySerialNumbers(List<String> serialNumbers) {
        return postgresRepository.findAllBySerialNumberIn(serialNumbers).stream()
                .map(PersistenceProductMapper.INSTANCE::productEntityToProduct)
                .toList();
    }

    @Override
    public Optional<Product> findBySerialNumber(String serialNumber) {
        Optional<ProductEntity> entityProduct = postgresRepository.findBySerialNumber(serialNumber);
        return entityProduct.map(PersistenceProductMapper.INSTANCE::productEntityToProduct);
    }

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return postgresRepository.existsBySerialNumber(serialNumber);
    }

    @Override
    public List<Product> fullTextSearch(String search) {
        return PersistenceProductMapper.INSTANCE.productsToProductEntities(postgresRepository.searchFullText(search));
    }
}
