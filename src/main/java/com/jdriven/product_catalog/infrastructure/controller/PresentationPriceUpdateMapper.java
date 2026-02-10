package com.jdriven.product_catalog.infrastructure.controller;

import com.jdriven.product_catalog.domain.PriceUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PresentationPriceUpdateMapper {
    PresentationPriceUpdateMapper INSTANCE = Mappers.getMapper(PresentationPriceUpdateMapper.class);

    List<PriceUpdate> presentationPricesToPriceUpdates(List<org.openapitools.model.PriceUpdate> priceUpdate);
}
