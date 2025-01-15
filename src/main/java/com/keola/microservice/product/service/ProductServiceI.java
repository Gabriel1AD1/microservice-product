package com.keola.microservice.product.service;

import com.keola.microservice.product.dto.CreateProductDTO;
import com.keola.microservice.product.dto.ReadProductDTO;
import com.keola.microservice.product.dto.UpdateProductDTO;
import com.keola.microservice.product.entity.ProductEntity;
import com.keola.microservice.product.exception.EntityNotFoundException;
import com.keola.microservice.product.mapper.ProductEntityMapper;
import com.keola.microservice.product.repo.CustomerEntityRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * ProductServiceI is a service class responsible for handling product-related operations.
 * It includes methods for creating, retrieving, updating, and deleting products.
 * It uses reactive programming with Reactor's Mono and Flux for non-blocking operations.
 * The service interacts with the database repository (CustomerEntityRepository)
 * and the entity mapper (ProductEntityMapper) to map entities to DTOs.
 */
@Service
@AllArgsConstructor
@Valid
public class ProductServiceI implements ProductService {

    private final CustomerEntityRepository productRepository; // Repository for accessing product entities.
    private final ProductEntityMapper productEntityMapper; // Mapper to convert between entity and DTO.

    /**
     * Creates a new product from the provided CreateProductDTO.
     * @param productDTO the data transfer object containing the product details.
     * @return a Mono wrapping the ReadProductDTO containing the created product's information.
     */
    @Override
    @Transactional
    public Mono<ReadProductDTO> createProduct(@RequestBody CreateProductDTO productDTO) {
        // Creating a new ProductEntity from the DTO
        ProductEntity product = ProductEntity.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .category(productDTO.getCategory())
                .imageUrl(productDTO.getImageUrl())
                .brand(productDTO.getBrand())
                .status(productDTO.getStatus().name())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // Saving the product to the repository and mapping the saved entity to a DTO
        return productRepository.save(product)
                .map(productEntityMapper::toDTO);  // Mapping entity to DTO
    }

    /**
     * Retrieves all products from the repository.
     * @return a Flux wrapping a stream of ReadProductDTOs representing all products.
     */
    @Override
    public Flux<ReadProductDTO> getAllProducts() {
        // Retrieving all products and mapping them to DTOs
        return productRepository.findAll()
                .map(productEntityMapper::toDTO);  // Mapping entity to DTO
    }

    /**
     * Retrieves a product by its ID.
     * @param id the ID of the product to retrieve.
     * @return a Mono wrapping the ReadProductDTO of the product if found.
     * @throws EntityNotFoundException if the product with the given ID does not exist.
     */
    @Override
    public Mono<ReadProductDTO> getProductById(@NotNull Long id) {
        // Finding the product by ID and handling the case if not found
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Producto no encontrado con ID: " + id)))  // Throwing an exception if not found
                .map(productEntityMapper::toDTO);  // Mapping entity to DTO
    }

    /**
     * Updates an existing product based on the provided ID and UpdateProductDTO.
     * @param id the ID of the product to update.
     * @param productDTO the DTO containing the updated product details.
     * @return a Mono indicating completion of the update operation.
     * @throws EntityNotFoundException if the product with the given ID does not exist.
     */
    @Override
    @Transactional
    public Mono<Void> updateProduct(@NotNull Long id, @RequestBody @Valid UpdateProductDTO productDTO) {
        // Finding the product by ID and updating its fields
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Producto no encontrado con ID: " + id)))  // Throwing an exception if not found
                .flatMap(product -> {
                    // Updating the product fields with the new data from the DTO
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setPrice(productDTO.getPrice());
                    product.setQuantity(productDTO.getQuantity());
                    product.setCategory(productDTO.getCategory());
                    product.setImageUrl(productDTO.getImageUrl());
                    product.setBrand(productDTO.getBrand());
                    product.setStatus(productDTO.getStatus().name());
                    product.setUpdatedAt(Instant.now()); // Setting the updated timestamp
                    return productRepository.save(product); // Saving the updated product
                })
                .then(); // Indicating the end of the operation with Mono<Void>
    }

    /**
     * Deletes a product based on the provided ID.
     * @param id the ID of the product to delete.
     * @return a Mono indicating completion of the delete operation.
     */
    @Override
    @Transactional
    public Mono<Void> deleteProduct(@NotNull Long id) {
        // Deleting the product by ID
        return productRepository.deleteById(id);
    }
}
