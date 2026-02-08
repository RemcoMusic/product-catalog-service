package com.jdriven.product_catalog.infrastructure.controller;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PresentationProductMapper {
    PresentationProductMapper INSTANCE = Mappers.getMapper(PresentationProductMapper.class);

    Product toPresentationProduct(com.jdriven.product_catalog.domain.Product product);
    List<Product> toPresentationProducts(List<com.jdriven.product_catalog.domain.Product> products);

    com.jdriven.product_catalog.domain.Product toDomainProduct(Product product);

}
