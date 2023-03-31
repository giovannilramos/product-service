package br.com.quaz.orderservice.dto;

public record InventoryResponse(String skuCode, Boolean isInStock) {
}
