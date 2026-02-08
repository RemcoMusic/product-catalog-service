package com.jdriven.product_catalog.application.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String serialNumber) {
        super("Product already exists with serial number: " + serialNumber);
    }
}
