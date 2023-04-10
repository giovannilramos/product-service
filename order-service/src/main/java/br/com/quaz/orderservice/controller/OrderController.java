package br.com.quaz.orderservice.controller;

import br.com.quaz.orderservice.dto.OrderRequest;
import br.com.quaz.orderservice.dto.OrderResponse;
import br.com.quaz.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<OrderResponse>> placeOrder(@RequestBody final OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(CREATED).body(orderService.placeOrder(orderRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(value = "id") final UUID uuid) {
        return ResponseEntity.ok(orderService.getOrderById(uuid));
    }

    public CompletableFuture<ResponseEntity<OrderResponse>> fallbackMethod(final OrderRequest orderRequest,
                                                                           final RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(SERVICE_UNAVAILABLE).build());
    }
}
