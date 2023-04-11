package br.com.quaz.orderservice.services;

import br.com.quaz.orderservice.dto.InventoryResponse;
import br.com.quaz.orderservice.dto.OrderRequest;
import br.com.quaz.orderservice.dto.OrderResponse;
import br.com.quaz.orderservice.entities.Order;
import br.com.quaz.orderservice.entities.OrderLineItems;
import br.com.quaz.orderservice.exceptions.BadRequestException;
import br.com.quaz.orderservice.exceptions.NotFoundException;
import br.com.quaz.orderservice.exceptions.ServiceUnavailableException;
import br.com.quaz.orderservice.repositories.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    @Value("${web-client.inventory-service.host}")
    private String host;

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory", fallbackMethod = "fallbackMethod")
    public OrderResponse placeOrder(final OrderRequest orderRequest) {
        final var order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderRequest
                        .orderLineItemsDtoList()
                        .stream()
                        .map(orderLineItemsDto -> OrderLineItems.builder()
                                .skuCode(orderLineItemsDto.skuCode())
                                .quantity(orderLineItemsDto.quantity())
                                .price(orderLineItemsDto.price())
                                .build())
                        .toList())
                .build();

        final var skuCodeList = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        final var inventoryResponseArray = webClientBuilder.build().get()
                .uri(host, uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (Objects.isNull(inventoryResponseArray) || inventoryResponseArray.length == 0) {
            throw new BadRequestException("No items with sku codes provided found");
        }

        final var allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (Boolean.FALSE.equals(allProductsInStock)) {
            throw new NotFoundException("Product is not in stock, please try again later");
        }

        final var orderEntity = orderRepository.save(order);
        return new OrderResponse(orderEntity);
    }

    public OrderResponse getOrderById(final UUID uuid) {
        final var order = orderRepository.findById(uuid).orElseThrow();
        return new OrderResponse(order);
    }

    public OrderResponse fallbackMethod(final OrderRequest orderRequest, final RuntimeException runtimeException) {
        log.error("Error, calling inventory micro service. Error: {}", runtimeException.getMessage());
        throw new ServiceUnavailableException("Service unavailable");
    }
}
