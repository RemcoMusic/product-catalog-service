package com.jdriven.product_catalog.infrastructure.controller;

import com.jdriven.product_catalog.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.ProductsApi;
import org.openapitools.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductsController implements ProductsApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<List<Product>> productsGet() {
        log.info("Getting products");
        return ResponseEntity.ok(PresentationProductMapper.INSTANCE
                .toPresentationProducts(productService.findAllProducts()));
    }

    @Override
    public ResponseEntity<Product> productsSerialNumberGet(String serialNumber) {
        log.info("Getting product by serial number {}", serialNumber);
        return ResponseEntity.ok(PresentationProductMapper.INSTANCE
                        .toPresentationProduct(productService.findBySerialNumber(serialNumber)));
    }

    @Override
    public ResponseEntity<Product> productsPost(Product product) {
        log.info("Creating product {}", product);
        com.jdriven.product_catalog.domain.Product domainProduct = productService
                .createProduct(PresentationProductMapper.INSTANCE.toDomainProduct(product));
        log.info("Created product {}", domainProduct);
        return ResponseEntity
                .status(201)
                .body(PresentationProductMapper.INSTANCE.toPresentationProduct(domainProduct));
    }

    @Override
    public ResponseEntity<List<Product>> productsSearchGet(String q) {
        return ResponseEntity.ok(PresentationProductMapper.INSTANCE.toPresentationProducts(productService.fullTextSearch(q)));
    }
}
