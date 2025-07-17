package com.skr.learning.bookstore.catalog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  //	Optional<Product> findByCode(String code);
}
