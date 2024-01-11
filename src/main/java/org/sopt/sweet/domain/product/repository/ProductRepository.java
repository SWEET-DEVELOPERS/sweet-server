package org.sopt.sweet.domain.product.repository;

import org.sopt.sweet.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p ORDER BY RAND() LIMIT :limit")
    List<Product> findRandomProducts(@Param("limit") int limit);
}
