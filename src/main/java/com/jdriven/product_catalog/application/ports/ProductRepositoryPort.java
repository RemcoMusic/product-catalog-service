package com.jdriven.product_catalog.application.ports;

import com.jdriven.product_catalog.domain.Product;

public interface ProductRepositoryPort {
    Product save(Product product);
}
