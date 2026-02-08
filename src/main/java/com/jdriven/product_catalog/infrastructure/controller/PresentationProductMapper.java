package com.jdriven.product_catalog.infrastructure.controller;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.Product;

@Mapper(componentModel = "spring")
public interface PresentationProductMapper {
    PresentationProductMapper INSTANCE = Mappers.getMapper(PresentationProductMapper.class);

    Product toPresentationProduct(com.jdriven.product_catalog.domain.Product product);

    com.jdriven.product_catalog.domain.Product toDomainProduct(Product product);

}
