package com.jdriven.product_catalog.infrastructure.persistence;

import com.jdriven.product_catalog.domain.Product;
import com.jdriven.product_catalog.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersistenceProductMapper {
    PersistenceProductMapper INSTANCE = Mappers.getMapper(PersistenceProductMapper.class);

    ProductEntity productToProductEntity(Product product);
    Product productEntityToProduct(ProductEntity productEntity);
    List<Product> productsToProductEntities(List<ProductEntity> productEntities);
}
