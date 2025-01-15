package com.keola.microservice.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.keola.microservice.product.constants.StateProduct;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name cannot be null or empty")  // El nombre es obligatorio
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")  // Tamaño limitado
    @NotNull(message = "Name cannot be null")  // Asegura que el nombre no sea nulo
    private String name;

    @JsonProperty("description")
    @Size(max = 500, message = "Description cannot exceed 500 characters")  // Limitar tamaño de descripción
    @NotNull(message = "Description cannot be null")  // Asegura que la descripción no sea nula
    private String description;

    @JsonProperty("price")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")  // El precio debe ser mayor a 0
    @NotNull(message = "Price cannot be null")  // Asegura que el precio no sea nulo
    private BigDecimal price;

    @JsonProperty("quantity")
    @Positive(message = "Quantity must be greater than 0")  // La cantidad debe ser mayor que 0
    @NotNull(message = "Quantity cannot be null")  // Asegura que la cantidad no sea nula
    private Integer quantity;

    @JsonProperty("category")
    @Size(max = 100, message = "Category must be less than 100 characters")  // Limitar el tamaño de la categoría
    @NotNull(message = "Category cannot be null")  // Asegura que la categoría no sea nula
    private String category;

    @JsonProperty("imageUrl")
    @Size(max = 255, message = "Image URL must be less than 255 characters")  // Limitar el tamaño de la URL de imagen
    @NotNull(message = "Image URL cannot be null")  // Asegura que la URL de imagen no sea nula
    private String imageUrl;

    @JsonProperty("brand")
    @Size(max = 100, message = "Brand must be less than 100 characters")  // Limitar el tamaño de la marca
    @NotNull(message = "Brand cannot be null")  // Asegura que la marca no sea nula
    private String brand;

    @JsonProperty("status")
    @NotNull(message = "Status cannot be null")  // Asegura que el estado no sea nulo
    private StateProduct status;
}
