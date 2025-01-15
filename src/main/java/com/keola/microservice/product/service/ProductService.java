package com.keola.microservice.product.service;


import com.keola.microservice.product.dto.CreateProductDTO;
import com.keola.microservice.product.dto.ReadProductDTO;
import com.keola.microservice.product.dto.UpdateProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ReadProductDTO> createProduct(CreateProductDTO productDTO);

    Flux<ReadProductDTO> getAllProducts();

    Mono<ReadProductDTO> getProductById(Long id);

    Mono<Void> updateProduct(Long id, UpdateProductDTO productDTO);

    Mono<Void> deleteProduct(Long id);
}
