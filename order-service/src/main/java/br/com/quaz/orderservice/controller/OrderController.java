package br.com.quaz.orderservice.controller;

import br.com.quaz.orderservice.dto.OrderRequest;
import br.com.quaz.orderservice.dto.OrderResponse;
import br.com.quaz.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody final OrderRequest orderRequest, final UriComponentsBuilder uriComponentsBuilder) {
        final var placeOrder = orderService.placeOrder(orderRequest);
        final var uri = uriComponentsBuilder.path("/api/order/{id}").buildAndExpand(placeOrder.uuid()).toUri();
        return ResponseEntity.created(uri).body(placeOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(value = "id") final UUID uuid) {
        return ResponseEntity.ok(orderService.getOrderById(uuid));
    }
}
