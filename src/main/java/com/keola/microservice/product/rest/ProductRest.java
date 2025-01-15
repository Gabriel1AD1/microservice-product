package com.keola.microservice.product.rest;

import com.keola.microservice.product.dto.CreateProductDTO;
import com.keola.microservice.product.dto.ReadProductDTO;
import com.keola.microservice.product.dto.UpdateProductDTO;
import com.keola.microservice.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.keola.microservice.product.rest.common.ApiVersion.api_v1;

@RestController
@RequestMapping(api_v1 + "products")
@AllArgsConstructor
public class ProductRest {

    private final ProductService productService;

    @Operation(summary = "Create a new product", description = "Allows creating a product in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public Mono<ResponseEntity<ReadProductDTO>> createProduct(@RequestBody @Valid CreateProductDTO productDTO) {
        return productService.createProduct(productDTO)
                .map(productDTOResponse -> ResponseEntity.status(HttpStatus.CREATED).body(productDTOResponse));
    }

    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public Mono<ResponseEntity<Flux<ReadProductDTO>>> getAllProducts() {
        return Mono.just(ResponseEntity.ok(productService.getAllProducts()));
    }

    @Operation(summary = "Get a product by ID", description = "Allows retrieving a product using its unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ReadProductDTO>> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a product", description = "Allows updating an existing product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Void>> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductDTO productDTO) {
        return productService.updateProduct(id, productDTO)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Delete a product", description = "Allows deleting a product from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
