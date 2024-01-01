package org.sopt.sweet.domain.product.repository;

import org.sopt.sweet.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
