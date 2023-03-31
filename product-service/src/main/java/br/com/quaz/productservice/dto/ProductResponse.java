package br.com.quaz.productservice.dto;

import br.com.quaz.productservice.entities.Product;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String description, BigDecimal price) {
    public ProductResponse(final Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
