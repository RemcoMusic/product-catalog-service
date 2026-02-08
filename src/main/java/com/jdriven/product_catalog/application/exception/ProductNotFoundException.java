package com.jdriven.product_catalog.application.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String serialNumber) {
        super("Product not found with serial number: " + serialNumber);
    }
}
