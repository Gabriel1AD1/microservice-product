package com.keola.microservice.product.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.keola.microservice.product.constants.StateProduct;
import com.keola.microservice.product.dto.*;
import com.keola.microservice.product.entity.ProductEntity;
import com.keola.microservice.product.exception.EntityNotFoundException;
import com.keola.microservice.product.mapper.ProductEntityMapper;
import com.keola.microservice.product.repo.CustomerEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

/**
 * Unit test class for testing ProductServiceI.
 * This class tests the behavior of ProductServiceI's methods using mocks and assertions to verify correct behavior.
 */
public class ProductServiceITest {

    @Mock
    private CustomerEntityRepository productRepository;

    @Mock
    private ProductEntityMapper productEntityMapper;

    private ProductServiceI productService;

    /**
     * Set up method that initializes the mock objects and the product service before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceI(productRepository, productEntityMapper);
    }

    /**
     * Test case for the createProduct method in ProductServiceI.
     * This test verifies that a product can be successfully created and returned.
     */
    @Test
    void testCreateProduct() {
        // Input data for creating a product
        CreateProductDTO productDTO = new CreateProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Description of test product");
        productDTO.setPrice(new BigDecimal("100.00"));
        productDTO.setQuantity(10);
        productDTO.setCategory("Category");
        productDTO.setImageUrl("http://example.com/image");
        productDTO.setBrand("Brand");
        productDTO.setStatus(StateProduct.activo);

        // Create a ProductEntity object based on the input data
        ProductEntity productEntity = ProductEntity.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .category(productDTO.getCategory())
                .imageUrl(productDTO.getImageUrl())
                .brand(productDTO.getBrand())
                .status(productDTO.getStatus().name())
                .build();

        // Create a response DTO from the product entity
        ReadProductDTO productDTOResponse = new ReadProductDTO();
        productDTOResponse.setName(productEntity.getName());
        productDTOResponse.setDescription(productEntity.getDescription());
        productDTOResponse.setPrice(productEntity.getPrice());
        productDTOResponse.setQuantity(productEntity.getQuantity());
        productDTOResponse.setCategory(productEntity.getCategory());
        productDTOResponse.setImageUrl(productEntity.getImageUrl());
        productDTOResponse.setStatus(productEntity.getStatus());

        // Mock the behavior of the repository and mapper
        when(productRepository.save(any(ProductEntity.class))).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toDTO(productEntity)).thenReturn(productDTOResponse);

        // Execute the method
        Mono<ReadProductDTO> result = productService.createProduct(productDTO);

        // Verify the result
        assertNotNull(result);
        assertEquals(productDTOResponse.getName(), result.block().getName());

        // Verify that the repository and mapper were called
        verify(productRepository, times(1)).save(any(ProductEntity.class));
        verify(productEntityMapper, times(1)).toDTO(any(ProductEntity.class));
    }

    /**
     * Test case for the getProductById method in ProductServiceI.
     * This test verifies that a product can be successfully retrieved by its ID.
     */
    @Test
    void testGetProductById() {
        // Input data
        Long productId = 1L;
        ProductEntity productEntity = ProductEntity.builder()
                .id(productId)
                .name("Test Product")
                .description("Description of test product")
                .price(new BigDecimal("100.00"))
                .quantity(10)
                .category("Category")
                .imageUrl("http://example.com/image")
                .brand("Brand")
                .status("active")
                .build();

        // Create the response DTO
        ReadProductDTO productDTOResponse = new ReadProductDTO();
        productDTOResponse.setId(productId);
        productDTOResponse.setName(productEntity.getName());
        productDTOResponse.setDescription(productEntity.getDescription());
        productDTOResponse.setPrice(productEntity.getPrice());
        productDTOResponse.setQuantity(productEntity.getQuantity());
        productDTOResponse.setCategory(productEntity.getCategory());
        productDTOResponse.setImageUrl(productEntity.getImageUrl());
        productDTOResponse.setStatus(productEntity.getStatus());

        // Mock the behavior of the repository and mapper
        when(productRepository.findById(productId)).thenReturn(Mono.just(productEntity));
        when(productEntityMapper.toDTO(productEntity)).thenReturn(productDTOResponse);

        // Execute the method
        Mono<ReadProductDTO> result = productService.getProductById(productId);

        // Verify the result
        assertNotNull(result);
        assertEquals(productDTOResponse.getName(), result.block().getName());

        // Verify that the repository and mapper were called
        verify(productRepository, times(1)).findById(productId);
        verify(productEntityMapper, times(1)).toDTO(any(ProductEntity.class));
    }

    /**
     * Test case for the getProductById method when the product is not found.
     * This test verifies that the correct exception is thrown when the product is not found.
     */
    @Test
    void testGetProductById_NotFound() {
        // Behavior when product is not found
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Mono.empty());

        // Execute the method
        Mono<ReadProductDTO> result = productService.getProductById(productId);

        // Verify that the exception is thrown
        assertThrows(EntityNotFoundException.class, result::block);

        // Verify that the repository was called
        verify(productRepository, times(1)).findById(productId);
    }

    /**
     * Test case for the deleteProduct method in ProductServiceI.
     * This test verifies that a product can be successfully deleted.
     */
    @Test
    void testDeleteProduct() {
        // Input data
        Long productId = 1L;

        // Mock the behavior of the repository
        when(productRepository.deleteById(productId)).thenReturn(Mono.empty());

        // Execute the method
        Mono<Void> result = productService.deleteProduct(productId);

        // Verify that the repository was called
        result.block();  // Should complete without error
        verify(productRepository, times(1)).deleteById(productId);
    }
}
