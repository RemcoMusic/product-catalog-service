package com.jdriven.product_catalog.domain;

public record Product(String serialNumber, String name, String description, String category, Double price, String currency) {
}
