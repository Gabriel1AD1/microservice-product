package com.keola.microservice.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadProductDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("category")
    private String category;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("status")
    private String status;
}
