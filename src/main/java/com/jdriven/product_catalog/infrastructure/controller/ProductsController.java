package com.jdriven.product_catalog.infrastructure.controller;

import com.jdriven.product_catalog.application.service.CreateProductService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ProductsApi;
import org.openapitools.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductsController implements ProductsApi {

    Logger log = LoggerFactory.getLogger(ProductsController.class);

    final CreateProductService createProductService;

    //TODO
    @Override
    public ResponseEntity<List<Product>> productsGet() {
        return ProductsApi.super.productsGet();
    }

    @Override
    public ResponseEntity<Product> productsPost(Product product) {
        log.info("Creating product {}", product);
        com.jdriven.product_catalog.domain.Product domainProduct = createProductService
                .createProduct(PresentationProductMapper.INSTANCE.toDomainProduct(product));
        log.info("Created product {}", domainProduct);
        return ResponseEntity.ok(PresentationProductMapper.INSTANCE.toPresentationProduct(domainProduct));
    }

    //TODO
    @Override
    public ResponseEntity<Product> productsProductIdGet(String productId) {
        return ProductsApi.super.productsProductIdGet(productId);
    }

    //TODO
    @Override
    public ResponseEntity<List<Product>> productsSearchGet(String q) {
        return ProductsApi.super.productsSearchGet(q);
    }
}
