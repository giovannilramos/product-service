package br.com.quaz.productservice.services;

import br.com.quaz.productservice.dto.ProductRequest;
import br.com.quaz.productservice.dto.ProductResponse;
import br.com.quaz.productservice.entities.Product;
import br.com.quaz.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductResponse createProduct(final ProductRequest productRequest) {
        final var product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.name())
                .price(productRequest.price())
                .build();
        final var productEntity = productRepository.save(product);
        return new ProductResponse(productEntity);
    }

    public ProductResponse getProductById(final String id) {
        final var product = productRepository.findById(id).orElseThrow();
        return new ProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductResponse::new).toList();
    }
}
