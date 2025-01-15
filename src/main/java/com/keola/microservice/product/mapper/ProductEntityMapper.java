package com.keola.microservice.product.mapper;


import com.keola.microservice.product.dto.ReadProductDTO;
import com.keola.microservice.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "status", target = "status")
    })
    ReadProductDTO toDTO(ProductEntity entity);

    List<ReadProductDTO> toListDTO(List<ProductEntity> entity);

}
