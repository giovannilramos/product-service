package br.com.quaz.orderservice.dto;

import br.com.quaz.orderservice.entities.OrderLineItems;

import java.math.BigDecimal;

public record OrderLineItemsDto(String skuCode, BigDecimal price, Integer quantity) {
    public OrderLineItemsDto(final OrderLineItems orderLineItems) {
        this(orderLineItems.getSkuCode(), orderLineItems.getPrice(), orderLineItems.getQuantity());
    }
}
