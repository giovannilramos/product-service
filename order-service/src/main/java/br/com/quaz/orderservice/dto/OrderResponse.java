package br.com.quaz.orderservice.dto;

import br.com.quaz.orderservice.entities.Order;

import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID uuid, String orderNumber, List<OrderLineItemsDto> orderLineItemsDtoList) {
    public OrderResponse(final Order order) {
        this(order.getUuid(), order.getOrderNumber(), order.getOrderLineItemsList().stream().map(OrderLineItemsDto::new).toList());
    }
}
