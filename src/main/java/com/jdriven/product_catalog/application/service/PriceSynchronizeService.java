package com.jdriven.product_catalog.application.service;

import com.jdriven.product_catalog.application.ports.ProductRepositoryPort;
import com.jdriven.product_catalog.domain.PriceUpdate;
import com.jdriven.product_catalog.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceSynchronizeService {

    private final ProductRepositoryPort productRepositoryPort;

    public void updatePrices(List<PriceUpdate> updatedPrices) {
        Map<String, PriceUpdate> updateMap = updatedPrices.stream()
                .collect(Collectors.toMap(PriceUpdate::serialNumber, Function.identity()));

        List<Product> products = productRepositoryPort.findAllBySerialNumbers(new ArrayList<>(updateMap.keySet()));

        List<Product> updatedProducts = products.stream()
                .map(product -> {
                    PriceUpdate priceUpdate = updateMap.get(product.serialNumber());
                    return new Product(
                            product.id(),
                            product.serialNumber(),
                            product.name(),
                            product.description(),
                            product.category(),
                            priceUpdate.price(),
                            priceUpdate.currency()
                    );
                }).toList();

        updatedProducts.forEach(productRepositoryPort::save);
    }
}
