package com.keola.microservice.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.keola.microservice.product.constants.StateProduct;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @JsonProperty("description")
    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    private String description;

    @JsonProperty("price")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @JsonProperty("quantity")
    @Positive(message = "Quantity must be greater than 0")
    private int quantity;

    @JsonProperty("category")
    @NotBlank(message = "Category is required")
    private String category;

    @JsonProperty("imageUrl")
    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @JsonProperty("brand")
    @Size(max = 100, message = "Brand name should not exceed 100 characters")
    private String brand;

    @JsonProperty("status")
    @NotNull(message = "Status cannot be null")
    private StateProduct status;
}
