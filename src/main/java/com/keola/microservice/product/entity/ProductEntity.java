package com.keola.microservice.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "tbl_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @Column("id")
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres")
    @Column("name")
    private String name;

    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    @Column("description")
    private String description;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Column("price")
    private BigDecimal price;

    @Positive(message = "La cantidad debe ser mayor que 0")
    @Column("quantity")
    private int quantity;

    @Column("category")
    private String category;

    @Column("image_url")
    private String imageUrl;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Column("brand")
    private String brand;

    @Column("status")
    private String status;
}
