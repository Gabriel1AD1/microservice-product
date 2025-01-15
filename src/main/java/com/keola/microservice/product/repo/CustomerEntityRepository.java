package com.keola.microservice.product.repo;

import com.keola.microservice.product.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerEntityRepository extends ReactiveCrudRepository<ProductEntity,Long> {

}
