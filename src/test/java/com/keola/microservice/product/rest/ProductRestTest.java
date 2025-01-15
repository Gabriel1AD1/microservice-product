package com.keola.microservice.product.rest;

import com.keola.microservice.product.constants.StateProduct;
import com.keola.microservice.product.dto.CreateProductDTO;
import com.keola.microservice.product.dto.ReadProductDTO;
import com.keola.microservice.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Test class for testing the REST endpoints of ProductRest.
 * It contains unit tests to verify the behavior of the endpoints related to products.
 */
@WebFluxTest(ProductRest.class)
public class ProductRestTest {

    @Autowired
    private WebTestClient webTestClient;  // WebTestClient for testing WebFlux controllers

    @MockBean
    private ProductService productService; // Mocked ProductService to simulate service layer behavior

    /**
     * Test case for creating a product.
     * It simulates a POST request to create a new product and checks the response.
     */
    @Test
    void testCreateProduct() {
        // Creating a new product DTO to be sent in the request
        CreateProductDTO productDTO = new CreateProductDTO();
        productDTO.setName("New Product");
        productDTO.setDescription("New product description");
        productDTO.setPrice(new BigDecimal("150.00"));
        productDTO.setQuantity(30);
        productDTO.setCategory("Electronics");
        productDTO.setImageUrl("http://example.com/new_image");
        productDTO.setBrand("Brand X");
        productDTO.setStatus(StateProduct.activo);

        // Expected response after product creation
        ReadProductDTO expectedResponse = ReadProductDTO.builder()
                .id(1L)
                .name("New Product")
                .description("New product description")
                .price(new BigDecimal("150.00"))
                .quantity(30)
                .category("Electronics")
                .imageUrl("http://example.com/new_image")
                .status(StateProduct.activo.name())
                .build();

        // Mocking the service method call to return the expected product DTO
        when(productService.createProduct(any())).thenReturn(Mono.just(expectedResponse));

        // Sending the POST request to create the product
        webTestClient.post()
                .uri("/api/v1/products")
                .bodyValue(productDTO)
                .exchange()
                .expectStatus().isCreated() // Verifying the HTTP status code
                .expectBody(ReadProductDTO.class) // Verifying the response body type
                .isEqualTo(expectedResponse); // Verifying the response content
    }

    /**
     * Test case for retrieving all products.
     * It simulates a GET request to fetch a list of products and checks the response.
     */
    @Test
    void testGetAllProducts() {
        // Creating sample products to be returned by the service
        ReadProductDTO product1 = new ReadProductDTO(1L, "Product 1", "Description 1", new BigDecimal("100.00"), 10, "Category 1", "http://example.com/image1", StateProduct.activo.name());
        ReadProductDTO product2 = new ReadProductDTO(2L, "Product 2", "Description 2", new BigDecimal("150.00"), 20, "Category 2", "http://example.com/image2", StateProduct.activo.name());

        // Creating a Flux to simulate multiple products
        Flux<ReadProductDTO> productFlux = Flux.just(product1, product2);

        // Mocking the service method to return the list of products
        when(productService.getAllProducts()).thenReturn(productFlux);

        // Sending the GET request to retrieve all products
        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk() // Verifying the HTTP status code
                .expectBodyList(ReadProductDTO.class) // Verifying the response body as a list of products
                .contains(product1, product2); // Verifying the presence of the products in the response
    }

    /**
     * Test case for retrieving a product by its ID.
     * It simulates a GET request to fetch a product by ID and checks the response.
     */
    @Test
    void testGetProductById() {
        Long productId = 1L;

        // Creating a sample product to be returned by the service
        ReadProductDTO product = new ReadProductDTO(productId, "Product 1", "Description 1", new BigDecimal("100.00"), 10, "Category 1", "http://example.com/image1", StateProduct.activo.name());

        // Mocking the service method to return the product by its ID
        when(productService.getProductById(productId)).thenReturn(Mono.just(product));

        // Sending the GET request to retrieve the product by its ID
        webTestClient.get()
                .uri("/api/v1/products/{id}", productId)
                .exchange()
                .expectStatus().isOk() // Verifying the HTTP status code
                .expectBody(ReadProductDTO.class) // Verifying the response body type
                .isEqualTo(product); // Verifying the response content
    }

    /**
     * Test case for deleting a product by its ID.
     * It simulates a DELETE request to remove a product and checks the response.
     */
    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        // Mocking the service method to simulate product deletion
        when(productService.deleteProduct(productId)).thenReturn(Mono.empty());

        // Sending the DELETE request to remove the product by its ID
        webTestClient.delete()
                .uri("/api/v1/products/{id}", productId)
                .exchange()
                .expectStatus().isNoContent(); // Verifying the HTTP status code for successful deletion
    }
}
