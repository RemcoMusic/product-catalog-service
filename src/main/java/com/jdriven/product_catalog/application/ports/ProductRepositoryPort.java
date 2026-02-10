package com.jdriven.product_catalog.application.ports;

import com.jdriven.product_catalog.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

    Product save(Product product);

    List<Product> findAllProducts();

    List<Product> findAllBySerialNumbers(List<String> serialNumbers);

    Optional<Product> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);

    List<Product> fullTextSearch(String search);

}
