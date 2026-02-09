package com.jdriven.product_catalog.application.service;

import com.jdriven.product_catalog.application.exception.ProductAlreadyExistsException;
import com.jdriven.product_catalog.application.exception.ProductNotFoundException;
import com.jdriven.product_catalog.application.ports.ProductRepositoryPort;
import com.jdriven.product_catalog.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepositoryPort productRepositoryPort;

    public Product createProduct(Product product) {
        if(productRepositoryPort.existsBySerialNumber(product.serialNumber())){
            throw new ProductAlreadyExistsException(product.serialNumber());
        }
        return productRepositoryPort.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepositoryPort.findAllProducts();
    }

    public Product findBySerialNumber(String serialNumber) {
        return productRepositoryPort
                .findBySerialNumber(serialNumber)
                .orElseThrow(() -> new ProductNotFoundException(serialNumber));
    }

    public List<Product> fullTextSearch(String search) {
        return productRepositoryPort.fullTextSearch(search);
    }
}
