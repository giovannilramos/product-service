package br.com.quaz.inventoryservice.dto;

public record InventoryResponse(String skuCode, Boolean isInStock) {
}
