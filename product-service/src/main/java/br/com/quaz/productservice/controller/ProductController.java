package br.com.quaz.productservice.controller;

import br.com.quaz.productservice.services.ProductService;
import br.com.quaz.productservice.dto.ProductRequest;
import br.com.quaz.productservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody final ProductRequest productRequest, UriComponentsBuilder uriComponentsBuilder) {
        final var productResponse = productService.createProduct(productRequest);
        final var uri = uriComponentsBuilder.path("/api/product/{id}").buildAndExpand(productResponse.id()).toUri();
        return ResponseEntity.created(uri).body(productResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(value = "id") final String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
